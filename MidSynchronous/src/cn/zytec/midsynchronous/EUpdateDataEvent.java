package cn.zytec.midsynchronous;

import java.util.EventObject;

/**
   * @ClassName: EUpdateDataEvent
   * @Description: 同步数据更新事件
   * @author: lee
   * @modify date: 2012-6-27 下午02:46:50
   */
public class EUpdateDataEvent extends EventObject {

	/**
	 * @Fields: serialVersionUID  TODO(用一句话描述这个变量表示什么)
	
	 */
	private static final long serialVersionUID = 1L;
	
	private UpdateDataEveDescription upDateDataEventDescription;
	private SyncTaskDescription taskDescription;
	
	public enum UpdateDataEveDescription {
		UPDATEEXCUTE,//更新数据执行
		UPDATECOMPLETE,//更新数据完成
		UPDATEERROR//更新数据错误
	}
	
	public UpdateDataEveDescription getUpDateDataEventDescription() {
		return upDateDataEventDescription;
	}

	public EUpdateDataEvent(Object source, UpdateDataEveDescription description,SyncTaskDescription taskDescription) {
		super(source);
		this.upDateDataEventDescription = description;
		this.taskDescription = taskDescription;
	}

	public SyncTaskDescription getTaskDescription() {
		return taskDescription;
	}

}
