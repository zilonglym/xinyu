package com.graby.store.simple;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.taobao.api.internal.util.WebUtils;

public class StoreLogin {

	private static String top_appkey="1023018428";
	private static String	top_appSecret="sandbox9ff1a05f6e09745612a048a61";
	private static String	top_redirectUri="http://127.0.0.1:8080/top_oauth";
	private static String code_url="https://oauth.tbsandbox.com/authorize";
	private static String token_url="https://oauth.taobao.com/token";
	public static void main(String[] args) {
	      String url=token_url;
	      Map<String,String> props=new HashMap<String,String>();
	      props.put("grant_type","authorization_code");
	      /*测试时，需把test参数换成自己应用对应的值*/
	      props.put("code","test");
	      props.put("client_id","test");
	      props.put("client_secret","test");
	      props.put("redirect_uri","http://www.test.com");
	      props.put("view","web");
	      String s="";
	      try{s=WebUtils.doPost(url, props, 30000, 30000);
	      System.out.println(s);
	      }catch(IOException e){
	          e.printStackTrace();}

	    } 
}
