package com.restapp.config;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import com.restapp.RestApp;

@Priority(1)
@Provider
public class ResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		
		//Cleaning all threads from container
		RestApp.getInstance().clearContainerThreads();
		
		//Enabling CORS support...
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
		responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		responseContext.getHeaders().add("Access-Control-Allow-Headers", "api_key, Authorization, Origin, X-Requested-With, Content-Type, Accept, token");
	}
	
}
