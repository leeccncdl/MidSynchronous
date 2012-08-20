package cn.zytec.midsynchronous;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.zytec.lee.App;
import cn.zytec.midsynchronous.utils.AppFileUtils;
import cn.zytec.midsynchronous.ws.UpwardWs;

import com.google.gson.Gson;

/**
 * @ClassName: DSyncUpDataTransfer
 * @Description: 上行同步数据传输器
 * @author: lee
 * @modify date: 2012-6-27 下午02:40:35
 */
public class DSyncUpDataTransfer extends Thread {

	private static final String TAG = "TAG:DSyncUpDataTransferUUUUUUUUUU";
	private static final String SEP = "!@#";
	private static final int EVERYTIMETRANSSIZE = 102400;

	private IUpDataTransferEventListener listener;
	private List<SyncTaskDescription> upTaskDescriptions;// 上行同步数据传输任务描述列表

	public DSyncUpDataTransfer(ClientSyncController c) {
		upTaskDescriptions = new ArrayList<SyncTaskDescription>();
		listener = c;
	}

	public void addTaskToUpDataTransfer(SyncTaskDescription description) {
		upTaskDescriptions.add(description);
		System.out.println(TAG + "任务添加到上行数据传输任务列表中"
				+ Thread.currentThread().getName());
	}

	/**
	 * 触发监听器上行任务开始事件
	 * 
	 * @param taskDescription
	 * @return void
	 * @throws
	 */

	private void taskTansferStart(SyncTaskDescription taskDescription) {
		EUpDataTransferEvent event = new EUpDataTransferEvent(
				this,
				EUpDataTransferEvent.UpDataTraEveDescription.UPTASKTRANSFERSTART,
				taskDescription);
		listener.upDataTransferEvent(event);
	}

	/**
	 * 触发监听器上行任务申请完成事件
	 * 
	 * @param taskDescription
	 * @return void
	 * @throws
	 */

	private void taskApplyComplete(SyncTaskDescription taskDescription) {
		EUpDataTransferEvent event = new EUpDataTransferEvent(this,
				EUpDataTransferEvent.UpDataTraEveDescription.UPAPPLYCOMPLETE,
				taskDescription);
		listener.upDataTransferEvent(event);
	}

	/**
	 * 触发监听器上行任务数据传输事件
	 * 
	 * @param taskDescription
	 * @return void
	 * @throws
	 */

	private void taskDataSend(SyncTaskDescription taskDescription) {
		EUpDataTransferEvent event = new EUpDataTransferEvent(this,
				EUpDataTransferEvent.UpDataTraEveDescription.UPDATASEND,
				taskDescription);
		listener.upDataTransferEvent(event);
	}

	/**
	 * 触发监听器上行任务传输完成事件
	 * 
	 * @param taskDescription
	 * @return void
	 * @throws
	 */

	private void taskTransferComplete(SyncTaskDescription taskDescription) {
		EUpDataTransferEvent event = new EUpDataTransferEvent(
				this,
				EUpDataTransferEvent.UpDataTraEveDescription.UPTASKTRANSFERCOMPLETE,
				taskDescription);
		listener.upDataTransferEvent(event);
	}

	/**
	 * 触发监听器上行任务传输错误事件
	 * 
	 * @param taskDescription
	 * @return void
	 * @throws
	 */

