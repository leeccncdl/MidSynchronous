package cn.zytec.midsynchronous.utils;


/**
   * @ClassName: ISyncStream
   * @Description: 文件读写流接口
   * @author: lee
   * @modify date: 2012-6-27 下午02:51:29
   */
public interface ISyncStream {
	
//	public long length = 0;
//	public long position = 0;
	
	public long seek (long lOffset,SyncSeekOrigin origin);
	
	public int readByte ();
	
	public int Read (byte[] buffer,int nOffset,int nCount);
	
	public void writeByte (byte btValue);
	
	public void write (byte[] buffer, int nOffset, int nCount);
	
	public void flush();
	
	public void close();
	
}
