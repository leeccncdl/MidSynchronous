package cn.zytec.midsynchronous.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import android.os.Environment;

public class SDFileUtils {
	private String SDCardRoot;
	private String SDStateString;

	public SDFileUtils() {
		// 得到当前外部存储设备的目录
		SDCardRoot = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator;
		// 获取扩展SD卡设备状态
		SDStateString = Environment.getExternalStorageState();
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @param dir
	 *            目录路径
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public File createFileInSDCard(String dir, String fileName)
			throws IOException {
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dir
	 *            目录路径
	 * @return
	 */
	public File creatSDDir(String dir) {
		File dirFile = new File(SDCardRoot + dir + File.separator);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		return dirFile;
	}

	/**
	 * 判断SD卡上的文件是否存在
	 * 
	 * @param dir
	 *            目录路径
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public boolean isFileExist(String dir, String fileName) {
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		return file.exists();
	}

	/***
	 * 获取文件的路径
	 * 
	 * @param dir
	 * @param fileName
	 * @return
	 */
	public String getFilePath(String dir, String fileName) {
		return SDCardRoot + dir + File.separator + fileName;
	}

	/***
	 * 获取SD卡的剩余容量,单位是Byte
	 * 
	 * @return
	 */
	public long getSDAvailableSize() {
		if (SDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {
			// 取得sdcard文件路径
			File pathFile = android.os.Environment
					.getExternalStorageDirectory();
			android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());
			// 获取SDCard上每个block的SIZE
			long nBlocSize = statfs.getBlockSize();
			// 获取可供程序使用的Block的数量
			long nAvailaBlock = statfs.getAvailableBlocks();
			// 计算 SDCard 剩余大小Byte
			long nSDFreeSize = nAvailaBlock * nBlocSize;
			return nSDFreeSize;
		}
		return 0;
	}


	public boolean write2SD(String dir, String fileName, byte[] bytes,
			long offset) {
		System.out.println("FFFFFFFFFFFfileName: "+fileName);
		if (bytes == null) {
			return false;
		}
		RandomAccessFile raFile = null;

		try {
			if (SDStateString.equals(android.os.Environment.MEDIA_MOUNTED)
					&& bytes.length < getSDAvailableSize()) {
				File file = null;
				creatSDDir(dir);
				file = createFileInSDCard(dir, fileName);
				raFile = new RandomAccessFile(file, "rwd");
				raFile.seek(offset);
				raFile.write(bytes);
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				raFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 将一个字节数组数据写入到SD卡中
	 */
	public boolean write2SD(String dir, String fileName, byte[] bytes,
			boolean append) {
		if (bytes == null) {
			return false;
		}
		OutputStream output = null;
		try {
			// 拥有可读可写权限，并且有足够的容量
			if (SDStateString.equals(android.os.Environment.MEDIA_MOUNTED)
					&& bytes.length < getSDAvailableSize()) {
				File file = null;
				creatSDDir(dir);
				file = createFileInSDCard(dir, fileName);
				output = new BufferedOutputStream(new FileOutputStream(file,
						append));
				output.write(bytes);
				output.flush();
				return true;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 从SD卡随即读取文件，返回byte数组
	 * 
	 * @param dir
	 * @param fileName
	 * @param offset
	 * @param length
	 * @return byte[]
	 * @throws
	 */

	public byte[] readFromSD(String dir, String fileName, long offset,
			int length) {
		byte[] buffer = new byte[length];
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		RandomAccessFile raFile = null;
		try {
			raFile = new RandomAccessFile(file, "r");
			raFile.seek(offset);
			raFile.read(buffer, 0, length);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			raFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/***
	 * 从sd卡中读取文件，并且以字节流返回
	 * 
	 * @param dir
	 * @param fileName
	 * @return
	 */
	public byte[] readFromSD(String dir, String fileName) {
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		if (!file.exists()) {
			return null;
		}
		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(file));
			byte[] data = new byte[inputStream.available()];
			inputStream.read(data);
			return data;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中 ,从网络上读取图片
	 */
	public File write2SDFromInput(String dir, String fileName, InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			int size = input.available();
			// 拥有可读可写权限，并且有足够的容量
			if (SDStateString.equals(android.os.Environment.MEDIA_MOUNTED)
					&& size < getSDAvailableSize()) {
				creatSDDir(dir);
				file = createFileInSDCard(dir, fileName);
				output = new BufferedOutputStream(new FileOutputStream(file));
				byte buffer[] = new byte[4 * 1024];
				int temp;
				while ((temp = input.read(buffer)) != -1) {
					output.write(buffer, 0, temp);
				}
				output.flush();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	public long getFileSize(String dir,String fileName,String mode) {
		long length = 0;
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		System.out.println("EEEEEEEEEEEEE"+file.exists());
		length = file.length();

		return length;
	}
}
