package com.ccc.crest.need.definition;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.crest.need.index.Index;
import com.ccc.crest.servlet.auth.CrestClientInformation;

public class RawDefinition extends WebPage
{
    private static final long serialVersionUID = 4933114262711780084L;

    public static final int REPO_INDEX = 0;
    public static final int IID_INDEX = 1;
    public static final int VERSION_INDEX = 2;
    public static final String TRANS_NAME = "trans";

    private String repo = null;
    private String iid = null;
    private String optionalVersion = null;
    private String trans = "HTML";
    private String translatedXML;
    private String contentType = "text/plain";

    private static Logger log = LoggerFactory.getLogger(RawDefinition.class);

    public RawDefinition(PageParameters parameters)
    {
        super(parameters);

        parseParameters(parameters);

//        InterfaceData interfaceData = null;
//        AuthenticatedUser authUser = null;
//        if (getClientInformation().isAuthenticated())
//            authUser = new AuthenticatedUser(getClientInformation().getName(), getClientInformation().getEmail(), getClientInformation().getDescription(), null);
//        try
//        {
//            interfaceData = getCore().getInterface(authUser, repo, iid, optionalVersion);
//        } catch (Exception e)
//        {
//            log.debug("Failed to get interface repo: " + repo + " iid: " + iid + " version: " + (optionalVersion == null ? "1" : optionalVersion), e);
//            throw new RestartResponseAtInterceptPageException(Index.class);
//        }
//
//        XMLTranslator translator;
//        try
//        {
//            translator = getCore().getTranslator(interfaceData.getRepoType(), trans);
//            if (!translator.hasRawSupport())
//            {
//                setResponsePage(new Definition(parameters));
//                return;
//            }
//            StringWriter writer = new StringWriter();
//            contentType = translator.toRaw(interfaceData.xml, writer);
//            translatedXML = writer.toString();
//        } catch (Exception e)
//        {
//            log.debug("Failed to translate interface: " + interfaceData, e);
//            throw new RestartResponseAtInterceptPageException(Index.class);
//        }
    }

    private CrestClientInformation getClientInformation()
    {
        return (CrestClientInformation) WebSession.get().getClientInfo();
    }

    @Override
    public final void renderPage()
    {
        WebResponse response = (WebResponse) getResponse();
        response.setContentType(contentType);
        response.write(translatedXML);
    }

//    private CoreController getCore()
//    {
//        return ((NeedServer) getApplication()).getCoreController();
//    }

    private void parseParameters(PageParameters parameters)
    {
        repo = parameters.get(REPO_INDEX).toString();
        try
        {
//            iid = getCore().getStandardIdentifier(repo, parameters.get(IID_INDEX).toString());
        } catch (Exception e)
        {
            log.debug("Failed to decode iid: " + parameters.get(IID_INDEX), e);
            throw new RestartResponseAtInterceptPageException(Index.class);
        }

        optionalVersion = parameters.get(VERSION_INDEX).toString();

        String translation = parameters.get(TRANS_NAME).toString();
        if (translation != null && !translation.isEmpty())
        {
            trans = translation;
        }

    }
}
