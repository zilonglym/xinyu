package com.xinyu.check.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xinyu.check.model.Person;
import com.xinyu.check.model.User;
import com.xinyu.check.util.Digests;
import com.xinyu.check.util.Encodes;

/**
 * controller控制基类
 * @author yangmin
 *
 */
@Scope("session")
public class BaseController {
protected final Logger logger = Logger.getLogger(this.getClass());
	


	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}

	public HttpSession getSession() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
	}

	public ServletContext getServletContext() {
		return ContextLoader.getCurrentWebApplicationContext()
				.getServletContext();
	}

	public int getInt(String name) {
		return getInt(name, 0);
	}

	public int getInt(String name, int defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return Integer.parseInt(resultStr);
			} catch (Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public BigDecimal getBigDecimal(String name) {
		return getBigDecimal(name, null);
	}

	public BigDecimal getBigDecimal(String name, BigDecimal defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return BigDecimal.valueOf(Double.parseDouble(resultStr));
			} catch (Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public String getString(String name) {
		return getString(name, null);
	}

	public String getString(String name, String defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr == null || "".equals(resultStr)
				|| "null".equals(resultStr) || "undefined".equals(resultStr)) {
			return defaultValue;
		} else {
			return resultStr;
		}
	}

	public void outPrint(HttpServletResponse response, Object result) {
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	/**
	 * 密码加密
	 * */
	public void entryptPassword(Person person) {
		byte[] salt = Digests.generateSalt(8);
		person.setSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(person.getPassword().getBytes(), salt, 1024);
		person.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	
	/**
	 * 密码加密
	 * */
	public void entryptPassword(Person person,String salt) {
		person.setSalt(salt);
		byte[] hashPassword = Digests.sha1(person.getPassword().getBytes(), Encodes.decodeHex(salt), 1024);
		person.setPassword(Encodes.encodeHex(hashPassword));
	}
}
