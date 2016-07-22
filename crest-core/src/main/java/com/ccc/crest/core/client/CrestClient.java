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
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
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
import com.github.scribejava.core.model.OAuth2AccessToken;

@SuppressWarnings("javadoc")
public class CrestClient
{
    private static final String CacheControlHeader = "Cache-Control";
    private static final String CacheControlMaxAge = "max-age";
    private static final int CrestGeneralMaxRequestsPerSecond = 150;
    private static final int XmlGeneralMaxRequestsPerSecond = 30;
    private static final int CrestMaxClients = 20;

    private static String CrestUrl;
    private final String xmlUrl;
    private final String userAgent;
    private final CrestController controller;
    private final PriorityQueue<CrestRequestData> refreshQueue;
    private volatile ScheduledFuture<?> queueCheckFuture;
    private final List<ClientElement> clients;
    private final AtomicInteger clientIndex;

    private final ExecutorService executor;

    public CrestClient(CrestController controller, String crestUrl, String xmlUrl, String userAgent, ExecutorService executor)
    {
        this.controller = controller;
        CrestUrl = StrH.stripTrailingSeparator(crestUrl);
        this.xmlUrl = StrH.stripTrailingSeparator(xmlUrl);
        this.userAgent = userAgent;
        refreshQueue = new PriorityQueue<>();
        this.executor = executor;
        clients = new ArrayList<>();
        clientIndex = new AtomicInteger(-1);
        for (int i = 0; i < CrestMaxClients; i++)
        {
            RequestThrottle crestGeneralThrottle = new RequestThrottle(CrestGeneralMaxRequestsPerSecond);
            RequestThrottle xmlGeneralThrottle = new RequestThrottle(XmlGeneralMaxRequestsPerSecond);
            CloseableHttpClient client = HttpClients.custom().setUserAgent(userAgent).build();
            ClientElement clientElement = new ClientElement(client, crestGeneralThrottle, xmlGeneralThrottle);
            clients.add(clientElement);
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
            if (clients != null)
            {
                for (int i = CrestMaxClients - 1; i >= 0; i--)
                {
                    clients.get(i).client.close();
                }
            }
        } catch (IOException e)
        {
            LoggerFactory.getLogger(getClass()).warn(getClass().getSimpleName() + " failedto cleanup cleanly");
        }
    }

    public static String getCrestBaseUri()
    {
        return CrestUrl;
    }

    public Future<EveData> getCrest(CrestRequestData requestData)
    {
        ClientElement client = null;
        synchronized (clients)
        {
            int idx = clientIndex.incrementAndGet();
            client = clients.get(idx);
            if (idx == CrestMaxClients - 1)
                clientIndex.set(-1);
        }
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
        LoggerFactory.getLogger(getClass()).info("pre-executor.submit accessToken: " + accessToken);
        return executor.submit(new CrestGetTask(client, get, requestData));
    }

    public String getXml(CrestClientInfo clientInfo) throws Exception
    {
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
        private final ClientElement client;

        private CrestGetTask(ClientElement client, HttpGet get, CrestRequestData rdata)
        {
            this.rdata = rdata;
            this.get = get;
            this.client = client;
        }

        @Override
        public EveData call() throws Exception
        {
            LoggerFactory.getLogger(getClass()).info("executing, pre-throttle: " + rdata.url);
            try
            {
                client.crestThrottle.waitAsNeeded();
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
                String json = client.client.execute(get, responseHandler);
                EveData data = rdata.gson.fromJson(json, rdata.clazz);
                data.init();
                if (rdata.continueRefresh.get())
                    synchronized (refreshQueue)
                    {
                        long time = System.currentTimeMillis() + cacheTime.get() * 1000;
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
                controller.fireCommunicationEvent(rdata.clientInfo, CommsEventListener.Type.CrestUp);
                return data;
            } catch (Exception e)
            {
                synchronized (controller.dataCache)
                {
                    controller.dataCache.remove(rdata.url);
                    controller.fireCommunicationEvent(rdata.clientInfo, CommsEventListener.Type.CrestDown);
                }
                LoggerFactory.getLogger(getClass()).warn("failed to finialize inbound data", e);
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
            long current = System.currentTimeMillis();
            do
            {
                CrestRequestData rdata = refreshQueue.peek();
                if (rdata == null)
                    break;
                if (rdata.getNextRefresh() > current)
                    break;
                rdata = refreshQueue.poll();
                LoggerFactory.getLogger(getClass()).debug("requeue " + rdata.toString());
                client.getCrest(rdata);
            } while (true);
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
