package cn.zytec.midsynchronous.utils;

/**
   * @ClassName: ISyncFile
   * @Description: 文件操作接口
   * @author: lee
   * @modify date: 2012-6-27 下午02:48:06
   */
public interface ISyncFile {
	
	/** 
	* @Title: create 
	* @Description: TODO 创建一个文件 
	* @param strFilePath 文件所在路径
	* @param strFileName 文件名   
	* @return ISyncStream 实现文件读写流接口的对象
	* @throws 
	*/ 
	
	public ISyncStream create (String strFilePath,String strFileName);
	
	public ISyncStream open (String strFilePath, String strFileName);
	
	public void delete (String strFilePath,String strFileName);
	
	public boolean Exists (String strFilePath,String strFileName);
}
