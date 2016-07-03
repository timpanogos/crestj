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

import org.apache.wicket.request.cycle.RequestCycle;

import com.ccc.crestj.servlet.auth.ClientInformation;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.Token;

@SuppressWarnings("javadoc")
public class EveClientInfo extends ClientInformation 
{
    private static final long serialVersionUID = 1L;
    
    private String code;
    private String state;
    private Token accessToken;
    private String loginBaseUrl;    // from config
    private String loginUrl;        // totally filled out from scribejava
    private String tokenUrl;
    private OAuthConfig oauthConfig;
    
    public EveClientInfo(RequestCycle requestCycle)
    {
        super(requestCycle);
    }

    public void validateState() throws Exception
    {
        if (oauthConfig.getState().equals(state))
            return;
        throw new Exception("unexpected oauth state returned");
    }
    
    public synchronized String getLoginUrl()
    {
        return loginUrl;
    }

    public synchronized void setLoginUrl(String loginUrl)
    {
        this.loginUrl = loginUrl;
    }

//    public synchronized String getOauthVerifier()
//    {
//        return oauthVerifier;
//    }
//
//    public synchronized void setOauthVerifier(String oauthVerifier)
//    {
//        this.oauthVerifier = oauthVerifier;
//    }

    public synchronized OAuthConfig getOauthConfig()
    {
        return oauthConfig;
    }

    public synchronized void setOauthConfig(OAuthConfig oauthConfig)
    {
        this.oauthConfig = oauthConfig;
    }

    
//    public synchronized String getOauthToken()
//    {
//        return oauthToken;
//    }
//
//    public synchronized void setOauthToken(String oauthToken)
//    {
//        this.oauthToken = oauthToken;
//    }

    public synchronized Token getAccessToken()
    {
        return accessToken;
    }

    public synchronized void setAccessToken(Token accessToken)
    {
        this.accessToken = accessToken;
    }

    public synchronized String getLoginBaseUrl()
    {
        return loginBaseUrl;
    }

    public synchronized void setLoginBaseUrl(String loginBaseUrl)
    {
        this.loginBaseUrl = loginBaseUrl;
    }
    
    public synchronized String getTokenUrl()
    {
        return tokenUrl;
    }

    public synchronized void setTokenUrl(String tokenUrl)
    {
        this.tokenUrl = tokenUrl;
    }
    
//    public synchronized String getConsumerKey()
//    {
//        return consumerKey;
//    }
//
//    public synchronized void setConsumerKey(String consumerKey)
//    {
//        this.consumerKey = consumerKey;
//    }
//
//    public synchronized String getPrivateHash()
//    {
//        return privateHash;
//    }
//
//    public synchronized void setPrivateHash(String privateHash)
//    {
//        this.privateHash = privateHash;
//    }

    public synchronized String getCode()
    {
        return code;
    }

    public synchronized void setCode(String code)
    {
        this.code = code;
    }

    public synchronized String getState()
    {
        return state;
    }

    public synchronized void setState(String state)
    {
        this.state = state;
    }
}