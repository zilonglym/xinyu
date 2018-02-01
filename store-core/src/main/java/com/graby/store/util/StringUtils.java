package com.graby.store.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
   /**
    * 获取前面数字 字符串
    * @param source
    * @return
    */
   public  static String  startNum(String  source){
	   
		Pattern pattern = Pattern.compile("^(\\d+)(.*)");
		Matcher matcher = pattern.matcher(source);
		if (matcher.matches()) {//数字开头
			return matcher.group(1);
		}
		return "";
   }
	
	
	
	
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
