package cn.zytec.midsynchronous.client;

import cn.zytec.midsynchronous.ClientSyncController;
import cn.zytec.midsynchronous.SyncTaskDescription;

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
	
	public void stopAllTask() {
		
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
