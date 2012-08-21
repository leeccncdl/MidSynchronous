package cn.zytec.midsynchronous;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;

import android.content.Context;

import cn.zytec.lee.App;
import cn.zytec.midsynchronous.utils.AppFileUtils;
import cn.zytec.midsynchronous.ws.DownwardWs;

/**
 * @ClassName: DSyncDownDataTransfer
 * @Description: 下行同步数据传输器
 * @author: lee
 * @modify date: 2012-6-27 下午02:41:05
 */
public class DSyncDownDataTransfer extends Thread {
	private static final String TAG = "TAG:DSyncDownDataTransferDDDDDDDDDD";
	private static final String SEP = "!@#";
	private static final int EVERYTIMETRANSSIZE = 102400;
	private IDownDataTransferEventListener listener;
	private List<SyncTaskDescription> downTaskDescriptions;// 下行同步数据传输任务列表

	public DSyncDownDataTransfer(ClientSyncController c) {
		downTaskDescriptions = new ArrayList<SyncTaskDescription>();
		listener = c;
	}

	public void addTaskToDownDataTransfer(SyncTaskDescription description) {
		downTaskDescriptions.add(description);
		System.out.println(TAG+"任务添加到下行任务列表中");
	}


	/** 
	* 触发监听器下行任务传输启动事件 
	* @param description   任务描述  
	* @return void
	* @throws 
	*/ 
	
	private void taskTansferStart(SyncTaskDescription description) {
		EDownDataTransferEvent event = new EDownDataTransferEvent(
				this,
				EDownDataTransferEvent.DownDataTraEveDescription.DOWNTASKTRANSFERSTART,
				description);
		listener.downDataTransferEvent(event);
	}

	/** 
	* 触发监听器下行任务传输申请完成事件 
	* @param description     
	* @return void
	* @throws 
	*/ 
	
	private void taskApplyComplete(SyncTaskDescription description) {
		EDownDataTransferEvent event = new EDownDataTransferEvent(
				this,
				EDownDataTransferEvent.DownDataTraEveDescription.DOWNAPPLYCOMPLETE,
				description);
		listener.downDataTransferEvent(event);
	}


	/** 
	* 触发监听器下行任务传输数据接收事件 
	* @param description     
	* @return void
	* @throws 
	*/ 
	
	private void taskDataIncept(SyncTaskDescription description) {
		EDownDataTransferEvent event = new EDownDataTransferEvent(
				this,
				EDownDataTransferEvent.DownDataTraEveDescription.DOWNDATAINCEPT,
				description);
		listener.downDataTransferEvent(event);
	}

	/** 
	* 触发监听器下行任务传输完成事件  
	* @param description     
	* @return void
	* @throws 
	*/ 
	
	private void taskTransferComplete(SyncTaskDescription description) {
		EDownDataTransferEvent event = new EDownDataTransferEvent(
				this,
				EDownDataTransferEvent.DownDataTraEveDescription.DOWNTASKTRANSFERCOMPLETE,
				description);
		listener.downDataTransferEvent(event);
	}

	/** 
	* 触发监听器下行任务传输错误事件  
	* @param description     
	* @return void
	* @throws 
	*/ 
	
