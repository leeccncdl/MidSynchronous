package cn.zytec.lee;

import java.util.List;

import cn.zytec.midsynchronous.client.ISyncDataUpdate;

/**
   * @class：AppDataUpdate.java 
   * @Description: 该类为中间件使用方法的演示类
   * 			        客户端程序的数据更新类，实现同步组件数据更新接口。
   * 			        当下行任务执行完成后，中间件自动调用的接口类，使用之前需要通过TaskControl注册到同步组件控制器
   * @author: lee
   *
   * @history:
   *   日期             版本          担当者         修改内容
   *   2012-11-19  1.0     lee      初版
   */
public class AppDataUpdate implements ISyncDataUpdate{

	private AppLogger log = AppLogger.getLogger("AppDataUpdate");
	
	/**
	* 数据更新，该方法由同步中间件控制器在下行数据传输完毕时调用，由客户端应用程序根据需要对数据进行解释使用
	* @param taskDataString 数据文件内容
	* @param sourceFiles 资源文件名列表
	* @see cn.zytec.midsynchronous.client.ISyncDataUpdate#clientDataUpdate(java.lang.String, java.util.List)
	*/
	@Override
	public void clientDataUpdate(String taskDataString, List<String> sourceFiles) {
		if(log.isDebugEnabled()) {
			log.debug("应用程序数据更新执行 "+"数据字符串："+taskDataString+"资源文件列表...");
		}
	}

}
