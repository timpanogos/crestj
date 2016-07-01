package org.opendof.tools.repository.interfaces.servlet.menu.allocate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebSession;
import org.opendof.tools.repository.interfaces.core.AuthenticatedUser;
import org.opendof.tools.repository.interfaces.core.CoreController;
import org.opendof.tools.repository.interfaces.da.DataAccessor;
import org.opendof.tools.repository.interfaces.da.InterfaceData;
import org.opendof.tools.repository.interfaces.da.SubRepositoryNode;
import org.opendof.tools.repository.interfaces.da.SubmitterData;
import org.opendof.tools.repository.interfaces.servlet.InterfaceRepository;
import org.opendof.tools.repository.interfaces.servlet.auth.ClientInformation;
import org.opendof.tools.repository.interfaces.servlet.menu.ConfirmationModalWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllocateInterfaceOptionPanel extends Panel {
	private static final long serialVersionUID = -4736200814044302198L;
	
	private static Logger log;
	
	private WebMarkupContainer expandablePanel;
	private AllocateIdentifierOption repositoryOption;
	private InterfaceData interfaceData;

	private ModalWindow notificationWindow;
	
	public AllocateInterfaceOptionPanel(String id) {
		super(id);

		log = LoggerFactory.getLogger(getClass());
		
		List<String> repos = new ArrayList<String>();
		repos.addAll(getCore().getRepositoryList());
		
		Map<String, SubRepositoryNode> repositoryNodeMap = new HashMap<String, SubRepositoryNode>();		
		for(String repo: repos){
			try {
				if(getCore().hasAllocateSupport(repo)){
					repositoryNodeMap.put(repo, getCore().getSubRepositoryNode(repo, ""));
				}
			} catch (Exception e) {
				log.debug("Failed to get allocate options for repository " + repo + ": " + e);
			}
		}

		String defaultRepo;
		if(repos.contains("opendof"))
			defaultRepo = "opendof";
		else
			defaultRepo = repos.get(0);

		repositoryOption = new AllocateIdentifierOption("Repository" , repositoryNodeMap, defaultRepo, true);
		
		expandablePanel = new WebMarkupContainer("expandablePanel");		
		Form<AllocateIdentifierOption> form = new AllocateOptionsForm("allocateForm", new CompoundPropertyModel<AllocateIdentifierOption>(repositoryOption));
		expandablePanel.add(form);
		add(expandablePanel);
	}
	
	private CoreController getCore(){
		return ((InterfaceRepository)getApplication()).getCoreController();
	}
	
	private ClientInformation getClientInformation(){
		return (ClientInformation)WebSession.get().getClientInfo();
	}
	
	private class CssClassRemover extends AttributeModifier {
		private static final long serialVersionUID = 9112914839494504958L;

		public CssClassRemover(String cssClass) {
	        super("class", new Model<String>(cssClass));
	    }

	    @Override
	    protected String newValue(String currentValue, String valueToRemove) {
	        if (currentValue == null) return "";

	        List<String> classes = new ArrayList<String>(Arrays.asList(currentValue.split(" ")));
	        classes.remove(valueToRemove);
	        return String.join(" ", classes);
	    }
	}
	
	private class OptionListView extends ListView<AllocateIdentifierOption>{
		private static final long serialVersionUID = 2527796233926912760L;

		protected OptionListView(String id) {
			super(id);
		}

		@Override
		protected void populateItem(ListItem<AllocateIdentifierOption> item) {
			final AllocateIdentifierOption option = item.getModelObject();
			
			item.add(new Label("optionLabel", new Model<String>(option.getOptionLabel() + ": ")));	
			item.add(new OptionDropDownChoice("optionValues", new PropertyModel<String>(option, "selectedValue"), option.getOptionValues()));			
		}
	}
	
	private class OptionDropDownChoice extends DropDownChoice<String>{
		private static final long serialVersionUID = -3712082508263393331L;

		public OptionDropDownChoice(String id, IModel<String> model, List<? extends String> choices) {
			super(id, model, choices);
		}
		
		@Override
		protected boolean wantOnSelectionChangedNotifications(){
			return true;
		}
		
		@Override
		protected void onSelectionChanged(final String newSelection){
			expandablePanel.add(new CssClassRemover("defaultHidden")); //Ensure panel doesn't collapse on reload
		}
	}

    private enum AccessDesire{Anonymous, User, Private}

	public class AllocateOptionsForm extends Form<AllocateIdentifierOption> {
		private static final long serialVersionUID = -5848135229801212417L;
		
		private ListView<AllocateIdentifierOption> optionListView;
		private ModalWindow confirmAllocate;
		private ConfirmAllocationPanel confirmAllocationPanel;

		public AllocateOptionsForm(String id, IModel<AllocateIdentifierOption> model) {
			super(id, model);
			
			optionListView = new OptionListView("selectedOptions");
			add(optionListView);		
			add(new SubmitButton("submit", this));
			
			confirmAllocate = new ConfirmationModalWindow("confirmAllocate", "", false);
			confirmAllocationPanel = new ConfirmAllocationPanel(confirmAllocate.getContentId(), confirmAllocate, this, "");
			confirmAllocate.setContent(confirmAllocationPanel);
			add(confirmAllocate);
		}
		
		@Override
		public void onSubmit(){
			expandablePanel.add(new CssClassRemover("defaultHidden")); //Ensure panel doesn't collapse on reload
			

		}
		
		private class SubmitButton extends AjaxButton{
			private static final long serialVersionUID = 579793156295508840L;

			public SubmitButton(String id, Form<AllocateIdentifierOption> form) {
				super(id, form);
			}
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form){
				
				AllocateIdentifierOption repoOption = (AllocateIdentifierOption)form.getModelObject();
				AccessDesire accessDesire = AccessDesire.Private;
				SubmitterData accessGroup = null;
				switch(accessDesire)
				{
	                case Anonymous:
	                    accessGroup = DataAccessor.AnonymousGroup;
	                    break;
	                case Private:
	                    accessGroup = DataAccessor.PrivateGroup;
	                    break;
	                case User:
	                    accessGroup = DataAccessor.UserGroup;
	                    break;
	                default:
	                    break;
				}
				
				AuthenticatedUser authUser = null;
				if(getClientInformation().isAuthenticated())
				    authUser = new AuthenticatedUser(getClientInformation().getName(), getClientInformation().getEmail(), getClientInformation().getDescription(), null);

				Date creation = new Date();
	            InterfaceData iface = new InterfaceData(null, null, null, "1", authUser, accessGroup, repoOption.getSelectedValue(), creation, creation, false);
				try {
					interfaceData = getCore().addInterface(authUser, repoOption.getSelectedValue(), iface, repoOption.getPath());
					log.debug("Allocated iid " + interfaceData.iid + " to " + getClientInformation().getName());
				} catch (Exception e) {
					log.warn("Failed to allocate interface: " + iface, e);
					confirmAllocationPanel.setMessages("Error", "Unable to allocate interface - " + e, true);
					confirmAllocate.show(target);
					return;
				}
				if(interfaceData == null){
					confirmAllocationPanel.setMessages("Error", "Unable to allocate interface.", true);
					confirmAllocate.show(target);
					return;
				}
				confirmAllocationPanel.setInterfaceId(interfaceData.iid);
				confirmAllocationPanel.setMessages("Interface Allocated", "You have been allocated the following interface identifier:", false);
				confirmAllocate.show(target);
			}			
		}
	}
}
