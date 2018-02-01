package com.xinyu.controller.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyu.common.BaseController;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.PackageInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.order.StockInOrder;
import com.xinyu.model.order.StockInOrderConfirm;
import com.xinyu.model.order.child.StockInCheckItem;
import com.xinyu.model.order.child.StockInItemInventory;
import com.xinyu.model.order.child.StockInOrderItem;
import com.xinyu.model.order.child.WmsStockInOrderItem;
import com.xinyu.model.order.enums.InOrderStateEnum;
import com.xinyu.model.order.enums.InOrderTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.service.caoniao.StockInOrderConfirmCpImpl;
import com.xinyu.service.caoniao.WmsOrderStatusUploadCpImpl;
import com.xinyu.service.order.StockInOrderConfirmService;
import com.xinyu.service.order.StockInOrderService;
import com.xinyu.service.order.WmsStockInOrderItemService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author shark_cj
 * @since  2017-05-23
 * 采购入库单确认
 */
@Controller
@RequestMapping(value = "inOrderConfirm")
public class StockInOrderConfirmController extends BaseController {
	
	@Autowired
	private ItemService  itemService;
	
	@Autowired
	private StockInOrderConfirmService  confirmService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WmsStockInOrderItemService stockInOrderItemService;
	
	@Autowired
	private  StockInOrderService stockInOrderService;
	
	@Autowired
	private  WmsOrderStatusUploadCpImpl  orderStatusUpload;
	
