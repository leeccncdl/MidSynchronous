package cn.zytec.midsynchronous.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;



import android.content.Context;

public class AppFileUtils {
//	private AppLogger log = AppLogger.getLogger("AppFileUtils");
	public static final String FILETAG = ".datafile";
	private static final String DOWNSOURCEFILEPATH = "MidSync"+File.separator+"downLoadsourceFile"+File.separator;
	private static final String UPSOURCEFILEPATH = "MidSync"+File.separator+"upwardSourceFile";
	
	public static String readFile(Context context,String fileName) {
		StringBuffer sb = new StringBuffer();
		FileInputStream ins = null;
		try {

			ins = context.openFileInput(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		InputStreamReader inReader = new InputStreamReader(ins); 
		
		int c;
		try {
			while((c = inReader.read())!= -1){
			       sb.append((char)c);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	public static byte[] readFile(Context context,String fileName,long offset,int length,String mode,boolean isSourceFile) {
		byte[] buffer = new byte[length];
		SDFileUtils sd = new SDFileUtils();
		
		if(!isSourceFile) {
			RandomAccessFile ra = null;
			try {
				ra = new RandomAccessFile(context.getFileStreamPath(fileName),mode);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ra.seek(offset);
				ra.read(buffer, 0, length);	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				ra.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			//资源文件在SD卡上
			buffer = sd.readFromSD(UPSOURCEFILEPATH, fileName, offset, length);
		}

		return buffer;
	}

	public static void writeFile(Context context,String fileName,
			String writeString, int mode) {

		FileOutputStream os = null;
		try {
			os = context.openFileOutput(fileName, mode);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("打开文件异常！");
			e.printStackTrace();
		}
		OutputStreamWriter outWriter = new OutputStreamWriter(os);
		try {
			outWriter.write(writeString);
			System.out.println("写入任务列表文件**************");
//			System.out.println(writeString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("写入文件异常");
			e.printStackTrace();
		}

		try {
			outWriter.close();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("关闭流异常");
			e.printStackTrace();

		}
	}

	/** 
	* 写入文件(追加方式)。随机写入?
	* @param context
	* @param fileName
	* @param writeByteArr
	* @param mode
	* @param isSourceFile  是否是资源文件，资源文件操作在SD卡目录上   
	* @return void
	* @throws 
	*/ 
	
	public static void writeFile(Context context, String fileName,
			byte[] writeByteArr, int mode,boolean isSourceFile,String sourceFileDir) {
		
		
		SDFileUtils sd = new SDFileUtils();
		if(isSourceFile) {//是资源文件，SD卡文件操作
//			sd.write2SD(DOWNSOURCEFILEPATH, fileName, writeByteArr, Context.MODE_APPEND);
		sd.write2SD(DOWNSOURCEFILEPATH+sourceFileDir, fileName, writeByteArr, true);
		} else {//不是资源文件
			FileOutputStream os = null;
			try {
				os = context.openFileOutput(fileName, mode);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("打开文件异常！");
			}
			try {
				os.write(writeByteArr);
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void deleteFile(Context context, String fileName) {
		File deleteFile = null;
		deleteFile = context.getFileStreamPath(fileName);
		deleteFile.delete();
	}
	
	public static long getFileSize(Context context,String fileName,String mode) {
		long length = 0;
		
		RandomAccessFile ra = null;
		if(fileName.endsWith(FILETAG)) {
			try {
				ra = new RandomAccessFile(context.getFileStreamPath(fileName),mode);
				length = ra.length();
				ra.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			SDFileUtils sd = new SDFileUtils();
			length = sd.getFileSize(UPSOURCEFILEPATH, fileName, mode);
		}


		return length;
		
	}

}
