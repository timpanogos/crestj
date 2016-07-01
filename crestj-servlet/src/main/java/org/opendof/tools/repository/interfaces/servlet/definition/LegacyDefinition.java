package org.opendof.tools.repository.interfaces.servlet.definition;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created to support legacy URL path.
 *
 */
public class LegacyDefinition extends WebPage {
	private static final long serialVersionUID = 1004205004721104125L;

	public LegacyDefinition(PageParameters parameters) {
		super(parameters);
		throw new RestartResponseAtInterceptPageException(Definition.class, parameters);
	}
}
