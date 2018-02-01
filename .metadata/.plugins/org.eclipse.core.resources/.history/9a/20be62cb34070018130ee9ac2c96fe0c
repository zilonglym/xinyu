package com.xinyu.controller.trade;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xinyu.common.BaseController;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.User;
import com.xinyu.model.system.Account;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderBack;
import com.xinyu.model.trade.ShipOrderBackItem;
import com.xinyu.model.trade.TmsOrder;
import com.xinyu.model.trade.TmsOrderItem;
import com.xinyu.service.caoniao.WmsConsignOrderConfirmService;
import com.xinyu.service.system.AccountService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.trade.ShipOrderBackItemService;
import com.xinyu.service.trade.ShipOrderBackService;
import com.xinyu.service.trade.ShipOrderService;
import com.xinyu.service.trade.TmsOrderService;
import com.xinyu.service.trade.WmsConsignOrderItemService;

import net.sf.json.JSONArray;

/**
 * 退货
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "orderBack")
public class ShipOrderBackController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ShipOrderService orderService;
	
	@Autowired
	private WmsConsignOrderItemService orderItemService;
	
	@Autowired
	private ShipOrderBackService orderBackService;
	
	@Autowired
	private ShipOrderBackItemService orderBackItemService;
	
	@Autowired
	private TmsOrderService tmsOrderService;
	
	/**
	 * 退货单据列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="list")
	public String orderBackList(ModelMap model){
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		return "admin/shipOrder/orderBackList";
	}

	/**
	 * 退货数据
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String, Object> orderBackListData(HttpServletRequest request,@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "500") int rows){
		if (rows==10) {
			rows=100;
		}
		String userId = request.getParameter("userId");
		String q = request.getParameter("q");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("q", q);
		List<ShipOrderBack> orderBacks = this.orderBackService.getShipOrderBackListByPage(params, page, rows);
		int total = this.orderBackService.getTotal(params);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("page", page);
		resultMap.put("rows", this.buildListData(orderBacks));
		resultMap.put("total", total);
		return resultMap;
	}

	/**
	 * 封装数据
	 */
	private List<Map<String, Object>> buildListData(List<ShipOrderBack> orderBacks) {
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(ShipOrderBack orderBack:orderBacks){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", orderBack.getId());
			/**
			 * 菜鸟商家信息查询不到查store那边
			 */
			User user = this.userService.getUserById(orderBack.getUserId());
			if (user!=null) {
				map.put("userName", user.getSubscriberName());
			}else {
				Map<String, Object> storeUser = this.userService.getStoreUserById(orderBack.getUserId());
				map.put("userName", ""+storeUser.get("shopName"));
			}
			
			map.put("returnCode", orderBack.getBackOrderCode());
			map.put("orderCode", orderBack.getTmsOrderCode());
			map.put("createDate", sf.format(orderBack.getCreateDate()));
			Account account = this.accountService.findAcountById(orderBack.getCreateBy().getId());
			map.put("account", account.getUserName());
			map.put("description", orderBack.getDescription());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderBackId", orderBack.getId());
			List<ShipOrderBackItem> orderBackItems = this.orderBackItemService.getOrderBackItemList(params);
			String items = "";
			for(int i = 0;i<orderBackItems.size();i++){
				items = items + orderBackItems.get(i).getItemName()+"("+orderBackItems.get(i).getQuantity()+");";
			}
			map.put("items", items);
			results.add(map);
		}
		return results;
	}
	
	/**
	 * 添加
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toAddBackOrder")
	public String toAddBackOrder(ModelMap model){
		
		return "admin/shipOrder/toAddBackOrder";
	}
	
	/**
	 * 修改
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="toEditBackOrder")
	public String toEditBackOrder(ModelMap model,HttpServletRequest request){
		String id = request.getParameter("id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		ShipOrderBack orderBack = this.orderBackService.getShipOrderBackByParams(params);
		model.put("orderBack", orderBack);
		return "admin/shipOrder/toEditBackOrder";
	}
	
	/**
	 * 添加商品选择列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="itemList")
	public String itemList(ModelMap model){
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		return "admin/shipOrder/toAddBackOrderItem";
	}
	
	/**
	 * 商品列表数据
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="itemListData")
	@ResponseBody
	public Map<String, Object> itemListData(HttpServletRequest request,@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "100") int rows){
		
		if (rows==10) {
			rows=100;
		}
		
		String userId = request.getParameter("userId");
		String q = request.getParameter("q");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("searchText", q);
		
		 Map<String, Object> retMap = new HashMap<String, Object>();
		
		if (StringUtils.isNotBlank(userId)) {
			
			Pattern pattern = Pattern.compile("[0-9]*");
	        Matcher isNum = pattern.matcher(userId);
	        /**
	         * userId是数值，查询store那边，否则查菜鸟这边          
	         */
			if(!isNum.matches()){
				List<Item> items = this.itemService.getItemsByPage(page, rows, params);
				int total = this.itemService.getTotal(params);
				retMap.put("rows", items);
				retMap.put("total", total);
			}else {
				List<Map<String, Object>> items = this.itemService.getStoreItemsByPage(page, rows, params);
				int total = this.itemService.getStoreTotal(params);
				System.err.println("items:"+items);
				retMap.put("rows", items);
				retMap.put("total", total);
			}
		}
		
		retMap.put("page", page);
	
		return retMap;
	}
	
	/**
	 * 添加表单明细数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="initOrderItem")
	@ResponseBody
	public Map<String, Object> backOrderItemList(HttpServletRequest request){
		String ids = request.getParameter("ids");
		String[] idsArry = ids.split(",");
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for(int i = 0; i<idsArry.length; i++ ){
			Map<String, Object> map = new HashMap<String, Object>();
			Item item = this.itemService.getItem(idsArry[i]);
			User user = this.userService.getUserById(item.getUser().getId());
			map.put("shopName", user.getSubscriberName());
			map.put("userId", item.getUser().getId());
			map.put("itemId", item.getId());
			map.put("itemName", item.getName());
			map.put("itemCode", item.getItemCode());
			map.put("itemSku", item.getSpecification());
			map.put("barCode", item.getBarCode());
			map.put("num", "1");
			results.add(map);
		}
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("rows", results);
		retMap.put("page", 1);
		retMap.put("total", results.size());
		return retMap;
	}
	
	/**
	 * 修改表单明细数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="initBackItem")
	@ResponseBody
	public Map<String, Object> initBackItem(HttpServletRequest request){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String id = request.getParameter("id");
		
		System.err.println("id:"+id);
		
		if (StringUtils.isNotBlank(id)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderBackId", id);
			List<ShipOrderBackItem> orderBackItems = this.orderBackItemService.getOrderBackItemList(params);
			List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
			for(ShipOrderBackItem orderBackItem:orderBackItems){
				Map<String, Object> map = new HashMap<String, Object>();
				Item item = this.itemService.getItem(orderBackItem.getItem().getId());
				System.err.println("item:"+item);
				/**
				 * 菜鸟Item为空，查store那边Item
				 */
				if (item!=null) {
					User user = this.userService.getUserById(item.getUser().getId());
					map.put("shopName", user.getSubscriberName());
					map.put("userId", item.getUser().getId());
					map.put("itemId", item.getId());
					map.put("itemName", item.getName());
					map.put("itemCode", item.getItemCode());
					map.put("itemSku", item.getSpecification());
					map.put("barCode", item.getBarCode());
				}else {
					Map<String, Object> itemMap = this.itemService.findStoreItemById(orderBackItem.getItem().getId());
					Map<String, Object> userMap = this.userService.getStoreUserById(""+itemMap.get("userId"));
					map.put("shopName", ""+userMap.get("shopName"));
					map.put("userId", ""+itemMap.get("userId"));
					map.put("itemId", ""+itemMap.get("itemId"));
					map.put("itemName", ""+itemMap.get("itemName"));
					map.put("itemCode", ""+itemMap.get("itemCode"));
					map.put("itemSku", ""+itemMap.get("itemSku"));
					map.put("barCode", ""+itemMap.get("barCode"));
				}
			
				map.put("num", orderBackItem.getQuantity());
				results.add(map);
			}
			retMap.put("rows", results);		
			retMap.put("total", results.size());
		}
		
		retMap.put("page", 1);
		
		return retMap;
	}
	
	/**
	 * 根据原始单号查明细数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="initItem")
	@ResponseBody
	public Map<String, Object> orderItemList(HttpServletRequest request){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		
		String orderCode = request.getParameter("orderCode");
		
		System.err.println("orderCode:"+orderCode);
		
		if (StringUtils.isNotBlank(orderCode)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("code", orderCode);
			List<ShipOrder> orders = this.orderService.getShipOrderListByAll(params);
			/**
			 * 菜鸟order为空，查store那边order
			 */
			if (orders.size()>0) {
				ShipOrder order = orders.get(0);
				System.err.println("菜鸟orderId:"+order.getId());
				params.clear();
				params.put("tmsOrderId", order.getTmsOrder().getId());
				List<TmsOrderItem> orderItems = this.tmsOrderService.getTmsOrderItemByList(params);
				for(TmsOrderItem orderItem:orderItems){
					Map<String, Object> map = new HashMap<String, Object>();
					Item item = this.itemService.getItem(orderItem.getItem().getId());
					User user = this.userService.getUserById(item.getUser().getId());
					map.put("shopName", user.getSubscriberName());
					map.put("userId", item.getUser().getId());
					map.put("itemId", item.getId());
					map.put("itemName", item.getName());
					map.put("itemCode", item.getItemCode());
					map.put("itemSku", item.getSpecification());
					map.put("barCode", item.getBarCode());
					map.put("num", "1");
					results.add(map);
				}
				retMap.put("rows", results);
				retMap.put("total", results.size());
			}else {
				List<Map<String, Object>> list = this.orderService.findStoreOrderList(params);
				if (list.size()>0) {
					Map<String, Object> map = list.get(0);
					String orderId = "" + map.get("orderId");
					System.err.println("中仓orderId："+orderId);
					params.clear();
					params.put("orderId", orderId);
					List<Map<String, Object>> sList = this.orderItemService.findStoreOrderItemList(params);
					retMap.put("rows", sList);
					retMap.put("total", sList.size());
				}
			}
		}
		
		retMap.put("page", 1);
	
		return retMap;
	}
	
	/**
	 * 添加保存
	 * @param request
	 * @return
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> saveBackOrder(HttpServletRequest request){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			String json=request.getParameter("json");
			JSONObject object=new JSONObject(json);
			
			ShipOrderBack orderBack = new ShipOrderBack();
			orderBack.generateId();
			
			String orderCode = "" + object.getString("orderCode");	
			System.err.println("orderCode:"+StringUtils.isNotBlank(orderCode));
			if (StringUtils.isNotBlank(orderCode)) {
				orderBack.setTmsOrderCode(orderCode);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("code", orderCode);
				System.err.println("params:"+params);
				List<ShipOrder> orders = this.orderService.getShipOrderListByAll(params);
				System.err.println("orders:"+orders.size());
				/**
				 * 菜鸟order为空，查store那边order
				 */
				if (orders.size()>0) {
					ShipOrder order = orders.get(0);
					orderBack.setOrderId(order.getTmsOrder().getId());
					orderBack.setOrderId(order.getTmsOrder().getId());
					orderBack.setUserId(order.getUser().getId());
					System.err.println("id:"+order.getTmsOrder().getId());
				}else {
					List<Map<String, Object>> list = this.orderService.findStoreOrderList(params);
					if (list.size()>0) {
						Map<String, Object> map = list.get(0);
						orderBack.setUserId(""+map.get("userId"));
						orderBack.setOrderId(""+map.get("orderId"));
						System.err.println("id:"+map.get("orderId"));
					}
				}
			}
			
			String returnCode = "" + object.getString("returnCode");
			orderBack.setBackOrderCode(returnCode);
			
			String reason = "" + object.getString("reason");
			String description = "" + object.getString("description");
			orderBack.setDescription(reason+";"+description);
	
			orderBack.setCreateBy(this.getCurrentAccount());
			
			Object rows = request.getParameter("rows");  //集合数据来源   
			JSONArray array = JSONArray.fromObject(rows);
			
			for(Object jsonObj : array){
				net.sf.json.JSONObject obj = (net.sf.json.JSONObject)jsonObj;
				String itemId = "" + obj.getString("itemId");
				String num = "" + obj.getString("num");
				String userId = "" + obj.getString("userId");
				String itemName = "" + obj.getString("itemName");
				String itemSku = "" + obj.getString("itemSku");
				String itemCode = "" + obj.getString("itemCode");
				String barCode = "" + obj.getString("barCode");
				orderBack.setUserId(userId);
				Item item = this.itemService.getItem(itemId);
				
				ShipOrderBackItem orderBackItem = new ShipOrderBackItem();
				
				/**
				 * 菜鸟item为空，查store那边item
				 */	
				if (item!=null) {
					String name = item.getName() + item.getSpecification();
					orderBackItem.setItemName(name);
					orderBackItem.setItem(item);
				}else {
					String name = itemName + itemSku;
					Item storeItem = new Item();
					storeItem.setId(itemId);
					storeItem.setName(itemName);
					storeItem.setItemCode(itemCode);
					storeItem.setSpecification(itemSku);
					storeItem.setBarCode(barCode);
					
					orderBackItem.setItemName(name);
					orderBackItem.setItem(storeItem);
				}
			
				
				orderBackItem.generateId();
				orderBackItem.setOrderBack(orderBack);
				orderBackItem.setQuantity(Integer.valueOf(num));
				this.orderBackItemService.insertOrderBackItem(orderBackItem);
			}	
			
			this.orderBackService.insertShipOrderBack(orderBack);
			
			retMap.put("msg", "添加成功！");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			retMap.put("msg", "添加失败！"+e.getMessage());
		}
		
		return retMap;
	}
	
	/**
	 * 更新保存
	 */
	@RequestMapping(value="update")
	@ResponseBody
	public Map<String, Object> updateBackOrder(HttpServletRequest request){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			String json=request.getParameter("json");
			JSONObject object=new JSONObject(json);
			
			String id = "" + object.getString("id");	
			
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", id);
			ShipOrderBack orderBack = this.orderBackService.getShipOrderBackByParams(m);
			
			String orderCode = "" + object.getString("orderCode");	
			
			if (StringUtils.isNotBlank(orderCode)) {
				
				orderBack.setTmsOrderCode(orderCode);
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("code", orderCode);
				List<ShipOrder> orders = this.orderService.getShipOrderListByAll(params);
				/**
				 * 菜鸟order为空，查store那边order
				 */
				if (orders.size()>0) {
					ShipOrder order = orders.get(0);
					orderBack.setOrderId(order.getTmsOrder().getId());
					orderBack.setOrderId(order.getTmsOrder().getId());
					orderBack.setUserId(order.getUser().getId());
					
				}else {
					List<Map<String, Object>> list = this.orderService.findStoreOrderList(params);
					if (list.size()>0) {
						Map<String, Object> map = list.get(0);
						orderBack.setUserId(""+map.get("userId"));
						orderBack.setOrderId(""+map.get("orderId"));
						System.err.println("id:"+map.get("orderId"));
					}
				}
			}
			
			String returnCode = "" + object.getString("returnCode");
			orderBack.setBackOrderCode(returnCode);
			
			String reason = "" + object.getString("reason");
			String description = "" + object.getString("description");
			orderBack.setDescription(reason+";"+description);
	
			orderBack.setCreateBy(this.getCurrentAccount());
			
			Object rows = request.getParameter("rows");  //集合数据来源   
			JSONArray array = JSONArray.fromObject(rows);
			/**
			 * 修改明细先删除当前原有明细，重新添加
			 */
			this.orderBackItemService.deleteOrderBackItemByBackId(id);
			
			for(Object jsonObj : array){
				net.sf.json.JSONObject obj = (net.sf.json.JSONObject)jsonObj;
				String itemId = "" + obj.getString("itemId");
				String num = "" + obj.getString("num");
				String userId = "" + obj.getString("userId");
				String itemName = "" + obj.getString("itemName");
				String itemSku = "" + obj.getString("itemSku");
				String itemCode = "" + obj.getString("itemCode");
				String barCode = "" + obj.getString("barCode");
				orderBack.setUserId(userId);
				Item item = this.itemService.getItem(itemId);
				
				ShipOrderBackItem orderBackItem = new ShipOrderBackItem();
				
				/**
				 * 菜鸟item为空，查store那边item
				 */
				if (item!=null) {
					String name = item.getName() + item.getSpecification();
					orderBackItem.setItemName(name);
					orderBackItem.setItem(item);
				}else {
					String name = itemName + itemSku;
					Item storeItem = new Item();
					storeItem.setId(itemId);
					storeItem.setName(itemName);
					storeItem.setItemCode(itemCode);
					storeItem.setSpecification(itemSku);
					storeItem.setBarCode(barCode);
					
					orderBackItem.setItemName(name);
					orderBackItem.setItem(storeItem);
				}
			
				
				orderBackItem.generateId();
				orderBackItem.setOrderBack(orderBack);
				orderBackItem.setQuantity(Integer.valueOf(num));
				this.orderBackItemService.insertOrderBackItem(orderBackItem);
			}	
			
			this.orderBackService.updateShipOrderBack(orderBack);
			
			retMap.put("msg", "更新成功！");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			retMap.put("msg", "更新失败！"+e.getMessage());
		}
		
		return retMap;
	}
	
}
