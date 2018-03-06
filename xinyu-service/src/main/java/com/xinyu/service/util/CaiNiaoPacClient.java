package com.xinyu.service.util;

import com.taobao.pac.sdk.cp.PacClient;
import com.taobao.pac.sdk.cp.util.HttpUtil;
import com.xinyu.service.common.Constants;

/**
 * 取菜鸟调用对象
 * @author yangmin
 * 2017年5月2日
 *
 */
public class CaiNiaoPacClient {
	
	private static PacClient pacClient;

	
	private static String url="https://link.tbsandbox.com/gateway/link.do";
	private static String appKey="SANDBOX026991";
	private static String secretKey="49j3ZNoOe36kqof6c484SHG00W27J83i";
	
	/**
	 * 获取菜鸟的调用对象，采用单例
	 * @return 
	 */
	public static PacClient getClient(){
		if(pacClient==null){
			pacClient=	new PacClient(Constants.cainiao_appKey, Constants.cainiao_secretKey, Constants.cainiao_url);
//			pacClient=	new PacClient(appKey, secretKey, url);
		}
		return pacClient;
	}
}
