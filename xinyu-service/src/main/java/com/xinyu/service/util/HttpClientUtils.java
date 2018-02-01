package com.xinyu.service.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;



/**
 * 
 * httpclient访问http接口的工具类
 * 
 * @author minux
 * 
 */
public class HttpClientUtils {

	public static final Logger logger = Logger.getLogger(HttpClientUtils.class);
	private static Map<String, String> headers = new HashMap<String, String>();
	static {
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
		headers.put("Accept-Language", "zh-cn,zh;q=0.5");
		headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		headers.put(
				"Accept",
				" image/gif, image/x-xbitmap, image/jpeg, "
						+ "image/pjpeg, application/x-silverlight, application/vnd.ms-excel, "
						+ "application/vnd.ms-powerpoint, application/msword, application/x-shockwave-flash, */*");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Accept-Encoding", "gzip, deflate");
	}

	/**
	 * 异常或者没拿到返回结果的情况下,result为""
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String httpPost(String url, String xmlStr) {
		logger.info("httpPost URL [" + url + "] start ");
		DefaultHttpClient httpclient = null;
		HttpPost httpPost = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		String result = "";
		StringBuffer suf = new StringBuffer();
		try {
			httpclient = new DefaultHttpClient();
			// 设置cookie的兼容性---考虑是否需要
			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
					CookiePolicy.BROWSER_COMPATIBILITY);
			httpPost = new HttpPost(url);
			// 设置各种头信息
			for (Entry<String, String> entry : headers.entrySet()) {
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			// 传入各种参数
		/*	if (null != param) {
				for (Entry<String, Object> set : param.entrySet()) {
					String key = set.getKey();
					String value = set.getValue() == null ? "" : set.getValue()
							.toString();
					nvps.add(new BasicNameValuePair(key, value));
					suf.append(" [" + key + "-" + value + "] ");
				}
			}*/
			//设置参数传入
			if(StringUtils.isNotBlank(xmlStr)){
				StringEntity bodyStr=new StringEntity(xmlStr,HTTP.UTF_8);
				httpPost.setEntity(bodyStr);
				logger.info("param " + bodyStr);
			}
			
			//httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			// 设置连接超时时间
			/*HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),3600
					);
			// 设置读数据超时时间
			HttpConnectionParams.setSoTimeout(httpPost.getParams(),3600);
			*/
			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("HttpStatus ERROR" + "Method failed: "
						+ response.getStatusLine());
				return "";
			} else {
				entity = response.getEntity();
				if (null != entity) {
					byte[] bytes = EntityUtils.toByteArray(entity);
					result = new String(bytes, "UTF-8");
				} else {
					logger.error("httpPost URL [" + url
							+ "],httpEntity is null.");
				}
				/**
				 * 正常返回结果集
				 */
				return result;
			}
		} catch (Exception e) {
			logger.error("httpPost URL [" + url + "] error, ", e);
			return "";
		} finally {
			if (null != httpclient) {
				httpclient.getConnectionManager().shutdown();
			}
			logger.info("RESULT:  [" + result + "]");
			logger.info("httpPost URL [" + url + "] end ");
		}
	}
	
}
