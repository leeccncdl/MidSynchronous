package cn.zytec.midsynchronous;

import java.util.EventListener;

/**
   * @ClassName: IDownDataTransferEventListener
   * @Description: 下行数据传输监听接口
   * @author: lee
   * @modify date: 2012-6-27 下午02:24:32
   */
public interface IDownDataTransferEventListener extends EventListener {
	
	/** 
	* @Title: downDataTransferEvent 
	* @Description: TODO 下行数据传输事件监听的处理
	* @param event     
	* @return void
	* @throws 
	*/ 
	
	public void downDataTransferEvent (EDownDataTransferEvent event);
}
