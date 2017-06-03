package com.restapp.config;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapp.RestApp;

@Provider
public class JsonProvider implements ContextResolver<ObjectMapper> {

	private static ObjectMapper mapper;

	public JsonProvider() {
		if (mapper == null) {
			synchronized (JsonProvider.class) {
				if (mapper == null) {
					mapper = RestApp.getInstance().getObjectMapper();
				}
			}
		}
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}

}