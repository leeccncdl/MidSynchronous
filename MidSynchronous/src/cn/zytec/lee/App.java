package cn.zytec.lee;

import android.app.Application;
import android.content.Context;

public class App extends Application {
	

	public static Context context;
	
//    public static Context getInstance() {
//        return context;
//    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        context = this.getApplicationContext();
        AppLogger.config(this);
    }
    
}
