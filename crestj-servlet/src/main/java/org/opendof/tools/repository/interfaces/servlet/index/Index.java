package org.opendof.tools.repository.interfaces.servlet.index;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.opendof.tools.repository.interfaces.core.AuthenticatedUser;
import org.opendof.tools.repository.interfaces.core.CoreController;
import org.opendof.tools.repository.interfaces.da.InterfaceData;
import org.opendof.tools.repository.interfaces.da.NotFoundException;
import org.opendof.tools.repository.interfaces.servlet.InterfaceRepository;
import org.opendof.tools.repository.interfaces.servlet.auth.ClientInformation;
import org.opendof.tools.repository.interfaces.servlet.browse.Browse;
import org.opendof.tools.repository.interfaces.servlet.definition.Definition;
import org.opendof.tools.repository.interfaces.servlet.menu.ViewAllocatedPanel;
import org.opendof.tools.repository.interfaces.servlet.menu.allocate.AllocateInterfaceOptionPanel;
import org.opendof.tools.repository.interfaces.servlet.template.FooterPanel;
import org.opendof.tools.repository.interfaces.servlet.template.HeaderPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("javadoc")
public class Index extends WebPage{
	static final long serialVersionUID = 5422903963692085199L;
	private Component headerPanel;
	private Component footerPanel;
	private MarkupContainer sidePanel;
	private WebMarkupContainer gcseDiv;
	private Label gcseLabel;
	
	private static Logger log = LoggerFactory.getLogger(Index.class);

	public Index(){		
		Properties properties = (Properties) ((InterfaceRepository) getApplication()).getServletContext().getAttribute(CoreController.PropertiesKey);
		
		headerPanel = new HeaderPanel("headerPanel");
		footerPanel = new FooterPanel("footerPanel");
		sidePanel = new WebMarkupContainer("sidePanel");
		AllocateInterfaceOptionPanel reservePanel = new AllocateInterfaceOptionPanel("allocateInterfaceOptionPanel");
		sidePanel.add(reservePanel);
		ViewAllocatedPanel viewAllocatedPanel = new ViewAllocatedPanel("viewAllocatedPanel");
		sidePanel.add(viewAllocatedPanel);
		
		reservePanel.setVisible(getClientInformation().isAuthenticated());
		viewAllocatedPanel.setVisible(getClientInformation().isAuthenticated());
		sidePanel.setVisible(getClientInformation().isAuthenticated());
		
		
		add(headerPanel);
		add(footerPanel);
		add(sidePanel);
		
		InterfaceData interfaceData = new InterfaceData("[1:{01}]", "1", "opendof");
		add(new InterfaceRequestForm("interfaceRequestForm", new CompoundPropertyModel<InterfaceData>(interfaceData)));		

		add(new BookmarkablePageLink<>("browse", Browse.class));		
		
		gcseDiv = new WebMarkupContainer("gcseDiv");
		String cx = properties.getProperty(InterfaceRepository.GoogleCSEcxKey);
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("(function() {");
		sb.append("var cx = '" + cx + "';");
		sb.append("var gcse = document.createElement('script');");
		sb.append("gcse.type = 'text/javascript';");
		sb.append("gcse.async = true;");
		sb.append("gcse.src = 'https://cse.google.com/cse.js?cx=' + cx;");
		sb.append("var s = document.getElementsByTagName('script')[0];");
		sb.append("s.parentNode.insertBefore(gcse, s);");
		sb.append("})();");
		sb.append("</script>");
		sb.append("<gcse:search></gcse:search>");
		gcseLabel = new Label("gcseLabel", sb.toString());
		gcseLabel.setEscapeModelStrings(false);
		
		gcseDiv.add(gcseLabel);
		add(gcseDiv);
	}
	
	private CoreController getCore(){
		return ((InterfaceRepository)getApplication()).getCoreController();
	}
	
	private ClientInformation getClientInformation(){
		return (ClientInformation)WebSession.get().getClientInfo();
	}
	
	private class InterfaceRequestForm extends Form<InterfaceData>{
		private static final long serialVersionUID = -36610284781844624L;

		private DropDownChoice<String> repoDropDownChoice;
		private Label versionLabel;
		private TextField<String> versionTextField;
		private TextField<String> iidTextField;
		private WebMarkupContainer versionRow;
		
