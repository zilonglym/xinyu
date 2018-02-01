package com.xinyu.controller.inventory;

import java.util.ArrayList;
import java.util.Date;
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
import com.xinyu.dao.order.StockOrderOperatorDao;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.User;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.inventory.Inventory;
import com.xinyu.model.inventory.InventoryCount;
import com.xinyu.model.inventory.child.InventoryCountReturnOrderItem;
import com.xinyu.model.inventory.enums.AdjustReasonTypeEnum;
import com.xinyu.model.inventory.enums.InventoryCountStateEnum;
import com.xinyu.model.inventory.enums.InventoryOrderTypeEnum;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.order.StockOrderOperator;
import com.xinyu.model.order.enums.StockOperateTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.SystemItem;
import com.xinyu.service.caoniao.InventoryCountCpImpl;
import com.xinyu.service.inventory.InventoryCountService;
import com.xinyu.service.inventory.InventoryRecordService;
import com.xinyu.service.inventory.InventoryService;
import com.xinyu.service.inventory.child.InventoryCountReturnOrderItemService;
import com.xinyu.service.order.StockOrderOperatorService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.SystemItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.util.JsonUtil;

import net.sf.json.JSONArray;


@Controller
@RequestMapping(value = "inventoryCount")
public class InventoryCountController  extends BaseController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private InventoryCountService inventoryCountService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private InventoryCountReturnOrderItemService orderItemService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private InventoryCountCpImpl inventoryCount;
	
	@Autowired
	private StockOrderOperatorService  operatorService;
	 
	@RequestMapping(value = "upInventoryCount")
	@ResponseBody
	public Map<String, Object> upInventoryCount(HttpServletRequest request, ModelMap model) {

		String id = super.getString("id");
		
		Account account = SessionUser.get();
		 
		InventoryCount inventory = this.inventoryCountService.getInventoryCountById(id);
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		if (inventory.getState().equals(InventoryCountStateEnum.CANCEL)) {
			retMap.put("msg", "单据已取消，不可操作！");
		}else if(inventory.getState().equals(InventoryCountStateEnum.WMS_UP_FINISH)){
			retMap.put("msg", "单据重复提交！");
		}else {
			retMap = inventoryCount.execute(id,account);
		}
		
		this.createStockOperator(inventory,account,StockOperateTypeEnum.CONFIRM,""+retMap.get("msg"));
		
		return retMap;
	}
	
	/**
	 * 盘点单日志
	 * @param inventoryCount
	 * @param account
	 * @param StockOperateTypeEnum
	 * @param msg
	 * */
	private void createStockOperator(InventoryCount inventoryCount, Account account, StockOperateTypeEnum type,
			String msg) {
		StockOrderOperator operator = new StockOrderOperator();
		
		operator.generateId();
			
		operator.setAccount(account);
		
		operator.setOperateDate(new Date());
		
		operator.setOperateType(type);
		
		operator.setNewValue(inventoryCount.getState().getKey());
		
		operator.setOldValue(InventoryCountStateEnum.SAVE.getKey());
		
		operator.setOrderId(inventoryCount.getCheckOrderCode());
		
		operator.setOrderType(inventoryCount.getOrderType().getDescription());
			
		operator.setDescription("单据确认："+inventoryCount.getCheckOrderCode()+"|"+msg);	
		
		this.operatorService.insertStockOrderOperator(operator);
	}
	
	/**
	 * 库存盘点单列表
	 * @param model
	 * @return String
	 * @throws Throwable 
	 * */
	@RequestMapping(value="list")
	public String InventoryCountList(ModelMap model) throws Throwable{
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
			
		InventoryTypeEnum[] itemTypes = InventoryTypeEnum.values();
		model.put("itemTypes", JsonUtil.toJson(itemTypes));
		
		InventoryOrderTypeEnum[] orderTypes= InventoryOrderTypeEnum.values();
		model.put("orderTypes", orderTypes);
		
		InventoryCountStateEnum[] status = InventoryCountStateEnum.values();
		model.put("status", status);
		
		return "admin/inventory/inventoryCountList";
	}
	
	/**
	 * 库存盘点单数据
	 * @param request
	 * @param rows
	 * @param page
	 * @return map
	 * */
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String, Object> InventoryCountListData(HttpServletRequest request,
			@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows){
		String userId = request.getParameter("userId");
		String status = request.getParameter("status");
		String orderType = request.getParameter("orderType");
		String searchText = request.getParameter("searchText");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId",userId);
		if (orderType!=null&&orderType!="") {
			for(InventoryOrderTypeEnum type:InventoryOrderTypeEnum.values()){
				if (orderType.equals(type.getKey())) {
						params.put("orderType",type);
				}
			}
		}
		params.put("status",status);
		params.put("searchText",searchText);
		
		List<InventoryCount> inventoryCounts = this.inventoryCountService.findInventoryCountsByPage(params,page,rows);
		int total = this.inventoryCountService.getTotal(params);
		Map<String, Object> retMap = new HashMap<String,Object>();
		retMap.put("page", page);
		retMap.put("rows", this.inventoryCountService.buildListData(inventoryCounts));
		retMap.put("total", total);
		return retMap;
	}
	
	/**
	 * 库存盘点单明细页面
	 * @param request
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="inventoryCountItem/list")
	public String InventoryCountItemList(HttpServletRequest request,ModelMap model){
		String id = request.getParameter("id");
		InventoryCount inventoryCount = this.inventoryCountService.getInventoryCountById(id);
		
		List<InventoryCountReturnOrderItem> orderItems = inventoryCount.getItemList();
		List<Map<String, Object>> resultList = this.inventoryCountService.buildDetailListData(orderItems);
		model.put("orderItems", resultList);
		return "admin/inventory/orderItemsList";
	}
	
	/**
	 * 库存盘点单明细数据
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="inventoryCountItem/listData")
	@ResponseBody
	public Map<String, Object> orderItemListData(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		
		String orderId = request.getParameter("orderId");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("invnetoryCountId", orderId);
		
		List<InventoryCountReturnOrderItem> orderItems = this.orderItemService.getInventoryCountReturnOrderItemByList(params);
		map.put("rows",this.orderItemService.buildListData(orderItems));
		map.put("total", orderItems.size());
		return map;
	}
	
	/**
	 * 新建库存盘点单
	 * @param request
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="f7Add")
	public String InventoryCountAdd(HttpServletRequest request,ModelMap model) throws Throwable{
		
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		
		model.put("types", InventoryOrderTypeEnum.values());
		
		model.put("reasons", AdjustReasonTypeEnum.values());
		
		return "admin/inventory/inventoryCountAdd";
	}
	
	
	
//	@RequestMapping(value="itemList/listData")
//	@ResponseBody
//	public Map<String, Object> itemListData(HttpServletRequest request){
//		Map<String, Object> map = new HashMap<String, Object>();
//		String userId = request.getParameter("userId");
//		if (userId.isEmpty()) {
//			return null;
//		}else {
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("userId", userId);	
//			List<Item> items = this.itemService.getItemByList(params);
//			map.put("rows", this.itemService.buildItemList(items));
//			map.put("total", items.size());
//			System.err.println(map);
//			return map;
//		}	
//	}
	
	/**
	 * 新建库存盘点单对应商家商品信息
	 * @param request
	 * @return json
	 * */
	@RequestMapping(value="itemList/listData")
	@ResponseBody
	public JSONArray itemListData(HttpServletRequest request){
		
		String userId = request.getParameter("userId");
		
		if (userId.isEmpty()) {			
			return null;
		}else {
			
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
	 * 编辑库存盘点单
	 * @param request
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="f7Edit")
	public String InventoryCountEdit(HttpServletRequest request,ModelMap model){
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		
		model.put("itemTypes", InventoryTypeEnum.values());	
		
		model.put("reasons", AdjustReasonTypeEnum.values());
		
		model.put("types", InventoryOrderTypeEnum.values());
		
		String id = request.getParameter("id");
		InventoryCount inventoryCount = this.inventoryCountService.getInventoryCountById(id);
		User user = this.userService.getUserById(inventoryCount.getUser().getId()) ;
		inventoryCount.setUser(user);
		model.put("inventoryCount", inventoryCount);
		
		return "admin/inventory/inventoryCountEdit";
	}
	
	/**
	 * 保存库存盘点单
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> InventoryCountSave(HttpServletRequest request){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String json=request.getParameter("json");	
		JSONObject object=new JSONObject(json);
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
		for(Object jsonObj : array){
			net.sf.json.JSONObject obj = (net.sf.json.JSONObject)jsonObj;
			String  num1 = ""+obj.get("num1");  // 正品数量
			String  num2 = ""+obj.get("num2");  //残次品数量
			String  num3 = ""+obj.get("num3");  //机损数量
			String  num4 = ""+obj.get("num4");  //箱损数量
			String itemId = ""+obj.get("itemId");
			String orderItemId = ""+obj.get("orderItemId");
			
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("num1", num1);
			map.put("num2", num2);
			map.put("num3", num3);
			map.put("num4", num4);
			map.put("itemId", itemId);
			map.put("orderItemId", orderItemId);
			
			dataList.add(map);
		}
	    
		String id = object.getString("id");		
		//单据不存在，新建单据
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dataList", dataList);
		params.put("data", data);
		params.put("account", (Account)SessionUser.get());
	
		
		if (id.isEmpty()) {		
			retMap = this.inventoryCountService.saveInventoryCount(params,"insert");		
		}
		//单据存在，修改单据信息
		else{
			retMap = this.inventoryCountService.saveInventoryCount(params,"update");	
		}
		return retMap;
	}
	
	@RequestMapping(value="item/list")
	public String itemList(HttpServletRequest request,ModelMap model){
		String userId = request.getParameter("userId");
//		System.err.println("userId:"+userId);
		User user = this.userService.getUserById(userId);
//		System.err.println("user"+user.getId());
		model.put("user", user);
		return "admin/inventory/itemList";
	}
	
	@RequestMapping(value="item/listData")
	@ResponseBody
	public Map<String, Object> ItemListData(HttpServletRequest request){
		String userId = request.getParameter("userId");
		String itemName = request.getParameter("itemName");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("itemName", itemName);
//		System.err.println("params:"+params);
		List<Item> items = this.itemService.getItemByList(params);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("rows", items);
		retMap.put("page", 1);
		retMap.put("total", items.size());
		return retMap;
	}
	
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
			params.put("itemId", itemId);
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
