package com.graby.store.remote;

import java.util.Map;

public interface ExpressRemote {
	
	/**
	 * 获取快递公司名称
	 * @param code 快递公司编码
	 * @return 快递公司名称
	 */
	String getExpressCompanyName(String code);
	
	/**
	 * 获取快递公司
	 * @return
	 */
	Map<String,String> getExpressMap();
	
	/**
	 * 运单规则校验
	 * @param code 快递公司编码
	 * @param orderno 运单号
	 * @return
	 */
	boolean validate(String code, String orderno);

}
