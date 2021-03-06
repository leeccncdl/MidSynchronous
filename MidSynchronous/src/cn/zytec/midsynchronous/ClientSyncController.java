package cn.zytec.midsynchronous;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.zytec.lee.App;
import cn.zytec.lee.AppLogger;
import cn.zytec.midsynchronous.client.ISyncDataUpdate;
import cn.zytec.midsynchronous.client.ISyncStateMonitor;
import cn.zytec.midsynchronous.utils.AppFileUtils;

/**
   * @ClassName  ClientSyncController
   * @Description 客户端同步控制器，负责中间件协调运行的中心组件
   * @author lee
   * @modify date: 2012-6-27 下午02:39:02
   */
public class ClientSyncController implements IDownDataTransferEventListener,IStateDistributeEventListener,ITaskManagerEventListener,IUpDataTransferEventListener,IUpdateDataEventListener {
	
	private AppLogger log = AppLogger.getLogger("ClientSyncController");
	
	private static ClientSyncController clientSyncControllerInstance;
	public static ClientSyncController getClientSyncControllerInstance() {
		if(clientSyncControllerInstance == null) {
			clientSyncControllerInstance = new ClientSyncController();
		}
		return clientSyncControllerInstance;
	}

	
	/*****************************应用程序数据更新和状态监控模块注册***************************/
	private ISyncDataUpdate dataUpdate = null;
	private static ISyncStateMonitor stateMonitor = null;
	
	/** 
	* 注册同步数据更新器
	* @param dataUpdate  要注册的数据更新器对象
	* @return void
	* @throws 
	*/ 
	public void registSyncDataUpdate(ISyncDataUpdate dataUpdate) {
		this.dataUpdate = dataUpdate;
	}
	
	/** 
	* 注册同步状态监控器 
	* @param stateMonitor 要注册的状态监控器对象    
	* @return void
	* @throws 
	*/  
	public static void registSyncStateMonitor(ISyncStateMonitor stateMonitor) {
		ClientSyncController.stateMonitor = stateMonitor;
	}
	/*******************************注册结束***************************************/
	
	
	private TSyncTaskManager taskManager = null;
	private StSyncStateDistribute stateDistribute = null;
	private DSyncUpdateDataDispatch updateDataDispatch = null;
	private DSyncUpDataTransfer upDataTransfer = null;
	private DSyncDownDataTransfer downDataTransfer = null;
	
	private ClientSyncController () {
		upDataTransfer = new DSyncUpDataTransfer(this);
		downDataTransfer = new DSyncDownDataTransfer(this);
		stateDistribute = new StSyncStateDistribute(this);
		taskManager = new TSyncTaskManager(this);
		updateDataDispatch = new DSyncUpdateDataDispatch(this);
		
		upDataTransfer.start();
		downDataTransfer.start();
		stateDistribute.start();
		updateDataDispatch.start();
	}
	

	/** 
	* 执行任务文件中未执行完的任务
	* @return void
	* @throws 
	*/ 
	
	public void startUnfinishedTask() {
		taskManager.LoadTask();
	}
	
	/** 
	* 创建上行同步任务，使用传入的任务描述对象，由控制任务管理器创建该任务 
	* @param taskDescription 任务描述对象  
	* @return void
	* @throws 
	*/ 
	
	public void initSyncUpDataTask (SyncTaskDescription taskDescription) {
		taskManager.create(taskDescription);
		if(log.isDebugEnabled()) {
			log.debug("创建上行新任务成功"+Thread.currentThread().getName());
		}
	}
	
	/** 
	* 创建下行同步任务，使用传入的任务描述对象，由控制任务管理器创建该任务
	* @param taskDescription 任务描述对象 
	* @return void
	* @throws 
	*/  
	
