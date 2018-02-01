package com.graby.store.admin.web.qm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.admin.util.BaseResource;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrder.EntryOrderStatus;
import com.graby.store.entity.ShipOrder.SendOrderStatus;
import com.graby.store.entity.enums.OperatorModel;
import com.graby.store.entity.enums.OrderStatusEnums;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.SystemOperatorRecord;
import com.graby.store.entity.Trade;
import com.graby.store.entity.User;
import com.graby.store.remote.InventoryRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.ShipOrderRemote;
import com.graby.store.remote.SystemItemRemote;
import com.graby.store.remote.SystemOperatorRecordRemote;
import com.graby.store.remote.UserRemote;
import com.graby.store.service.inventory.Accounts;

@Controller
@RequestMapping(value = "/store/shipOrder")
public class ShipOrderController extends QMBaseController{
	
	public static final Logger logger = Logger.getLogger(ShipOrderController.class);
	@Autowired
	private ShipOrderRemote shipOrderRemote;
	@Autowired
	private ItemRemote itemRemote;
	@Autowired
	private UserRemote userRemote;
	@Autowired
	private SystemItemRemote sysRemote;
	@Autowired
	private SystemOperatorRecordRemote operatorRemote;
	@Autowired
	private InventoryRemote inventoryRemote;
	/**
	 * 入库单列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="entryOrderList")
	public String entryOrderList(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		//默认查询所有的未完成入库单
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("status", EntryOrderStatus.ENTRY_WAIT_STORAGE_RECEIVED);
		params.put("type", ShipOrder.TYPE_ENTRY);
		List<ShipOrder> orderList=this.shipOrderRemote.selectShipOrderByList(params);
		model.put("orders", orderList);
		return "store/list/entryOrderList";
	}
	
	/**
	 * 入库单列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="stockoutList")
	public String stockoutList(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		//默认查询所有的未完成入库单
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("status", SendOrderStatus.WAIT_EXPRESS_RECEIVED);
		params.put("type", ShipOrder.TYPE_SEND);
		List<ShipOrder> orderList=this.shipOrderRemote.selectShipOrderByList(params);
		model.put("orders", orderList);
		return "store/list/stockoutList";
	}
	/**
	 * 发货单列表,这里只显示奇门同步过来的单据
	 * @param model
	 * @return
	 */
	@RequestMapping(value="deliveryorderList")
	public String deliveryorderList(ModelMap model){
		//默认查询所有的未完成发货单
		List<User> users=userRemote.findUsers();
		String type="waybill";
		List<SystemItem> companys=sysRemote.findSystemItemByType(type);
		model.addAttribute("companys", companys);
		model.addAttribute("users", users);
		return "store/list/deliveryorderList";
	}
	
	/**
	 * 发货单列表数据填充
	 * @param row
	 * @param page
	 * @param request
	 * @return map
	 */
	@RequestMapping(value="deliveryorderList/listData")
	@ResponseBody
	public Map<String,Object> deliveryorderListData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
		if (rows==10) {
			rows=100;
		}
		String status=request.getParameter("status");
		String userId=request.getParameter("selectUser");
		String company=request.getParameter("selectCompany");
		String q=request.getParameter("q");
		String beigainTime=request.getParameter("beigainTime");
		String lastTime=request.getParameter("lastTime");
		if(status==null || status.length()<=0){
			status=OrderStatusEnums.WMS_PRINT.getKey();
		}
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("status", status);
		params.put("userId", userId);
		params.put("company", company);
		params.put("q", q);
		params.put("beigainTime", beigainTime);
		params.put("lastTime", lastTime);
		params.put("type", ShipOrder.TYPE_DELIVER);
		/**
		 * 这里只显示奇门同步过来的单据
		 */
		params.put("source", Trade.SourceFlag.SourceFlag_QM); //这里只显示奇门同步过来的单据
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<ShipOrder> orderList=this.shipOrderRemote.findeSendOrderByList(params,page,rows);
		int total=this.shipOrderRemote.getSendOrderCount(params);
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		for(ShipOrder shipOrder:orderList){
			Map<String,Object> map=new HashMap<String, Object>();
			User user=userRemote.getUser(shipOrder.getCreateUser().getId());
			shipOrder.setOriginPersion(user.getShopName());
			map.put("id", shipOrder.getId());
			if (shipOrder.getSellerNick()!=null&&shipOrder.getSellerNick()!="") {
				map.put("originPersion", shipOrder.getSellerNick());
			}else {
				map.put("originPersion", user.getShopName());
			}
			map.put("orderno", shipOrder.getOrderno());
			map.put("orderType", shipOrder.getOrderType());
			map.put("buyerNick", shipOrder.getBuyerNick());
			map.put("receiverName", shipOrder.getReceiverName());
			map.put("expressCompany", shipOrder.getExpressCompany());
			map.put("expressOrderno", shipOrder.getExpressOrderno());
			map.put("lastDate", format.format(shipOrder.getLastUpdateDate()));
			map.put("status", shipOrder.getStatus());
			map.put("items", shipOrder.getItems());
			resultList.add(map);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("page",page);
		resultMap.put("total",total);
		resultMap.put("rows",resultList);
		return resultMap;
	}
	
