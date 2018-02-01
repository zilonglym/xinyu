package com.xinyu.controller.qm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyu.common.BaseController;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.order.StockInOrder;
import com.xinyu.model.order.enums.InOrderStateEnum;
import com.xinyu.model.order.enums.InOrderTypeEnum;
import com.xinyu.model.system.SystemItem;
import com.xinyu.model.system.enums.OperatorModel;
import com.xinyu.model.system.enums.OrderStatusEnum;
import com.xinyu.model.system.enums.StoreSystemItemEnums;
import com.xinyu.model.system.enums.SystemSourceEnums;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderOperator;
import com.xinyu.model.trade.WmsConsignOrderItem;
import com.xinyu.service.order.StockInOrderService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.SystemItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.trade.ReceiverInfoService;
import com.xinyu.service.trade.ShipOrderOperatorService;
import com.xinyu.service.trade.ShipOrderService;
import com.xinyu.service.trade.WmsConsignOrderItemService;

@Controller
@RequestMapping(value = "qm/shipOrder")
public class QMShipOrderController extends BaseController{
	@Autowired
	private ShipOrderService shipOrderService;
	@Autowired
	private ShipOrderOperatorService shipOrderOperatorService;
	@Autowired
	private WmsConsignOrderItemService orderItemService;
	@Autowired
	private StockInOrderService stockInOrderService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private UserService userService;
	@Autowired
	private SystemItemService sysService;
	@Autowired
	private ReceiverInfoService receiverInfoService;
	/**
	 * 入库单列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="stockInOrderList")
	public String stockInOrderList(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		model.put("orderTypes", InOrderTypeEnum.values());
		model.put("orderStatuses", InOrderStateEnum.values());
		return "admin/qm/stockInOrderList";
	}
	
//	@RequestMapping(value="stockInOrderListData")
//	@ResponseBody
//	public Map<String, Object> stockInOrderListData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
//		if (rows==10) {
//			rows=100;
//		}
//		String userId = request.getParameter("userId");
//		String searchText = request.getParameter("searchText");
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("userId", userId);
//		params.put("searchText", searchText);
//		params.put("type", "QM");
//		List<StockInOrder> stockInOrders = this.stockInOrderService.findStockInOrderByPage(params, page, rows);
//		int total = this.stockInOrderService.getTotal(params);
//		Map<String, Object> retMap = new HashMap<String, Object>();
//		retMap.put("rows", this.stockInOrderService.buildListData(stockInOrders));
//		retMap.put("total", total);
//		retMap.put("page", page);
//		return retMap;
//	}
	
	/**
	 * 出库单列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="stockoutList")
	public String stockoutList(HttpServletRequest request,HttpServletResponse response,ModelMap model){
//		//默认查询所有的未完成入库单
//		Map<String,Object> params=new HashMap<String,Object>();
//		params.put("status", SendOrderStatus.WAIT_EXPRESS_RECEIVED);
//		params.put("type", ShipOrder.TYPE_SEND);
//		List<ShipOrder> orderList=this.shipOrderService.selectShipOrderByList(params);
//		model.put("orders", orderList);
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		model.put("orderTypes", InOrderTypeEnum.values());
		model.put("orderStatuses", InOrderStateEnum.values());
		return "admin/qm/stockOutOrderList";
	}
	/**
	 * 标记完成
	 * @return
	 */
	@RequestMapping(value="submitOk")
	@ResponseBody
	public Map<String,Object> submitOk(HttpServletRequest request){
		String id=request.getParameter("id");
		ShipOrder order=this.shipOrderService.findShipOrderById(id);
		String oldValue = order.getStatus().getKey();
		order.setStatus(OrderStatusEnum.WMS_FINASH);
		this.shipOrderService.updateShipOrder(order);
		//生成日志
		ShipOrderOperator record=new ShipOrderOperator();
		record.generateId();
		record.setOldValue(oldValue);
		record.setNewValue(OrderStatusEnum.WMS_FINASH.getKey());
		record.setCu("");
		record.setDescription("奇门订单发货标记完成!");
		record.setOperatorDate(new Date());
		record.setOperatorModel(OperatorModel.TRADE_CONFIRM);
		record.setShipOrder(order);
		record.setAccount(this.getCurrentAccount());
		this.shipOrderOperatorService.saveShipOrderOperator(record);
		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("ret", 1);
		return resultMap;
	}
	/**
	 * 单据详情
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="detailInfo/{id}")
	public String shipOrderDetailInfo(HttpServletRequest request,ModelMap model,@PathVariable String id){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("orderId", id);
		List<WmsConsignOrderItem> detailList=this.orderItemService.getWmsConsignOrderItemByList(params);
		for(int i=0;detailList!=null && i<detailList.size();i++){
			WmsConsignOrderItem detail=detailList.get(i);
			String itemId=detail.getItem().getId();
			if(itemId.isEmpty()){
				itemId="1";
			}
			Item item=this.itemService.getItem(itemId);
			detail.setItem(item);
//			detail.setInventoryType(processinventoryType(detail.getInventoryType()));
		}
		ShipOrder order=this.shipOrderService.findShipOrderById(id);
		
		String userId=order.getUser().getId();
		User user=userService.getUserById(userId);
		order.setUser(user);
		
		model.put("order", order);
		model.put("list", detailList);
		return "store/list/shipOrderDetailInfo";
	}
	
	
	/**
	 * 处理商品库存类型
	 * @param inventoryType
	 * @return
	 */
	private String processinventoryType(String inventoryType){
		if(inventoryType==null || inventoryType.length()==0){
			return "正品";
		}
		if(inventoryType.equals("ZP")){
			return "正品";
		}
		if(inventoryType.equals("CC")){
			return "残次";
		}
		if(inventoryType.equals("JS")){
			return "机损";
		}
		if(inventoryType.equals("XS")){
			return "箱损";
		}
		
		return "";
		
	}
	
	
	
