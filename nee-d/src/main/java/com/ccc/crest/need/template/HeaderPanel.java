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
package com.ccc.crest.need.template;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.CrestController;
import com.ccc.crest.need.index.Index;
import com.ccc.crest.servlet.needApi.ApiKeyInput;
import com.ccc.servlet.wicket.WicketClientInfo;
import com.ccc.servlet.wicket.login.LoginPage;
import com.ccc.servlet.wicket.logout.LogoutPage;

@SuppressWarnings("javadoc")
public class HeaderPanel extends Panel
{
    private static final long serialVersionUID = -3206507354482474558L;

    private BookmarkablePageLink<Object> login;
    private BookmarkablePageLink<Object> logout;
    private BookmarkablePageLink<Object> needsApi;
    private Label logoutLabel;
    private Label needsApiLabel;

    public HeaderPanel(String id)
    {
        super(id);

        WicketClientInfo sessionClientInfo = (WicketClientInfo) getWebSession().getClientInfo();
        CrestClientInfo clientInfo = (CrestClientInfo) sessionClientInfo.getOauthClientInfo();

        String user = sessionClientInfo.isAuthenticated() ? clientInfo.getVerifyData().CharacterName : null;

        login = new BookmarkablePageLink<Object>("login", LoginPage.class);
        logout = new BookmarkablePageLink<Object>("logout", LogoutPage.class);
        logoutLabel = new Label("logoutLabel", "Welcome, " + user + " - ");
        needsApiLabel = new Label("needsApiLabel", "We need your API Key");
//        String s = CrestController.getCrestController().scopes.getCreatePredefinedUrl(ScopeToMask.Type.Character);
//        needsApi = new ExternalLink("needsApi", s);
        needsApi = new BookmarkablePageLink<Object>("needsApi", ApiKeyInput.class);

        boolean loggedIn = user != null && !user.isEmpty();
        boolean hasKeys = true; // not going to display if not authenticated
        //TODO: add in check if there are actually any scopes configured
        if(loggedIn)
            hasKeys = CrestController.getCrestController().capsuleerHasApiKey(user);            
        logout.setVisible(loggedIn);
        logoutLabel.setVisible(loggedIn);
        login.setVisible(!loggedIn);
        needsApiLabel.setVisible(!hasKeys);
        needsApi.setVisible(!hasKeys);

        PackageResourceReference resourceReference = new PackageResourceReference(getClass(), "eveSsoSmlWht.png");
        login.add(new Image("loginImg", resourceReference));

        add(new BookmarkablePageLink<>("homeURL", Index.class));
        add(login);
        add(logoutLabel);
        add(logout);
        add(needsApiLabel);
        add(needsApi);
    }
}
