/*
**  Copyright (c) 2016, Chad Adams.
**
**  This program is free software: you can redistribute it and/or modify
**  it under the terms of the GNU Lesser General Public License as 
**  published by the Free Software Foundation, either version 3 of the 
**  License, or any later version.
**
**  This program is distributed in the hope that it will be useful,
**  but WITHOUT ANY WARRANTY; without even the implied warranty of
**  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
**  GNU General Public License for more details.

**  You should have received copies of the GNU GPLv3 and GNU LGPLv3
**  licenses along with this program.  If not, see http://www.gnu.org/licenses
*/
package com.ccc.crest.need.index;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.protocol.http.WebSession;

import com.ccc.crest.need.template.FooterPanel;
import com.ccc.crest.need.template.HeaderPanel;
import com.ccc.servlet.wicket.WicketClientInfo;

@SuppressWarnings("javadoc")
public class Index extends WebPage
{
    static final long serialVersionUID = 5422903963692085199L;
    private Component headerPanel;
    private Component footerPanel;
    private MarkupContainer sidePanel;
//    private WebMarkupContainer gcseDiv;
//    private Label gcseLabel;

//    private static Logger log = LoggerFactory.getLogger(Index.class);

    public Index()
    {
        // Properties properties = (Properties) ((NeedServer) getApplication()).getServletContext().getAttribute(CoreController.PropertiesKey);

        headerPanel = new HeaderPanel("headerPanel");
        footerPanel = new FooterPanel("footerPanel");
        sidePanel = new WebMarkupContainer("sidePanel");
        add(new Label("nonPriviledgedMessage", "Everyone can see this"));
        sidePanel.add(new Label("priviledged", "Only authenticated users can see this"));
        
//        AllocateInterfaceOptionPanel reservePanel = new AllocateInterfaceOptionPanel("allocateInterfaceOptionPanel");
//        sidePanel.add(reservePanel);
//        ViewAllocatedPanel viewAllocatedPanel = new ViewAllocatedPanel("viewAllocatedPanel");
//        sidePanel.add(viewAllocatedPanel);

//        reservePanel.setVisible(getClientInformation().isAuthenticated());
//        viewAllocatedPanel.setVisible(getClientInformation().isAuthenticated());
//        sidePanel.setVisible(getClientInformation().isAuthenticated());

        add(headerPanel);
        add(footerPanel);
        add(sidePanel);

        boolean isAuthenticated = ((WicketClientInfo)WebSession.get().getClientInfo()).isAuthenticated();
        sidePanel.setVisible(isAuthenticated);
        

//        InterfaceData interfaceData = new InterfaceData("[1:{01}]", "1", "opendof");
//        add(new InterfaceRequestForm("interfaceRequestForm", new CompoundPropertyModel<InterfaceData>(interfaceData)));
//
//        add(new BookmarkablePageLink<>("browse", Browse.class));
//
//        gcseDiv = new WebMarkupContainer("gcseDiv");
        // String cx = properties.getProperty(NeedServer.GoogleCSEcxKey);
//        StringBuilder sb = new StringBuilder();
//        sb.append("<script>");
//        sb.append("(function() {");
//        // sb.append("var cx = '" + cx + "';");
//        sb.append("var gcse = document.createElement('script');");
//        sb.append("gcse.type = 'text/javascript';");
//        sb.append("gcse.async = true;");
//        sb.append("gcse.src = 'https://cse.google.com/cse.js?cx=' + cx;");
//        sb.append("var s = document.getElementsByTagName('script')[0];");
//        sb.append("s.parentNode.insertBefore(gcse, s);");
//        sb.append("})();");
//        sb.append("</script>");
//        sb.append("<gcse:search></gcse:search>");
//        gcseLabel = new Label("gcseLabel", sb.toString());
//        gcseLabel.setEscapeModelStrings(false);
//        gcseDiv.add(gcseLabel);
//        add(gcseDiv);
    }

//    private CoreController getCore()
//    {
//        return ((NeedServer) getApplication()).getCoreController();
//    }

//    private CrestClientInformation getClientInformation()
//    {
//        return (CrestClientInformation) WebSession.get().getClientInfo();
//    }

//    private class InterfaceRequestForm extends Form<InterfaceData>
//    {
//        private static final long serialVersionUID = -36610284781844624L;
//
//        private DropDownChoice<String> repoDropDownChoice;
//        private Label versionLabel;
//        private TextField<String> versionTextField;
//        private TextField<String> iidTextField;
//        private WebMarkupContainer versionRow;
//
//        public InterfaceRequestForm(String id, IModel<InterfaceData> model)
//        {
//            super(id, model);
//
//            List<String> repos = new ArrayList<String>();
////            repos.addAll(getCore().getRepositoryList());
//
//            // TODO: Make this configurable somehow?
////            String defaultRepo;
////            if (repos.contains("opendof"))
////                defaultRepo = "opendof";
////            else
////                defaultRepo = repos.get(0);
////
////            model.getObject().setRepoType(defaultRepo);
//
//            repoDropDownChoice = new RepoDropDownChoice("repoField", new PropertyModel<String>(model, "repoType"), repos);
//            iidTextField = new TextField<String>("iidField", new PropertyModel<String>(model, "iid"), String.class);
//            versionRow = new WebMarkupContainer("versionRow");
//            versionLabel = new Label("versionLabel", "Version: ");
//            versionTextField = new TextField<String>("versionField", new PropertyModel<String>(model, "version"), String.class);
//
//            setVersionVisibility(model.getObject().getRepoType());
//
//            add(new FeedbackPanel("feedback"));
//            add(new Button("submit"));
//            add(repoDropDownChoice);
//            add(iidTextField);
//            versionRow.add(versionLabel);
//            versionRow.add(versionTextField);
//            add(versionRow);
//        }
//
//        private void setVersionVisibility(String repo)
//        {
//            try
//            {
//                if (true/*getCore().getVersionSupported(repo)*/)
//                {
//                    versionRow.setVisible(true);
//                } else
//                {
//                    versionRow.setVisible(false);
//                }
//            } catch (Exception e)
//            {
//                log.warn("Failed to check version support for repo " + repo + " - " + e, e);
//            }
//        }
//
//        @Override
//        protected void onValidate()
//        {
//            super.onValidate();
//
//            String repo = repoDropDownChoice.getConvertedInput();
//            String iid = iidTextField.getConvertedInput();
//            String version = versionTextField.getConvertedInput();
//
//            InterfaceData interfaceData = null;
//            AuthenticatedUser authUser = null;
//            if (getClientInformation().isAuthenticated())
//                authUser = new AuthenticatedUser(getClientInformation().getVerifyData().CharacterName, null, null, null);
//            try
//            {
//                interfaceData = null; //getCore().getInterface(authUser, repo, iid, version);
//            } 
////            catch (NotFoundException e)
////            {
////                String msg = getString("invalid_interface_identifier");
////                error(msg);
////                return;
////            } 
//            catch (Exception e)
//            {
//                log.debug("Failed to validate {} interface id {} - {}", repo, iid, e, e);
//            }
//
//            if (interfaceData == null)
//            {
//                error(getString("invalid_interface_identifier"));
//            }
//        }
//
//        @Override
//        public void onSubmit()
//        {
//            String repo = repoDropDownChoice.getDefaultModelObjectAsString();
//            String iid = iidTextField.getDefaultModelObjectAsString();
//            String version = versionTextField.getDefaultModelObjectAsString();
//
//            try
//            {
//                iid = null; //getCore().getURLIdentifier(repo, iid);
//                repo = URLEncoder.encode(repo, "UTF-8");
//                if (version != null)
//                    version = URLEncoder.encode(version, "UTF-8");
//            } catch (Exception e)
//            {
//                log.debug("Failed to parse parameters.", e);
//            }
//
//            boolean versionSupported;
//            try
//            {
//                versionSupported = true; // getCore().getVersionSupported(repo);
//            } catch (Exception e)
//            {
//                log.warn("failed to check version support for repo " + repo + " - " + e, e);
//                versionSupported = false;
//            }
//
//            PageParameters parameters = new PageParameters();
//            parameters.set(Definition.REPO_INDEX, repo);
//            parameters.set(Definition.IID_INDEX, iid);
//            if (versionSupported && version != null && !version.isEmpty())
//            {
//                parameters.set(Definition.VERSION_INDEX, version);
//            }
//            setResponsePage(Definition.class, parameters);
//        }
//
//        private CrestClientInformation getClientInformation()
//        {
//            return (CrestClientInformation) WebSession.get().getClientInfo();
//        }
//
//        private class RepoDropDownChoice extends DropDownChoice<String>
//        {
//            private static final long serialVersionUID = -3712082508263393331L;
//
//            public RepoDropDownChoice(String id, IModel<String> model, List<? extends String> choices)
//            {
//                super(id, model, choices);
//            }
//
//            @Override
//            protected boolean wantOnSelectionChangedNotifications()
//            {
//                return true;
//            }
//
//            @Override
//            protected void onSelectionChanged(final String newSelection)
//            {
//                setVersionVisibility(newSelection);
//            }
//        }
//    }
}
