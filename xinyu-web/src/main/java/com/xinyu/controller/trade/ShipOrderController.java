package com.xinyu.controller.trade;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyu.common.BaseController;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.ItemOperator;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.base.enums.ReceiveStateEnums;
import com.xinyu.model.order.enums.OrderTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.SystemItem;
import com.xinyu.model.system.enums.OperatorModel;
import com.xinyu.model.system.enums.OrderStatusEnum;
import com.xinyu.model.system.enums.StoreSystemItemEnums;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderOperator;
import com.xinyu.model.trade.ShipOrderStop;
import com.xinyu.model.trade.TmsOrder;
import com.xinyu.model.trade.TmsOrderItem;
import com.xinyu.model.trade.WmsConsignOrderItem;
import com.xinyu.service.caoniao.WmsConsignOrderConfirmService;
import com.xinyu.service.caoniao.WmsOrderStatusUploadCpImpl;
import com.xinyu.service.system.AuditAreaService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.SystemItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.trade.ReceiverInfoService;
import com.xinyu.service.trade.ShipOrderOperatorService;
import com.xinyu.service.trade.ShipOrderService;
import com.xinyu.service.trade.ShipOrderStopService;
import com.xinyu.service.trade.TmsOrderService;
import com.xinyu.service.trade.WmsConsignOrderItemService;
import com.xinyu.util.CityJson;

import redis.clients.jedis.params.Params;

/**
 * 发货订单处理
 */
@Controller
@RequestMapping(value = "shipOrder")
public class ShipOrderController extends BaseController {

	@Autowired
	private ShipOrderService shipOrderService;
	
	@Autowired
	private ShipOrderStopService orderStopService;

	@Autowired
	private ShipOrderOperatorService shipOrderOperatorService;
	
	@Autowired
	private WmsConsignOrderItemService orderItemService;

	@Autowired
	private ReceiverInfoService receiverInfoService;

	@Autowired
	private UserService userService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private SystemItemService systemItemService;

	@Autowired
	private AuditAreaService auditAreaService;

	@Autowired
	private ShipOrderOperatorService operatorService;

	@Autowired
	private WmsConsignOrderConfirmService orderConfirmService;
	
	@Autowired
	private WmsOrderStatusUploadCpImpl wmsOrderStatusUploadCpImpl;
	@Autowired
	private TmsOrderService tmsOrderService;
	
	public static String sessionKey = "";

	
	
	

	@RequestMapping(value="index")
	public Map<String,Object> index(){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("ret", "OK");
		return resultMap;
	}
	
	
	/**
	 * 订单列表
	 * 
	 * @param model
	 * @return string
	 */
	@RequestMapping(value = "ShipOrderList")
	public String ShipOrderList(ModelMap model) {
		Map<String,Object> params=new HashMap<String, Object>();
		List<User> users = this.userService.getUserBySearch(params);
		model.put("users", users);
		return "admin/shipOrder/shipOrderList";
	}

