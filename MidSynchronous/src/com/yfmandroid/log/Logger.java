package com.yfmandroid.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

import com.yfmandroid.log.LoggerConfigure.LogLevel;


public class Logger extends AbstractLogger {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public Logger(String name) {
		super(name);
	}

	@Override
	public void trace(String s) {
		Log.v(LoggerConfigure.logTag, getLogCatString(s));
		logStorage(LogLevel.TRACE, s);
	}

	@Override
	public void debug(String s) {
		Log.d(LoggerConfigure.logTag, getLogCatString(s));
		logStorage(LogLevel.DEBUG, s);
	}

	@Override
	public void info(String s) {
		Log.i(LoggerConfigure.logTag, getLogCatString(s));
		logStorage(LogLevel.INFO, s);
	}

	@Override
	public void warn(String s) {
		Log.w(LoggerConfigure.logTag, getLogCatString(s));
		logStorage(LogLevel.WARN, s);
	}

	@Override
	public void error(String s) {
		Log.e(LoggerConfigure.logTag, getLogCatString(s));
		logStorage(LogLevel.ERROR, s);
	}

	@Override
	public void error(String s, Throwable e) {
		Log.e(LoggerConfigure.logTag, getLogCatString(s), e);
		logStorage(LogLevel.ERROR, getStackTraceString(s, e));
	}

	@Override
	public void fatal(String s) {
		Log.println(Log.ERROR, LoggerConfigure.logTag, getLogCatString(s));
		logStorage(LogLevel.FATAL, s);
	}

	@Override
	public void fatal(String s, Throwable e) {
		Log.println(Log.ERROR, LoggerConfigure.logTag, getStackTraceString(getLogCatString(s), e));
		logStorage(LogLevel.FATAL, getStackTraceString(s, e));
	}
	
	@Override
	public void logEntry() {
		if(isDebugEnabled()){
			logEntryExit("Enter");
		}
	}
	
	@Override
	public void logExit() {
		if(isDebugEnabled()){
			logEntryExit("Exit");
		}
	}
	
	private void logEntryExit(String type){
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		if(stackTrace.length > 2){
			StackTraceElement elem = stackTrace[2];
			String message = Thread.currentThread().getName() + " " + type + " " + elem.toString();
			Log.d(LoggerConfigure.logTag, message);
			FileAppender.print(buildLogMessage(LogLevel.DEBUG, "[" + dateFormat.format(new Date()) + "] " + message));
		}
	}
	
	protected String buildLogMessage(LogLevel level, String message){
		return "[" + dateFormat.format(new Date()) + "] " + name + "(" + Thread.currentThread().getName() + ") [" + level.name() + "] - " + message;
	}
	
	protected void logStorage(LogLevel level, String message){
		FileAppender.print(buildLogMessage(level, message));
	}

	@Override
	public boolean isTraceEnabled() {
		return LoggerConfigure.shouldLog(LoggerConfigure.LogLevel.TRACE);
	}

	@Override
	public boolean isDebugEnabled() {
		return LoggerConfigure.shouldLog(LoggerConfigure.LogLevel.DEBUG);
	}

	@Override
	public boolean isInfoEnabled() {
		return LoggerConfigure.shouldLog(LoggerConfigure.LogLevel.INFO);
	}
}
