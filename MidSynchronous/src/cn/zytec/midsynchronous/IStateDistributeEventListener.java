package cn.zytec.midsynchronous;

import java.util.EventListener;

/**
   * @ClassName: IStateDistributeEventListener
   * @Description: 同步状态分发监听接口
   * @author: lee
   * @modify date: 2012-6-27 下午02:29:36
   */
public interface IStateDistributeEventListener extends EventListener {
	
	/** 
	* @Title: stateDistributeEvent 
	* @Description: TODO 同步状态分发事件监听的处理
	* @param event     
	* @return void
	* @throws 
	*/ 
	
	public void stateDistributeEvent(EStateDistributeEvent event);
}
