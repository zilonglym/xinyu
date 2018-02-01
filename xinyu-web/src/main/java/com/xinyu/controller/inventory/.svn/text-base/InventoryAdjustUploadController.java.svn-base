package com.xinyu.controller.inventory;

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
import com.xinyu.model.base.User;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.inventory.InventoryAdjustUpload;
import com.xinyu.model.inventory.InventoryCount;
import com.xinyu.model.inventory.enums.AdjustReasonTypeEnum;
import com.xinyu.model.inventory.enums.InventoryOrderTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.service.caoniao.WmsInventoryAdjustUploadCpImpl;
import com.xinyu.service.inventory.InventoryAdjustUploadService;
import com.xinyu.service.inventory.InventoryService;
import com.xinyu.service.system.CentroService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.UserService;

import net.sf.json.JSONArray;

/**
 * 库存调整单
 * 残次转正,
 * 或者  正品转残次
 * */
@Controller
@RequestMapping(value = "inventoryAdjust")
public class InventoryAdjustUploadController  extends  BaseController{

	@Autowired
	private   UserService  userService;
	
	@Autowired
	private   ItemService  itemService;
	
	@Autowired
	private   CentroService  centroService;
	
	@Autowired
	private   InventoryService  inventoryService;
	
	@Autowired
	private  InventoryAdjustUploadService  adjustService;
	
	@Autowired
	private  WmsInventoryAdjustUploadCpImpl  adjustUploadCpImpl;
	
	/**
	 * 调整单列表
	 * */
	@RequestMapping(value="list")
	public String inventoryAdjustList(HttpServletRequest request,ModelMap model){
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		return "admin/inventory/inventoryAdjustList";
	}
	
	
	/**
	 * 库存调整单数据
	 * */
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String, Object> inventoryAdjustListData(HttpServletRequest request,
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
		
		List<InventoryAdjustUpload> adjustUploads = this.adjustService.getInventoryAdjustUploadListByParams(params);
		
		int total = this.adjustService.getToTal(params);
		
		retMap.put("page", page);
		retMap.put("rows", this.adjustService.buildListData(adjustUploads));
		retMap.put("total", total);
		
		return retMap;
	}
	
	
	/**
	 * 转向调整单 添加
	 **/
	@RequestMapping(value="f7Add")
	public String toAdd(HttpServletRequest request,ModelMap model){
		
		String type = request.getParameter("type");
		if (type.equals("0")) {
			model.put("key", "0");
			model.put("description", "正转残");
		}else if(type.equals("1")){
			model.put("key", "1");
			model.put("description", "残转正");
		}	
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		
		List<Centro> centros = this.centroService.getCentroByList(null);
		model.put("centros", centros);
		
		model.put("reasonTypes", AdjustReasonTypeEnum.values());
		
		return "admin/inventory/inventoryAdjustAdd";
	}
	
	/**
	 * 转向调整单orderItem
	 **/
	@RequestMapping(value="itemList/listData")
	@ResponseBody
	public JSONArray orderItemListData(HttpServletRequest request,ModelMap model){
		String userId = request.getParameter("userId");
		if (userId.isEmpty()) {
			return null;
		}else{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			List<Item> items = this.itemService.getItemByList(params);
			JSONArray jsonArray = new JSONArray();
			for(Item item:items){
				net.sf.json.JSONObject object = new net.sf.json.JSONObject();
				object.put("id", item.getId());
				String name = item.getName()+(item.getColor()==null?"":(","+item.getColor()));
				object.put("name", name);
				jsonArray.add(object);
			}	
			return jsonArray;		
		}
	}
	
	
	/**
	 * 保存库存调整单
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> InventoryAdjustSave(HttpServletRequest request){
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
		System.err.println(dataList);
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("dataList", dataList);
		
		params.put("data", data);
		
		Account account = (Account)SessionUser.get();
		params.put("account", account);
		
		retMap = this.adjustService.saveInventoryAdjust(params);
		
		return retMap;
	}
	
	/**
	 * 提交调整单
	 * @param request
	 * @param model
	 * @return map
	 * */
	@RequestMapping(value = "upInventoryAdjustUplaod")
	@ResponseBody
	public Map<String, Object> upInventoryAdjustUplaod(HttpServletRequest request, ModelMap model) {

		String id = super.getString("id");
		
		Account account = SessionUser.get();
					
		return adjustUploadCpImpl.execute(id,account);
	}
	
	/**
	 * 新建调整单商品选择列表可多选
	 * @param request
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="item/list")
	public String itemList(HttpServletRequest request,ModelMap model){
		String userId = request.getParameter("userId");
		User user = this.userService.getUserById(userId);
		model.put("user", user);
		return "admin/inventory/itemList";
	}
	
	/**
	 * 列表数据
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
	 * 单据明细
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
			List<Map<String, Object>> inventories = this.inventoryService.findInventoryByPage(params, 0, 500);
			if (inventories.size()!=0) {
				results.add(this.buildData(inventories.get(0)));
			}else{
				Item item = this.itemService.getItem(itemId);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("itemId", item.getId());
				map.put("itemName", item.getName());
				map.put("color", item.getColor());
				map.put("barCode", item.getBarCode());
				map.put("num11", 0);
				map.put("num12", 0);
				results.add(map);
			}
			
		}
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("rows", results);
		retMap.put("total", results.size());
		retMap.put("page", 1);
		return retMap;
		
	}
	
	/**
	 * 构建商品数据
	 * @param itemMap
	 * @return map
	 * */
	private Map<String, Object> buildData(Map<String, Object> itemMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemId", itemMap.get("itemId"));
		map.put("itemName", itemMap.get("itemName"));
		map.put("color", itemMap.get("color"));
		map.put("barCode", itemMap.get("barCode"));
		map.put("num11", itemMap.get("c1"));
		map.put("num12", itemMap.get("c2"));
		return map;
	}
}
