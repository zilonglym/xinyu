package com.xinyu.controller.trade;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.api.ApiException;
import com.xinyu.common.BaseController;
import com.xinyu.model.base.Centro;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.SenderInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.base.enums.ReceiveStateEnums;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.qm.StoreConstants;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.SystemItem;
import com.xinyu.model.system.enums.OperatorModel;
import com.xinyu.model.system.enums.OrderStatusEnum;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderOperator;
import com.xinyu.model.trade.TmsOrder;
import com.xinyu.service.system.CentroService;
import com.xinyu.service.system.SystemItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.trade.ReceiverInfoService;
import com.xinyu.service.trade.SenderInfoService;
import com.xinyu.service.trade.ShipOrderOperatorService;
import com.xinyu.service.trade.ShipOrderService;
import com.xinyu.service.trade.TmsOrderService;
import com.xinyu.service.waybill.SFWayBillService;
import com.xinyu.service.waybill.WayBillService;

/**
 * 面单打印相关处理
 * */
@Controller
@RequestMapping(value = "express")
public class WayBillController extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(WayBillController.class);
	
	public static String sessionKey="";
	
	private Map<String,Object> templateMap=null;
	
	/**
	 * 发货特殊处理的商家，黑白电脑椅
	 */
	private String otherUser="55";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CentroService centroService;
	
	@Autowired
	private SystemItemService systemItemService;
	
	@Autowired
	private ShipOrderService shipOrderService;
	
	@Autowired
	private ShipOrderOperatorService shipOrderOperatorService;
	
	@Autowired
	private WayBillService wayBillService;
	
	@Autowired
	private SFWayBillService SfBillService;
	
	@Autowired
	private ReceiverInfoService receiverInfoService;
	
	@Autowired
	private SenderInfoService senderInfoService;

	@Autowired
	private TmsOrderService tmsOrderService;
	/**
 	 * easyui待打印订单列表
 	 * @param model
 	 * @return String
 	 * */
 	@RequestMapping(value="waits")
 	public String waitsList(ModelMap model,HttpServletRequest request){
 		
 		//添加仓库ID参数
 		Map<String, Object> params = new  HashMap<String, Object>();
 		params.put("cu", this.getCurrentAccount().getCu());
 		List<User> users = this.userService.getUserBySearch(params);
 		model.put("users", users);
 		
 		String type = "waybill";
 		params.clear();;
 		params.put("type", type);
 		List<SystemItem> itemList = this.systemItemService.findSystemItemByList(params);
 		model.put("itemList", itemList);
 		
 		model.put("stateList", ReceiveStateEnums.values());
 		
 		return "admin/shipOrder/shipOrderListWaits";
 	}
	
 	/**
 	 * easyui待打印订单列表数据填充
 	 * @param page
 	 * @param rows
 	 * @param request
 	 * @return resultMap
 	 * */
 	@RequestMapping(value="waits/listData")
 	@ResponseBody
 	public Map<String, Object> waitsListData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
 		
 		if (rows==10) {
 			rows=200;
 		}
 		
 		Map<String, Object> params = new HashMap<String, Object>();
 		String userId = request.getParameter("userId");
 		String cpCode = request.getParameter("sysId");
 		String beigainTime = request.getParameter("beigainTime");
 		String lastTime = request.getParameter("lastTime");
 		String searchText = request.getParameter("searchText");
 		String txtno = request.getParameter("txtno");
 		String searchType=this.getString("searchType");
// 		String tradeBatchId = request.getParameter("tradeBatchId");
 		String receiveState = request.getParameter("receiveState");
 		if(receiveState!=null && receiveState.equals("0")){
 			receiveState = "";
 		}

		params.clear();
		
		//添加仓库ID参数
		params.put("cu", this.getCurrentAccount().getCu());
		
 		if (cpCode!=null&&!cpCode.equals("0")) {
 	 		params.put("tmsServiceCode",cpCode);
		}	
