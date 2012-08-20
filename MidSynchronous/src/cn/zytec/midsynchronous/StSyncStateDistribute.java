package cn.zytec.midsynchronous;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
   * @ClassName: StSyncStateDistribute
   * @Description: 同步状态分发器
   * @author: lee
   * @modify date: 2012-6-27 下午02:37:18
   */
public class StSyncStateDistribute extends Thread{
	
	private static final String TAG = "TAG:StSyncStateDistribute";
	
	private IStateDistributeEventListener listener;
	private List<SyncTaskDescription> distributeDescriptions;
	
	public StSyncStateDistribute(ClientSyncController c) {
		listener = c;
		distributeDescriptions = new ArrayList<SyncTaskDescription> (); 
	}
	
	//20120724添加修改  任务状态列表添加新状态时判断列表中是否已经存在该任务，如果存在无需重复添加，原先指向的任务描述状态已经修改为最新状态
	public void addStateDistribute(SyncTaskDescription description) {
		if(distributeDescriptions.size()!=0) {
			for(int i=0;i<distributeDescriptions.size();i++) {
				if(description != distributeDescriptions.get(i)) {//直接比较地址，判断是不是同一个对象
					distributeDescriptions.add(description);
				}
			}
		} else {
			distributeDescriptions.add(description);
		}
		System.out.println(TAG+"任务状态分发器：添加新的任务状态列表,添加时的状态为："+description.getTaskState()+Thread.currentThread().getName());
	}
	
	/** 
	* @Title: stateDiatribute 
	* @Description: TODO 状态分发      
	* @return void
	* @throws 
	*/ 
	
	private void stateDistribute (SyncTaskDescription description) {
		EStateDistributeEvent event = new EStateDistributeEvent(this,description);
		listener.stateDistributeEvent(event);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(TAG+"同步状态分发线程运行！"+Thread.currentThread().getName());
		
		while (true) {
			
			while(distributeDescriptions.size()<1) {
				try{
					System.out.println(Thread.currentThread().getName()+"同步状态分发线程进入休眠状态");
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					
				}
			}
			
			runSyncStateDistribute(distributeDescriptions.iterator());
			/****************
			for(int i = 0,size = distributeDescriptions.size(); i < size; i++) {

				System.out.println(Thread.currentThread().getName()+"同步状态分发任务执行！！！任务名称："+distributeDescriptions.get(i).getTaskName());
				stateDistribute(distributeDescriptions.get(i));
			}*******************/
			
		}
		
	}

	private boolean runSyncStateDistribute(Iterator<SyncTaskDescription> iterator) {
		SyncTaskDescription description = null;
		while(iterator.hasNext()) {
			description = iterator.next();
			System.out.println(Thread.currentThread().getName()+"同步状态分发任务执行！！");
			stateDistribute(description);
			iterator.remove();
		}
		
		
		return true;
	}
}
