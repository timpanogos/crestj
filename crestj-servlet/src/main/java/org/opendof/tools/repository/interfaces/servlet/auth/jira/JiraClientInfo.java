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

import org.apache.wicket.request.cycle.RequestCycle;
import org.opendof.tools.repository.interfaces.servlet.auth.ClientInformation;

import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.Token;

@SuppressWarnings("javadoc")
public class JiraClientInfo extends ClientInformation 
{
    private static final long serialVersionUID = 1L;
    
    private OAuth1RequestToken requestToken;
    private Token accessToken;
    private String loginUrl;
    private OAuthConfig oauthConfig;
    private String oauthToken;
    private String oauthVerifier;
    private String applicationUrl;
    private String consumerKey;
    private String privateHash;
    
    public JiraClientInfo(RequestCycle requestCycle)
    {
        super(requestCycle);
    }

    public synchronized OAuth1RequestToken getRequestToken()
    {
        return requestToken;
    }

    public synchronized void setRequestToken(OAuth1RequestToken requestToken)
    {
        this.requestToken = requestToken;
    }

    public synchronized String getLoginUrl()
    {
        return loginUrl;
    }

    public synchronized void setLoginUrl(String loginUrl)
    {
        this.loginUrl = loginUrl;
    }

    public synchronized String getOauthVerifier()
    {
        return oauthVerifier;
    }

    public synchronized void setOauthVerifier(String oauthVerifier)
    {
        this.oauthVerifier = oauthVerifier;
    }

    public synchronized OAuthConfig getOauthConfig()
    {
        return oauthConfig;
    }

    public synchronized void setOauthConfig(OAuthConfig oauthConfig)
    {
        this.oauthConfig = oauthConfig;
    }

    
    public synchronized String getOauthToken()
    {
        return oauthToken;
    }

    public synchronized void setOauthToken(String oauthToken)
    {
        this.oauthToken = oauthToken;
    }

    public synchronized Token getAccessToken()
    {
        return accessToken;
    }

    public synchronized void setAccessToken(Token accessToken)
    {
        this.accessToken = accessToken;
    }

    public synchronized String getApplicationUrl()
    {
        return applicationUrl;
    }

    public synchronized void setApplicationUrl(String applicationUrl)
    {
        this.applicationUrl = applicationUrl;
    }
    
    public synchronized String getConsumerKey()
    {
        return consumerKey;
    }

    public synchronized void setConsumerKey(String consumerKey)
    {
        this.consumerKey = consumerKey;
    }

    public synchronized String getPrivateHash()
    {
        return privateHash;
    }

    public synchronized void setPrivateHash(String privateHash)
    {
        this.privateHash = privateHash;
    }
}