	private void taskTransferError(SyncTaskDescription description) {
		EDownDataTransferEvent event = new EDownDataTransferEvent(
				this,
				EDownDataTransferEvent.DownDataTraEveDescription.DOWNTRANSFERERROR,
				description);
		listener.downDataTransferEvent(event);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {

			while (downTaskDescriptions.size() < 1) {
				try {
					System.out.println(TAG+ "下行数据同步线程休眠 5s"+Thread.currentThread().getName());
					sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {

				}
			}
			
			runDownDataTransfer(downTaskDescriptions.iterator());
			
			/*********修改前方法******
			//当前列表里有任务，执行
			SyncTaskDescription description = null;
			Gson gson = null;
			for (int i = 0, size = downTaskDescriptions.size(); i < size; i++) {
				System.out.println(TAG
						+ "任务下载开始啦！！！！"
						+Thread.currentThread().getName());
				description = downTaskDescriptions.get(i);
				//传输开始
				taskTansferStart(description);
				gson = new Gson();
				//请求
				String jsonTask = DownwardWs.DownwardRequest(gson.toJson(description), "lee");
				if(jsonTask==null||jsonTask.equals("")) {
					System.out.println(TAG+"下行申请返回错误");
					taskTransferError(description);
					break;
				}

				String token = jsonTask.split(SEP)[0]+SEP+jsonTask.split(SEP)[1];
//				System.out.println("++++++++++++++"+token+"++++++++++++++");
				description.setAssociateId(token);
				if(jsonTask.split(SEP).length == 3) {
					SyncTaskDescription taskDescription = gson.fromJson(jsonTask.split(SEP)[2], SyncTaskDescription.class);
					updateDescription(description,taskDescription);
				} else {
					System.out.println(TAG+"本次为断点续传");
				}
				//任务申请完成
				taskApplyComplete(description);
				if(tasktaskDataInceptfiles(description)) {
					//传输完成请求
					DownwardWs.DownwardFinish(token);
					//传输完成事件
					taskTransferComplete(description);
					downTaskDescriptions.remove(i);//暂时这么处理，不严谨*****移除下行任务（更改为使用迭代器）
				} else {
					System.out.println(TAG+"当前下行任务数据接收意外终止");
					taskTransferError(description);
					break;
				}
			}************修改前方法结束**************/

		}
	}
	
	private boolean runDownDataTransfer(Iterator<SyncTaskDescription> iterator) {
		SyncTaskDescription description = null;
		Gson gson = new Gson();
		
		while(iterator.hasNext()) {
			description = iterator.next();
			System.out.println(TAG
					+ "任务下载开始啦！！！！"
					+Thread.currentThread().getName());
			//传输开始
			taskTansferStart(description);
			gson = new Gson();
			//请求
			String jsonTask = DownwardWs.DownwardRequest(gson.toJson(description), "lee");
			if(jsonTask==null||jsonTask.equals("")) {
				System.out.println(TAG+"下行申请返回错误");
				taskTransferError(description);
				return false;
			}

			String token = jsonTask.split(SEP)[0]+SEP+jsonTask.split(SEP)[1];
//			System.out.println("++++++++++++++"+token+"++++++++++++++");
			description.setAssociateId(token);
			if(jsonTask.split(SEP).length == 3) {
				SyncTaskDescription taskDescription = gson.fromJson(jsonTask.split(SEP)[2], SyncTaskDescription.class);
				updateDescription(description,taskDescription);
			} else {
				System.out.println(TAG+"本次为断点续传");
			}
			//任务申请完成
			taskApplyComplete(description);
			if(taskDataInceptfiles(description)) {
				//传输完成请求
				DownwardWs.DownwardFinish(token);
				//传输完成事件
				taskTransferComplete(description);
				iterator.remove();//在本类的任务列表中移除该任务
			} else {
				System.out.println(TAG+"当前下行任务数据接收意外终止");
				taskTransferError(description);
				return false;
			}
		}
		return true;
	}

	/** 
	* 接收下行任务所有文件方法 
	* @param description
	* @return     
	* @return boolean
	* @throws 
	*/ 
	
	private boolean taskDataInceptfiles(SyncTaskDescription description) {
		Map<String, SyncFileDescription> fileInfo = description.getFileInfo();
		for (Entry<String, SyncFileDescription> item : fileInfo.entrySet()) {
			String key = item.getKey();
			SyncFileDescription value = item.getValue();
			// 调用单个文件传输方法,判断是数据文件还是资源文件，使用同一的后缀判断
			boolean isSourceFile = !key.endsWith(".sync");
			if (!value.getAuxiliary().equals("Done")) {
		
				if (!inceptFile(key, value, description.getAssociateId(),
						description,isSourceFile)) {
					return false;
				}
			}
		}
		return true;
	}
 
	/** 
	* 接收单个文件 
	* @param fileName
	* @param fileDes
	* @param token
	* @param description
	* @param isSourceFile 是否是资源文件，如果是资源文件，需要保存在SD卡目录 
	* @return boolean
	* @throws 
	*/ 
	
	private boolean inceptFile(String fileName, SyncFileDescription fileDes,
			String token, SyncTaskDescription description, boolean isSourceFile) {

		boolean result = true;
		
		/*******************断点相关处理*********************/
		long transferSize = fileDes.getTransSize();
		byte[] buffer = null;
		int alreadyTransTimes = (int)transferSize/EVERYTIMETRANSSIZE;
		while (true) {
			System.out.println(TAG+fileName+"  Offset"+((EVERYTIMETRANSSIZE * alreadyTransTimes) - 1 == -1 ? 0 : EVERYTIMETRANSSIZE * alreadyTransTimes - 1));
			buffer = DownwardWs.DownwardTransmit(token,fileName,
					((EVERYTIMETRANSSIZE * alreadyTransTimes) - 1 == -1 ? 0 : EVERYTIMETRANSSIZE * alreadyTransTimes - 1), EVERYTIMETRANSSIZE);

			if (buffer!=null && buffer.length != 0) {
				AppFileUtils.writeFile(App.getInstance(), fileName,
						buffer, Context.MODE_APPEND,isSourceFile);
				if(buffer.length != EVERYTIMETRANSSIZE) {
					fileDes.setAuxiliary("Done");
					taskDataIncept(description);
					break;
				} else {
					fileDes.setTransSize(EVERYTIMETRANSSIZE*(alreadyTransTimes+1));
				}
				taskDataIncept(description);
			} else {
				System.out.println("接收数据失败，中止！");
				alreadyTransTimes = 0;
				result = false;
				break;
			}
			alreadyTransTimes++;
		}
		return result;
	}
	
	/** 
	* 更新第一次请求返回的任务描述信息到本地任务描述信息 
	* @param oldDes
	* @param newDes
	* @return     
	* @return boolean
	* @throws 
	*/ 
	
	private boolean updateDescription(SyncTaskDescription oldDes,SyncTaskDescription newDes) {
		
		oldDes.setTaskId(newDes.getTaskId());
		
		Map<String, SyncFileDescription> fileInfo = newDes.getFileInfo();
		
		for(Entry<String, SyncFileDescription> item : fileInfo.entrySet()) {
			SyncFileDescription value = item.getValue();
			value.setAuxiliary("false");
		}
		
		oldDes.setDataInfo(fileInfo);
		return true;
	}
}
