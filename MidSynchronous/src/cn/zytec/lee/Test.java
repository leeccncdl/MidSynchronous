package cn.zytec.lee;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.zytec.midsynchronous.ClientSyncController;
import cn.zytec.midsynchronous.SyncTaskDescription;
import cn.zytec.midsynchronous.client.CreateTaskDescription;
import cn.zytec.midsynchronous.client.ISyncDataCollect;
import cn.zytec.midsynchronous.client.ISyncDataUpdate;
import cn.zytec.midsynchronous.client.ISyncStateMonitor;



import com.google.gson.Gson;

public class Test implements ISyncDataCollect,ISyncDataUpdate,ISyncStateMonitor {

	
	public Test() {
		
	}


	@Override
	public void addSyncDataTransferTask(SyncTaskDescription taskDescription) {
        ClientSyncController client = ClientSyncController.getClientSyncControllerInstance();
        //注册客户端的状态监控器和数据更新处理器
        client.registSyncStateMonitor(this);
        client.registSyncDataUpdate(this);
        //控制器初始化上行数据传输任务 数据文件为Json格式的数据文件，由应用程序自行生成，传入需要发送的数据文件路径即可
        
        TestDao testObj = new TestDao(1, 24, "张三", "85");
        TestDao testObj2 = new TestDao(1, 24, "李四", "88");
        
        ArrayList<TestDao> testArr = new ArrayList<TestDao>();
        testArr.add(testObj);
        testArr.add(testObj2);
        
        
		Gson gson = new Gson(); 
		String gsonString = gson.toJson(testArr);
        List<String> sourceFiles = new ArrayList<String>();
        sourceFiles.add("sourcefile1.png");
        sourceFiles.add("sourcefile2.JPG");
        //添加一个上行任务
        client.initSyncUpDataTask(CreateTaskDescription.createUpDescription(App.getInstance(), sourceFiles, gsonString));
        //添加一个下行任务
        client.initSyncDownDataTask(CreateTaskDescription.createDownDescription("condition"));
	
	}

	@Override
	public void clientDataUpdate(String dataFilepath) {
		// TODO Auto-generated method stub
	}


	@Override
	public void clientStateUpdate(SyncTaskDescription.SynTaskState taskState) {
		// TODO Auto-generated method stub
		
	}
	
}
