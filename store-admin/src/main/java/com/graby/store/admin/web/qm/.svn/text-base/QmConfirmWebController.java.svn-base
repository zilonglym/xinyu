
package com.graby.store.admin.web.qm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.admin.util.qm.XmlUtil;
import com.graby.store.entity.Centro;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.ShipOrderPackage;
import com.graby.store.entity.StoreProcess;
import com.graby.store.entity.StoreProcessItem;
import com.graby.store.entity.User;
import com.graby.store.entity.enums.qm.XmlEnum;
import com.graby.store.entity.qm.EntryorderConfirmEntry;
import com.graby.store.entity.qm.ReturnorderConfirmEntry;
import com.graby.store.entity.qm.StockoutConfirmEntry;
import com.graby.store.entity.qm.StoreprocessConfirmEntry;
import com.graby.store.remote.CentroRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.ProcessRemote;
import com.graby.store.remote.QmSyncRemote;
import com.graby.store.remote.ShipOrderPackageRemote;
import com.graby.store.remote.ShipOrderRemote;
import com.graby.store.remote.UserRemote;
import com.graby.store.web.auth.ShiroContextUtils;


/**
 * 
 * 奇门确认WEBcontroller
 * @author shark_cj
 * 
 */

@Controller
@RequestMapping(value = "/store/entry/confirm")
public class QmConfirmWebController extends QMBaseController{
	
	@Autowired
	private QmSyncRemote qmSyncRemote;
	
	@Autowired
	private ShipOrderRemote shipOrderRemote;
	
	@Autowired
	private CentroRemote  centroRemote;
	
	@Autowired
	private ShipOrderPackageRemote packageRemote;
	
	@Autowired
	private ItemRemote itemRemote;
	
	@Autowired
	private ProcessRemote processRemote;
	
