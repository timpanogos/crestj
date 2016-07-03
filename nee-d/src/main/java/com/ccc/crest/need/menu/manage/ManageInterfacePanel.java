package com.ccc.crest.need.menu.manage;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.value.AttributeMap;
import org.apache.wicket.util.value.IValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.crest.need.definition.Definition;
import com.ccc.crest.need.index.Index;
import com.ccc.crest.need.menu.ConfirmPanel;
import com.ccc.crest.need.menu.ConfirmationHandler;
import com.ccc.crest.need.menu.ConfirmationLink;
import com.ccc.crest.need.menu.ConfirmationModalWindow;
import com.ccc.crest.need.menu.FormConfirmationLink;
import com.ccc.crest.need.menu.NotificationPanel;
import com.ccc.crest.servlet.auth.CrestClientInformation;

public class ManageInterfacePanel extends Panel
{
    private static final long serialVersionUID = -4736200814044302198L;

    private static String publicOption = "Public";
    private static String privateOption = "Private";

    // private InterfaceData interfaceData;
    private ModalWindow publishWindow;
    private ConfirmPanel publishConfirmPanel;
    private ModalWindow accessWindow;
    private AccessConfirmPanel accessConfirmPanel;
    private ModalWindow submitWindow;
    private ConfirmPanel submitConfirmPanel;
    private FileUpload uploadedFile;
    private ModalWindow deleteWindow;
    private ConfirmPanel deleteConfirmPanel;
    private AccessRadioChoice accessChoice;
    // private AuthenticatedUser authUser;
    private ModalWindow notificationWindow;
    private NotificationPanel notificationPanel;

    private boolean currentPublish;
    private String currentAccess;

    private IModel<String> publishedLabelModel;
    private Label publishedLabel;
    private ConfirmationLink publishButton;
    private ConfirmationLink visibilityButton;
    private FormConfirmationLink submitButton;
    private ConfirmationLink deleteButton;

    FileUploadField fileUploadField;

    private static Logger log = LoggerFactory.getLogger(ManageInterfacePanel.class);

    public ManageInterfacePanel(String id)
    {
        super(id);
    }
    // public ManageInterfacePanel(String id, InterfaceData interfaceData) {
    // super(id);
    //// this.interfaceData = interfaceData;
    // this.currentAccess = interfaceData.accessGroup.email.equals(DataAccessor.PrivateGroupEmail) ? privateOption : publicOption;
    // this.currentPublish = interfaceData.publish;
    //
    // addPublishOptions();
    // addAccessOptions();
    // addSubmitOptions();
    // addDeleteOptions();
    //
    // notificationWindow = new ConfirmationModalWindow("notificationWindow", "Error", false);
    // notificationPanel = new NotificationPanel(notificationWindow, "", "");
    // add(notificationWindow);
    //
    //
    //// if(getClientInformation().isAuthenticated())
    //// authUser = new AuthenticatedUser(getClientInformation().getName(), getClientInformation().getEmail(), getClientInformation().getDescription(), null);
    // }

    private void addAccessOptions()
    {
        IModel<List<String>> accessChoices = new ListModel<String>(Arrays.asList(privateOption, publicOption));
        accessChoice = new AccessRadioChoice("accessChoice", accessChoices);
        add(accessChoice);
        accessChoice.setEnabled(!(currentAccess.equals(publicOption) && currentPublish));

        accessWindow = new ConfirmationModalWindow("confirmAccess", "Access Change", true);

        visibilityButton = new ConfirmationLink("visibilityButton", accessWindow);
        add(visibilityButton);

        visibilityButton.setVisible(!(currentPublish && currentAccess.equals(publicOption)));

        AccessConfirmationHandler accessConfirmationHandler = new AccessConfirmationHandler();
        accessConfirmPanel = new AccessConfirmPanel(accessWindow, accessConfirmationHandler, "", "", "OK", "Close", currentAccess);
        accessWindow.setWindowClosedCallback(accessConfirmationHandler);

        add(accessWindow);
    }

