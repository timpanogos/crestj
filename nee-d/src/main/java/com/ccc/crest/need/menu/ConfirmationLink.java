package com.ccc.crest.need.menu;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

/**
 * An AjaxLink that opens a confirmation window.
 */
public class ConfirmationLink extends AjaxLink<String>{
	private static final long serialVersionUID = -8728060668356033289L;
	
	private ModalWindow confirmWindow;
	
	public ConfirmationLink(String id, ModalWindow confirmWindow) {
		super(id);
		this.confirmWindow = confirmWindow;
	}

	@Override
	public void onClick(final AjaxRequestTarget target) {
		confirmWindow.show(target);
	}
}
