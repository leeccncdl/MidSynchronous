package cn.zytec.lee;

import cn.zytec.midsynchronous.SyncTaskDescription.SynTaskState;
import cn.zytec.midsynchronous.client.ISyncStateMonitor;

public class AppStateMonitor implements ISyncStateMonitor {

	@Override
	public void clientStateUpdate(SynTaskState taskState) {
		// TODO Auto-generated method stub
		System.out.println("CCCCCCCCC"+"应用程序状态更新执行");
	}

	@Override
	public void clientStateException(StateExceptionCode ecxeptionCode) {
		// TODO Auto-generated method stub
		System.out.println("CCCCCCCCC"+"应用程序状态错误接收"+ecxeptionCode);
	}

}
