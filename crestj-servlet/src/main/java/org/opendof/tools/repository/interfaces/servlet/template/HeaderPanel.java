package org.opendof.tools.repository.interfaces.servlet.template;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.opendof.tools.repository.interfaces.servlet.auth.LoginPage;
import org.opendof.tools.repository.interfaces.servlet.auth.ClientInformation;
import org.opendof.tools.repository.interfaces.servlet.auth.LogoutPage;
import org.opendof.tools.repository.interfaces.servlet.index.Index;

public class HeaderPanel extends Panel {
	private static final long serialVersionUID = -4736200814044302198L;
	
	BookmarkablePageLink<Object> login;
	BookmarkablePageLink<Object> logout;
	Label logoutLabel;

	public HeaderPanel(String id) {
		super(id);
		
		ClientInformation clientInformation = getClientInformation();
		String user = clientInformation.isAuthenticated() ? clientInformation.getName(): null;
		
		login = new BookmarkablePageLink<Object>("login", LoginPage.class);
		logout = new BookmarkablePageLink<Object>("logout", LogoutPage.class);
		logoutLabel = new Label("logoutLabel", "Welcome, " + user + " - ");
		
		boolean loggedIn = user != null && !user.isEmpty();
		logout.setVisible(loggedIn);
		logoutLabel.setVisible(loggedIn);
		login.setVisible(!loggedIn);
		
		add(new BookmarkablePageLink<>("homeURL", Index.class));
		add(login);
		add(logoutLabel);
		add(logout);
	}
		
	private ClientInformation getClientInformation(){
		return (ClientInformation)getWebSession().getClientInfo();
	}
}
