package com.example.restapp.db;

import java.sql.Connection;

import org.mentacontainer.Factory;
import org.mentacontainer.Interceptor;

import com.example.restapp.App;

public class ConnectionFactory implements Factory, Interceptor<Connection> {

	@Override
	public void onCreated(Connection conn) {
		//Nothing to do here
	}

	@Override
	public void onCleared(Connection conn) {
		getConnectionManager().commit(conn);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getInstance() {
		return (T) getConnectionManager().getConnection();
	}

	@Override
	public Class<?> getType() {
		return Connection.class;
	}
	
	public ConnectionManager getConnectionManager() {
		return App.container().get(ConnectionManager.class);
	}

}

