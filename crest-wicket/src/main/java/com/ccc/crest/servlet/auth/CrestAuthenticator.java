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

import java.util.Properties;
import java.util.Random;

import com.ccc.crest.core.CrestController;
import com.ccc.oauth.ScribeApi20Impl;
import com.ccc.oauth.UserAuthenticationHandler;
import com.ccc.servlet.wicket.WicketUserAuthenticator;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuthService;


@SuppressWarnings("javadoc")
public class CrestAuthenticator implements WicketUserAuthenticator
{
    public static final String CallbackMount = "/oauth/eve";

    private volatile String loginUrl;
    private volatile String tokenUrl;
    private volatile String verifyUrl;
    private volatile String clientSecret;
    private volatile String clientId;
    private volatile String scope;
    private volatile String callbackUrl;

    public CrestAuthenticator()
    {
    }
    
    @Override
    public void init(Properties properties) throws Exception
    {
        loginUrl = properties.getProperty(CrestController.OauthLoginUrlKey);
        tokenUrl = properties.getProperty(CrestController.OauthTokenUrlKey);
        verifyUrl = properties.getProperty(CrestController.OauthVerifyUrlKey);
        clientId = properties.getProperty(CrestController.OauthClientIdKey);
        clientSecret = properties.getProperty(CrestController.OauthClientSecretKey);
        scope = CrestController.getCrestController().getAuthenticationScopesString();
        callbackUrl = properties.getProperty(CrestController.OauthCallbackUrlKey);
        if (loginUrl == null)
            loginUrl = CrestController.OauthLoginUrlDefault;
        if (tokenUrl == null)
            tokenUrl = CrestController.OauthTokenUrlDefault;
        if (verifyUrl == null)
            verifyUrl = CrestController.OauthVerifyUrlDefault;
        if (clientId == null)
            throw new IllegalArgumentException("Missing property " + CrestController.OauthClientIdKey);
        if (clientSecret == null)
            throw new IllegalArgumentException("Missing property " + CrestController.OauthClientSecretKey);
        if (callbackUrl == null)
            throw new IllegalArgumentException("Missing property " + CrestController.OauthCallbackUrlKey);
        if(callbackUrl.endsWith("/"))
            callbackUrl = callbackUrl.substring(0, callbackUrl.length() - 2);
        callbackUrl += CallbackMount;

        UserAuthenticationHandler.createInstance(this);
    }

    @Override
    public OAuthService getOAuthService()
    {
        //@formatter:off
        return new ServiceBuilder()
                .apiKey(clientId)
                .apiSecret(clientSecret)
                .scope(scope)
                .state("secret"+ new Random().nextInt(999_99))
                .callback(callbackUrl)
                .grantType("authorization_code")
                .build(new ScribeApi20Impl(loginUrl, tokenUrl));
        //@formatter:on
    }

    @Override
    public String getOAuthCallbackMount()
    {
        return CallbackMount;
    }
    
    @Override
    public Class<CrestAuthCallback> getOAuthCallbackClass()
    {
        return CrestAuthCallback.class;
    }
}
