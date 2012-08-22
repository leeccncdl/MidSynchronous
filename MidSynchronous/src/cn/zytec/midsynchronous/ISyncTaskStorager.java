package cn.zytec.midsynchronous;

/**
   * @ClassName: ISyncTaskStorager
   * @Description: 任务描述信息存储接口
   * @author: lee
   * @modify date: 2012-6-27 下午02:52:32
   */
public interface ISyncTaskStorager {

	/** 
	* 创建任务 
	* @param description 任务描述对象     
	* @return void
	* @throws 
	*/ 
	
	public void create (SyncTaskDescription description);
	
	public SyncTaskDescription read (String strTaskId);
	
	public SyncTaskDescription readUnover () ;
	
	public boolean update (SyncTaskDescription description);
	
	public boolean updateTransferSize (String strTaskId,long lTransferSize);
	
	public boolean updateTaskState (String strTaskId, SyncTaskDescription.SynTaskState state);
	
	public boolean delete (SyncTaskDescription taskDescription);
}
