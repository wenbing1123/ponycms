package com.pony.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.logicalcobwebs.proxool.ProxoolFacade;

public class ApplicationContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {

	}
	
	public void contextDestroyed(ServletContextEvent event) {
		ProxoolFacade.shutdown();
	}

}
