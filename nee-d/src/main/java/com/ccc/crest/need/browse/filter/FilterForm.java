package com.ccc.crest.need.browse.filter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.crest.need.browse.Browse;

public class FilterForm extends Form<Filter> {
	private static final long serialVersionUID = -184348669905342472L;
	
	private TextField<String> repoTextField;
	private TextField<String> submitterTextField;
	private DropDownChoice<String> publishDropDownChoice;
	
	private static Logger log = LoggerFactory.getLogger(FilterForm.class);
	
	public FilterForm(String id, IModel<Filter> model) {
		super(id, model);
		
		repoTextField = new TextField<String>("repoField", new PropertyModel<String>(model,"repo"), String.class);
		submitterTextField = new TextField<String>("submitterField", new PropertyModel<String>(model,"submitter"), String.class);
		publishDropDownChoice = new DropDownChoice<String>("publishField", new PropertyModel<String>(model, "publish"), Filter.PUBLISH_OPTIONS);

		add(repoTextField);
		add(submitterTextField);
		add(publishDropDownChoice);
		add(new Button("submit"));
	}
	
	@Override
	public void onSubmit(){
		PageParameters parameters = new PageParameters();
		String repo = repoTextField.getDefaultModelObjectAsString();;
		String submitter = submitterTextField.getDefaultModelObjectAsString();
		String publish = publishDropDownChoice.getDefaultModelObjectAsString();
		
		try {
			if(repo != null) repo = URLEncoder.encode(repo, "UTF-8");
			if(submitter != null) submitter = URLEncoder.encode(submitter, "UTF-8");
			if(publish != null) publish = URLEncoder.encode(publish, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.debug("Failed to parse parameters.", e);
		}
		
		if(repo != null && !repo.isEmpty()){
			parameters.add("repo", repo);
		}
		if(submitter != null && !submitter.isEmpty()){
			parameters.add("submitter", submitter);
		}
		if(publish != null && !publish.isEmpty()){
			parameters.add("publish", publish);
		}
		setResponsePage(Browse.class, parameters);
	}
}
