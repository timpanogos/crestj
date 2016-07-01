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

import java.util.Properties;

import org.apache.wicket.markup.html.WebPage;
import org.opendof.tools.repository.interfaces.servlet.auth.jira.JiraAuthenticator;

import com.github.scribejava.core.oauth.OAuthService;

/**
 * OAuth User Authenticator Interface 
 */
public interface OAuthUserAuthenticator {
	
    /** oauth implementation class */
    public final static String ImplClassKey = "opendof.tools.interface.repository.auth.impl-class";
    /** default oauth implementation class */
    public final static String ImplClassDefault = JiraAuthenticator.class.getName();
    
    /** callback host key */
    public static final String CallbackHostKey = "opendof.tools.interface.repository.auth.callback-host";
    
	/**
	 * Initialize the OAuthUserAuthenticator with the specified properties.
	 * @param properties The properties to be passed to the OAuthUserAuthenticator.
	 */
	public void init(Properties properties);
	
	/**
	 * Get an OAuthService object. This may be a OAuth10aService or OAuth20Service.
	 * @return The OAuthService object.
	 */
	public OAuthService getOAuthService();
	
    /**
     * Get the URL that the OAuthService calls back to.
     * @return The callback URL.
     */
    public String getOAuthCallbackMount();
    
    /**
     * Get the Callback class.
     * @return The callback class.
     */
    public Class<? extends WebPage> getOAuthCallbackClass();  
    
    /**
     * Get the Custom ClientInformation object.
     * @return The callback class.
     */
    public ClientInformation getClientInformation();
}
