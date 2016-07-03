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
package com.ccc.crestj.servlet.auth.eve;

import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;

import org.apache.wicket.request.cycle.RequestCycle;

import com.ccc.crestj.servlet.NeedServlet;
import com.ccc.crestj.servlet.auth.ClientInformation;
import com.ccc.crestj.servlet.auth.OAuthUserAuthenticator;
import com.ccc.crestj.servlet.auth.UserAuthenticationHandler;
import com.ccc.tools.PropertiesHelper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuthService;

@SuppressWarnings("javadoc")
public class EveAuthenticator implements OAuthUserAuthenticator
{
    public static final String CallbackMount = "/oauth/eve";

    private volatile String loginUrl;
    private volatile String tokenUrl;
    private volatile String clientSecret;
    private volatile String clientId;
    private volatile String scope;
    private volatile String callbackUrl;

    public EveAuthenticator()
    {
    }
    
    @Override
    public void init(Properties properties)
    {
        loginUrl = properties.getProperty(NeedServlet.OauthLoginUrlKey);
        tokenUrl = properties.getProperty(NeedServlet.OauthTokenUrlKey);
        clientId = properties.getProperty(NeedServlet.OauthClientIdKey);
        clientSecret = properties.getProperty(NeedServlet.OauthClientSecretKey);
        List<Entry<String, String>> scopes = PropertiesHelper.getPropertiesForBaseKey(NeedServlet.OauthScopeKey, properties);
        callbackUrl = properties.getProperty(NeedServlet.OauthCallbackUrlKey);
        if (loginUrl == null)
            loginUrl = NeedServlet.OauthLoginUrlDefault;
        if (tokenUrl == null)
            tokenUrl = NeedServlet.OauthTokenUrlDefault;
        if (clientId == null)
            throw new IllegalArgumentException("Missing property " + NeedServlet.OauthClientIdKey);
        if (clientSecret == null)
            throw new IllegalArgumentException("Missing property " + NeedServlet.OauthClientSecretKey);
        if (scopes.size() == 0)
            scope = NeedServlet.OauthScopeDefault;
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
            throw new IllegalArgumentException("Missing property " + NeedServlet.OauthCallbackUrlKey);
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
                .build(new EveAPI(loginUrl, tokenUrl));
        //@formatter:on
    }

    @Override
    public String getOAuthCallbackMount()
    {
        return CallbackMount;
    }
    
    @Override
    public Class<EveAuthCallback> getOAuthCallbackClass()
    {
        return EveAuthCallback.class;
    }

    @Override
    public ClientInformation getClientInformation()
    {
        EveClientInfo info = new EveClientInfo(RequestCycle.get());
        info.setLoginUrl(loginUrl);
        info.setTokenUrl(tokenUrl);
//        info.setConsumerKey(clientId);
//        info.setPrivateHash(clientSecret);
        return info;
    }
}
