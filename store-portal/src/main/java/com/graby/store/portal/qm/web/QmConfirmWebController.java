package com.graby.store.portal.qm.web;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.graby.store.entity.Centro;
import com.graby.store.entity.Item;
import com.graby.store.entity.StoreProcess;
import com.graby.store.entity.StoreProcessItem;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.ShipOrderPackage;
import com.graby.store.portal.qm.entry.EntryorderConfirmEntry;
import com.graby.store.portal.qm.entry.ReturnorderConfirmEntry;
import com.graby.store.portal.qm.entry.StockoutConfirmEntry;
import com.graby.store.portal.qm.entry.StoreprocessConfirmEntry;
import com.graby.store.portal.qm.enums.XmlEnum;
import com.graby.store.portal.qm.service.QmConfirmService;
import com.graby.store.portal.qm.service.QmSyncQueryService;
import com.graby.store.portal.qm.util.XmlUtil;
import com.graby.store.service.base.CentroService;
import com.graby.store.service.item.ItemService;
import com.graby.store.service.wms.ProcessService;
import com.graby.store.service.wms.ShipOrderPackageService;
import com.graby.store.service.wms.ShipOrderService;
import com.graby.store.web.auth.ShiroContextUtils;


/**
 * 
 * 奇门确认WEBcontroller
 * @author shark_cj
 * 
 */

@Controller
@RequestMapping(value = "/store/entry/confirm")
public class QmConfirmWebController {
	
	@Autowired
	private QmSyncQueryService qmSyncQueryService;
	
	@Autowired
	private QmConfirmService qmConfirmService;
	
	@Autowired
	private ShipOrderService shipOrderService;
	
	@Autowired
	private CentroService  centroService;
	
	@Autowired
	private ShipOrderPackageService  shipOrderPackageService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ProcessService processService;
	
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
	    List<ShipOrder> orders  = shipOrderService.findEntryOrderOnWay(null);
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
		
		 ShipOrder shipOrder  =shipOrderService.getShipOrderInfo(orderId);
		 
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
		
		ShipOrder shipOrder = shipOrderService.getShipOrder(orderId);
		
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
		Centro centro = centroService.findCentroById(""+shipOrder.getCentroId());
		headMap.put(EntryorderConfirmEntry.warehouseCode,centro!=null?centro.getCode():"");
		
		//入库单类型 
		headMap.put(EntryorderConfirmEntry.entryOrderType,shipOrder.getOrderType());
		
		//外部业务编码, 多次确认时, 每次传入要求唯一
		headMap.put(EntryorderConfirmEntry.outBizCode,shipOrder.getOrderno()+"-"+orderId);
		
		//提交状态(1 表示入库单中间状态确认)
		headMap.put(EntryorderConfirmEntry.confirmType,1);
		
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
			  
			 
			  
			  Item item =this.itemService.getItem(shipOrderDetail.getItem().getId());
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
		String  retStr  = qmSyncQueryService.submitQm(xmlStr, EntryorderConfirmEntry.method);
		System.out.println("retStr:"+retStr);
		
