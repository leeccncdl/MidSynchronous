package cn.zytec.midsynchronous.client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import cn.zytec.lee.App;
import cn.zytec.midsynchronous.SyncFileDescription;
import cn.zytec.midsynchronous.SyncTaskDescription;
import cn.zytec.midsynchronous.utils.AppFileUtils;

public class CreateTaskDescription {
//	private AppLogger log = AppLogger.getLogger("CreateTaskDescription");
	//上行任务的数据文件存储在一个固定的SD卡文件夹路径。
//	private static final String SDCARDSOURCEDIR = "";
	/** 
	* 将传入的json字符串写成同步传输的数据文件，同资源文件一起创建成任务描述文件。
	* @param context
	* @param sourceFilesPaths 资源文件列表
	* @param jsonString json格式的待传输的数据字符串
	* @return SyncTaskDescription 同步中间件的任务描述对象
	* @throws 
	*/ 
	
	public static SyncTaskDescription createUpDescription(Context context,List<String> sourceFilesPaths,String jsonString) {
//		System.out.println(TAG+"PACKING JSONSTRING:"+jsonString);
		String fileName = createPackingFileName();
		//将json串写入任务data文件
		AppFileUtils.writeFile(context, fileName, jsonString,Context.MODE_PRIVATE);

		//构建任务描述内容
		Map<String, SyncFileDescription> fileInfo = new HashMap<String, SyncFileDescription>();
		
		SyncFileDescription fileDes = null;
		for(int i =0,length = sourceFilesPaths.size();i<length;i++) {
			fileDes = new SyncFileDescription(sourceFilesPaths.get(i), AppFileUtils.getFileSize(App.getInstance(), sourceFilesPaths.get(i), "r"), 0, "false");
			fileInfo.put(sourceFilesPaths.get(i), fileDes);
		}
		fileDes = new SyncFileDescription(fileName, AppFileUtils.getFileSize(App.getInstance(), fileName, "r"), 0, "false");
		fileInfo.put(fileName, fileDes);
		
//		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		SyncTaskDescription taskDescription = new SyncTaskDescription("", "associateId",007, 
				null, "condition",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), SyncTaskDescription.UPTASK, null, fileInfo);
		return taskDescription;
	}
	
	/** 
	* 根据传入的condition 创建下行任务
	* @param condition 应用程序自定义的同步条件，只要服务端能解释即可
	* @return SyncTaskDescription 下行任务的描述对象
	* @throws 
	*/ 
	
	public static SyncTaskDescription createDownDescription(String condition) {
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		SyncTaskDescription taskDescription = new SyncTaskDescription("", "associateId",8, 
				null, condition,time.format(new Date()), SyncTaskDescription.DOWNTASK, null, null);
	
		return taskDescription;
	}
	
	
	/** 
	* 生成打包数据文件的文件名，命名方式为  UP_+ 生成时间的字符串 + .datafile    
	* @return String 返回生成的文件名
	* @throws 
	*/ 
	
	private static String createPackingFileName() {
		
		Calendar c = Calendar.getInstance();
		
		return "UP_"+c.get(Calendar.YEAR)+(c.get(Calendar.MONTH)+1)+c.get(Calendar.DATE)+
		c.get(Calendar.HOUR_OF_DAY)+c.get(Calendar.MINUTE)+c.get(Calendar.SECOND)+c.get(Calendar.MILLISECOND)+".datafile";
	}
}