	/**
	 *  分单
	 * */
	@RequestMapping(value="f7Split")
	public String f7Split(HttpServletRequest request,ModelMap model){
		Map<String,Object> params=new HashMap<String,Object>();
		String id=request.getParameter("id");
		ShipOrder shipOrder = shipOrderService.findShipOrderById(id);		
//		Person person=this.personService.findPersonByQ(params);
		model.put("shipOrder",shipOrder);
		params.put("orderId", id);
		List<WmsConsignOrderItem> orderItems = this.orderItemService.getWmsConsignOrderItemByList(params);	
		List<Object>  list  = new ArrayList<Object>();
		for(int i  = 0  ,size  = orderItems.size();i<size;i++  ){
			WmsConsignOrderItem orderItem = orderItems.get(i);
			Map<String,Object>  map  =  new HashMap<String,Object>();
			map.put("id", orderItem.getId());
			map.put("num", orderItem.getItemQuantity());
			map.put("item",itemService.getItem(orderItem.getItem().getId()));
			list.add(map);
		}
		model.put("details",list);
//		List<Centro> centroList=this.centroService.findCentros();
//		model.put("centroList",centroList);
		return "shipOrder/Splitf7ShipOrder";
	}
	
	/**
	 *  分单保存 
	 *  数量拆单
	 * */
	@RequestMapping(value="splitShipOrder")
	@ResponseBody
	public Map<String,Object> splitShipOrder(HttpServletRequest request,ModelMap model){
		String  jsonStr  =  request.getParameter("json");
		JSONObject json=new JSONObject(jsonStr.substring(1,jsonStr.length()-1));
        JSONArray date= json.getJSONArray("date");
		HashMap<String,Object> param = new HashMap<String, Object>();
		param.put("tradeId", Long.valueOf(""+json.get("id")));
		List<Map<String,Long>> list  = new ArrayList<Map<String,Long>>() ;
	    Map<String, Object> retMap  = new HashMap<String,Object>();
		try {
			for(int i = 0, size    = date.length();i<size ;i++ ){
		        	Map<String,Long> map  = new HashMap<String,Long>();
		        	JSONObject obj=date.getJSONObject(i);
		        	Long detailId  =  Long.valueOf(""+obj.get("detailId"));
		        	map.put("detailId", detailId);
		        	Long quantity  = null;
		        	try {
		        		quantity  = Long.valueOf(""+obj.get("quantity"));
					} catch (Exception e) {
						quantity  =  Long.valueOf("0");
					}
		        	if(quantity!=0){
		        		map.put("quantity", quantity);
		        		list.add(map);
		        		param.put(""+detailId, quantity);
		        	}
		        }
		} catch (Exception e) {
			System.err.println(e.getMessage());
			retMap.put("code", "200");
        	retMap.put("msg", "数量填写异常");
			return retMap;
		}
      
        param.put("detailList", list);
        
        if(list.size()>0){
        	retMap = shipOrderService.splitShipOrder(param);
        }else{
        	retMap.put("code", "404");
        	retMap.put("msg", "无法识别有效返单数量");
        }
        System.err.println("拆单结果:"+retMap.get("code")+"|"+retMap.get("msg"));
        System.out.println("拆单结果:"+retMap.get("code")+"|"+retMap.get("msg"));
        return retMap ;
	}
	
