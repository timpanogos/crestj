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
package com.ccc.crest.servlet.needApi;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.slf4j.LoggerFactory;

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.InvalidApiKeysException;
import com.ccc.crest.core.ScopeToMask;
import com.ccc.crest.servlet.CrestServlet;
import com.ccc.db.DataAccessorException;

@SuppressWarnings("javadoc")
//TODO: at least get header in here, consider popup
public class ApiKeyInput extends WebPage
{
    private static final long serialVersionUID = -8608018794880878650L;

    private class InputForm extends Form<ApiKeyInputModel>
    {
        private static final long serialVersionUID = -3790319921848926702L;

        @SuppressWarnings("serial")
        public InputForm(String name)
        {
            super(name, new CompoundPropertyModel<ApiKeyInputModel>(new ApiKeyInputModel()));
            
            add(new Label("keyidlabel", "KeyID:"));
            add(new TextField<String>("keyId").setRequired(true));
            add(new Label("codelabel", "Code:"));
            add(new TextField<String>("code").setRequired(true).setLabel(new Model<>("Code")));
            
            CrestClientInfo clientInfo = CrestServlet.getCrestClientInfo();
            String createPredefinedUrl = CrestController.getCrestController().scopes.getCreatePredefinedUrl(clientInfo, ScopeToMask.Type.Character);
            PopupSettings predefinedPopupSettings = new PopupSettings(PopupSettings.RESIZABLE |PopupSettings.SCROLLBARS).setHeight(500).setWidth(700);
            add(new ExternalLink("predefinedkey", createPredefinedUrl).setPopupSettings(predefinedPopupSettings));
            
            add(new Button("saveButton")
            {
                @Override
                public void onSubmit()
                {
                    CrestClientInfo clientInfo = CrestServlet.getCrestClientInfo();
                    ApiKeyInputModel data = (ApiKeyInputModel)getForm().getModel().getObject();
                    try
                    {
                        CrestController.getCrestController().setCapsuleerApiKey(clientInfo.getVerifyData().CharacterName, data.getKeyId(), data.getCode());
                    } 
                    catch (InvalidApiKeysException e)
                    {
                        // TODO: work in dialogs for these two
                        LoggerFactory.getLogger(getClass()).warn("invalid KeyID likely", e);
                    }
                    catch (DataAccessorException e1)
                    {
                        // TODO: work in dialogs for these two
                        LoggerFactory.getLogger(getClass()).warn("DataSource failure", e1);
                    }
                    System.err.println(data.toString());
                    setResponsePage(getApplication().getHomePage());
                }
            });
            
            add(new Button("cancelButton")
            {
                @Override
                public void onSubmit()
                {
                    setResponsePage(getApplication().getHomePage());
                }
            }.setDefaultFormProcessing(false));
        }

        @Override
        public void onSubmit()
        {
            // Form validation successful. Display message showing edited model.
            info("Saved model " + getDefaultModelObject());
        }
    }

    public ApiKeyInput()
    {
        // Construct form and feedback panel and hook them up
//        final FeedbackPanel feedback = new FeedbackPanel("feedback");
//        add(feedback);
        add(new InputForm("inputForm"));
    }
}