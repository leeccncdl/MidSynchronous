package cn.zytec.midsynchronous.ws;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import cn.zytec.midsynchronous.utils.Base64;

public class UpwardWs {
	private static final String TAG = "TAG:UpwardWs";
	private static final String HOST = "http://192.168.4.117:8080/MidSynchronous/servlet/ServletEntrance";

	private static HttpClient httpclient = new HttpClient();
	public static String UpwardRequest (String strJsonTask, String strJsonIdentity) {
		String token = null;
		PostMethod postMethod = new PostMethod(HOST);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		NameValuePair[] postData = new NameValuePair[3];
		postData[0] = new NameValuePair("strJsonTask", strJsonTask);
		postData[1] = new NameValuePair("strJsonIdentity", strJsonIdentity);
		postData[2] = new NameValuePair("requestType", "UpwardRequest");
		postMethod.addParameters(postData);

		
		
		try {
			int statusCode = httpclient.executeMethod(postMethod);
			if(statusCode!=200) {
				System.out.println(TAG+"上行数据传输申请http返回异常");
				return null;
			}
//			httpclient.executeMethod(postMethod);
			token = postMethod.getResponseBodyAsString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		postMethod.releaseConnection();
		
		if(token.equals("")) {
			return null;
		}
		return token;
	}
	
	public static String UpwardTransmit (String strToken, String fileName,long lOffset, byte[] buffer) {

		System.out.println(TAG +" fileName:"+fileName+" Offset:"+lOffset+"~~~~~~");
		
		String returnString = null;
		PostMethod postMethod = new PostMethod(HOST);
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
			int statusCode = httpclient.executeMethod(postMethod);
			if(statusCode!=200) {
				System.out.println(TAG+"上行传输数据http返回错误");
				return "";
			}
			returnString = postMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		postMethod.releaseConnection();

		return returnString;
	}
	
	public static boolean UpwardFinish (String strToken, boolean bTrash) {
		boolean excuteState = false;

		String btra = bTrash?"true":"false";
		PostMethod postMethod = new PostMethod(HOST);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		NameValuePair[] postData = new NameValuePair[3];
		postData[0] = new NameValuePair("strToken", strToken);
		postData[1] = new NameValuePair("bTrash", btra);
		postData[2] = new NameValuePair("requestType", "UpwardFinish");
		postMethod.addParameters(postData);
	
		try {
			int statusCode = httpclient.executeMethod(postMethod);
			if(statusCode!=200) {
				System.out.println(TAG+"上行完成请求http出错");
				return false;
			}

			excuteState = (postMethod.getResponseBodyAsString().equals("true")?true:false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		postMethod.releaseConnection();
		
		return excuteState;
	}
}