    private void addSubmitOptions()
    {

        submitWindow = new ConfirmationModalWindow("confirmSubmit", "Submit InterfaceDefinition", false);
        SubmitConfirmationHandler submitConfirmationHandler = new SubmitConfirmationHandler();
        submitConfirmPanel = new ConfirmPanel(submitWindow, submitConfirmationHandler, "", "", "Submit", "Cancel");
        submitWindow.setWindowClosedCallback(submitConfirmationHandler);

        Form<Void> submitForm = new SubmitForm("submitForm");

        submitForm.setMultiPart(true);
        submitForm.setMaxSize(Bytes.megabytes(2));

        fileUploadField = new FileUploadField("upload");
        submitForm.add(fileUploadField);
        submitButton = new FormConfirmationLink("submitButton", submitWindow, submitForm);
        submitForm.add(submitButton);
        add(submitForm);

        visibilityButton.setVisible(!(currentPublish && currentAccess.equals(publicOption)));

        add(submitWindow);
    }

    private void addPublishOptions()
    {
        publishWindow = new ConfirmationModalWindow("confirmPublish", "Publish Interface", true);

        PublishConfirmationHandler publishConfirmationHandler = new PublishConfirmationHandler();
        publishWindow.setWindowClosedCallback(publishConfirmationHandler);
        publishConfirmPanel = new ConfirmPanel(publishWindow, publishConfirmationHandler, null, null, "Publish", "Cancel");
        setPublishWindowMessages();

        String publishedMessage = "This interface is " + (currentPublish == true ? "published." : "working: ");

        publishedLabelModel = new Model<String>(publishedMessage);
        publishedLabel = new Label("publishedLabel", publishedLabelModel);
        publishedLabel.setOutputMarkupId(true);
        add(publishedLabel);
        publishButton = new ConfirmationLink("publishButton", publishWindow);
        // publishButton.setVisible(!currentPublish && interfaceData.xml != null);

        add(publishButton);
        add(publishWindow);
    }

    private void addDeleteOptions()
    {
        deleteWindow = new ConfirmationModalWindow("confirmDelete", "Delete Interface", false);
        DeleteConfirmationHandler deleteConfirmationHandler = new DeleteConfirmationHandler();
        deleteWindow.setWindowClosedCallback(deleteConfirmationHandler);

        // String msg1 = "Are you sure you want to delete " + interfaceData.iid + "?";
        String msg2 = "After deleting an interface you will no longer have access to the interface identifier and must allocate another.";
        // deleteConfirmPanel = new ConfirmPanel(deleteWindow, deleteConfirmationHandler, msg1, msg2, "Delete", "Cancel");

        deleteButton = new ConfirmationLink("deleteButton", deleteWindow);
        deleteButton.setVisible(!currentPublish);

        add(deleteButton);
        add(deleteWindow);
    }

    private void setPublishWindowMessages()
    {
        // String confirmMsg1 = "Are you sure you want to publish " + interfaceData.iid + "?";
        String confirmMsg2 = "Published interfaces cannot be deleted. Changes to published interface definitions " + "are restricted to documentation, so ensure that you have completed your organization's " + "review process before publishing.";

        if (currentAccess.equals(publicOption))
        {
            confirmMsg2 = confirmMsg2 + " This is a public interface. Public interfaces may not be made private once published.";
        }
        // publishConfirmPanel.setMessages(confirmMsg1, confirmMsg2);
    }

//    private CoreController getCore()
//    {
//        return ((NeedServer) getApplication()).getCoreController();
//    }

    private CrestClientInformation getClientInformation()
    {
        return (CrestClientInformation) WebSession.get().getClientInfo();
    }

    private class SubmitForm extends Form<Void>
    {

        public SubmitForm(String id)
        {
            super(id);
        }

        private static final long serialVersionUID = -4431707972578398057L;

        @Override
        protected void onSubmit()
        {
            uploadedFile = fileUploadField.getFileUpload();

            String msg1;
            String msg2;
            if (uploadedFile != null)
            {
                msg1 = "Are you sure you want to update the interface definition to the contents of " + uploadedFile.getClientFileName() + "?";
                msg2 = "No change history is kept. You will not be able to revert this change.";
                submitConfirmPanel.enableConfirmButton();
                submitConfirmPanel.setCancelButtonText("Cancel");
            } else
            {
                msg1 = "No file selected.";
                msg2 = "";
                submitConfirmPanel.disableConfirmButton();
                submitConfirmPanel.setCancelButtonText("Close");
            }
            submitConfirmPanel.setMessages(msg1, msg2);
        }
    }

