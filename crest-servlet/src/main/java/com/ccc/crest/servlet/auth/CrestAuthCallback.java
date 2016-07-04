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
package com.ccc.crest.servlet.auth;

import java.io.InputStream;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authroles.authentication.pages.SignOutPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.LoggerFactory;

import com.ccc.crest.json.OauthVerifyData;
import com.ccc.crest.servlet.CrestServlet;
import com.ccc.tools.servlet.OAuthServlet;
import com.ccc.tools.servlet.UserAuthenticationHandler;
import com.github.scribejava.core.model.OAuth2AccessToken;

@SuppressWarnings("javadoc")
public class CrestAuthCallback extends WebPage
{
    private static final long serialVersionUID = 1L;

    // TODO: centralize with a threadpool or somesuch ... no cleanup here right
    // now
    // what about multiple users? how to scale the refresh?
    // also need to cancel it in logout
    private Timer timer = new Timer();

    public CrestAuthCallback(final PageParameters parameters)
    {
        CrestClientInfo clientInfo = (CrestClientInfo) WebSession.get().getClientInfo();
        try
        {
            UserAuthenticationHandler handler = UserAuthenticationHandler.getInstance();
            clientInfo.setCode(parameters.get("code").toString());
            clientInfo.setState(parameters.get("state").toString());
            clientInfo.validateState();
            clientInfo.setAccessToken(handler.getAccessToken(clientInfo.getCode()));
            clientInfo.setAuthenticated(true);
            long expiresIn = ((OAuth2AccessToken) clientInfo.getAccessToken()).getExpiresIn();
            expiresIn *= 1000;
            expiresIn -= 30 * 1000; // give it 30 seconds pre-expire
            timer.scheduleAtFixedRate(new RefreshTask(handler, clientInfo), expiresIn, expiresIn);
            getVerifyData(clientInfo);
        } catch (Exception e)
        {
            LoggerFactory.getLogger(getClass()).info("OAuth authentication phase 2 failed", e);
            throw new RestartResponseAtInterceptPageException(SignOutPage.class);
        }
        throw new RestartResponseAtInterceptPageException(LoginPage.class);
    }

    private void getVerifyData(CrestClientInfo clientInfo) throws Exception
    {
        Properties properties = ((OAuthServlet)getApplication()).getFileProperties();
        String verifyUrl = properties.getProperty(CrestServlet.OauthVerifyUrlKey, CrestServlet.OauthVerifyUrlDefault);
        String userAgent = properties.getProperty(CrestServlet.UserAgentKey, CrestServlet.UserAgentDefault);
//        CloseableHttpClient httpclient = HttpClients.createDefault();
        //@formatter:off
        CloseableHttpClient httpclient = 
                        HttpClients.custom()
                            .setUserAgent(userAgent)
                            .build();
        //@formatter:on
        
        String accessToken = ((OAuth2AccessToken)clientInfo.getAccessToken()).getAccessToken();
        HttpGet httpGet = new HttpGet(verifyUrl);
        httpGet.addHeader("Authorization", "Bearer " + accessToken);
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        try
        {
            HttpEntity entity1 = response1.getEntity();
            InputStream is = entity1.getContent();
            String json = IOUtils.toString(is, "UTF-8");
            is.close();
            clientInfo.setVerifyData(OauthVerifyData.getOauthVerifyData(json));
            EntityUtils.consume(entity1);
        } finally
        {
            response1.close();
        }
    }

    private class RefreshTask extends TimerTask
    {
        private final UserAuthenticationHandler handler;
        private final CrestClientInfo clientInfo;

        private RefreshTask(UserAuthenticationHandler handler, CrestClientInfo clientInfo)
        {
            this.handler = handler;
            this.clientInfo = clientInfo;
        }

        @Override
        public void run()
        {
            try
            {
                clientInfo.setAccessToken(handler.refreshAccessToken(((OAuth2AccessToken) clientInfo.getAccessToken()).getRefreshToken()));
            } catch (Exception e)
            {
                timer.cancel();
                LoggerFactory.getLogger(getClass()).error("Refresh Access Token failed", e);
            }
        }
    }
}
