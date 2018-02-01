package com.xinyu.controller.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyu.common.BaseController;
import com.xinyu.model.base.Centro;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.ItemGroup;
import com.xinyu.model.base.ItemGroupDetail;
import com.xinyu.model.base.User;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.inventory.enums.AdjustReasonTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.service.system.ItemGroupService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.UserService;

import net.sf.json.JSONArray;

/**
 * 组合商品
 * */
@Controller
@RequestMapping(value = "group")
public class ItemGroupCotroller extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ItemGroupService itemGroupService;
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 组合商品列表
	 * */
	@RequestMapping(value="list")
	public String ItemGroupList(HttpServletRequest request,ModelMap model){
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		return "admin/item/itemGroupList";
	}
	
	/**
	 * 组合商品列表数据
	 * */
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String, Object> ItemGroupListData(HttpServletRequest request,
			@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows){
		if (rows==10) {
			rows=100;
		}
		
		Map<String, Object> retMap = new HashMap<String,Object>();		
		
		Map<String, Object> params = new HashMap<String, Object>();
		String userId = request.getParameter("userId");
		String searchText = request.getParameter("searchText");
		params.put("userId", userId);
		params.put("searchText", searchText);
		
		List<ItemGroup> itemGroups = this.itemGroupService.getItemGroupListByParams(params,page,rows);
		
		int total = this.itemGroupService.getToTal(params);
		
		retMap.put("page", page);
		retMap.put("rows", this.itemGroupService.buildListData(itemGroups));
		retMap.put("total", total);
		
		return retMap;
	}
	
	
	/**
	 * 转向组合商品 添加
	 **/
	@RequestMapping(value="f7Add")
	public String toAdd(HttpServletRequest request,ModelMap model){
		
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		
		return "admin/item/itemGroupAdd";
	}
	
	/**
	 * 转向组合商品 修改
	 **/
	@RequestMapping(value="f7Edit")
	public String toEdit(HttpServletRequest request,ModelMap model){
		
		String id = request.getParameter("id");
		ItemGroup itemGroup = this.itemGroupService.getItemGroupById(id);
		model.put("itemGroup", itemGroup);
	
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
			
		return "admin/item/itemGroupEdit";
	}
	
	/**
	 * 新建组合商品选择列表可多选
	 * @param request
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="item/list")
	public String itemList(HttpServletRequest request,ModelMap model){
		String userId = request.getParameter("userId");
		User user = this.userService.getUserById(userId);
		model.put("user", user);
		return "admin/item/groupItemList";
	}
	
	/**
	 * 商品列表数据
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="item/listData")
	@ResponseBody
	public Map<String, Object> ItemListData(HttpServletRequest request){
		String userId = request.getParameter("userId");
		String itemName = request.getParameter("itemName");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("itemName", itemName);
		List<Item> items = this.itemService.getItemByList(params);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("rows", items);
		retMap.put("page", 1);
		retMap.put("total", items.size());
		return retMap;
	}
	
	/**
	 * 商品列表数据
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="detail/listData")
	@ResponseBody
	public Map<String, Object> detailListData(HttpServletRequest request){
		String id = request.getParameter("id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemGroupId", id);
		List<ItemGroupDetail> details = this.itemGroupService.getDetailsByList(params);
 		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("rows", this.buildDetails(details));
		retMap.put("page", 1);
		retMap.put("total", details.size());
		return retMap;
	}
	
	/**
	 * 封装明细
	 * */
	private List<Map<String, Object>> buildDetails(List<ItemGroupDetail> details) {
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for(ItemGroupDetail detail:details){
			Map<String, Object> map = new HashMap<String, Object>();
			Item item = this.itemService.getItem(detail.getItem().getId());
			map.put("itemId", item.getId());
			map.put("itemName", item.getName());
			map.put("color", item.getColor());
			map.put("barCode", item.getBarCode());
			map.put("num", detail.getNum());
			results.add(map);
		}
		return results;
	}

	/**
	 * 组合商品明细
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="newItemList/listData")
	@ResponseBody
	public Map<String, Object> newItemListData(HttpServletRequest request){
		
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		
		Object dataStr = request.getParameter("dataStr");
		
		JSONArray array = JSONArray.fromObject(dataStr);
		
		for(Object jsonObj : array){
			net.sf.json.JSONObject obj = (net.sf.json.JSONObject)jsonObj;
			String itemId = "" + obj.get("id");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("item", itemId);
			Item item = this.itemService.getItem(itemId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("itemId", item.getId());
			map.put("itemName", item.getName());
			map.put("color", item.getColor());
			map.put("barCode", item.getBarCode());
			results.add(map);
			}		
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("rows", results);
		retMap.put("total", results.size());
		retMap.put("page", 1);
		return retMap;
		
	}
	
	/**
	 * 保存组合商品信息
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> ItemGroupSave(HttpServletRequest request){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String json = request.getParameter("json");
		JSONObject object = new JSONObject(json);
		Iterator it = object.keys();
		Map<String, Object> data = new HashMap<String, Object>();
	    while (it.hasNext()) {  
	        String key = String.valueOf(it.next());  
	        String value = (String) object.get(key);  
	        data.put(key, value);  
	    }  
	   
	    List<Map<String, Object>>  dataList  = new ArrayList<Map<String, Object>>();
		Object rows = request.getParameter("rows");  //集合数据来源
		JSONArray array = JSONArray.fromObject(rows);
		for(Object jsonObj:array){
			net.sf.json.JSONObject obj = (net.sf.json.JSONObject) jsonObj;
			String num = ""+obj.get("num");
			String itemId = ""+obj.get("itemId");
			
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("num", num);
			map.put("itemId", itemId);
			dataList.add(map);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("dataList", dataList);
		
		params.put("data", data);
		
		Account account = (Account)SessionUser.get();
		params.put("account", account);
		
		retMap = this.itemGroupService.saveItemGroup(params);
		
		return retMap;
	}
	
	/**
	 * 禁用
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="closeItemGroupState")
	@ResponseBody
	public Map<String, Object> closeItemGroupState(HttpServletRequest request){
		String id = request.getParameter("id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", "false");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.itemGroupService.updateState(params);
			map.put("msg", "禁用成功！");
		} catch (Exception e) {
			map.put("msg", "禁用失败！"+e.getMessage());
			e.printStackTrace();
		}	
		return map;
	}
	
	
	/**
	 * 启用
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="openItemGroupState")
	@ResponseBody
	public Map<String, Object> openItemGroupState(HttpServletRequest request){
		String id = request.getParameter("id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", "true");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.itemGroupService.updateState(params);
			map.put("msg", "启用成功！");
		} catch (Exception e) {
			map.put("msg", "启用失败！"+e.getMessage());
			e.printStackTrace();
		}	
		return map;
	}
	
}
