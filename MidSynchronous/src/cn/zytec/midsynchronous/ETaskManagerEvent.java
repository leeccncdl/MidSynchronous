package cn.zytec.midsynchronous;

import java.util.EventObject;

/**
   * @ClassName: ETaskManagerEvent
   * @Description: 同步任务事件
   * @author: lee
   * @modify date: 2012-6-27 下午02:46:06
   */
public class ETaskManagerEvent extends EventObject {
	
	/**
	 * @Fields: serialVersionUID  TODO(用一句话描述这个变量表示什么)
	
	 */
	private static final long serialVersionUID = 1L;
	
	private SyncTaskDescription taskDescription;

	public SyncTaskDescription getTaskDescription() {
		return taskDescription;
	}

	public ETaskManagerEvent(Object source,SyncTaskDescription taskDescription) {
		
		super(source);
		this.taskDescription = taskDescription;
	}

}
