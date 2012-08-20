package cn.zytec.midsynchronous.client;

import cn.zytec.midsynchronous.SyncTaskDescription;

/**
   * 同步数据收集接口
   * @author: lee
   * @modify date: 2012-8-14 下午12:51:50
   */
public interface ISyncDataCollect {
	
	public void addSyncDataTransferTask (SyncTaskDescription taskDescription);

}
