package com.graby.store.admin.web.item;

import java.text.SimpleDateFormat;
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

import com.graby.store.admin.web.BaseController;
import com.graby.store.entity.Item;
import com.graby.store.entity.ItemGroup;
import com.graby.store.entity.ItemGroupDetail;
import com.graby.store.entity.User;
import com.graby.store.remote.ItemGroupRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.UserRemote;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "itemGroup")
public class ItemGroupCotroller extends BaseController{
	
	@Autowired
	private UserRemote userRemote;
	
	@Autowired
	private ItemGroupRemote itemGroupRemote;
	
	@Autowired
	private ItemRemote itemRemote;
	
	/**
	 * 组合商品列表
	 * @param request
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="list")
	public String ItemGroupList(HttpServletRequest request,ModelMap model){
		List<User> users = this.userRemote.findUsers();
		model.put("users", users);
		return "item/itemGroupList";
	}
	
	/**
	 * 组合商品列表数据
	 * @param request
	 * @param page
	 * @param rows
	 * @return map
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
		
		List<ItemGroup> itemGroups = this.itemGroupRemote.getItemGroupListByParams(params,page,rows);
		
		int total = this.itemGroupRemote.getToTal(params);
		
		retMap.put("page", page);
		retMap.put("rows", this.buildListData(itemGroups));
		retMap.put("total", total);
		
		return retMap;
	}
	
	/**
	 * 封装数据
	 * @param itemGroups
	 * @return list
	 * */
	private List<Map<String, Object>> buildListData(List<ItemGroup> itemGroups) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for(ItemGroup itemGroup:itemGroups){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", itemGroup.getId());
			User user = this.userRemote.getUser(itemGroup.getUser().getId());
			map.put("userName", user.getShopName());
			map.put("remark", itemGroup.getRemark());
			map.put("itemName", itemGroup.getName());
			map.put("barCode", itemGroup.getBarCode());
			map.put("level", itemGroup.getPriority());
			map.put("laterDate", sf.format(itemGroup.getLaterDate()));
			map.put("state", itemGroup.getState());
			results.add(map);
		}
		return results;
	}

	/**
	 * 转向组合商品 添加
	 * @param request
	 * @param model
	 * @return string
	 **/
	@RequestMapping(value="f7Add")
	public String toAdd(HttpServletRequest request,ModelMap model){
		
		List<User> users = this.userRemote.findUsers();
		model.put("users", users);
		
		return "item/itemGroupAdd";
	}
	
	/**
	 * 转向组合商品 修改
	 * @param request
	 * @param model
	 * @return String
	 **/
	@RequestMapping(value="f7Edit")
	public String toEdit(HttpServletRequest request,ModelMap model){
		
		String id = request.getParameter("id");
		ItemGroup itemGroup = this.itemGroupRemote.getItemGroupById(id);
		model.put("itemGroup", itemGroup);
	
		List<User> users = this.userRemote.findUsers();
		model.put("users", users);
			
		return "item/itemGroupEdit";
	}
	
	/**
	 * 新建组合商品选择列表可多选
	 * @param request
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="f7Item/list")
	public String itemList(HttpServletRequest request,ModelMap model){
		String userId = request.getParameter("userId");
		User user = this.userRemote.getUser(Long.parseLong(userId));
		model.put("user", user);
		return "item/groupItemList";
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
		params.put("name", itemName);
		List<Item> items = this.itemRemote.getItemListByParams(params);
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
//		System.err.println(params);
		List<ItemGroupDetail> details = this.itemGroupRemote.getDetailsByList(params);
 		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("rows", this.buildDetails(details));
		retMap.put("page", 1);
		retMap.put("total", details.size());
		return retMap;
	}
	
	/**
	 * 封装明细
	 * @param details
	 * @return list
	 * */
	private List<Map<String, Object>> buildDetails(List<ItemGroupDetail> details) {
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for(ItemGroupDetail detail:details){
			Map<String, Object> map = new HashMap<String, Object>();
			Item item = this.itemRemote.getItem(detail.getItem().getId());
			map.put("itemId", item.getId());
			map.put("itemName", item.getTitle());
			map.put("color", item.getSku());
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
		
		Object dataStr = request.getParameter("dataStr");
		
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
				
		JSONArray array = JSONArray.fromObject(dataStr);
		
		for(Object jsonObj : array){
			net.sf.json.JSONObject obj = (net.sf.json.JSONObject)jsonObj;
			String itemId = "" + obj.get("id");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("item", itemId);
			Item item = this.itemRemote.getItem(Long.parseLong(itemId));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("itemId", item.getId());
			map.put("itemName", item.getTitle());
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
//		System.err.println(json);
		Iterator it = object.keys();
		Map<String, Object> data = new HashMap<String, Object>();
	    while (it.hasNext()) {  
	        String key = String.valueOf(it.next());  
	        String value = (String) object.get(key);  
	        data.put(key, value);  
	    }  
	   
	    Object rows = request.getParameter("rows");  //集合数据来源
	    
	    List<Map<String, Object>>  dataList  = new ArrayList<Map<String,Object>>();
		
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
		
		retMap = this.itemGroupRemote.saveItemGroup(params);
		
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
			this.itemGroupRemote.updateState(params);
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
			this.itemGroupRemote.updateState(params);
			map.put("msg", "启用成功！");
		} catch (Exception e) {
			map.put("msg", "启用失败！"+e.getMessage());
			e.printStackTrace();
		}	
		return map;
	}
	
}
