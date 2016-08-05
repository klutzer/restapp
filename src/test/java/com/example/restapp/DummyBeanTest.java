package com.example.restapp;

import static org.junit.Assert.*;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.example.restapp.business.DummyBean;

public class DummyBeanTest extends AbstractTest {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		session.createTable(DummyBean.class);
	}
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		session.dropTable(DummyBean.class);
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
	
	@Test
	public void testPostAsJSON() throws Exception {
		String json = "{\"name\": \"Érico KL\"}";
		DummyBean response = target("dummy")
				.request()
				.post(Entity.entity(json, MediaType.APPLICATION_JSON), DummyBean.class);
		assertNotNull(response);
		assertEquals(new Long(1), response.getId());
	}
	
	@Test
	public void testGet() throws Exception {
		List<DummyBean> response = target("dummy")
				.request()
				.get(new GenericType<List<DummyBean>>(){});
		assertNotNull(response);
		assertTrue(response.isEmpty());
		
		DummyBean d1 = new DummyBean();
		d1.setName("John");
		DummyBean d2 = new DummyBean();
		d2.setName("Kate");
		
		session.insert(d1);
		session.insert(d2);
		
		commit();
		
		response = target("dummy")
				.request()
				.get(new GenericType<List<DummyBean>>(){});
		assertNotNull(response);
		assertEquals(2, response.size());
		assertEquals(new Long(1), response.get(0).getId());		
		assertEquals("John", response.get(0).getName());		
		assertEquals(new Long(2), response.get(1).getId());		
		assertEquals("Kate", response.get(1).getName());
	}
	
}
