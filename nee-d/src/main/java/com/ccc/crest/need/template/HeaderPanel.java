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
import com.ccc.crest.servlet.auth.CrestClientInformation;
import com.ccc.crest.servlet.auth.LoginPage;
import com.ccc.crest.servlet.auth.LogoutPage;

@SuppressWarnings("javadoc")
public class HeaderPanel extends Panel
{
    private static final long serialVersionUID = -3206507354482474558L;
    
    BookmarkablePageLink<Object> login;
    BookmarkablePageLink<Object> logout;
    Label logoutLabel;

    public HeaderPanel(String id)
    {
        super(id);

        CrestClientInformation clientInformation = getClientInformation();
        String user = clientInformation.isAuthenticated() ? clientInformation.getCharacterName() : null;

        login = new BookmarkablePageLink<Object>("login", LoginPage.class);
        logout = new BookmarkablePageLink<Object>("logout", LogoutPage.class);
        logoutLabel = new Label("logoutLabel", "Welcome, " + user + " - ");

        boolean loggedIn = user != null && !user.isEmpty();
        logout.setVisible(loggedIn);
        logoutLabel.setVisible(loggedIn);
        login.setVisible(!loggedIn);

        PackageResourceReference resourceReference = new PackageResourceReference(getClass(), "eveSsoSmlWht.png");
        login.add(new Image("loginImg", resourceReference));        
        
        add(new BookmarkablePageLink<>("homeURL", Index.class));
        add(login);
        add(logoutLabel);
        add(logout);
    }

    private CrestClientInformation getClientInformation()
    {
        return (CrestClientInformation) getWebSession().getClientInfo();
    }
}
