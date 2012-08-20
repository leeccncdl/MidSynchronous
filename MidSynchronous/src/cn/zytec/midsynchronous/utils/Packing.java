package cn.zytec.midsynchronous.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Packing {
	
	private File packingFile;
	private DataOutputStream dataOuts;
	private long firstDataRowOffset = 0;
	private int firstDatarowOffsetPosition = 22; //文件头定义格式，一个固定的位置。
	
	List<DataRow> dataRows = new ArrayList<DataRow>();
	List<DataField> dataFields = new ArrayList<DataField>();

	public class DataRow {
		public long nextDataRowOffset = 0;
		public int nextDataRowOffsetPosition;
		public long dataFieldOffset = 0;
		public int dataFieldOffsetPosition;
		
		public DataRow(int nextDataRowOffsetPosition,int dataFieldOffsetPosition) {
			this.nextDataRowOffsetPosition = nextDataRowOffsetPosition;
			this.dataFieldOffsetPosition = dataFieldOffsetPosition;
		}
		
	}
	
	public class DataField {
		public long nextDataFieldOffset = 0;
		public int nextDataFieldOffsetPosition;
		
		public DataField(int nextDataFieldOffsetPosition) {
			this.nextDataFieldOffsetPosition = nextDataFieldOffsetPosition;
		}
	}
	
	

	public Packing(String filePath) {
		packingFile = new File(filePath);
		try {
			dataOuts = new DataOutputStream(new FileOutputStream(packingFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeHeader(int version,int firstCategory,int secondaryCategory,int assistantSign) {
	
		try {
			dataOuts.writeInt(version);
			dataOuts.writeInt(firstCategory);
			dataOuts.writeInt(secondaryCategory);
			dataOuts.write(new byte[6], dataOuts.size(), 6);
			dataOuts.writeLong(firstDataRowOffset);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeDataRow(int firstCategory,int secondCategory,int assistantSign) {
		dataRows.add(new DataRow(dataOuts.size(),dataOuts.size()+22));
		try {
			//先修改上一个数据行的下一数据行偏移量
			if(dataRows.size()==0) {
				firstDataRowOffset = dataOuts.size();//第一个数据行描述信息位置
				
				byte[] buff = new byte[8];
		        buff[0] = (byte) (firstDataRowOffset >> 56);
		        buff[1] = (byte) (firstDataRowOffset >> 48);
		        buff[2] = (byte) (firstDataRowOffset >> 40);
		        buff[3] = (byte) (firstDataRowOffset >> 32);
		        buff[4] = (byte) (firstDataRowOffset >> 24);
		        buff[5] = (byte) (firstDataRowOffset >> 16);
		        buff[6] = (byte) (firstDataRowOffset >> 8);
		        buff[7] = (byte) firstDataRowOffset;
		        
		        dataOuts.write(buff, firstDatarowOffsetPosition, 8);
		        
			} else { //前面已经有数据行，修改前面数据行
				int pos = dataRows.get(dataRows.size()-1).nextDataRowOffsetPosition;
				long offset = dataOuts.size();

				byte[] buff = new byte[8];
		        buff[0] = (byte) (offset >> 56);
		        buff[1] = (byte) (offset >> 48);
		        buff[2] = (byte) (offset >> 40);
		        buff[3] = (byte) (offset >> 32);
		        buff[4] = (byte) (offset >> 24);
		        buff[5] = (byte) (offset >> 16);
		        buff[6] = (byte) (offset >> 8);
		        buff[7] = (byte) offset;
		        
		        dataOuts.write(buff, pos, 8);
			}
			
			
			//开始写数据行描述
			
			dataOuts.writeLong(dataRows.get(dataRows.size()).nextDataRowOffset);
			dataOuts.writeInt(firstCategory);
			dataOuts.writeInt(secondCategory);
			dataOuts.writeInt(assistantSign);
			dataOuts.write(new byte[2], dataOuts.size(), 2);
			dataOuts.writeLong(dataRows.get(dataRows.size()).dataFieldOffset);
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void writeDataField(int fieldId, int fieldType, int fieldLength, byte[] bytes ) {
		//先写入第一个字段的偏移量
//		if(){  是否是数据行的而第一个字段
//			
//		} else {
//			
//		}
		
		dataFields.add(new DataField(dataOuts.size()));
		try {
			dataOuts.writeLong(dataFields.get(dataFields.size()).nextDataFieldOffset);
			dataOuts.writeInt(fieldId);
			dataOuts.writeInt(fieldType);
			dataOuts.writeInt(fieldLength);
			dataOuts.write(bytes, dataOuts.size(), fieldLength);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//......
	}
	
}
