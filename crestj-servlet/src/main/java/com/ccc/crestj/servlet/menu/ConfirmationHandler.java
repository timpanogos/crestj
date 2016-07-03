package com.ccc.crestj.servlet.menu;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * A callback interface for confirmation windows to notify the webpage whether the action has been confirmed or cancelled.
 *
 */
public interface ConfirmationHandler extends Serializable {
	
	/**
	 * Called when the action is confirmed.
	 */
	void onConfirm(AjaxRequestTarget target);
	
	/**
	 * Called when the action is cancelled.
	 */
	void onCancel(AjaxRequestTarget target);
	
}
