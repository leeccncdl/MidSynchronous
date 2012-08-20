package cn.zytec.midsynchronous;

import java.util.EventListener;

/**
   * @ClassName: ITaskManagerEventListener
   * @Description: 任务管理事件监听器接口
   * @author: lee
   * @modify date: 2012-6-27 下午02:32:48
   */
public interface ITaskManagerEventListener extends EventListener {
	
	/** 
	* @Title: taskManagerEvent 
	* @Description: TODO 任务管理事件监听的处理 
	* @param event     
	* @return void
	* @throws 
	*/ 
	
	public void taskManagerEvent (ETaskManagerEvent event);
}
