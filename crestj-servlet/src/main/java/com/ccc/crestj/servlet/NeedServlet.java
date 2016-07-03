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
package com.ccc.crestj.servlet;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.crestj.servlet.allocated.AllocatedPage;
import com.ccc.crestj.servlet.auth.ClientInformation;
import com.ccc.crestj.servlet.auth.IrWebSession;
import com.ccc.crestj.servlet.auth.LoginPage;
import com.ccc.crestj.servlet.auth.OAuthUserAuthenticator;
import com.ccc.crestj.servlet.auth.eve.EveAuthenticator;
import com.ccc.crestj.servlet.auth.eve.EveSignInPage;
import com.ccc.crestj.servlet.browse.Browse;
import com.ccc.crestj.servlet.definition.Definition;
import com.ccc.crestj.servlet.definition.LegacyDefinition;
import com.ccc.crestj.servlet.definition.RawDefinition;
import com.ccc.crestj.servlet.index.Index;
import com.ccc.tools.TabToLevel;
import com.ccc.tools.app.serviceUtility.PropertiesFile;

@SuppressWarnings("javadoc")
public class NeedServlet extends AuthenticatedWebApplication
{
    public static final String NeedServletConfigKey = "ccc.crestj.servlet.config";
    public static final String ContextRealBaseKey = "ccc.crestj.servlet.context-base";
    public static final String WicketPropertiesKey = "ccc.crestj.wicket.need.properties";
    
    public static final String OauthLoginUrlKey = "ccc.crestj.oauth.eve.login-url";
    public static final String OauthTokenUrlKey = "ccc.crestj.oauth.eve.token-url";
    public static final String OauthVerifyUrlKey = "ccc.crestj.oauth.eve.verify-url";
    public static final String OauthCallbackUrlKey = "ccc.crest.oauth.eve.callback-url";
    public static final String OauthClientIdKey = "ccc.crest.oauth.eve.client-key";
    public static final String OauthClientSecretKey = "ccc.crest.oauth.eve.client-secret";
    public static final String OauthScopeKey = "ccc.crest.eve.scope";
    public static final String OauthImplClassKey = "ccc.crestj.oauth.impl-class";
    
    public static final String NeedServletConfigDefault = "etc/opt/ccc/crest/need/need.properties";
    public final static String OauthImplClassDefault = EveAuthenticator.class.getName();
    public static final String OauthLoginUrlDefault = "https://login.eveonline.com/oauth/authorize";
    public static final String OauthTokenUrlDefault = "https://login.eveonline.com/oauth/token";
    public static final String OauthVerifyUrlDefault = "https://login.eveonline.com/oauth/verify";
    
    public final static String OauthScopeDefault = "publicData";
    public static final String CopyrightYearKey = "ccc.crest.copyright-year";
    public static final String CopyrightOwnerKey = "ccc.crest.copyright-owner";
    private static final String CopyrightYearDefault = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
    private static final String CopyrightOwnerDefault = "";
    
    
    
    private static final String LogFilePathKey = "opendof.tools.log-file-path";
    private static final String LogFileBaseKey = "opendof.tools.log-file-base";
    private static final String LogFilePathDefault = "/var/log/tomcat8/interface-repository.log";
    private static final String LogFileBaseDefault = "/var/log/tomcat8/interface-repository"; // rolling log appender will name from here

    public static final String  GoogleCSEcxKey = "opendof.tools.interface.repository.gcse.cx";
    private static final String  GoogleCSEcxDefault = "";
    
    
    
    private final Logger log;
    private volatile String contextPath;
    private volatile OAuthUserAuthenticator authenticator;
    
    public NeedServlet()
    {
        System.setProperty(LogFilePathKey, LogFilePathDefault);
        System.setProperty(LogFileBaseKey, LogFileBaseDefault);
        System.setProperty(GoogleCSEcxKey, GoogleCSEcxDefault);
        System.setProperty(CopyrightYearKey, CopyrightYearDefault);
        System.setProperty(CopyrightOwnerKey, CopyrightOwnerDefault);
        log = LoggerFactory.getLogger(getClass());
    }

    public ClientInformation getClientInformation()
    {
        return authenticator.getClientInformation();
    }
    
