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
package org.opendof.tools.repository.interfaces.servlet.auth.jira;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authroles.authentication.pages.SignOutPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.opendof.tools.repository.interfaces.servlet.NeedServer;
import org.opendof.tools.repository.interfaces.servlet.auth.UserAuthenticationHandler;
import org.opendof.tools.repository.interfaces.servlet.auth.UserAuthenticationHandler.AuthenticateUserData;
import org.slf4j.LoggerFactory;

@SuppressWarnings({ "javadoc" })
public final class JiraSignInPage extends WebPage
{
    private static final long serialVersionUID = 1L;

    public JiraSignInPage(final PageParameters parameters)
    {
        super(parameters);
        NeedServer.logRequest((HttpServletRequest)getRequest().getContainerRequest());
        JiraClientInfo clientInfo = (JiraClientInfo) WebSession.get().getClientInfo();
        try
        {
            UserAuthenticationHandler handler = UserAuthenticationHandler.getInstance();
            AuthenticateUserData oauthData = handler.authenticateUser();
            clientInfo.setRequestToken(oauthData.requestToken);
            clientInfo.setLoginUrl(oauthData.loginUrl);
            clientInfo.setOauthConfig(oauthData.oauthConfig);
        } catch (Exception e)
        {
            LoggerFactory.getLogger(getClass()).info("OAuth authentication phase 1 failed", e);
            throw new RestartResponseAtInterceptPageException(SignOutPage.class);
        }
        throw new RedirectToUrlException(clientInfo.getLoginUrl());
    }
}
