package cn.zytec.midsynchronous.client;



/**
   * @ClassName: ISyncDataUpdate
   * @Description: 同步数据更新接口，应用系统同步数据更新器需要继承该接口
   * 			        接收来自同步控制器的同步数据文件，做应用端数据的解析和更新
   * 			        继承该接口的类注册到同步控制器，由同步控制器调用触发
   * @author: lee
   * @modify date: 2012-7-13 上午11:13:52
   */
public interface ISyncDataUpdate {
	
	public void clientDataUpdate(String s);
	
}