		//请求成功
		if(retStr.indexOf("success")!=-1){
			sysMap.put(EntryorderConfirmEntry.head, shipOrder);
			sysMap.put(EntryorderConfirmEntry.details, sysList);
			sysMap.put("orderId", shipOrder.getId());
			sysMap.put("status", orderType);
			qmConfirmService.entryOrderConfirm(sysMap);
		}
		return "redirect:/store/entry/confirm/entryOrderList";
	}
	
	
	
	
	
	/**
	 * 显示出库待确认单据
	 */
	@RequestMapping(value = "stockoutList")
	public String showStockoutList(Model model, ServletRequest request){
		
		List<ShipOrder> waits = shipOrderService.findSendOrderWaits(null);
		
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
		
		 ShipOrder shipOrder  =shipOrderService.getShipOrderInfo(orderId);
		 
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
		
		
	    ShipOrder shipOrder = shipOrderService.getShipOrder(orderId);
		
	    String orderType = ShipOrder.SendOrderStatus.WAIT_BUYER_RECEIVED;
	    
		Map<String,Object> map   = new HashMap<String,Object>();
		
		Map<String,Object> orderMap   = new HashMap<String,Object>();
		
		//出库单号
		orderMap.put(StockoutConfirmEntry.deliveryOrderCode,shipOrder.getOrderno());
		
		//仓储系统出库单号
		orderMap.put(StockoutConfirmEntry.deliveryOrderId,""+shipOrder.getId());
		
		//仓库编码
		Centro centro = centroService.findCentroById(""+shipOrder.getCentroId());
		orderMap.put(StockoutConfirmEntry.warehouseCode,centro.getCode());
		
		
		//出库单类型
		orderMap.put(StockoutConfirmEntry.orderType,shipOrder.getOrderType());
		
		
		//出库单类型  外部业务编码, 消息ID, 用于去重, 一个合作伙伴中要求唯一, 多次确认时, 每次传入要求唯一 ，条件必填，条件为一单需要多次确认时
		orderMap.put(StockoutConfirmEntry.outBizCode,shipOrder.getOrderno()+"-"+orderId);
		
		orderMap.put(StockoutConfirmEntry.confirmType,"1");
		
		//物流公司名称
		orderMap.put(StockoutConfirmEntry.logisticsName,shipOrder.getLogisticsName());
		
		//运单号
		orderMap.put(StockoutConfirmEntry.expressCode,shipOrder.getExpressCode());
		
		//订单完成时间
		orderMap.put(StockoutConfirmEntry.orderConfirmTime,sdf.format(new Date()));
		
		
		
		//package   start 
		
		List<ShipOrderPackage> orderPackAge = shipOrderPackageService.findPackageByOrderId(orderId.intValue());
		
		Map<String, Object> orderPackageMap   =   new  HashMap<String, Object>();
		List<Map<String,Object>>  orderPackageList = new ArrayList<Map<String,Object>>();
		//物流公司名称
		orderPackageMap.put(StockoutConfirmEntry.packages.logisticsName, "韵达快递");
		
		//运单号
		orderPackageMap.put(StockoutConfirmEntry.packages.expressCode, sdf.format(new Date()));
		
		//包裹编号
		orderPackageMap.put(StockoutConfirmEntry.packages.packageCode, sdf.format(new Date()));
		if(orderPackAge!=null  && orderPackAge.size()>0){
//			ShipOrderPackage shipOrderPackage = orderPackAge.get(0);
//			//物流公司名称
//			orderPackageMap.put(StockoutConfirmEntry.packages.logisticsName, shipOrderPackage.getLogisticsName());
//			
//			//运单号
//			orderPackageMap.put(StockoutConfirmEntry.packages.expressCode, shipOrderPackage.getExpressCode());
//			
//			//包裹编号
//			orderPackageMap.put(StockoutConfirmEntry.packages.packageCode, shipOrderPackage.getPackageCode());
			//包裹长度 (厘米)
//			orderPackageMap.put(StockoutConfirmEntry.packages.length, "");
//			orderPackageMap.put(StockoutConfirmEntry.packages.width,  "");
//			orderPackageMap.put(StockoutConfirmEntry.packages.height, "");
//			orderPackageMap.put(StockoutConfirmEntry.packages.weight, "");
//			orderPackageMap.put(StockoutConfirmEntry.packages.volume, "");
			
		}
		
		//package  end 
		
		String[] detailsPar = request.getParameterValues("detail");
		
		String[] numsPar = request.getParameterValues("num");
		
		
		Map<String,String> mapPars  = new HashMap<String, String>();
		//获得页面参数
		for(int i  = 0,size  =detailsPar.length ;i<size;i++ ){
			mapPars.put(detailsPar[i], numsPar[i]);
		}
		
		
		 // 单据detail  明细出来  start
		 List<Map<String,Object>>  onlinesList  =  new ArrayList<Map<String,Object>>();
		  
		 List<ShipOrderDetail> details = shipOrder.getDetails();
		 
		 List<Map<String,Object>> itemList =  new ArrayList<Map<String,Object>>();
		 
		 
			
		List<Map<String,Object>>  sysList = new ArrayList<Map<String,Object>>();
			
		 for(int i = 0, size =  details.size();i<size;i++){
			  
			  ShipOrderDetail shipOrderDetail = details.get(i);
			  
			  String numStr = mapPars.get(shipOrderDetail.getId()+"");
			  //不需要修改
			  if(numStr.equals("0")){
				  continue;
			  }
			  
			  Map<String,Object>  onlineMap  =  new HashMap<String,Object>();
			  
			  Map<String,Object>  itemMap  =  new HashMap<String,Object>();
			  Item item  = itemService.getItem(shipOrderDetail.getItem().getId());
			  //商品编码
			  itemMap.put(StockoutConfirmEntry.packageItems.itemCode, item.getCode());
			  //商品仓储系统编码
			  itemMap.put(StockoutConfirmEntry.packageItems.itemId, item.getItemId());
			  //包裹内该商品的数量
			  itemMap.put(StockoutConfirmEntry.packageItems.quantity, numStr);
			
			  itemList.add(itemMap);
			  
			 
			  
			  //后天系统需要参数
			  Map<String,Object> sysUpdateMap =  new HashMap<String, Object>();//后台参数 ， 用于更新库存数据
			  sysUpdateMap.put("detailId", shipOrderDetail.getId());
			  sysUpdateMap.put("numStr", numStr);
			  sysUpdateMap.put("itemId", item.getItemId());
			  sysList.add(sysUpdateMap);
			  
			  //行号
			  onlineMap.put(StockoutConfirmEntry.onlines.orderLineNo, shipOrderDetail.getOrderLineNo());
			 
			  //商品编码
			  onlineMap.put(StockoutConfirmEntry.onlines.itemCode, item.getCode());
			  
			  //商品仓储系统编码
			  onlineMap.put(StockoutConfirmEntry.onlines.itemId, item.getItemId());
			  
			  //商品名称
			  onlineMap.put(StockoutConfirmEntry.onlines.itemName, item.getTitle());
			  
			  //库存类型
			  onlineMap.put(StockoutConfirmEntry.onlines.inventoryType, shipOrderDetail.getInventoryType());
			  
			  
			  //实发商品数量
			  onlineMap.put(StockoutConfirmEntry.onlines.actualQty, numStr);
			  
			  //批次编号
			  onlineMap.put(StockoutConfirmEntry.onlines.batchCode, "");
			  
			  //生产日期
			  onlineMap.put(StockoutConfirmEntry.onlines.productDate, "");
			  
			  //过期日期
			  onlineMap.put(StockoutConfirmEntry.onlines.expireDate, "");
			  
			  //生产批号
			  onlineMap.put(StockoutConfirmEntry.onlines.produceCode, "");
			  
			  onlinesList.add(onlineMap);
		 }
		 orderPackageList.add(orderPackageMap);
		
		 Map<String,Object>  itemMap =  new HashMap<String, Object>();
		 itemMap.put("item", itemList);
		 orderPackageMap.put("items", itemMap);
		 
		 Map<String,Object>  orderPackagesMap =  new HashMap<String, Object>();
		 orderPackagesMap.put("package", orderPackageList);
		 map.put("packages", orderPackagesMap);
		 Map<String,Object>  onlinesMap =  new HashMap<String, Object>();
		 onlinesMap.put("orderLine", onlinesList);
		 map.put("orderLines", onlinesMap);
		 map.put("deliveryOrder", orderMap);
		 
		// 订单detail end
		String xmlStr = XmlUtil.converterPayPalm(map, XmlEnum.REQUEST);
		System.out.println("xmlStr:" + xmlStr);
		String retStr = qmSyncQueryService.submitQm(xmlStr, StockoutConfirmEntry.method);
		System.out.println("retStr:" + retStr);
		// 请求成功
		if (retStr.indexOf("success") != -1) {
			Map<String, Object> sysMap = new HashMap<String, Object>();
			sysMap.put("stockout", sysList);
			sysMap.put("orderId", shipOrder.getId());
			sysMap.put("status", orderType);
			qmConfirmService.stockoutConfirm(sysMap);
		}
			
		return "redirect:/store/entry/confirm/stockoutList";
		
	}
	
	
	
	
	/**
	 * 显示出库待确认单据
	 */
	@RequestMapping(value = "returnorderList")
	public String returnorderList(Model model, ServletRequest request){
		
		List<ShipOrder> returns = shipOrderService.findReturnOrderOnWay();
		
		model.addAttribute("orders", returns);
		
		return "store/confirm/returnorderList";
		
	}
	
	
	/**
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "returnorderDetail/{id}")
	public String returnorderDetail(@PathVariable("id") Long orderId, Model model) {
		// 获得需要确认的 入库单
		
		 ShipOrder shipOrder  =shipOrderService.getShipOrderInfo(orderId);
		 
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
		
		
		ShipOrder shipOrder = shipOrderService.getShipOrder(orderId);
		
		Map<String,Object> map   = new HashMap<String,Object>();
		
		Map<Object,Object> headMap =  new HashMap<Object,Object>(); 
		//入库单编码
		headMap.put(ReturnorderConfirmEntry.returnOrderCode,shipOrder.getOrderno());
		
		
		//仓储系统入库单ID
		headMap.put(ReturnorderConfirmEntry.returnOrderId,""+orderId);
		  
		
		//仓库编码
		Centro centro = centroService.findCentroById(""+shipOrder.getCentroId());
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
			  Item item = itemService.getItem(shipOrderDetail.getItem().getId());
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
		String  retStr  = qmSyncQueryService.submitQm(xmlStr, ReturnorderConfirmEntry.method);
		System.out.println("retStr:"+retStr);
		
		//请求成功
		if(retStr.indexOf("success")!=-1){
			sysMap.put(EntryorderConfirmEntry.head, shipOrder);
			sysMap.put(EntryorderConfirmEntry.details, sysList);
			sysMap.put("orderId", shipOrder.getId());
			sysMap.put("status", ShipOrder.EntryOrderStatus.ENTRY_FINISH);
			qmConfirmService.entryOrderConfirm(sysMap);
		}
		return "redirect:/store/entry/confirm/returnorderList";
	}
	
	/**
	 * 显示加工单 待确认
	 */
	@RequestMapping(value = "storeprocessList")
	public String showStoreprocessList(Model model, ServletRequest request){
		
		List<StoreProcess>  lists = processService.findProcessConfirm();
		
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
		
		StoreProcess process = processService.findById(orderId);

		model.addAttribute("order", process);

		List<StoreProcessItem> details = processService.findByparentId(orderId);

		model.addAttribute("details", details);
		
		return "store/confirm/storeprocessDetail";
	}
	
	
	
	/**
	 * 加工单确认
	 * @param orderId
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "submitStoreprocessDetail/{id}")
	public String submitStoreprocessDetail(@PathVariable("id") Integer orderId, Model model,HttpServletRequest request) throws Exception {
		
		StoreProcess process = processService.findById(orderId);
		
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
		
		
		List<StoreProcessItem> items = processService.findByparentId(orderId);
		
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
		String  retStr  = qmSyncQueryService.submitQm(xmlStr, ReturnorderConfirmEntry.method);
		System.out.println("retStr:"+retStr);
		
		return null;
	}
}
