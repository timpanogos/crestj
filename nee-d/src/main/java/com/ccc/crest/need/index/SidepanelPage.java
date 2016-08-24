package com.ccc.crest.need.index;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.protocol.http.WebSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.crest.need.template.FooterPanel;
import com.ccc.crest.need.template.HeaderPanel;

@AuthorizeInstantiation("ADMIN")
public class SidepanelPage extends WebPage
{
    private static final long serialVersionUID = 1244571370667267003L;
    private Component headerPanel;
    private Component footerPanel;
    private MarkupContainer sidePanel;

    private static Logger log = LoggerFactory.getLogger(SidepanelPage.class);

    public SidepanelPage()
    {
        headerPanel = new HeaderPanel("headerPanel");
        footerPanel = new FooterPanel("footerPanel");
        sidePanel = new WebMarkupContainer("sidePanel");
        add(new Label("message", "Hello World!"));
//        AllocateInterfaceOptionPanel reservePanel = new AllocateInterfaceOptionPanel("allocateInterfaceOptionPanel");
//        sidePanel.add(reservePanel);
//        ViewAllocatedPanel viewAllocatedPanel = new ViewAllocatedPanel("viewAllocatedPanel");
//        sidePanel.add(viewAllocatedPanel);
//
//        viewAllocatedPanel.setVisible(getClientInformation().isAuthenticated());
//        reservePanel.setVisible(getClientInformation().isAuthenticated());
//        sidePanel.setVisible(getClientInformation().isAuthenticated());

        // AuthenticatedUser authUser = null;
        // if(getClientInformation().isAuthenticated())
        // authUser = new AuthenticatedUser(getClientInformation().getName(),
        // getClientInformation().getEmail(),
        // getClientInformation().getDescription(), null);
        //
        // List<InterfaceData> interfaces = null;
        // try {
        // interfaces = getCore().getAllInterfaces(authUser, null,
        // authUser.email, null, null);
        // } catch (Exception e) {
        // log.warn("Unable to get list of interfaces.", e);
        // interfaces = new ArrayList<InterfaceData>();
        // }

        add(headerPanel);
        add(footerPanel);
        add(sidePanel);

        WebMarkupContainer browseTable = new WebMarkupContainer("allocatedDiv");

        // final DataView<InterfaceData> dataView = new
        // InterfaceDataView("allocatedDataView", new
        // ListDataProvider<InterfaceData>(interfaces));

        int maxItemsPerPage = 30;
        // dataView.setItemsPerPage(maxItemsPerPage);
        // browseTable.add(dataView);

        // PagingNavigator navigator = new PagingNavigator("navigator",
        // dataView);
        // navigator.setVisible(interfaces.size() > maxItemsPerPage);
        // browseTable.add(navigator);

//        add(browseTable);
//
//        Label emptyMessage = new Label("emptyMessage", new Model<String>("You do not have any allocated interfaces."));
//        add(emptyMessage);
        // browseTable.setVisible(!interfaces.isEmpty());
        // emptyMessage.setVisible(interfaces.isEmpty());
    }

    // private CoreController getCore(){
    // return ((NeedServer)getApplication()).getCoreController();
    // }

//    private CrestClientInformation getClientInformation()
//    {
//        return (CrestClientInformation) WebSession.get().getClientInfo();
//    }

//    private class InterfaceDataView extends DataView<InterfaceData>
//    {
//        private static final long serialVersionUID = 2527796233926912760L;
//
//        protected InterfaceDataView(String id, IDataProvider<InterfaceData> dataProvider)
//        {
//            super(id, dataProvider);
//        }
//
//        @Override
//        protected void populateItem(Item<InterfaceData> item)
//        {
//            final InterfaceData interfaceData = item.getModelObject();
//            PageParameters definitionParameters = new PageParameters();
//            definitionParameters.set(Definition.REPO_INDEX, interfaceData.getRepoType());
//            String iid = "";
//            try
//            {
//                iid = getCore().getURLIdentifier(interfaceData.getRepoType(), interfaceData.iid);
//            } catch (Exception e)
//            {
//                log.warn("Failed to encode iid: " + interfaceData.iid, e);
//            }
//
//            definitionParameters.set(Definition.IID_INDEX, iid);
//            String version = interfaceData.version;
//            if (version != null && !version.isEmpty())
//            {
//                definitionParameters.set(Definition.VERSION_INDEX, version);
//            }
//            String name = interfaceData.getName();
//            BookmarkablePageLink<Definition> interfaceLink = new BookmarkablePageLink<Definition>("interfaceLink", Definition.class, definitionParameters);
//            interfaceLink.add(new Label("interfaceID", interfaceData.iid + (name == null ? "" : " - " + name)));
//
//            item.add(interfaceLink);
//        }
//    }
}
