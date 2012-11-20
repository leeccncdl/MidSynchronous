package cn.zytec.midsynchronous.client;

import java.util.List;

/**
   * @ClassName: ISyncDataUpdate
   * @Description: 同步数据更新接口，应用系统同步数据更新器需要继承该接口
   * 			        接收来自同步控制器的同步数据文件，做应用端数据的解析和更新
   * 			        继承该接口的类注册到同步控制器，由同步控制器调用触发
   * @author: lee
   * @modify date: 2012-7-13 上午11:13:52
   */
public interface ISyncDataUpdate {
	
	/** 
	* 客户应用程序的数据更新方法，通过注册数据更新组件后，有中间件调用实现该接口的方法体 
	* @param taskDataString 数据字符串
	* @param sourceFiles 资源文件列表
	* @return void    
	* @throws 
	*/ 
	
	public void clientDataUpdate(String taskDataString,List<String> sourceFiles);
	
}
