package com.restapp.db;

import org.mentabean.BeanSession;
import org.mentabean.jdbc.H2BeanSession;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class H2ConnectionManager extends ConnectionManager {

	@Override
	public HikariDataSource createPool() {
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
		config.addDataSourceProperty("URL", "jdbc:h2:mem:;MODE=PostgreSQL");
		config.setConnectionTimeout(5000);
		return new HikariDataSource(config);
	}
	
	@Override
	public Class<? extends BeanSession> getSessionClass() {
		return H2BeanSession.class;
	}
	
	@Override
	public void preRun(BeanSession session) {
		session.createTables();
	}

}