	public void initSyncDownDataTask(SyncTaskDescription taskDescription) {
		taskManager.create(taskDescription);
		if(log.isDebugEnabled()) {
			log.debug("创建下行新任务成功"+Thread.currentThread().getName());
		}
	}
	
	
	/******************************作为监听器，处理触发事件的方法*********************************/
	@Override
	public void updateDataEvent(EUpdateDataEvent event) {

		EUpdateDataEvent.UpdateDataEveDescription eveDescription = event.getUpDateDataEventDescription();
		SyncTaskDescription taskDescription = event.getTaskDescription();
		switch (eveDescription) {
		case UPDATEEXCUTE:
			updateExcute(taskDescription);
			break;
		case UPDATECOMPLETE:
			updateComplete(taskDescription);
			break;
		case UPDATEERROR:
			updateError (taskDescription);
			break;
		default:
			if(log.isDebugEnabled()) {
				log.debug("数据更新事件对象传输错误");
			}
			break;
		}
	}

	
	@Override
	public void upDataTransferEvent(EUpDataTransferEvent event) {

		EUpDataTransferEvent.UpDataTraEveDescription eveDescription = event.getUpDatTraEveDescription();
		SyncTaskDescription taskDescription = event.getTaskTescription();
		switch (eveDescription) {
		case UPTASKTRANSFERSTART:
			taskTransferStart(taskDescription);
			break;
			
		case UPAPPLYCOMPLETE:
			taskApplyComplete(taskDescription);
			break;

		case UPDATASEND:
			taskDataSend(taskDescription);
			break;
		
		case UPTASKTRANSFERCOMPLETE:
			upTaskTransferComplete(taskDescription);
			break;
		
		case UPTRANSFERERROR:
			taskTransferError(taskDescription);
			break;

		default:
			if(log.isDebugEnabled()) {
				log.debug("上行数据传输事件对象传输错误");
			}
			break;
		}
		
	}

	@Override
	public void taskManagerEvent(ETaskManagerEvent event) {

		SyncTaskDescription taskDescription = event.getTaskDescription();
		putInTask(taskDescription);
	}

	@Override
	public void stateDistributeEvent(EStateDistributeEvent event) {

		SyncTaskDescription description = event.getDistributeTaskDescription();
		stateDistribute(description);
		
	}

	@Override
	public void downDataTransferEvent(EDownDataTransferEvent event) {

		EDownDataTransferEvent.DownDataTraEveDescription eveDescription = event.getEveDescription();
		SyncTaskDescription taskDescription = event.getTaskDescription(); 
		switch (eveDescription) {
		case DOWNTASKTRANSFERSTART:
			taskTransferStart(taskDescription);
			break;
			
		case DOWNAPPLYCOMPLETE:
			taskApplyComplete(taskDescription);
			break;
			
		case DOWNDATAINCEPT:
			taskDataIncept(taskDescription);
			break;
			
		case DOWNTASKTRANSFERCOMPLETE:
			downTaskTransferComplete(taskDescription);
			break;
		case DOWNTRANSFERERROR:
			taskTransferError(taskDescription);
			break;

		default:
			if(log.isDebugEnabled()) {
				log.debug("下行数据传输事件对象错误");
			}
			break;
		}
	}
	/**************************************************************************/
	
	/** 
	* 状态异常分发 ，应用注册的状态监控组件执行处理
	* @param stateException 异常代码 
	* @return void    
	* @throws 
	*/ 
	
	public static void stateExceptionDistribte(ISyncStateMonitor.StateExceptionCode stateException) {
		if(stateMonitor==null) {
			System.out.println("状态监控模块没有注册");
		} else {
			stateMonitor.clientStateException(stateException);
		}
	}
	
	/** 
	* 任务提交方法      
	* 1.如果是上行同步任务，则添加到上行同步数据传输器，否则添加到下行同步数据传输器。
	* 2.构造同步状态信息，添加到同步状态分发器中。
	* @param description 任务描述信息对象
	* @return void
	* @throws 
	*/ 
	
