package com.example.restapp.business;

import org.joda.time.DateTime;

public class DummyBean {
	
	private Long id;
	private String name;
	private DateTime date;
	
	public DummyBean() {}
	
	public DummyBean(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
