package com.example.restapp.dao;

import org.mentabean.BeanSession;

public abstract class AbstractDAO {

	protected final BeanSession session;
	
	public AbstractDAO(BeanSession session) {
		this.session = session;
	}
	
}
