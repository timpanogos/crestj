package org.opendof.tools.repository.interfaces.servlet.template;

import java.util.Properties;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.opendof.tools.repository.interfaces.core.CoreController;
import org.opendof.tools.repository.interfaces.servlet.InterfaceRepository;

public class FooterPanel extends Panel {
	private static final long serialVersionUID = -4736200814044302198L;

	public FooterPanel(String id) {
		super(id);
		Properties properties = (Properties) ((InterfaceRepository) getApplication()).getServletContext().getAttribute(CoreController.PropertiesKey);
		String copyrightYear = properties.getProperty(InterfaceRepository.CopyrightYearKey);
		String copyrightowner = properties.getProperty(InterfaceRepository.CopyrightOwnerKey);
		add(new Label("copyright", copyrightYear + " " + copyrightowner));
	}
}
