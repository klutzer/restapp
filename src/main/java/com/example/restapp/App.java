package com.example.restapp;

import java.sql.Connection;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.mentabean.BeanConfig;
import org.mentabean.BeanManager;
import org.mentabean.BeanSession;
import org.mentabean.DBTypes;
import org.mentabean.jdbc.H2BeanSession;
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

import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

@ApplicationPath("/api/*")
public class App extends ResourceConfig {

	private static Container container;
	private final BeanManager beanManager;
	
	public App() {
		
		container = new MentaContainer();
		beanManager = new BeanManager();
		
		//Mapping recursively by package name
		packages(getClass().getPackage().getName());
		
		setUpSwagger();
		beans();
		ioc();
		
		BeanSession session = container.get(BeanSession.class);
		session.createTables();
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
	
	private void ioc() {
		container.ioc(ConnectionManager.class, new SingletonFactory(new H2ConnectionManager()));
		container.ioc(BeanManager.class, new SingletonFactory(beanManager));
		container.ioc(Connection.class, new ConnectionFactory(), Scope.THREAD);
		container.ioc(BeanSession.class, H2BeanSession.class, Scope.THREAD)
			.addConstructorDependency(BeanManager.class)
			.addConstructorDependency(Connection.class);
		container.autowire(BeanSession.class);

		//Here add your own IoC settings...
		container.ioc(DummyBeanDAO.class, DummyBeanDAO.class);
		//...
	}
	
	private void beans() {
		
		//Here add the mapping for your beans
		
		DummyBean bean = PropertiesProxy.create(DummyBean.class);
		BeanConfig config = new BeanConfig(DummyBean.class, "dummies")
				.pk(bean.getId(), DBTypes.AUTOINCREMENT)
				.field(bean.getName(), DBTypes.STRING);
		
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
