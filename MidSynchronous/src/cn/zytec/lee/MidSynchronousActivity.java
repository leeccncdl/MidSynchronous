package cn.zytec.lee;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MidSynchronousActivity extends Activity {
    /** Called when the activity is first created. */
	Thread th;
    
    Button b1;;
    Button b2;;
    Button b3;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        /******************模拟上行同步数据************************/
        TestDao testObj = new TestDao(1, 24, "张三", "85");
        TestDao testObj2 = new TestDao(1, 24, "李四", "88");
        ArrayList<TestDao> testArr = new ArrayList<TestDao>();
        testArr.add(testObj);
        testArr.add(testObj2);
		Gson gson = new Gson(); 
		final String gsonString = gson.toJson(testArr);
        final List<String> sourceFiles = new ArrayList<String>();
        sourceFiles.add("sourcefile1.png");
        sourceFiles.add("sourcefile2.JPG");
        /********************模拟上行同步数据结束*******************/
        

        b1 = (Button)findViewById(R.id.add_upTask);
        b2 = (Button)findViewById(R.id.add_downTask);
        b3 = (Button)findViewById(R.id.excute_Task);
        
        final Test t = new Test();
        
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		        th = new Thread() {
					public void run() {
		                t.addSyncDataTransferTask(gsonString,sourceFiles);
		                System.out.println("$$$$$$$$$$上行任务启动线程执行完毕$$$$$$$$$$");
					}
		        };
		        th.start();

			}
		});
        
        b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			        th = new Thread() {
						public void run() {
//			                Test t = new Test();
			                t.addDownSyncDataTransferTask("TestCondition");	
			                System.out.println("$$$$$$$$$$下行任务启动线程执行完毕$$$$$$$$$$");
						}
			        };
			        th.start();
				}
		});
        
        b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		        th = new Thread() {
					public void run() {
//		                Test t = new Test();
		                t.runTask();
		                System.out.println("$$$$$$$$$$执行未完成任务启动线程执行完毕$$$$$$$$$$");
		                }
		        };
		        th.start();
			}
		});
        //创建上行，下行资源文件夹
//        SDFileUtils sd = new SDFileUtils();
//        sd.creatSDDir("MidSync"+File.separator+"downLoadsourceFile");
//        sd.creatSDDir("MidSync"+File.separator+"upwardSourceFile");
    }
}