	/**
	 * 订单列表数据填充
	 * 
	 * @param request
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @return map
	 */
	@RequestMapping(value = "listData")
	@ResponseBody
	public Map<String, Object> ShipOrderListData(HttpServletRequest request, ModelMap model,
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "100") int pageSize) {
		if (pageSize == 10) {
			pageSize = 100;
		}
		String userId = request.getParameter("userId");
		String searchText = request.getParameter("searchText");
		String status = request.getParameter("status");
		String searchType=this.getString("searchType");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("searchType", searchType);
		params.put("userId", userId);
		params.put("searchText", searchText);
		params.put("status", status);
		int start = (pageNum - 1) * pageSize;
 		params.put("pageSize", pageSize);
		params.put("pageNum", start); 
		logger.error("page:"+pageNum+"pageSize:"+pageSize);
		Date date=new Date();
		List<ShipOrder> shipOrders = this.shipOrderService.getShipOrderListByAll(params);
		logger.error("订单列表查询数据:"+(new Date().getTime()-date.getTime()));
		date=new Date();
		int total = this.shipOrderService.getShipOrderListByAllCount(params);
		logger.error("订单列表查询总数:"+(new Date().getTime()-date.getTime()));
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("total", total);
		retMap.put("page", pageNum);
		date=new Date();
		retMap.put("rows", this.shipOrderService.bulidListData(shipOrders));
		logger.error("订单列表构建数据:"+(new Date().getTime()-date.getTime()));
		return retMap;
	}
	
	/**
	 * 历史订单列表
	 * 
	 * @param model
	 * @return string
	 */
	@RequestMapping(value = "oldOrderList")
	public String oldOrderList(ModelMap model) {
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		return "admin/shipOrder/oldOrderList";
	}

	/**
	 * 历史订单列表数据填充
	 * 
	 * @param request
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @return map
	 */
	@RequestMapping(value = "oldOrder/listData")
	@ResponseBody
	public Map<String, Object> oldOrderListData(HttpServletRequest request, ModelMap model,
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "100") int pageSize) {
		if (pageSize == 10) {
			pageSize = 100;
		}
		String userId = request.getParameter("userId");
		String searchText = request.getParameter("searchText");
		String status = request.getParameter("status");
		
		Map<String, Object> params = new HashMap<String, Object>();
		// params.put("type", StoreSystemItemEnums.SESSIONKEY.getKey());
		// List<SystemItem>
		// systemItemList=this.systemItemService.findSystemItemByList(params);
		// if(systemItemList==null || systemItemList.size()==0){
		// //跳转到登陆淘宝授权帐号，回转页面URL就是此方法URL
		// return null;
		// }
		// SystemItem item=systemItemList.get(0);
		// sessionKey=item.getValue();
		// params.clear();
		params.put("userId", userId);
		params.put("searchText", searchText);
		params.put("status", status);
		logger.error("page:"+pageNum+"pageSize:"+pageSize);
		Date date=new Date();
		List<ShipOrder> shipOrders = this.shipOrderService.findOldShipOrderListByPage(params, pageNum, pageSize);
		logger.error("time1:"+(new Date().getTime()-date.getTime()));
		date=new Date();
		int total = this.shipOrderService.getOldTotal(params);
		logger.error("time2:"+(new Date().getTime()-date.getTime()));
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("total", total);
		retMap.put("page", pageNum);
		date=new Date();
		retMap.put("rows", this.shipOrderService.bulidOldListData(shipOrders));
		logger.error("time3:"+(new Date().getTime()-date.getTime()));
		return retMap;
	}


	/**
	 * 修改订单快递页面
	 * 
	 * @param model
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "tof7Express")
	public String editExpress(ModelMap model, HttpServletRequest request) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", StoreSystemItemEnums.WAYBILL.getKey());
		List<SystemItem> expressNames = this.systemItemService.findSystemItemByList(params);
		model.put("expressNames", expressNames);
		
		return "admin/shipOrder/editExpress";
	}

	/**
	 * 修改订单快递
	 * 
	 * @param request
	 * @return map
	 */
	@RequestMapping(value = "updateExpress")
	@ResponseBody
	public Map<String, Object> EditShipOrderExpress(HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String expressName = request.getParameter("expressName");
		String[] ids = request.getParameterValues("ids[]");
		
		for (String id : ids) {
			
			ShipOrder shipOrder = this.shipOrderService.findShipOrderById(id);
			
			if (shipOrder == null || shipOrder.getTmsServiceCode() == null) {
				resultMap.put("ret", 0);
				resultMap.put("msg", "该订单没有设置快递，请先审核!");
			} else if (shipOrder != null && shipOrder.getTmsOrderCode() != null
					&& shipOrder.getTmsOrderCode().length() > 0) {
				resultMap.put("ret", 0);
				resultMap.put("msg", "该订单已打印，要修改快递请先取消！");
			} else {
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("type", StoreSystemItemEnums.WAYBILL.getKey());
				params.put("value", expressName);
				List<SystemItem> systemItems = this.systemItemService.findSystemItemByList(params);
				
				Account account = this.getCurrentAccount();
				this.shipOrderService.chooseExpress(shipOrder.getId(), systemItems.get(0).getId(), account);
				
				resultMap.put("ret", 1);
				resultMap.put("msg", "订单审核成功！！");
			}
		}
		return resultMap;
	}

	/**
	 * 订单反审
	 * 
	 * @param request
	 * @return map
	 */
	@RequestMapping(value = "ajax/resetAudit")
	@ResponseBody
	public Map<String, Object> resetShipOrder(HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String[] ary = request.getParameterValues("ids[]");
		for (int i = 0; i < ary.length; i++) {
			String id = ary[i];
			ShipOrder shipOrder = this.shipOrderService.findShipOrderById(id);
			// 订单打印反审到订单审核
			this.shipOrderService.resetShipOrder(shipOrder, this.getCurrentAccount());
			resultMap.put("ret", "success");
			resultMap.put("msg", "该订单成功反审!");
			
		}
		return resultMap;
	}

	/**
	 * 单个删除订单
	 * 
	 * @param request
	 * @return map
	 */
	@RequestMapping(value = "deleteShipOrder")
	@ResponseBody
	public Map<String, Object> deleteShipOrder(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = request.getParameter("id");
		try {
			this.shipOrderService.deleteShipOrder(id, this.getCurrentAccount());
			resultMap.put("ret", 1);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("ret", 0);
		}
		return resultMap;
	}

	/**
	 * 批量删除订单
	 * 
	 * @param request
	 * @return map
	 */
	@RequestMapping(value = "batchDeleteShipOrder")
	@ResponseBody
	public Map<String, Object> BatchDeleteShipOrder(HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String ids = request.getParameter("ids");
		String[] ary = ids.split(",");
		
		try {
			for (int i = 0; i < ary.length; i++) {
				this.shipOrderService.deleteShipOrder(ary[i], this.getCurrentAccount());
			}
			resultMap.put("ret", 1);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("ret", 0);
		}
		return resultMap;
	}

	/**
	 * 订单退货
	 * 
	 * @param request
	 * @return map
	 */
	@RequestMapping(value = "addReturn")
	@ResponseBody
	public Map<String, Object> addReturn(HttpServletRequest request) {
		String id = request.getParameter("id");
		ShipOrder shipOrder = this.shipOrderService.findShipOrderById(id);
		Map<String, Object> resultMap = this.shipOrderService.addReturn(shipOrder, this.getCurrentAccount());
		return resultMap;
	}

	/**
	 * 订单作废 订单不做删除操作，打上删除标记。如果是奇门的单据则需要对此单的商品库存做已占用数量的恢复操作
	 * 
	 * @return
	 */
	@RequestMapping(value = "invalidShipOrder")
	@ResponseBody
	public Map<String, Object> invalidShipOrder() {		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = this.getString("id");
		try {
			
			this.shipOrderService.invalidShipOrder(id, this.getCurrentAccount());		
			resultMap.put("ret", 1);
			
		} catch (Exception e) {
			
			e.printStackTrace();	
			resultMap.put("ret", 0);		
			resultMap.put("msg", e.getMessage());
			
		}
		return resultMap;
	}

	/**
	 * 订单remark修改页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "tof7Remark")
	public String EditShipOrderRemark(HttpServletRequest request, ModelMap model) {
		
		String id = request.getParameter("id");
		ShipOrder shipOrder = this.shipOrderService.findShipOrderById(id);
		model.put("shipOrder", shipOrder);
		
		return "admin/shipOrder/editRemark";
	}

	/**
	 * 订单修改仓库备注
	 * 
	 * @param request
	 * @return map
	 */
	@RequestMapping(value = "updateRemark")
	@ResponseBody
	public Map<String, Object> updateShipOrderRemark(HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String id = request.getParameter("id");
		String remark = request.getParameter("remark");
		ShipOrder shipOrder = this.shipOrderService.findShipOrderById(id);
		shipOrder.setRemark(remark);	
		this.shipOrderService.updateShipOrder(shipOrder);
		
		resultMap.put("ret", "success");
		
		return resultMap;
	}

	/**
	 * 订单批量审核列表
	 * 
	 * @param model
	 * @return string
	 */
	@RequestMapping(value = "waits/search")
	public String ShipOrderWaitsList(ModelMap model) {
		
		String userId=this.getString("userId");
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		
		CityJson cj = new CityJson();
		model.put("stateList", ReceiveStateEnums.values());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", StoreSystemItemEnums.WAYBILL.getKey());
		List<SystemItem> systemItems = this.systemItemService.findSystemItemByList(params);
		model.put("companys", systemItems);
		model.put("userId", userId);
		return "admin/shipOrder/shipOrderWaitsList";
	}

	/**
	 * 订单批量审核列表数据
	 * 
	 * @param page
	 * @param rows
	 * @param page
	 * @return map
	 */
	@RequestMapping(value = "waits/listData")
	@ResponseBody
	public Map<String, Object> waitAuditsBatch(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "500") int rows, HttpServletRequest request, ModelMap model)
					throws Exception {
		
		if (rows == 10) {
			rows = 500;
		}
		Date date=new Date();
		List<ShipOrder> shipOrders = null;
		String userId = request.getParameter("userId");
		String q = request.getParameter("q");
		String others = request.getParameter("others");
		String state = request.getParameter("state");
		String startDate = request.getParameter("beigainTime");
		String endDate = request.getParameter("lastTime");
		String company = request.getParameter("company");
		String weight_x = request.getParameter("weight_x");
		String weight = request.getParameter("weight");
		String searchType=this.getString("searchType");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("userId", userId);
		/**
		 * 订单审核列表，取单据到达系统的状态
		 */
		params.put("status", OrderStatusEnum.WMS_ACCEPT.getKey());
		
		if (StringUtils.isNotEmpty(startDate) && !startDate.equals("null")) {
			params.put("startTime", startDate);
		}
		
		if (StringUtils.isNotEmpty(endDate) && !endDate.equals("null")) {
			params.put("endTime", endDate);
		}
		
		if (StringUtils.isNotEmpty(state) && !state.equals("0")) {
			params.put("receiverState", state);
		}
		if (StringUtils.isNotBlank(q)) {
			params.put("searchText", q);
		}
		if(StringUtils.isNotEmpty(searchType)){
			params.put("searchType", searchType);
		}
		if (others != null && !others.equals("0")) {
			params.put("others", others);
		}
		
		if (weight != null && !weight.equals("0") && !weight.equals("")) {
			
			params.put("weight", weight);
			
			if (weight_x.equals("=")) {
				params.put("weight_x", 1);
			}
			
			else if (weight_x.equals(">=")) {
				params.put("weight_x", 2);
			} 
			
			else if (weight_x.equals("<=")) {
				params.put("weight_x", 3);
			}

		}
		int start = (page - 1) * rows;
 		params.put("pageSize", rows);
		params.put("pageNum", start); 
		
		if (company != null) {
			params.put("tmsServiceCode", company);
		}
	

		shipOrders = this.shipOrderService.getShipOrderListByAll(params);
		
		logger.error("查询表表时长:"+(new Date().getTime()-date.getTime()));
		date=new Date();
		int total = this.shipOrderService.getShipOrderListByAllCount(params);
		logger.error("统计总数时长:"+(new Date().getTime()-date.getTime()));
		date=new Date();
		resultMap.put("rows", this.shipOrderService.bulidListData(shipOrders));
		logger.error("构建列表时长:"+(new Date().getTime()-date.getTime()));
		resultMap.put("total", total);
		resultMap.put("userId", userId);
		resultMap.put("q", q);
		
		return resultMap;
	}

	/**
	 * 发货单列表,这里只显示奇门同步过来的单据
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "deliveryorderList")
	public String deliveryorderList(ModelMap model,HttpServletRequest request) {
	
		String status = request.getParameter("status");
		model.addAttribute("status", status);
		String userId = request.getParameter("userId");
		model.addAttribute("userId", userId);
		String company = request.getParameter("selectCompany");
		model.addAttribute("company", company);
		String q = request.getParameter("q");
		model.addAttribute("q", q);
		String beigainTime = request.getParameter("beigainTime");
		model.addAttribute("beigainTime", beigainTime);
		String lastTime = request.getParameter("lastTime");
		model.addAttribute("lastTime", lastTime);
		String txtno = request.getParameter("txtno");
		model.addAttribute("txtno", txtno);
		String searchType=this.getString("searchType");
		model.addAttribute("searchType", searchType);
		
		if (status == null || status.length() <= 0) {
			status = OrderStatusEnum.WMS_PRINT.getKey();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		params.put("userId", userId);
		params.put("tmsServiceCode", company);
		params.put("searchText", q);
		params.put("searchType", searchType);
		params.put("startDate", beigainTime);
		params.put("endDate", lastTime);
		params.put("txtno", txtno);
		System.err.println(params);
		
		int count = this.shipOrderService.getShipOrderListByAllCount(params);
		model.addAttribute("count",count);
		
		// 默认查询所有的未完成发货单
		List<User> users = userService.getUserBySearch(null);
		model.addAttribute("users", users);
		
//		Map<String, Object> params = new HashMap<String, Object>();
		
		params.clear();
		params.put("type", StoreSystemItemEnums.WAYBILL.getKey());
		List<SystemItem> companys = systemItemService.findSystemItemByList(params);
		model.addAttribute("companys", companys);
		
		return "admin/shipOrder/deliveryorderList";
	}

	/**
	 * 发货单列表数据填充
	 * 
	 * @param row
	 * @param page
	 * @param request
	 * @return map
	 */
	@RequestMapping(value = "deliveryorderList/listData")
	@ResponseBody
	public Map<String, Object> deliveryorderListData(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "100") int rows, HttpServletRequest request) {
		
		if (rows == 10) {
			rows = 500;
		}
		
		String status = request.getParameter("status");
		String userId = request.getParameter("selectUser");
		String company = request.getParameter("selectCompany");
		String q = request.getParameter("q");
		String beigainTime = request.getParameter("beigainTime");
		String lastTime = request.getParameter("lastTime");
		String txtno = request.getParameter("txtno");
		String searchType=this.getString("searchType");
		
		if (status == null || status.length() <= 0) {
			status = OrderStatusEnum.WMS_PRINT.getKey();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		params.put("userId", userId);
		params.put("tmsServiceCode", company);
		params.put("searchText", q);
		params.put("searchType", searchType);
		params.put("startDate", beigainTime);
		params.put("endDate", lastTime);
		params.put("txtno", txtno);
		logger.debug("deliveryorderListData" + params);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<ShipOrder> orderList = this.shipOrderService.findShipOrderListByPage(params, page, rows);
		int total = this.shipOrderService.getTotal(params);
//		System.err.println("params:" + params);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		for (ShipOrder shipOrder : orderList) {
//			ShipOrder shipOrder=order.getOrder();
			Map<String, Object> map = new HashMap<String, Object>();
			
			User user = userService.getUserById(shipOrder.getUser().getId());
			
			ReceiverInfo receiverInfo = this.receiverInfoService
					.getReceiverInfoById(shipOrder.getReceiverInfo().getId());
//			ReceiverInfo receiverInfo = shipOrder.getReceiverInfo();
			shipOrder.setUser(user);
			
			map.put("id", shipOrder.getId());
			
			if (user.getSubscriberNick() != null && user.getSubscriberNick() != "") {
				
				map.put("originPersion", user.getSubscriberNick());
				
			} else {
				
				map.put("originPersion", user.getSubscriberName());
				
			}
			
			/**
			 * orderId对应tmsOrder的数量
			 */
			int num = this.tmsOrderService.getTotalByOrderId(shipOrder.getId());
			map.put("num", num-1);
			
			map.put("orderno", shipOrder.getOrderCode());
			
			map.put("orderType", OrderTypeEnum.getOrderTypeEnum(""+shipOrder.getOrderType()).getDescription());
			
			map.put("buyerNick", receiverInfo.getReceiverNick());
			
			map.put("receiverName", receiverInfo.getReceiverName());
			
			map.put("expressCompany", shipOrder.getTmsServiceCode());
			
			map.put("expressOrderno", shipOrder.getTmsOrderCode());
			
			map.put("lastDate", format.format(shipOrder.getLastUpdateTime()));
			
			map.put("status", shipOrder.getStatus());
			
			String items = shipOrder.getItems();
			if (StringUtils.isEmpty(items)) {
				map.put("items", this.shipOrderService.bulidItemsData(shipOrder));
			}else {
				map.put("items", items);
			}
			
			
			resultList.add(map);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("page", page);
		resultMap.put("total", total);
		resultMap.put("rows", resultList);
	
		return resultMap;
	}

	/**
	 * 订单批量发货确认（菜鸟）
	 * 
	 * @param ids
	 * @return map
	 * @throws Exception 
	 */
	@RequestMapping(value = "deliveryorder/submitAll")
	@ResponseBody
	public Map<String, Object> wmsDeliveryorderConfirm(HttpServletRequest request) throws Exception {
		
		String ids = request.getParameter("ids");
		String[] idAry = ids.split(",");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (int i = 0; i < idAry.length; i++) {
			resultMap = this.orderConfirmService.submitConsignOrderConfirm(idAry[i],this.getCurrentAccount());
		}
		
		return resultMap;
	}

	
	/**
	 * 转向批次发货页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "deliveryOrder/toBatchSubmit")
	public String toBatchSubmitDeliveryOrder(ModelMap model) {
		
		String id = this.getString("id");
		ShipOrder order = this.shipOrderService.findShipOrderById(id);
		model.put("order", order);
		// 5184674a-82e5-4ab8-8e43-c534ae22cd8d
		return "admin/shipOrder/batchDeliveryOrder";
	}

	/**
	 * 批次发货确认
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "deliveryOrder/submitBatch")
	@ResponseBody
	public Map<String, Object> submitBatch() throws Exception {
		
		String data = this.getString("data");
		String id = this.getString("id");
		
		net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(data);
		
		for (int i = 0; array != null && i < array.size(); i++) {
			net.sf.json.JSONObject json = array.getJSONObject(i);
			String orderItemId = json.getString("id");
			WmsConsignOrderItem item = this.orderItemService.getWmsConsignOrderItemById(orderItemId);
			Long quantity = json.getLong("quantity");
			item.setItemOutQuantity(item.getItemOutQuantity() + quantity);
			item.setItemBatchQuantity(quantity);
			this.orderItemService.updateWmsConsignOrderItem(item);
		}
		
		ShipOrder shipOrder = this.shipOrderService.findShipOrderById(id);
		// this.wmsOrderStatusUploadCpImpl.updateOrderState(shipOrder,
		// null,"201");

		this.orderConfirmService.submitConsignOrderConfirm(id,this.getCurrentAccount());
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("ret", 1);
		
		return resultMap;
	}

	/**
	 * 订单状态上传
	 * 
	 * @return
	 */
	@RequestMapping(value = "order/statusUpload")
	@ResponseBody
	public Map<String, Object> shipOrderStatusUpload(HttpServletRequest request) {
		
		String[] ary =request.getParameterValues("ids[]");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
//		String[] ary = ids.split(",");
		
		try {
			
			for (int i = 0; ary != null && i < ary.length; i++) {
				
//				Map<String, Object> params = new HashMap<String, Object>();
//				params.put("account", this.getCurrentAccount());
				
				ShipOrder shipOrder = this.shipOrderService.findShipOrderById(ary[i]);
				shipOrder.setStatus(OrderStatusEnum.WMS_ACCEPT);
				this.wmsOrderStatusUploadCpImpl.updateOrderState(shipOrder, null, String.valueOf(shipOrder.getOrderType()));
			}
			
			resultMap.put("ret", 1);
			
		} catch (Exception e) {
			
			resultMap.put("ret", 0);
			resultMap.put("msg", e.getMessage());
			e.printStackTrace();
			
		}
		
		return resultMap;
	}

	@RequestMapping(value = "deliveryOrder/itemsData")
	@ResponseBody
	public Map<String, Object> getShipOrderItemsData() {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String id = this.getString("id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", id);
		List<WmsConsignOrderItem> itemList = this.orderItemService.getWmsConsignOrderItemByList(params);
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (int i = 0; itemList != null && i < itemList.size(); i++) {
			WmsConsignOrderItem orderItem = itemList.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", orderItem.getId());
			String itemId = orderItem.getItem().getId();
			Item item = this.itemService.getItem(itemId);
			map.put("itemName", item.getName());
			map.put("itemCode", item.getItemCode());
			map.put("itemQuantity", orderItem.getItemQuantity());
			map.put("itemOutQuantity", orderItem.getItemOutQuantity());
			map.put("noQuantity", orderItem.getItemQuantity() - (orderItem.getItemOutQuantity()==null?0:orderItem.getItemOutQuantity()));
			map.put("quantity", "0");
			mapList.add(map);
		}
		
		resultMap.put("rows", mapList);
		
		return resultMap;
	}

	/**
	 * 标记完成
	 * 
	 * @return
	 */
	@RequestMapping(value = "submitOk")
	@ResponseBody
	public Map<String, Object> submitOk(HttpServletRequest request) {
		
		String id = request.getParameter("id");
		ShipOrder order = this.shipOrderService.findShipOrderById(id);
		String oldValue = order.getStatus().getKey();
		order.setStatus(OrderStatusEnum.WMS_FINASH);
		
		/**
		 * 标记完成更新相关tmsOrder的状态
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", order.getId());
		List<TmsOrder> tmsOrders = this.tmsOrderService.getTmsOrderByList(params);
		for(TmsOrder tmsOrder:tmsOrders){
			tmsOrder.setOrderStatus(OrderStatusEnum.WMS_FINASH);
			this.tmsOrderService.updateTmsOrder(tmsOrder);
		}
		
		this.shipOrderService.updateShipOrder(order);
		
		// 生成日志
		ShipOrderOperator record = new ShipOrderOperator();
		record.generateId();
		record.setOldValue(oldValue);
		record.setNewValue(OrderStatusEnum.WMS_FINASH.getKey());
//		record.setCu("");
		record.setDescription(order.getOrderCode()+"订单发货标记完成!"+order.getTmsOrderCode());
		record.setOperatorDate(new Date());
		record.setOperatorModel(OperatorModel.TRADE_CONFIRM);
		record.setShipOrder(order);
		record.setAccount(this.getCurrentAccount());
		this.operatorService.saveShipOrderOperator(record);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("ret", 1);
	
		return resultMap;
	}

	/**
	 * 批量审核订单
	 * 
	 * @param ids
	 * @param expressCompany
	 * @return map
	 */
	@RequestMapping(value = "mkships")
	@ResponseBody
	public Map<String, Object> batchMkship(HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String expressCompany = request.getParameter("expressName");
		String[] ids = request.getParameterValues("ids[]");
		logger.debug("批量审单开始[tradeId:" + ids + "]");
		
		try {
			for (String id : ids) {
				TmsOrder tmsOrder=this.tmsOrderService.getTmsOrderById(id);
				ShipOrder shipOrder = this.shipOrderService.findShipOrderById(tmsOrder.getOrder().getId());
				// 如果联系电话为空，则不审通过
				ReceiverInfo receiverInfo = this.receiverInfoService
						.getReceiverInfoById(shipOrder.getReceiverInfo().getId());
				
				if (shipOrder != null && receiverInfo.getReceiverMobile() != null && tmsOrder.getOrderStatus().equals(OrderStatusEnum.WMS_ACCEPT)) {
					/**
					 * 接单状态时才允许审单
					 */
					this.shipOrderService.auditOneTrade(id, expressCompany, this.getCurrentAccount());
					
					resultMap.put("ret", 1);
					resultMap.put("msg", "订单审核完成！");
					
				} else {
					
					resultMap.put("ret", 0);
					resultMap.put("msg", "收件人手机号码为空！！");
					
				}
			}
		} catch (Exception e) {
			resultMap.put("ret", 0);
			e.printStackTrace();
		}
		logger.debug("批量审单结束");
		return resultMap;
	}

	/**
	 * 顺丰自动审单
	 * 
	 * @param request
	 * @param model
	 * @return string
	 */
	@RequestMapping(value = "toAuditArea")
	public String toAuditArea(HttpServletRequest request, ModelMap model) {
		String ids = request.getParameter("ids");
		model.put("ids", ids.substring(0, ids.length() - 1));
		System.err.println(model);
		return "admin/shipOrder/auditAreaWaits";
	}

	@RequestMapping(value = "ajax/auditArea")
	@ResponseBody
	public Map<String, Object> auditArea(@RequestParam(value = "ids", defaultValue = "0") String[] ids)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("array", ids);
//		params.put("status", "WMS_ACCEPT");
		try {
			List<TmsOrder> tmsOrders = this.tmsOrderService.getTmsOrderByList(params);
			resultMap.put("rows", buildSfAuditTradeList(tmsOrders));
			resultMap.put("total", tmsOrders.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 构建自动审核 订单结构
	 * 
	 * @param trades
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private List<Map<String, Object>> buildSfAuditTradeList(List<TmsOrder> tmsOrders)
			throws UnsupportedEncodingException {
		logger.debug("需要审核订单："+tmsOrders.size());
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for (int i = 0; tmsOrders != null && i < tmsOrders.size(); i++) {
			
			TmsOrder tmsOrder = tmsOrders.get(i);
			ShipOrder shipOrder=this.shipOrderService.findShipOrderById(tmsOrder.getOrder().getId());
			
			
			Map<String, Object> checkArea = this.auditAreaService.checkSFArea(tmsOrder.getId());// 顺风地址检查
			
			Map<String, Object> map = new HashMap<String, Object>();
			String msg = "" + checkArea.get("msg");
			map.put("msg", msg);
			
			if (msg.indexOf("未开通") >= 0) {
				map.put("status", "0");
			}
			// System.out.println("msg:"+checkArea.get("msg"));
			if (Boolean.valueOf("" + checkArea.get("flag")) == true) {
				try {
					this.shipOrderService.auditOneTrade(tmsOrder.getId(), "SF", this.getCurrentAccount());
					map.put("audit", "成功");
					map.put("status", "1");
				} catch (Exception e) {
					map.put("audit", "联系管理员");
					map.put("status", "2");
					e.printStackTrace();
				}
			} else {
				map.put("audit", "");
			}
			
			map.put("val", tmsOrder.getId());
			map.put("id", tmsOrder.getId());
			User user = this.userService.getUserById(shipOrder.getUser().getId());
			map.put("shopName", user.getSubscriberName());
			map.put("createDate", sdf.format(shipOrder.getOrderCreateTime()));
			map.put("erpOrderCode", shipOrder.getErpOrderCode());
			
//			ReceiverInfo receiverInfo = this.receiverInfoService.getReceiverInfoById(shipOrder.getReceiverInfo().getId());
			ReceiverInfo receiverInfo = this.receiverInfoService.getReceiverInfoById(shipOrder.getReceiverInfo().getId());
			String phone = ((receiverInfo.getReceiverMobile() == null) ? ""
					: receiverInfo.getReceiverMobile())
					+ ((receiverInfo.getReceiverPhone() == null) ? ""
							: "," + receiverInfo.getReceiverPhone());
			map.put("phone", phone);
			
			
			String address = receiverInfo.getReceiverProvince() + receiverInfo.getReceiverCity()
					+ receiverInfo.getReceiverArea() + receiverInfo.getReceiveTown()
					+ receiverInfo.getReceiverAddress();
			map.put("address", address);
			
			map.put("items", tmsOrder.getItems());
			
			map.put("buyerNick", (receiverInfo.getReceiverNick() == null ? receiverInfo.getReceiverName()
					: receiverInfo.getReceiverNick()));
			
			map.put("weight", this.shipOrderService.buildWeight(shipOrder));
			
			resultList.add(map);
		}
		logger.debug("已自动审核订单："+resultList.size());
		return resultList;
	}

	/**
	 * 快速拆单
	 * 
	 * @param tradeId
	 * @return
	 */
	@RequestMapping(value = "ajax/batchSplit")
	public String ShipOrderBatchSplit() {
		return "admin/shipOrder/batchSplit";
	}

	/**
	 * 快速拆单
	 * 
	 * @param tradeId
	 * @return
	 */
	@RequestMapping(value = "ajax/batchSplitData")
	@ResponseBody
	public Map<String, Object> batchSplit(@RequestParam("ids") String ids,
			@RequestParam(value = "type", defaultValue = "line") String type,
			@RequestParam(value = "num", defaultValue = "1") Long num) {
		
		logger.debug("batchSplit:" + ids);
		
		String[] orderIds = ids.split(",");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			for (int i = 0, size = orderIds.length; i < size; i++) {
				if (orderIds[i] != null) {
					this.shipOrderService.splitShipOrderOperation(type, orderIds[i], num);
				}
			}
			resultMap.put("ret", 1);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("ret", 0);
		}
		
		logger.debug("batchSplit" + resultMap);
		
		return resultMap;
	}

	/**
	 * 订单自定义拆单页面
	 * 
	 * @param request
	 * @param nodel
	 * @return string
	 */
	@RequestMapping(value = "toSplitOrder")
	public String toSplitOrder(HttpServletRequest request, ModelMap model) {
		return "admin/shipOrder/splitOrder";
	}

	/**
	 * 拆单列表页面数据填充
	 * 
	 * @param request
	 * @param model
	 * @param orderId
	 * @return map
	 */
	@RequestMapping(value = "ajax/shipOrderdata")
	@ResponseBody
	public Map<String, Object> shipOrderdataList(HttpServletRequest request, ModelMap model) {
		
		String orderId = request.getParameter("tmsId");
		TmsOrder tmsOrder=this.tmsOrderService.getTmsOrderById(orderId);
//		ShipOrder shipOrder = this.shipOrderService.findShipOrderById(orderId);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tmsOrderId", tmsOrder.getId());
//		List<WmsConsignOrderItem> orderItems = this.orderItemService.getWmsConsignOrderItemByList(params);
		List<TmsOrderItem> orderItems=this.tmsOrderService.getTmsOrderItemByList(params);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// Map<String,Object> params=new HashMap<String,Object>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		// List<TradeOrder> fetchTradeOrders =
		// tradeRemote.fetchTradeOrders(tradeId);
		for (TmsOrderItem orderItem : orderItems) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", orderItem.getId());
			Item item = this.itemService.getItem(orderItem.getItem().getId());
			map.put("title", item.getName());
			map.put("num", orderItem.getItemQuantity());
			resultList.add(map);
		}
		resultMap.put("rows", resultList);
		resultMap.put("total", resultList.size());
		resultMap.put("status", tmsOrder.getStatus());
		return resultMap;
	}

	/**
	 * 分单保存 数量拆单
	 * 
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "splitShipOrder")
	@ResponseBody
	public Map<String, Object> splitShipOrder(HttpServletRequest request, ModelMap model) {
		
		String jsonStr = request.getParameter("json");
		JSONObject json = new JSONObject(jsonStr.substring(1, jsonStr.length() - 1));
		JSONArray date = json.getJSONArray("date");
		String tmsId=json.optString("id");
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("tmsOrderId", tmsId);
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			for (int i = 0, size = date.length(); i < size; i++) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject obj = date.getJSONObject(i);
				String detailId = String.valueOf("" + obj.get("detailId"));
				map.put("detailId", detailId);
				
				Long quantity = null;
				
				try {
					quantity = Long.valueOf("" + obj.get("quantity"));
				} catch (Exception e) {
					quantity = Long.valueOf("0");
				}
				
				if (quantity != 0) {
					map.put("quantity", quantity);
					list.add(map);
					param.put("" + detailId, quantity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			retMap.put("code", "200");
			retMap.put("msg", "数量填写异常");
			return retMap;
		}

		param.put("detailList", list);

		if (list.size() > 0) {
			param.put("account", this.getCurrentAccount());
			retMap = this.shipOrderService.splitShipOrder(param);
		} else {
			retMap.put("code", "404");
			retMap.put("msg", "无法识别有效返单数量");
		}
		
		System.err.println("拆单结果:" + retMap.get("code") + "|" + retMap.get("msg"));
		System.out.println("拆单结果:" + retMap.get("code") + "|" + retMap.get("msg"));
		
		return retMap;
	}

	/**
	 * 分单保存 行数拆单
	 * 
	 * @param request
	 * @param model
	 * @return map
	 */
	@RequestMapping(value = "splitShipOrderLine")
	@ResponseBody
	public Map<String, Object> splitShipOrderLine(HttpServletRequest request, ModelMap model) {
		
		String jsonStr = request.getParameter("json");
		JSONObject json = new JSONObject(jsonStr.substring(1, jsonStr.length() - 1));
		JSONArray date = json.getJSONArray("date");
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("tmsOrderId", json.get("id"));
		
		List<Map<String, Long>> list = new ArrayList<Map<String, Long>>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		for (int i = 0, size = date.length(); i < size; i++) {
			Map<String, Long> map = new HashMap<String, Long>();
			JSONObject obj = date.getJSONObject(i);
			String detailId = String.valueOf(obj.get("detailId"));
			param.put("" + detailId, detailId);
			list.add(map);
		}

		if (list.size() > 0) {
			param.put("type", "line");
			param.put("account", this.getCurrentAccount());
			retMap = this.shipOrderService.splitShipOrder(param);
		} else {
			retMap.put("code", "404");
			retMap.put("msg", "无法识别有效返单数量");
		}
		
		System.err.println("拆单结果:" + retMap.get("code") + "|" + retMap.get("msg"));
		System.out.println("拆单结果:" + retMap.get("code") + "|" + retMap.get("msg"));
		
		return retMap;
	}

	/**
	 * 分单
	 * 
	 * @param request
	 * @param model
	 * @return string
	 */
	@RequestMapping(value = "f7Split")
	public String f7Split(HttpServletRequest request, ModelMap model) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String id = request.getParameter("id");
		ShipOrder shipOrder = this.shipOrderService.findShipOrderById(id);
		// Person person=this.personRemote.findPersonByQ(params);
		model.put("shipOrder", shipOrder);
		
		params.put("orderId", id);
		List<WmsConsignOrderItem> orderItems = this.orderItemService.getWmsConsignOrderItemByList(params);
		
		List<Object> list = new ArrayList<Object>();
		System.err.println(orderItems.size());
		
		for (int i = 0, size = orderItems.size(); i < size; i++) {
			WmsConsignOrderItem orderItem = orderItems.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", orderItem.getId());
			map.put("index", i);
			map.put("num", orderItem.getItemQuantity());
			map.put("item", orderItem.getItem());
			list.add(map);
		}
		model.put("details", list);
		
		// List<Centro> centroList=this.centroRemote.findCentros();
		// model.put("centroList",centroList);
		return "admin/shipOrder/splitOrder";
	}
	
	/**
	 * 修改收件信息页面
	 * @param request
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="toEditReceiverInfo")
	public String ShipOrderReceiverInfoEdit(HttpServletRequest request,ModelMap model){
		
		String id = request.getParameter("id");
		ShipOrder shipOrder = this.shipOrderService.findShipOrderById(id);
		ReceiverInfo receiverInfo = this.receiverInfoService.getReceiverInfoById(shipOrder.getReceiverInfo().getId());
		model.put("receiverInfo", this.receiverInfoService.buildData(receiverInfo));
		
		return "admin/shipOrder/receiverInfoEdit";
	}
	
	/**
	 * 修改收件信息
	 * @param request
	 * @param model
	 * @return map
	 * */
	@RequestMapping(value="saveReceiverInfo")
	@ResponseBody
	public Map<String, Object> ShipOrderReceiverInfoSave(HttpServletRequest request,ModelMap model){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String provice = request.getParameter("province");
		String city = request.getParameter("city");
		String area = request.getParameter("area");
		String town = request.getParameter("town");
		String address = request.getParameter("address");
		String name = request.getParameter("name");
		String nick = request.getParameter("nick");
		String phone = request.getParameter("phone");
		String mobile = request.getParameter("mobile");
		String id = request.getParameter("id");
	
		ReceiverInfo receiverInfo = this.receiverInfoService.getReceiverInfoById(id);
		String oldAddress = receiverInfo.getReceiverName()+","+receiverInfo.getReceiverNick()+","+receiverInfo.getReceiverPhone()+","+receiverInfo.getReceiverMobile()
		+","+receiverInfo.getReceiverProvince()+receiverInfo.getReceiverCity()+receiverInfo.getReceiverArea()+receiverInfo.getReceiverAddress();;
		receiverInfo.setReceiverAddress(address);
		receiverInfo.setReceiverArea(area);
		receiverInfo.setReceiverCity(city);
		receiverInfo.setReceiverMobile(mobile);
		receiverInfo.setReceiverName(name);
		receiverInfo.setReceiverNick(nick);
		receiverInfo.setReceiverPhone(phone);
		receiverInfo.setReceiverProvince(provice);
		receiverInfo.setReceiveTown(town);
		
		try {
			
			this.receiverInfoService.updateReceiverInfo(receiverInfo);
			retMap.put("msg", "修改成功！");
			
			ShipOrderOperator operator = new ShipOrderOperator();
			operator.generateId();
			operator.setAccount(this.getCurrentAccount());
			String newAddress = name+","+nick+","+phone+","+mobile+","+provice+city+area+address;
			operator.setNewValue(newAddress);
			operator.setOldValue(oldAddress);
			operator.setOperatorDate(new Date());
			operator.setOperatorModel(OperatorModel.TRADE_AUDIT);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("receiverInfoId", receiverInfo.getId());
			ShipOrder shipOrder = this.shipOrderService.getShipOrderByParams(params);
			operator.setShipOrder(shipOrder);
			operator.setDescription(shipOrder.getOrderCode()+"修改收件地址信息！");
			this.operatorService.saveShipOrderOperator(operator);
			
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("msg", e.getMessage());
		}
		
		return retMap;
	}
	
	/**
	 * 异常订单列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="shipOrderStop")
	public String orderStopList(ModelMap model){
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		return "admin/shipOrder/orderStopList";
	}
	
	@RequestMapping(value="shipOrderStop/lisData")
	@ResponseBody
	public Map<String, Object> orderStopListData(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "100") int rows, HttpServletRequest request){
		if (rows==10) {
			rows=100;
		}
		String userId = request.getParameter("userId");
		String txt = request.getParameter("txt");
		String type = request.getParameter("type");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("expressOrderno", txt);
		params.put("type", type);
		List<ShipOrderStop> orderStops = this.orderStopService.getShipOrderStopByPage(params,page,rows);
		int total = this.orderStopService.getTotal(params);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("rows",this.orderStopService.buildOrderStop(orderStops));
		retMap.put("page", page);
		retMap.put("total", total);
		return retMap;
	}
	
	/**
	 * 韵达自动审单
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "toAuditAreaYUNDA/{type}", method = RequestMethod.GET)
	public String toAuditAreaYUNDA(@PathVariable("type") String type,HttpServletRequest request,Model model)
			throws Exception {
		
		String  ids  = request.getParameter("ids");
		
		model.addAttribute("ids",ids.substring(0,ids.length()-1));
		
		model.addAttribute("type",type);
		
		return "/admin/shipOrder/auditAreaWaitsYUNDA";
	}
	
	@RequestMapping(value = "ajax/auditAreaYUNDA/{type}")
	@ResponseBody
	public Map<String,Object> auditAreaYUNDA(@PathVariable("type") String type,@RequestParam(value = "ids", defaultValue = "0") String[] ids
		) throws Exception {
		
		if(!("YUNDAX".equals(type)  || "YUNDA".equals(type) || "YTO".equals(type)) ){
			logger.error("快递公司异常【"+type+"】");
			return null;
		}
		
		logger.debug("YUNDA自动审单Start");
		Map<String,Object>  resultMap  =new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("array", ids);
		try {
			List<TmsOrder> tmsOrders = this.tmsOrderService.getTmsOrderByList(params);
//			System.err.println("size:"+orders.size());
			Map<String, Map<String,Object>>  mapList=  new HashMap<String, Map<String,Object>>();
			for(TmsOrder  tmsOrder:  tmsOrders){
				ShipOrder order=this.shipOrderService.findShipOrderById(tmsOrder.getOrder().getId());
				ReceiverInfo receiverInfo=this.receiverInfoService.getReceiverInfoById(order.getReceiverInfo().getId());
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id", tmsOrder.getId());
//				map.put("orderCode", order.getOrderCode());
//				ReceiverInfo receiverInfo = this.receiverInfoService.getReceiverInfoById(shipOrder.getReceiverInfo().getId());
				String address = receiverInfo.getReceiverProvince() + receiverInfo.getReceiverCity() + receiverInfo.getReceiverArea() + receiverInfo.getReceiverAddress();
//				String address = receiverInfo.getReceiverAddress();
				map.put("address", address.replace(" ", ""));
				mapList.put(tmsOrder.getId(), map);
			}
 			System.err.println("mapList:"+mapList);
			
			Map<String, Object> apiRet = this.auditAreaService.checkYUNDAAddressApi(mapList);
			
			System.err.println("apiRet:"+apiRet);
			
			Map<String, Map<String,Object>> maps = (Map<String, Map<String, Object>>) apiRet.get("maps");
			System.err.println("maps:"+maps);
			resultMap.put("rows", buildYunDaAuditTradeList(tmsOrders,maps,type));
	        resultMap.put("total", tmsOrders.size());
		} catch (Exception e) {
			 logger.error(e.getMessage());
			 e.printStackTrace();
		}
		logger.debug("YUNDA自动审单End");
		return resultMap;
	}
	
	/**
	 * 韵达快递 构建自动审核  订单结构
	 * @param trades
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
 	private List<Map<String,Object>> buildYunDaAuditTradeList(List<TmsOrder> orders,Map<String, Map<String,Object>> retMaps,String type) throws UnsupportedEncodingException{
 		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
 		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		
 		for(int i=0;orders!=null && i<orders.size();i++){
 			TmsOrder tmsOrder=orders.get(i);
 			ShipOrder order=this.shipOrderService.findShipOrderById(tmsOrder.getOrder().getId());
// 			System.err.println("orderId:"+order.getOrderCode());
 			Map<String,Object> map=new HashMap<String, Object>();
 			Map<String, Object> retMap = retMaps.get(tmsOrder.getId());
// 			System.out.println("retMaps:"+retMaps);
// 			System.out.println("retMap:"+retMap);
 			if("1".equals(retMap.get("reach"))){
 				try {
 					map.put("msg", "送达");
 					this.shipOrderService.auditOneTrade(tmsOrder.getId(), type, this.getCurrentAccount());
 					map.put("audit", "成功");
 					map.put("status", "1");
 				} catch (Exception e) {
 					e.printStackTrace();
 					map.put("audit", "联系管理员");
 					map.put("status", "2");
 					logger.error(e.getMessage());
 				}
 				
 			}else if("0".equals(retMap.get("reach"))){
 				map.put("status", "0");
 				map.put("msg", "不到");
 			}else{
 				map.put("audit", "单据异常");
 				map.put("msg", "不到");
 			}
 			
 			map.put("val", order.getId());
 			User user = this.userService.getUserById(order.getUser().getId());
 			map.put("shopName", user.getSubscriberName());
 			map.put("createDate", sdf.format(order.getOrderCreateTime()));
 			map.put("erpOrderCode", order.getErpOrderCode());
 			
 			ReceiverInfo receiverInfo = this.receiverInfoService.getReceiverInfoById(order.getReceiverInfo().getId());
 			String phone=((receiverInfo.getReceiverMobile()==null)?"":receiverInfo.getReceiverMobile())+((receiverInfo.getReceiverPhone()==null)?"":","+receiverInfo.getReceiverPhone());
 			map.put("phone",phone);
 			String address = receiverInfo.getReceiverProvince() + receiverInfo.getReceiverCity() + receiverInfo.getReceiverArea() + receiverInfo.getReceiverAddress();
 			map.put("address", address);
// 			String items="";
// 			items=this.tradeRemote.getItemsByTrade(trade.getId().intValue());
 			map.put("items", order.getItems());
 			if (receiverInfo.getReceiverNick()!=null) {
 				map.put("buyerNick", receiverInfo.getReceiverNick());
 			}else {
 				map.put("buyerNick", receiverInfo.getReceiverName());
			}
 			map.put("weight",(order.getTotalWeight()!=null)?order.getTotalWeight():0);
 			resultList.add(map);
 		}
 		return resultList;
 	}
 	
 	/**
 	 * 根据时间段批量修改tmsOrder数据
 	 * @return
 	 */
 	@RequestMapping(value="initTmsOrder")
 	@ResponseBody
 	public Map<String, Object> initTmsOrder(){
 		logger.error("tmsOrder单号批量修改开始！");
 		Map<String, Object> params = new HashMap<String, Object>();
 		params.put("startDate", "2017-11-01 00:00:00");
 		params.put("endDate", "2017-11-23 00:00:00");
// 		params.put("orderStatus", OrderStatusEnum.WMS_FINASH);
 		List<TmsOrder> orders = this.tmsOrderService.getTmsOrderByList(params);
 		for(TmsOrder tmsOrder:orders){
 			ShipOrder shipOrder = this.shipOrderService.findShipOrderById(tmsOrder.getOrder().getId());
 			if (shipOrder!=null) {
 				String tmsOrderCode = shipOrder.getTmsOrderCode();
 	 			String orderCode = tmsOrder.getOrderCode();
 	 			if (!(orderCode.equals(tmsOrderCode))) {
 	 				logger.error(shipOrder.getId()+"开始修改！");
 					tmsOrder.setOrderCode(tmsOrderCode);
 					this.tmsOrderService.updateTmsOrder(tmsOrder);
 				}
			}	
 		}
 		Map<String, Object> retMap = new HashMap<String, Object>();
 		retMap.put("msg", "更新完成！");
 		logger.error("tmsOrder单号批量修改结束！");
		return retMap;
 	}
 	
 	/**
 	 * 根据orderId创建TmsOrder
 	 * @param request
 	 * @return
 	 */
 	@RequestMapping(value="createTmsOrder")
 	@ResponseBody
 	public Map<String, Object> createTmsOrderByOrderID(HttpServletRequest request){
 		String orderId = request.getParameter("orderId");
 		ShipOrder shipOrder = this.shipOrderService.findShipOrderById(orderId);
 		Map<String, Object> params = new HashMap<String, Object>();
 		params.put("orderId", orderId);
 		List<TmsOrder> tmsOrders = this.tmsOrderService.getTmsOrderByList(params);
 		if (tmsOrders.size()<1) {
 			logger.error("tmsOrder开始创建！");
			TmsOrder tmsOrder = new TmsOrder();
			tmsOrder.generateId();
			tmsOrder.setOrder(shipOrder);
			List<TmsOrderItem> tmsOrderItems =new ArrayList<TmsOrderItem>();
			List<WmsConsignOrderItem> itemList = this.orderItemService.getWmsConsignOrderItemByList(params);
			for(WmsConsignOrderItem orderItem:itemList){
				logger.error("tmsOrderItems开始创建！");
				Item item = this.itemService.getItem(orderItem.getItem().getId());
				TmsOrderItem tmsItem=new TmsOrderItem();
				tmsItem.generateId();
				tmsItem.setItemCode(item.getItemCode());
				tmsItem.setItemId(item.getItemId());
				tmsItem.setItemQuantity(orderItem.getItemQuantity());
				tmsItem.setOrderItemId(orderItem.getOrderItemId());
				tmsItem.setTmsOrder(tmsOrder);
				tmsItem.setItem(item);
				tmsItem.setOrder(shipOrder);
				tmsOrderItems.add(tmsItem);
			}
			logger.error("tmsOrderItems结束创建！");
			tmsOrder.setCode(shipOrder.getTmsServiceCode());
			tmsOrder.setOrderCode(shipOrder.getTmsOrderCode());
			tmsOrder.setBatchCode(shipOrder.getBatchCode());
			tmsOrder.setConsolidationCode(shipOrder.getConsolidationCode());
			tmsOrder.setConsolidationName(shipOrder.getConsolidationName());
			tmsOrder.setRouteCode(shipOrder.getTmsRouteCode());
			tmsOrder.setTemplateURL(shipOrder.getTemplateURL());
			tmsOrder.setOrder(shipOrder);
			tmsOrder.setCreateDate(new Date());
			tmsOrder.setStatus(TmsOrder.split);
			tmsOrder.setItems(shipOrder.getItems());
			tmsOrder.setOrderStatus(shipOrder.getStatus());
			this.tmsOrderService.insertTmsOrder(tmsOrder);
			this.tmsOrderService.insertTmsOrderItem(tmsOrderItems);
			logger.error("tmsOrder结束创建！");
		}
 		Map<String, Object> map = new HashMap<String, Object>();
 		map.put("msg", "tmsOrder初始化成功！");
		return map;
 	}
 	
 	/**
 	 * 根据时间段批量创建tmsOrder
 	 * @param request
 	 * @return
 	 */
	@RequestMapping(value="createTmsOrders")
 	@ResponseBody
 	public Map<String, Object> createTmsOrders(HttpServletRequest request){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", "2016-11-11 09:00:00");
		params.put("endDate", "2017-11-12 00:00:00");
		List<ShipOrder> orders = this.shipOrderService.findTmsNotExsist(params);	
 		for(ShipOrder shipOrder:orders){
 			params.put("orderId", shipOrder.getId());
 	 		List<TmsOrder> tmsOrders = this.tmsOrderService.getTmsOrderByList(params);
 	 		if (tmsOrders.size()<1) {
 	 			logger.error("tmsOrder开始创建！");
 				TmsOrder tmsOrder = new TmsOrder();
 				tmsOrder.generateId();
 				tmsOrder.setOrder(shipOrder);
 				List<TmsOrderItem> tmsOrderItems =new ArrayList<TmsOrderItem>();
 				List<WmsConsignOrderItem> itemList = this.orderItemService.getWmsConsignOrderItemByList(params);
 				for(WmsConsignOrderItem orderItem:itemList){
 					logger.error("tmsOrderItems开始创建！");
 					Item item = this.itemService.getItem(orderItem.getItem().getId());
 					TmsOrderItem tmsItem=new TmsOrderItem();
 					tmsItem.generateId();
 					tmsItem.setItemCode(item.getItemCode());
 					tmsItem.setItemId(item.getItemId());
 					tmsItem.setItemQuantity(orderItem.getItemQuantity());
 					tmsItem.setOrderItemId(orderItem.getOrderItemId());
 					tmsItem.setTmsOrder(tmsOrder);
 					tmsItem.setItem(item);
 					tmsItem.setOrder(shipOrder);
 					tmsOrderItems.add(tmsItem);
 				}
 				logger.error("tmsOrderItems结束创建！");
 				tmsOrder.setCode(shipOrder.getTmsServiceCode());
 				tmsOrder.setOrderCode(shipOrder.getTmsOrderCode());
 				tmsOrder.setBatchCode(shipOrder.getBatchCode());
 				tmsOrder.setConsolidationCode(shipOrder.getConsolidationCode());
 				tmsOrder.setConsolidationName(shipOrder.getConsolidationName());
 				tmsOrder.setSortationName(shipOrder.getSortationName());
 				tmsOrder.setRouteCode(shipOrder.getTmsRouteCode());
 				tmsOrder.setTemplateURL(shipOrder.getTemplateURL());
 				tmsOrder.setOrder(shipOrder);
 				tmsOrder.setCreateDate(shipOrder.getLastUpdateTime());
 				tmsOrder.setStatus(TmsOrder.split);
 				tmsOrder.setItems(shipOrder.getItems());
 				tmsOrder.setOrderStatus(shipOrder.getStatus());
 				this.tmsOrderService.insertTmsOrder(tmsOrder);
 				this.tmsOrderService.insertTmsOrderItem(tmsOrderItems);
 				logger.error("tmsOrder结束创建！");
 			}
 		}		
 		Map<String, Object> map = new HashMap<String, Object>();
 		map.put("msg", "tmsOrder初始化成功！");
		return map;
 	}
	
}
