package cn.zytec.lee;

import cn.zytec.midsynchronous.SyncTaskDescription.SynTaskState;
import cn.zytec.midsynchronous.client.ISyncStateMonitor;

/**
   * 应用程序状态监控类，实现同步组件状态监控接口，需要通过TaskControl注册到同步组件控制器
   * @author: lee
   * @modify date: 2012-8-30 下午01:11:57
   */
public class AppStateMonitor implements ISyncStateMonitor {

	private AppLogger log = AppLogger.getLogger("AppStateMonitor");
	
	@Override
	public void clientStateUpdate(SynTaskState taskState) {
		if(log.isDebugEnabled()) {
			log.debug("应用程序状态更新"+taskState);
		}
		switch(taskState) {
		case DRAFT:
			break;
		case TOTRANSMIT:
			break;
		case TRANSMITTING:
			break;
		case PENDING:
			break;
		case TERMINATION:
			break;
		default:
		}
	}

	/**
	* 中间件状态异常监控，该方法由中间件控制器在传输过程发生异常时调用，应用程序可以通过异常代码进行具体处理
	* @param exceptionCode 自定义枚举类型的异常代码
	* @see cn.zytec.midsynchronous.client.ISyncStateMonitor#clientStateException(cn.zytec.midsynchronous.client.ISyncStateMonitor.StateExceptionCode)
	*/
	
	@Override
	public void clientStateException(StateExceptionCode exceptionCode) {

		if(log.isDebugEnabled()) {
			log.debug("应用程序状态错误接收:"+exceptionCode);
		}
		
		switch(exceptionCode) {
		case SER_AUTH_FAIL:
			break;
		case SER_SESSION_INVALID:
			break;
		case HTTP_STATUS_EXCEP:
			break;
		default:

		}
	}

}
