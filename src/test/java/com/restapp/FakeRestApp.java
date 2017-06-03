package com.restapp;

import org.mentabean.BeanManager;
import org.mentabean.BeanSession;
import org.mentacontainer.Container;

import com.restapp.db.ConnectionManager;
import com.restapp.db.H2ConnectionManager;

public class FakeRestApp extends RestApp {

	@Override
	public void onStart() {
	}

	@Override
	public void onFinish() {
	}

	@Override
	public void configureBeans(BeanManager manager) {
	}

	@Override
	public void configureIoC(Container container) {
	}

	@Override
	public ConnectionManager createConnectionManager() {
		return new H2ConnectionManager() {
			@Override
			public void preRun(BeanSession session) {
			}
		};
	}

}
