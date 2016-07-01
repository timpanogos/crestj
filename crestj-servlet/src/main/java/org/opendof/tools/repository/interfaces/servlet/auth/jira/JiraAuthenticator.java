/*
**  Copyright (c) 2010-2015, Panasonic Corporation.
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
package org.opendof.tools.repository.interfaces.servlet.auth.jira;

import java.util.Properties;

import org.apache.wicket.request.cycle.RequestCycle;
import org.opendof.tools.repository.interfaces.servlet.auth.ClientInformation;
import org.opendof.tools.repository.interfaces.servlet.auth.OAuthUserAuthenticator;
import org.opendof.tools.repository.interfaces.servlet.auth.RsaPrivateKeyFactory;
import org.opendof.tools.repository.interfaces.servlet.auth.UserAuthenticationHandler;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuthService;

@SuppressWarnings("javadoc")
public class JiraAuthenticator implements OAuthUserAuthenticator
{
    public static final String CallbackMount = "/oauth/jira";
    public static final String JiraApplicationUrlKey = "opendof.tools.interface.repository.auth.jira.application-url";
    public static final String JiraPrivateKeyFileKey = "opendof.tools.interface.repository.auth.jira.rsa-key-file";
    public static final String JiraConsumerKeyKey = "opendof.tools.interface.repository.auth.jira.consumer-key";

    private volatile String applicationUrl;
    private volatile String privateKey;
    private volatile String consumerKey;
    private volatile String callbackUrl;

    public JiraAuthenticator()
    {
    }
    
    @Override
    public void init(Properties properties)
    {
        String keyFile = properties.getProperty(JiraPrivateKeyFileKey);
        if (keyFile == null)
            throw new IllegalArgumentException("Missing property " + JiraPrivateKeyFileKey);
        try
        {
            privateKey = RsaPrivateKeyFactory.getCommentStripedString(keyFile);
        } catch (Exception e)
        {
            throw new RuntimeException("RSA key parsing failed", e);
        }

        callbackUrl = properties.getProperty(CallbackHostKey);
        if (callbackUrl == null)
            throw new IllegalArgumentException("Missing property " + CallbackHostKey);
        if(callbackUrl.endsWith("/"))
            callbackUrl = callbackUrl.substring(0, callbackUrl.length() - 2);
        callbackUrl += CallbackMount;

        applicationUrl = properties.getProperty(JiraApplicationUrlKey);
        if (applicationUrl == null)
            throw new IllegalArgumentException("Missing property " + JiraApplicationUrlKey);
        consumerKey = properties.getProperty(JiraConsumerKeyKey);
        if (consumerKey == null)
            throw new IllegalArgumentException("Missing property " + JiraConsumerKeyKey);
        
        UserAuthenticationHandler.createInstance(this);
    }

    @Override
    public OAuthService getOAuthService()
    {
        //@formatter:off
        return new ServiceBuilder()
                        .apiSecret(privateKey)
                        .apiKey(consumerKey)
                        .callback(callbackUrl)
                        .build(new JiraAPI(applicationUrl, privateKey));
        //@formatter:on
    }

    @Override
    public String getOAuthCallbackMount()
    {
        return CallbackMount;
    }
    
    @Override
    public Class<JiraAuthCallback> getOAuthCallbackClass()
    {
        return JiraAuthCallback.class;
    }

    @Override
    public ClientInformation getClientInformation()
    {
        JiraClientInfo info = new JiraClientInfo(RequestCycle.get());
        info.setApplicationUrl(applicationUrl);
        info.setConsumerKey(consumerKey);
        info.setPrivateHash(privateKey);
        return info;
    }
}