		public InterfaceRequestForm(String id, IModel<InterfaceData> model) {
			super(id, model);

			List<String> repos = new ArrayList<String>();
			repos.addAll(getCore().getRepositoryList());
			
			//TODO: Make this configurable somehow?
			String defaultRepo;
			if(repos.contains("opendof"))
				defaultRepo = "opendof";
			else
				defaultRepo = repos.get(0);
			
			model.getObject().setRepoType(defaultRepo);
			
			repoDropDownChoice = new RepoDropDownChoice("repoField", new PropertyModel<String>(model, "repoType"), repos);
			iidTextField = new TextField<String>("iidField", new PropertyModel<String>(model,"iid"), String.class);
			versionRow = new WebMarkupContainer("versionRow");
			versionLabel = new Label("versionLabel", "Version: ");
			versionTextField = new TextField<String>("versionField", new PropertyModel<String>(model,"version"), String.class);
			
			setVersionVisibility(model.getObject().getRepoType());
			
			add(new FeedbackPanel("feedback"));
			add(new Button("submit"));
			add(repoDropDownChoice);
			add(iidTextField);
			versionRow.add(versionLabel);
			versionRow.add(versionTextField);
			add(versionRow);
		}
		
		private void setVersionVisibility(String repo){
			try {
				if(getCore().getVersionSupported(repo)){
					versionRow.setVisible(true);
				} else{
					versionRow.setVisible(false);
				}
			} catch (Exception e) {
				log.warn("Failed to check version support for repo " + repo + " - " + e, e);
			}
		}
		
		@Override
		protected void onValidate() {
			super.onValidate();
			
			String repo = repoDropDownChoice.getConvertedInput();
			String iid = iidTextField.getConvertedInput();
			String version = versionTextField.getConvertedInput();
			
	        InterfaceData interfaceData = null;
	        AuthenticatedUser authUser = null;
	        if(getClientInformation().isAuthenticated())
	            authUser = new AuthenticatedUser(getClientInformation().getName(), getClientInformation().getEmail(), getClientInformation().getDescription(), null); 
	        try {
	        	interfaceData = getCore().getInterface(authUser, repo, iid, version);
			} catch (NotFoundException e) {
				String msg = getString("invalid_interface_identifier");
				error(msg);
				return;
			} catch (Exception e) {
				log.debug("Failed to validate {} interface id {} - {}", repo, iid, e, e);
			}
	        
	        if(interfaceData == null){
	        	error(getString("invalid_interface_identifier"));
	        }
		}
		
		@Override
		public void onSubmit(){
			String repo = repoDropDownChoice.getDefaultModelObjectAsString();
			String iid = iidTextField.getDefaultModelObjectAsString();
			String version = versionTextField.getDefaultModelObjectAsString();
			
			try {
				iid = getCore().getURLIdentifier(repo, iid);
				repo = URLEncoder.encode(repo, "UTF-8");
				if(version != null) version = URLEncoder.encode(version, "UTF-8");
			} catch (Exception e) {
				log.debug("Failed to parse parameters.", e);
			}
			
			boolean versionSupported;
			try{
				versionSupported = getCore().getVersionSupported(repo);
			} catch(Exception e){
				log.warn("failed to check version support for repo " + repo + " - " + e, e);
				versionSupported = false;
			}

			PageParameters parameters = new PageParameters();
			parameters.set(Definition.REPO_INDEX, repo);
			parameters.set(Definition.IID_INDEX, iid);
			if(versionSupported && version != null && !version.isEmpty()){
				parameters.set(Definition.VERSION_INDEX, version);
			}
			setResponsePage(Definition.class, parameters);
		}
		
	    private ClientInformation getClientInformation(){
	        return (ClientInformation)WebSession.get().getClientInfo();
	    }
	    
		
		private class RepoDropDownChoice extends DropDownChoice<String>{
			private static final long serialVersionUID = -3712082508263393331L;

			public RepoDropDownChoice(String id, IModel<String> model, List<? extends String> choices) {
				super(id, model, choices);
			}
			
			@Override
			protected boolean wantOnSelectionChangedNotifications(){
				return true;
			}
			
			@Override
			protected void onSelectionChanged(final String newSelection){
				setVersionVisibility(newSelection);
			}
		}
	}
}
