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
package com.ccc.crestj.servlet.auth;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;

import com.ccc.crestj.servlet.NeedServlet;

@SuppressWarnings("javadoc")
public class IrWebSession extends AuthenticatedWebSession
{
    private static final long serialVersionUID = 1L;
    private ClientInformation clientInfo;
    
    public IrWebSession(Request request)
    {
        super(request);
        clientInfo = ((NeedServlet)getApplication()).getClientInformation();
        setClientInfo(clientInfo);
        if(isDeveloper())
            clientInfo.setAuthenticated(true);
    }

    @Override
    public boolean authenticate(final String username, final String password)
    {
        return clientInfo.isAuthenticated();
    }

    @Override
    public Roles getRoles()
    {
        if (clientInfo.isAuthenticated())
            return new Roles(Roles.ADMIN);
        return null;
    }
    
    // don't advertise this in javadoc or example properties files
    private static final String DeveloperKey = "opendof.tools.interface.repository.auth.developer";

    private boolean isDeveloper()
    {
        Boolean dev = (Boolean)((WebApplication)getApplication()).getServletContext().getAttribute(DeveloperKey);
        if(dev == null)
            return false;
        return dev;
    }
}
