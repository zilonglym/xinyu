package com.xinyu.controller.order;

import java.util.ArrayList;
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
import com.xinyu.model.base.User;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.order.StockOutOrder;
import com.xinyu.model.order.child.WmsStockOutOrderItem;
import com.xinyu.model.order.enums.OutOrderStateEnum;
import com.xinyu.model.order.enums.OutOrderTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.SystemItem;
import com.xinyu.model.system.enums.StoreSystemItemEnums;
import com.xinyu.service.caoniao.WmsOrderStatusUploadCpImpl;
import com.xinyu.service.order.StockOutOrderConfirmService;
import com.xinyu.service.order.StockOutOrderService;
import com.xinyu.service.order.WmsStockOutOrderItemService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.SystemItemService;
import com.xinyu.service.system.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "outOrderConfirm")
public class StockOutOrderConfirmController extends BaseController{

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private SystemItemService systemItemService;
	
	@Autowired
	private StockOutOrderService stockOutOrderService;
	
	@Autowired
	private StockOutOrderConfirmService stockOutOrderConfirmService;
	
	@Autowired
	private WmsStockOutOrderItemService wmsStockOutOrderItemService;
	
	@Autowired
	private  WmsOrderStatusUploadCpImpl  orderStatusUpload;
	
	
	@RequestMapping(value="confirmDateListByOrderId")
	@ResponseBody
	public  Map<String,Object>  confirmDateList(){
		String orderId = super.getString("id");
		stockOutOrderConfirmService.getConfirmListDataByOrderId(orderId);
		Map<String,Object>  retMap  =  new HashMap<String, Object>();
		retMap.put("rows", stockOutOrderConfirmService.getConfirmListDataByOrderId(orderId));
		return retMap;
	}
	
	/**
	 * 出库单列表
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="stockOutOrderList")
	public String StockOutOrderList(ModelMap model){
		
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		
		OutOrderStateEnum[] status = OutOrderStateEnum.values();
		model.put("status", status);
		
		OutOrderTypeEnum[] types = OutOrderTypeEnum.values();
		model.put("types", types);
		
		return "admin/inventory/stockOutOrderList";
	}
	
	/**
	 * 出库单列表数据
	 * @param request
	 * @param page
	 * @param rows
	 * @return map
	 * */
	@RequestMapping(value = "stockOutOrderList/listData")
	@ResponseBody
	public Map<String, Object> StockOutOrderListData(HttpServletRequest request,
			@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows){
		if (rows==10) {
			rows=100;
		}
		String userId = request.getParameter("userId");
		String status = request.getParameter("status");
		String orderType = request.getParameter("orderType");
		String searchText = request.getParameter("searchText");
		
		Map<String, Object> params = new HashMap<String, Object>();
		for(OutOrderTypeEnum type:OutOrderTypeEnum.values()){
			if (type.getKey().equals(orderType)) {
				params.put("orderType", type);
			}
		}
		params.put("status", status);
		params.put("userId", userId);
		params.put("searchText", searchText);
		
		List<StockOutOrder> stockOutOrders = this.stockOutOrderService.findStockOutOrderByPage(params,page,rows);
		int total = this.stockOutOrderService.getTotal(params);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("page", page);
		retMap.put("rows", this.stockOutOrderService.buildListData(stockOutOrders));
		retMap.put("total", total);
		
		return retMap;
	}
	
