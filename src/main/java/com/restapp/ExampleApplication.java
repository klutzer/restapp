package com.restapp;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;
import org.mentabean.BeanConfig;
import org.mentabean.BeanManager;
import org.mentabean.DBTypes;
import org.mentabean.util.PropertiesProxy;
import org.mentacontainer.Container;

import com.restapp.db.ConnectionManager;
import com.restapp.db.H2ConnectionManager;
import com.restapp.entity.DummyBean;

@SuppressWarnings("deprecation")
@ApplicationPath("/api/*")
public class ExampleApplication extends RestApp {

	@Override
	public void onStart() {
		register(new LoggingFilter(java.util.logging.Logger.getLogger(getClass().getName()), true));
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureBeans(BeanManager manager) {
		
		DummyBean bean = PropertiesProxy.create(DummyBean.class);
		BeanConfig config = new BeanConfig(DummyBean.class, "dummies")
				.pk(bean.getId(), DBTypes.AUTOINCREMENT)
				.field(bean.getName(), DBTypes.STRING)
				.field(bean.getDate(), DBTypes.JODA_DATETIME);
		
		manager.addBeanConfig(config);
	}

	@Override
	public void configureIoC(Container container) {
		// nothing to do here
	}

	@Override
	public ConnectionManager createConnectionManager() {
		return new H2ConnectionManager();
	}

}
