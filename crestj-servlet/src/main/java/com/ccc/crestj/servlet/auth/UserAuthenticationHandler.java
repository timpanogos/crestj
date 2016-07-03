/*
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
package com.ccc.crestj.servlet.auth;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth.OAuthService;

@SuppressWarnings("javadoc")
public class UserAuthenticationHandler
{
    private static final String ResponseType = "response_type";
    private static final String ResponseTypeDefault = "code";
    private static final String RedirectUri = "redirect_uri";
    private static final String ClientId = "client_id";
    private static final String Scope = "scope";
    private static final String State = "state";
    
    private static UserAuthenticationHandler instance;

    public static UserAuthenticationHandler getInstance()
    {
        if (instance == null)
            throw new IllegalStateException("UserAuthenticationHandler instance not set");
        return instance;
    }

    public static void createInstance(OAuthUserAuthenticator oauthUserAuthenticator)
    {
        if (instance != null)
            return;
        instance = new UserAuthenticationHandler(oauthUserAuthenticator);
    }

    private boolean isOauth10a = false;
    private OAuth10aService oauth10aService;
    private OAuth20Service oauth20Service;

    private UserAuthenticationHandler(OAuthUserAuthenticator oauthUserAuthenticator)
    {
        OAuthService oauthService = oauthUserAuthenticator.getOAuthService();
        if (oauthService instanceof OAuth10aService)
        {
            this.oauth10aService = (OAuth10aService) oauthService;
            this.isOauth10a = true;
        }else
        {
            this.oauth20Service = (OAuth20Service) oauthService;
            this.isOauth10a = false;
        }
    }

    public AuthenticateUserData authenticateUser() throws Exception
    {
        if (isOauth10a)
        {
            OAuth1RequestToken requestToken = oauth10aService.getRequestToken();
            //@formatter:off
		    return new AuthenticateUserData(
		            true,
                    requestToken,
                    oauth10aService.getAuthorizationUrl(requestToken),
                    oauth10aService.getConfig());
            //@formatter:on
        }
        
    /*            
Example URL: 
https://login.eveonline.com/oauth/authorize/?
    response_type=code&
    redirect_uri=https%3A%2F%2F3rdpartysite.com%2Fcallback&
    client_id=3rdpartyClientId&
    scope=characterContactsRead%20characterContactsWrite&
    state=uniquestate123
  */    
        OAuthConfig config = oauth20Service.getConfig(); 
        Map<String, String> eveParams = new HashMap<>();
        eveParams.put(ResponseType, ResponseTypeDefault);
        eveParams.put(RedirectUri, config.getCallback());
        eveParams.put(ClientId, config.getApiKey());
        eveParams.put(Scope, config.getScope());
        eveParams.put(State, config.getState());
        
        //@formatter:off
        return new AuthenticateUserData(
                false,
                null,
                oauth20Service.getAuthorizationUrl(eveParams),
                oauth20Service.getConfig());
        //@formatter:on
    }

    public OAuth1AccessToken getAccessToken(OAuth1RequestToken requestToken, String oauthVerifier)
    {
        if (isOauth10a)
            return oauth10aService.getAccessToken(requestToken, oauthVerifier);
        throw new IllegalArgumentException("This method only supports OAuth10aService, try the other getAccessToken method.");
    }

    public OAuth2AccessToken getAccessToken(String code)
    {
        if (isOauth10a)
            throw new IllegalArgumentException("This method only supports OAuth20Service, try the other getAccessToken method.");
        return oauth20Service.getAccessToken(code);
    }

    public void addAuthenticatedUser(HttpServletRequest request, HttpServletResponse response, String email, String displayName)
    {
        // TODO
    }

    public static class AuthenticateUserData
    {
        public final boolean oauth10a;
        public final OAuth1RequestToken requestToken;
        public final String loginUrl;
        public final OAuthConfig oauthConfig;

        public AuthenticateUserData(boolean oauth10a, OAuth1RequestToken requestToken, String loginUrl, OAuthConfig oauthConfig)
        {
            this.oauth10a = oauth10a;
            this.requestToken = requestToken;
            this.loginUrl = loginUrl;
            this.oauthConfig = oauthConfig;
        }
    }

}
