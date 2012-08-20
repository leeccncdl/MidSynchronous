package cn.zytec.midsynchronous.utils;


public class TSyncStreamWriter implements ISyncStream {

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

	@Override
	public void writeByte(byte btValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(byte[] buffer, int nOffset, int nCount) {
		// TODO Auto-generated method stub

	}

	public void writeInt16(short nValue) {
		
	}
	
	public void  writeInt32(int nValue) {
		
	}
	
	public void writeVint32(int nValue) {
		
	}
	
	public void writeVint64(long lValue) {
		
	}
	
	public void writeVSting (String strValue) {
		
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
