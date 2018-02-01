package com.xinyu.model.common;
/**
 * 当前登陆的User，线程绑定
 * @author yangmin
 *
 */
public class SessionUser {
    private static final ThreadLocal currentUser = new ThreadLocal();

    public static <T>T get() {
        return (T) currentUser.get();
    }

    public static <T>  void set(T account) {
    	currentUser.set(account);
    }
    
    public static void clear() {
    	currentUser.remove();
    }
}
