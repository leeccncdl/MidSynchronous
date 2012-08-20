package cn.zytec.midsynchronous;

import java.util.ArrayList;
import java.util.List;

/**
   * @ClassName: TSyncTaskDescriptions
   * @Description: 任务描述信息列表
   * @author: lee
   * @modify date: 2012-6-27 下午02:57:51
   */
public class TSyncTaskDescriptions {
	

	private int count;
	private List<SyncTaskDescription> arrTaskDescriptions;
	
	public TSyncTaskDescriptions () {
		arrTaskDescriptions = new ArrayList<SyncTaskDescription> (); 
		count = 0;
	}
	
	public SyncTaskDescription searchTaskByIndex (int nIndex) {
		return null;
	}
	
	public SyncTaskDescription searchTaskByTaskId (String taskId) {
		return null;
	}
	
	public boolean exists (String strTaskId) {
		for(int i=0,size = arrTaskDescriptions.size();i<size;i++) {
			if(arrTaskDescriptions.get(i).getTaskId().equals(strTaskId)) {
				return true;
			}
		}
		return false;
	}
	
	public int getCount() {
		return count;
	}

	public List<SyncTaskDescription> getArrTaskDescriptions() {
		return arrTaskDescriptions;
	}

	/** 
	* @Title: add 
	* @Description: TODO 将任务添加到任务列表中，并且写入任务持久化文件中 
	* @param description     
	* @return void
	* @throws 
	*/ 
	
	public void add (SyncTaskDescription description) {
		arrTaskDescriptions.add(description);
		count++;
		System.out.println(Thread.currentThread().getName()+"TSyncTaskDescriptions:任务添加到任务列表中，并且写入任务列表持久化文件中");
		//写入任务持久化文件 应该单独写一个接口，在这调用下。因为任务状态变化的时候同样也要写入任务持久化文件
	}
	

	
	public void remove (String strTaskId) {
		for (int i=0,size = arrTaskDescriptions.size();i<size;i++) {
			if(arrTaskDescriptions.get(i).getTaskId().equals(strTaskId)) {
				arrTaskDescriptions.remove(i);
				count--;
				return;
			}
		}
	}
	
	public void removeAt (int nIndex) {
		
	}
	
	public void clear () {
		
	}
}
