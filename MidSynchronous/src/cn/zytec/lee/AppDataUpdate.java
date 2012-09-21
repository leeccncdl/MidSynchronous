package cn.zytec.lee;

import java.util.List;

import cn.zytec.midsynchronous.client.ISyncDataUpdate;

/**
   * 应用程序数据更新类，实现同步组件数据更新接口，需要通过TaskControl注册到同步组件控制器
   * @author: lee
   * @modify date: 2012-8-30 下午12:58:48
   */
public class AppDataUpdate implements ISyncDataUpdate{

	private AppLogger log = AppLogger.getLogger("AppDataUpdate");
	
	/**
	* 数据更新，该方法由同步中间件控制器在下行数据传输完毕时调用，由应用程序解释数据并更新
	* @param taskDataString 数据内容
	* @param sourceFiles 资源文件列表
	* @see cn.zytec.midsynchronous.client.ISyncDataUpdate#clientDataUpdate(java.lang.String, java.util.List)
	*/
	
	@Override
	public void clientDataUpdate(String taskDataString, List<String> sourceFiles) {
		if(log.isDebugEnabled()) {
			log.debug("应用程序数据更新执行 "+"数据字符串："+taskDataString+"资源文件列表：");
		}
	}

}
