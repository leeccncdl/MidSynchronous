package cn.zytec.midsynchronous;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import android.content.Context;
import cn.zytec.lee.App;
import cn.zytec.midsynchronous.utils.AppFileUtils;

import com.google.gson.Gson;

/**
   * @ClassName: TSyncTaskManager
   * @Description: 同步任务管理器
   * @author: lee
   * @modify date: 2012-6-27 下午02:38:17
   */
public class TSyncTaskManager implements ISyncTaskStorager {
	
	private static final String TAG = "TAG:TSyncTaskManagerMMMMMMMMMM";
	private static final String DESCRIPTIONFILENAME = "Description.des";
	
	private ITaskManagerEventListener listener;
	private TSyncTaskDescriptions taskDescriptions;
	
	public TSyncTaskManager (ClientSyncController c) {
		listener = c;
		taskDescriptions = new TSyncTaskDescriptions();
		isDescriptionFileExist();
		LoadTask();//加载未完成任务，未完成任务应该保留在任务持久化文件中
		
	}
	
	private void isDescriptionFileExist() {
		String[] files = App.getInstance().fileList();
		for(int i=0,len=files.length;i<len;i++) {
			if(files[i].equals(DESCRIPTIONFILENAME)) {
				return ;
			}
		}
		
		TSyncTaskDescriptions taskDescriptions = new TSyncTaskDescriptions();
		Gson gson = new Gson();
		AppFileUtils.writeFile(App.getInstance(), DESCRIPTIONFILENAME, gson.toJson(taskDescriptions), Context.MODE_PRIVATE);	
	}
  
	/** 
	* 从任务描述文件中加载没有执行完成的任务，并且将任务依次提交处理   
	* @return boolean    
	* @throws 
	*/ 
	
	private boolean LoadTask () {
		TSyncTaskDescriptions loadTasks = readDescriptionFile(App.getInstance());
		List<SyncTaskDescription> arrTaskDescriptions = loadTasks.getArrTaskDescriptions();
		System.out.println(TAG+"加载任务列表文件 任务数目SIZE="+arrTaskDescriptions.size());
		if(arrTaskDescriptions.size()!=0) {
			for (SyncTaskDescription syncTaskDescription : arrTaskDescriptions) {
				putinTask(syncTaskDescription);
			}
		}
		return true;
	}
	

	/**
	* 创建任务，将任务添加任务描述列表，写入任务描述列表文件，最后提交给任务控制器继续处理任务
	* @param description 任务描述对象
	* @see cn.zytec.midsynchronous.ISyncTaskStorager#create(cn.zytec.midsynchronous.SyncTaskDescription)
	*/
	
	@Override
	public void create(SyncTaskDescription description){
		
		taskDescriptions.add(description);
		//创建任务唯一的入口，将当前任务列表内容写入任务描述文件，每次重新写入
		AppFileUtils.writeFile(App.getInstance(),DESCRIPTIONFILENAME, new Gson().toJson(taskDescriptions), Context.MODE_PRIVATE);	
		//提交任务事件，在主控制器中具体处理。添加一个任务，就应该提交一个任务
		putinTask(description);
	}

	@Override
	public SyncTaskDescription read(String strTaskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SyncTaskDescription readUnover() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* 任务描述列表文件更新，将当前任务列表任务重写如文件,传入参数并没有使用
	* @param description
	* @return
	* @see cn.zytec.midsynchronous.ISyncTaskStorager#update(cn.zytec.midsynchronous.SyncTaskDescription)
	*/
	
	@Override
	public boolean update(SyncTaskDescription description) {
		// TODO Auto-generated method stub
		System.out.println(TAG+"UPDATE********************************");
		AppFileUtils.writeFile(App.getInstance(),DESCRIPTIONFILENAME, new Gson().toJson(taskDescriptions), Context.MODE_PRIVATE);
		
		return true;
	}

	@Override
	public boolean updateTransferSize(String strTaskId, long lTransferSize) {
		// TODO Auto-generated method stub
//		List<TSyncTaskDescription> list = taskDescriptions.getArrTaskDescriptions(); 
//		TSyncTaskDescription d = null;
//		for (int i = 0, size = list.size(); i < size; i++) {
//			d = list.get(i);
//			if(d.getTaskId().equals(strTaskId)) {
//				d.setTrandferSize(lTransferSize);
//				//任务状态改变，写入任务描述文件
//				writeDescriptionFile(App.getInstance());
//				return true;
//			}
//		}
		return false;
	}

	/**
	* 更新任务状态，这里做的方式有点多余，其实可以直接修改任务描述对象的状态即可。这样做牺牲了效率，提高了可读性和可理解性。
	* @param strTaskId
	* @param state
	* @return
	* @see cn.zytec.midsynchronous.ISyncTaskStorager#updateTaskState(java.lang.String, cn.zytec.midsynchronous.SyncTaskDescription.SynTaskState)
	*/
	
	@Override
	public boolean updateTaskState(String strTaskId, SyncTaskDescription.SynTaskState state) {
		// TODO Auto-generated method stub
		List<SyncTaskDescription> list = taskDescriptions.getArrTaskDescriptions(); 
		SyncTaskDescription description = null;
		for (int i = 0, size = list.size(); i < size; i++) {
			description = list.get(i);
			if(description.getTaskId().equals(strTaskId)) {
				description.setTaskState(state);
				//任务状态改变，写入任务描述文件
//				writeDescriptionFile(App.getInstance());
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean delete(SyncTaskDescription taskDescription) {
		// TODO Auto-generated method stub
		if(taskDescriptions.exists(taskDescription.getTaskId())) {
			//删除数据传输完成的文件
			taskDescriptions.remove(taskDescription.getTaskId());
		}

		System.out.println("任务从任务管理器移除");
		return false;
	}
	

	/** 
	* 触发提交任务事件 
	* @param description  
	* @return void
	* @throws 
	*/ 
	
	private void putinTask (SyncTaskDescription description) {

		ETaskManagerEvent event = new ETaskManagerEvent(this,description);
		listener.taskManagerEvent(event);

	} 
	
	/** 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param context
	* @return TSyncTaskDescriptions    
	* @throws 
	*/ 
	
	private TSyncTaskDescriptions readDescriptionFile(Context context) {
		
		Gson gson = new Gson();
		FileInputStream ins = null;
		try {
			ins = context.openFileInput(DESCRIPTIONFILENAME);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		InputStreamReader inReader = new InputStreamReader(ins); 
		

		StringBuffer sb = new StringBuffer();
		int c;
		try {
			while((c = inReader.read())!= -1){
			       sb.append((char)c);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TSyncTaskDescriptions taskDescriptions = gson.fromJson(sb.toString(), TSyncTaskDescriptions.class);

		return taskDescriptions;
		
	}

}
