/*
**  Copyright (c) 2016, Chad Adams.
**
**  This program is free software: you can redistribute it and/or modify
**  it under the terms of the GNU Lesser General Public License as 
**  published by the Free Software Foundation, either version 3 of the 
**  License, or any later version.
**
**  This program is distributed in the hope that it will be useful,
**  but WITHOUT ANY WARRANTY; without even the implied warranty of
**  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
**  GNU General Public License for more details.

**  You should have received copies of the GNU GPLv3 and GNU LGPLv3
**  licenses along with this program.  If not, see http://www.gnu.org/licenses
*/
package com.ccc.crest.servlet;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.pages.SignOutPage;
import org.apache.wicket.markup.html.WebPage;

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.cache.DataCache;
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
    
    public static CrestClientInfo getCrestClientInfo()
    {
        return (CrestClientInfo)getClientInfo();
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
