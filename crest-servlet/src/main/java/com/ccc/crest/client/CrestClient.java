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
package com.ccc.crest.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.Callable;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.ccc.crest.cache.CrestData;
import com.ccc.crest.servlet.auth.CrestClientInfo;
import com.ccc.tools.RequestThrottle;
import com.ccc.tools.StrH;
import com.ccc.tools.app.executor.BlockingExecutor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.google.gson.Gson;

@SuppressWarnings("javadoc")
public class CrestClient
{
    public static final int CrestGeneralMaxRequestsPerSecond = 150;
    public static final int XmlGeneralMaxRequestsPerSecond = 30;

    private static String crestUrl;
    private static String xmlUrl;
    private static String userAgent;
    private static RequestThrottle crestGeneralThrottle;
    private static RequestThrottle xmlGeneralThrottle;
    private static HashMap<String, RequestThrottle> crestThrottleMap;
    private static BlockingExecutor executor;

    private CrestClient(String crestUrl, String xmlUrl, String userAgent, BlockingExecutor executor)
    {
        if (this.crestUrl == null)
        {
            this.crestUrl = StrH.stripTrailingSeparator(crestUrl);
            this.xmlUrl = StrH.stripTrailingSeparator(xmlUrl);
            this.userAgent = userAgent;
            crestGeneralThrottle = new RequestThrottle(CrestGeneralMaxRequestsPerSecond);
            xmlGeneralThrottle = new RequestThrottle(XmlGeneralMaxRequestsPerSecond);
            crestThrottleMap = new HashMap<String, RequestThrottle>();
            this.executor = executor;
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
            throw new RuntimeException("someone must call the getClient method that supplies the two urls");
        return new CrestClient(crestUrl, xmlUrl, userAgent, executor);
    }

    public static CrestClient getClient(String crestUrl, String xmlUrl, String userAgent, BlockingExecutor executor)
    {
        return new CrestClient(crestUrl, xmlUrl, userAgent, executor);
    }

    //@formatter:off
    public void getCrest(
                    CrestClientInfo clientInfo, String url, 
                    Gson gson, Class<? extends CrestData> crestDataclass, 
                    CrestResponseCallback callback,
                    int throttle, String scope, String version) throws Exception
    //@formatter:on
    {
        CloseableHttpClient client = HttpClients.custom().setUserAgent(userAgent).build();
        String accessToken = ((OAuth2AccessToken) clientInfo.getAccessToken()).getAccessToken();
        HttpGet get = new HttpGet(url);
        get.addHeader("Authorization", "Bearer " + accessToken);
        if (scope != null)
            get.addHeader("Scope", scope);
        if (version != null)
            get.addHeader("Accept", version);
        
        RequestThrottle apiThrottle = crestThrottleMap.get(url);

        executor.submit((new CrestGetTask(client, get, apiThrottle, gson, crestDataclass, url, throttle, callback)));
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

    private class CrestGetTask implements Callable<CrestData>
    {
        private final CloseableHttpClient client;
        private final HttpGet get;
        private final RequestThrottle apiThrottle;
        private final Gson gson;
        private Class<? extends CrestData> crestDataClass;
        private final String url;
        private final int throttle;
        private final CrestResponseCallback callback;

        //@formatter:off
        private CrestGetTask(
                        CloseableHttpClient client, HttpGet get, 
                        RequestThrottle apiThrottle, 
                        Gson gson, Class<? extends CrestData> crestDataClass,
                        String url, int throttle,
                        CrestResponseCallback callback)
        //@formatter:on
        {
            this.client = client;
            this.get = get;
            this.apiThrottle = apiThrottle;
            this.gson = gson;
            this.crestDataClass = crestDataClass;
            this.url = url;
            this.throttle = throttle;
            this.callback = callback;
        }

        @Override
        public CrestData call() throws Exception
        {
            try
            {
                if (apiThrottle != null)
                    apiThrottle.waitAsNeeded();
                crestGeneralThrottle.waitAsNeeded();

                ResponseHandler<String> responseHandler = new ResponseHandler<String>()
                {
                    @Override
                    public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException
                    {
                        int status = response.getStatusLine().getStatusCode();
                        if (status >= 200 && status < 300)
                        {
                            HttpEntity entity = response.getEntity();
                            return entity != null ? EntityUtils.toString(entity) : null;
                        }
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                };
                String json = client.execute(get, responseHandler);
                CrestData data = gson.fromJson(json, crestDataClass);
                if (apiThrottle == null)
                    crestThrottleMap.put(url, new RequestThrottle(throttle));
                callback.received(data);
                return data;
            } finally
            {
                client.close();
            }
        }
    }
}
