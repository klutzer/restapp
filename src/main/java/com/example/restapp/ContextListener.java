package com.example.restapp;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {   
   
    @Override
	public void contextInitialized(ServletContextEvent ev) {}
       
    @Override
	public void contextDestroyed(ServletContextEvent ev) {
    	App.releaseAndShutdown();
    }
    
}