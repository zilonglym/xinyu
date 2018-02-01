
package com.xinyu.controller.qm;

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

import com.xinyu.common.util.XmlUtil;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.SenderInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.base.enums.XmlEnum;
import com.xinyu.model.order.StockInOrder;
import com.xinyu.model.order.child.WmsStockInOrderItem;
import com.xinyu.model.order.enums.InOrderStateEnum;
import com.xinyu.model.qm.EntryorderConfirmEntry;
import com.xinyu.model.qm.ReturnorderConfirmEntry;
import com.xinyu.service.order.StockInOrderService;
import com.xinyu.service.qm.QmConfirmService;
import com.xinyu.service.qm.QmSyncQueryService;
import com.xinyu.service.qm.QmSyncService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.trade.SenderInfoService;
import com.xinyu.service.trade.ShipOrderService;

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
	private QmSyncService qmSyncService;
	
	@Autowired
	private QmSyncQueryService qmSyncQueryService;
	
	@Autowired
	private QmConfirmService qmConfirmService;

	@Autowired
	private SenderInfoService senderInfoService;

	
	@Autowired
	private StockInOrderService stockInOrderService;
	
	@Autowired
	private ItemService itemService;
	
	
	@Autowired
	private UserService userService;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	/**
	 * 入库单确认
	 * @param orderId
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "submitStockInOrder")
	@ResponseBody
	public Map<String, Object> submitEntryOrderDetail(Model model,HttpServletRequest request) throws Exception {
	
		String id = request.getParameter("id");
		
		StockInOrder stockInOrder = this.stockInOrderService.findStockInOrderById(id);
		
		User user=this.userService.getUserById(stockInOrder.getUser().getId());
		
 		String orderType  = "PART_RECEIVED";
		
		Map<String,Object> map   = new HashMap<String,Object>();
		
		Map<Object,Object> headMap =  new HashMap<Object,Object>(); 
		//入库单编码
		headMap.put(EntryorderConfirmEntry.entryOrderCode,stockInOrder.getErpOrderCode());
		
		//货主编号
		headMap.put(EntryorderConfirmEntry.ownerCode,"QIMENUSER1");
		
		//仓储系统入库单ID
		headMap.put(EntryorderConfirmEntry.entryOrderId,""+id);
		  
		
		//仓库编码
//		Centro centro = centroService.findCentroById(""+shipOrder.getCentroId());
		headMap.put(EntryorderConfirmEntry.warehouseCode,"zhongcang");
		
		//入库单类型 
		headMap.put(EntryorderConfirmEntry.entryOrderType,stockInOrder.getOrderType().getKey());
		
		//外部业务编码, 多次确认时, 每次传入要求唯一
		headMap.put(EntryorderConfirmEntry.outBizCode,stockInOrder.getErpOrderCode()+"-"+id);
		
		//提交状态(1 表示入库单中间状态确认)
		headMap.put(EntryorderConfirmEntry.confirmType,0);
		
		//  ACCEPT-仓库接单 
		headMap.put(EntryorderConfirmEntry.status,orderType);
		
		//YYYY-MM-DD HH:MM:SS，  入库时间
		headMap.put(EntryorderConfirmEntry.operateTime,sdf.format(stockInOrder.getOrderCreateTime()));
		
		//备注   
		headMap.put(EntryorderConfirmEntry.remark,stockInOrder.getRemark());
		
		map.put(EntryorderConfirmEntry.head, headMap);
		
		//DETAIL
		List<WmsStockInOrderItem> details = stockInOrder.getOrderItemList();
		
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
			
			  WmsStockInOrderItem stockInOrderItem = details.get(i);
			  String numStr = mapPars.get(stockInOrderItem.getId()+"");
			  //不需要修改
			  if(numStr.equals("0")){
				  continue;
			  }
			
			  //单据行号 
			  Map<Object,Object>  detailMap = new HashMap<Object, Object>();
			  detailMap.put(EntryorderConfirmEntry.orderLineNoDetail, stockInOrderItem.getOrderItemId());
			  			  
			  Item item =this.itemService.getItem(stockInOrderItem.getItem().getId());
			  //商品编码
			  detailMap.put(EntryorderConfirmEntry.itemCodeDetail, item.getItemCode());
			  //仓储系统商品ID
			  detailMap.put(EntryorderConfirmEntry.itemIdDetail, item.getItemId());
			  
			  //商品名称
			  detailMap.put(EntryorderConfirmEntry.itemNameDetail, item.getName());
			  
			  //库存类型
			  detailMap.put(EntryorderConfirmEntry.inventoryTypeDetail,stockInOrderItem.getInventoryType() );
			  
			  //应收数量
			  detailMap.put(EntryorderConfirmEntry.planQtyDetail, stockInOrderItem.getItemQuantity());

			  
			  Map<String,Object> sysUpdateMap =  new HashMap<String, Object>();//后台参数 ， 用于更新库存数据
			  sysUpdateMap.put("detailId", stockInOrderItem.getId());
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
		
		String xmlStr = XmlUtil.converterPayPalm(map, "request");
		System.out.println("xmlStr:"+xmlStr);
		String  retStr  = qmSyncQueryService.submitQm(xmlStr, EntryorderConfirmEntry.method,user.getOwnerCode());
		System.out.println("retStr:"+retStr);
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		//请求成功
		if(retStr.indexOf("success")!=-1){
			sysMap.put(EntryorderConfirmEntry.head, stockInOrder);
			sysMap.put(EntryorderConfirmEntry.details, sysList);
			sysMap.put("orderId", stockInOrder.getId());
			sysMap.put("status", orderType);
			String result = qmConfirmService.entryOrderConfirm(sysMap);
			stockInOrder.setState(InOrderStateEnum.WMS_CONFIRM_FINISH);
			this.stockInOrderService.updateStockInOrder(stockInOrder);
			retMap.put("ret", result);
		}
		return retMap;
	}
	
	/**
	 * 退货入库确认
	 * @param orderId
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "submitReturnOrder")
	@ResponseBody
	public Map<String, Object> submitReturnorderDetail(Model model,HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		
		StockInOrder stockInOrder = this.stockInOrderService.findStockInOrderById(id);
		
		User user=this.userService.getUserById(stockInOrder.getUser().getId());
		
		Map<String,Object> map   = new HashMap<String,Object>();
		
		Map<Object,Object> headMap =  new HashMap<Object,Object>(); 
		//入库单编码
		headMap.put(ReturnorderConfirmEntry.returnOrderCode,stockInOrder.getErpOrderCode());
		
		
		//仓储系统入库单ID
		headMap.put(ReturnorderConfirmEntry.returnOrderId,""+id);
		  
		
		//仓库编码
//		Centro centro = centroService.findCentroById(""+shipOrder.getCentroId());
		headMap.put(ReturnorderConfirmEntry.warehouseCode,"zhongcang");
		
		//入库单类型 
		headMap.put(ReturnorderConfirmEntry.orderType,stockInOrder.getOrderType().getKey());
		
		//外部业务编码, 多次确认时, 每次传入要求唯一
		headMap.put(ReturnorderConfirmEntry.outBizCode,stockInOrder.getErpOrderCode()+"-"+id);
		
		//YYYY-MM-DD HH:MM:SS，  入库时间
		headMap.put(ReturnorderConfirmEntry.orderConfirmTime,sdf.format(stockInOrder.getOrderCreateTime()));
		
		//物流公司编码
		headMap.put(ReturnorderConfirmEntry.logisticsCode,stockInOrder.getTmsServiceCode());
		
		//物流公司名称
		headMap.put(ReturnorderConfirmEntry.logisticsName,stockInOrder.getTmsServiceName());
		
		//运单号
		headMap.put(ReturnorderConfirmEntry.expressCode,stockInOrder.getTmsOrderCode());
		
		//备注   
		headMap.put(ReturnorderConfirmEntry.remark,stockInOrder.getRemark());
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
		
		SenderInfo senderInfo = this.senderInfoService.getSenderInfoById(stockInOrder.getSenderInfo().getId());
		
		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.company ,senderInfo.getSenderName());
		
		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.name ,senderInfo.getSenderName());
		
		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.zipCode ,senderInfo.getSenderZipCode());
		
		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.mobile ,senderInfo.getSenderMobile());

		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.province ,senderInfo.getSenderProvince());

		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.city ,senderInfo.getSenderCity());
		
		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.area ,senderInfo.getSenderArea());

		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.town ,senderInfo.getSenderTown());
		
		senderInfoMap.put(ReturnorderConfirmEntry.senderInfo.detailAddress ,senderInfo.getSenderAddress());
		
		
		headMap.put("senderInfo", senderInfoMap);
		//senderInfo  end 
		
		
		map.put(ReturnorderConfirmEntry.head, headMap);
		
		
		
		
		//DETAIL
		List<WmsStockInOrderItem> details = stockInOrder.getOrderItemList();
		
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
			
			  WmsStockInOrderItem stockInOrderItem = details.get(i);
			  String numStr = mapPars.get(stockInOrderItem.getId()+"");
			  //不需要修改
			  if(numStr.equals("0")){
				  continue;
			  }
			  Item item = itemService.getItem(stockInOrderItem.getItem().getId());
			  //单据行号 
			  Map<Object,Object>  detailMap = new HashMap<Object, Object>();
			  detailMap.put(ReturnorderConfirmEntry.orderLine.orderLineNo, stockInOrderItem.getOrderItemId());
			  
			  //商品编码
			  detailMap.put(ReturnorderConfirmEntry.orderLine.itemCode, item.getItemCode());
			  
			  //仓储系统商品ID
			  detailMap.put(ReturnorderConfirmEntry.orderLine.itemId, item.getId());
			  
			  //库存类型
			  detailMap.put(ReturnorderConfirmEntry.orderLine.inventoryType,stockInOrderItem.getInventoryType().getKey());
			  
			  //应收数量
			  detailMap.put(ReturnorderConfirmEntry.orderLine.planQty, stockInOrderItem.getItemQuantity());

			  Map<String,Object> sysUpdateMap =  new HashMap<String, Object>();//后台参数 ， 用于更新库存数据
			  sysUpdateMap.put("detailId", stockInOrderItem.getId());
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
		
		String xmlStr = XmlUtil.converterPayPalm(map, "request");
		System.out.println("xmlStr:"+xmlStr);
		String  retStr  = qmSyncQueryService.submitQm(xmlStr, ReturnorderConfirmEntry.method,user.getOwnerCode());
		System.out.println("retStr:"+retStr);
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		//请求成功
		if(retStr.indexOf("success")!=-1){
			sysMap.put(EntryorderConfirmEntry.head, stockInOrder);
			sysMap.put(EntryorderConfirmEntry.details, sysList);
			sysMap.put("orderId", stockInOrder.getId());
			sysMap.put("status", "ENTRY_FINISH");
			String result = qmConfirmService.entryOrderConfirm(sysMap);
			stockInOrder.setState(InOrderStateEnum.WMS_CONFIRM_FINISH);
			this.stockInOrderService.updateStockInOrder(stockInOrder);
			retMap.put("ret", result);
		}
		return retMap;
	}
	
}
