package com.yfmandroid.log;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class AbstractLogger {
	protected String name;
	
	@SuppressWarnings("unused")
	private AbstractLogger(){
	}

	protected AbstractLogger(String name){
		this.name = name;
	}
	
	public abstract void trace(String s);

	public abstract void debug(String s);
	
	public abstract void info(String s);
	
	public abstract void warn(String s);
	
	public abstract void error(String s);
	
	public abstract void error(String s, Throwable e);
	
	public abstract void fatal(String s);
	
	public abstract void fatal(String s, Throwable e);
	
	public abstract void logEntry();
	
	public abstract void logExit();
	
	public abstract boolean isTraceEnabled();
	
	public abstract boolean isDebugEnabled();
	
	public abstract boolean isInfoEnabled();
	
	public static String getStackTraceString(String message, Throwable tr){
		StringWriter writer = new StringWriter();
		PrintWriter print = new PrintWriter(writer, true);
		if(message != null){
			print.println(message);
		}
		tr.printStackTrace(print);
		return writer.toString();
	}
	
	public static String getStackTraceString(Throwable tr){
		return getStackTraceString(null, tr);
	}
	
	public String getLogCatString(String s){
		return name + " - " + s;
	}
}
