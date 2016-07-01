package org.opendof.tools.repository.interfaces.servlet;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.opendof.tools.repository.interfaces.core.CoreController;
import org.opendof.tools.repository.interfaces.core.PropertiesFile;
import org.opendof.tools.repository.interfaces.core.TabToLevel;
import org.opendof.tools.repository.interfaces.servlet.allocated.AllocatedPage;
import org.opendof.tools.repository.interfaces.servlet.auth.ClientInformation;
import org.opendof.tools.repository.interfaces.servlet.auth.IrWebSession;
import org.opendof.tools.repository.interfaces.servlet.auth.LoginPage;
import org.opendof.tools.repository.interfaces.servlet.auth.OAuthUserAuthenticator;
import org.opendof.tools.repository.interfaces.servlet.auth.jira.JiraSignInPage;
import org.opendof.tools.repository.interfaces.servlet.browse.Browse;
import org.opendof.tools.repository.interfaces.servlet.definition.Definition;
import org.opendof.tools.repository.interfaces.servlet.definition.LegacyDefinition;
import org.opendof.tools.repository.interfaces.servlet.definition.RawDefinition;
import org.opendof.tools.repository.interfaces.servlet.index.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("javadoc")
public class InterfaceRepository extends AuthenticatedWebApplication
{
    private static final String LogFilePathKey = "opendof.tools.log-file-path";
    private static final String LogFileBaseKey = "opendof.tools.log-file-base";
    private static final String LogFilePathDefault = "/var/log/tomcat8/interface-repository.log";
    private static final String LogFileBaseDefault = "/var/log/tomcat8/interface-repository"; // rolling log appender will name from here

    public static final String  GoogleCSEcxKey = "opendof.tools.interface.repository.gcse.cx";
    private static final String  GoogleCSEcxDefault = "";
    
    public static final String CopyrightYearKey = "opendof.tools.interface.repository.copyright-year";
    private static final String CopyrightYearDefault = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
    public static final String CopyrightOwnerKey = "opendof.tools.interface.repository.copyright-owner";
    private static final String CopyrightOwnerDefault = "";
    
    
    private final Logger log;
    private volatile String contextPath;
    private volatile String transFolder; 
    private volatile CoreController core;
    private volatile OAuthUserAuthenticator authenticator;
    
    public InterfaceRepository()
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
    public CoreController getCoreController()
    {
    	return core;
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
        
        String fileNameIn = getInitParameter(CoreController.IrServletConfigKey);
        String fileName = CoreController.IrServletConfigDefault;
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

            transFolder = CoreController.TranslationFolderDefault;
            String transFolderIn = properties.getProperty(CoreController.TranslationFolderKey);
            if(transFolderIn == null)
                transFolder = CoreController.TranslationFolderDefault;
            else
                transFolderIn = "null";
            sb.append("\n\t" + CoreController.TranslationFolderKey + ": " + transFolder + " (" + transFolderIn + ")");
            
            String catalogFolderIn = properties.getProperty(CoreController.CatalogFolderUrlKey);
            String catalogFolder = CoreController.CatalogFolderDefault;
            if (catalogFolderIn != null)
                catalogFolder = catalogFolderIn;
            else
                catalogFolderIn = "null";
            sb.append("\n\t" + CoreController.CatalogFolderUrlKey + ": " + catalogFolder + " (" + catalogFolderIn + ")");
            
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
            
            // make the configurable catalog folder available to the RepositoryController implementations
            catalogFolder = PropertiesFile.combinePaths(contextPath, catalogFolder, false, true);
            properties.setProperty(CoreController.CatalogFolderUrlKey, catalogFolder);
            // make the context real path available as well
            properties.setProperty(CoreController.ContextRealBaseKey, contextPath);
                        
            for (Entry<Object, Object> entry : properties.entrySet())
                sb.append("\n\t\t" + entry.getKey().toString() + "=" + entry.getValue().toString());
            msg = "core engine init failed";
            core = new CoreController();
            core.init(properties);
        } catch (Exception e)
        {
            sb.append("\n\n" + msg);
            log.error(sb.toString(), e);
            throw new RuntimeException(sb.toString(), e);
        }

        String oauthImplClass = properties.getProperty(OAuthUserAuthenticator.ImplClassKey, OAuthUserAuthenticator.ImplClassDefault);
        try
        {
            Class<?> clazz = Class.forName(oauthImplClass);
            authenticator = (OAuthUserAuthenticator) clazz.newInstance();
        } catch (Exception e)
        {
            throw new RuntimeException("Invalid OAuth implementation class, " + OAuthUserAuthenticator.ImplClassKey + " = " + oauthImplClass);
        }
        authenticator.init(properties);
        
        Class<? extends WebPage> cbclass = authenticator.getOAuthCallbackClass();
        mountPage(authenticator.getOAuthCallbackMount(), cbclass);
        
        getServletContext().setAttribute(CoreController.PropertiesKey, properties);
        
        isDeveloper(properties);
        log.info(sb.toString());
	}

    @Override
    protected void onDestroy()
    {
        if(core != null)
            core.destroy();
    }
    
    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass()
    {
        return IrWebSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass()
    {
        return JiraSignInPage.class;
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
        LoggerFactory.getLogger(InterfaceRepository.class).debug(format.toString());
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
