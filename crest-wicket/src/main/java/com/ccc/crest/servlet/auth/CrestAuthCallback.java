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

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.client.json.OauthVerify;
import com.ccc.oauth.CoreController;
import com.ccc.oauth.clientInfo.Base20ClientInfo;
import com.ccc.oauth.clientInfo.SessionClientInfo;
import com.ccc.oauth.events.AuthEventListener;
import com.ccc.servlet.wicket.login.AuthCallback;
import com.ccc.servlet.wicket.login.LoginPage;
import com.github.scribejava.core.model.OAuth2AccessToken;

@SuppressWarnings("javadoc")
public class CrestAuthCallback extends AuthCallback
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
            CoreController.getController().fireAuthenticatedEvent(clientInfo, AuthEventListener.Type.Authenticated);
        } catch (Exception e)
        {
            LoggerFactory.getLogger(getClass()).info("OAuth authentication phase 2 failed", e);
            throw new RestartResponseAtInterceptPageException(SignOutPage.class);
        }
        throw new RestartResponseAtInterceptPageException(LoginPage.class);
    }
    
    private void getVerifyData(Base20ClientInfo clientInfo) throws Exception
    {
        Properties properties = CoreController.getController().getProperties();
        String verifyUrl = properties.getProperty(CrestController.OauthVerifyUrlKey, CrestController.OauthVerifyUrlDefault);
        String userAgent = properties.getProperty(CrestController.UserAgentKey, CrestController.UserAgentDefault);
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