// 		String  cu =  BaseResource.getCurrentCentroId();
// 		params.put("cu",cu);
// 		params.put("tradeBatchId", tradeBatchId);

 		if (StringUtils.isNotEmpty(beigainTime) && !beigainTime.equals("null")) {
 			params.put("startTime", beigainTime);
		}
		
		if (StringUtils.isNotEmpty(lastTime) && !lastTime.equals("null")) {
			params.put("endTime", lastTime);
		}
		
		if (StringUtils.isNotBlank(searchText)) {
			params.put("searchText",searchText);
		}
		if (StringUtils.isNotBlank(searchType)) {
			params.put("searchType",searchType);
		}
 		params.put("userId",userId);
 		if (StringUtils.isNotEmpty(receiveState) && !receiveState.equals("0")) {
			params.put("receiverState", receiveState);
		}
 		params.put("txtno", txtno);
 		params.put("status", OrderStatusEnum.WMS_AUDIT.getKey());
 		int start = (page - 1) * rows;
 		params.put("pageSize", rows);
		params.put("pageNum", start); 		
 		List<ShipOrder> orders = this.shipOrderService.getShipOrderListByAll(params);
   		long total = this.shipOrderService.getShipOrderListByAllCount(params);
 		
 		Map<String, Object> resultMap = new HashMap<String, Object>();
 		resultMap.put("total",total);
 		resultMap.put("page",page);
 		resultMap.put("rows",this.shipOrderService.bulidListData(orders));
 		
 		return resultMap;
 	}
 	
 	/**
 	 * 手动填写物流单号
 	 * @param model
 	 * @param request
 	 * @return
 	 */
 	@RequestMapping(value="writeCode")
 	public String writeExpressOrderNo(ModelMap model,HttpServletRequest request){
 		String tmsId = request.getParameter("id");
 		TmsOrder tmsOrder = this.tmsOrderService.getTmsOrderById(tmsId);
 		ShipOrder shipOrder = this.shipOrderService.findShipOrderById(tmsOrder.getOrder().getId());
 		model.put("shipOrder", shipOrder);
 		model.put("tmsId", tmsId);
 		return "admin/shipOrder/writeCode";
 	}
 	
 	/**
 	 * 更新订单物流单号和状态
 	 * @param request
 	 * @return
 	 */
 	@RequestMapping(value="saveCode")
 	@ResponseBody
 	public Map<String, Object> saveOrderNo(HttpServletRequest request){
 		String id = request.getParameter("id");
 		String orderNo = request.getParameter("orderNo");
 		String tmsId = request.getParameter("tmsId");
 		Map<String, Object> retMap = new HashMap<String, Object>();
 		retMap.put("id", id);
 		retMap.put("orderNo", orderNo);
 		retMap.put("status", OrderStatusEnum.WMS_PRINT);
 		System.err.println("retMap:"+retMap);
 		try {
 			if (orderNo.isEmpty()) {
 				retMap.put("msg", "物流单号不能为空！");
			}else {
				ShipOrder shipOrder = this.shipOrderService.findShipOrderById(id);
				
			
				/**
				 * 特殊快递进行处理
				 * TmsOrder没有特殊处理
				 */
				String company = shipOrder.getTmsServiceCode();
				if ("YUNDAX".equals(company)) {
					retMap.put("company", "YUNDA");
					company = "YUNDA";
				}else if("HTKY5".equals(company)||"HTKY11".equals(company)){
					retMap.put("company", "HTKY");
					company = "HTKY";
				}
				
				/**
				 * 更新tmsOrder
				 */
				TmsOrder tmsOrder = this.tmsOrderService.getTmsOrderById(tmsId);
				tmsOrder.setCode(company);
				tmsOrder.setOrderCode(orderNo);
				tmsOrder.setOrderStatus(OrderStatusEnum.WMS_PRINT);
				this.tmsOrderService.updateTmsOrder(tmsOrder);
				
				this.shipOrderService.updateExpressOrderNo(retMap);
				//写操作日志
				this.createOrderOperator(shipOrder);
	 			retMap.clear();
	 			retMap.put("msg", "更新成功！"+orderNo);
			}
		} catch (Exception e) {
			retMap.clear();
			retMap.put("msg", e.getMessage());
		}
 		return retMap;
 	}
 	
 	/**
 	 * 根据shipOrder创建日志
 	 * @param shipOrder
 	 */
 	private void createOrderOperator(ShipOrder shipOrder) {
		ShipOrderOperator orderOperator = new ShipOrderOperator();
		orderOperator.generateId();
		orderOperator.setAccount(this.getCurrentAccount());
		orderOperator.setNewValue(OrderStatusEnum.WMS_PRINT.getKey());
		orderOperator.setOldValue(OrderStatusEnum.WMS_AUDIT.getKey());
		orderOperator.setOperatorDate(new Date());
		orderOperator.setShipOrder(shipOrder);
		if ("WMS_PRINT".equals(shipOrder.getStatus().getKey())) {
			orderOperator.setOperatorModel(OperatorModel.TRADE_PRINT);
			orderOperator.setDescription(shipOrder.getOrderCode()+"手动填写物流单号！"+shipOrder.getTmsServiceCode()+"|"+shipOrder.getTmsOrderCode());
		}
		this.shipOrderOperatorService.saveShipOrderOperator(orderOperator);
	}
 	
 	
 	/**
 	 * 根据shipOrder创建日志
 	 * @param shipOrder
 	 */
 	private void saveOrderOperator(TmsOrder tmsOrder) {
		ShipOrderOperator orderOperator = new ShipOrderOperator();
		orderOperator.generateId();
		orderOperator.setAccount(this.getCurrentAccount());
		orderOperator.setNewValue(OrderStatusEnum.WMS_GETNO.getKey());
		orderOperator.setOldValue(OrderStatusEnum.WMS_PRINT.getKey());
		orderOperator.setOperatorDate(new Date());
		orderOperator.setShipOrder(tmsOrder.getOrder());
		orderOperator.setOperatorModel(OperatorModel.TRADE_PRINT);
		orderOperator.setDescription(tmsOrder.getOrder().getOrderCode()+"面单打印:"+tmsOrder.getOrderCode());
		this.shipOrderOperatorService.saveShipOrderOperator(orderOperator);
	}

	/**
 	 * easyui已获取单号订单列表
 	 * @param model
 	 * @return String
 	 * */
 	@RequestMapping(value="waitsOk")
 	public String waitsOkList(ModelMap model){
 		
 		//添加仓库ID参数
 		Map<String, Object> params = new HashMap<String, Object>();
 		params.put("cu", this.getCurrentAccount().getCu());
 		List<User> users=this.userService.getUserBySearch(params);
 		model.put("users", users);
 		
 		String type = "waybill";
 		params.clear();;
 		params.put("type", type);
 		List<SystemItem> itemList = this.systemItemService.findSystemItemByList(params);
 		model.put("itemList", itemList);
 		
 		model.put("stateList", ReceiveStateEnums.values());
 		
 		return "admin/shipOrder/shipOrderListWaitsOK";
 	}
 	
 	/**
 	 * 订单重新打
 	 * @param page
 	 * @param rows
 	 * @param request
 	 * @return resultMap
 	 * */
 	@RequestMapping(value="waitsOk/listData")
 	@ResponseBody
 	public Map<String, Object> waitsOkListData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
 		if (rows==10) {
 			rows=200;
 		}
 		Map<String, Object> params = new HashMap<String, Object>();
 		String userId = request.getParameter("userId");
 		String cpCode = request.getParameter("sysId");
 		String beigainTime = request.getParameter("beigainTime");
 		String lastTime = request.getParameter("lastTime");
 		String searchText = request.getParameter("searchText");
 		String txtno = request.getParameter("txtno");
 		String receiveState = request.getParameter("receiveState");
 		String searchType=this.getString("searchType");
		params.clear();	
 	 	params.put("tmsServiceCode",cpCode);	
 		params.put("startTime", beigainTime);
		params.put("endTime", lastTime);
		params.put("searchType",searchType);
		params.put("searchText",searchText);
 		params.put("userId",userId);
		params.put("receiverState", receiveState);
 		params.put("txtno", txtno);
 		params.put("status", "PerPrint");
 		
 		//添加仓库ID参数
 		params.put("cu", this.getCurrentAccount().getCu());
 		
 		int start = (page - 1) * rows;
 		params.put("pageSize", rows);
		params.put("pageNum", start); 		
 		List<ShipOrder> orders = this.shipOrderService.getShipOrderListByAll(params);
   		long total = this.shipOrderService.getShipOrderListByAllCount(params);
 		Map<String, Object> resultMap = new HashMap<String, Object>();
 		resultMap.put("total",total);
 		resultMap.put("page",page);
 		resultMap.put("rows",this.shipOrderService.bulidListData(orders));
 		
 		return resultMap;
 	}
 	
 	
 	@RequestMapping(value="waitsPrint")
 	public String waitsPrintList(ModelMap model){
 		
 		//添加仓库ID参数
 		Map<String, Object> params = new HashMap<String, Object>();
 		params.put("cu", this.getCurrentAccount().getCu());
 		List<User> users=this.userService.getUserBySearch(params);
 		model.put("users", users);
 		
 		String type = "waybill";
 		params.clear();
 		params.put("type", type);
 		List<SystemItem> itemList = this.systemItemService.findSystemItemByList(params);
 		model.put("itemList", itemList);
 		
 		model.put("stateList", ReceiveStateEnums.values());
 		
 		return "admin/shipOrder/shipOrderListWaitsPrint";
 	}
 	
 	/**
 	 * easyui已获取单号订单列表数据填充
 	 * @param page
 	 * @param rows
 	 * @param request
 	 * @return resultMap
 	 * */
 	@RequestMapping(value="waitsPrint/listData")
 	@ResponseBody
 	public Map<String, Object> waitsPrintListData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
 		
 		if (rows==10) {
 			rows = 200;
 		}
 		
 		Map<String, Object> params = new HashMap<String, Object>();
 		String userId = request.getParameter("userId");
 		String tmsServiceCode = request.getParameter("sysId");
 		String beigainTime = request.getParameter("beigainTime");
 		String lastTime = request.getParameter("lastTime");
 		String searchText = request.getParameter("searchText");
 		String txtno = request.getParameter("txtno");
 		String receiveState = request.getParameter("receiveState");
 		String searchType=this.getString("searchType");
 		params.clear();		
		params.put("searchText",searchText);
		params.put("searchType",searchType);
 		params.put("userId",userId);
 		params.put("startDate", beigainTime);
 		params.put("endDate", lastTime);
 		params.put("receiverState", receiveState);
 		params.put("txtno", txtno);
 		params.put("tmsServiceCode", tmsServiceCode);
 		params.put("status", OrderStatusEnum.WMS_GETNO);
 		
 		//添加仓库ID参数
 		params.put("cu", this.getCurrentAccount().getCu());
 		
 		int start = (page - 1) * rows;
 		params.put("pageSize", rows);
		params.put("pageNum", start); 		
 		List<ShipOrder> orders = this.shipOrderService.getShipOrderListByAll(params);
 		long total = this.shipOrderService.getShipOrderListByAllCount(params);
 		
 		Map<String, Object> resultMap = new HashMap<String, Object>();
 		resultMap.put("total",total);
 		resultMap.put("page",page);
 		resultMap.put("rows",this.shipOrderService.bulidListData(orders));
 		
 		return resultMap;
 	}
 	
	/**
	 * rs 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="c_print")
	public String test(ModelMap model){
		String type=this.getString("type");
		String ids=this.getString("ids");
		String cpCode=this.getString("cp_code");
		
		model.put("cpCode", cpCode);
		model.put("batchCode", this.getbatchCode());
		model.put("type", type);
		model.put("ids", ids);
		if(StringUtils.isNotBlank(cpCode) && cpCode.equals("SF")){
			return "admin/waybill/sf_ajax";
		}else{
			return "admin/print/c_cainiao";
		}
	}
	
	@RequestMapping(value="getTemplates")
	@ResponseBody
	public Map<String,Object> getCainiaoTemplates()  throws Exception {
		
		Map<String,Object> resultMap=new HashMap<String, Object>();	
		String templates=this.wayBillService.getCainiaoTemplates();
		resultMap.put("ret", 1);
		resultMap.put("templates", templates);
		
		logger.debug("templates:"+templates);
		
		return resultMap;
	}
	
	/**
	 * 刷新URL
	 * @return
	 */
	@RequestMapping(value="refresh")
	@ResponseBody
	public Map<String,Object> refersh(){	
		Map<String,Object> resultMap=new HashMap<String, Object>();
		this.refreshTemplate();
		resultMap.put("ret", 1);
		resultMap.put("msg", "刷新成功");
		return resultMap;
	}
	
	private void refreshTemplate(){
		if(this.templateMap==null){
			this.templateMap=new HashMap<String, Object>();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", "template");
		List<SystemItem>  sysItem =this.systemItemService.findSystemItemByList(params);
		for(int i=0;i<sysItem.size();i++){
			SystemItem obj=sysItem.get(i);
			this.templateMap.put(obj.getDescription(), obj.getValue());
		}
	}
	
	
	
	/**
	 * 菜鸟去打接口取订单号
	 * @return
	 */
	@RequestMapping(value="c_getPrintData")
	@ResponseBody
	public Map<String,Object> getCainiaoNo(){		
		String ids=this.getString("ids");
		String cpCode=this.getString("cp_code");
		String batchCode=this.getbatchCode();
	
		Map<String,Object> resultMap=new HashMap<String, Object>();
		
		if(this.templateMap==null){
			this.refreshTemplate();
		}
		String url=(String)this.templateMap.get(cpCode);
		Map<String,Object> map=null;
		try{
			System.err.println("url:"+url);
			map =this.wayBillService.getCainiaoBill(ids, cpCode, url, batchCode,this.getCurrentAccount());
			String msg=(String)map.get("msg");
			if(!StringUtils.isEmpty(msg) ){
				resultMap.put("msg", msg);
				resultMap.put("ret", 0);
			}else{
				resultMap.put("ret", 1);
			}
			resultMap.put("ids", map.get("ids"));
		}catch(Exception e){
			StackTraceElement[] ary=e.getStackTrace();
			resultMap.put("ret", 0);
			resultMap.put("msg", e.toString()+"<br>"+ary[0]);
			e.printStackTrace();
		}		
		return resultMap;
	}

	/**
	 * 取云打印数据
	 * @return
	 */
	@RequestMapping(value="c_printData")
	@ResponseBody
	public Map<String,Object> getCPrintData(){
		
		if(this.templateMap==null){
			this.refreshTemplate();
		}
		String type=this.getString("type");
		
		String waybillTemplateURL = "";
	    String customAreaURL    = "";
		String ids=this.getString("ids");
		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		
		JSONArray documents=new JSONArray();	
		
		String[] ary=ids.split(",");
		
		for(int i=0;i<ary.length;i++){
			Date date=new Date();
			TmsOrder tmsOrder=this.tmsOrderService.getTmsOrderById(ary[i]);
			ShipOrder order=this.shipOrderService.findShipOrderById(tmsOrder.getOrder().getId());
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("orderId", order.getId());
			List<TmsOrder> tmsList=this.tmsOrderService.getTmsOrderByList(params);
			waybillTemplateURL=tmsOrder.getTemplateURL();
			if(StringUtils.isEmpty(waybillTemplateURL)){
				waybillTemplateURL=(String)this.templateMap.get(tmsOrder.getCode());
			}
			
			customAreaURL=(String)this.templateMap.get("AREA");//自定义区域
			
 			Centro centro=this.centroService.getCentroById(order.getCu());
 			
			User user=this.userService.getUserById(order.getUser().getId());
			
			JSONObject doc=new JSONObject();
			
			JSONArray contents=new JSONArray(); //内容数据，一个内容一个运单
			
			if(StringUtils.isNotBlank(order.getOrderCode())){
				doc.put("documentID", order.getOrderCode());
			}else{
				continue;
			}
			if(StringUtils.isEmpty(type) 
					&&!order.getStatus().equals(OrderStatusEnum.WMS_GETNO)
					&& tmsList.size()<=1
					){
				continue;
			}
			//运单主区域
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			
			JSONObject dataJson=new JSONObject();
			
			JSONObject address=new JSONObject();
			
			ReceiverInfo receiverInfo = this.receiverInfoService.getReceiverInfoById(order.getReceiverInfo().getId());
			address.put("city", receiverInfo.getReceiverCity());
			address.put("detail", receiverInfo.getReceiverAddress());
			address.put("district", receiverInfo.getReceiverArea());
			address.put("province", receiverInfo.getReceiverProvince());
			address.put("town", receiverInfo.getReceiveTown());
			
			JSONObject recipient=new JSONObject();
			recipient.put("address", address);
			recipient.put("mobile", receiverInfo.getReceiverMobile());
			recipient.put("name", receiverInfo.getReceiverName());
			recipient.put("phone", receiverInfo.getReceiverPhone());
			
			
			dataJson.put("recipient", recipient);//收件人信息处理
			
			JSONObject routingInfo=new JSONObject();
			
			JSONObject consolidation=new JSONObject();//集包地信息
			consolidation.put("name",tmsOrder.getConsolidationName());
			consolidation.put("code", tmsOrder.getConsolidationCode());
			routingInfo.put("consolidation", consolidation);
			
			JSONObject origin=new JSONObject();//发件网点名称
			origin.put("code", tmsOrder.getCode());
			routingInfo.put("origin", origin);
			
			JSONObject sortation=new JSONObject();//大头笔信息
			sortation.put("name", tmsOrder.getSortationName());
			routingInfo.put("sortation", sortation);
			routingInfo.put("routeCode", tmsOrder.getRouteCode());
			dataJson.put("routingInfo", routingInfo);
			
			JSONObject sender=new JSONObject();//发件人信息
			
			JSONObject senderAddress=new JSONObject();
			
			String sellerMessage = order.getSellerMessage();
			
			if(sellerMessage!=null&&sellerMessage.indexOf("环球捕手")!=-1){
				senderAddress.put("city","");
				senderAddress.put("detail", "环球捕手");
				senderAddress.put("district", "");//区
				senderAddress.put("province", "");
				sender.put("address", senderAddress);
				sender.put("mobile", "");
				sender.put("name", "环球捕手4001603602");
			}else if(sellerMessage!=null&&sellerMessage.indexOf("脉宝云店")!=-1){
				senderAddress.put("city","");
				senderAddress.put("detail", "脉宝云店");
				senderAddress.put("district", "");//区
				senderAddress.put("province", "");
				sender.put("address", senderAddress);
				sender.put("mobile","");
				sender.put("name", "脉宝云店4001116789");
			}else if("idzc18000597278".equals(order.getUser().getId())){
				senderAddress.put("city","");
				senderAddress.put("detail", "四川省成都市蒲江县西来镇");
				senderAddress.put("district", "");//区
				senderAddress.put("province", "");
				sender.put("address", senderAddress);
				sender.put("mobile", "028-88606756");
				sender.put("name", "果佳果园");
				//sender.put("phone", user.getCode());
			}else if("idzc660824712".equals(order.getUser().getId())){
				senderAddress.put("city",centro.getCity());
				senderAddress.put("detail", "湖南省湘潭市岳塘区双马镇金钢人防");
				senderAddress.put("district", "");//区
				senderAddress.put("province", centro.getProvince());
				sender.put("address", senderAddress);
				sender.put("mobile", user.getSubscriberMobile());
				sender.put("name", order.getShopName());
				//sender.put("phone", user.getCode());
			}else {
				senderAddress.put("city",centro.getCity());
				senderAddress.put("detail", "湖南省湘潭市岳塘区双马镇金钢人防");
				senderAddress.put("district", "");//区
				senderAddress.put("province", centro.getProvince());
				sender.put("address", senderAddress);
				sender.put("mobile", user.getSubscriberMobile());
				sender.put("name", user.getSubscriberName());
			}
			dataJson.put("sender", sender);
			
			JSONObject shippingOption=new JSONObject();
			shippingOption.put("code", "COD");
			
			JSONObject services=new JSONObject();
			
			JSONObject other=new JSONObject();
			other.put("value", order.getTmsDisplayName());
			services.put("SVC-COD", other);
			shippingOption.put("title", tmsOrder.getCode());//右上角标题
			shippingOption.put("services", services);
			dataJson.put("shippingOption", shippingOption);
			dataJson.put("waybillCode", tmsOrder.getOrderCode());
			
			//运单备注区域
			JSONObject remarkJson=new JSONObject();
			remarkJson.put("templateURL", customAreaURL);
			
			JSONObject data=new JSONObject();
			
			/**
			 * modify 2017-09-18 fufangue
			 * 世派和现代不打印备注
			 * 根据items是否为空，打印明细
			 * */
			String userId = order.getUser().getId();
			if (userId.equals("idzc16473350920")||userId.equals("idzc16473350942")||userId.equals("idzc16473350928")||userId.equals(" idzc1674590543")) {
				if (StringUtils.isEmpty(tmsOrder.getItems())) {
					data.put("item_name", this.shipOrderService.bulidItemsDataByTms(tmsOrder)+(order.getSellerMessage()!=null?(" 卖家："+order.getSellerMessage()):""));
				}else {
					data.put("item_name", tmsOrder.getItems()+(order.getSellerMessage()!=null?(" 卖家："+order.getSellerMessage()):""));
				}	
			}else if (StringUtils.isNotEmpty(order.getSellerMessage()) && order.getSellerMessage().indexOf("闪电发货")!=-1) {
				data.put("item_name", tmsOrder.getItems()+" 卖家："+order.getSellerMessage());
			}else{
				if (StringUtils.isEmpty(tmsOrder.getItems())) {
					data.put("item_name", this.shipOrderService.bulidItemsDataByTms(tmsOrder));
				}else {
					data.put("item_name", tmsOrder.getItems());
				}	
			}
			
			remarkJson.put("data", data);
		//	contents.put(dataJson);
			
			JSONObject jsonMap=new JSONObject();
			jsonMap.put("templateURL", waybillTemplateURL);
			jsonMap.put("signature", UUID.randomUUID());
			jsonMap.put("data", dataJson);

			contents.put(jsonMap);
			contents.put(remarkJson);
			doc.put("contents", contents);
			documents.put(doc);
			order.setStatus(OrderStatusEnum.WMS_PRINT);
			order.setLastUpdateTime(new Date());

			/**
			 * 如果不是重打，则修改单据的状态
			 */
			if(StringUtils.isEmpty(type)){
				this.shipOrderService.updateShipOrder(order);
				tmsOrder.setOrderStatus(OrderStatusEnum.WMS_PRINT);
				this.tmsOrderService.updateTmsOrder(tmsOrder);
			}
			/**
			 * 打单操作日志
			 */
			
			tmsOrder.setOrder(order);
			saveOrderOperator(tmsOrder);
			
			logger.error(i+"云打处理时间:"+(new Date().getTime()-date.getTime()));
		}

		//System.err.println(documents.toString());
		resultMap.put("ret", "1");
		String result=documents.toString();
		//result=result.replaceAll("a_2", "templateURL");
		//result=result.replaceAll("a_1", "signature");
		resultMap.put("results", result);
		return resultMap;
	}
	
	/**
	 * 顺丰电子面单打印异步调用的实现
	 * @return
	 */
	@RequestMapping(value="sfAjax")
	public String sfAjax(ModelMap model){
		
		String idsObj=this.getString("ids");
		String cpCode=this.getString("cpCode");
		String type=this.getString("type","");
		model.put("ids", idsObj);
		
		model.put("cpCode", cpCode);
		
		model.put("batchCode", this.getbatchCode());
		model.put("type", type);
		return "admin/waybill/sf_ajax";
	}
	
	
	/**
	 * 异步批量取顺丰面单号
	 * @throws Exception 
	 * 
	 */
	@RequestMapping(value = "getPrintAjax")
	@ResponseBody
	public Map<String, Object> getSfPrintAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		
		String idsObj =(String) request.getParameter("ids");
		String batchCode=(String)request.getParameter("batchCode");
		Date date=new Date();
		//String cp_code = (String)request.getParameter("cpCode");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		
		/**
		 * 调用接口去取订单号，这里应该考虑一个问题。如果有一个订单取不到订单号，应该整批都不能进行打印。并且还需要给出具体的提示
		 * 修改为批量的取订单，用事务完成这一功能。
		 */
	
		if (idsObj != null) {
			String[] ids = idsObj.split(",");
//			try{
				resultMap=this.SfBillService.getSFBillNo(ids,batchCode);
//			}catch(Exception e ){
				/**
				 * 异常，描述出这批异常单据的具体情况。写出来
				 */
//				TmsOrder tmsOrder=this.tmsOrderService.getTmsOrderById(ids[0]);
//				ShipOrder shipOrder=this.shipOrderService.findShipOrderById(tmsOrder.getOrder().getId()); 
				//ShipOrder trade_end=this.tradeService.getShipOrder(Long.valueOf(ids[ids.length]));
				//resultMap.put("msg",trade.getReceiverName()+"-"+trade_end.getReceiverName()+"没有取得运单号!["+ids+"]");
//				resultMap.put("code", 500);
//				e.printStackTrace();
				
//			}
		}
		if(resultMap!=null && String.valueOf(resultMap.get("code")).equals("200") ){
			/**
			 * 取完订单号，查询构建数据进行打印
			 */
			List<Map<String, String>> tradeList = new ArrayList<Map<String,String>>();
//			buildSfPrintData(idsObj.split(","), tradeList);
//			resultMap.put("data", tradeList);
			resultMap.put("code",200);
			logger.debug("面单打印:此批单据取号所花时间:"+(new Date().getTime()-date.getTime()));
		}else{
			resultMap.put("code", 500);
		}
		System.err.println("sfMap:"+resultMap);
		return resultMap;
	}
	/**
	 * 取顺丰电子面单的打印数据
	 * @return
	 */
	@RequestMapping(value="getSfPrint")
	@ResponseBody
	public Map<String,Object> getSfPrint(){
		String idsObj =this.getString("ids");
		String type=this.getString("type");
		String batchCode=this.getbatchCode();
		List<Map<String, String>> tradeList = new ArrayList<Map<String,String>>();
		Map<String,Object> resultMap=new HashMap<String, Object>();
		buildSfPrintData(idsObj.split(","), tradeList,batchCode,type);
		/**
		 * 顺丰打印日志
		 */
		createSFPrintOperator(idsObj.split(","));
		logger.error("顺丰打印:"+tradeList);
		System.err.println("顺丰打印:"+tradeList);
		resultMap.put("data", tradeList);
		resultMap.put("code", "200");
		return resultMap;
	}

	/**
	 * 顺丰打印日志
	 * @param ids
	 */
	private void createSFPrintOperator(String[] ids) {
		for(int i=0; i<ids.length; i++){
			TmsOrder tmsOrder = this.tmsOrderService.getTmsOrderById(ids[i]); 
			ShipOrderOperator record=new ShipOrderOperator();
			record.generateId();
			record.setOperatorModel(OperatorModel.TRADE_PRINT);
			ShipOrder shipOrder = this.shipOrderService.findShipOrderById(tmsOrder.getOrder().getId());
			record.setDescription(shipOrder.getOrderCode()+"顺丰打印："+tmsOrder.getCode()+":"+tmsOrder.getOrderCode());
			record.setAccount((Account)SessionUser.get());
			record.setOperatorDate(new Date());
//			record.setCu("");
			record.setNewValue(tmsOrder.getCode()+":"+tmsOrder.getOrderCode());
			record.setOldValue(tmsOrder.getId());
			record.setShipOrder(tmsOrder.getOrder());
			this.shipOrderOperatorService.saveShipOrderOperator(record);
		}
	}

	private JSONArray buildSfPrintData(String[] ids, List<Map<String, String>> tradeList,String batchCode,String type) {
 
		JSONArray array=new JSONArray();
 
		for (int i = 0, size = ids.length; i < size; i++) {
			logger.error("buildSfPrintData:"+ids[i]);
			System.err.println(ids[i]);
			TmsOrder tmsOrder=this.tmsOrderService.getTmsOrderById(ids[i]);
			ShipOrder shipOrder = this.shipOrderService.findShipOrderById(tmsOrder.getOrder().getId());
			if(shipOrder.getTmsOrderCode()==null || shipOrder.getTmsOrderCode().length()==0){
				//判断，如果订单号为空，则不输出打前台
				continue;
			}
			
			String name;
			
			Centro centro = this.centroService.getCentroById(shipOrder.getCu());
			
			User user = userService.getUserById(shipOrder.getUser().getId());
			
			ReceiverInfo receiverInfo = this.receiverInfoService.getReceiverInfoById(shipOrder.getReceiverInfo().getId());
			
			SenderInfo senderInfo = this.senderInfoService.getSenderInfoById(shipOrder.getSenderInfo().getId());
			
			String address = receiverInfo.getReceiverProvince() + "," + receiverInfo.getReceiverCity() + ","
					+ receiverInfo.getReceiverArea();
			
			String address1 = receiverInfo.getReceiverProvince() + receiverInfo.getReceiverCity() +
					receiverInfo.getReceiverArea()+receiverInfo.getReceiverAddress();
			
			if(user!=null && user.getId()==otherUser){
				name = receiverInfo.getReceiverName();
			}else{
			 name = receiverInfo.getReceiverName() + " "
					+ (receiverInfo.getReceiverNick() == null ? "" : receiverInfo.getReceiverNick());
			}
			
			String phone = receiverInfo.getReceiverMobile() + " "
					+ (receiverInfo.getReceiverPhone() == null ? "" : receiverInfo.getReceiverPhone());
			
			Map<String, String> map = new HashMap<String, String>();
			//JSONObject json=new JSONObject();
			
		    //上方运单号 条形码
//		   	    LODOP.ADD_PRINT_BARCODE(65,80,161,50,"128C","444 835 934 440");
			map.put("sf_orderno", tmsOrder.getOrderCode());
			
			map.put("sf_selforderno", shipOrder.getErpOrderCode());
		   
			//目的地
			map.put("sf_destcode", tmsOrder.getRouteCode());
			
			DecimalFormat df=new DecimalFormat("######0.00");
			Double weight = (tmsOrder.getPackageWeight());
			Double totalWeight = weight/1000;
			map.put("weight", df.format(totalWeight)+"KG");
			
		    //收货地址
			map.put("sf_destaddress", address);
			
			//详细地址
			map.put("sf_destdetailaddress", address1);
		    
		    //收货人  和 收货电话号码
			map.put("sf_destname", name+" "+phone);
			
			SimpleDateFormat smf  = new SimpleDateFormat("MM月dd日");
			String format = smf.format(new Date());	
			map.put("sf_date", format);
		    //代收货款
		    //卡号
		    //运费
		    //费用合计
			
			//寄件信息
			if(user!=null && user.getId()==otherUser){
				
				String  caddres =  centro.getProvince()+centro.getCity()+centro.getArea();
				String detailAddress  = centro.getSfAddress().replaceAll(caddres, "");
				String items = this.shipOrderService.bulidItemsDataByTms(tmsOrder);
				
				if(items.indexOf("AF")!=-1){
					map.put("sf_sellername", " 傲风AutoFull   4000929959" );
				}else{
					map.put("sf_sellername", user.getSubscriberName()+" "+(user.getSubscriberMobile()!=null ?user.getSubscriberMobile():"") );
				}
				
				map.put("sf_selleraddress", "");
				
				map.put("sf_sellerdetailaddress", "北京");
				
				//目的地
				map.put("sf_sellercode", senderInfo.getSenderMobile());
				
			}else if(StringUtils.isNotBlank(shipOrder.getSellerMessage()) && shipOrder.getSellerMessage().indexOf("环球捕手")!=-1){
				map.put("sf_sellername", "环球捕手4001603602" );
				map.put("sf_selleraddress", "");
				map.put("sf_sellerdetailaddress", "环球捕手");
				map.put("sf_sellercode", senderInfo.getSenderMobile());
			}else if(StringUtils.isNotBlank(shipOrder.getSellerMessage()) && (shipOrder.getSellerMessage()).indexOf("脉宝云店")!=-1){
				map.put("sf_sellername", "脉宝云店4001116789" );
				map.put("sf_selleraddress", "");
				map.put("sf_sellerdetailaddress", "脉宝云店");
				map.put("sf_sellercode", senderInfo.getSenderMobile());
			}else if("idzc18000597278".equals(shipOrder.getUser().getId())){
				map.put("sf_sellername", "果佳果园"+" "+ "028-88606756");
				map.put("sf_selleraddress", "");
				map.put("sf_sellerdetailaddress", "四川省成都市蒲江县西来镇");
				map.put("sf_sellercode", senderInfo.getSenderMobile());
			}else if("idzc660824712".equals(shipOrder.getUser().getId())){
				map.put("sf_sellername", shipOrder.getShopName()+" "+user.getSubscriberMobile());
				map.put("sf_selleraddress", "");
				map.put("sf_sellerdetailaddress", "湖南省湘潭市岳塘区双马镇金钢人防");
				map.put("sf_sellercode", senderInfo.getSenderMobile());
			}else{
				
				String  caddres =  centro.getProvince()+centro.getCity()+centro.getArea();
				
				String detailAddress  = centro.getSfAddress().replaceAll(caddres, "");
				
				map.put("sf_sellername", user.getSubscriberName()+" "+(user.getSubscriberMobile()!=null ?user.getSubscriberMobile():"") );
//				System.err.println("user:"+user.getShopName());
//				map.put("sf_sellername", user.getShopName());
				
				map.put("sf_selleraddress", caddres);
				
				map.put("sf_sellerdetailaddress", "湖南省湘潭市岳塘区双马镇金钢人防");
			
				//目的地
				map.put("sf_sellercode", senderInfo.getSenderMobile());
//				System.err.println("sf_sellername:"+map.get("sf_sellername"));
			}
		   
			//下收方信息
			/**
			 * modify 2017-09-18 fufangue
			 * 世派和现代不打印备注
			 * 根据items是否为空，打印明细
			 * */
			if (!(" idzc1674590543".equals(shipOrder.getUser().getId()))&&!("idzc16473350928".equals(shipOrder.getUser().getId()))&&!("idzc16473350920".equals(shipOrder.getUser().getId()))&&!("idzc16473350942".equals(shipOrder.getUser().getId()))) {
 				if (StringUtils.isEmpty(tmsOrder.getItems())) {
					map.put("sf_selleritems", this.shipOrderService.bulidItemsDataByTms(tmsOrder));
				}else if (shipOrder.getSellerMessage()!=null &&  (shipOrder.getSellerMessage()).indexOf("闪电发货")!=-1) {
					map.put("sf_selleritems", tmsOrder.getItems()+" 卖家："+shipOrder.getSellerMessage());
				}else {
					map.put("sf_selleritems", tmsOrder.getItems());
				}
			}else {
 				if (StringUtils.isEmpty(tmsOrder.getItems())) {
					map.put("sf_selleritems", this.shipOrderService.bulidItemsDataByTms(tmsOrder)+"备注："+(shipOrder.getSellerMessage()!=null ?shipOrder.getSellerMessage():""));
				}else if (shipOrder.getSellerMessage()!=null && (shipOrder.getSellerMessage()).indexOf("闪电发货")!=-1) {
					map.put("sf_selleritems", tmsOrder.getItems()+" 卖家："+shipOrder.getSellerMessage());
				}else {
					map.put("sf_selleritems", tmsOrder.getItems()+"备注："+(shipOrder.getSellerMessage()!=null ?shipOrder.getSellerMessage():""));
				}
			}
		   
			map.put("userId", user.getId().toString());
		   
			//原寄地CODE
 			tradeList.add(map);
			array.put(map);
			
			logger.debug("account:"+((Account)SessionUser.get()).getUserName());
			/**
			 * 写操作记录，写打印批次号
			 */
			if(StringUtils.isEmpty(type)){
				Map<String,Object> p=new HashMap<String, Object>();
				p.put("orderId", shipOrder.getId());
				p.put("orderStatus", OrderStatusEnum.WMS_GETNO);
				List<TmsOrder> tmsList=this.tmsOrderService.getTmsOrderByList(p);
				/**
				 * 最后一个包裹面单打印的时候修改主单据的状态
				 */
				if(tmsList!=null && tmsList.size()>1){
					tmsOrder.setOrderStatus(OrderStatusEnum.WMS_PRINT);
					this.tmsOrderService.updateTmsOrder(tmsOrder);
				}else{
					shipOrder.setStatus(OrderStatusEnum.WMS_PRINT);
					shipOrder.setLastUpdateTime(new Date());
					shipOrder.setBatchCode(batchCode);
					this.shipOrderService.updateShipOrder(shipOrder);
					tmsOrder.setOrderStatus(OrderStatusEnum.WMS_PRINT);
					this.tmsOrderService.updateTmsOrder(tmsOrder);
				}
			}
			
		}
		return array;
	}
	
	/**
	 * 快递打印(预览打印)
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	@RequestMapping(value = "preview")
	public String preview(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception, IOException {
		
		Object idsObj = request.getParameter("ids");
		String type=request.getParameter("type");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean  tradeBatchFlag  =  false;
		
		if (idsObj != null) {
			String[] ids = ("" + idsObj).split(",");
			List<Map<String, String>> tradeList = null;
			for (int i = 0, size = ids.length; i < size; i++) {
				ShipOrder shipOrder = this.shipOrderService.findShipOrderById(ids[i]);
				if(i==0  && "SF".equals(shipOrder.getTmsOrderCode())){
					if(type==null || type.trim().length()==0){
						return "redirect:sfpreview?ids="+idsObj;
					}else{
						return "redirect:sfpreview?type=print&ids="+idsObj;
					}
				}
					
//				
//				if(!tradeBatchFlag && shipOrder.getShipOrderBatchId()!=null){
//					tradeBatchFlag  = true;
//				}
				Centro centro = this.centroService.getCentroById(shipOrder.getCu());

				User user = this.userService.getUserById(shipOrder.getUser().getId());
				
				ReceiverInfo receiverInfo = this.receiverInfoService.getReceiverInfoById(shipOrder.getReceiverInfo().getId());
				
				SenderInfo senderInfo = this.senderInfoService.getSenderInfoById(shipOrder.getSenderInfo().getId());
				
				String address = receiverInfo.getReceiverProvince() + " " + receiverInfo.getReceiverCity() + " "
						+ receiverInfo.getReceiverArea() + " " + receiverInfo.getReceiverAddress();
				address=this.getHtml(address);
				
				String buyNick=(receiverInfo.getReceiverNick()==null?"":("("+receiverInfo.getReceiverNick()+")"));
				
				String name = receiverInfo.getReceiverName()+buyNick;
				
				String phone = receiverInfo.getReceiverMobile() + " "
						+ (receiverInfo.getReceiverPhone() == null ? "" : receiverInfo.getReceiverPhone());
				
				DecimalFormat df = new DecimalFormat("######0.00");   
				Double weight = Double.valueOf(String.valueOf(this.shipOrderService.buildWeight(shipOrder)));
				Double totalWeight = weight/1000;
				
				String goodsInfoItems = shipOrder.getItems();
				
				if (shipOrder.getUser().getId()=="28") {
					String meno=(shipOrder.getSellerMessage()!=null ?shipOrder.getSellerMessage():"" )+(shipOrder.getBuyerMessage()!=null ?shipOrder.getBuyerMessage():"" );
					goodsInfoItems=goodsInfoItems+" 备注："+(meno.replaceAll(" ", "").replaceAll("[\\t\\n\\r]", ""));
				}
				
				if (i == 0) {
					
					model.addAttribute("user_id", userService.getUserById(shipOrder.getUser().getId()).getTbUserId());
					
					model.addAttribute("app_key", StoreConstants.rookie_appkey);

					model.addAttribute("cp_code", shipOrder.getTmsServiceCode()); // 选择快递末班公司
					
					//EMS 的EYB是经济快递，其它都为标准快递
					if(shipOrder.getTmsServiceCode().equals("EYB")){
						
						model.addAttribute("ali_waybill_product_type", "经济快递");// 单据类型
						
						model.addAttribute("ali_waybill_package_center_name", "6283   "+senderInfo.getSenderMobile());// 集散地名称
					
					}else{
						if(shipOrder.getTmsServiceCode()!=null && shipOrder.getTmsServiceCode().length()>0){
							if(shipOrder.getTmsServiceCode().equals("YUNDA") && receiverInfo.getReceiverProvince()!=null && receiverInfo.getReceiverProvince().indexOf("湖南")!=-1){
								model.addAttribute("ali_waybill_product_type", "06 省内");// 单据类型
							}else{
								model.addAttribute("ali_waybill_product_type", shipOrder.getTmsServiceCode()+" 汽运");// 单据类型
							}
						}else{
							if (user.getId()=="18") {
								model.addAttribute("ali_waybill_product_type", "凯盈汽运");// 单据类型
							}else{
								model.addAttribute("ali_waybill_product_type", "汽运");// 单据类型
							}			
						}
						model.addAttribute("ali_waybill_package_center_name", senderInfo.getSenderMobile());// 集散地名称
					}
					
					model.addAttribute("ali_waybill_package_center_code", senderInfo.getSenderPhone());//集散地代码（卖家电话）
					
					model.addAttribute("ali_waybill_waybill_code", shipOrder.getTmsOrderCode());//快递代码（物流单号）
				
					model.addAttribute("ali_waybill_short_address", shipOrder.getOrderFlag());
				
					model.addAttribute("ali_waybill_package_center_code", senderInfo.getSenderPhone());
					
					model.addAttribute("ali_waybill_waybill_code", shipOrder.getTmsOrderCode());
					
					// model.addAttribute("ali_waybill_cod_amount",
					// "FKFS=到付;");// 服务
					model.addAttribute("ali_waybill_consignee_name", name);//收件人姓名
					
					model.addAttribute("ali_waybill_consignee_phone", phone);//收件人手机
					
					model.addAttribute("ali_waybill_consignee_address", address.replaceAll("/", "").replaceAll("\"", "").replaceAll("\\\\","").replaceAll("\'", ""));// 收件人地址
					
					//model.addAttribute("ali_waybill_consignee_address", address);// 收件人地址
					model.addAttribute("ali_waybill_send_name", user.getSubscriberName());//发件人姓名（店铺名）
					
					model.addAttribute("ali_waybill_send_phone", user.getSubscriberMobile());//发件人手机（客户编码）
					
					model.addAttribute("ali_waybill_shipping_address", "湖南省湘潭市岳塘区双马镇金钢人防   "+sdf.format(new Date())+"[允许先验收后签收]");//发件地址（仓库地址）
					
					model.addAttribute("goodsInfos", df.format(weight)+"kg "+"[1]"+goodsInfoItems+"    汽运 ");//商品明细信息

				} else {
					if (tradeList == null) {
						tradeList = new ArrayList<Map<String, String>>();
					}
					Map<String, String> map = new HashMap<String, String>();
					//EMS 的EYB是经济快递，其它都为标准快递
					if(shipOrder.getTmsServiceCode().equals("EYB")){
						
						map.put("ali_waybill_product_type", "经济快递");// 单据类型
						
						map.put("ali_waybill_package_center_name", "6283   "+senderInfo.getSenderPhone());// 集散地名称
					
					}else{
						if(shipOrder.getTmsServiceCode()!=null && shipOrder.getTmsServiceCode().length()>0){
							if(shipOrder.getTmsServiceCode().equals("YUNDA") &&  receiverInfo.getReceiverProvince()!=null && receiverInfo.getReceiverProvince().indexOf("湖南")!=-1){
								map.put("ali_waybill_product_type","06 省内");// 单据类型
							}else{
								map.put("ali_waybill_product_type", shipOrder.getTmsServiceCode()+" 汽运");// 单据类型
							}
						}else{
							if (user.getId()=="18") {
								model.addAttribute("ali_waybill_product_type", "凯盈汽运");// 单据类型
							}else{
								model.addAttribute("ali_waybill_product_type", "汽运");// 单据类型
							}		
						}
						map.put("ali_waybill_package_center_name", senderInfo.getSenderMobile());// 集散地名称
					}
					
					//map.put("ali_waybill_product_type", "标准快递");// 单据类型
					
					map.put("ali_waybill_short_address", shipOrder.getOrderFlag());
				//	map.put("ali_waybill_package_center_name", sendShipOrder.getSellerMobile());// 集散地名称
					
					map.put("ali_waybill_package_center_code", senderInfo.getSenderPhone());
					
					map.put("ali_waybill_waybill_code", "" + shipOrder.getTmsOrderCode());
					// map.put("ali_waybill_cod_amount", "FKFS=到付;");// 服务
					
					map.put("ali_waybill_consignee_name", name);
					
					map.put("ali_waybill_consignee_phone", phone);
					
					map.put("ali_waybill_consignee_address", address.replaceAll("/", "").replaceAll("\"", "").replaceAll("\\\\","").replaceAll("\'", ""));// 收件人地址
					//map.put("ali_waybill_consignee_address", address);// 收件人地址
					
					map.put("ali_waybill_send_name", user.getSubscriberName());
					
					map.put("ali_waybill_send_phone", user.getSubscriberMobile());
				//	map.put("ali_waybill_shipping_address", centro.getAddress()+"   "+sdf.format(new Date()));
					
					map.put("ali_waybill_shipping_address","湖南省湘潭市岳塘区双马镇金钢人防   "+sdf.format(new Date())+"[允许先验收后签收]");
					
					map.put("goodsInfos",df.format(weight)+"kg "+"["+(i+1)+"]"+goodsInfoItems +"    汽运");
					
					tradeList.add(map);
				}

				/**
				 * 添加打印日志
				 */
				ShipOrderOperator record=new ShipOrderOperator();
				record.setOperatorModel(OperatorModel.TRADE_PRINT);
				record.setDescription("订单打印！");
				record.setAccount((Account)SessionUser.get());
				record.setOperatorDate(new Date());
