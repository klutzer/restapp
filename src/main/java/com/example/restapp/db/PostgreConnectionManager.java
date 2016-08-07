package com.example.restapp.db;

import org.mentabean.BeanSession;
import org.mentabean.jdbc.PostgreSQLBeanSession;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class PostgreConnectionManager extends ConnectionManager {

	@Override
	public HikariDataSource createPool() {
		
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
		
		//Your configurations here...
		config.setUsername("postgres");
		config.setPassword("postgres");
		config.addDataSourceProperty("databaseName", "restapp");
		config.addDataSourceProperty("portNumber", 5432);
		
		return new HikariDataSource(config);
	}
	
	@Override
	public Class<? extends BeanSession> getSessionClass() {
		return PostgreSQLBeanSession.class;
	}

}
