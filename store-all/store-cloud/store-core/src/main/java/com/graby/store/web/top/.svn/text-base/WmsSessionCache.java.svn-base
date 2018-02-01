package com.graby.store.web.top;

import javax.annotation.Resource;

import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.cache.Cache;

@Resource
public class WmsSessionCache {
	
	@Autowired
	private Cache<String, String> userCache;
	
	public static final String WMS_SESSION_KEY = "WMS_SESSION_KEY_";
	
	public boolean isAuthed(String nick) {
		return getSessionKey(nick) != null;
	}
	
	public void setSessionKey(String nick, String sessionKey) {
		userCache.put(WMS_SESSION_KEY + nick, sessionKey);
	}
	
	public String getSessionKey(String nick) {
		return userCache.get(WMS_SESSION_KEY + nick);
	}
}