    @Override
    public Class<? extends WebPage> getHomePage()
    {
        return Index.class;
    }

    @Override
    public void init()
    {
        super.init();
        mountPage("/browse", Browse.class);
        mountPage("/interface", Definition.class);
        mountPage("/interface-repository/interface", LegacyDefinition.class);
        mountPage("/interface/raw", RawDefinition.class);
        mountPage("/allocated", AllocatedPage.class);
        
        getDebugSettings().setAjaxDebugModeEnabled(false);
        getApplicationSettings().setAccessDeniedPage(LoginPage.class);
                
        contextPath = getServletContext().getRealPath("/");
        StringBuilder sb = new StringBuilder("\n" + getClass().getSimpleName() + ".init");
        //@formatter:off
        sb.append("\n\tapplicationKey: " + getApplicationKey())
            .append("\n\tcontext path: " + contextPath);
        //@formatter:on
        
        String fileNameIn = getInitParameter(NeedServletConfigKey);
        String fileName = NeedServletConfigDefault;
        if (fileNameIn != null)
            fileName = fileNameIn;
        else
            fileNameIn = "null";
        sb.append("\n\tproperties: /classes/" + fileName +"(" +fileNameIn +")");
            
        Properties properties = new Properties();
        String msg = "configuration properties file not found: " + fileName;
        try
        {
//            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
            PropertiesFile.load(properties, fileName);
            
            String copyrightYearIn = properties.getProperty(CopyrightYearKey);
            String copyrightYear = CopyrightYearDefault;
            if (copyrightYearIn != null)
                copyrightYear = copyrightYearIn;
            else
                copyrightYearIn = "null";
            sb.append("\n\t" + CopyrightYearKey + ": " + copyrightYear + " (" + copyrightYearIn + ")");
            
            String copyrightOwnerIn = properties.getProperty(CopyrightOwnerKey);
            String copyrightOwner = CopyrightOwnerDefault;
            if (copyrightOwnerIn != null)
                copyrightOwner = copyrightOwnerIn;
            else
                copyrightOwnerIn = "null";
            sb.append("\n\t" + CopyrightOwnerKey + ": " + copyrightOwner + " (" + copyrightOwnerIn + ")");
            
            properties.setProperty(ContextRealBaseKey, contextPath);
                        
            for (Entry<Object, Object> entry : properties.entrySet())
                sb.append("\n\t\t" + entry.getKey().toString() + "=" + entry.getValue().toString());
            msg = "core engine init failed";
        } catch (Exception e)
        {
            sb.append("\n\n" + msg);
            log.error(sb.toString(), e);
            throw new RuntimeException(sb.toString(), e);
        }

        String oauthImplClass = properties.getProperty(OauthImplClassKey, OauthImplClassDefault);
        try
        {
            Class<?> clazz = Class.forName(oauthImplClass);
            authenticator = (OAuthUserAuthenticator) clazz.newInstance();
        } catch (Exception e)
        {
            throw new RuntimeException("Invalid OAuth implementation class, " + OauthImplClassKey + " = " + oauthImplClass);
        }
        authenticator.init(properties);
        
        Class<? extends WebPage> cbclass = authenticator.getOAuthCallbackClass();
        mountPage(authenticator.getOAuthCallbackMount(), cbclass);
        
        getServletContext().setAttribute(WicketPropertiesKey, properties);
        
        isDeveloper(properties);
        log.info(sb.toString());
	}

    @Override
    protected void onDestroy()
    {
    }
    
    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass()
    {
        return IrWebSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass()
    {
        return EveSignInPage.class;
    }
    
    // don't advertise this in javadoc or example properties files
    // needs to match the same declaration in IrWebSession.java
    private static final String DeveloperKey = "opendof.tools.interface.repository.auth.developer";

    private void isDeveloper(Properties properties)
    {
        String devstr = properties.getProperty(DeveloperKey);
        if(devstr == null)
            return;
        getServletContext().setAttribute(DeveloperKey, Boolean.parseBoolean(devstr));
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
        {
            for(int i=0; i < cookies.length; i++)
                logCookie(cookies[i], format);
        }else
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
}
