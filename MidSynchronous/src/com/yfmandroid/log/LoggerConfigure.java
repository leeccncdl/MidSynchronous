package com.yfmandroid.log;

public class LoggerConfigure {
	//给枚举赋值是为了对值进行比较
	public enum LogLevel{
		TRACE(0),
		DEBUG(1),
		INFO(2),
		WARN(3),
		ERROR(4),
		FATAL(5),
		OFF(6);
		
		public int levelValue;
		
		LogLevel(int aLevelValue){
			levelValue = aLevelValue;
		}
	}

	public static LogLevel logLevel = LogLevel.TRACE;//配置的shouldlog值
	
	public static String logTag = "APPLOG";
	
	public static String logLocation = null;//Log文件路径的配置
	
	public static String fileName = "log_";
	
	public static String currentLogFile;
	
	//根据配置的shouldLog值返回 是否输出log
	public static boolean shouldLog(LogLevel level){
		return logLevel.levelValue <= level.levelValue;
	}
	
	public static void initialize(){
		shutdown();
		FileAppender.initialize();
	}
	
	public static void shutdown(){
		FileAppender.shutdown();
	}
}
