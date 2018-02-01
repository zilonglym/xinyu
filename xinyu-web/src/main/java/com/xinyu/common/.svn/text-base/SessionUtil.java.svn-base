package com.xinyu.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * seesion帮助类
 * @author hxy
 *
 */
public class SessionUtil {
	
	 /**
	  * 获取Infra中的SessionId
	  * @param request
	  * @return
	  */
     public static  String getSessionId(HttpServletRequest request){
    	HttpSession session = request.getSession();
    	Cookie[] cookies = request.getCookies();
		String sessionId = session.getId();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equalsIgnoreCase("COMMONSESSION")) {
					sessionId = cookie.getValue();
				}
			}
		}			
    	return sessionId;
     }
     
    
     
 	
     
     /**
 	 * 切换城市数据源
 	 * @param request
 	 */
 	/*public static void changeCity(Map<String, String> urlMap,HttpServletRequest request){
 		String serverName = request.getServerName();
 		 
 		if(urlMap.get("SHENZHEN").indexOf(serverName) != -1){
 			ContextHolder.initThread(CityEnum.SHENZHEN, CityEnum.SHENZHEN.name()+InfraConstant.DATA_SOURCE_SUFFIX);
 		}else if(urlMap.get("SHANGHAI").indexOf(serverName) != -1){
 			ContextHolder.initThread(CityEnum.SHANGHAI, CityEnum.SHANGHAI.name()+InfraConstant.DATA_SOURCE_SUFFIX);
 		}else if(urlMap.get("ZHUHAI").indexOf(serverName) != -1){
 			ContextHolder.initThread(CityEnum.ZHUHAI, CityEnum.ZHUHAI.name()+InfraConstant.DATA_SOURCE_SUFFIX);
 		}else if(urlMap.get("GUANGZHOU").indexOf(serverName) != -1){
 			ContextHolder.initThread(CityEnum.GUANGZHOU, CityEnum.GUANGZHOU.name()+InfraConstant.DATA_SOURCE_SUFFIX);
 		}else if(urlMap.get("CHENGDU").indexOf(serverName) != -1){
 			ContextHolder.initThread(CityEnum.CHENGDU, CityEnum.CHENGDU.name()+InfraConstant.DATA_SOURCE_SUFFIX);
 		}else if(urlMap.get("CHONGQING").indexOf(serverName) != -1){
 			ContextHolder.initThread(CityEnum.CHONGQING, CityEnum.CHONGQING.name()+InfraConstant.DATA_SOURCE_SUFFIX);
 		}else if(urlMap.get("WUHAN").indexOf(serverName) != -1){
 			ContextHolder.initThread(CityEnum.WUHAN, CityEnum.WUHAN.name()+InfraConstant.DATA_SOURCE_SUFFIX);
 		}else if(urlMap.get("SUZHOU").indexOf(serverName) != -1){
 			ContextHolder.initThread(CityEnum.SUZHOU, CityEnum.SUZHOU.name()+InfraConstant.DATA_SOURCE_SUFFIX);
 		}else if(urlMap.get("NANJING").indexOf(serverName) != -1){
 			ContextHolder.initThread(CityEnum.NANJING, CityEnum.NANJING.name()+InfraConstant.DATA_SOURCE_SUFFIX);
 		}else if(urlMap.get("ZHENGZHOU").indexOf(serverName) != -1){
 			ContextHolder.initThread(CityEnum.ZHENGZHOU, CityEnum.ZHENGZHOU.name()+InfraConstant.DATA_SOURCE_SUFFIX);
 		}else if(urlMap.get("HANGZHOU").indexOf(serverName) != -1){
 			ContextHolder.initThread(CityEnum.HANGZHOU, CityEnum.HANGZHOU.name()+InfraConstant.DATA_SOURCE_SUFFIX);
 		}
 	}*/
}
