package com.example.restapp;

import static org.mentacontainer.impl.SingletonFactory.*;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

import java.sql.Connection;
import java.util.logging.Logger;

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

import com.example.restapp.business.DummyBean;
import com.example.restapp.db.ConnectionManager;
import com.example.restapp.db.H2ConnectionManager;

@SuppressWarnings("deprecation")
@ApplicationPath("/api/*")
public class App extends ResourceConfig {

	private static Container container;
	private final BeanManager beanManager;
	private final ConnectionManager connectionManager;
	
	public App(ConnectionManager connectionManager) {
		
		this.connectionManager = connectionManager;
		this.beanManager = new BeanManager();
		container = new MentaContainer();
		
		//Mapping recursively by package name
		packages(getClass().getPackage().getName());
		
		setUpSwagger();
		beans();
		ioc();
		executePreRun();
	}
	
	public App() {
		this(new H2ConnectionManager());
		register(new LoggingFilter(Logger.getLogger(getClass().getSimpleName()), true));
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
	
	private void setUpSwagger() {
		
		registerClasses(SwaggerSerializers.class, ApiListingResource.class);
		
		io.swagger.jaxrs.config.BeanConfig conf = new io.swagger.jaxrs.config.BeanConfig();
		conf.setTitle("RestApp API");
		conf.setDescription("Live docs for RestApp API");
        conf.setVersion("v1");
        conf.setResourcePackage(getClass().getPackage().getName()+".resources");
        conf.setPrettyPrint(true);
        conf.setBasePath("/RestApp/api");
        conf.setScan(true);
	}
	
}
