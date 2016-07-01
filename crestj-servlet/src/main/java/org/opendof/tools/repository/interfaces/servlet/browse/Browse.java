package org.opendof.tools.repository.interfaces.servlet.browse;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.opendof.tools.repository.interfaces.core.AuthenticatedUser;
import org.opendof.tools.repository.interfaces.core.CoreController;
import org.opendof.tools.repository.interfaces.da.InterfaceData;
import org.opendof.tools.repository.interfaces.servlet.InterfaceRepository;
import org.opendof.tools.repository.interfaces.servlet.auth.ClientInformation;
import org.opendof.tools.repository.interfaces.servlet.browse.filter.Filter;
import org.opendof.tools.repository.interfaces.servlet.browse.filter.FilterForm;
import org.opendof.tools.repository.interfaces.servlet.definition.Definition;
import org.opendof.tools.repository.interfaces.servlet.menu.ViewAllocatedPanel;
import org.opendof.tools.repository.interfaces.servlet.menu.allocate.AllocateInterfaceOptionPanel;
import org.opendof.tools.repository.interfaces.servlet.template.FooterPanel;
import org.opendof.tools.repository.interfaces.servlet.template.HeaderPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Browse extends WebPage {
	private static final long serialVersionUID = 1244571370667267003L;
	private Component headerPanel;
	private Component footerPanel;
	private MarkupContainer sidePanel;
	
	private String repo = null;
	private String submitter = null;
	private String publish = null;
	
	private static Logger log = LoggerFactory.getLogger(Browse.class);
	
	public Browse(){
		this(null);
	}

	public Browse(final PageParameters parameters){		
		headerPanel = new HeaderPanel("headerPanel");
		footerPanel = new FooterPanel("footerPanel");
		sidePanel = new WebMarkupContainer("sidePanel");
		AllocateInterfaceOptionPanel reservePanel = new AllocateInterfaceOptionPanel("allocateInterfaceOptionPanel");
		sidePanel.add(reservePanel);
		ViewAllocatedPanel viewAllocatedPanel = new ViewAllocatedPanel("viewAllocatedPanel");
		sidePanel.add(viewAllocatedPanel);
		
		viewAllocatedPanel.setVisible(getClientInformation().isAuthenticated());		
		reservePanel.setVisible(getClientInformation().isAuthenticated());
		sidePanel.setVisible(getClientInformation().isAuthenticated());
		
		if(parameters != null){
			repo = parameters.get("repo").toString();
			submitter = parameters.get("submitter").toString();
			publish = parameters.get("publish").toString();
			
			try {
				if(repo != null) repo = URLDecoder.decode(repo, "UTF-8");
				if(submitter != null) submitter = URLDecoder.decode(submitter, "UTF-8");
				if(publish != null) publish = URLDecoder.decode(publish, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.debug("Failed to parse parameters.", e);
			}
		}
		
		Boolean isPublished = null;
		if (publish != null && publish.toLowerCase().equals("published")){
			isPublished = true;
		}
		if (publish != null && publish.toLowerCase().equals("working")){
			isPublished = false;
		}
		
		AuthenticatedUser authUser = null;
		if(getClientInformation().isAuthenticated())
		    authUser = new AuthenticatedUser(getClientInformation().getName(), getClientInformation().getEmail(), getClientInformation().getDescription(), null); 
		
		List<InterfaceData> interfaces = null;
		try {
			interfaces = getCore().getAllInterfaces(authUser, repo, submitter, null, isPublished);
		} catch (Exception e) {
			log.warn("Unable to get list of interfaces.", e);
			interfaces = new ArrayList<InterfaceData>();
		}
		
		interfaces.removeIf(new Predicate<InterfaceData>() {
			@Override
			public boolean test(InterfaceData t) {
				return t.xml == null;
			}
		});
		
		add(headerPanel);
		add(footerPanel);
		add(sidePanel);
		Filter filter = new Filter();
		if(publish == null || publish.isEmpty()){
			filter.setPublish(Filter.PUBLISHED_AND_WORKING);
		} else{
			filter.setPublish(publish);
		}
		filter.setRepo(repo);
		filter.setSubmitter(submitter);
		Form<Filter> form = new FilterForm("filterForm", new CompoundPropertyModel<Filter>(filter));
		
		final DataView<InterfaceData> dataView = new InterfaceDataView("browseTable", new ListDataProvider<InterfaceData>(interfaces));
		
		int maxItemsPerPage = 30;
		dataView.setItemsPerPage(maxItemsPerPage);
		add(dataView);
		
		PagingNavigator navigator = new PagingNavigator("navigator", dataView);
		navigator.setVisible(interfaces.size() > maxItemsPerPage);
		add(navigator);

		add(form);
	}
	
	private CoreController getCore(){
		return ((InterfaceRepository)getApplication()).getCoreController();
	}
	
	private ClientInformation getClientInformation(){
		return (ClientInformation)WebSession.get().getClientInfo();
	}
	
	private class InterfaceDataView extends DataView<InterfaceData>{
		private static final long serialVersionUID = 2527796233926912760L;

		protected InterfaceDataView(String id, IDataProvider<InterfaceData> dataProvider) {
			super(id, dataProvider);
		}

		@Override
		protected void populateItem(Item<InterfaceData> item) {
			final InterfaceData interfaceData = item.getModelObject();
			PageParameters definitionParameters = new PageParameters();
			definitionParameters.set(Definition.REPO_INDEX, interfaceData.getRepoType());
			String iid = "";
			try {
				iid = getCore().getURLIdentifier(interfaceData.getRepoType(), interfaceData.iid);
			} catch (Exception e) {
				log.warn("Failed to encode iid: " + interfaceData.iid, e);
			}
			
			definitionParameters.set(Definition.IID_INDEX, iid);
			String version = interfaceData.version;
			if(version != null && !version.isEmpty()){
				definitionParameters.set(Definition.VERSION_INDEX, version);
			}				
			BookmarkablePageLink<Definition> nameLink = new BookmarkablePageLink<Definition>("nameLink", Definition.class, definitionParameters);
			nameLink.add(new Label("nameLabel", interfaceData.getName()));
			
			item.add(nameLink);	
			item.add(new Label("interfaceID", interfaceData.iid));	
			item.add(new Label("publish", interfaceData.publish ? "Published" : "Working"));	
			item.add(new Label("repo", interfaceData.getRepoType()));       
		}	
	}
}