	private void putInTask(SyncTaskDescription description) {
		if(log.isDebugEnabled()) {
			log.debug("同步控制器任务提交方法执行。"+Thread.currentThread().getName());
		}

		if(description.getSource().equals("UP")){//上行
			upDataTransfer.addTaskToUpDataTransfer(description);
		} else if (description.getSource().equals("DOWN")) { //下行
			downDataTransfer.addTaskToDownDataTransfer(description);
		} else {
			if (log.isDebugEnabled()) {
				log.debug("任务提交任务类型参数错误");
			}
		}
		//直接更改当前任务状态信息为草稿态
		description.setTaskState(SyncTaskDescription.SynTaskState.DRAFT);
		//状态分发的意义不大，先省略了
//		stateDistribute.addStateDistribute(description);
	}
	

	/** 
	* 状态分发处理，将任务的状态分发给已注册的应用状态监控模块，由应用做相应处理
	* @param description 任务描述文件
	* @return void
	* @throws 
	*/ 
	
	private void stateDistribute(SyncTaskDescription description) {
		if(log.isDebugEnabled()) {
			log.debug("状态分发执行！状态为："+description.getTaskState()+Thread.currentThread().getName());
		}
		if(stateMonitor==null) {
			if(log.isDebugEnabled()) {
				log.debug("应用程序没有注册状态监控组件");
			}
		} else {
			/***************如果需要更多的任务信息，还需修改具体内容****************/
			stateMonitor.clientStateUpdate(description.getTaskState());
		}
	}
	
	/** 
	* 更新执行，应用注册的数据更新器执行更新操作
	* @param taskDescription     
	* @return void
	* @throws 
	*/ 
	
	private void updateExcute (SyncTaskDescription taskDescription) {
		if(dataUpdate==null) {
			System.out.println("应用程序没有注册程序更新组件");
		} else {
			//data字符串 资源文件字符串List
			Map<String, SyncFileDescription> fileInfo = taskDescription.getFileInfo();
			List<String> sourceFilesName = new ArrayList<String>();
			String dataString = "";
			for (Entry<String, SyncFileDescription> item : fileInfo.entrySet()) {
				String key = item.getKey();
				System.out.println("updateExcute：fileName---------------" + key);
				boolean isSourceFile = !key.endsWith(App.DATAFILETAG);
				if(isSourceFile&&fileInfo.get(key).getFileSize()!=0&&!item.getKey().equals("")) {//加入文件长度是否为零判断,文件名是否为 ""判断)
					sourceFilesName.add(item.getKey());
				} else {
					dataString =AppFileUtils.readFile(App.context, key);
				}
			}
			
			dataUpdate.clientDataUpdate(dataString,sourceFilesName);
		}
	}
	

 
	/** 
	* 任务传输启动，修改任务状态，同时将任务加入到状态分发器
	* @param taskDescription  任务描述   
	* @return void
	* @throws 
	*/ 
	
	private void taskTransferStart (SyncTaskDescription taskDescription) {
		if(log.isDebugEnabled()) {
			log.debug("任务传输启动"+Thread.currentThread().getName());
		}
		//直接修改任务状态
		taskDescription.setTaskState(SyncTaskDescription.SynTaskState.DRAFT);
		stateDistribute.addStateDistribute(taskDescription);
	}
	 
	
	/** 
	* 任务申请完成，任务状态，更新任务（将任务列表文件更新，持久化保存任务状态）。 同时将任务加入到状态分发器。
	* @param taskDescription  任务描述对象
	* @return void
	* @throws 
	*/ 
	
	private void taskApplyComplete (SyncTaskDescription taskDescription) {
		if(log.isDebugEnabled()) {
			log.debug("任务申请完成态"+Thread.currentThread().getName());
		}
		taskDescription.setTaskState( SyncTaskDescription.SynTaskState.TOTRANSMIT);
//		taskManager.updateTaskState(taskDescription.getTaskId(), SyncTaskDescription.SynTaskState.TOTRANSMIT);
		taskManager.update(taskDescription);//将修改任务名称，token以及任务状态写入文件
		stateDistribute.addStateDistribute(taskDescription) ;
	}
	
