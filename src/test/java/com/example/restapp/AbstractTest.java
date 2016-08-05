package com.example.restapp;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.mentabean.BeanSession;
import org.mentabean.util.SQLUtils;

public class AbstractTest extends JerseyTest {

	protected final BeanSession session() {
		return App.container().get(BeanSession.class);
	}
	
	@Override
	protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
		return new GrizzlyWebTestContainerFactory();
	}
	
	@Override
	protected DeploymentContext configureDeployment() {
		
		//for parallel tests
		forceSet(TestProperties.CONTAINER_PORT, "0");
		
		return ServletDeploymentContext
				.forServlet(new ServletContainer(new App()))
				.addListener(ContextListener.class)
				.build();
	}
	
	protected void commit() {
		SQLUtils.commitTransaction(session().getConnection());
	}
	
}
