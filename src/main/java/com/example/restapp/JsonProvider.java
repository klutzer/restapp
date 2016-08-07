package com.example.restapp;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class JsonProvider implements ContextResolver<ObjectMapper> {

	private ObjectMapper mapper = new CustomObjectMapper();
	
	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}
	
}
