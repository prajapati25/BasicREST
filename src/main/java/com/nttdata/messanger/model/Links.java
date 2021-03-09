package com.nttdata.messanger.model;

public class Links {

	private String uri;
	private String relation;
	public Links(String uri, String rel) {
		this.uri= uri;
		this.relation=rel;
	}
	public String getUri() {
		return uri;
	}
	public void setLink(String uri) {
		this.uri = uri;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
}
