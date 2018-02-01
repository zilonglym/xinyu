package com.xinyu.service.util;

public class StringUtils {
	

	public static String concat(String... source) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < source.length; i++) {
			buf.append(source[i]);
			if (i < source.length - 1)
				buf.append(",");
		}
		return buf.toString();
	}
	
	
	public static String formatAddZero(int length , String source)  {
		int n=length;
		String zeroStr = String.format("%1$0"+(n-source.length())+"d",0)+source;
		
		return  zeroStr;
	}
}
