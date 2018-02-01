package com.graby.store.admin.web.store;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.admin.web.BaseController;
import com.graby.store.entity.User;
import com.graby.store.entity.store.ShopTypeEnums;
import com.graby.store.entity.store.StatusEnums;
import com.graby.store.entity.store.UserShop;
import com.graby.store.remote.UserRemote;
import com.graby.store.remote.UserShopRemote;
import com.taobao.api.internal.util.WebUtils;

/**
 * 商家店铺设置操作类
 * 
 * @author lenovo
 *
 */
@Controller
@RequestMapping(value = "shop")
public class UserShopController extends BaseController {
	private final String current_access_token="current_access_token";
	
	@Autowired
	private UserShopRemote userShopRemote;
	@Autowired
	private UserRemote userRemote;
	
	public static String current_access_token_key="";
	
	@RequestMapping(value = "list")
	public String index(ModelMap model) {
		return "/store/system/shopList";
		//List<User> userList=this.userRemote.findUsers();
 	//	model.put("users", userList);
		//return "/storage/itemList";
	}

	@RequestMapping(value = "toAdd")
	public String toAdd(ModelMap model) {
		model.put("typeList", ShopTypeEnums.values());
		StringBuilder codeUrl=new StringBuilder();
		codeUrl.append(SystemSet.api_codeUrl)
		.append("?response_type=code&client_id=")
		.append(SystemSet.appKey)
		.append("&redirect_uri=")
		.append(SystemSet.api_codeUrl_redirect)
		.append("&state=1212&view=web")
		;
		model.put("codeUrl", codeUrl.toString());
		return "/store/system/shopAdd";
	}
	/**
	 * 保存店铺信息
	 * @return
	 * */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String,Object> saveUserShop(){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		String json=this.getString("json");
		JSONObject object=new JSONObject(json);
		UserShop shop=new UserShop();
		shop.setDescription(object.getString("description"));
		shop.setName(object.getString("name"));
		shop.setSessionKey(object.getString("sessionKey"));
		shop.setShopSource(ShopTypeEnums.valueOf(object.getString("source")));
		shop.setStatus(StatusEnums.ENABLED);
		this.userShopRemote.save(shop);
		return resultMap;
	}
	/**
	 * 获取code的回调
	 * @return
	 * */
	@RequestMapping(value="getSessionKey")
	public String getSessionKey(ModelMap model){
		String code=this.getString("code");
		System.out.println(code);
		String resultStr=getSessionKey(code);
		JSONObject json=new JSONObject(resultStr);
		String tokenCode=(String)json.get("access_token");
		this.getSession().setAttribute(this.current_access_token, tokenCode);
		current_access_token_key=tokenCode;
		model.put("sessionKey", tokenCode);
		return "system/sessionKey";
	}
	/**
	 * 获取tokenCode的回调
	 * @return
	 * */
	@RequestMapping(value="getTokenCode")
	@ResponseBody
	public String getTokenCode(){
		String tokenCode=(String) this.getSession().getAttribute(current_access_token);
		if(tokenCode==null){
			tokenCode=current_access_token_key;
		}
		return tokenCode;
	}

	private String getSessionKey(String code) {

		String url = "https://oauth.taobao.com/token";
		Map<String, String> props = new HashMap<String, String>();
		props.put("grant_type", "authorization_code");
		props.put("code", code);
		props.put("client_id", SystemSet.appKey);
		props.put("client_secret", SystemSet.secretKey);
		props.put("redirect_uri", "http://admin.wlpost.com/shop/getSessionKey");
		props.put("view", "web");
		String resultStr = "";
		try {
			resultStr = WebUtils.doPost(url, props, 30000, 30000);

			System.err.println(resultStr);
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		return resultStr;
	}

}
