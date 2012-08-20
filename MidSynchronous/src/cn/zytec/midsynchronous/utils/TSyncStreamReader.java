package cn.zytec.midsynchronous.utils;


/**
   * @ClassName: TSyncStreamReader
   * @Description: 文件流读取器
   * @author: lee
   * @modify date: 2012-6-27 下午02:56:45
   */
public class TSyncStreamReader implements ISyncStream {

	private long length;
	private long position;

	@Override
	public long seek(long lOffset, SyncSeekOrigin origin) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readByte() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int Read(byte[] buffer, int nOffset, int nCount) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int readInt16() {
		return 0;
	}
	
	public int readInt32() {
		return 0;
	}
	
	public TSyncInt64 readInt64() {
		return new TSyncInt64(0);
	}
	
	public String readVString () {
		return "";
	}

	@Override
	public void writeByte(byte btValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(byte[] buffer, int nOffset, int nCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