    private class PublishConfirmationHandler implements ConfirmationHandler, WindowClosedCallback
    {
        private static final long serialVersionUID = 2316940336271416989L;

        private boolean isError = false;
        private String errorMessage = null;

        @Override
        public void onConfirm(AjaxRequestTarget target)
        {
            try
            {
//                getCore().publishInterface(authUser, interfaceData.getRepoType(), interfaceData.iid, interfaceData.version);
//                log.debug("Published interface " + interfaceData.iid);
                onInterfaceUpdate(target, true, currentAccess);
            } catch (Exception e)
            {
//                log.debug("Failed to publish interface " + interfaceData.iid + " - " + e, e);
                isError = true;
                errorMessage = e.toString();
            } finally
            {
                publishWindow.close(target);
            }
        }

        @Override
        public void onCancel(AjaxRequestTarget target)
        {
            publishWindow.close(target);
        }

        @Override
        public void onClose(AjaxRequestTarget target)
        {
            if (isError)
            {
                notificationPanel.setMessages("Error", "Failed to publish interface.", errorMessage);
                notificationWindow.show(target);
            }
            isError = false;
        }
    }

    private class SubmitConfirmationHandler implements ConfirmationHandler, WindowClosedCallback
    {
        private static final long serialVersionUID = 8490632423427084563L;

        private boolean isError = false;
        private String errorMessage = null;

        @Override
        public void onConfirm(AjaxRequestTarget target)
        {
            try
            {
                if (uploadedFile != null)
                {
                    String xml = new String(uploadedFile.getBytes());
//                    getCore().updateInterface(authUser, interfaceData.getRepoType(), interfaceData.iid, xml, interfaceData.version, null, null, null);
                }
//                log.debug("Submitted interface definiton for " + interfaceData.iid);
                onInterfaceUpdate(target, currentPublish, currentAccess);
                setResponsePage(Definition.class, getPage().getPageParameters());
            }
//            catch (ImmutableException ie)
//            {
//                isError = true;
//                errorMessage = "Attempting to modify an immutable element/attribute of a published interface. " + "Only metadata may be modified on published interfaces.";
//            } 
            catch (Exception e)
            {
                isError = true;
//                log.debug("Failed to submit interface definition for " + interfaceData.iid + " - " + e, e);
                errorMessage = e.toString();
            } finally
            {
                submitWindow.close(target);
            }
        }

        @Override
        public void onCancel(AjaxRequestTarget target)
        {
            submitWindow.close(target);
        }

        @Override
        public void onClose(AjaxRequestTarget target)
        {
            if (isError)
            {
                notificationPanel.setMessages("Error", "Failed to update interface definitions.", errorMessage);
                notificationWindow.show(target);
            }
            isError = false;
        }
    }

    private void onInterfaceUpdate(AjaxRequestTarget target, boolean publish, String visibility)
    {
        currentPublish = publish;
        currentAccess = visibility;

        // Publish Options
        if (currentPublish)
        {
            publishedLabelModel.setObject("This interface is published.");
        }
//        publishButton.setVisible(!currentPublish && interfaceData.xml != null);
        target.add(publishButton);
        setPublishWindowMessages();

        // Visibility options
        accessConfirmPanel.updateMessage();
        accessChoice.setEnabled(!(currentAccess.equals(publicOption) && currentPublish));
        target.add(accessChoice);
        visibilityButton.setVisible(!(currentPublish && currentAccess.equals(publicOption)));
        target.add(visibilityButton);

        // Delete Options
        deleteButton.setVisible(!currentPublish);
        target.add(deleteButton);
    }

    private class AccessConfirmPanel extends ConfirmPanel
    {
        private static final long serialVersionUID = 840919396153656322L;

        private String selectedAccess;

        public AccessConfirmPanel(ModalWindow window, ConfirmationHandler confirmationHandler, String confirmationMsg1, String confirmationMsg2, String confirmText, String cancelText, String selectedAccess)
        {
            super(window, confirmationHandler, confirmationMsg1, confirmationMsg2, confirmText, cancelText);

            setAccess(selectedAccess);
        }

