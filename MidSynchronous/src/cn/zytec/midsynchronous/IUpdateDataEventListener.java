package cn.zytec.midsynchronous;

import java.util.EventListener;

/**
   * @ClassName: IUpdateDataEventListener
   * @Description: 更新数据事件监听的接口
   * @author: lee
   * @modify date: 2012-6-27 下午02:35:58
   */
public interface IUpdateDataEventListener extends EventListener {
	
	/** 
	* @Title: updateDataEvent 
	* @Description: 更新数据事件监听的处理 
	* @param event     
	* @return void
	* @throws 
	*/ 
	
	public void updateDataEvent (EUpdateDataEvent event);
}
