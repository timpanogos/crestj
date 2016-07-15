/*
 **  Copyright (c) 2016, Cascade Computer Consulting.
 **
 **  Permission to use, copy, modify, and/or distribute this software for any
 **  purpose with or without fee is hereby granted, provided that the above
 **  copyright notice and this permission notice appear in all copies.
 **
 **  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 **  WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 **  MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 **  ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 **  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 **  ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 **  OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package com.ccc.crest.core.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.cache.CrestRequestData;
import com.ccc.crest.core.cache.EveData;
import com.ccc.crest.core.cache.SourceFailureException;
import com.ccc.crest.core.events.CommsEventListener;
import com.ccc.tools.RequestThrottle;
import com.ccc.tools.StrH;
import com.ccc.tools.executor.BlockingExecutor;
import com.github.scribejava.core.model.OAuth2AccessToken;

@SuppressWarnings("javadoc")
public class CrestClient
{
    private static final String CacheControlHeader = "Cache-Control";
    private static final String CacheControlMaxAge = "max-age";
    private static final int CrestGeneralMaxRequestsPerSecond = 150;
    private static final int XmlGeneralMaxRequestsPerSecond = 30;

    private static String crestUrl;
    private static String xmlUrl;
    private static String userAgent;
    private static CrestController controller;
    private static RequestThrottle crestGeneralThrottle;
    private static RequestThrottle xmlGeneralThrottle;
    private static HashMap<String, RequestThrottle> crestThrottleMap;
    private static BlockingExecutor executor;

    private CrestClient(CrestController controller, String crestUrl, String xmlUrl, String userAgent, BlockingExecutor executor)
    {
        if (CrestClient.crestUrl == null)
        {
            CrestClient.controller = controller;
            CrestClient.crestUrl = StrH.stripTrailingSeparator(crestUrl);
            CrestClient.xmlUrl = StrH.stripTrailingSeparator(xmlUrl);
            CrestClient.userAgent = userAgent;
            crestGeneralThrottle = new RequestThrottle(CrestGeneralMaxRequestsPerSecond);
            xmlGeneralThrottle = new RequestThrottle(XmlGeneralMaxRequestsPerSecond);
            crestThrottleMap = new HashMap<>();
            CrestClient.executor = executor;
        }
    }

    public static String getCrestBaseUri()
    {
        if (crestUrl == null)
            throw new RuntimeException("someone must call the getClient method that supplies the urls and user agent");
        return crestUrl;
    }

    public static CrestClient getClient()
    {
        if (crestUrl == null)
            throw new RuntimeException("someone's initialize must call the getClient method that supplies the two urls");
        return new CrestClient(controller, crestUrl, xmlUrl, userAgent, executor);
    }

    public static CrestClient getClient(CrestController controller, String crestUrl, String xmlUrl, String userAgent, BlockingExecutor executor)
    {
        return new CrestClient(controller, crestUrl, xmlUrl, userAgent, executor);
    }

    public Future<EveData> getCrest(CrestRequestData requestData)
    {
        CloseableHttpClient client = HttpClients.custom().setUserAgent(userAgent).build();
        String accessToken = null;
        if (requestData.clientInfo != null)
            accessToken = ((OAuth2AccessToken) requestData.clientInfo.getAccessToken()).getAccessToken();
        HttpGet get = new HttpGet(requestData.url);
        if (accessToken != null)
        {
            get.addHeader("Authorization", "Bearer " + accessToken);
            if (requestData.scope != null)
                get.addHeader("Scope", requestData.scope);
        }
        if (requestData.version != null)
            get.addHeader("Accept", requestData.version);
        RequestThrottle apiThrottle = null;
        synchronized (crestThrottleMap)
        {
            apiThrottle = crestThrottleMap.get(requestData.url);
        }
        LoggerFactory.getLogger(getClass()).info("pre-executor.submit accessToken: " + accessToken);

        return executor.submit(new CrestGetTask(client, get, requestData, apiThrottle));
    }

    public String getXml(CrestClientInfo clientInfo) throws Exception
    {
        //TODO: move this to xml api later

        //        https://api.eveonline.com/eve/CharacterInfo.xml.aspx?characterID={character_id_here}

        CloseableHttpClient httpclient = HttpClients.custom().setUserAgent(userAgent).build();
        String accessToken = ((OAuth2AccessToken) clientInfo.getAccessToken()).getAccessToken();
        HttpGet httpGet = new HttpGet(xmlUrl);
        httpGet.addHeader("Authorization", "Bearer " + accessToken);
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        try
        {
            HttpEntity entity1 = response1.getEntity();
            InputStream is = entity1.getContent();
            String json = IOUtils.toString(is, "UTF-8");
            is.close();
            EntityUtils.consume(entity1);
            return json;
        } finally
        {
            response1.close();
        }
    }

    private class CrestGetTask implements Callable<EveData>
    {
        private final CrestRequestData rdata;
        private final HttpGet get;
        private final RequestThrottle apiThrottle;
        private final CloseableHttpClient client;

        private CrestGetTask(CloseableHttpClient client, HttpGet get, CrestRequestData rdata, RequestThrottle apiThrottle)
        {
            this.rdata = rdata;
            this.get = get;
            this.apiThrottle = apiThrottle;
            this.client = client;
        }

        @Override
        public EveData call() throws Exception
        {
            LoggerFactory.getLogger(getClass()).info("executing, pre-throttle: " + rdata.url);
            try
            {
                if (apiThrottle != null)
                    apiThrottle.waitAsNeeded();
                crestGeneralThrottle.waitAsNeeded();
                final AtomicInteger cacheTime = new AtomicInteger(0);
                ResponseHandler<String> responseHandler = new ResponseHandler<String>()
                {
                    @Override
                    public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException
                    {
                        LoggerFactory.getLogger(getClass()).info("handling response: " + response.toString());
                        Header[] headers = response.getHeaders(CacheControlHeader);
                        boolean found = false;
                        if (headers != null && headers.length > 0)
                            for (int i = 0; i < headers.length; i++)
                            {
                                HeaderElement[] headerElements = headers[i].getElements();
                                for (int j = 0; j < headerElements.length; j++)
                                    if (headerElements[i].getName().equals(CacheControlMaxAge))
                                    {
                                        cacheTime.set(Integer.parseInt(headerElements[i].getValue()));
                                        found = true;
                                        break;
                                    }
                            }
                        if (!found)
                        {
                            String msg = "Could not find " + CacheControlMaxAge + " " + response.getStatusLine().getReasonPhrase();
                            LoggerFactory.getLogger(getClass()).warn(msg);
                            throw new ClientProtocolException("Did not find " + CacheControlMaxAge + " in " + CacheControlHeader + " header");
                        }
                        int status = response.getStatusLine().getStatusCode();
                        if (status >= 200 && status < 300)
                        {
                            HttpEntity entity = response.getEntity();
                            return entity != null ? EntityUtils.toString(entity) : null;
                        }
                        String msg = "Unexpected response status: " + status + " " + response.getStatusLine().getReasonPhrase();
                        LoggerFactory.getLogger(getClass()).warn(msg);
                        throw new ClientProtocolException(msg);
                        //TODO: is it enough to just log it here, currently no one is calling get
                        // maybe just re-issue, what about connection down retries?  Need to look at status code ranges to determine which to retry on.
                    }
                };
                LoggerFactory.getLogger(getClass()).info("executing, post-throttle: " + rdata.url);
                String json = client.execute(get, responseHandler);
                EveData data = rdata.gson.fromJson(json, rdata.clazz);
                data.init();
                if (apiThrottle == null)
                {
                    RequestThrottle throttle = data.getThrottle(cacheTime.get());
                    // The throttle did not exist for the first call, which was needed to even obtain the throttle time.
                    // hit the new throttle once now to register the initiating call
                    throttle.waitAsNeeded(); // this will return immediately without blocking and register the first call
                    synchronized (data)
                    {
                        crestThrottleMap.put(rdata.url, throttle);
                    }
                }
                data.setCacheTimeInSeconds(cacheTime.get());
                data.refreshed();
                rdata.setCacheSeconds(cacheTime.get());
                rdata.callback.received(rdata, data);
                return data;
            } catch (Exception e)
            {
                synchronized (controller.dataCache)
                {
                    controller.dataCache.remove(rdata.url);
                    controller.fireCommunicationEvent(rdata.clientInfo, CommsEventListener.Type.CrestDown);
                }
                throw new SourceFailureException("HttpRequest for url: " + rdata.url + " failed", e);
            } finally
            {
                client.close();
            }
        }
    }
}
