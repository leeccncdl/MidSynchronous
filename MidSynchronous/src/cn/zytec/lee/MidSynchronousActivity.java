package cn.zytec.lee;

import java.io.File;

import cn.zytec.midsynchronous.utils.AppFileUtils;
import cn.zytec.midsynchronous.utils.SDFileUtils;
import android.app.Activity;
import android.os.Bundle;


public class MidSynchronousActivity extends Activity {
    /** Called when the activity is first created. */
	Thread th;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       

        
        //创建上行，下行资源文件夹
        SDFileUtils sd = new SDFileUtils();
//        sd.creatSDDir("MidSync"+File.separator+"downLoadsourceFile");
//        sd.creatSDDir("MidSync"+File.separator+"upwardSourceFile");
        
        
        Test testFileName = new Test();   
        testFileName.addSyncDataTransferTask(null);

        /*************下行测试代码******************/
//        String response = DownwardWs.DownwardRequest("lee", "lee");
//        System.out.println(response);
//        
//        byte[] buffer = null;
//        int i = 0;
//        while(true) {
//      
//        	buffer = DownwardWs.DownwardTransmit("download-test.JPG", ((102400*i)-1 == -1?0:102400*i-1), 102400);
//
//        	if(buffer.length != 0) {
//        		AppFileUtils.writeFile(App.getInstance(), "download-test.JPG", buffer,MODE_APPEND);
//        	}
//        	if(buffer.length != 102400) {
//        		i = 0;
//        		break;
//        	}
//        	i++;
//        } 
        
        /*************下行测试代码结束******************/
        

//        th = new Thread() {
//
//			public void run() {
//                Test t = new Test();
//                
//                String[] source = {"sourcefile1.png","dataFile1.sync"};
//               
//                
//                t.addSyncDataTransferTask(source);
////                for (int i = 0; i<5; i++) {
////                	t.addSyncDataTransferTask(null,""+i);
////                    try {
////    					Thread.sleep(2000);
////    				} catch (InterruptedException e) {
////    					// TODO Auto-generated catch block
////    					e.printStackTrace();
////    				}
////                }
//
//        	}
//        };
//        th.start();
    }
}