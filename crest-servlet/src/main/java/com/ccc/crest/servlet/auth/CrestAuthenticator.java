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

import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;

import com.ccc.crest.servlet.CrestServlet;
import com.ccc.tools.PropertiesHelper;
import com.ccc.tools.servlet.OauthUserAuthenticator;
import com.ccc.tools.servlet.ScribeApi20Impl;
import com.ccc.tools.servlet.UserAuthenticationHandler;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuthService;

@SuppressWarnings("javadoc")
public class CrestAuthenticator implements OauthUserAuthenticator
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
    public void init(Properties properties)
    {
        loginUrl = properties.getProperty(CrestServlet.OauthLoginUrlKey);
        tokenUrl = properties.getProperty(CrestServlet.OauthTokenUrlKey);
        verifyUrl = properties.getProperty(CrestServlet.OauthVerifyUrlKey);
        clientId = properties.getProperty(CrestServlet.OauthClientIdKey);
        clientSecret = properties.getProperty(CrestServlet.OauthClientSecretKey);
        List<Entry<String, String>> scopes = PropertiesHelper.getPropertiesForBaseKey(CrestServlet.OauthScopeKey, properties);
        callbackUrl = properties.getProperty(CrestServlet.OauthCallbackUrlKey);
        if (loginUrl == null)
            loginUrl = CrestServlet.OauthLoginUrlDefault;
        if (tokenUrl == null)
            tokenUrl = CrestServlet.OauthTokenUrlDefault;
        if (verifyUrl == null)
            verifyUrl = CrestServlet.OauthVerifyUrlDefault;
        if (clientId == null)
            throw new IllegalArgumentException("Missing property " + CrestServlet.OauthClientIdKey);
        if (clientSecret == null)
            throw new IllegalArgumentException("Missing property " + CrestServlet.OauthClientSecretKey);
        if (scopes.size() == 0)
            scope = CrestServlet.OauthScopeDefault;
        else
        {
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for(Entry<String, String> entry : scopes)
            {
                if(!first)
                    sb.append(" ");
                first = false;
                sb.append(entry.getValue());
            }
            scope = sb.toString();
        }
        if (callbackUrl == null)
            throw new IllegalArgumentException("Missing property " + CrestServlet.OauthCallbackUrlKey);
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

//    @Override
//    public SessionClientInfo getClientInformation()
//    {
//        return clientInfo;
//    }
}
