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
	 * 任务描述文件名，任务描述文件保存在应用的目录下,SD卡中不可见
	 */
	public static final String DESCRIPTIONFILENAME = "Description.taskfile";
	
	/**
	 * 数据文件的后缀名，数据文件保存在应用的目录下，任务下载完成以字符串的形式返回给客户端更新接口，完成更新后被自动删除
	 */
	public static final String DATAFILETAG = ".datafile";
	
	/**
	 * 下行任务资源文件夹路径，文件保存在SD卡上（MidSync/downLoadsourceFile/）
	 */
	public static final String DOWNSOURCEFILEPATH = "MidSync"+File.separator+"downLoadsourceFile"+File.separator;
	/**
	 * 上行任务资源文件夹路径，文件保存在SD卡上（MidSync/upwardSourceFile/）
	 */
	public static final String UPSOURCEFILEPATH = "MidSync"+File.separator+"upwardSourceFile";
    
	/**
	 * 服务器访问地址
	 */
	public static final String HOST = "http://192.168.6.82:8080/MidSynchronous/servlet/ServletEntrance";
	
	/**
	 * 服务器连接和返回超时时间
	 */
	public static final int TIMEOUT = 20000;
	
	
	/**
	 * 服务器身份认证信息
	 */
	public static String USER = "liming";
	public static String PASSWORD = "PASS";
	
	
	@Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        AppLogger.config(this);
    }
    
}
