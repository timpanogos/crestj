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
package com.ccc.crest.servlet;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;

import com.ccc.crest.servlet.auth.CrestAuthenticator;
import com.ccc.crest.servlet.auth.CrestSignInPage;
import com.ccc.crest.servlet.auth.CrestWebSession;
import com.ccc.crest.servlet.auth.LoginPage;
import com.ccc.tools.servlet.OAuthServlet;

@SuppressWarnings("javadoc")
public abstract class CrestServlet extends OAuthServlet
{
    public static final String OauthLoginUrlKey = "ccc.crest.oauth.login-url";
    public static final String OauthTokenUrlKey = "ccc.crest.oauth.token-url";
    public static final String OauthVerifyUrlKey = "ccc.crest.oauth.verify-url";
    public static final String OauthCallbackUrlKey = "ccc.crest.oauth.callback-url";
    public static final String OauthClientIdKey = "ccc.crest.oauth.client-id";
    public static final String OauthClientSecretKey = "ccc.crest.oauth.client-secret";
    public static final String OauthScopeKey = "ccc.crest.scope";
    public static final String CrestUrlKey = "ccc.crest.url";
    
    public static final String CrestServletConfigDefault = "etc/opt/ccc/crest/crest.properties";
    public final static String OauthImplClassDefault = CrestAuthenticator.class.getName(); //
    public static final String OauthLoginUrlDefault = "https://login.eveonline.com/oauth/authorize";
    public static final String OauthTokenUrlDefault = "https://login.eveonline.com/oauth/token";
    public static final String OauthVerifyUrlDefault = "https://login.eveonline.com/oauth/verify";
    public static final String OauthScopeDefault = "publicData";
    public static final String CrestUrlDefault = "https://crest-tq.eveonline.com";
    
    public CrestServlet()
    {
    }
    
    @Override
    protected String getOauthImplClassDefault()
    {
        return OauthImplClassDefault;
    }

    @Override
    protected String getServletConfigDefault()
    {
        return CrestServletConfigDefault;
    }

    @Override
    protected void init(StringBuilder sb)
    {
        getApplicationSettings().setAccessDeniedPage(LoginPage.class);
    }

    @Override
    protected void onDestroy()
    {
    }
    
    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass()
    {
        return CrestWebSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass()
    {
        return CrestSignInPage.class;
    }
}
