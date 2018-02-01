package com.xinyu.service.util;

/**
 * 第三方快递平台地址处理
 * @author yangmin
 *
 */
public class AddressUtil {

	public static String sfFormat(String address){
		if(address==null) return "";     
		String str=format(address);
		return str;
	}
	
	public static String cainiaoFormat(String address){
		if(address==null) return "";     
		String str=format(address);
		return str;
	}
	
	
	public static String format(String address){
		   
	     String html = address;
	     html = html.replaceAll( "&", "");
	     html = html.replaceAll( "\\\\", "");
	     html = html.replaceAll( "\"", "");  //"
	     html = html.replaceAll( "\t", "");// 替换跳格
	     html = html.replaceAll( "'", "");// 替换空格
	     html = html.replaceAll("<", "");
	     html = html.replaceAll( ">", "");
	     return html;
	}
	
	
	public static void main(String[] args) {
		String str="四川省 成都市 双流县 中和街道中和大道二段卡斯摩广场99号负一楼\\番茄码头生活超市\\";
		System.err.println(format(str));
	}
	
}
