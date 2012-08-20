package cn.zytec.midsynchronous;

import java.util.EventObject;

/**
   * @ClassName: EStateDistributeEvent
   * @Description: 同步状态分发事件
   * @author: lee
   * @modify date: 2012-6-27 下午02:45:36
   */
public class EStateDistributeEvent extends EventObject {

	/**
	 * @Fields: serialVersionUID  TODO(用一句话描述这个变量表示什么)
	
	 */
	private static final long serialVersionUID = 1L;
	
	private SyncTaskDescription distributeTaskDescription;
	
	public EStateDistributeEvent(Object source,SyncTaskDescription description) {
		super(source);
		this.distributeTaskDescription = description;
		// TODO Auto-generated constructor stub
	}

	public SyncTaskDescription getDistributeTaskDescription() {
		return distributeTaskDescription;
	}

}
