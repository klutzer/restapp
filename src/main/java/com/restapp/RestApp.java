package com.restapp;

import static org.mentacontainer.impl.SingletonFactory.singleton;

import java.sql.Connection;

import org.glassfish.jersey.server.ResourceConfig;
import org.mentabean.BeanManager;
import org.mentabean.BeanSession;
import org.mentacontainer.Container;
import org.mentacontainer.Scope;
import org.mentacontainer.impl.MentaContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.restapp.config.SwaggerConfigurator;
import com.restapp.db.ConnectionManager;

public abstract class RestApp extends ResourceConfig {

	private static RestApp instance;
	private static final Logger LOGGER = LoggerFactory.getLogger(RestApp.class);

	private static Container container;
	private final BeanManager beanManager;
	private final ConnectionManager connectionManager;

	public RestApp() {
		instance = this;
		container = new MentaContainer();
		beanManager = new BeanManager();
		connectionManager = getConnectionManager();
		initialize();
	}

	public abstract void onStart();
	public abstract void onFinish();
	public abstract void configureBeans(BeanManager manager);
	public abstract void configureIoC(Container container);
	public abstract ConnectionManager getConnectionManager();

	public static Container container() {
		return container;
	}

	public static RestApp getInstance() {
		return instance;
	}

	public void releaseAndShutdown() {

		container.clear(Scope.THREAD);
		container.clear(Scope.SINGLETON);
		connectionManager.shutdown();
		onFinish();
		instance = null;
	}

	private void initialize() {

		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		LOGGER.info("Starting server...");

		//Mapping recursively by package name
		packages(getClass().getPackage().getName());

		SwaggerConfigurator.setUpSwagger(this);
		LOGGER.info("Setup beans...");
		configureBeans(beanManager);
		LOGGER.info("Setup IoC...");
		startIoC();
		LOGGER.info("Executing pre-run...");
		executePreRun();
		LOGGER.info("OK");
	}

	private void executePreRun() {

		BeanSession session = container.get(BeanSession.class);
		connectionManager.preRun(session);
		container.clear(Scope.THREAD);
		onStart();
	}

	private void startIoC() {

		container.ioc(ConnectionManager.class, singleton(connectionManager));
		container.ioc(BeanManager.class, singleton(beanManager));
		container.ioc(Connection.class, connectionManager, Scope.THREAD);
		container.ioc(BeanSession.class, connectionManager.getSessionClass(), Scope.THREAD)
		.addConstructorDependency(BeanManager.class)
		.addConstructorDependency(Connection.class);
		container.autowire(BeanSession.class);
		configureIoC(container);
	}

}
