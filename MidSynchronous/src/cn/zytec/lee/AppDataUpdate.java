package cn.zytec.lee;

import java.util.List;

import cn.zytec.midsynchronous.client.ISyncDataUpdate;

public class AppDataUpdate implements ISyncDataUpdate{

	@Override
	public void clientDataUpdate(String taskDataString, List<String> sourceFiles) {
		// TODO Auto-generated method stub
		System.out.println("CCCCCCCCCCCCCCCCCC:"+"应用程序数据更新执行");
	}

}
