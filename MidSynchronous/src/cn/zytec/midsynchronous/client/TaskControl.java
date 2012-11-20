package cn.zytec.midsynchronous.client;

import cn.zytec.lee.App;
import cn.zytec.midsynchronous.ClientSyncController;
import cn.zytec.midsynchronous.SyncTaskDescription;
import cn.zytec.midsynchronous.utils.AppFileUtils;

/**
   * 任务控制类，控制任务的启动，应用程序的模块注册
   * @author: lee
   * @modify date: 2012-8-29 下午04:02:56
   */
public class TaskControl {
	
	ClientSyncController client = ClientSyncController.getClientSyncControllerInstance();
	
	/** 
	* 开始一个上行任务 
	* @param taskDescription 任务描述对象
	* @return void    
	* @throws 
	*/ 
	public void startUpTransTask(SyncTaskDescription taskDescription) {
		client.initSyncUpDataTask(taskDescription);
	}
	
	/** 
	* 开始一个下行任务
	* @param taskDescription 任务描述对象
	* @return void    
	* @throws 
	*/ 
	public void startDownTransTask(SyncTaskDescription taskDescription) {
		client.initSyncDownDataTask(taskDescription);
	}
	
	/** 
	* 开始未完成的任务
	* @param taskDescription 任务描述对象
	* @return void    
	* @throws 
	*/ 
	public void startUnfinishedTask() {
		client.startUnfinishedTask();
	}
	
	
	/** 
	* @Description: 清空所有任务（删除任务描述文件），如果需要调用该方法，应该在启动任务执行之前调用，避免中间件在执行过程中删除文件可能产生异常  
	* @return void    
	* @throws 
	*/ 
	public void clearAllTask() {
		AppFileUtils.deleteFile(App.context, App.DESCRIPTIONFILENAME);
	}
	
	/** 
	* 注册同步状态监控器 
	* @param stateMonitor 同步状态监控器对象（需要实现状态监控接口）
	* @return void    
	* @throws 
	*/ 
	public void registSyncStateMonitor(ISyncStateMonitor stateMonitor) {
		ClientSyncController.registSyncStateMonitor(stateMonitor);
	}
	
	/** 
	* 注册数据更新器
	* @param dataUpdate 数据更新器对象（需要实现数据更新接口）
	* @return void    
	* @throws 
	*/ 
	public void registSyncDataUpdate(ISyncDataUpdate dataUpdate) {
		client.registSyncDataUpdate(dataUpdate);
	}
	
}
