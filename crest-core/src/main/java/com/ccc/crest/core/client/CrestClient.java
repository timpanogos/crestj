/*
**  Copyright (c) 2016, Chad Adams.
**
**  This program is free software: you can redistribute it and/or modify
**  it under the terms of the GNU Lesser General Public License as 
**  published by the Free Software Foundation, either version 3 of the 
**  License, or any later version.
**
**  This program is distributed in the hope that it will be useful,
**  but WITHOUT ANY WARRANTY; without even the implied warranty of
**  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
**  GNU General Public License for more details.

**  You should have received copies of the GNU GPLv3 and GNU LGPLv3
**  licenses along with this program.  If not, see http://www.gnu.org/licenses
*/
package com.ccc.crest.core.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.cache.CrestRequestData;
import com.ccc.crest.core.cache.EveData;
import com.ccc.crest.core.cache.SourceFailureException;
import com.ccc.crest.core.client.xml.EveApi;
import com.ccc.crest.core.client.xml.EveApiSaxHandler;
import com.ccc.crest.core.events.CommsEventListener;
import com.ccc.tools.RequestThrottle;
import com.ccc.tools.StrH;
import com.github.scribejava.core.model.OAuth2AccessToken;

@SuppressWarnings("javadoc")
public class CrestClient
{
    private static final String CrestDeprecatedHeader = "X-Deprecated";
    private static final int NoLongerSupported = 406;
    private static final String CacheControlHeader = "Cache-Control";
    private static final String CacheControlMaxAge = "max-age";
    private static final int CrestGeneralMaxRequestsPerSecond = 150;
    private static final int XmlGeneralMaxRequestsPerSecond = 30;
    private static final int CrestMaxClients = 20;

    private static String crestUrl;
    private static String xmlUrl;
    private final String userAgent;
    private final CrestController controller;
    private final PriorityQueue<CrestRequestData> refreshQueue;
    private volatile ScheduledFuture<?> queueCheckFuture;
    private final List<ClientElement> crestClients;
    private final List<ClientElement> xmlClients;
    private final AtomicInteger crestClientIndex;
    private final AtomicInteger xmlClientIndex;
    private final Logger log;

    private final ExecutorService executor;

    public CrestClient(CrestController controller, String crestUrl, String xmlUrl, String userAgent, ExecutorService executor)
    {
        log = LoggerFactory.getLogger(getClass());
        this.controller = controller;
        this.crestUrl = StrH.stripTrailingSeparator(crestUrl);
        this.xmlUrl = StrH.stripTrailingSeparator(xmlUrl);
        this.userAgent = userAgent;
        refreshQueue = new PriorityQueue<>();
        this.executor = executor;
        crestClients = new ArrayList<>();
        xmlClients = new ArrayList<>();
        crestClientIndex = new AtomicInteger(-1);
        xmlClientIndex = new AtomicInteger(-1);
        for (int i = 0; i < CrestMaxClients; i++)
        {
            RequestThrottle crestGeneralThrottle = new RequestThrottle(CrestGeneralMaxRequestsPerSecond);
            RequestThrottle xmlGeneralThrottle = new RequestThrottle(XmlGeneralMaxRequestsPerSecond);
            CloseableHttpClient client = HttpClients.custom().setUserAgent(userAgent).build();
            ClientElement clientElement = new ClientElement(client, crestGeneralThrottle, xmlGeneralThrottle);
            crestClients.add(clientElement);
        }
        for (int i = 0; i < CrestMaxClients; i++)
        {
            RequestThrottle xmlcrestGeneralThrottle = new RequestThrottle(CrestGeneralMaxRequestsPerSecond);
            RequestThrottle xmlGeneralThrottle = new RequestThrottle(XmlGeneralMaxRequestsPerSecond);
            CloseableHttpClient client = HttpClients.custom().setUserAgent(userAgent).build();
            ClientElement clientElement = new ClientElement(client, xmlGeneralThrottle, xmlGeneralThrottle);
            xmlClients.add(clientElement);
        }
    }

