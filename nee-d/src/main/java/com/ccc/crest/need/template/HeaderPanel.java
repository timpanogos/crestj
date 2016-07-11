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
package com.ccc.crest.need.template;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;

import com.ccc.crest.need.index.Index;
import com.ccc.crest.servlet.CrestController;
import com.ccc.crest.servlet.auth.CrestClientInfo;
import com.ccc.tools.servlet.clientInfo.SessionClientInfo;
import com.ccc.tools.servlet.login.LoginPage;
import com.ccc.tools.servlet.logout.LogoutPage;

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

        SessionClientInfo sessionClientInfo = (SessionClientInfo) getWebSession().getClientInfo();
        CrestClientInfo clientInfo = (CrestClientInfo) sessionClientInfo.getOauthClientInfo();

        String user = sessionClientInfo.isAuthenticated() ? clientInfo.getVerifyData().CharacterName : null;

        login = new BookmarkablePageLink<Object>("login", LoginPage.class);
        logout = new BookmarkablePageLink<Object>("logout", LogoutPage.class);
        logoutLabel = new Label("logoutLabel", "Welcome, " + user + " - ");
        needsApiLabel = new Label("needsApiLabel", "We need your API Key");
        needsApi = new BookmarkablePageLink<Object>("needsApi", LogoutPage.class);

        boolean loggedIn = user != null && !user.isEmpty();
        boolean hasKeys = true; // not going to display if not authenticated
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
