package com.ccc.crestj.servlet.browse.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Filter implements Serializable{
	private static final long serialVersionUID = 5809345235433691152L;
	
	public static final String PUBLISHED = "Published";
	public static final String WORKING = "Working";
	public static final String PUBLISHED_AND_WORKING = "Published/Working";
	public static final List<String> PUBLISH_OPTIONS;
	
	static{
		PUBLISH_OPTIONS = new ArrayList<String>();
		PUBLISH_OPTIONS.add(PUBLISHED);
		PUBLISH_OPTIONS.add(WORKING);
		PUBLISH_OPTIONS.add(PUBLISHED_AND_WORKING);
	}
	
	private String repo = "";
	private String submitter = "";
	private String publish = PUBLISHED_AND_WORKING;
	
	public String getPublish() {
		return publish;
	}
	
	public void setPublish(String publish) {
		if(publish == null){
			this.publish = PUBLISHED_AND_WORKING;
		} else if(publish.equals(PUBLISHED)) {
			this.publish = PUBLISHED;
		} else if(publish.equals(WORKING)){
			this.publish = WORKING;
		} else{
			this.publish = PUBLISHED_AND_WORKING;
		}
	}
	
	public String getSubmitter() {
		return submitter;
	}
	
	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}
	
	public String getRepo() {
		return repo;
	}
	
	public void setRepo(String repo) {
		this.repo = repo;
	}
}