	private void taskTransferError(SyncTaskDescription taskDescription) {
		EUpDataTransferEvent event = new EUpDataTransferEvent(this,
				EUpDataTransferEvent.UpDataTraEveDescription.UPTRANSFERERROR,
				taskDescription);
		listener.upDataTransferEvent(event);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			while (upTaskDescriptions.size() == 0) {
				try {
					System.out.println(Thread.currentThread().getName() + TAG
							+ "当前无上行同步任务，上行线程任务进入休眠状态 3s");
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {

				}
			}
			
			//修改后的使用迭代器方式
			runUpDataTransfer(upTaskDescriptions.iterator());
			
			/**********之前的方式*******************
			SyncTaskDescription description = null;
			Gson gson = null;
			for (int i = 0, size = upTaskDescriptions.size(); i < size; i++) {
				description = runUpDataTransfer(i);
				System.out.println(TAG + "当前有上行同步任务，任务ID为："
						+ description.getTaskId()
						+ Thread.currentThread().getName());
				// 任务启动事件
				taskTansferStart(description);

				gson = new Gson();
				// 上行请求返回token
				String token = null;
				token = UpwardWs.UpwardRequest(gson.toJson(description), "lee");// 用户验证信息如何传入？？
				System.out.println(TAG + "Token：" + token);
				if(token==null) {
					System.out.println(TAG+"Request 返回token失败，终止请求");
					break;
				}
				String newTaskId = token.split(SEP)[0];
				System.out.println(TAG + "newTaskId:" + newTaskId);
				description.setTaskId(newTaskId);

				// 将token保存在description中，任务申请完成
				description.setAssociateId(token);
				taskApplyComplete(description);// 任务申请完成事件

				// 数据传输,返回true标识传输完毕
				if (transmitDescriptionDataFile(description)) {// 任务文件传输
					UpwardWs.UpwardFinish(description.getAssociateId(), true);
					taskTransferComplete(description);// 任务传输结束事件
					// **********当多个上行任务时，这里有可能有问题******************
					upTaskDescriptions.remove(i);
				} else {
					System.out.println(TAG + "当前任务上行数据传输没有完成，已终止");
					break;
				}
			}
			// 暂时先不清空任务！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
			// upTaskDescriptions.clear();
			暂时保留之前的处理方式********************************/
		}


	}

	/** 
	* 执行上行任务的传输过程
	* @param iterator 任务集合的迭代器对象
	* @return     
	* @return boolean 任务是否正确执行结束
	* @throws 
	*/ 
	
	private boolean runUpDataTransfer(Iterator<SyncTaskDescription> iterator) {
		SyncTaskDescription description = null;
		Gson gson = new Gson();
		while(iterator.hasNext()) {
			description = iterator.next();
			System.out.println(TAG + "当前有上行同步任务，任务ID为："
					+ description.getTaskId()
					+ Thread.currentThread().getName());
			// 任务启动事件
			taskTansferStart(description);

			// 上行请求返回token
			String token = null;
			token = UpwardWs.UpwardRequest(gson.toJson(description), "lee");// 用户验证信息如何传入？？
			System.out.println(TAG + "Token：" + token);
			if(token==null) {
				System.out.println(TAG+"Request 返回token失败，终止请求");
				return false;
			}
			String newTaskId = token.split(SEP)[0];
			System.out.println(TAG + "newTaskId:" + newTaskId);
			description.setTaskId(newTaskId);

			// 将token保存在description中，任务申请完成
			description.setAssociateId(token);
			taskApplyComplete(description);// 任务申请完成事件

			// 数据传输,返回true标识传输完毕
			if (transmitDescriptionDataFile(description)) {// 任务文件传输
				UpwardWs.UpwardFinish(description.getAssociateId(), true);
				taskTransferComplete(description);// 任务传输结束事件
				//移除本类列表中的该任务
				iterator.remove();
			} else {
				System.out.println(TAG + "当前任务上行数据传输没有完成，已终止");
				return false;
			}
		}	
		return true;
	}
	
	/**
	 * 上行同步任务数据传输 传输任务数据文件方法，一次取出需要传输的数据文件和资源文件，调用监听器方法，记录传输状态
	 * 
	 * @param description
	 *            任务描述对象
	 * @return void
	 * @throws
	 */

