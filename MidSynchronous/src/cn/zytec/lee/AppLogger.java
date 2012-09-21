package cn.zytec.lee;

import android.content.Context;

import com.yfmandroid.log.Logger;
import com.yfmandroid.log.LoggerConfigure;
import com.yfmandroid.log.LoggerConfigure.LogLevel;

public class AppLogger extends Logger {
	
	public static boolean config(Context context){
		LoggerConfigure.logTag = "MidSync";
		LoggerConfigure.logLevel = LogLevel.DEBUG;
		LoggerConfigure.logLocation = null;
		return true;
	}
	
	public static AppLogger getLogger(Class<?> cls){
		String name = cls.getSimpleName();
		if(name == null || name.length() == 0){
			name = cls.getName();
		}
		return new AppLogger(name);
	}

	public static AppLogger getLogger(String name) {
		return new AppLogger(name);
	}
	
	public AppLogger(String name) {
		super(name);
	}

	@Override
	protected void logStorage(LogLevel level, String message) {
	}
	
}