    public void init()
    {
        queueCheckFuture = controller.scheduledExecutor.scheduleAtFixedRate(new QueueTask(), 1L, 1L, TimeUnit.SECONDS);
    }

    public void destroy()
    {
        try
        {
            if (queueCheckFuture != null)
                queueCheckFuture.cancel(true);
            if (refreshQueue != null)
                refreshQueue.clear();
            if (crestClients != null)
                for (int i = CrestMaxClients - 1; i >= 0; i--)
                    crestClients.get(i).client.close();
        } catch (IOException e)
        {
            log.warn(getClass().getSimpleName() + " failed to cleanup cleanly");
        }
    }

    public static String getCrestBaseUri()
    {
        return crestUrl;
    }

    public static String getXmlBaseUri()
    {
        return xmlUrl;
    }

    public Future<EveData> getOptions(CrestRequestData requestData)
    {
        ClientElement client = null;
        synchronized (crestClients)
        {
            int idx = crestClientIndex.incrementAndGet();
            client = crestClients.get(idx);
            if (idx == CrestMaxClients - 1)
                crestClientIndex.set(-1);
        }
        return getOptions(requestData, client);
    }
    
    public Future<EveData> getCrest(CrestRequestData requestData)
    {
        ClientElement client = null;
        //TODO: this should check for all taken and block
        synchronized (crestClients)
        {
            int idx = crestClientIndex.incrementAndGet();
            client = crestClients.get(idx);
            if (idx == CrestMaxClients - 1)
                crestClientIndex.set(-1);
        }
        return get(requestData, client);
    }

    public Future<EveData> getXml(CrestRequestData requestData)
    {
        ClientElement client = null;
        //TODO: this should check for all taken and block
        synchronized (xmlClients)
        {
            int idx = xmlClientIndex.incrementAndGet();
            client = xmlClients.get(idx);
            if (idx == CrestMaxClients - 1)
                xmlClientIndex.set(-1);
        }
        return get(requestData, client);
    }

    public Future<EveData> getOptions(CrestRequestData requestData, ClientElement client)
    {
        String accessToken = null;
        if (requestData.clientInfo != null)
            accessToken = ((OAuth2AccessToken) requestData.clientInfo.getAccessToken()).getAccessToken();
        HttpOptions get = new HttpOptions(requestData.url);
        if (accessToken != null)
        {
            get.addHeader("Authorization", "Bearer " + accessToken);
            if (requestData.scope != null)
                get.addHeader("Scope", requestData.scope);
        }
        if (requestData.version != null)
            get.addHeader("Accept", requestData.version);
        return executor.submit(new CrestGetTask(client, get, requestData));
    }
    
    public Future<EveData> get(CrestRequestData requestData, ClientElement client)
    {
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
        return executor.submit(new CrestGetTask(client, get, requestData));
    }

    private class CrestGetTask implements Callable<EveData>
    {
        private final CrestRequestData rdata;
        private final HttpRequestBase get;
        private final ClientElement client;

        private CrestGetTask(ClientElement client, HttpRequestBase get, CrestRequestData rdata)
        {
            this.rdata = rdata;
            this.get = get;
            this.client = client;
        }

