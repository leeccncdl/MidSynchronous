package cn.zytec.midsynchronous;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
   * @ClassName: DSyncUpdateDataDispatch
   * @Description: 数据更新调度器
   * @author: lee
   * @modify date: 2012-6-27 下午02:39:55
   */
public class DSyncUpdateDataDispatch extends Thread {
	
	private static final String TAG = "TAG:DSyncUpdateDataDispatch";

	private IUpdateDataEventListener listener;	
	private List<SyncTaskDescription> updateDataDescriptions;

	public DSyncUpdateDataDispatch(ClientSyncController c) {
		this.listener = c;
		updateDataDescriptions = new ArrayList<SyncTaskDescription> ();
	}
	
	public void addUpdateDataDispatchTask (SyncTaskDescription taskDescription) {
		updateDataDescriptions.add(taskDescription);
	}
	
	/** 
	* @Title: updateExcute 
	* @Description: TODO 更新执行      
	* @return void
	* @throws 
	*/ 
	
	private void updateExcute (SyncTaskDescription taskDescription) {
		EUpdateDataEvent event = new EUpdateDataEvent(this, EUpdateDataEvent.UpdateDataEveDescription.UPDATEEXCUTE,taskDescription);
		listener.updateDataEvent(event);
	}
	
	/** 
	* @Title: updateComplete 
	* @Description: TODO  更新完成   
	* @return void
	* @throws 
	*/ 
	
	private void updateComplete (SyncTaskDescription taskDescription) {
		EUpdateDataEvent event = new EUpdateDataEvent(this, EUpdateDataEvent.UpdateDataEveDescription.UPDATECOMPLETE,taskDescription);
		listener.updateDataEvent(event);
	}
	
	/** 
	* @Title: updateError 
	* @Description: TODO 更新错误      
	* @return void
	* @throws 
	*/
	
	private void updateError (SyncTaskDescription taskDescription) {
		EUpdateDataEvent event = new EUpdateDataEvent(this,EUpdateDataEvent.UpdateDataEveDescription.UPDATEERROR,taskDescription);
		listener.updateDataEvent(event);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(TAG+ "数据更新调度器线程运行！"+Thread.currentThread().getName());
		while (true) {
			
			while(updateDataDescriptions.size()<1) {
				try{
					System.out.println(TAG+"下行数据更新调度线程休眠"+Thread.currentThread().getName());
					sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					
				}
			}
			
			runSyncUpdateDataDispatch(updateDataDescriptions.iterator());
			/**************
			SyncTaskDescription description = null;
			for(int i = 0, size = updateDataDescriptions.size(); i < size; i++) {
				description = updateDataDescriptions.get(i);
				//更新执行和更新完成需要返回值，需修改
				updateExcute(description);
				updateComplete(description);
				updateError(description);
				updateDataDescriptions.remove(i);//////////////////处理不够妥当
			}****************************/

		}
	}
	
	private boolean runSyncUpdateDataDispatch(Iterator<SyncTaskDescription> iterator) {
		SyncTaskDescription description = null;
		
		while(iterator.hasNext()) {
			description = iterator.next();
			//更新执行和更新完成需要返回值，需修改
			updateExcute(description);
			updateComplete(description);
			updateError(description);
			iterator.remove();
		}
		
		return true;
	}
}
