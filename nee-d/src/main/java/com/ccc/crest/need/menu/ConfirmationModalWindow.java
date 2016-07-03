package com.ccc.crest.need.menu;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

public class ConfirmationModalWindow extends ModalWindow{
	private static final long serialVersionUID = 8356335537617558681L;

	public ConfirmationModalWindow(String id, String title, boolean showUnloadConfirmation) {
		super(id);
		this.setTitle(title);
		this.setWidthUnit("em");
		this.setInitialWidth(40);
		this.setHeightUnit("em");
		this.setInitialHeight(13);
		this.setResizable(false);
		this.showUnloadConfirmation(showUnloadConfirmation);
	}
}
