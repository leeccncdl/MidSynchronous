package com.yfmandroid.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import android.util.Log;

public class FileAppender {

	private static PrintWriter printer;
	
	private static long fileTime; // YYYYMMDDHH
	
	public static synchronized void initialize(){
		initialize(null);
	}

	private static void initialize(String fileName){
		if(printer != null){
			return;
		}
		
		try{
			if(LoggerConfigure.logLocation != null && LoggerConfigure.logLocation.length() > 0){
				File location = new File(LoggerConfigure.logLocation);
				if(!location.exists()){
					if(!location.mkdirs()){
						return;
					}
				}else if(!location.isDirectory()){
					return;
				}
				
				initPrinter(fileName == null ? validatePrinter() : fileName);
			}
		}catch(Exception e){
			Log.e(LoggerConfigure.logTag, "FileAppender initialize err", e);
			shutdown();
		}
	}
	
	private static void initPrinter(String fileName) throws IOException{
		File logFile = new File(LoggerConfigure.logLocation, fileName);
		if(!logFile.exists()){
			if(!logFile.createNewFile()){
				return;
			}
		}
		LoggerConfigure.currentLogFile = fileName;
		printer = new PrintWriter(new FileOutputStream(logFile, true));
	}
	
	public static synchronized void shutdown(){
		if(printer != null){
			try{
				printer.flush();
				printer.close();
			}catch(Exception e){
				// ignore
			}
			printer = null;
		}
	}
	
	public static boolean isAviable(){
		return printer != null;
	}
	
	private static String validatePrinter(){
		String newFileName = null;
		Calendar c = Calendar.getInstance();
		long now = c.get(Calendar.YEAR) * 1000000L + (c.get(Calendar.MONTH) + 1) * 10000L + c.get(Calendar.DAY_OF_MONTH) * 100 + c.get(Calendar.HOUR_OF_DAY);
		if(LoggerConfigure.logLevel.levelValue <= LoggerConfigure.LogLevel.DEBUG.levelValue){
			if(now > fileTime || printer == null){
				fileTime = now;
				newFileName = "log_" + fileTime + ".txt";
			}
		}else if(LoggerConfigure.logLevel.levelValue <= LoggerConfigure.LogLevel.FATAL.levelValue){
			if(now/10000 > fileTime/10000 || printer == null){
				fileTime = now;
				newFileName = LoggerConfigure.fileName + fileTime/10000 + ".txt";
			}
		}
		return newFileName;
	}
	
	public static synchronized void print(String str){
		if(printer == null){
			return;
		}
		
		String fileName = validatePrinter();
		if(fileName != null){
			shutdown();
			initialize(fileName);
		}
		
		try{
			printDirectly(str);
		}catch(Exception e){
			shutdown();
			initialize();
			try{
				printDirectly(str);
			}catch(Exception e1){
				shutdown();
				Log.e(LoggerConfigure.logTag, "FileAppender - Failed to log message to file.", e1);
			}
		}
	}
	
	private static void printDirectly(String str){
		printer.println(str);
		printer.flush();
	}
}
