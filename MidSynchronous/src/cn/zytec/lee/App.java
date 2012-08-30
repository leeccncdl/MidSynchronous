package cn.zytec.lee;

import java.io.File;

import android.app.Application;
import android.content.Context;

public class App extends Application {
	

	public static Context context;
	/**
	 * 每次上行，下行传输字节数组长度
	 */
	public static final int EVERYTIMETRANSSIZE = 102400;
	
	/**
	 * HttpRequest返回字符串的分割符
	 */
	public static final String SEP = "!@#";
	
	/**
	 * 任务描述文件名，任务描述文件保存在应用的目录下
	 */
	public static final String DESCRIPTIONFILENAME = "Description.taskfile";
	
	/**
	 * 数据文件的后缀名
	 */
	public static final String DATAFILETAG = ".datafile";
	
	/**
	 * 下行资源文件夹路径，保存在SD卡上
	 */
	public static final String DOWNSOURCEFILEPATH = "MidSync"+File.separator+"downLoadsourceFile"+File.separator;
	/**
	 * 上行资源文件夹路径，保存在SD卡上
	 */
	public static final String UPSOURCEFILEPATH = "MidSync"+File.separator+"upwardSourceFile";
    
	/**
	 * 服务器访问地址
	 */
	public static final String HOST = "http://192.168.4.117:8080/MidSynchronous/servlet/ServletEntrance";
	
	/**
	 * 服务器连接和返回超时时间
	 */
	public static final int TIMEOUT = 20000;
	@Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        context = this.getApplicationContext();
        AppLogger.config(this);
    }
    
}