        @Override
        public EveData call() throws Exception
        {
            try
            {
                client.crestThrottle.waitAsNeeded();
                final AtomicInteger cacheTime = new AtomicInteger(0);
                ResponseHandler<String> responseHandler = new ResponseHandler<String>()
                {
                    @Override
                    public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException
                    {
                        log.debug("handling response for: " + rdata.url + " response: " + response.toString());
                        int status = response.getStatusLine().getStatusCode();
                        if (status < 200 || status > 299)
                        {
                            String msg = "Unexpected response status: " + status + " " + response.getStatusLine().getReasonPhrase();
                            msg += " : " + response.toString();
                            log.warn(msg);
                            throw new ClientProtocolException(msg);
                            //TODO: is it enough to just log it here, currently no one is calling get
                            // maybe just re-issue, what about connection down retries?  Need to look at status code ranges to determine which to retry on.
                        }
                        if (rdata.gson != null)
                        {
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
                                log.warn(msg);
                                throw new ClientProtocolException("Did not find " + CacheControlMaxAge + " in " + CacheControlHeader + " header");
                            }
                            headers = response.getHeaders(CrestDeprecatedHeader);
                            if (headers != null && headers.length > 0)
                            {
                                rdata.deprecated.set(true);
                                log.warn("Requested Endpoint version is deprecated: " + rdata.url + " " + rdata.version);
                            }
                        }
                        if (status == NoLongerSupported)
                        {
                            String msg = "Endpoint no longer supported: " + status + " " + response.getStatusLine().getReasonPhrase();
                            log.warn(msg);
                            throw new ClientProtocolException(msg);
                        }

                        if (status >= 200 && status < 300)
                        {
                            if(status != 200)
                                log.warn("received a non 200, but under 300 response: " + response.toString());
                            HttpEntity entity = response.getEntity();
                            String body = entity != null ? EntityUtils.toString(entity) : null;
                            if (rdata.gson == null)
                                try
                                {
                                    cacheTime.set(EveApi.getCachedUntil(body));
                                } catch (Exception e)
                                {
                                    log.warn(e.getMessage(), e);
                                }
                            return body;
                        }
                        return null;
                    }
                };
                String body = client.client.execute(get, responseHandler);
                EveData data = null;
                if (rdata.gson != null)
                    data = rdata.gson.fromJson(body, rdata.clazz);
                else
                    data = new EveApiSaxHandler().getData(body, rdata.baseEveData);
                data.init();
                if (rdata.continueRefresh.get())
                    synchronized (refreshQueue)
                    {
                        log.debug(rdata.url + " nextRefresh: " + cacheTime.get() + " seconds");
                        long time = System.currentTimeMillis() + cacheTime.get() * 1000;
                        //                        if(rdata.url.equals(Time.getCrestUrl()) || rdata.url.equals(ServerStatus.getXmlUrl()))
                        //                            time = 2000; //TODO: make this health check refresh configurable
                        rdata.setNextRefresh(time);
                        data.setNextRefresh(time);
                        refreshQueue.add(rdata);
                    }
                else
                    data.setNextRefresh(0);

                data.setCacheTimeInSeconds(cacheTime.get());
                data.refreshed();
                if (rdata.callback != null)
                    rdata.callback.received(rdata, data);
                controller.fireCommunicationEvent(rdata.clientInfo, rdata.gson == null ? CommsEventListener.Type.XmlUp : CommsEventListener.Type.CrestUp);
                return data;
            } catch (Exception e)
            {
                synchronized (controller.dataCache)
                {
                    controller.dataCache.remove(rdata.url);
                    controller.fireCommunicationEvent(rdata.clientInfo, CommsEventListener.Type.CrestDown);
                }
                log.warn("failed to finialize inbound data", e);
                throw new SourceFailureException("HttpRequest for url: " + rdata.url + " failed", e);
            } finally
            {
                //                client.client.close();
            }
        }
    }

    private class QueueTask implements Runnable
    {
        private final CrestClient client;

        private QueueTask()
        {
            client = controller.crestClient;
        }

        @Override
        public void run()
        {
            do
                synchronized (refreshQueue)
                {
                    long current = System.currentTimeMillis();
                    CrestRequestData rdata = refreshQueue.peek();
                    if (rdata == null)
                        break;
                    if (rdata.getNextRefresh() > current)
                        break;
                    rdata = refreshQueue.poll();
                    log.debug("refreshing " + rdata.toString());
                    if (rdata.gson != null)
                        client.getCrest(rdata);
                    else
                        client.getXml(rdata);
                }
            while (true);
        }
    }

    protected class ClientElement
    {
        protected final CloseableHttpClient client;
        protected final RequestThrottle crestThrottle;
        protected final RequestThrottle xmlThrottle;

        private ClientElement(CloseableHttpClient client, RequestThrottle crestThrottle, RequestThrottle xmlThrottle)
        {
            this.client = client;
            this.crestThrottle = crestThrottle;
            this.xmlThrottle = xmlThrottle;
        }
    }
}
