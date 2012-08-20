package cn.zytec.midsynchronous;



public class SyncFileDescription {

//	private String filePath; ////////////////////////////
	/**
	 * 数据包名称
	 * */
	private String fileName;
	
	/**
	 * 数据包大小
	 * */
	private long fileSize;
	
	/**
	 * 已经传输大小
	 * */
	private long transSize;
	
	/**
	 * 辅助标记
	 * */
	private String Auxiliary;
	
	public SyncFileDescription(String fileName,long fileSize,long transSize,String Auxiliary) {
//		this.filePath = filePath;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.transSize = transSize;
		this.Auxiliary = Auxiliary;
		
		
	}
	
	public void setFileName(String value){
		this.fileName = value;
	}
	public String getFileName(){
		return this.fileName;
	}
	
	public void setFileSize(long value){
		this.fileSize = value;
	}
	public long getFileSize(){
		return this.fileSize;
	}
	
	public void setTransSize(long value){
		if(value == getFileSize()) {
			setAuxiliary("Done");
		}
		this.transSize = value;
	}
	public long getTransSize(){
		return this.transSize;
	}
	
	public void setAuxiliary(String value){
		this.Auxiliary = value;
	}
	public String getAuxiliary(){
		return this.Auxiliary;
	}

//	public String getFilePath() {
//		return filePath;
//	}
//
//	public void setFilePath(String filePath) {
//		this.filePath = filePath;
//	}
	
}
