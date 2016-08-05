package com.example.restapp.dao;

import java.util.List;

import org.mentabean.BeanException;

import com.example.restapp.business.DummyBean;

public class DummyBeanDAO extends AbstractDAO {

	public DummyBean add(DummyBean bean) {
		
		session.insert(bean);
		return bean;
	}
	
	public DummyBean save(DummyBean bean) {
		
		session.save(bean);
		return bean;
	}
	
	public DummyBean update(DummyBean bean) {
		
		session.update(bean);
		return bean;
	}
	
	public boolean delete(DummyBean bean) {
		
		return session.delete(bean);
	}
	
	public DummyBean get(DummyBean proto) {
		
		try {
			if (!session.load(proto)) {
				return null;
			}
			
			return proto;
			
		}catch (BeanException e) {
			
			return session.loadUnique(proto);
		}
	}
	
	public List<DummyBean> listByExample(DummyBean bean) {
		
		return session.loadList(bean);
	}
	
	public int deleteAll(DummyBean proto) {
		
		return session.deleteAll(proto);
	}
	
}
