package com.graby.store.portal.web;

import java.math.BigDecimal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
/**
 * 公共类
 * @author yangin
 *
 */
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.graby.store.entity.Centro;
import com.graby.store.entity.Person;
import com.graby.store.entity.User;
import com.graby.store.util.Digests;
import com.graby.store.util.Encodes;
@Controller
public class BaseController {

	/***
	 * 当前登陆用户
	 * @return
	 */
	public User getCurrentUser(){
		User user=new User();
		user.setId(Long.valueOf("1"));
		return user;
	}
	
	/**
	 * 取当前仓库
	 * @return
	 */
	public Centro getCurrentCentro(){
		Centro centro=new Centro();
		centro.setId(Long.valueOf("1"));
		return centro;
	}
	
	
	/**
	 * 密码加密
	 * */
	public void entryptPassword(Person person,String salt) {
		person.setSalt(salt);
		byte[] hashPassword = Digests.sha1(person.getPassword().getBytes(), Encodes.decodeHex(salt), 1024);
		person.setPassword(Encodes.encodeHex(hashPassword));
	}
	

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

}
