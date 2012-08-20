package cn.zytec.midsynchronous;

import java.util.EventListener;

/**
   * @ClassName: IUpDataTransferEventListener
   * @Description: 上行数据传输事件监听器接口
   * @author: lee
   * @modify date: 2012-6-27 下午02:34:15
   */
public interface IUpDataTransferEventListener extends EventListener {
	
	/** 
	* @Title: upDataTransferEvent 
	* @Description: TODO 上行数据传输事件监听的处理
	* @param event     
	* @return void
	* @throws 
	*/ 
	
	public void upDataTransferEvent (EUpDataTransferEvent event);
}
