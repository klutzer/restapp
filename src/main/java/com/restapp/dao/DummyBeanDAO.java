package com.restapp.dao;

import org.mentabean.BeanSession;

import com.restapp.entity.DummyBean;

public class DummyBeanDAO extends GenericDAO<DummyBean> {

	public DummyBeanDAO(BeanSession session) {
		super(session);
	}

}
