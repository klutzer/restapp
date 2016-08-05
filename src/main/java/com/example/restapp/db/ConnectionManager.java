package com.example.restapp.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

import org.mentabean.BeanException;
import org.mentabean.util.SQLUtils;

import com.zaxxer.hikari.HikariDataSource;

public abstract class ConnectionManager {

	private final HikariDataSource pool;
	
	public ConnectionManager() {
		pool = createPool();
		pool.setAutoCommit(false);
	}
	
	/**
	 * Here you can configure your database settings...<br>
	 * Take a look at HikariCP (https://github.com/brettwooldridge/HikariCP)
	 */
	public abstract HikariDataSource createPool();
	
	public void shutdown() {
		pool.close();
		deregisterDrivers();
	}
	
	private static void deregisterDrivers() {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
			} catch (Exception e) {
				throw new BeanException(e);
			}
		}
	}
	
	public Connection getConnection() {
		try {
			return pool.getConnection();
		} catch (Exception e) {
			throw new BeanException("Erro ao retornar conexão do pool", e);
		}
	}
	
	public void commit(Connection conn) {
		try {
			if (!conn.getAutoCommit() && !conn.isClosed()) {
				SQLUtils.commitTransaction(conn);
			}
		}catch (Exception e) {
			rollback(conn);
			throw new BeanException(e);
		}finally {
			SQLUtils.close(conn);
		}
	}

	public void rollback(Connection conn) {
		try {
			if (!conn.getAutoCommit() && !conn.isClosed()) {
				SQLUtils.rollbackTransaction(conn);
			}
		}catch (Exception e) {
			throw new BeanException(e);
		}
	}
	
}
