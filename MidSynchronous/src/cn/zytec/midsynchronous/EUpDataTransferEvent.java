package cn.zytec.midsynchronous;

import java.util.EventObject;

/**
   * @ClassName: EUpDataTransferEvent
   * @Description: 上行同步数据传输事件
   * @author: lee
   * @modify date: 2012-6-27 下午02:46:30
   */
public class EUpDataTransferEvent extends EventObject {
	
	/**
	 * @Fields: serialVersionUID  TODO(用一句话描述这个变量表示什么)
	
	 */
	private static final long serialVersionUID = 1L;
	
	private UpDataTraEveDescription upDatTraEveDescription;
	private SyncTaskDescription taskDescription;
	
	public enum UpDataTraEveDescription {
		UPTASKTRANSFERSTART,//任务传输启动事件
		UPAPPLYCOMPLETE,//任务申请完成事件
		UPDATASEND,//任务数据发送事件 
		UPTASKTRANSFERCOMPLETE,//任务传输结束事件
		UPTRANSFERERROR//任务传输错误事件
	}
	
	public SyncTaskDescription getTaskTescription() {
		return taskDescription;
	}

	public EUpDataTransferEvent(Object source,UpDataTraEveDescription description,SyncTaskDescription taskDescription) {
		super(source);
		this.upDatTraEveDescription = description;
		this.taskDescription = taskDescription;
	}

	public UpDataTraEveDescription getUpDatTraEveDescription() {
		return upDatTraEveDescription;
	}

}
