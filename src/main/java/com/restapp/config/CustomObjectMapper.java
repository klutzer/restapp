package com.restapp.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

@SuppressWarnings("serial")
public class CustomObjectMapper extends ObjectMapper {

	public CustomObjectMapper() {
		configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, true);
		configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		configure(SerializationFeature.INDENT_OUTPUT, true);
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		setSerializationInclusion(Include.NON_NULL);
		registerModule(new JodaModule());
	}
}
