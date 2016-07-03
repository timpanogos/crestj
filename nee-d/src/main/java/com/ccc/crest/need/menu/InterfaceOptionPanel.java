package com.ccc.crest.need.menu;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterfaceOptionPanel extends Panel
{
    private static final long serialVersionUID = -4736200814044302198L;

    private DropDownChoice<String> translationDropDownChoice;
    private MarkupContainer languageRow;
    private DropDownChoice<String> languageDropDownChoice;
    private PageParameters pageParameters;

    private static Logger log = LoggerFactory.getLogger(InterfaceOptionPanel.class);

    public InterfaceOptionPanel(String id)
    {
        super(id);
    }

    // public InterfaceOptionPanel(String id, XMLTranslator translator, InterfaceData interfaceData, PageParameters pageParameters, boolean isTranslateError)
    // {
    // super(id);
    // this.pageParameters = pageParameters;
    //
    // List<String> translations = null;
    // try
    // {
    // translations = getCore().getTranslations(interfaceData.getRepoType());
    // } catch (Exception e)
    // {
    // log.warn("Failed to get translations - " + e, e);
    // }
    //
    // translationDropDownChoice = new TranslationDropDownChoice("translationField", translations, translator.getTranslationName());
    //
    // languageRow = new WebMarkupContainer("languageRow");
    // languageDropDownChoice = new DropDownChoice<String>("languageField");
    // languageRow.add(languageDropDownChoice);
    // languageRow.setVisible(translator.hasLanguageSupport() && interfaceData.xml != null && !isTranslateError);
    //
    // add(translationDropDownChoice);
    // add(languageRow);
    // }
    //
    // private CoreController getCore()
    // {
    // return ((NeedServer) getApplication()).getCoreController();
    // }
    //
    // private class TranslationDropDownChoice extends DropDownChoice<String>
    // {
    // private static final long serialVersionUID = -3712082508263393331L;
    //
    // public TranslationDropDownChoice(String id, List<? extends String> choices, String defaultTranslation)
    // {
    // super(id, new Model<String>(defaultTranslation), choices);
    // }
    //
    // @Override
    // protected boolean wantOnSelectionChangedNotifications()
    // {
    // return true;
    // }
    //
    // @Override
    // protected void onSelectionChanged(final String newSelection)
    // {
    // pageParameters.set(Definition.TRANS_NAME, newSelection);
    // setResponsePage(Definition.class, pageParameters);
    // }
    // }
}
