package cn.zytec.midsynchronous.utils;

import cn.zytec.lee.App;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.provider.Settings;

public class CheckNetworkUtils {
	
	private static ConnectivityManager conMan = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

	public static boolean check3G() {
		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		
		return (mobile == NetworkInfo.State.CONNECTED);
	}
	
	public static boolean checkWifi() {
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		
		return (wifi == NetworkInfo.State.CONNECTED);
	}
	
	public static void wirelessSettingAct() {
		Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		App.getInstance().startActivity(intent);//进入无线网络配置界面
	}

}
