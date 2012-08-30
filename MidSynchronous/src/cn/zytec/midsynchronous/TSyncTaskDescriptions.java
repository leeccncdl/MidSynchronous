package cn.zytec.midsynchronous;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.zytec.lee.App;
import cn.zytec.midsynchronous.utils.AppFileUtils;

/**
   * @ClassName: TSyncTaskDescriptions
   * @Description: 任务描述信息列表
   * @author: lee
   * @modify date: 2012-6-27 下午02:57:51
   */
public class TSyncTaskDescriptions {
	

	private int count;
	private List<SyncTaskDescription> arrTaskDescriptions;
	
	public void setCount(int count) {
		this.count = count;
	}

	public void setArrTaskDescriptions(List<SyncTaskDescription> arrTaskDescriptions) {
		this.arrTaskDescriptions = arrTaskDescriptions;
	}

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
				//删除数据文件方法
//				clearDataFile(arrTaskDescriptions.get(i));
				arrTaskDescriptions.remove(i);
				count--;
				return;
			}
		}
	}
	
	public void removeAt (int nIndex) {
		
	}
	
	public void clearDataFile (SyncTaskDescription taskDescription) {
		Map<String, SyncFileDescription> fileInfo = taskDescription.getFileInfo();
		
		for (Entry<String, SyncFileDescription> item : fileInfo.entrySet()) {
			String key = item.getKey();
			
			if(key.endsWith(App.DATAFILETAG)) {
				AppFileUtils.deleteFile(App.context, key);
			}
		}
	}
}
