package com.graby.store.admin.web.store;

/**
 * 系统设置项
 * @author lenovo
 *
 */
public interface SystemSet {
	
	public final static String appKey="23012748";
	public final static String secretKey="f2e7f709ff1a05f6e09745612a048a61";
	
	public final static String api_codeUrl="https://oauth.taobao.com/authorize";
	public final static String api_codeUrl_redirect="http://zc.wlpost.com/shop/getSessionKey";
//	public final static String api_codeUrl_redirect="http://admin.wlpost.com/shop/getSessionKey";
	
	public final static String api_token_url="https://oauth.taobao.com/token";
	public final static String api_token_redirect="http://admin.wlpost.com";
}
