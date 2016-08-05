package com.example.restapp.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class H2ConnectionManager extends ConnectionManager {

	@Override
	public HikariDataSource createPool() {
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
		config.addDataSourceProperty("URL", "jdbc:h2:mem:dummytest;MODE=PostgreSQL");
		config.setConnectionTimeout(5000);
		return new HikariDataSource(config);
	}

}
