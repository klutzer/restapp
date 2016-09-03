package com.example.restapp;

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
import org.mentacontainer.impl.SingletonFactory;

import com.example.restapp.business.DummyBean;
import com.example.restapp.dao.DummyBeanDAO;
import com.example.restapp.db.ConnectionFactory;
import com.example.restapp.db.ConnectionManager;
import com.example.restapp.db.H2ConnectionManager;

@SuppressWarnings("deprecation")
@ApplicationPath("/api/*")
public class App extends ResourceConfig {

	private static Container container;
	private final BeanManager beanManager;
	
	public App(ConnectionManager cm) {
		
		container = new MentaContainer();
		beanManager = new BeanManager();
		
		//Mapping recursively by package name
		packages(getClass().getPackage().getName());
		
		setUpSwagger();
		beans();
		ioc(cm);
		executePreRun(cm);
	}
	
	public App() {
		this(new H2ConnectionManager());
		register(new LoggingFilter(Logger.getLogger(getClass().getSimpleName()), true));
	}
	
	public static Container container() {
		return container;
	}
	
	public static void releaseAndShutdown() {
		ConnectionManager cm = container.get(ConnectionManager.class);
		container.clear(Scope.THREAD);
		container.clear(Scope.SINGLETON);
		cm.shutdown();
	}
	
	private void ioc(ConnectionManager cm) {
		container.ioc(ConnectionManager.class, new SingletonFactory(cm));
		container.ioc(BeanManager.class, new SingletonFactory(beanManager));
		container.ioc(Connection.class, new ConnectionFactory(), Scope.THREAD);
		container.ioc(BeanSession.class, cm.getSessionClass(), Scope.THREAD)
			.addConstructorDependency(BeanManager.class)
			.addConstructorDependency(Connection.class);
		container.autowire(BeanSession.class);

		// here add your own IoC settings...
		container.ioc(DummyBeanDAO.class, DummyBeanDAO.class);
		//...
	}
	
	private void executePreRun(ConnectionManager cm) {
		BeanSession session = container.get(BeanSession.class);
		cm.preRun(session);
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
        conf.setResourcePackage("com.example.restapp.resources");
        conf.setPrettyPrint(true);
        conf.setBasePath("/RestApp/api");
        conf.setScan(true);
	}
	
}
