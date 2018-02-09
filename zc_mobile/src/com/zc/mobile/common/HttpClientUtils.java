package com.zc.mobile.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * 
 * httpclient访问http接口的工具类
 * 
 * @author minux
 * 
 */
public class HttpClientUtils {
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
			// 设置参数传入
			if (xmlStr != null) {
				StringEntity bodyStr = new StringEntity(xmlStr, HTTP.UTF_8);
				httpPost.setEntity(bodyStr);
			}
			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				result = "";
			} else {
				entity = response.getEntity();
				if (null != entity) {
					InputStream in = entity.getContent();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in, "UTF-8"));
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
			System.err.println("httpPost URL [" + url + "] error, ");
		} finally {
			if (null != httpclient) {
				httpclient.getConnectionManager().shutdown();
			}
			// logger.info("httpPost URL [" + url + "] end ");
		}
		return result;
	}

	public static String httpGet(String url, String xmlStr) {
		DefaultHttpClient httpclient = null;
		HttpGet httpGet = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		String result = "";
		StringBuffer suf = new StringBuffer();
		try {
			httpclient = new DefaultHttpClient();
			httpGet = new HttpGet(url);

			// 设置参数传入

			response = httpclient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				result = "";
			} else {
				entity = response.getEntity();
				if (null != entity) {

					result = EntityUtils.toString(entity, "UTF-8").trim();

				} else {
					System.err.println("httpPost URL [" + url
							+ "],httpEntity is null.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("httpPost URL [" + url + "] error, ");
		} finally {
			if (null != httpclient) {
				httpclient.getConnectionManager().shutdown();
			}
			System.err.println("RESULT:  [" + result + "]");
		}
		return result;
	}

	public static String convertStreamToString(InputStream is) {
		StringBuilder sb1 = new StringBuilder();
		byte[] bytes = new byte[4096];
		int size = 0;

		try {
			while ((size = is.read(bytes)) > 0) {
				String str = new String(bytes, 0, size, "UTF-8");
				sb1.append(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb1.toString();
	}

}