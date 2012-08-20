package cn.zytec.midsynchronous;

import java.util.Map;

/**
   * @ClassName: TSyncTaskDescription
   * @Description: 任务描述信息
   * @author: lee
   * @modify date: 2012-6-27 下午02:57:24
   */
public class SyncTaskDescription {
	
	public static final String UPTASK = "UP";
	public static final String DOWNTASK = "DOWN";
	
	
	private String taskId;//任务标识 appname username time
	private String associateId;//
	private int applicationCode;//
	private String taskName;//
	private String condition;
	private String createTime;//创建时间 创建时间
	private String source;//任务来源 上行下行人任务描述
	private SynTaskState taskState;//任务状态 
	
	/**
	 * 数据包列表
	 * */
	private Map<String, SyncFileDescription> fileInfo;
	

	public enum SynTaskState {
		DRAFT,//草稿态
		TOTRANSMIT,//待传输态
		TRANSMITTING,//传输态
		PENDING,//待处理态
		COMPLETION,//完成态
		TERMINATION//终止态
	}
	
	public SyncTaskDescription() {
		 
	}
	
	public SyncTaskDescription(String taskId,String associateId,int applicationCode,
								String taskName,String condition,String createTime,
								String source,SynTaskState taskState,Map<String, SyncFileDescription> fileInfo) {
		
		this.taskId = taskId;
		this.associateId = associateId;
		this.applicationCode = applicationCode;
		this.taskName = taskName;
		this.condition = condition;
		this.createTime = createTime;
		this.source = source;;
		this.taskState = taskState;
		
		this.fileInfo = fileInfo;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getAssociateId() {
		return associateId;
	}

	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}

	public int getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(int applicationCode) {
		this.applicationCode = applicationCode;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public SynTaskState getTaskState() {
		return taskState;
	}

	public void setTaskState(SynTaskState taskState) {
		this.taskState = taskState;
	}
	
	public Map<String, SyncFileDescription> getFileInfo(){
		return this.fileInfo;
	}
	public void setDataInfo(Map<String, SyncFileDescription> _map){
		this.fileInfo = _map;
	}
	
	
}