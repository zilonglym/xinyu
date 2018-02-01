package com.graby.store.portal.qm.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.ShipOrderPackage;
import com.graby.store.entity.ShipOrderPackageItem;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.enums.StoreSystemItemEnums;
import com.graby.store.portal.qm.enums.XmlEnum;
import com.graby.store.portal.qm.service.QmMethodInterface;
import com.graby.store.portal.qm.service.QmSyncQueryService;
import com.graby.store.portal.qm.service.QmSyncService;
import com.graby.store.portal.qm.util.XmlUtil;
import com.graby.store.portal.web.BaseController;
import com.graby.store.service.item.ItemService;
import com.graby.store.service.wms.ShipOrderPackageService;
import com.graby.store.service.wms.ShipOrderService;
import com.graby.store.service.wms.SystemItemService;

/**
 * 奇门接口入口
 * 
 * @author 杨敏
 *
 */
@Controller
public class QmWebController extends BaseController{
	@Autowired
	private QmSyncService qmService;
	@Autowired
	private QmSyncQueryService qmSyncQueryService;
	@Autowired
	private ShipOrderService shipOrderService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private SystemItemService systemService;
	@Autowired
	private ShipOrderPackageService packageService;
	

	/**
	 * 奇门调用WMS接口入口
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "qm_index")
	@ResponseBody
	public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String method = request.getParameter("method");// 调用API的方法名，根据相就在的method来调用不同的方法。
		Map<String, Object> resultMap = new HashMap<String, Object>();
		InputStreamReader reader = new InputStreamReader(request.getInputStream(), "UTF8");
		char[] buff = new char[1024];
		int length = 0;
		StringBuilder requestStr=new StringBuilder();
		while ((length = reader.read(buff)) != -1) {
			String x = new String(buff, 0, length);
			requestStr.append(x);
		}
		String xmlStr = requestStr.toString();
		System.err.println(xmlStr);
		String msg = "";
		String resultStr="";
		try {
			if (method == null || method.length() == 0) {
				resultMap.put("flag", "failure");
				resultMap.put("message", "method参数为空!");
				String resultXml = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
				System.err.println(resultXml);
				return resultXml;
			}
			if (method.equals("singleitem.synchronize")) {// 商品同步 OK
				msg = this.qmService.itemSync(xmlStr);
			} else if (method.equals("combineitem.synchronize")) {// 组合商品
				msg = this.qmService.combineitem(xmlStr);
			} else if (method.equals("entryorder.create")) {// 入库单创建 OK
				msg = this.qmService.entryorder(xmlStr);
				
			} else if (method.equals("returnorder.create")) {// 退货入库单 ok
				msg = this.qmService.returnorder(xmlStr);
			} else if (method.equals("stockout.create")) {// 出库单创建 OK
				msg = this.qmService.stockout(xmlStr);
			} else if (method.equals("deliveryorder.create")) {// 发货单创建 OK
				msg = this.qmService.deliveryorder(xmlStr);
			} else if (method.equals("deliveryorder.query")) {// 发货单查询接口
				resultStr=this.qmService.deliveryQuery(xmlStr);
			} else if (method.equals("orderprocess.query")) {// 订单查询接口
				resultStr=this.qmService.orderprocessQuery(xmlStr);
			}else if(method.equals("order.cancel")){//单据取消接口
				msg=this.qmService.orderCancel(xmlStr);
			}else if(method.equals("inventory.query")){//库存查询接口
				resultStr=this.qmService.inventoryQuery(xmlStr);
			}else if(method.equals("storeprocess.create")){//仓内加工单创建接口 OK
				msg=this.qmService.storeprocessCreate(xmlStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("flag", "failure");
			resultMap.put("message", msg);
			resultMap.put("itemId", "");
			msg = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
			System.err.println(msg);
		}
		/*if (msg != null && msg.length() > 0) {
			resultMap.put("flag", "failure");
			resultMap.put("message", msg);
			resultMap.put("itemId", "");
			String resultXml = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
			System.err.println(resultXml);
			return resultXml;
		}else{
			//msg为空，调用的为查询接口
			if(resultStr!=null && resultStr.length()>0){
				resultMap.put("flag", "success");
				resultMap.put("message", "");
				resultMap.put("itemId", "");
				String resultXml = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
				System.err.println(resultXml);
				return resultXml;
			}else{
				resultMap.put("flag", "success");
				resultMap.put("message", "接口调用成功!");
				resultMap.put("itemId", "");
				resultMap.put("code", "200");
				String resultXml = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
				System.err.println(resultXml);
				return resultXml;
			}
			*/
		return msg;
	}
	
	/**
	 *跳转发货确认界面
	 * @return
	 */
	@RequestMapping(value="/store/shipOrder/toConfirmDelivery/{id}")
	public String confirmDeliveryOrder(@PathVariable int id,HttpServletRequest request,ModelMap model){
		ShipOrder shipOrder = shipOrderService.getShipOrderInfo(Long.valueOf(id));
		model.addAttribute("order", shipOrder);
		List<SystemItem> itemList=this.systemService.findSystemItemByType(StoreSystemItemEnums.LOGISTICS.getKey());
		model.put("logistList", itemList);
		return "store/list/deliveryOrderDetail";
	}
	
	/**
	 *跳转发货确认界面
	 * @return
	 */
	@RequestMapping(value="/store/shipOrder/toItemLack/{id}")
	public String toItemLack(@PathVariable int id,HttpServletRequest request,ModelMap model){
		ShipOrder shipOrder = shipOrderService.getShipOrderInfo(Long.valueOf(id));
		model.addAttribute("order", shipOrder);
		List<SystemItem> itemList=this.systemService.findSystemItemByType(StoreSystemItemEnums.LOGISTICS.getKey());
		List<ShipOrderDetail> detailList=new ArrayList<ShipOrderDetail>();
		for(int i=0;shipOrder.getDetails()!=null && i<shipOrder.getDetails().size();i++){
			ShipOrderDetail detail=shipOrder.getDetails().get(i);
			if(detail.getNum()<=detail.getActualnum()){
				continue;
			}
			detailList.add(detail);
		}
		model.put("logistList", itemList);
		model.put("detailList", detailList);
		return "store/list/itemLackDetail";
	}
	
	
	/**
	 * 提交发货确认信息
	 * @return
	 */
	@RequestMapping(value="/store/shipOrder/submitDeliverOrder/{id}")
	public String submitDeliverOrder(@PathVariable int id,HttpServletRequest request,ModelMap model){
		try{
		ShipOrder shipOrder = shipOrderService.getShipOrderInfo(Long.valueOf(id));
		//物流信息填写
		String logistics=request.getParameter("logist");
		String expressCode=request.getParameter("expressCode");
		String invoiceNo=request.getParameter("invoiceNo");//发票号
		List<ShipOrderPackageItem> packageLists=new ArrayList<ShipOrderPackageItem>();
		String[] itemAry = request.getParameterValues("item");
		String[] itemIdAry = request.getParameterValues("itemId");
		String[] numsPar = request.getParameterValues("num");
		String[] numTempsPar = request.getParameterValues("numTemp");
		String[] actualnumsPar = request.getParameterValues("actualnum");
		String[] detailAry=request.getParameterValues("detailId");
		
		Map<String,Object> orderMap=new HashMap<String,Object>();
		orderMap.put("deliveryOrderCode", shipOrder.getOrderno());
		orderMap.put("deliveryOrderId", shipOrder.getId());
		orderMap.put("orderType", shipOrder.getOrderType());
		orderMap.put("outBizCode", shipOrder.getOrderno()+"-"+id);
		orderMap.put("confirmType", 1);
		orderMap.put("operateTime", shipOrder.getOperateTime());
		
		//发票信息
			if (shipOrder.getInvoiceAmount() != null) {
				Map<String, Object> invoice = new HashMap<String, Object>();
				invoice.put("header", shipOrder.getInvoiceHeader());
				invoice.put("amount", Double.valueOf(shipOrder.getInvoiceAmount()));
				invoice.put("content", shipOrder.getInvoiceContent() + " ");
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				mapList.add(invoice);
				Map<String, Object> invoices = new HashMap<String, Object>();
				invoices.put("invoice", invoice);
				orderMap.put("invoices", invoices);
			}
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("deliveryOrder", orderMap);
		
		Map<String,Object> packagesMap=new HashMap<String,Object>();
		
		List<Map<String,Object>> packageList=new ArrayList<Map<String,Object>>();
		Map<String,Object> packageMap=new HashMap<String,Object>();
		//List<Map<String,Object>> packagesItemList=new ArrayList<Map<String,Object>>();
		packageList.add(packageMap);
		packagesMap.put("package", packageList);
		resultMap.put("packages", packagesMap);
		packageMap.put("logisticsCode", logistics);
		packageMap.put("expressCode", expressCode);
		packageMap.put("invoiceNo", invoiceNo);
		
		Map<String,Object> itemsMap=new HashMap<String,Object>();
		List<Map<String,Object>> itemsList=new ArrayList<Map<String,Object>>();
		packageMap.put("items", itemsMap);
		itemsMap.put("item", itemsList);
		
		for(int i=0;i<numsPar.length;i++){
			String num=numsPar[i];
			if(num.length()==0 || num.equals("0")){
				continue;
			}
			Map<String,Object> item=new HashMap<String,Object>();
			item.put("itemCode", itemAry[i]);
			item.put("quantity", Integer.valueOf(num));
			item.put("itemId", itemIdAry[i]);
			itemsList.add(item);
			//packageList.add(item);
			ShipOrderPackageItem packageItem=new ShipOrderPackageItem();
			packageItem.setCreateTime(new Date());
			packageItem.setItemId(Integer.valueOf(itemIdAry[i]));
			packageItem.setQuantity(Integer.valueOf(num));
			packageItem.setDetailId(Integer.valueOf(detailAry[i]));
			packageLists.add(packageItem);
			
		}
		Map<String,Object> orderLineMap=new HashMap<String,Object>();
		List<Map<String,Object>> orderLineList=new ArrayList<Map<String,Object>>(itemsList.size());
		try{
			orderLineList.addAll(itemsList);
		}catch(Exception e){
			e.printStackTrace();
		}
		orderLineMap.put("orderLine", orderLineList);
		resultMap.put("orderLines", orderLineMap);
		
		String xmlStr = XmlUtil.converterPayPalm(resultMap, XmlEnum.REQUEST);
		
		System.out.println("xmlStr:"+xmlStr);
		String  retStr  = qmSyncQueryService.submitQm(xmlStr, QmMethodInterface.METHOD_DELIVERORDER);
		System.out.println("retStr:"+retStr);
		//请求成功
		if(retStr.indexOf("success")!=-1){
			/**
			 * 发货成功进行操作
			 * 1.运单号之类的数据进行返回
			 * 2.单据的状态进行改写
			 * 3.发货数据保存
			 */
			shipOrder.setLogisticsCode(logistics);
			shipOrder.setExpressCode(expressCode);
			this.shipOrderService.updateShipOrder(shipOrder);
			ShipOrderPackage packages=new ShipOrderPackage();
			packages.setExpressCode(expressCode);
			packages.setLogisticsCode(logistics);
			packages.setOrderId(shipOrder.getId().intValue());
			//packages.setPackageCode(packageCode);
			//packageLists
			this.packageService.save(packages);
			for(int i=0;packageLists!=null && i<packageLists.size();i++){
				ShipOrderPackageItem item=packageLists.get(i);
				item.setPackageId(packages.getId());
				
				//发货单详情操作 shipOrderDetail
				int detailId=item.getDetailId();
				Map<String,Object> params=new HashMap<String,Object>();
				params.put("id", detailId);
				ShipOrderDetail detail=this.shipOrderService.getShipOrderDetail(params);
				int quantity=item.getQuantity()+detail.getActualnum();
				params.put("actualnum", quantity);
				this.shipOrderService.updateDetailQuantity(params);
			}
			this.packageService.saveItemList(packageLists);
			//单据状态改写
		}
		}catch(Exception  e){
			e.printStackTrace();
		}
		return this.confirmDeliveryOrder(id, request, model);
	}

	
	//itemlack
	@RequestMapping(value="/store/shipOrder/itemLack/{id}")
	public String itemLack(@PathVariable int id,HttpServletRequest request,ModelMap model){
		ShipOrder shipOrder = shipOrderService.getShipOrderInfo(Long.valueOf(id));
		//
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("warehouseCode", QmMethodInterface.warehouseCode);
		resultMap.put("deliveryOrderCode", shipOrder.getOrderno());
		resultMap.put("deliveryOrderId", shipOrder.getId());
		resultMap.put("createTime", sdf.format(new Date()));
		resultMap.put("outBizCode", shipOrder.getOrderno());
		
		String[] itemIdAry = request.getParameterValues("itemId");
		String[] itemAry = request.getParameterValues("item");
		String[] numsPar = request.getParameterValues("num");
		String[] detailAry=request.getParameterValues("detailId");
		String[] reasonAry=request.getParameterValues("reason");
		
		List<Map<String,Object>> itemList=new ArrayList<Map<String,Object>>();
		Map<String,Object> itemMap=new HashMap<String,Object>();
		itemMap.put("item", itemList);
		resultMap.put("items", itemMap);
		for(int i=0;i<numsPar.length;i++){
			String num=numsPar[i];
			if(num==null || num.equals("0") || num.length()==0){
				continue;
			}
			String detailId=detailAry[i];
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("id", detailId);
			ShipOrderDetail detail=this.shipOrderService.getShipOrderDetail(params); 
			Map<String,Object> item=new HashMap<String,Object>();
			item.put("itemId", itemIdAry[i]);
			item.put("itemCode", itemAry[i]);
			item.put("planQty", detail.getNum());
			item.put("lackQty", num);
			item.put("reason", reasonAry[i]);
			itemList.add(item);
		}
		
		String xmlStr = XmlUtil.converterPayPalm(resultMap, XmlEnum.REQUEST);
		
		System.out.println("xmlStr:"+xmlStr);
		String  retStr  = qmSyncQueryService.submitQm(xmlStr, QmMethodInterface.METHOD_ITEMLACK);
		System.out.println("retStr:"+retStr);
		if(retStr.indexOf("success")!=-1){
			//转向至发送成功页面
		}
		return confirmDeliveryOrder(id, request, model);
	}
}
