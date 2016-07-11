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

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.pages.SignOutPage;
import org.apache.wicket.markup.html.WebPage;

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.cache.DataCache;
import com.ccc.crest.core.client.CrestClient;
import com.ccc.crest.core.events.ApiKeyEventListener;
import com.ccc.crest.servlet.auth.CrestAuthenticator;
import com.ccc.oauth.clientInfo.BaseClientInfo;
import com.ccc.servlet.wicket.WicketBaseServlet;
import com.ccc.servlet.wicket.WicketWebSession;
import com.ccc.servlet.wicket.login.LoginPage;
import com.ccc.tools.TabToLevel;

@SuppressWarnings("javadoc")
public abstract class CrestServlet extends WicketBaseServlet implements ApiKeyEventListener
{
    public final static String OauthImplClassDefault = CrestAuthenticator.class.getName(); //
    
    public CrestServlet()
    {
    }
    
    public DataCache getDataCache()
    {
        return CrestController.getCrestController().dataCache;
    }
    
    @Override
    public void needsApiKey(CrestClientInfo clientInfo)
    {
        log.info("look here");
        throw new RestartResponseAtInterceptPageException(SignOutPage.class);
        
    }

    @Override
    protected String getOauthImplClassDefault()
    {
        return OauthImplClassDefault;
    }

    @Override
    protected String getServletConfigDefault()
    {
        return CrestController.CrestServletConfigDefault;
    }
    
    @Override
    protected BaseClientInfo getBaseClientInfo()
    {
        return new CrestClientInfo();
    }

    @Override
    protected void init(TabToLevel format)
    {
        getApplicationSettings().setAccessDeniedPage(LoginPage.class);
        String crestUrl = properties.getProperty(CrestController.CrestUrlKey, CrestController.CrestUrlDefault); 
        String xmlUrl = properties.getProperty(CrestController.XmlUrlKey, CrestController.XmlUrlDefault); 
        String userAgent = properties.getProperty(CrestController.UserAgentKey, CrestController.UserAgentDefault);
        CrestController controller = CrestController.getCrestController();
        CrestClient.getClient(controller, crestUrl, xmlUrl, userAgent, controller.blockingExecutor);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
    
    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionImplClass()
    {
        return WicketWebSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass()
    {
        return super.getSignInPageClass();
    }
}
