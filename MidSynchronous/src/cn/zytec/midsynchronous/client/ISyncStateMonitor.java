package cn.zytec.midsynchronous.client;

import cn.zytec.midsynchronous.SyncTaskDescription;

public interface ISyncStateMonitor {

	public enum StateExceptionCode {
		SER_AUTH_FAIL,
		SER_SESSION_INVALID,
		HTTP_STATUS_EXCEP;
	}
	
	public void clientStateUpdate(SyncTaskDescription.SynTaskState taskState);
	
	public void clientStateException (ISyncStateMonitor.StateExceptionCode ecxeptionCode);

}