	@Autowired
	private UserRemote userRemote;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	/**
	 * 显示待入库单据
	 */
	@RequestMapping(value = "entryOrderList")
	public String showEntryOrderList(Model model, ServletRequest request){
	
		Long userId = ShiroContextUtils.getUserid();
		
//		/** 入库单  */   ShipOrder
//		public static final String TYPE_ENTRY = "entry";
//		/** WAIT_STORAGE_RECEIVED ： 等待仓库接收 */
//		public static final String ENTRY_WAIT_STORAGE_RECEIVED = "ENTRY_WAIT_STORAGE_RECEIVED";
		/**
		 * 仓库无法确定
		 */
		
	    List<ShipOrder> orders  = shipOrderRemote.findEntryOrderOnWay(null);
		model.addAttribute("orders", orders);
		
		return "store/confirm/entryOrderList";
		
	}
	
	
	/**
	 * 跳转到 确认界面
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "entryOrderDetail/{id}")
	public String entryOrderDetail(@PathVariable("id") Long orderId, Model model) {
		// 获得需要确认的 入库单
		
		ShipOrder shipOrder  =shipOrderRemote.getShipOrderInfo(orderId);
		 
		model.addAttribute("order", shipOrder);
		
		return "store/confirm/entryOrderDetail";
	}
	
	
	
	/**
	 * 入库单确认
	 * @param orderId
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "submitEntryOrderDetail/{id}")
	public String submitEntryOrderDetail(@PathVariable("id") Long orderId, Model model,HttpServletRequest request) throws Exception {
	
		ShipOrder shipOrder = shipOrderRemote.getShipOrder(orderId);
		
		User user=this.userRemote.getUser(shipOrder.getCreateUser().getId());
		
		String orderType  = ShipOrder.EntryOrderStatus.PART_RECEIVED;
		
		Map<String,Object> map   = new HashMap<String,Object>();
		
		Map<Object,Object> headMap =  new HashMap<Object,Object>(); 
		//入库单编码
		headMap.put(EntryorderConfirmEntry.entryOrderCode,shipOrder.getOrderno());
		
		//货主编号
		headMap.put(EntryorderConfirmEntry.ownerCode,"QIMENUSER1");
		
		//仓储系统入库单ID
		headMap.put(EntryorderConfirmEntry.entryOrderId,""+orderId);
		  
		
		//仓库编码
		Centro centro = centroRemote.findCentroById(""+shipOrder.getCentroId());
		headMap.put(EntryorderConfirmEntry.warehouseCode,centro!=null?centro.getCode():"");
		
		//入库单类型 
		headMap.put(EntryorderConfirmEntry.entryOrderType,shipOrder.getOrderType());
		
		//外部业务编码, 多次确认时, 每次传入要求唯一
		headMap.put(EntryorderConfirmEntry.outBizCode,shipOrder.getOrderno()+"-"+orderId);
		
		//提交状态(1 表示入库单中间状态确认)
		headMap.put(EntryorderConfirmEntry.confirmType,0);
		
		//  ACCEPT-仓库接单 
		headMap.put(EntryorderConfirmEntry.status,orderType);
		
		//YYYY-MM-DD HH:MM:SS，  入库时间
		headMap.put(EntryorderConfirmEntry.operateTime,sdf.format(shipOrder.getCreateDate()));
		
		//备注   
		headMap.put(EntryorderConfirmEntry.remark,shipOrder.getRemark());
		
		map.put(EntryorderConfirmEntry.head, headMap);
		
		//DETAIL
		List<ShipOrderDetail> details = shipOrder.getDetails();
		
		List<Map<Object,Object>>  detailsList = new ArrayList<Map<Object,Object>>();
		
		
		String[] detailsPar = request.getParameterValues("detail");
		
		String[] numsPar = request.getParameterValues("num");
		
		String[] numTempsPar = request.getParameterValues("numTemp");
		
		String[] actualnumsPar = request.getParameterValues("actualnum");
		
		Map<String,String> mapPars  = new HashMap<String, String>();
		//获得页面参数
		for(int i  = 0,size  =detailsPar.length ;i<size;i++ ){
			mapPars.put(detailsPar[i], numsPar[i]);
		}
		
		Map<String, Object>  sysMap  =  new HashMap<String, Object>();
		
		
		
		List<Map<String,Object>>  sysList = new ArrayList<Map<String,Object>>();
		
		for(int i =0,size =  details.size();i  < size;i++){
			
			  ShipOrderDetail shipOrderDetail = details.get(i);
			  String numStr = mapPars.get(shipOrderDetail.getId()+"");
			  //不需要修改
			  if(numStr.equals("0")){
				  continue;
			  }
			
			  //单据行号 
			  Map<Object,Object>  detailMap = new HashMap<Object, Object>();
			  detailMap.put(EntryorderConfirmEntry.orderLineNoDetail, shipOrderDetail.getOrderLineNo());
			  
			 
			  
			  Item item =this.itemRemote.getItem(shipOrderDetail.getItem().getId());
			  //商品编码
			  detailMap.put(EntryorderConfirmEntry.itemCodeDetail, item.getCode());
			  //仓储系统商品ID
			  detailMap.put(EntryorderConfirmEntry.itemIdDetail, item.getItemId());
			  
			  //商品名称
			  detailMap.put(EntryorderConfirmEntry.itemNameDetail, item.getTitle());
			  
			  //库存类型
			  detailMap.put(EntryorderConfirmEntry.inventoryTypeDetail,shipOrderDetail.getInventoryType() );
			  
			  //应收数量
			  detailMap.put(EntryorderConfirmEntry.planQtyDetail, shipOrderDetail.getNum());

			  
			  Map<String,Object> sysUpdateMap =  new HashMap<String, Object>();//后台参数 ， 用于更新库存数据
			  sysUpdateMap.put("detailId", shipOrderDetail.getId());
			  sysUpdateMap.put("numStr", numStr);
			  sysUpdateMap.put("itemId", item.getItemId());
			  sysList.add(sysUpdateMap);
			  
			  //实收数量    修改后数量
			  detailMap.put(EntryorderConfirmEntry.actualQtyDetail, numStr);
		
			  //批次编码
			  detailMap.put(EntryorderConfirmEntry.batchCodeDetail, "");

			  //商品生产日期
			  detailMap.put(EntryorderConfirmEntry.productDateDetail, "");
			  
			  //商品过期日期
			  detailMap.put(EntryorderConfirmEntry.expireDateDetail, "");
			  
			  //生产批号
			  detailMap.put(EntryorderConfirmEntry.produceCodeDetail, "");
			  
			 //备注
			  detailMap.put(EntryorderConfirmEntry.remarkDetail, "");
			  
			  detailsList.add(detailMap);
		}
		
		Map<Object,Object>  mapDetail = new HashMap<Object, Object>();
		
		mapDetail.put(EntryorderConfirmEntry.detail, detailsList);
		
		map.put(EntryorderConfirmEntry.details, mapDetail);
		
		String xmlStr = XmlUtil.converterPayPalm(map, XmlEnum.REQUEST);
		System.out.println("xmlStr:"+xmlStr);
		String  retStr  = qmSyncRemote.submitQm(xmlStr, EntryorderConfirmEntry.method,user.getOwnerCode());
		System.out.println("retStr:"+retStr);
		
		//请求成功
		if(retStr.indexOf("success")!=-1){
			sysMap.put(EntryorderConfirmEntry.head, shipOrder);
			sysMap.put(EntryorderConfirmEntry.details, sysList);
			sysMap.put("orderId", shipOrder.getId());
			sysMap.put("status", orderType);
			qmSyncRemote.entryOrderConfirm(sysMap);
		}
		return "redirect:/store/entry/confirm/entryOrderList";
	}
	
	
	
	
	
	/**
	 * 显示出库待确认单据
	 */
	@RequestMapping(value = "stockoutList")
	public String showStockoutList(Model model, ServletRequest request){
		Map<String,Object> params=new HashMap<String, Object>();
		List<ShipOrder> waits = shipOrderRemote.findSendOrderWaits(params);
		
		model.addAttribute("orders", waits);
		
		return "store/confirm/stockoutList";
		
	}
	
	
	/**
	 * 跳转到出库确认界面
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "stockoutDetail/{id}")
	public String stockoutDetail(@PathVariable("id") Long orderId, Model model) {
		// 获得需要确认的 入库单
		
		 ShipOrder shipOrder  =shipOrderRemote.getShipOrderInfo(orderId);
		 
		model.addAttribute("order", shipOrder);
		
		return "store/confirm/stockoutDetail";
	}
	
	
	
	/**
	 * 出库单确认
	 * @param orderId
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "submitStockoutDetail/{id}")
	public String submitStockoutDetail(@PathVariable("id") Long orderId, Model model,HttpServletRequest request) throws Exception {
		
	    ShipOrder shipOrder = shipOrderRemote.getShipOrder(orderId);
	    
	    User user=this.userRemote.getUser(shipOrder.getCreateUser().getId());
	    
	    String orderType = ShipOrder.SendOrderStatus.WAIT_BUYER_RECEIVED;
	    //String orderType = ShipOrder.SendOrderStatus.WAIT_EXPRESS_PICKING;
	    
		Map<String,Object> map   = new HashMap<String,Object>();
		
		Map<String,Object> orderMap   = new HashMap<String,Object>();
		Map<String,Object> objMap=new HashMap<String, Object>();
		String xmlStr=buildDeliverOrderInfo(orderMap, shipOrder,user);
		 
		// 订单detail end
		System.out.println("xmlStr:" + xmlStr);
		String retStr = qmSyncRemote.submitQm(xmlStr, StockoutConfirmEntry.method,user.getOwnerCode());
		System.out.println("retStr:" + retStr);
		// 请求成功
		
		if(retStr.indexOf("success")!=-1){
			/**
			 * 发货成功进行操作
			 * 1.运单号之类的数据进行返回
			 * 2.单据的状态进行改写
			 * 3.发货数据保存
			 * 暂时不反写操作,运单号在打面单的时候已写入数据库，这里不能修改  2015-10-12
			 */
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("id", shipOrder.getId());
			params.put("status", ShipOrder.EntryOrderStatus.WMS_FINISH);
			this.shipOrderRemote.updateShipOrderStatus(params);
		}
		/*if (retStr.indexOf("success") != -1) {
			Map<String, Object> sysMap = new HashMap<String, Object>();
			sysMap.put("stockout", sysList);
			sysMap.put("orderId", shipOrder.getId());
			sysMap.put("status", orderType);
			qmSyncRemote.stockoutConfirm(sysMap);
		}*/
			
		return "redirect:/store/entry/confirm/stockoutList";
		
	}
	
	
	
	
	/**
	 * 显示出库待确认单据
	 */
	@RequestMapping(value = "returnorderList")
	public String returnorderList(ModelMap model,HttpServletRequest request){
		List<User> users = this.userRemote.findUsers();
		model.addAttribute("users", users);
		return "store/confirm/returnorderList";
	}
	
	@RequestMapping(value = "returnorderList/listData")
	@ResponseBody
	public Map<String, Object> returnOrderListData(ModelMap model,HttpServletRequest request,
			@RequestParam(value = "page",defaultValue = "1") int page,
			@RequestParam(value = "rows",defaultValue = "100") int rows){
		if (rows==10) {
			rows=100;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		String userId = request.getParameter("userId");
		String q = request.getParameter("q");
		String status = request.getParameter("status");
		param.put("q", q);	
		param.put("userId", userId);		
		param.put("type", "return");
		if (status!=null) {
			param.put("status", status);
		}else {
			param.put("status", "ENTRY_WAIT_STORAGE_RECEIVED");
		}
		List<ShipOrder> orders = this.shipOrderRemote.findeSendOrderByList(param,page,rows);
		int total = this.shipOrderRemote.getSendOrderCount(param);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", buildListData(orders));
		result.put("page", page);
		result.put("total", total);
		return result;
	}
	
	private List<Map<String, Object>> buildListData(List<ShipOrder> orders) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for(ShipOrder order:orders){
			Map<String, Object> map = new HashMap<String, Object>();
			User user = this.userRemote.getUser(order.getCreateUser().getId());
			map.put("createDate", sf.format(order.getCreateDate()));
			map.put("shopName",(user==null?"":user.getShopName()));
			map.put("orderno", order.getOrderno());		
			map.put("expressCode", order.getExpressCode());		
			map.put("expressName", order.getLogisticsCode());		
			map.put("name", (order.getOriginPersion()==null?"":order.getOriginPersion()));
			map.put("buyerNick", (order.getBuyerNick()==null?"":order.getBuyerNick()));
			map.put("id", order.getId());
			map.put("status", order.getStatus());
			result.add(map);
		}
		return result;
	}


	/**
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "returnorderDetail/{id}")
	public String returnorderDetail(@PathVariable("id") Long orderId, Model model) {
		// 获得需要确认的 入库单
		
		 ShipOrder shipOrder  =shipOrderRemote.getShipOrderInfo(orderId);
		 List<ShipOrderDetail> detailList=this.shipOrderRemote.getShipOrderDetailByOrderId(shipOrder.getId());
		 for(int i=0;detailList!=null && i<detailList.size();i++){
			 ShipOrderDetail detail=detailList.get(i);
			 Item item=this.itemRemote.getItem(detail.getItem().getId());
			 detail.setItem(item);
		 }
		shipOrder.setDetails(detailList);
		model.addAttribute("order", shipOrder);
		
		return "store/confirm/returnorderDetail";
	}
	
	
	
	/**
	 * 退货入库确认
	 * @param orderId
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "submitReturnorderDetail/{id}")
	public String submitReturnorderDetail(@PathVariable("id") Long orderId, Model model,HttpServletRequest request) throws Exception {
		
		
		ShipOrder shipOrder = shipOrderRemote.getShipOrder(orderId);
		
		User user=this.userRemote.getUser(shipOrder.getCreateUser().getId());
		
		Map<String,Object> map   = new HashMap<String,Object>();
		
		Map<Object,Object> headMap =  new HashMap<Object,Object>(); 
		//入库单编码
		headMap.put(ReturnorderConfirmEntry.returnOrderCode,shipOrder.getOrderno());
		
		
		//仓储系统入库单ID
		headMap.put(ReturnorderConfirmEntry.returnOrderId,""+orderId);
		  
		
		//仓库编码
		Centro centro = centroRemote.findCentroById(""+shipOrder.getCentroId());
		headMap.put(ReturnorderConfirmEntry.warehouseCode,centro!=null?centro.getCode():"");
		
		//入库单类型 
		headMap.put(ReturnorderConfirmEntry.orderType,shipOrder.getOrderType());
		
		//外部业务编码, 多次确认时, 每次传入要求唯一
		headMap.put(ReturnorderConfirmEntry.outBizCode,shipOrder.getOrderno()+"-"+orderId);
		
		//YYYY-MM-DD HH:MM:SS，  入库时间
		headMap.put(ReturnorderConfirmEntry.orderConfirmTime,sdf.format(shipOrder.getCreateDate()));
		
		//物流公司编码
		headMap.put(ReturnorderConfirmEntry.logisticsCode,shipOrder.getLogisticsCode());
		
		//物流公司名称
		headMap.put(ReturnorderConfirmEntry.logisticsName,shipOrder.getLogisticsName());
		
		//运单号
		headMap.put(ReturnorderConfirmEntry.expressCode,shipOrder.getExpressCode());
		
		//备注   
		headMap.put(ReturnorderConfirmEntry.remark,shipOrder.getRemark());
//		//发件人信息
//				Map<String,Object>  info=(Map<String, Object>) entity.get("senderInfo");
//				String company=(String) info.get("company");
//				order.setSenderCompany(company);
//				order.setOriginPersion((String) info.get("name"));
//				order.setSenderZipCode((String) info.get("zipCode"));
//				order.setOriginPhone((String) info.get("mobile"));
//				order.setSenderProvince((String) info.get("province"));
//				String city=(String) info.get("city");
//				order.setSenderCity(city);
//				order.setSenderArea((String) info.get("area"));
//				order.setSenderTown((String) info.get("town"));
//				order.setSenderaddress((String) info.get("detailAddress"));
		
		
		//senderInfo  start 
		Map<String,Object>  senderInfoMap  = new HashMap<String, Object>();
		
		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.company ,shipOrder.getSenderCompany());
		
		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.name ,shipOrder.getOriginPersion());
		
		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.zipCode ,shipOrder.getSenderZipCode());
		
		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.mobile ,shipOrder.getOriginPhone());

		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.province ,shipOrder.getSenderProvince());

		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.city ,shipOrder.getSenderCity());
		
		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.area ,shipOrder.getSenderArea());

		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.town ,shipOrder.getSenderTown());
		
		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.detailAddress ,shipOrder.getSenderaddress());
		
		
		headMap.put("senderInfo", senderInfoMap);
		//senderInfo  end 
		
		
		map.put(ReturnorderConfirmEntry.head, headMap);
		
		
		
		
		//DETAIL
		List<ShipOrderDetail> details = shipOrder.getDetails();
		
		List<Map<Object,Object>>  detailsList = new ArrayList<Map<Object,Object>>();
		
		
		String[] detailsPar = request.getParameterValues("detail");
		
		String[] numsPar = request.getParameterValues("num");
		
		Map<String,String> mapPars  = new HashMap<String, String>();
		//获得页面参数
		for(int i  = 0,size  =detailsPar.length ;i<size;i++ ){
			mapPars.put(detailsPar[i], numsPar[i]);
		}
		
		Map<String, Object>  sysMap  =  new HashMap<String, Object>();
		
		
		
		List<Map<String,Object>>  sysList = new ArrayList<Map<String,Object>>();
		
		for(int i =0,size =  details.size();i  < size;i++){
			
			  ShipOrderDetail shipOrderDetail = details.get(i);
			  String numStr = mapPars.get(shipOrderDetail.getId()+"");
			  //不需要修改
			  if(numStr.equals("0")){
				  continue;
			  }
			  Item item = itemRemote.getItem(shipOrderDetail.getItem().getId());
			  //单据行号 
			  Map<Object,Object>  detailMap = new HashMap<Object, Object>();
			  detailMap.put(ReturnorderConfirmEntry.orderLine.orderLineNo, shipOrderDetail.getOrderLineNo());
			  
			  //商品编码
			  detailMap.put(ReturnorderConfirmEntry.orderLine.itemCode, item.getCode());
			  
			  //仓储系统商品ID
			  detailMap.put(ReturnorderConfirmEntry.orderLine.itemId, item.getItemId());
			  
			  //库存类型
			  detailMap.put(ReturnorderConfirmEntry.orderLine.inventoryType,shipOrderDetail.getInventoryType() );
			  
			  //应收数量
			  detailMap.put(ReturnorderConfirmEntry.orderLine.planQty, shipOrderDetail.getNum());

			  Map<String,Object> sysUpdateMap =  new HashMap<String, Object>();//后台参数 ， 用于更新库存数据
			  sysUpdateMap.put("detailId", shipOrderDetail.getId());
			  sysUpdateMap.put("numStr", numStr);
			  sysUpdateMap.put("itemId", item.getItemId());
			  sysList.add(sysUpdateMap);
			  
			  //实收数量    修改后数量
			  detailMap.put(ReturnorderConfirmEntry.orderLine.actualQty, numStr);
		

			  //商品生产日期
//			  detailMap.put(ReturnorderConfirmEntry.orderLine.productDate, "");
//			  
//			  //商品过期日期
//			  detailMap.put(ReturnorderConfirmEntry.orderLine.expireDate, "");
//			  
//			  //生产批号
//			  detailMap.put(ReturnorderConfirmEntry.orderLine.produceCode, "");
			  
			  detailsList.add(detailMap);
		}
		
		Map<Object,Object>  mapDetail = new HashMap<Object, Object>();
		
		mapDetail.put(EntryorderConfirmEntry.detail, detailsList);
		
		map.put(EntryorderConfirmEntry.details, mapDetail);
		
		String xmlStr = XmlUtil.converterPayPalm(map, XmlEnum.REQUEST);
		System.out.println("xmlStr:"+xmlStr);
		String  retStr  = qmSyncRemote.submitQm(xmlStr, ReturnorderConfirmEntry.method,user.getOwnerCode());
		System.out.println("retStr:"+retStr);
		
		//请求成功
		if(retStr.indexOf("success")!=-1){
			sysMap.put(EntryorderConfirmEntry.head, shipOrder);
			sysMap.put(EntryorderConfirmEntry.details, sysList);
			sysMap.put("orderId", shipOrder.getId());
			sysMap.put("status", ShipOrder.EntryOrderStatus.ENTRY_FINISH);
			qmSyncRemote.entryOrderConfirm(sysMap);
		}
		return "redirect:/store/entry/confirm/returnorderList";
	}
	
	/**
	 * 显示加工单 待确认
	 */
	@RequestMapping(value = "storeprocessList")
	public String showStoreprocessList(Model model, ServletRequest request){
		
		List<StoreProcess>  lists = processRemote.findProcessConfirm();
		
		model.addAttribute("orders", lists);
		
		return "store/confirm/storeprocessList";
		
	}
	
	
	/**
	 * 跳转到加工单确认界面
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "storeprocessDetail/{id}")
	public String storeprocessDetail(@PathVariable("id") Integer orderId, Model model) {
		
		StoreProcess process = processRemote.findById(orderId);

		model.addAttribute("order", process);

		List<StoreProcessItem> details = processRemote.findByparentId(orderId);

		model.addAttribute("details", details);
		
		return "store/confirm/storeprocessDetail";
	}
	
	
	
	/**
	 * 加工单确认 ... 仓内加工单
	 * @param orderId
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "submitStoreprocessDetail/{id}")
	public String submitStoreprocessDetail(@PathVariable("id") Integer orderId, Model model,HttpServletRequest request) throws Exception {
		
		StoreProcess process = processRemote.findById(orderId);
		
	//	User user=this.userRemote.getUser(process.get)
		
		Map<String,Object> processMap  = new HashMap<String, Object>();
		
		// 加工单编码
		processMap.put(StoreprocessConfirmEntry.processOrderCode, process.getCode());

		// 仓储系统加工单ID
		processMap.put(StoreprocessConfirmEntry.processOrderId, process.getId());

		// 外部业务编码
		processMap.put(StoreprocessConfirmEntry.outBizCode, process.getCode()+"-"+orderId);

		// 单据类型
		processMap.put(StoreprocessConfirmEntry.orderType, process.getType());
		
		// 加工单完成时间
		processMap.put(StoreprocessConfirmEntry.orderCompleteTime, process.getCreatetime());
		
//		// 实际作业总数量
//		processMap.put(StoreprocessConfirmEntry.actualQty, process.getPlanqty());

		
		
		String[] detailsPar = request.getParameterValues("detail");
		
		
		
		String[] quantityPar = request.getParameterValues("quantity");
		
		Map<String,String> mapPars  = new HashMap<String, String>();
		
		//获得页面参数
		for(int i  = 0,size  =detailsPar.length ;i<size;i++ ){
			mapPars.put(detailsPar[i], quantityPar[i]);
		}
		
		
		List<StoreProcessItem> items = processRemote.findByparentId(orderId);
		
		List<Map<String,Object>>  itemsList  =  new ArrayList<Map<String,Object>>();
		
		
		for(int i = 0, size =  items.size();  i < size  ; i++){
			StoreProcessItem item = items.get(i);

			String quantityStr = mapPars.get(item.getId() + "");
			// 不需要修改
			if (quantityStr.equals("0")) {
				continue;
			}
			Map<String,Object>  itemMap  = new HashMap<String, Object>();
			
			//商品编码
			itemMap.put(StoreprocessConfirmEntry.materialitems.itemCode, item.getItemcode());
			
			//仓储系统商品ID
			itemMap.put(StoreprocessConfirmEntry.materialitems.itemId, item.getItemid());
			
			//库存类型
			itemMap.put(StoreprocessConfirmEntry.materialitems.inventoryType, item.getInventorytype());
			
			//数量,
			itemMap.put(StoreprocessConfirmEntry.materialitems.quantity, quantityStr);
			
			itemsList.add(itemMap);
		}
		Map<String,Object> itemsMap  =  new  HashMap<String, Object>();
		
		
		itemsMap.put("item", itemsList) ;
		
		processMap.put("materialitems", itemsMap);
		
		processMap.put("productitems", itemsMap);
		
		
		String xmlStr = XmlUtil.converterPayPalm(processMap, XmlEnum.REQUEST);
		System.out.println("xmlStr:"+xmlStr);
		String  retStr  = qmSyncRemote.submitQm(xmlStr, ReturnorderConfirmEntry.method,null);
		System.out.println("retStr:"+retStr);
		
		return null;
	}
}
