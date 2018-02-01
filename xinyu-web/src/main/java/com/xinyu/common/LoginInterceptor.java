package com.xinyu.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xinyu.controller.indexController;
import com.xinyu.model.base.User;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.system.Account;

/**
 * 将用户信息存入SessionPerson
 * 
 * @author yangmin
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private List<String> excludeUris;
	private List<String> timerUris;

	public void setTimerUris(List<String> timerUris) {
		this.timerUris = timerUris;
	}

	private static final Logger LOGGER = Logger.getLogger(LoginInterceptor.class);

	private boolean matchUri(String uri){
		if(excludeUris !=  null){
			for(String _u : excludeUris){
				if(uri.startsWith(_u))return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception { 
		//清空Person
	//	SessionUser.clear();
		// IE浏览器 使用Chrome Frame加速
		response.addHeader("X-UA-Compatible", "chrome=1");
		//return true;/*
		String requestType=request.getHeader("X-Requested-With");
		String uri = request.getRequestURI().replaceAll(request.getContextPath(), "");
//		LOGGER.error("LoginInterceptor:["+requestType+"]"+uri);
		if (excludeUris != null && (excludeUris.contains(uri) || this.matchUri(uri))) {
			return true;
		}
		if(uri.indexOf("cainiao")!=-1 || uri.indexOf("check")!=-1){
			return true;
		}
		
			String sessionId = SessionUtil.getSessionId(request);
			Account account= SessionUser.get();
//			LOGGER.error("LoginInterceptor1:"+uri+account);
			if(account==null){
				account=(Account)request.getSession().getAttribute(indexController.Session_User_key);
				if(account!=null){
					SessionUser.set(account);
					LOGGER.error("account is not null;");
				}
				LOGGER.error("account is null");
			}
			boolean flag = true;
			if (account != null ) {			
					flag = false;
				if (timerUris == null || !(timerUris.contains(uri))) {
					if (account != null) {
						//memcachedClient.replace(sessionId, InfraConstant.SESSION_TIME, userInfo);
					}
				}
			}
			if (flag) { 
					String url = request.getScheme() + "://"
							+ request.getServerName() + ":"
							+ request.getServerPort()
							+request.getContextPath();
					url = url + "/login";
//					request.getRequestDispatcher("/login").forward(request, response);
					response.sendRedirect(url);
				return false;
			}else{
				return super.preHandle(request, response, handler);
			}
		//*/
	}

	public void setExcludeUris(List<String> excludeUris) {
		this.excludeUris = excludeUris;
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	
}
