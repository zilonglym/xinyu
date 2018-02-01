package com.store.check;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;



/**
 * 
 * httpclient访问http接口的工具类
 * 
 * @author minux
 * 
 */
public class HttpClientUtils {

	/**
	 * 异常或�?没拿到返回结果的情况�?result�?"
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String httpPost(String url, String xmlStr) {
		DefaultHttpClient httpclient = null;
		HttpPost httpPost = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		String result = "";
		StringBuffer suf = new StringBuffer();
		try {
			httpclient = new DefaultHttpClient();
			httpPost = new HttpPost(url);
			//设置参数传入
			StringEntity bodyStr=new StringEntity(xmlStr,HTTP.UTF_8);
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1500);
			httpPost.setEntity(bodyStr);
			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				result= "";
			} else {
				entity = response.getEntity();
				if (null != entity) {
					InputStream in=entity.getContent();
					BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
					String tempbf;
					StringBuffer html = new StringBuffer(100);
					while ((tempbf = br.readLine()) != null) {
					    html.append(tempbf);
					}
					result = html.toString();
				} else {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != httpclient) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		return result;
	}
	
}
