package cn.zytec.midsynchronous.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import android.content.Context;
import cn.zytec.lee.App;

public class AppFileUtils {
	// private AppLogger log = AppLogger.getLogger("AppFileUtils");

	private static SDFileUtils sd = new SDFileUtils();
	private static Object lock = new Object();

	public static String readFile(Context context, String fileName) {
		StringBuilder sb = new StringBuilder();
		String s = "";
		FileInputStream ins = null;
		BufferedReader bufferedReader = null; 
		InputStreamReader inReader = null;
		try {

			ins = context.openFileInput(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			inReader = new InputStreamReader(ins,"UTF-8");
			bufferedReader = new BufferedReader(inReader);
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        try {
			if ((s = bufferedReader.readLine()) != null) {  
				sb.append(s);  
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
//		int c;
//		try {
//			while ((c = inReader.read()) != -1) {
//				sb.append((char) c);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			inReader.close();
			ins.close();
			bufferedReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	public static boolean readFile(Context context, String fileName,
			long offset, int length, String mode, boolean isSourceFile,
			byte[] buffer) {
		// byte[] buffer = new byte[length];
		// SDFileUtils sd = new SDFileUtils();

		if (!isSourceFile) {
			RandomAccessFile ra = null;
			try {
				ra = new RandomAccessFile(context.getFileStreamPath(fileName),
						mode);

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
			// 资源文件在SD卡上
			sd.readFromSD(App.UPSOURCEFILEPATH, fileName, offset, length,
					buffer);
		}
		return true;
		// return buffer;
	}

	public static void writeFile(Context context, String fileName,
			String writeString, int mode) {
		synchronized (lock) {

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
				// System.out.println(writeString);
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
	}

	/**
	 * 写入文件(追加方式)。随机写入?
	 * 
	 * @param context
	 * @param fileName
	 * @param writeByteArr
	 * @param mode
	 * @param isSourceFile
	 *            是否是资源文件，资源文件操作在SD卡目录上
	 * @return void
	 * @throws
	 */

	public static void writeFile(Context context, String fileName,
			byte[] writeByteArr, int mode, boolean isSourceFile,
			String sourceFileDir) {

		SDFileUtils sd = new SDFileUtils();
		if (isSourceFile) {// 是资源文件，SD卡文件操作
			// sd.write2SD(DOWNSOURCEFILEPATH, fileName, writeByteArr,
			// Context.MODE_APPEND);
			sd.write2SD(App.DOWNSOURCEFILEPATH + sourceFileDir, fileName,
					writeByteArr, true);
		} else {// 不是资源文件
			FileOutputStream os = null;
			try {
				os = context.openFileOutput(fileName, mode);
			} catch (FileNotFoundException e) {
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

	public static long getFileSize(Context context, String fileName, String mode) {
		long length = 0;

		RandomAccessFile ra = null;
		if (fileName.endsWith(App.DATAFILETAG)) {
			try {
				ra = new RandomAccessFile(context.getFileStreamPath(fileName),
						mode);
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
			length = sd.getFileSize(App.UPSOURCEFILEPATH, fileName, mode);
		}

		return length;
	}

	public static String getDateDiff(Date newDate, Date oldDate) {
		long l = newDate.getTime() - oldDate.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long t = (l - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60 - s * 60);
		String strReturn = " " + day + "天" + hour + "小时" + min + "分" + s + "秒"
				+ t + "毫秒";
		return strReturn;
	}

}