	private boolean transmitDescriptionDataFile(SyncTaskDescription description) {

		Map<String, SyncFileDescription> fileInfo = description.getFileInfo();

		for (Entry<String, SyncFileDescription> item : fileInfo.entrySet()) {
			String key = item.getKey();
			SyncFileDescription value = item.getValue();
			// System.out.println(key+"KKKKKKKKKKKKKKKKKKKKKKKKK");
			// System.out.println(value+"VVVVVVVVVVVVVVVVVVVVVVVVVV");
			// 调用单个文件传输方法
			if (!value.getAuxiliary().equals("Done")) {
				if (!transFile(key, value, description.getAssociateId(),
						description,false)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 传输单个文件分段传输方法
	 * 
	 * @param fileName
	 *            文件名
	 * @param fileDes
	 *            文件描述信息
	 * @param token
	 *            令牌
	 * @return boolean 执行状态
	 * @throws
	 */

	private boolean transFile(String fileName, SyncFileDescription fileDes,
			String token, SyncTaskDescription description, boolean isSourceFile) {

		byte[] buffer = new byte[EVERYTIMETRANSSIZE];
		long length = fileDes.getFileSize();

		/******** 处理断点情况 *************************/
		long transSize = fileDes.getTransSize();
		int transTimes = (int) transSize / EVERYTIMETRANSSIZE;
		/******** 处理断点情况结束 *************************/

		// 总次数 - 已传输次数
		double times = Math.ceil(length / (double) EVERYTIMETRANSSIZE)
				- transTimes;// 有断点相关修改

		System.out.println(TAG + "需要传输的次数：" + times);
		System.out.println("INT" + (int) times);

		for (int i = 0; i < times; i++) {
			if ((times - 1) == i) {// 最后一次传输

				int lastlength = (int) (length - EVERYTIMETRANSSIZE
						* (times + transTimes - 1));// 有断点相关修改
				System.out.println("LASTLENGTH" + lastlength);

				byte[] bufferLast = new byte[lastlength];
				bufferLast = AppFileUtils.readFile(App.getInstance(), fileName,
						EVERYTIMETRANSSIZE * (i + transTimes), lastlength, "r",false);// 有断点相关修改
				/*******************/
				// AppFileUtils.writeFile(App.getInstance(), fileName+".t",
				// bufferLast, Context.MODE_APPEND);
				// ///////////////////////////////////////////////
				if (UpwardWs.UpwardTransmit(token, fileName,
						EVERYTIMETRANSSIZE * (i + transTimes), bufferLast)
						.equals("1")) {// 有断点相关修改
					fileDes.setTransSize(EVERYTIMETRANSSIZE * (i + transTimes)
							+ lastlength);// 有断点相关修改
					taskDataSend(description);// 控制器控制其他线程写入文件
				} else {
					System.out.println(TAG
							+ "***********上行传输返回值不为 1************");
					taskTransferError(description);// 暂时没有具体处理
					return false;
					// break;
				}

				if (UpwardWs.UpwardTransmit(token, fileName, -1, null).equals(
						"1")) {
					fileDes.setAuxiliary("Done");
					taskDataSend(description);// 控制器控制其他线程写入文件
				} else {
					System.out.println(TAG
							+ "***********上行传输返回值不为 1************");
					taskTransferError(description);// 暂时没有具体处理
					return false;
					// break;
				}
			} else {
				// 其他正常情况
				buffer = AppFileUtils.readFile(App.getInstance(), fileName,
						((EVERYTIMETRANSSIZE * (i + transTimes)) - 1 == -1 ? 0
								: EVERYTIMETRANSSIZE * (i + transTimes) - 1),
						EVERYTIMETRANSSIZE, "r",false);// ///
				if (UpwardWs.UpwardTransmit(
						token,
						fileName,
						((EVERYTIMETRANSSIZE * (i + transTimes)) - 1 == -1 ? 0
								: EVERYTIMETRANSSIZE * (i + transTimes) - 1),
						buffer).equals("1")) {// //////////////
					fileDes.setTransSize(EVERYTIMETRANSSIZE
							* ((i + transTimes) + 1));// //////////////
					taskDataSend(description);// 控制器控制其他线程写入文件
				} else {
					System.out.println(TAG
							+ "***********上行传输返回值不为 1************");
					taskTransferError(description);
					return false;
					// break;
				}
			}
		}
		return true;
	}
}
