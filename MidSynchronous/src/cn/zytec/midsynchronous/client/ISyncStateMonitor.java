package cn.zytec.midsynchronous.client;

import cn.zytec.midsynchronous.SyncTaskDescription;

/**
   * 客户应用程序状态监控接口
   * @author: lee
   * @modify date: 2012-8-29 下午03:59:03
   */
public interface ISyncStateMonitor {

	/**
	   * 中间件状态异常代码枚举类
	   * @author: lee
	   * @modify date: 2012-8-29 下午03:58:42
	   */
	public enum StateExceptionCode {
		SER_AUTH_FAIL,//
		SER_SESSION_INVALID,
		HTTP_STATUS_EXCEP;
	}
	
	/** 
	* 客户应用程序状态常规状态更新 
	* @param taskState  任务执行状态
	* @return void    
	* @throws 
	*/ 
	
	public void clientStateUpdate(SyncTaskDescription.SynTaskState taskState);
	
	/** 
	* 客户应用程序异常状态监控 
	* @param exceptionCode 异常描述代码
	* @return void    
	* @throws 
	*/ 
	
	public void clientStateException (ISyncStateMonitor.StateExceptionCode exceptionCode);

}
