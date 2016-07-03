package com.ccc.crestj.servlet.menu;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;

/**
 * An AjaxLink that opens a confirmation window.
 */
public class FormConfirmationLink extends AjaxSubmitLink{
	private static final long serialVersionUID = -8728060668356033289L;
	
	private ModalWindow confirmWindow;
	
	public FormConfirmationLink(String id, ModalWindow confirmWindow, final Form<?> form) {
		super(id, form);
		this.confirmWindow = confirmWindow;
	}
	
	@Override
	protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form)
	{
		confirmWindow.show(target);
	}
}