	/**
	 * 标记完成
	 * @return
	 */
	@RequestMapping(value="submitOk")
	@ResponseBody
	public Map<String,Object> submitOk(HttpServletRequest request){
		String id=request.getParameter("id");
		ShipOrder order=this.shipOrderRemote.getShipOrder(Long.valueOf(id));
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("id", order.getId());
		params.put("status", ShipOrder.EntryOrderStatus.WMS_FINISH);
		params.put("lastUpdate", "n");
	
		
		Map<String,String> memcachedMap=new HashMap<String, String>();
		int zcIndex=0,zpIndex=0;
		for(int i=0;i<order.getDetails().size();i++){
			ShipOrderDetail detail=order.getDetails().get(i);
			Item item=detail.getItem();
			memcachedMap.put(item.getBarCode(), item.getId()+"||"+detail.getNum());
			if(item!=null && item.getItemType()!=null && item.getItemType().equals("ZC")){
				zcIndex+=detail.getNum();
			}
			if(item!=null && item.getItemType()!=null && item.getItemType().equals("ZP")){
				zpIndex+=detail.getNum();
			}
		}
		logger.error("标记完成写入缓存数据!"+order.getExpressOrderno());
		memcachedMap.put("zcIndex", String.valueOf(zcIndex));
		memcachedMap.put("zpIndex", String.valueOf(zpIndex));
		memcachedMap.put("orderInfo", order.getId()+"||"+order.getTotalWeight()+"||"+order.getCreateUser().getId());
		this.saveMemcached(order.getExpressOrderno().trim(), memcachedMap);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("ret", 1);
		SystemOperatorRecord record=new SystemOperatorRecord();
		record.setOperator(BaseResource.getCurrentUser().getId().intValue());
		record.setDescription("标记完成");
		record.setTime(new Date());
		record.setIpaddr(order.getExpressOrderno());
		record.setUser(order.getCreateUser()!=null ?order.getCreateUser().getId().intValue():0);
		record.setOperatorModel(OperatorModel.QM_TRADE_CONFIRM);
		record.setStatus("Y");
		this.operatorRemote.insert(record);	
		
		this.shipOrderRemote.updateShipOrderStatus(params);
		
		/**
		 * modify 2017-09-07
		 * @author fufangjue
		 * 订单发货标记完成修改库存（分单拆单）
		 * */
		if (order.getSourcePlatformCode().equals(ShipOrder.SplitStatus.split_detail)) {
			logger.debug("拆单标记完成库存修改！");
			this.itemInventoryHandle(order);
		}
			
		return resultMap;
	}
	
	
	private void itemInventoryHandle(ShipOrder shipOrder) {
		List<ShipOrderDetail> detailList=this.shipOrderRemote.getShipOrderDetailByOrderId(shipOrder.getId());
		for(ShipOrderDetail detail :detailList){
			 logger.debug("qm item inventory:"+detail.getItem().getId()+"|"+detail.getNum());
			 this.inventoryRemote.addInventory(Long.valueOf(BaseResource.getCurrentCentroId()), shipOrder.getCreateUser().getId(), detail.getItem().getId(), -detail.getNum(), Accounts.CODE_SALEABLE, "确认出库", shipOrder.getExpressOrderno());
			 this.inventoryRemote.updateUserNum(Long.valueOf(BaseResource.getCurrentCentroId()),  detail.getItem().getId(), Accounts.CODE_SALEABLE, -detail.getNum() );
		}
	}

	/**
	 * 单据详情
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="detailInfo/{id}")
	public String shipOrderDetailInfo(HttpServletRequest request,ModelMap model,@PathVariable int id){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("shipOrderId", id);
		List<ShipOrderDetail> detailList=this.shipOrderRemote.shipOrderDetailbyList(params);
		for(int i=0;detailList!=null && i<detailList.size();i++){
			ShipOrderDetail detail=detailList.get(i);
			long itemId=detail.getItem().getId();
			if(itemId==0){
				itemId=1;
			}
			Item item=this.itemRemote.getItem(itemId);
			detail.setItem(item);
			detail.setInventoryType(processinventoryType(detail.getInventoryType()));
		}
		ShipOrder order=this.shipOrderRemote.getShipOrder(Long.valueOf(id));
		
		Long userId=order.getCreateUser().getId();
		User user=userRemote.getUser(userId);
		order.setCreateUser(user);
		
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
		ShipOrder shipOrder = shipOrderRemote.getShipOrder(Long.valueOf(id));
		
		
//		Person person=this.personRemote.findPersonByQ(params);
		model.put("shipOrder",shipOrder);
		List<ShipOrderDetail> shipOrderDetailByOrderId = shipOrderRemote.getShipOrderDetailByOrderId(Long.valueOf(id));
		
		List<Object>  list  = new ArrayList<Object>();
		for(int i  = 0  ,size  = shipOrderDetailByOrderId.size();i<size;i++  ){
			ShipOrderDetail shipOrderDetail = shipOrderDetailByOrderId.get(i);
			Map<String,Object>  map  =  new HashMap<String,Object>();
			map.put("id", shipOrderDetail.getId());
			map.put("num", shipOrderDetail.getNum());
			map.put("item",itemRemote.getItem(shipOrderDetail.getItem().getId()));
			list.add(map);
		}
		model.put("details",list);
//		List<Centro> centroList=this.centroRemote.findCentros();
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
        	retMap = shipOrderRemote.splitShipOrder(param);
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
        	retMap = shipOrderRemote.splitShipOrder(param);
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
//        	retMap = shipOrderRemote.splitShipOrder(param);
//        }else{
//        	retMap.put("code", "404");
//        	retMap.put("msg", "无法识别有效返单数量");
//        }
//        System.err.println("拆单结果:"+retMap.get("code")+"|"+retMap.get("msg"));
//        System.out.println("拆单结果:"+retMap.get("code")+"|"+retMap.get("msg"));
//        return retMap ;
//	}
}
