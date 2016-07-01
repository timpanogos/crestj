package org.opendof.tools.repository.interfaces.servlet.menu;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.opendof.tools.repository.interfaces.servlet.allocated.AllocatedPage;

public class ViewAllocatedPanel extends Panel {
	private static final long serialVersionUID = -4736200814044302198L;
	
	public ViewAllocatedPanel(String id) {
		super(id);
		WebMarkupContainer viewAllocatedContainer = new WebMarkupContainer("viewAllocatedContainer");
		viewAllocatedContainer.add(new AjaxEventBehavior("click") {
			private static final long serialVersionUID = -6598019866136034362L;

			@Override
			protected void onEvent(AjaxRequestTarget target) {
				setResponsePage(AllocatedPage.class);
			}
		});
		
		add(viewAllocatedContainer);
	}
}
