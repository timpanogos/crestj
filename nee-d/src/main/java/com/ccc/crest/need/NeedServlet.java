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
package com.ccc.crest.need;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebPage;
import org.slf4j.LoggerFactory;

import com.ccc.crest.need.index.Index;
import com.ccc.crest.servlet.CrestServlet;
import com.ccc.tools.TabToLevel;

@SuppressWarnings("javadoc")
public class NeedServlet extends CrestServlet
{
    public static final String LogFilePathDefault = "/var/opt/ccc/crest/need/log/need.log";
    public static final String LogConfigFilePath = "/etc/opt/ccc/crest/need/logback.xml";
    public static final String NeedServletConfigDefault = "/etc/opt/ccc/crest/need/need.properties";
    public static final String NeedControllerDefault = NeedController.class.getName();

    public NeedServlet()
    {
    }

    @Override
    protected String getLogFilePathDefault()
    {
        return LogFilePathDefault;
    }

    @Override
    protected String getLogConfigFilePath()
    {
        return LogConfigFilePath;
    }

    @Override
    protected String getServletConfigDefault()
    {
        return NeedServletConfigDefault;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageImplClass()
    {
        return super.getSignInPageClass();
    }

    @Override
    public Class<? extends WebPage> getHomePage()
    {
        return Index.class;
    }

    @Override
    public void init(TabToLevel format)
    {
        super.init(format);
//        mountPage("/browse", Index.class);
//        mountPage("/interface", Definition.class);
//        mountPage("/interface-repository/interface", LegacyDefinition.class);
//        mountPage("/interface/raw", RawDefinition.class);
//        mountPage("/allocated", AllocatedPage.class);
//        log.info(sb.toString());
	}

    @Override
    public void onEvent(IEvent<?> event)
    {
        // TODO Auto-generated method stub
        super.onEvent(event);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    public static void logRequest(HttpServletRequest request)
    {
        TabToLevel format = new TabToLevel();
        format.ttl("HttpServletRequest:\n");
        format.level.incrementAndGet();
        format.ttl("requestClass: ", request.getClass().getName());
        format.ttl("requestToString: ", request.toString());
        format.ttl("authType: ", request.getAuthType());
        Cookie[] cookies = request.getCookies();
        format.ttl("cookies: ");
        format.level.incrementAndGet();
        if(cookies != null && cookies.length > 0)
            for(int i=0; i < cookies.length; i++)
                logCookie(cookies[i], format);
        else
            format.ttl("none");
        format.level.decrementAndGet();

        format.ttl("headers:");
        format.level.incrementAndGet();
        Enumeration<String> headerNames = request.getHeaderNames();
        if(!headerNames.hasMoreElements())
            format.ttl("none");
        while(headerNames.hasMoreElements())
        {
            String headerName = headerNames.nextElement();
            format.ttl("headerName: ", headerName);
            format.level.incrementAndGet();
            format.ttl("header: ", request.getHeader(headerName));
//            format.ttl("intHeader: ", request.getIntHeader(headerName));
//            Date headerDate = new Date(request.getDateHeader(headerName));
//            format.ttl("dateHeader: ", headerDate.toString());
            format.level.decrementAndGet();
        }
        format.level.decrementAndGet();
        format.ttl("method: ", request.getMethod());
        format.ttl("pathInfo: ", request.getPathInfo());
        format.ttl("pathTranslated: ", request.getPathTranslated());
        format.ttl("contextPath: ", request.getContextPath());
        format.ttl("queryString: ", request.getQueryString());
        format.ttl("getRemoteUser: ", request.getRemoteUser());
//        format.ttl("userInRole: ", request.isUserInRole(String role));
        format.ttl("userPrincipal: ", request.getUserPrincipal());
        format.ttl("requestedSessionId: ", request.getRequestedSessionId());
        format.ttl("requestURI: ", request.getRequestURI());
        format.ttl("requestURL: ", request.getRequestURL());
        format.ttl("servletPath: ", request.getServletPath());
        format.ttl("session: not currently expanded");
//        public HttpSession getSession();
        format.ttl("requestedSessionIdValid: ", request.isRequestedSessionIdValid());
        format.ttl("requestedSessionIdFromCookie: ", request.isRequestedSessionIdFromCookie());
        format.ttl("requestedSessionIdFromURL: ", request.isRequestedSessionIdFromURL());
//        format.ttl("requestedSessionIdFromUrl: ", request.isRequestedSessionIdFromUrl());
        format.ttl("requestedSessionIdValid: ", request.isRequestedSessionIdValid());
        format.ttl("requestedSessionIdValid: ", request.isRequestedSessionIdValid());
//        public boolean authenticate(HttpServletResponse response)
//        public void login(String username, String password)
//        public void logout() throws ServletException;

        format.ttl("parts: not currently expanded");
//        public Collection<Part> getParts() throws IOException, ServletException;
//        public Part getPart(String name) throws IOException, ServletException;
//        public <T extends HttpUpgradeHandler> T  upgrade(Class<T> handlerClass)
        LoggerFactory.getLogger(NeedServlet.class).debug(format.toString());
    }

    public static void logCookie(Cookie cookie, TabToLevel format)
    {
        format.ttl("name: ", cookie.getName());
        format.level.incrementAndGet();
        format.ttl("value: ", cookie.getValue());
        format.ttl("comment: ", cookie.getComment());
        format.ttl("domain: ", cookie.getDomain());
        format.ttl("maxAge: ", cookie.getMaxAge());
        format.ttl("path: ", cookie.getPath());
        format.ttl("secure: ", cookie.getSecure());
        format.ttl("version: ", cookie.getVersion());
//        format.ttl("token", cookie.isToken(String value));
        format.ttl("httpOnly: ", cookie.isHttpOnly());
        format.level.decrementAndGet();
    }

    @Override
    protected String getCoreImplClassDefault()
    {
        return NeedControllerDefault;
    }
}
