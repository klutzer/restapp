package com.example.restapp.dao;

import org.mentabean.BeanSession;

import com.example.restapp.business.DummyBean;

public class DummyBeanDAO extends GenericDAO<DummyBean> {

	public DummyBeanDAO(BeanSession session) {
		super(session);
	}

}