//				record.setCu("");
				record.setNewValue(shipOrder.getTmsServiceCode()+":"+shipOrder.getTmsOrderCode());
				record.setShipOrder(shipOrder);
				this.shipOrderOperatorService.saveShipOrderOperator(record);
				
			}

			if (tradeList != null) {
				if(tradeBatchFlag){ //更新 批次
					
				}
				model.addAttribute("tradeList", tradeList);
			}
		}
		if(type==null || type.trim().length()==0){
			return "admin/waybill/yunda_isvPreview";
		}else{
			return "admin/waybill/cainiao_new";
		}
		
	}

	/**
	 * 获取菜鸟面单号(预览打印)
	 * Map key tradeId 订单ID orderNo 面单号
	 */
	@RequestMapping(value = "ajax/getPrint")
	@ResponseBody
	public Map<String, Object> getPrint(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		Object idsObj = request.getParameter("ids");
		String cp_code = "" + request.getParameter("cp_code");
		
		//取批次号
		String batch=this.getbatchCode();
		
		List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
		if (idsObj != null) {
			String[] ids = ("" + idsObj).split(",");
			for (int i = 0, size = ids.length; i < size; i++) {
				ShipOrder shipOrder = this.shipOrderService.findShipOrderById(ids[i]);
				System.err.println("shipOrder:"+shipOrder);
				if (!"SF".equals(cp_code)) {
					/**
					 * 2017-03-09
					 * 菜鸟打印加入打印批次号的更新 batch
					 */
					Map<String, String> getMap = this.wayBillService.billGet(shipOrder, cp_code,String.valueOf(batch));
					if(!"getMap".equals("200")){
						retList.add(getMap);
					}
				} else {
					try{
//						retList.add(this.SfBillService.billSfGet(shipOrder,String.valueOf(batch)));
					}catch(Exception e){
						e.printStackTrace();
						logger.debug(e.getMessage());
					}
				}
			}
		}
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retList", retList);
		logger.debug("取订单号:"+retList);
		return retMap;
	}

	/**
	 * 取消电子面单
	 */
	@RequestMapping(value = "ajax/cancel")
	@ResponseBody
	public Map<String, Object> cancel(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception, IOException {
		Object idsObj = request.getParameter("ids");
		List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
		if (idsObj != null) {
			String[] ids = ("" + idsObj).split(",");
			for (int i = 0, size = ids.length; i < size; i++) {
				ShipOrder shipOrder = this.shipOrderService.findShipOrderById(ids[i]);
				retList.add(wayBillService.billCancel(shipOrder));
			}
		}
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retList", retList);
		System.err.println("订单取消:"+retList);
		return retMap;
	}
	/**
	 * 查询快递单状态
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="queryDetail")
	public Map<String,Object> queryDetail(HttpServletRequest request,ModelMap model){
		String code=request.getParameter("code");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.putAll(this.wayBillService.queryDetail(code));
		resultMap.put("ret", 1);
		return resultMap;
	}
	
	@RequestMapping(value="getPrintbatchCode")
	@ResponseBody
	public Map<String,Object> getPrintbatchCode(){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		String batchCode=getbatchCode();				
		resultMap.put("batchCode", String.valueOf(batchCode).split(","));
		resultMap.put("code", 200);
		return resultMap;
	}
	
	private synchronized String getbatchCode(){
		String timeStr = String.valueOf(new Date().getTime());
		String str = timeStr.replaceAll(",", "");
		String batchCode = str.substring(0, 11);
		return batchCode;
	}
	/**
	 * 格式化地址
	 * @param str
	 * @return
	 */
	public String getHtml(String str){
		if(str!=null && str.length()>0){
			str=str.replaceAll("(\r\n|\r|\n|\n\r)", "");
			if(str.indexOf("\"")>0){
				str=str.replaceAll("\"", "");
			}
			if(str.indexOf("'")>0){
				str=str.replaceAll("'", "");
			}
			return str;
		}
		return null;
	}
	
}
