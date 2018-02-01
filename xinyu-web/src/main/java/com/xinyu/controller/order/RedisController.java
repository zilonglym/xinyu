package com.xinyu.controller.order;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyu.common.BaseController;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.RdsConstants;
import com.xinyu.model.base.User;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.util.IRedisProxy;
import com.xinyu.service.util.ObjectTranscoder;

@Controller
@RequestMapping(value = "redis")
public class RedisController extends BaseController {

	@Autowired
	private IRedisProxy redisProxy;
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private UserService userService;
	
	/**
	 * 初始化商品信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="initMobileItem")
	@ResponseBody
	public Map<String,Object> initMobileItemInfo() throws Exception{
		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		List<Item> itemList=this.itemService.getItemByList(null);
		for(int i=0;i<itemList.size();i++){
			Item item=itemList.get(i);
			User user=this.userService.getUserById(item.getUser().getId());
			Map<String,String> itemMap=new HashMap<String, String>();
			itemMap.put("id", item.getId());
			itemMap.put("itemName", item.getName());
			itemMap.put("code", item.getItemCode());
			itemMap.put("color", item.getColor());
			itemMap.put("barCode", item.getBarCode());
			if(user==null){
				continue;
			}
			itemMap.put("userId", user.getId());
			itemMap.put("userName", user.getShortName());
			itemMap.put("shortName", item.getShortName());
			String itemKey=RdsConstants.MOBILE_ITEM+item.getId()+item.getBarCode()+item.getItemCode()+item.getName()+user.getShortName();
			this.redisProxy.set(itemKey.getBytes("utf-8"), ObjectTranscoder.serialize(itemMap));
			logger.error(i+""+itemMap);
		}
		logger.error("初始化完成！");
		return resultMap;
	}

	@RequestMapping(value = "index")
	public void index() {
		String itemKey=RdsConstants.ORDER_ACCEPT+"*";
		Set<String> itemKeys=this.redisProxy.keys(itemKey);
//		Map<String, Object> map = (Map<String, Object>) ObjectTranscoder.deserialize(this.redisProxy.get(item.getBytes()));
		System.err.println(itemKeys.size());
		

	}
	
}
