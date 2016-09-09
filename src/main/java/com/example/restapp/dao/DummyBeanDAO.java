package com.example.restapp.dao;

import java.util.List;

import org.mentabean.BeanSession;

import com.example.restapp.business.DummyBean;

public class DummyBeanDAO extends AbstractDAO {

	public DummyBeanDAO(BeanSession session) {
		super(session);
	}

	public DummyBean add(DummyBean bean) {

		session.insert(bean);
		return bean;
	}

	public DummyBean save(DummyBean bean) {

		session.save(bean);
		return bean;
	}

	public DummyBean update(DummyBean bean) {

		if (session.update(bean) > 0) {
			return bean;
		}
		
		return null;
	}

	public boolean delete(DummyBean bean) {

		return session.delete(bean);
	}

	public DummyBean getById(DummyBean bean) {

		if (!session.load(bean)) {
			return null;
		}
		
		return bean;
	}

	public DummyBean getByExample(DummyBean proto) {

		return session.loadUnique(proto);
	}

	public List<DummyBean> listByExample(DummyBean bean) {

		return session.loadList(bean);
	}

	public int deleteAll(DummyBean proto) {

		return session.deleteAll(proto);
	}

}
