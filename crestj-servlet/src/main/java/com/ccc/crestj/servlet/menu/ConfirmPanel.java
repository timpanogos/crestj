package com.ccc.crestj.servlet.menu;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class ConfirmPanel extends Panel{
	private static final long serialVersionUID = 5002768027298558935L;
	
	protected Model<String> confirmMessage1Model;
	protected Model<String> confirmMessage2Model;
	protected AjaxLink<String> confirmButton;
	protected AjaxLink<String> cancelButton;
	
	public ConfirmPanel(final ModalWindow window, final ConfirmationHandler confirmationHandler, String message1, String message2, String confirmText, String cancelText) {
		super(window.getContentId());
		
		confirmMessage1Model = new Model<String>(message1);
		add(new Label("confirmationMessage1", confirmMessage1Model));
		
		confirmMessage2Model = new Model<String>(message2);
		add(new Label("confirmationMessage2", confirmMessage2Model));
		
		confirmButton = new AjaxLink<String>("confirmButton"){
			private static final long serialVersionUID = -7546678661678344696L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if(confirmationHandler != null){
					confirmationHandler.onConfirm(target);
				}
			}
		};
		confirmButton.setBody(new Model<String>(confirmText));
		add(confirmButton);
		
		cancelButton = new AjaxLink<String>("cancelButton"){
			private static final long serialVersionUID = 6862101028757230316L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if(confirmationHandler != null){
					confirmationHandler.onCancel(target);
				}
			}
		};
		cancelButton.setBody(new Model<String>(cancelText));
		add(cancelButton);
		
		window.setContent(this);
	}
	
	public void setMessages(String message1, String message2){
		confirmMessage1Model.setObject(message1);
		confirmMessage2Model.setObject(message2);
	}
	
	public String getMessage1(){
		return confirmMessage1Model.getObject();
	}
	
	public String getMessage2(){
		return confirmMessage2Model.getObject();
	}
	
	public void disableConfirmButton(){
		confirmButton.setVisible(false);
	}
	
	public void enableConfirmButton(){
		confirmButton.setVisible(true);
	}
	
	public void setCancelButtonText(String cancelButtonText){
		cancelButton.setBody(new Model<String>(cancelButtonText));
	}
}
