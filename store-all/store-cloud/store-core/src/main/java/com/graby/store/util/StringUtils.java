package com.graby.store.util;


public class StringUtils {
	
	public static String concat(String ... source) {
		
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < source.length; i++) {
			buf.append(source[i]);
			if (i < source.length-1)
			buf.append(",");
		}
		return buf.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(concat("aa", "bb", "dd"));
	}
}
