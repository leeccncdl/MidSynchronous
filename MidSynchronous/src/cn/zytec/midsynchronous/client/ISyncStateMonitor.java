package cn.zytec.midsynchronous.client;

import cn.zytec.midsynchronous.SyncTaskDescription;

/**
   * @ClassName: ISyncStateMonitor 
   * @Description: 同步状态监控接口，应用系统的同步状态监控器需要实现该接口
   * 			         根据接收到的任务状态在应用程序端做相应的反馈处理，继承该接口的类注册到同步控制器
   * 			         由同步控制器 调用触发
   * @author: lee
   * @modify date: 2012-7-13 上午11:14:54
   */
public interface ISyncStateMonitor {
		
	public void clientStateUpdate(SyncTaskDescription.SynTaskState taskState);
	
}
