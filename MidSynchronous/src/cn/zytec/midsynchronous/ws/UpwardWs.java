package cn.zytec.midsynchronous.ws;

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import cn.zytec.lee.App;
import cn.zytec.midsynchronous.ClientSyncController;
import cn.zytec.midsynchronous.client.ISyncStateMonitor;
import cn.zytec.midsynchronous.utils.Base64;

public class UpwardWs {
	private static final String TAG = "TAG:UpwardWs";
	private static HttpClient httpclient = new HttpClient();
	
	/** 
	* @Description: 上行任务传输请求 
	* @param strJsonTask 任务描述字符串
	* @param strJsonIdentity 身份认证信息
	* @return String 服务器返回token
	* @throws 
	*/ 
	public static String UpwardRequest (String strJsonTask, String strJsonIdentity) {
		String token = null;
		
		httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(App.TIMEOUT);
		httpclient.getHttpConnectionManager().getParams().setSoTimeout(App.TIMEOUT);
		httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");

		PostMethod postMethod = new PostMethod(App.HOST);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		
		NameValuePair[] postData = new NameValuePair[3];
		postData[0] = new NameValuePair("strJsonTask", strJsonTask);
		postData[1] = new NameValuePair("strJsonIdentity", strJsonIdentity);
		postData[2] = new NameValuePair("requestType", "UpwardRequest");
		postMethod.addParameters(postData);

		try {
			int statusCode = httpclient.executeMethod(postMethod);
			if(statusCode != HttpStatus.SC_OK) {
				System.out.println(TAG+"Http错误状态码："+statusCode);
				ClientSyncController.stateExceptionDistribte(ISyncStateMonitor.StateExceptionCode.HTTP_STATUS_EXCEP);
				return "";
			} 

			token = postMethod.getResponseBodyAsString();
		} catch (IOException e) {
			System.out.println(TAG+"Http异常"+e.getClass().getName());
			e.printStackTrace();
		} finally {
			if(postMethod!=null) {
				postMethod.releaseConnection();
			}
		}
		
//		System.out.println(TAG+"UpwardRequest return token: " + token);
		
		return token;
	}
	
	/** 
	* @Description: 上行任务传输 
	* @param strToken 服务器请求返回的的token
	* @param fileName 文件明年
	* @param lOffset 偏移量
	* @param buffer 传送的字节数组
	* @return boolean    
	* @throws 
	*/ 
	public static boolean UpwardTransmit (String strToken, String fileName,long lOffset, byte[] buffer) {
		String returnString = null;
		
		httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(App.TIMEOUT);
		httpclient.getHttpConnectionManager().getParams().setSoTimeout(App.TIMEOUT);
		httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		
		System.out.println(TAG +" UpwardTransmit fileName:"+fileName+" Offset:"+lOffset+"~~~~~~");
		
		PostMethod postMethod = new PostMethod(App.HOST);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		
		NameValuePair[] postData = new NameValuePair[5];
		postData[0] = new NameValuePair("strToken", strToken);
		postData[1] = new NameValuePair("fileName", fileName);
		postData[2] = new NameValuePair("lOffset", String.valueOf(lOffset));
		postData[3] = new NameValuePair("requestType", "UpwardTransmit");
		
		if(buffer!=null) {
			postData[4] = new NameValuePair("buffer", Base64.encodeBytes(buffer));
		} else {
			postData[4] = new NameValuePair("buffer", "");
		}
	
		postMethod.addParameters(postData);

		try {
//			Date d1 = new Date();
			int statusCode = httpclient.executeMethod(postMethod);
//			Date d2 = new Date();
			if(statusCode != HttpStatus.SC_OK) {
				System.out.println(TAG+"Http错误状态码："+statusCode);
				ClientSyncController.stateExceptionDistribte(ISyncStateMonitor.StateExceptionCode.HTTP_STATUS_EXCEP);
				return false;
			}
			returnString = postMethod.getResponseBodyAsString();
			
//			System.out.println("时间差：：：：：："+AppFileUtils.getDateDiff(d2,d1));
			
		} catch (IOException e) {
			System.out.println(TAG+"Http异常"+e.getClass().getName());
			ClientSyncController.stateExceptionDistribte(ISyncStateMonitor.StateExceptionCode.HTTP_STATUS_EXCEP);
			e.printStackTrace();
		}
		postMethod.releaseConnection();
		if(returnString.equals("1")) {
			return true;
		} else if(returnString.equals("-1")) {
			//用户验证失败
			System.out.println(TAG+"上行数据传输失败,用户验证失败");
			ClientSyncController.stateExceptionDistribte(ISyncStateMonitor.StateExceptionCode.SER_AUTH_FAIL);
			return false;
		} else if(returnString.equals("-2")) {
			//session 过期
			System.out.println(TAG+"上行数据传输失败,Session过期");
			ClientSyncController.stateExceptionDistribte(ISyncStateMonitor.StateExceptionCode.SER_SESSION_INVALID);
			return false;
		}
		return false;
	}
	
	/** 
	* @Description: 上行请求结束 
	* @param strToken token
	* @param bTrash 结束状态 （成功，失败）
	* @return boolean  执行状态
	* @throws 
	*/ 
	public static boolean UpwardFinish (String strToken, boolean bTrash) {
		boolean excuteState = false;
		
		httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(App.TIMEOUT);
		httpclient.getHttpConnectionManager().getParams().setSoTimeout(App.TIMEOUT);
		httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");

		String btra = bTrash?"true":"false";
		PostMethod postMethod = new PostMethod(App.HOST);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		NameValuePair[] postData = new NameValuePair[3];
		postData[0] = new NameValuePair("strToken", strToken);
		postData[1] = new NameValuePair("bTrash", btra);
		postData[2] = new NameValuePair("requestType", "UpwardFinish");
		postMethod.addParameters(postData);
	
		try {
			int statusCode = httpclient.executeMethod(postMethod);
			if(statusCode != HttpStatus.SC_OK) {
				System.out.println(TAG+"Http错误状态码："+statusCode);
				ClientSyncController.stateExceptionDistribte(ISyncStateMonitor.StateExceptionCode.HTTP_STATUS_EXCEP);
				return false;
			}

			excuteState = (postMethod.getResponseBodyAsString().equals("1")?true:false);
		} catch (IOException e) {
			System.out.println(TAG+"Http异常"+e.getClass().getName());
			ClientSyncController.stateExceptionDistribte(ISyncStateMonitor.StateExceptionCode.HTTP_STATUS_EXCEP);
			e.printStackTrace();
		}
		postMethod.releaseConnection();
		System.out.println(TAG+"上行任务完成请求结束"+ excuteState);
		return excuteState;
	}
}
