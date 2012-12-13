package cn.zytec.midsynchronous;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.zytec.lee.AppLogger;

/**
   * @ClassName: DSyncUpdateDataDispatch
   * @Description: 数据更新调度器
   * @author: lee
   * @modify date: 2012-6-27 下午02:39:55
   */
public class DSyncUpdateDataDispatch extends Thread {
	
	private AppLogger log = AppLogger.getLogger("DSyncUpdateDataDispatch");

	private IUpdateDataEventListener listener;
	private List<SyncTaskDescription> updateDataDescriptions;

	public DSyncUpdateDataDispatch (ClientSyncController c) {
		this.listener = c;
		updateDataDescriptions = new ArrayList<SyncTaskDescription> ();
	}
	
	public void addUpdateDataDispatchTask (SyncTaskDescription taskDescription) {
		synchronized(this) {
			if(this.getState() == Thread.State.WAITING) {
				updateDataDescriptions.add(taskDescription);
				this.notify();
			}
		}
	}
	
	/** 
	* @Title: updateExcute 
	* @Description:  更新执行      
	* @return void
	* @throws 
	*/ 
	
	private void updateExcute (SyncTaskDescription taskDescription) {
		EUpdateDataEvent event = new EUpdateDataEvent(this, EUpdateDataEvent.UpdateDataEveDescription.UPDATEEXCUTE,taskDescription);
		listener.updateDataEvent(event);
	}
	
	/** 
	* @Title: updateComplete 
	* @Description:  更新完成   
	* @return void
	* @throws 
	*/ 
	
	private void updateComplete (SyncTaskDescription taskDescription) {
		EUpdateDataEvent event = new EUpdateDataEvent(this, EUpdateDataEvent.UpdateDataEveDescription.UPDATECOMPLETE,taskDescription);
		listener.updateDataEvent(event);
	}
	
	/** 
	* @Title: updateError 
	* @Description: 更新错误      
	* @return void
	* @throws 
	*/
	
	private void updateError (SyncTaskDescription taskDescription) {
		EUpdateDataEvent event = new EUpdateDataEvent(this,EUpdateDataEvent.UpdateDataEveDescription.UPDATEERROR,taskDescription);
		listener.updateDataEvent(event);
	}

	@Override
	public void run() {
		if(log.isDebugEnabled()) {
			log.debug("数据更新调度器线程运行！"+Thread.currentThread().getName());
		}
		while(true) {
			synchronized(this) {	
				while(updateDataDescriptions.size()>0) {
					runSyncUpdateDataDispatch(updateDataDescriptions.iterator());
				}
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private boolean runSyncUpdateDataDispatch(Iterator<SyncTaskDescription> iterator) {
		SyncTaskDescription description = null;
		
		while(iterator.hasNext()) {
			description = iterator.next();
			//更新执行和更新完成需要返回值，需修改
			updateExcute(description);
			updateComplete(description);
//			updateError(description);
			iterator.remove();
		}
		
		return true;
	}
}
