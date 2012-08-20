package cn.zytec.midsynchronous.utils;

public class PackUtil {
	/**
	 * @Title: makeDatatoFile
	 * @Description: TODO 封包
	 * @return
	 * @return String 文件路径
	 * @throws
	 */

	public String packing() {
		return null;
	}

	/**
	 * @Title: unPacking
	 * @Description: 解包
	 * @return void
	 * @throws
	 */

	public void unPacking() {

	}

	public static byte[] intToByte(int i, int length) {
		byte[] abyte = null;
		if (length == 1) {
			abyte = new byte[length];
			abyte[0] = (byte) (0xff & i);
		} else if (length == 2) {
			abyte = new byte[2];
			abyte[0] = (byte) (0xff & i);
			abyte[1] = (byte) ((0xff00 & i) >> 8);
		} else {
			abyte = new byte[length];
			abyte[0] = (byte) (0xff & i);
			abyte[1] = (byte) ((0xff00 & i) >> 8);
			abyte[2] = (byte) ((0xff0000 & i) >> 16);
			abyte[3] = (byte) ((0xff000000 & i) >> 24);
		}

		return abyte;
	}

	public static int bytesToInt(byte[] bytes) {
		int addr = 0;
		if (bytes.length == 1) {
			addr = bytes[0] & 0xFF;
		} else if (bytes.length == 2) {
			addr = bytes[0] & 0xFF;
			addr |= ((bytes[1] << 8) & 0xFF00);
		} else {
			addr = bytes[0] & 0xFF;
			addr |= ((bytes[1] << 8) & 0xFF00);
			addr |= ((bytes[2] << 16) & 0xFF0000);
			addr |= ((bytes[3] << 24) & 0xFF000000);
		}
		return addr;
	}
}
