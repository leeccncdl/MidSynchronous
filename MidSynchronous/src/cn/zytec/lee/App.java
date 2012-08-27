package cn.zytec.lee;

import android.app.Application;

public class App extends Application {
	
	private static App instance;
	
    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
        AppLogger.config(instance);
    }

}
