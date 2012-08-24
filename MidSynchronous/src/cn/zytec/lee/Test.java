package cn.zytec.lee;

import java.util.List;

import cn.zytec.midsynchronous.ClientSyncController;
import cn.zytec.midsynchronous.SyncTaskDescription;
import cn.zytec.midsynchronous.client.CreateTaskDescription;
import cn.zytec.midsynchronous.client.ISyncDataCollect;
import cn.zytec.midsynchronous.client.ISyncDataUpdate;
import cn.zytec.midsynchronous.client.ISyncStateMonitor;


public class Test implements ISyncDataCollect,ISyncDataUpdate,ISyncStateMonitor {

	
	public Test() {
		
	}


	@Override
	public void addSyncDataTransferTask(String taskDataString,List<String> sourceFiles) {
        ClientSyncController client = ClientSyncController.getClientSyncControllerInstance();
        //注册客户端的状态监控器和数据更新处理器
        client.registSyncStateMonitor(this);
        client.registSyncDataUpdate(this);
        //控制器初始化上行数据传输任务 数据文件为Json格式的数据文件，由应用程序自行生成，传入需要发送的数据文件路径即可

        //添加一个上行任务
        client.initSyncUpDataTask(CreateTaskDescription.createUpDescription(App.getInstance(), sourceFiles, taskDataString));
        //添加一个下行任务
        
	}

	
	public void addDownSyncDataTransferTask(String condition) {
        ClientSyncController client = ClientSyncController.getClientSyncControllerInstance();
        //注册客户端的状态监控器和数据更新处理器
        client.registSyncStateMonitor(this);
        client.registSyncDataUpdate(this);
		client.initSyncDownDataTask(CreateTaskDescription.createDownDescription(condition));
	}
	
	public void runTask() {
		ClientSyncController client = ClientSyncController.getClientSyncControllerInstance();
        client.registSyncStateMonitor(this);
        client.registSyncDataUpdate(this);
        client.activeTask();
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
