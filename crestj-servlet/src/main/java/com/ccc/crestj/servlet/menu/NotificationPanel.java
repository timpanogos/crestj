package com.ccc.crestj.servlet.menu;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class NotificationPanel extends Panel{
	private static final long serialVersionUID = 5002768027298558935L;
	
	protected Model<String> confirmMessage1Model;
	protected Model<String> confirmMessage2Model;
	protected AjaxLink<String> closeButton;
	private ModalWindow window;
	
	public NotificationPanel(final ModalWindow window, String message1, String message2) {
		super(window.getContentId());
		this.window = window;
		
		confirmMessage1Model = new Model<String>(message1);
		add(new Label("confirmationMessage1", confirmMessage1Model));
		
		confirmMessage2Model = new Model<String>(message2);
		add(new Label("confirmationMessage2", confirmMessage2Model));
		
		closeButton = new AjaxLink<String>("closeButton"){
			private static final long serialVersionUID = -7546678661678344696L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				window.close(target);
			}
		};
		closeButton.setBody(new Model<String>("Close"));
		add(closeButton);
		
		window.setContent(this);
	}
	
	public void setMessages(String title, String message1, String message2){
		window.setTitle(new Model<String>(title));
		confirmMessage1Model.setObject(message1);
		confirmMessage2Model.setObject(message2);
	}
	
	public String getMessage1(){
		return confirmMessage1Model.getObject();
	}
	
	public String getMessage2(){
		return confirmMessage2Model.getObject();
	}
}
