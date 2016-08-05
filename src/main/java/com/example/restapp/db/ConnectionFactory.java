package com.example.restapp.db;

import java.sql.Connection;

import org.mentacontainer.Factory;
import org.mentacontainer.Interceptor;

import com.example.restapp.App;

public class ConnectionFactory implements Factory, Interceptor<Connection> {

	private static final ConnectionManager manager = App.container().get(ConnectionManager.class);
	
	@Override
	public void onCreated(Connection conn) {
		//Nothing to do here
	}

	@Override
	public void onCleared(Connection conn) {
		manager.commit(conn);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getInstance() {
		return (T) manager.getConnection();
	}

	@Override
	public Class<?> getType() {
		return Connection.class;
	}

}

