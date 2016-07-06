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
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.ccc.crest.client.json.CrestClientData;
import com.ccc.crest.servlet.auth.CrestClientInfo;
import com.ccc.tools.RequestThrottle;
import com.ccc.tools.StrH;
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

    private CrestClient(String crestUrl, String xmlUrl, String userAgent)
    {
        if (this.crestUrl == null)
        {
            this.crestUrl = StrH.stripTrailingSeparator(crestUrl);
            this.xmlUrl = StrH.stripTrailingSeparator(xmlUrl);
            this.userAgent = userAgent;
            crestGeneralThrottle = new RequestThrottle(CrestGeneralMaxRequestsPerSecond);
            xmlGeneralThrottle = new RequestThrottle(XmlGeneralMaxRequestsPerSecond);
            crestThrottleMap = new HashMap<String, RequestThrottle>();
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
        return new CrestClient(crestUrl, xmlUrl, userAgent);
    }

    public static CrestClient getClient(String crestUrl, String xmlUrl, String userAgent)
    {
        return new CrestClient(crestUrl, xmlUrl, userAgent);
    }

    //@formatter:off
    public void getCrest(
                    CrestClientInfo clientInfo, String url, 
                    Gson gson, Class<? extends CrestClientData> clazz, 
                    CrestClientCallback callback,
                    int throttle, String scope, String version) throws Exception
    //@formatter:on
    {
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom().setUserAgent(userAgent).build();
        httpclient.start();
        String accessToken = ((OAuth2AccessToken) clientInfo.getAccessToken()).getAccessToken();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Authorization", "Bearer " + accessToken);
        if (scope != null)
            httpGet.addHeader("Scope", scope);
        if (version != null)
            httpGet.addHeader("Accept", version);
        RequestThrottle apiThrottle = crestThrottleMap.get(url);
        if (apiThrottle != null)
            apiThrottle.waitAsNeeded();
        crestGeneralThrottle.waitAsNeeded();

        //@formatter:off
        Future<Boolean> future = httpclient.execute(
                        HttpAsyncMethods.createGet("http://localhost:8080/"), 
                        new AsynchResponseConsumer(), null);
        //@formatter:on

        try
        {
            future.get();
//            HttpResponse response = future.get();
//            HttpEntity entity = response.getEntity();
//            InputStream is = entity.getContent();
//            String json = IOUtils.toString(is, "UTF-8");
//            is.close();
//            EntityUtils.consume(entity);
            if (apiThrottle == null)
                crestThrottleMap.put(url, new RequestThrottle(throttle));
//            CrestClientData data = gson.fromJson(json, clazz);
        } finally
        {
            httpclient.close();
        }
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

    static class AsynchResponseConsumer extends AsyncCharConsumer<Boolean>
    {
        @Override
        protected void onResponseReceived(final HttpResponse response)
        {
            System.out.println("onResponseRecieved");
        }

        @Override
        protected void onCharReceived(final CharBuffer buf, final IOControl ioctrl) throws IOException
        {
            System.out.println("onCharRecieved");
            while (buf.hasRemaining())
            {
                System.out.print(buf.get());
            }
        }

        @Override
        protected void releaseResources()
        {
            System.out.println("releaseResources");
        }

        @Override
        protected Boolean buildResult(final HttpContext context)
        {
            System.out.println("buioldResult");
            return Boolean.TRUE;
        }
    }
}
