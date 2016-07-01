package org.opendof.tools.repository.interfaces.servlet.definition;

import java.io.StringWriter;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.opendof.tools.repository.interfaces.core.AuthenticatedUser;
import org.opendof.tools.repository.interfaces.core.CoreController;
import org.opendof.tools.repository.interfaces.core.XMLTranslator;
import org.opendof.tools.repository.interfaces.da.InterfaceData;
import org.opendof.tools.repository.interfaces.servlet.InterfaceRepository;
import org.opendof.tools.repository.interfaces.servlet.auth.ClientInformation;
import org.opendof.tools.repository.interfaces.servlet.index.Index;
import org.opendof.tools.repository.interfaces.servlet.menu.InterfaceOptionPanel;
import org.opendof.tools.repository.interfaces.servlet.menu.ViewAllocatedPanel;
import org.opendof.tools.repository.interfaces.servlet.menu.allocate.AllocateInterfaceOptionPanel;
import org.opendof.tools.repository.interfaces.servlet.menu.manage.ManageInterfacePanel;
import org.opendof.tools.repository.interfaces.servlet.template.FooterPanel;
import org.opendof.tools.repository.interfaces.servlet.template.HeaderPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Definition extends WebPage {
	private static final long serialVersionUID = -3874669805256218726L;
	
	public static final int REPO_INDEX = 0;
	public static final int IID_INDEX = 1;
	public static final int VERSION_INDEX = 2;
	public static final String TRANS_NAME = "trans";
	
	private Component headerPanel;
	private Component footerPanel;
	private MarkupContainer sidePanel;
	private MarkupContainer sourceLinkSpan;
	private BookmarkablePageLink<Object> sourceLink;
	private Button sourceButton;
	private Label sourceLabel;
	
	private String repo = null;
	private String iid = null;
	private String optionalVersion = null;
	private String trans = "HTML";
	
	private static Logger log = LoggerFactory.getLogger(Definition.class);

	public Definition(PageParameters parameters) {
		super(parameters);
						
		headerPanel = new HeaderPanel("headerPanel");
		footerPanel = new FooterPanel("footerPanel");
		
		add(headerPanel);
		add(footerPanel);
		
		if("GetInterface".equals(parameters.get("cmd").toString())){
			//Legacy URL format
			iid = parameters.get("iid").toString();
			repo = parameters.get("repo").toOptionalString();
			trans = parameters.get("trans").toOptionalString();
			if(trans == null || trans.isEmpty() || trans.equals("html")){
				trans = "HTML";
			} else if(trans.equals("raw")){
				trans = "XML";
			}

			if(iid.indexOf("dof/") == 0){
				iid = iid.substring(4, iid.length());
				repo = "opendof";
			}
			
			try {
				iid = getCore().getStandardIdentifier(repo, iid); //Better for parsing various forms potentially used in legacy links
				iid = getCore().getURLIdentifier(repo, iid); //Back to newer official URL form
			} catch (Exception e) {
				log.debug("Failed to decode iid: " + iid, e);
				throw new RestartResponseAtInterceptPageException(Index.class);
			}
			PageParameters newParameters = new PageParameters();
			newParameters.set(Definition.REPO_INDEX, repo);
			newParameters.set(Definition.IID_INDEX, iid);
			newParameters.set(Definition.TRANS_NAME, trans);
			if(trans.equals("XML")){
				throw new RestartResponseAtInterceptPageException(RawDefinition.class, newParameters);
			}
			throw new RestartResponseAtInterceptPageException(Definition.class, newParameters);
		}else{
			parseParameters(parameters);
		}
		
        InterfaceData interfaceData = null;
        AuthenticatedUser authUser = null;
        if(getClientInformation().isAuthenticated())
            authUser = new AuthenticatedUser(getClientInformation().getName(), getClientInformation().getEmail(), getClientInformation().getDescription(), null); 

        try {
        	interfaceData = getCore().getInterface(authUser, repo, iid, optionalVersion);
		} catch (Exception e) {
			log.debug("Failed to get interface repo: " + repo + " iid: " + iid + " version: " + (optionalVersion == null ? "1" : optionalVersion), e);
			throw new RestartResponseAtInterceptPageException(Index.class);
		}
        
        String interfaceTitle = interfaceData.iid + (interfaceData.getName() != null ? " - " + interfaceData.getName() : "");
		
		Label interfaceTitleLabel = new Label("interfaceTitle", new Model<String>(interfaceTitle));
		add(interfaceTitleLabel);
        
        XMLTranslator translator = null;
        String translatedXML = null;
        try {
        	translator = getCore().getTranslator(interfaceData.getRepoType(), trans);
        	StringWriter writer = new StringWriter();
        	translator.toHTML(interfaceData.xml, writer);
        	translatedXML = writer.toString();
		} catch (Exception e) {
			log.debug("Failed to translate interface: " + interfaceData, e);
		}
        
        Label interfaceContentLabel;
        if(interfaceData.xml == null){
        	interfaceContentLabel = new Label("interfaceContent", "No XML definition has been submitted.");
        } else if(translatedXML == null || translatedXML.isEmpty()){
        	interfaceContentLabel = new Label("interfaceContent", "Conversion from XML to " + trans + " has failed.");
        } else{
        	interfaceContentLabel = new Label("interfaceContent", translatedXML);
        }
        	
        	
		if(!trans.equals("XML")){
			interfaceContentLabel.setEscapeModelStrings(false);
		}
        add(interfaceContentLabel);
        
        sourceLinkSpan = new WebMarkupContainer("sourceLinkSpan");
		sourceLink = new BookmarkablePageLink<Object>("sourceLink", RawDefinition.class, parameters);
		sourceButton = new Button("sourceButton");
		sourceLabel = new Label("sourceLabel", new Model<String>("View " + translator.getTranslationName() + " Source"));
		sourceButton.add(sourceLabel);
		sourceLink.add(sourceButton);
		sourceLinkSpan.add(sourceLink);
		sourceLinkSpan.setVisible(translator.hasRawSupport() && interfaceData.xml != null);
        add(sourceLinkSpan);
        
        WebMarkupContainer horizontalRule = new WebMarkupContainer("rule");
        add(horizontalRule);
        
        sourceLinkSpan.setVisible(!translator.hasHTMLSupport());
        horizontalRule.setVisible(translator.hasHTMLSupport() && translator.hasRawSupport());
        interfaceContentLabel.setVisible(translator.hasHTMLSupport());
		ViewAllocatedPanel viewAllocatedPanel = new ViewAllocatedPanel("viewAllocatedPanel");
		 
		sidePanel = new WebMarkupContainer("sidePanel");
		sidePanel.add(viewAllocatedPanel);
		AllocateInterfaceOptionPanel reservePanel = new AllocateInterfaceOptionPanel("allocateInterfaceOptionPanel");
		sidePanel.add(reservePanel);
		ManageInterfacePanel manageInterfacePanel = new ManageInterfacePanel("manageInterfacePanel", interfaceData);
		InterfaceOptionPanel interfaceOptionPanel = new InterfaceOptionPanel("interfaceOptionPanel", translator, interfaceData, parameters, translatedXML == null || translatedXML.isEmpty());
		sidePanel.add(interfaceOptionPanel);
		sidePanel.add(manageInterfacePanel);
		add(sidePanel);

		viewAllocatedPanel.setVisible(getClientInformation().isAuthenticated());
		interfaceOptionPanel.setVisible(interfaceData.xml != null);
		reservePanel.setVisible(getClientInformation().isAuthenticated());	
		manageInterfacePanel.setVisible(getClientInformation().isAuthenticated() && getClientInformation().getEmail().equals(interfaceData.submitter.email));
	}
	
	private CoreController getCore(){
		return ((InterfaceRepository)getApplication()).getCoreController();
	}
	
	private ClientInformation getClientInformation(){
		return (ClientInformation)WebSession.get().getClientInfo();
	}
	
	private void parseParameters(PageParameters parameters){
		repo = parameters.get(REPO_INDEX).toString();
		try {
			iid = getCore().getStandardIdentifier(repo, parameters.get(IID_INDEX).toString());
		} catch (Exception e) {
			log.debug("Failed to decode iid: " + parameters.get(IID_INDEX), e);
			throw new RestartResponseAtInterceptPageException(Index.class);
		}
		
		optionalVersion = parameters.get(VERSION_INDEX).toString();
		
		String translation = parameters.get(TRANS_NAME).toString();
		if(translation != null && !translation.isEmpty()){
			trans = translation;
		}
		
	}
}
