package com.restapp.entity;

import org.joda.time.DateTime;

public class DummyBean {
	
	private long id;
	private String name;
	private DateTime date;
	
	public DummyBean() {}
	
	public DummyBean(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DateTime getDate() {
		return date;
	}
	public void setDate(DateTime date) {
		this.date = date;
	}

}