	/**
	 *  分单保存 
	 *  行数拆单
	 * */
	@RequestMapping(value="splitShipOrderLine")
	@ResponseBody
	public Map<String,Object> splitShipOrderLine(HttpServletRequest request,ModelMap model){
		String  jsonStr  =  request.getParameter("json");
		JSONObject json=new JSONObject(jsonStr.substring(1,jsonStr.length()-1));
        JSONArray date= json.getJSONArray("date");
		HashMap<String,Object> param = new HashMap<String, Object>();
		param.put("tradeId", Long.valueOf(""+json.get("id")));
		List<Map<String,Long>> list  = new ArrayList<Map<String,Long>>() ;
	    Map<String, Object> retMap  = new HashMap<String,Object>();
		for (int i = 0, size = date.length(); i < size; i++) {
			Map<String, Long> map = new HashMap<String, Long>();
			JSONObject obj = date.getJSONObject(i);
			Long detailId = Long.valueOf("" + obj.get("detailId"));
			param.put(""+detailId, detailId);
			list.add(map);
		}
        
        if(list.size()>0){
        	param.put("type", "line");
        	retMap = shipOrderService.splitShipOrder(param);
        }else{
        	retMap.put("code", "404");
        	retMap.put("msg", "无法识别有效返单数量");
        }
        System.err.println("拆单结果:"+retMap.get("code")+"|"+retMap.get("msg"));
        System.out.println("拆单结果:"+retMap.get("code")+"|"+retMap.get("msg"));
        return retMap ;
	}
	
	
//	/**
//	 *  分单保存
//	 * */
//	@RequestMapping(value="splitShipOrder")
//	@ResponseBody
//	public Map<String,Object> splitShipOrder(HttpServletRequest request,ModelMap model){
//		String  jsonStr  =  request.getParameter("json");
//		System.err.println(jsonStr.substring(1,jsonStr.length()-1));
//		JSONObject json=new JSONObject(jsonStr.substring(1,jsonStr.length()-1));
//        JSONArray date= json.getJSONArray("date");
//		HashMap<String,Object> param = new HashMap<String, Object>();
//		param.put("id", Long.valueOf(""+json.get("id")));
//		List<Map<String,Long>> list  = new ArrayList<Map<String,Long>>() ;
//        for(int i = 0, size    = date.length();i<size ;i++ ){
//        	Map<String,Long> map  = new HashMap<String,Long>();
//        	JSONObject obj=date.getJSONObject(i);
//        	Long detailId  =  Long.valueOf(""+obj.get("detailId"));
//        	map.put("detailId", detailId);
//        	Long quantity  = null;
//        	try {
//        		quantity  = Long.valueOf(""+obj.get("quantity"));
//			} catch (Exception e) {
//				quantity  =  Long.valueOf("0");
//			}
//        	if(quantity!=0){
//        		map.put("quantity", quantity);
//        		list.add(map);
//        	}
//        }
//        param.put("detailList", list);
//        Map<String, Object> retMap  = new HashMap<String,Object>();
//        if(list.size()>0){
//        	retMap = shipOrderService.splitShipOrder(param);
//        }else{
//        	retMap.put("code", "404");
//        	retMap.put("msg", "无法识别有效返单数量");
//        }
//        System.err.println("拆单结果:"+retMap.get("code")+"|"+retMap.get("msg"));
//        System.out.println("拆单结果:"+retMap.get("code")+"|"+retMap.get("msg"));
//        return retMap ;
//	}
}
