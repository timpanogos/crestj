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

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authroles.authentication.pages.SignOutPage;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.LoggerFactory;

import com.ccc.crest.cache.character.ContactList;
import com.ccc.crest.client.json.OauthVerify;
import com.ccc.crest.servlet.CrestServlet;
import com.ccc.tools.servlet.OauthServlet;
import com.ccc.tools.servlet.clientInfo.Base20ClientInfo;
import com.ccc.tools.servlet.clientInfo.SessionClientInfo;
import com.ccc.tools.servlet.login.Auth20Callback;
import com.ccc.tools.servlet.login.LoginPage;
import com.github.scribejava.core.model.OAuth2AccessToken;

@SuppressWarnings("javadoc")
public class CrestAuthCallback extends Auth20Callback
{
    private static final long serialVersionUID = 4563195900276611276L;
    
    public CrestAuthCallback(PageParameters parameters) throws Exception
    {
        super(parameters);
        SessionClientInfo sessionClientInfo = (SessionClientInfo) WebSession.get().getClientInfo();
        handleCallback(parameters, sessionClientInfo);
    }

    @Override
    protected void handleCallback(PageParameters parameters, SessionClientInfo sessionClientInfo) throws Exception
    {
        super.handleCallback(parameters, sessionClientInfo);
        try
        {
            CrestClientInfo clientInfo = (CrestClientInfo) sessionClientInfo.getOauthClientInfo();
            getVerifyData(clientInfo);
ContactList c = ((CrestServlet)getApplication()).getDataCache().getContactList((CrestClientInfo)sessionClientInfo.getOauthClientInfo());            
System.out.println("look here");
        } catch (Exception e)
        {
            LoggerFactory.getLogger(getClass()).info("OAuth authentication phase 2 failed", e);
            throw new RestartResponseAtInterceptPageException(SignOutPage.class);
        }
        throw new RestartResponseAtInterceptPageException(LoginPage.class);
    }
    
    private void getVerifyData(Base20ClientInfo clientInfo) throws Exception
    {
        Properties properties = ((OauthServlet)getApplication()).getFileProperties();
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
            ((CrestClientInfo)clientInfo).setVerifyData(OauthVerify.getOauthVerifyData(json));
            EntityUtils.consume(entity1);
        } finally
        {
            response1.close();
        }
    }
}
