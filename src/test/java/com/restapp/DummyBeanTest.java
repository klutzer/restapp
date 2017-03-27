package com.restapp;

import static org.junit.Assert.*;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import com.restapp.entity.DummyBean;
import com.restapp.resources.BeanResponse;

public class DummyBeanTest extends AbstractTest {

	@Test
	public void testAddAsBean() throws Exception {
		DummyBean dummy = new DummyBean();
		dummy.setName("Test");
		DummyBean response = target("dummy")
				.request()
				.post(Entity.json(dummy), DummyBean.class);
		assertNotNull(response);
		assertEquals(1, response.getId());
	}
	
	@Test
	public void testAddAsJSON() throws Exception {
		String json = "{\"name\": \"Érico KL\","
				+ "\"date\": \"1\"}";
		DummyBean response = target("dummy")
				.request()
				.put(Entity.entity(json, MediaType.APPLICATION_JSON), DummyBean.class);
		assertNotNull(response);
		assertEquals(1, response.getId());
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
		
		session().insert(d1);
		session().insert(d2);
		
		commit();
		
		response = target("dummy")
				.request()
				.get(new GenericType<List<DummyBean>>(){});
		assertNotNull(response);
		assertEquals(2, response.size());
		assertEquals(1, response.get(0).getId());		
		assertEquals("John", response.get(0).getName());		
		assertEquals(2, response.get(1).getId());		
		assertEquals("Kate", response.get(1).getName());
	}
	
	@Test
	public void testSendingDateTimeType() throws Exception {
		DummyBean d = new DummyBean();
		d.setName("Érico");
		DateTime time = new DateTime().withDate(1991, 01, 31);
		d.setDate(time);
		
		d = target("dummy")
				.request()
				.put(Entity.json(d), DummyBean.class);
		assertEquals(1, d.getId());
		assertEquals(time.withZone(DateTimeZone.UTC), d.getDate().withZone(DateTimeZone.UTC));
	}
	
	@Test
	public void testDelete() throws Exception {
		
		DummyBean d1 = new DummyBean();
		d1.setName("John");
		session().insert(d1);
		
		BeanResponse response = target("dummy")
				.path("1")
				.request()
				.delete(BeanResponse.class);
		assertEquals("Bean deleted", response.getMsg());
		
		response = target("dummy")
				.path("1")
				.request()
				.delete(BeanResponse.class);
		assertEquals("Nothing was deleted", response.getMsg());
	}
	
	@Test
	public void testUpdate() throws Exception {
		
		DummyBean d1 = new DummyBean();
		d1.setName("John");
		session().insert(d1);
		
		DummyBean d2 = new DummyBean();
		d2.setName("Kate");
		session().insert(d2);
		
		DummyBean data = new DummyBean();
		data.setDate(new DateTime().withDate(1991, 01, 31));
		
		DummyBean response = target("dummy")
				.path("2")
				.request()
				.put(Entity.json(data), DummyBean.class);
		assertNotNull(response);
		assertEquals(2, response.getId());
		assertEquals(data.getDate().withZone(DateTimeZone.UTC), response.getDate().withZone(DateTimeZone.UTC));
	}
	
	@Test
	public void testUpsert() throws Exception {
		DummyBean dummy = new DummyBean();
		dummy.setName("Test");
		DummyBean response = target("dummy")
				.request()
				.put(Entity.json(dummy), DummyBean.class);
		assertNotNull(response);
		assertEquals(1, response.getId());
		
		dummy = new DummyBean(1);
		dummy.setName("Another test");
		
		response = target("dummy")
				.request()
				.put(Entity.json(dummy), DummyBean.class);
		assertNotNull(response);
		assertEquals(1, response.getId());
		assertEquals("Another test", response.getName());
	}
	
}
