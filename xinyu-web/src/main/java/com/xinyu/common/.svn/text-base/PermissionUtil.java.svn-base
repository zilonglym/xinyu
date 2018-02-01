package com.xinyu.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;




/**
 * 权限处理
 * @author hxy
 *
 */
public class PermissionUtil {

	
	/**
	 * 得到路径
	 * 
	 * @param request
	 * @return
	 */
	public static String processUri(String uri) {
		// 去掉问号
		final int questionMarkIndex = uri.indexOf("?");
		if (questionMarkIndex != -1) {
			uri = uri.substring(0, questionMarkIndex);
		}
		return uri;
	}
	
	/**
	 * 移除域名和端口
	 * @param uri
	 * @return
	 */
	public static String removeHostAndPort(String uri){
		final String hostAndPortMark="http://";
		final boolean existHostAndPort=uri.contains(hostAndPortMark);//http://
		if(!existHostAndPort){
			return uri;
		}
		return uri.substring(uri.indexOf("/",hostAndPortMark.length()));
	}
	
	
	/**
	 * 得到路径前缀 例如 /shihua-infra/login/toLogin 返回 /shihua-infra/
	 * 
	 * @param uri
	 * @return
	 */
	public static String getUriPrefix(String uri) {
		return uri.substring(0, uri.indexOf("/", 1) + 1);
	}

	/**
	 * 匹配
	 * 
	 * @param url
	 *            用户提交过来的url
	 * @param targetUrl
	 *            含有通配符的url
	 * @return
	 */
	public static boolean matchUrl(String url, String targetUrl) {
		if (StringUtils.isEmpty(url) || StringUtils.isEmpty(targetUrl)) {
			return false;
		}
		
        if(targetUrl.indexOf(url)!=-1){
        	return true;
        }
        
        return false;
	}
	/***
	 * 匹配url
	 * @param url
	 * @param targetUrl
	 * @return
	 */
	public static boolean startsWithUrl(String url, String targetUrl) {
		if (StringUtils.isEmpty(url) || StringUtils.isEmpty(targetUrl)) {
			return false;
		}
		
        if(targetUrl.startsWith(url) ){
        	return true;
        }
        
        return false;
	}

	/**
	 * 判断请求是否为ajax
	 * @param request
	 * @return
	 */
	public static boolean isAjaxAccess(HttpServletRequest request){
		String requestType = request.getHeader("X-Requested-With");  
		if("XMLHttpRequest".equals(requestType)){
			return true;
		}
		
		return false;
	}
	
	

}
