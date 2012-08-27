package com.yfmandroid.log;


public class LoggerFactory {
	public static Logger getLogger(Class<?> cls){
		return new Logger(cls.getSimpleName());
	}
	
	public static Logger getLogger(String name) {
		return new Logger(name);
	}
}
