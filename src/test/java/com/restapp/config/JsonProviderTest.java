package com.restapp.config;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import com.restapp.FakeRestApp;

public class JsonProviderTest {


	@Before
	public void setUp() {
		new FakeRestApp();
	}
	
	@Test
	public void shouldRetrieveSameObjectMapperInstance() {
		JsonProvider provider = new JsonProvider();
		JsonProvider provider2 = new JsonProvider();
		assertThat(provider.getContext(null), instanceOf(DefaultObjectMapper.class));
		assertThat(provider.getContext(Object.class), is(provider2.getContext(null)));
	}

}