	/** 
	* 下行任务数据接收，通过控制器更新任务状态，更新任务描述列表文件，保存最新状态，同时将任务添加到状态分发器
	* @param taskDescription  任务描述    
	* @return void
	* @throws 
	*/ 
	
	private void taskDataIncept (SyncTaskDescription taskDescription) {
//		taskManager.updateTaskState(taskDescription.getTaskId(), SyncTaskDescription.SynTaskState.TRANSMITTING);
		taskDescription.setTaskState(SyncTaskDescription.SynTaskState.TRANSMITTING);//传输态
		taskManager.update(taskDescription);
		stateDistribute.addStateDistribute(taskDescription);
	}
	
	/** 
	* 下行任务传输完成，修改任务状态 ，状态分发，更新调度器添加任务，任务管理器删除任务.更新任务描述文件。
	* @param taskDescription     
	* @return void
	* @throws 
	*/ 
	
	private void downTaskTransferComplete (SyncTaskDescription taskDescription) {

//		taskManager.updateTaskState(taskDescription.getTaskId(), SyncTaskDescription.SynTaskState.COMPLETION);
		taskDescription.setTaskState(SyncTaskDescription.SynTaskState.COMPLETION);//完成态
		stateDistribute.addStateDistribute(taskDescription);
		updateDataDispatch.addUpdateDataDispatchTask(taskDescription);
		//放在 updateDataDispatch 处理
//		taskManager.delete(taskDescription.getTaskId());
//		taskManager.update(taskDescription);//完成
	} 
	
	/** 
	* 任务传输错误
	* @param description     
	* @return void
	* @throws 
	*/ 
	
	private void taskTransferError (SyncTaskDescription description) {
		if(log.isDebugEnabled()) {
			log.debug("任务传输错误");
		}
	}
	
	
	/** 
	* 上行任务数据发送 
	* @param taskDescription 任务描述对象     
	* @return void
	* @throws 
	*/ 
	
	private void taskDataSend (SyncTaskDescription taskDescription) {
		if(log.isDebugEnabled()) {
			log.debug("上行任务数据传输态！！！"+Thread.currentThread().getName());
		}
		
		taskDescription.setTaskState(SyncTaskDescription.SynTaskState.TRANSMITTING);
//		taskManager.updateTaskState(taskDescription.getTaskId(), SyncTaskDescription.SynTaskState.TRANSMITTING);
		taskManager.update(taskDescription);
		stateDistribute.addStateDistribute(taskDescription);
	}
	
	/** 
	* 上行任务传输完成
	* @param taskDescription 任务描述对象
	* @return void
	* @throws 
	*/ 
	
	private void upTaskTransferComplete (SyncTaskDescription taskDescription) {
		if(log.isDebugEnabled()) {
			log.debug("任务完成态态！！！"+Thread.currentThread().getName());
		}
//		taskManager.updateTaskState(taskDescription.getTaskId(), SyncTaskDescription.SynTaskState.COMPLETION);
		taskDescription.setTaskState(SyncTaskDescription.SynTaskState.COMPLETION);//完成态
		stateDistribute.addStateDistribute(taskDescription);
		taskManager.delete(taskDescription);
		taskManager.update(taskDescription);///完成
	}
	

	/** 
	* 更新完成
	* @param taskDescription   任务描述对象  
	* @return void
	* @throws 
	*/ 
	
	private void updateComplete (SyncTaskDescription taskDescription) {
		if(log.isDebugEnabled()) {
			log.debug("下行任务最后被处理，删除任务信息，更新文件");
		}
		taskManager.delete(taskDescription);
		taskManager.update(taskDescription);//完成
	}
	
	/** 
	* 数据更新错误
	* @param taskDescription
	* @return void
	* @throws 
	*/ 
	
	private void updateError (SyncTaskDescription taskDescription) {
		if(log.isDebugEnabled()) {
			log.debug("数据已保存完成，当前客户应用没有更新");
		}
	}
	
}
