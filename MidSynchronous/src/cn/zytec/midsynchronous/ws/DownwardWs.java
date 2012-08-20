package cn.zytec.midsynchronous.ws;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import cn.zytec.midsynchronous.utils.Base64;

public class DownwardWs {
	
	private static final String HOST = "http://192.168.4.117:8080/MidSynchronous/servlet/ServletEntrance";
	
	private static HttpClient httpclient = new HttpClient();
	private static final String TAG = "TAG:DownwardWs";
	
	/** 
	* 下行同步申请
	* @param strJsonTask  任务描述信息
	* @param strJsonIdentity 用户身份信息   
	* @return String 下行同步令牌以及下行同步数据包文件的尺寸
	* @throws 
	*/ 
	
	public static String DownwardRequest (String strJsonTask, String strJsonIdentity) {
		String jsonTask = "";
		
		PostMethod postMethod = new PostMethod(HOST);
		NameValuePair[] postData = new NameValuePair[3];
		postData[0] = new NameValuePair("strJsonTask", strJsonTask);
		postData[1] = new NameValuePair("strJsonIdentity", strJsonIdentity);
		postData[2] = new NameValuePair("requestType", "DownwardRequest");
		postMethod.addParameters(postData);
		try {
			int statusCode = httpclient.executeMethod(postMethod);
			if(statusCode!=200) {
				System.out.println(TAG+"*********************http 返回错误");
				return "";
			}
			
			
			jsonTask = postMethod.getResponseBodyAsString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		postMethod.releaseConnection();
		System.out.println(TAG+jsonTask);
		return jsonTask;
	}
	
	
	/** 
	* 下行文件传输 
	* @param strToken 下行同步令牌
	* @param lOffset 文件偏移量
	* @param lLength 数据长度（字节长度）
	* @return byte[] 本次接收的文件数据
	* @throws 
	*/ 
	
	public static byte[] DownwardTransmit (String strToken,String fileName, long lOffset, long lLength) {
		
		System.out.println(TAG+" strToken:"+strToken+" Offset:"+lOffset+"~~~~~~");
		
		byte[] byteArrayData = null;
		String response = null;
		
		PostMethod postMethod = new PostMethod(HOST);
		
		NameValuePair[] postData = new NameValuePair[5];
		postData[0] = new NameValuePair("strToken", strToken);
		postData[1] = new NameValuePair("fileName", fileName);
		postData[2] = new NameValuePair("lLength", String.valueOf(lLength));
		postData[3] = new NameValuePair("lOffset", String.valueOf(lOffset));
		postData[4] = new NameValuePair("requestType", "DownwardTransmit");
		postMethod.addParameters(postData);	
		
		try {
			int statusCode = httpclient.executeMethod(postMethod);
			if(statusCode!=200) {
				System.out.println(TAG+"*********************http 返回错误");
				return null;
			}
			response = postMethod.getResponseBodyAsString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		postMethod.releaseConnection();
		
		try {
			byteArrayData = Base64.decode(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return byteArrayData;
	}
	
	/** 
	* 下行同步结束 
	* @param strToken 下行同步令牌   
	* @return boolean true表示成功 false表示失败
	* @throws 
	*/ 
	
	public static boolean DownwardFinish (String strToken) {

		String excuteState = null;
		PostMethod postMethod = new PostMethod(HOST);
		NameValuePair[] postData = new NameValuePair[2];
		postData[0] = new NameValuePair("strToken", strToken);
		postData[1] = new NameValuePair("requestType", "DownwardFinish");
		postMethod.addParameters(postData);
		
		try {
			int statusCode = httpclient.executeMethod(postMethod);
			if(statusCode!=200) {
				System.out.println(TAG+"finish方法*********************http 返回错误");
				return false;
			}
			excuteState = postMethod.getResponseBodyAsString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		postMethod.releaseConnection();
		return (excuteState.equals("true"));
		
	}
	
}

