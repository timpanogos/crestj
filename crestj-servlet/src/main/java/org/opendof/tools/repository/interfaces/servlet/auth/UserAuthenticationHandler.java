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
package org.opendof.tools.repository.interfaces.servlet.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuthService;

@SuppressWarnings("javadoc")
public class UserAuthenticationHandler {
	
	private static UserAuthenticationHandler instance;
	
	public static UserAuthenticationHandler getInstance(){
		if(instance == null) throw new IllegalStateException("UserAuthenticationHandler instance not set");
		return instance;
	}
	
	public static void createInstance(OAuthUserAuthenticator oauthUserAuthenticator){
	    if(instance != null)
	        return;
		instance = new UserAuthenticationHandler(oauthUserAuthenticator);
	}
	
	private boolean isOauth10a = false;
	private OAuth10aService oauth10aService;
	//TODO implement OAuth20Service handling
	
	private UserAuthenticationHandler(OAuthUserAuthenticator oauthUserAuthenticator){
		OAuthService oauthService = oauthUserAuthenticator.getOAuthService();
		if(oauthService instanceof OAuth10aService){
			this.oauth10aService = (OAuth10aService)oauthService;
			this.isOauth10a = true;
		}
		else{
			throw new IllegalArgumentException("Only OAuth10aService supported.");
		}		
	}
	
	public AuthenticateUserData authenticateUser() throws Exception{
		if(isOauth10a){
		    OAuth1RequestToken requestToken = oauth10aService.getRequestToken();
		    //@formatter:off
		    return new AuthenticateUserData(
		                    requestToken,
		                    oauth10aService.getAuthorizationUrl(requestToken),
		                    oauth10aService.getConfig());
            //@formatter:on
		}
        throw new IllegalArgumentException("Only OAuth10aService supported.");
	}
	
	
	public OAuth1AccessToken getAccessToken(OAuth1RequestToken requestToken,  String oauthVerifier){
		if(isOauth10a)
		{
			return oauth10aService.getAccessToken(requestToken, oauthVerifier);
		}
		throw new IllegalArgumentException("Only OAuth10aService supported.");
	}
	
	public void addAuthenticatedUser(HttpServletRequest request, HttpServletResponse response, String email, String displayName){
		//TODO
	}
	
	public static class AuthenticateUserData
	{
	    public final OAuth1RequestToken requestToken;
	    public final String loginUrl;
	    public final OAuthConfig oauthConfig;
	    
	    public AuthenticateUserData(OAuth1RequestToken requestToken, String loginUrl, OAuthConfig oauthConfig)
	    {
	        this.requestToken = requestToken;
	        this.loginUrl = loginUrl;
	        this.oauthConfig = oauthConfig;
	    }
	}

}
