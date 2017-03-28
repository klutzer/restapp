package com.restapp.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.restapp.RestApp;

@WebListener
public class ContextListener implements ServletContextListener {   
   
    @Override
	public void contextInitialized(ServletContextEvent ev) {}
       
    @Override
	public void contextDestroyed(ServletContextEvent ev) {
    	RestApp.getInstance().releaseAndShutdown();
    }
    
}