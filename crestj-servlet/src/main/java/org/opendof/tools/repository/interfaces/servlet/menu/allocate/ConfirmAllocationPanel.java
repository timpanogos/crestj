package org.opendof.tools.repository.interfaces.servlet.menu.allocate;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.opendof.tools.repository.interfaces.core.CoreController;
import org.opendof.tools.repository.interfaces.servlet.InterfaceRepository;
import org.opendof.tools.repository.interfaces.servlet.definition.Definition;
import org.opendof.tools.repository.interfaces.servlet.menu.allocate.AllocateInterfaceOptionPanel.AllocateOptionsForm;

public class ConfirmAllocationPanel extends Panel{
	private static final long serialVersionUID = -8011965548433860397L;
	
	private String interfaceId;
	private AjaxLink<String> allocatedLink;
	private Model<String> allocateMessage;
	private AjaxLink<String> closeButton;
	private ModalWindow window;

	public ConfirmAllocationPanel(String id, final ModalWindow window, final AllocateOptionsForm form, String allocateMessage) {
		super(id);
		
		this.window = window;
		
		this.allocateMessage = new Model<String>(allocateMessage);
		add(new Label("allocateMessage", this.allocateMessage));
		
		
		this.allocatedLink = (AjaxLink<String>) new AjaxLink<String>("allocatedLink"){
			private static final long serialVersionUID = 9060706640447251988L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				AllocateIdentifierOption repoOption = form.getModelObject();
				PageParameters params = new PageParameters();
				params.set(Definition.REPO_INDEX, repoOption.getSelectedValue());
				try {
					params.set(Definition.IID_INDEX, getCore().getURLIdentifier(repoOption.getSelectedValue(), interfaceId));
				} catch (Exception e) {
					params.set(Definition.IID_INDEX, interfaceId);
				}
				setResponsePage(Definition.class, params);
			}
		};
		
		add(allocatedLink);
		
		closeButton = new AjaxLink<String>("closeButton"){
			private static final long serialVersionUID = -7546678661678344696L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				window.close(target);
			}
		};
		closeButton.setBody(new Model<String>("Close"));
		add(closeButton);
	}
	
	private CoreController getCore(){
		return ((InterfaceRepository)getApplication()).getCoreController();
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
		allocatedLink.setBody(new Model<String>(interfaceId));
		
	}
	
	public void setMessages(String title, String allocateMessage, boolean isError){
		window.setTitle(new Model<String>(title));
		this.allocateMessage.setObject(allocateMessage);
		
		allocatedLink.setVisible(!isError);
	}
}