	/**
	 * 出库单明细
	 * @param request
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value="f7StockOutOrderItemList")
	public String WmsStockOutOrderItemList(HttpServletRequest request,ModelMap model){
		
		String id = request.getParameter("id");
		StockOutOrder stockOutOrder = this.stockOutOrderService.findStockOutOrderById(id);
		
		List<StockOutOrder> stockOutOrders = new ArrayList<StockOutOrder>();
		stockOutOrders.add(stockOutOrder);
		
		model.put("stockOrder", this.stockOutOrderService.buildListData(stockOutOrders).get(0));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", StoreSystemItemEnums.LOGISTICS.getKey());
		List<SystemItem> expressCompanys = this.systemItemService.findSystemItemByList(params);
		model.put("companys", expressCompanys);
		
		return "admin/inventory/outOrderItemsList";
	}
	
	@RequestMapping(value="stockOutOrderItem/listData")
	@ResponseBody
	public Map<String, Object> WmsStockOutOrderItemListData(HttpServletRequest request,ModelMap model){
		
		String stockOutOrderId = request.getParameter("id");
//		StockOutOrder stockOutOrder = this.stockOutOrderService.findStockOutOrderById(stockOutOrderId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stockOutOrderId", stockOutOrderId);
		List<WmsStockOutOrderItem> orderItems = this.wmsStockOutOrderItemService.findWmsStockOutOrderItemsByList(params);
		
//		List<WmsStockOutOrderItem> orderItems = stockOutOrder.getOrderItemList();
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("rows", this.wmsStockOutOrderItemService.buildDetailListData(orderItems));
		retMap.put("total", orderItems.size());
		return retMap;
	}
	
	@RequestMapping(value="confirmDataListByOrderId")
	@ResponseBody
	public  Map<String,Object>  confirmDataList(){
		String orderId = super.getString("id");
		this.stockOutOrderConfirmService.getConfirmListDataByOrderId(orderId);
		System.out.println("orderId:"+orderId);
		Map<String,Object>  retMap  =  new HashMap<String, Object>();
		retMap.put("rows", stockOutOrderConfirmService.getConfirmListDataByOrderId(orderId));
		return retMap;
	}
	
	/**
	 * 出库单回传状态
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="statusUpload")
	@ResponseBody
	public Map<String, Object> StockOutOrderStatusUpload(HttpServletRequest request){
		
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		StockOutOrder stockOutOrder = this.stockOutOrderService.findStockOutOrderById(id);
		
		
//		Account account = this.getCurrentAccount();
//		params.put("account", account);
		
		return this.orderStatusUpload.updateOrderState(stockOutOrder, params, null);
	}
	
	/**
	 * 出库单回传菜鸟
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="saveStockOutOrderConfirm")
	@ResponseBody
	public Map<String, Object> stockOutOrderConfirmSave(HttpServletRequest request){
		
		List<Map<String, Object>>  dataList  = new ArrayList<Map<String, Object>>();
		Object data  = request.getParameter("data");  //集合数据来源
		JSONArray array = JSONArray.fromObject(data);
		for(Object jsonObj : array){
			
			JSONObject object = (JSONObject)jsonObj;
			String  num1 = ""+object.get("num1");  // 正品数量
			String  num2 = ""+object.get("num2");  //残次品数量
			String  num3 = ""+object.get("num3");  //机损数量
			String  num4 = ""+object.get("num4");  //箱损数量
			String itemId = ""+object.get("itemId");
			String orderItemId = ""+object.get("orderItemId");
			
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("num1", num1);
			map.put("num2", num2);
			map.put("num3", num3);
			map.put("num4", num4);
			map.put("itemId", itemId);
			map.put("orderItemId", orderItemId);
			
			dataList.add(map);
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		Object orderId  = request.getParameter("id"); //入库单 ID
		Object type  = request.getParameter("type");  //提交类型,部分提交和   一次性提交
		Object company = request.getParameter("company");//出库物流公司
		Object orderNo = request.getParameter("orderNo");//出库物流单号
		Object packageLength = request.getParameter("packageLength");//包裹长度
		Object packageWidth = request.getParameter("packageWidth");//包裹宽度
		Object packageHeight = request.getParameter("packageHeight");//包裹高度
		Object remark = request.getParameter("remark");//备注
		
		
		params.put("orderId", orderId);
		params.put("type", type);
		params.put("company", company);
		params.put("orderNo", orderNo);
		params.put("packageLength", packageLength);
		params.put("packageWidth", packageWidth);
		params.put("packageHeight", packageHeight);
		params.put("remark", remark);			
		params.put("dataList", dataList);			
		params.put("account", (Account)SessionUser.get());			
		
		return this.stockOutOrderService.submitStockOutOrder(params);
	}
	
}
