package com.restapp;

import static org.mentacontainer.impl.SingletonFactory.*;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

import java.sql.Connection;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.mentabean.BeanConfig;
import org.mentabean.BeanManager;
import org.mentabean.BeanSession;
import org.mentabean.DBTypes;
import org.mentabean.util.PropertiesProxy;
import org.mentacontainer.Container;
import org.mentacontainer.Scope;
import org.mentacontainer.impl.MentaContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.restapp.config.SwaggerConfigurator;
import com.restapp.db.ConnectionManager;
import com.restapp.db.H2ConnectionManager;
import com.restapp.entity.DummyBean;

@SuppressWarnings("deprecation")
@ApplicationPath("/api/*")
public class RestApp extends ResourceConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestApp.class);

	private static final Container container = new MentaContainer();
	private final BeanManager beanManager;
	private final ConnectionManager connectionManager;
	
	public RestApp(ConnectionManager connectionManager) {
		
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		
		LOGGER.info("Starting server...");
		this.connectionManager = connectionManager;
		this.beanManager = new BeanManager();
		
		//Mapping recursively by package name
		packages(getClass().getPackage().getName());
		
		SwaggerConfigurator.setUpSwagger(this);
		LOGGER.info("Setup beans...");
		beans();
		LOGGER.info("Setup IoC...");
		ioc();
		LOGGER.info("Executing pre-run...");
		executePreRun();
		LOGGER.info("OK");
	}
	
	public RestApp() {
		this(new H2ConnectionManager());
		register(new LoggingFilter(java.util.logging.Logger.getLogger(getClass().getName()), true));
	}
	
	public static Container container() {
		return container;
	}
	
	public static void releaseAndShutdown() {
		ConnectionManager connectionManager = container.get(ConnectionManager.class);
		container.clear(Scope.THREAD);
		container.clear(Scope.SINGLETON);
		connectionManager.shutdown();
	}
	
	private void ioc() {
		container.ioc(ConnectionManager.class, singleton(connectionManager));
		container.ioc(BeanManager.class, singleton(beanManager));
		container.ioc(Connection.class, connectionManager, Scope.THREAD);
		container.ioc(BeanSession.class, connectionManager.getSessionClass(), Scope.THREAD)
			.addConstructorDependency(BeanManager.class)
			.addConstructorDependency(Connection.class);
		container.autowire(BeanSession.class);

		// more IoC configs go here...
	}
	
	private void executePreRun() {
		BeanSession session = container.get(BeanSession.class);
		connectionManager.preRun(session);
		container.clear(Scope.THREAD);
	}
	
	private void beans() {
		
		// here add the mappings for your beans
		
		DummyBean bean = PropertiesProxy.create(DummyBean.class);
		BeanConfig config = new BeanConfig(DummyBean.class, "dummies")
				.pk(bean.getId(), DBTypes.AUTOINCREMENT)
				.field(bean.getName(), DBTypes.STRING)
				.field(bean.getDate(), DBTypes.JODA_DATETIME);
		
		beanManager.addBeanConfig(config);
	}
	
}
