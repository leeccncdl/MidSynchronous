package cn.zytec.midsynchronous;

import java.util.EventObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
   * @ClassName: EDownDataTransferEvent
   * @Description: 下行同步数据传输事件
   * @author: lee
   * @modify date: 2012-6-27 下午02:45:02
   */
public class EDownDataTransferEvent extends EventObject {
	
	/**
	 * @Fields: serialVersionUID  TODO(用一句话描述这个变量表示什么)
	
	 */
	private static final long serialVersionUID = 1L;
	private SyncTaskDescription taskDescription;
	private DownDataTraEveDescription eveDescription;
	
	public enum DownDataTraEveDescription {
		DOWNTASKTRANSFERSTART,//任务传输启动事件
		DOWNAPPLYCOMPLETE,//任务申请完成事件
		DOWNDATAINCEPT,//任务数据接收事件 
		DOWNTASKTRANSFERCOMPLETE,//任务传输结束事件
		DOWNTRANSFERERROR//任务传输错误事件
	}
	
	public EDownDataTransferEvent(Object source,DownDataTraEveDescription eveDescription,SyncTaskDescription Description) {
		super(source);
		taskDescription = Description;
		this.eveDescription = eveDescription;
	}
	
	public SyncTaskDescription getTaskDescription() {
		return taskDescription;
	}

	public DownDataTraEveDescription getEveDescription() {
		return eveDescription;
	}

}
