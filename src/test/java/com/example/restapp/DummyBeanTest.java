package com.example.restapp;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;

import org.junit.Test;

import com.example.restapp.business.DummyBean;

public class DummyBeanTest extends AbstractTest {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		session.createTables();
	}
	
	@Test
	public void testPostAsBean() throws Exception {
		DummyBean dummy = new DummyBean();
		dummy.setName("Test");
		DummyBean response = target("dummy")
				.request()
				.post(Entity.json(dummy), DummyBean.class);
		assertNotNull(response);
		assertEquals(new Long(1), response.getId());
	}
	
}