	@Autowired
	private  StockInOrderConfirmCpImpl   stockInOrderConfirm;
	
	
	@Autowired
	private  StockInOrderConfirmService   stockInOrderConfirmService;
	
	
	@RequestMapping(value="confirmDateListByOrderId")
	@ResponseBody
	public  Map<String,Object>  confirmDateList(){
		String orderId = super.getString("id");
		stockInOrderConfirmService.getConfirmListDataByOrderId(orderId);
		System.out.println("orderId:"+orderId);
		Map<String,Object>  retMap  =  new HashMap<String, Object>();
		retMap.put("rows", stockInOrderConfirmService.getConfirmListDataByOrderId(orderId));
		return retMap;
	}
	
	
	/**
	 * 入库单列表
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="stockInOrderList")
	public String StockInOrderList(ModelMap model){
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		model.put("orderTypes", InOrderTypeEnum.values());
		model.put("orderStatuses", InOrderStateEnum.values());
		return "admin/inventory/stockInOrderList";
	}
	
	/**
	 * 入库单列表数据
	 * @param request
	 * @param model
	 * @return map
	 * */
	@RequestMapping(value="stockInOrderListData")
	@ResponseBody
	public Map<String, Object>  StockInOrderListData(HttpServletRequest request,ModelMap model,
			@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows){
		if (rows==10) {
			rows=100;
		}
		String userId = request.getParameter("userId");
		String orderType = request.getParameter("orderType");
		String searchText = request.getParameter("searchText");
		String orderStatus = request.getParameter("orderStatus");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("searchText", searchText);
		if (orderStatus!=null&&orderStatus!="") {
			params.put("state", InOrderStateEnum.getInOrderStateEnum(orderStatus));
		}
		if (orderType!=null&&orderType!="") {
			params.put("orderType", InOrderTypeEnum.getOrderTypeEnum(orderType));
		}
		
		List<StockInOrder> stockInOrders = this.stockInOrderService.findStockInOrderByPage(params,page,rows);
		int total = this.stockInOrderService.getTotal(params);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("page", page);
		retMap.put("rows", this.stockInOrderService.buildListData(stockInOrders));
		retMap.put("total", total);
		return retMap;
	}
	
	
	/**
	 * 入库单据明细页面
	 * @param request
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="f7StockInOrderItemList")
	public String  WmsStockInOrderItemList(HttpServletRequest request,ModelMap model){
		String id = request.getParameter("id");
		StockInOrder stockInOrder = this.stockInOrderService.findStockInOrderById(id);
		List<StockInOrder> stockInOrders = new ArrayList<StockInOrder>();
		stockInOrders.add(stockInOrder);
		model.put("stockOrder", this.stockInOrderService.buildListData(stockInOrders).get(0));
		return "admin/inventory/inOrderItemsList";
	}
	
	@RequestMapping(value="StockInOrderItemList")
	public String  StockInOrderItemList(HttpServletRequest request,ModelMap model){
		String id = request.getParameter("orderId");
		System.err.println(id);
		StockInOrder stockInOrder = this.stockInOrderService.findStockInOrderById(id);
		model.put("stockOrder", stockInOrder);
		return "admin/inventory/orderItemsList";
	}
	
	/**
	 * 入库单据明细数据
	 * @param request
	 * @param model
	 * @return map
	 * */
	@RequestMapping(value="stockInOrderItemListData")
	@ResponseBody
	public Map<String, Object>  WmsStockInOrderItemListData(HttpServletRequest request,ModelMap model){
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		StockInOrder stockInOrder = this.stockInOrderService.findStockInOrderById(id);
		List<WmsStockInOrderItem> orderItems = stockInOrder.getOrderItemList();
		map.put("rows", this.stockInOrderItemService.buildDetailListData(orderItems));
		map.put("total", orderItems.size());
		return map;
	}
	
	
	/**
	 * 终结订单状态
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="finalConfirm")
	@ResponseBody
	public Map<String, Object>  finalConfirm(HttpServletRequest request,ModelMap model){
		Map<String, Object> map = new HashMap<String, Object>();
		Object data  = request.getParameter("data");  //集合数据来源
		Object orderId  = request.getParameter("id"); //入库单 ID
		StockInOrderConfirm  confirm  = new StockInOrderConfirm();
		confirm.generateId();
		JSONArray array = JSONArray.fromObject(data);
		List<StockInOrderItem>  orderList  = new ArrayList<StockInOrderItem>();
		List<StockInCheckItem>  checkList  = new ArrayList<StockInCheckItem>();
		for(Object jsonObj : array){
			JSONObject object = (JSONObject)jsonObj;
			String  orderItemId = ""+object.get("orderItemId");
			
			StockInOrderItem itemOrderItem  = new StockInOrderItem();
			itemOrderItem.generateId();
			
			List<StockInItemInventory>  inventoryList = new ArrayList<StockInItemInventory>();
		    StockInItemInventory itemInventory = new StockInItemInventory();
			itemInventory.generateId();
			itemInventory.setInventoryType(InventoryTypeEnum.getInventoryType("1"));
			itemInventory.setQuantity(0);  //终结订单直接  给商品设置为0
			itemInventory.setStockInOrderItem(itemOrderItem);
			inventoryList.add(itemInventory);
			
			itemOrderItem.setItems(inventoryList);
			orderList.add(itemOrderItem);
			
			String  itemId = ""+object.get("itemId");
			Item item = itemService.getItem(itemId);
			itemOrderItem.setItem(item);
			itemOrderItem.setOrderItemId(orderItemId);
			
			//此处计算入库 商品  个  单位, 目前默认一件商品
			itemOrderItem.setHeight(item.getWmsHeight()!=null?item.getWmsHeight().intValue():0);
			itemOrderItem.setVolume(item.getVolume()!=null?item.getVolume().intValue():0);
			itemOrderItem.setLength(item.getLength()!=null?item.getLength().intValue():0);
			itemOrderItem.setWeight(item.getWmsGrossWeight()!=null?item.getWmsGrossWeight():0);
			itemOrderItem.setWidth(item.getWidth()!=null?item.getWidth().intValue():0);
			
			//设置需要检测商品
			StockInCheckItem checkItem = new StockInCheckItem();
			checkItem.generateId();
			checkItem.setOrderItemId(orderItemId);
			
			//设置checkNum  逻辑
			//如果confirmtype  0, 全部提交,  checkNum 为: 之前所有部分提交之和  + 当前提交数
			//如果confirmtype  1, 部分提交, checkNum为:当前部分提交商品数
			Map<String,Object>    params =  new HashMap<String, Object>();
			params.put("orderItemId", orderItemId);
			int checkItemSum = confirmService.getCheckItemSum(params);
			checkItem.setQuantity(checkItemSum);
			checkItem.setStockInOrderConfirm(confirm);
			checkList.add(checkItem);
			itemOrderItem.setStockInOrderConfirm(confirm);
			
		}
		
		if(checkList.size()>0){
			confirm.setCheckItems(checkList);
			confirm.setOrderItems(orderList);
			StockInOrder stockInOrder = stockInOrderService.findStockInOrderById(""+orderId);
			
			confirm.setOrderCode(stockInOrder.getOrderCode());
			confirm.setOrderType(stockInOrder.getOrderType());
			confirm.setOutBizCode(""+new Date().getTime());
			confirm.setOrderConfirmTime(new Date());
			confirm.setTimeZone(stockInOrder.getTimeZone());
			confirm.setConfirmType(0);//设置终结提交
			//map.put("msg", "<table><tr><td>详细信息:</td><td>残次品 12个</td></tr><tr><td>详细信息:</td><td>正品 11个</td></tr></table>");
			confirm.setStockInOrder(stockInOrder);
			if (stockInOrder.getState().equals(InOrderStateEnum.getInOrderStateEnum("WMS_CONFIRM_FINISH"))||stockInOrder.getState().equals(InOrderStateEnum.getInOrderStateEnum("CANCEL"))) {
				map.put("msg", "该入库单已操作完成，不可再次重复操作！");
			}else{
				Map<String, Object> confirmMap  = stockInOrderConfirm.stockInOrderConfirm(confirm);
				map.put("msg", confirmMap.get("msg"));
			}	
		}
		return map;
	}
	
	
	
	/**
	 * 提交
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="saveStockInOrderConfirm")
	@ResponseBody
	public Map<String, Object>  saveStockInOrderConfirm(HttpServletRequest request,ModelMap model){
		Map<String, Object> map = new HashMap<String, Object>();
		
		Object data  = request.getParameter("data");  //集合数据来源
		Object orderId  = request.getParameter("id"); //入库单 ID
		Object type  = request.getParameter("type");  //提交类型,部分提交和   一次性提交
		
		StockInOrderConfirm  confirm  = new StockInOrderConfirm();
		confirm.generateId();
		JSONArray array = JSONArray.fromObject(data);
		List<StockInOrderItem>  orderList  = new ArrayList<StockInOrderItem>();
		List<StockInCheckItem>  checkList  = new ArrayList<StockInCheckItem>();
		
		for(Object jsonObj : array){
			
			JSONObject object = (JSONObject)jsonObj;
			
			String  num = ""+object.get("num");
			String  num1 = ""+object.get("num1");  // 正品数量
			String  num2 = ""+object.get("num2");  //残次品数量
			
			if(("0".equals(num1)||"".equals(num1) ||  "null".equals(num1)) && ("0".equals(num2)||"".equals(num2) ||  "null".equals(num2))){
				continue;
			}
			String  orderItemId = ""+object.get("orderItemId");
			
			
			StockInOrderItem itemOrderItem  = new StockInOrderItem();
			itemOrderItem.generateId();
			
			List<StockInItemInventory>  inventoryList = new ArrayList<StockInItemInventory>();
			
			Long  numCount  = 0L;
			//正品录入数量
			if(!("0".equals(num1) ||"".equals(num1) || "null".equals(num1))){
				StockInItemInventory itemInventory = new StockInItemInventory();
				itemInventory.generateId();
				itemInventory.setInventoryType(InventoryTypeEnum.getInventoryType("1"));
				itemInventory.setQuantity(Integer.valueOf(num1));
				
				//添加check数量
				numCount  =  numCount  +Integer.valueOf(num1);
				
				itemInventory.setStockInOrderItem(itemOrderItem);
				inventoryList.add(itemInventory);
			}
			
			//残次品录入数量
			if(!("0".equals(num2)||"".equals(num2) ||"null".equals(num2))) {
				StockInItemInventory itemInventory = new StockInItemInventory();
				itemInventory.generateId();
				itemInventory.setInventoryType(InventoryTypeEnum.getInventoryType("101"));
				itemInventory.setQuantity(Integer.valueOf(num2));
				//添加check数量
				numCount  =  numCount  +Integer.valueOf(num2);
				itemInventory.setStockInOrderItem(itemOrderItem);
				inventoryList.add(itemInventory);
			}
			itemOrderItem.setItems(inventoryList);
			orderList.add(itemOrderItem);
			
			String  itemId = ""+object.get("itemId");
			Item item = itemService.getItem(itemId);
			itemOrderItem.setItem(item);
			itemOrderItem.setOrderItemId(orderItemId);
			
			//此处计算入库 商品  个  单位, 目前默认一件商品
			itemOrderItem.setHeight(item.getWmsHeight()!=null?item.getWmsHeight().intValue():0);
			itemOrderItem.setVolume(item.getVolume()!=null?item.getVolume().intValue():0);
			itemOrderItem.setLength(item.getLength()!=null?item.getLength().intValue():0);
			itemOrderItem.setWeight(item.getWmsGrossWeight()!=null?item.getWmsGrossWeight():0);
			itemOrderItem.setWidth(item.getWidth()!=null?item.getWidth().intValue():0);
			
			//设置需要检测商品
			StockInCheckItem checkItem = new StockInCheckItem();
			checkItem.generateId();
			checkItem.setOrderItemId(orderItemId);
			
			//设置checkNum  逻辑
			//如果confirmtype  0, 全部提交,  checkNum 为: 之前所有部分提交之和  + 当前提交数
			//如果confirmtype  1, 部分提交, checkNum为:当前部分提交商品数
			if("1".equals(type)){
				checkItem.setQuantity(numCount);
			}else if("0".equals(type)){
				Map<String,Object>    params =  new HashMap<String, Object>();
				System.out.println("orderItemId:"+orderItemId);
				params.put("orderItemId", orderItemId);
				int checkItemSum = confirmService.getCheckItemSum(params);
				checkItem.setQuantity(numCount+checkItemSum);
			}
			checkItem.setStockInOrderConfirm(confirm);
			checkList.add(checkItem);
			
			itemOrderItem.setStockInOrderConfirm(confirm);
		}
		
		
		if(checkList.size()>0){
			confirm.setCheckItems(checkList);
			confirm.setOrderItems(orderList);
//			
			StockInOrder stockInOrder = stockInOrderService.findStockInOrderById(""+orderId);
			
			confirm.setOrderCode(stockInOrder.getOrderCode());
			confirm.setOrderType(stockInOrder.getOrderType());
			confirm.setOutBizCode(""+new Date().getTime());
			confirm.setOrderConfirmTime(new Date());
			confirm.setTimeZone(stockInOrder.getTimeZone());
			confirm.setConfirmType(Integer.valueOf(""+type));
			//map.put("msg", "<table><tr><td>详细信息:</td><td>残次品 12个</td></tr><tr><td>详细信息:</td><td>正品 11个</td></tr></table>");
			confirm.setStockInOrder(stockInOrder);
			if (stockInOrder.getState().equals(InOrderStateEnum.getInOrderStateEnum("WMS_CONFIRM_FINISH"))||stockInOrder.getState().equals(InOrderStateEnum.getInOrderStateEnum("CANCEL"))) {
				map.put("msg", "该入库单已操作完成，不可再次重复操作！");
			}else{
				Map<String, Object> confirmMap  = stockInOrderConfirm.stockInOrderConfirm(confirm);
				map.put("msg", confirmMap.get("msg"));
			}	
		}else{
			map.put("msg", "没有详细信息!");
		}
		return map;
	}
	
	
	
	@RequestMapping(value="statusUpload")
	@ResponseBody
	public Map<String, Object>  statusUpload(HttpServletRequest request,ModelMap model){
		
		
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("status", status);
		StockInOrder stockInOrder = stockInOrderService.findStockInOrderById(id);
		params.put("account", (Account)SessionUser.get());
		return orderStatusUpload.updateOrderState(stockInOrder, params,null);
	}
	
}