        public void updateMessage()
        {
            String msg1;
            String msg2;
            if (currentAccess.equals(selectedAccess))
            {
                msg1 = "Interface is already set to " + selectedAccess;
                msg2 = "";
                disableConfirmButton();
                setCancelButtonText("Close");
            } else if (selectedAccess.equals(publicOption))
            {
                msg1 = "Interface will be set to public.";
                msg2 = "Public interfaces may be viewable by anyone.";
                if (currentPublish)
                {
                    msg2 += " This interface is published. Once made public, published interfaces cannot be made private again.";
                }
                enableConfirmButton();
                setCancelButtonText("Cancel");
            } else
            {
                msg1 = "Interface will be set to private.";
                msg2 = "Private interfaces may only be viewed by you.";
                enableConfirmButton();
                setCancelButtonText("Cancel");
            }

            setMessages(msg1, msg2);
        }

        public void setAccess(String access)
        {
            selectedAccess = access;
            updateMessage();
        }

        public String getSelectedAccess()
        {
            return selectedAccess;
        }
    }

    private class AccessRadioChoice extends RadioChoice<String>
    {
        private static final long serialVersionUID = -498306121179292730L;

        public AccessRadioChoice(String id, IModel<? extends List<? extends String>> choices)
        {
            super(id, new Model<String>(currentAccess), choices);
        }

        @Override
        protected boolean wantOnSelectionChangedNotifications()
        {
            return true;
        }

        @Override
        protected void onSelectionChanged(String newSelection)
        {
            accessConfirmPanel.setAccess(newSelection);
        }

        @Override
        protected IValueMap getAdditionalAttributesForLabel(int index, String choice)
        {
            if (choice.equals(currentAccess))
            {
                AttributeMap attributeMap = new AttributeMap();
                attributeMap.put("style", "font-style: italic;");
                return attributeMap;
            }
            return null;
        }
    }

    private class AccessConfirmationHandler implements ConfirmationHandler, WindowClosedCallback
    {
        private static final long serialVersionUID = 6718630523755175586L;

        private boolean isError = false;
        private String errorMessage = null;

        @Override
        public void onConfirm(AjaxRequestTarget target)
        {
            String selectedAccess = accessConfirmPanel.getSelectedAccess();
            try
            {
//                SubmitterData accessGroup;
//                if (selectedAccess.equals(privateOption))
//                {
//                    accessGroup = DataAccessor.PrivateGroup;
//                } else
//                {
//                    accessGroup = DataAccessor.AnonymousGroup;
//                }
//                getCore().updateInterface(authUser, interfaceData.getRepoType(), interfaceData.iid, interfaceData.xml, interfaceData.version, interfaceData.submitter, accessGroup, null);

//                onInterfaceUpdate(target, currentPublish, selectedAccess);
//                log.debug("Set interface " + interfaceData.iid + " to " + selectedAccess);
            } catch (Exception e)
            {
                isError = true;
//                log.debug("Failed to set interface " + interfaceData.iid + " to " + selectedAccess + " - " + e, e);
                errorMessage = e.toString();
            } finally
            {
                accessWindow.close(target);
            }
        }

        @Override
        public void onCancel(AjaxRequestTarget target)
        {
            accessWindow.close(target);
        }

        @Override
        public void onClose(AjaxRequestTarget target)
        {
            if (isError)
            {
                notificationPanel.setMessages("Error", "Failed to update interface visibility.", errorMessage);
                notificationWindow.show(target);
            }
            isError = false;
        }
    }

    private class DeleteConfirmationHandler implements ConfirmationHandler, WindowClosedCallback
    {
        private static final long serialVersionUID = -5102019936712886844L;

        private boolean isError = false;
        private String errorMessage = null;

        @Override
        public void onConfirm(AjaxRequestTarget target)
        {
            try
            {
//                getCore().deleteInterface(authUser, interfaceData.getRepoType(), interfaceData.iid, interfaceData.version);
//                log.debug("Deleted interface " + interfaceData.iid);
                setResponsePage(Index.class);
            } catch (Exception e)
            {
                isError = true;
//                log.debug("Failed to delete interface " + interfaceData.iid + " - " + e, e);
                errorMessage = e.toString();
            } finally
            {
                deleteWindow.close(target);
            }
        }

        @Override
        public void onCancel(AjaxRequestTarget target)
        {
            deleteWindow.close(target);
        }

        @Override
        public void onClose(AjaxRequestTarget target)
        {
            if (isError)
            {
                notificationPanel.setMessages("Error", "Failed to delete interface.", errorMessage);
                notificationWindow.show(target);
            }
            isError = false;
        }
    }
}
