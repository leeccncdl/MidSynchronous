package cn.zytec.lee;

import java.util.ArrayList;
import java.util.List;

import cn.zytec.midsynchronous.client.CreateTaskDescription;
import cn.zytec.midsynchronous.client.TaskControl;
import cn.zytec.midsynchronous.utils.CheckNetworkUtils;

import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
   * 中间件Activity测试类
   * @author: lee
   * @modify date: 2012-8-30 下午01:31:48
   */
public class MidSynchronousActivity extends Activity {

	private AppLogger log = AppLogger.getLogger(MidSynchronousActivity.class);

	private String gsonString;//json格式的数据字符串
	private List<String> sourceFiles;//资源文件列表
	private TaskControl taskControl;
	
	Thread th;
	Button b1;
	Button b2;
	Button b3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/****************** 模拟上行同步数据 ************************/
		TestDao testObj = new TestDao(1, 24, "张三", "85");
		TestDao testObj2 = new TestDao(1, 24, "李四", "88");
		ArrayList<TestDao> testArr = new ArrayList<TestDao>();
		testArr.add(testObj);
		testArr.add(testObj2);
		Gson gson = new Gson();
		gsonString = gson.toJson(testArr);
		sourceFiles = new ArrayList<String>();
		sourceFiles.add("sourcefile1.png");
		sourceFiles.add("sourceFile4.jpg");
//		sourceFiles.add("sourcefile2.JPG");
		/******************** 模拟上行同步数据结束 *******************/

		b1 = (Button) findViewById(R.id.add_upTask);
		b2 = (Button) findViewById(R.id.add_downTask);
		b3 = (Button) findViewById(R.id.excute_Task);
		
		taskControl = new TaskControl();
		// 注册数据更新和状态监控组件
		taskControl.registSyncDataUpdate(new AppDataUpdate());
		taskControl.registSyncStateMonitor(new AppStateMonitor());
		
		//网络状态检查
		if(!CheckNetworkUtils.checkWifi()) {
			if(!CheckNetworkUtils.check3G()) {
				CheckNetworkUtils.wirelessSettingAct();
			} else {
				Toast.makeText(this, "提示：您当前使用的是数据网络", Toast.LENGTH_SHORT).show();
			}
		}
		
		//添加一个上行传输任务，加载并添加到之前的任务列表，任务按顺序执行
		b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				th = new Thread() {
					public void run() {
						taskControl.startUpTransTask(CreateTaskDescription
									.createUpDescription(App.context,
											sourceFiles, gsonString));
						if (log.isDebugEnabled()) {
							log.debug("上行任务启动线程执行完毕");
						}
					}
				};
				th.start();
			}
		});

		//添加一个下行传输任务，加载并添加到之前的任务列表，任务按顺序执行
		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				th = new Thread() {
					public void run() {
						taskControl.startDownTransTask(CreateTaskDescription
									.createDownDescription("TestCondition"));
						if (log.isDebugEnabled()) {
							log.debug("下行任务启动线程执行完毕");
						}
					}
				};
				th.start();
			}
		});
		//启动传输任务，加载之前未执行完成的上行下行任务，任务按顺序执行
		b3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				th = new Thread() {
					public void run() {
						// Test t = new Test();
						taskControl.startUnfinishedTask();
						if (log.isDebugEnabled()) {
							log.debug("执行未完成任务启动线程执行完毕");
						}
					}
				};
				th.start();
			}
		});
		
		// 创建上行，下行资源文件夹
		// SDFileUtils sd = new SDFileUtils();
		// sd.creatSDDir("MidSync"+File.separator+"downLoadsourceFile");
		// sd.creatSDDir("MidSync"+File.separator+"upwardSourceFile");
	}
}