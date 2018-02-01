package com.xinyu.model.common;

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
}
