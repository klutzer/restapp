package com.restapp.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

public class SwaggerConfigurator {

	private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfigurator.class);

	public static void setUpSwagger(ResourceConfig application) {

		LOGGER.info("Setup Swagger...");

		application.registerClasses(SwaggerSerializers.class, ApiListingResource.class);

		BeanConfig conf = new BeanConfig();
		conf.setTitle("RestApp API");
		conf.setDescription("Live docs for RestApp API");
		conf.setVersion("v1");
		conf.setResourcePackage(application.getClass().getPackage().getName()+".resources");
		conf.setPrettyPrint(true);
		conf.setBasePath("/RestApp/api");
		conf.setScan(true);

		LOGGER.info("Setup Swagger Complete.");
	}
}
