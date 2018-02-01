package com.xinyu.util;

import org.apache.shiro.SecurityUtils;


public class ShiroContextUtils {
	
	public static void setWmsSessionKey(String sessionKey) {
		SecurityUtils.getSubject().getSession().setAttribute("WMS_SESSION_KEY", sessionKey);
	}
	
	public static String getWmsSessionKey() {
		return (String)SecurityUtils.getSubject().getSession().getAttribute("WMS_SESSION_KEY");
	}
}
