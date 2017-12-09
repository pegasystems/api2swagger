package com.pega.api2swagger.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class LogHelper {
	
	private Logger logger;
	private static volatile boolean isInitialized = false;
	
	public synchronized static void initializeLogger() {
		
		if(isInitialized)
			return;
		
		InputStream configStream = null;
		try {
			configStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("log4j.xml");
			if(configStream != null){
				new DOMConfigurator().doConfigure(configStream, LogManager.getLoggerRepository());	
			} else {
				System.out.println("Error reading log configurstion file. Could not initialize logger.");
			}
		} finally{
			if(configStream != null){
				try {
					configStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
		isInitialized = true;
	}
	
	public LogHelper(Class<?> clz) {
		
		if(!isInitialized){
			initializeLogger();
		}
		
		logger = LogManager.getLogger(clz);
	}
	
	public void error(String f, Object ... o) {
		logger.error(String.format(f,o));
	}
	
	public void error(Throwable t, String f, Object ... o) {
		String message = String.format(f,o);
		logger.error(message, t);
	}
	
	public void error(Object o) {
		logger.error(o);
	}
	
	public void error(Object o, Throwable t) {
		logger.error(o, t);
	}
	
	public void info(String f, Object ... o) {
		logger.info(String.format(f,o));
	}

	public void info(Throwable t, String f, Object ... o) {
		logger.info(String.format(f,o), t);
	}

	public void info(Object o) {
		logger.info(o);
	}
	
	public void info(Object o, Throwable t) {
		logger.info(o, t);
	}

	public void debug(String o) {
		logger.debug(o);
	}
	
	public void debug(String f, Object ... o) {
		logger.debug(String.format(f,o));
	}
	
	public void debug(Throwable t, String f, Object ... o) {
		logger.debug(String.format(f,o), t);
	}
	
	public void debug(Object o, Throwable t) {
		logger.debug(o, t);
	}
}
