package com.xinyu.controller.qm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xinyu.common.BaseController;
import com.xinyu.model.base.InvoiceInfo;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.User;
import com.xinyu.model.base.enums.XmlEnum;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.WmsConsignOrderItem;
import com.xinyu.service.qm.QmMethodInterface;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.trade.InvoiceInfoService;
import com.xinyu.service.trade.ShipOrderService;
import com.xinyu.service.trade.WmsConsignOrderItemService;
import com.xinyu.service.trade.WmsConsignOrderPackageRequirementService;
import com.xinyu.service.util.XmlUtil;

/**
 * 公共类
 * @author yangin
 *
 */

@Controller
public class QMBaseController extends BaseController{

	@Autowired
	private ShipOrderService shipOrderService;
	
	@Autowired
	private WmsConsignOrderItemService orderItemService;
	
	@Autowired
	private InvoiceInfoService invoiceInfoService;
	
	@Autowired
	private WmsConsignOrderPackageRequirementService packageService;
	
	@Autowired
	private ItemService itemService;
	/***
	 * 当前登陆用户
	 * @return
	 */
//	public User getCurrentUser(){
//		User user=new User();
//		user.setId(Long.valueOf("1"));
//		return user;
//	}
	
	/**
	 * 取当前仓库
	 * @return
	 */
//	public Centro getCurrentCentro(){
//		Centro centro=new Centro();
//		centro.setId(Long.valueOf("1"));
//		return centro;
//	}
	
	/**
	 * 重新组合单据的明细信息
	 * @param shipOrder
	 */
	public void buildOrderDetailInfo(ShipOrder shipOrder){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("orderCode", shipOrder.getOrderCode());
		List<ShipOrder> orderList=this.shipOrderService.findShipOrderByList(params);
		/**
		 * 先把shipOrder的明细放入map ,key为itemId
		 */
		Map<String,Object> detailMap=new HashMap<String, Object>();
		for(int i=0;i<shipOrder.getOrderItemList().size() && shipOrder.getOrderItemList()!=null; i++){
			WmsConsignOrderItem detail=shipOrder.getOrderItemList().get(i);
			if(detailMap.get(detail.getItem().getId().toString())!=null){
				WmsConsignOrderItem obj=(WmsConsignOrderItem) detailMap.get(detail.getItem().getId().toString());
				obj.setItemQuantity(obj.getItemQuantity()+detail.getItemQuantity());
			}else{
				detailMap.put(shipOrder.getOrderItemList().get(i).getItem().getId().toString(), shipOrder.getOrderItemList().get(i));
			}
		}
		for(int i=0;i<orderList.size() && orderList!=null;i++){
			ShipOrder order=orderList.get(i);
			if(order.getId().equals(shipOrder.getId())){
				continue;
			}
			//查询订单的明细，组合
			params.clear();
			params.put("orderId", order.getId());
			List<WmsConsignOrderItem> detailList=this.orderItemService.getWmsConsignOrderItemByList(params);
			for(int j=0;detailList!=null && j<detailList.size();j++){
				WmsConsignOrderItem detail=detailList.get(j);
				if(detailMap.get(detail.getItem().getId().toString())!=null){
					//数量组合
					WmsConsignOrderItem obj=(WmsConsignOrderItem) detailMap.get(detail.getItem().getId().toString());
					obj.setItemQuantity(obj.getItemQuantity()+detail.getItemQuantity());
				}else{
					//商品组合
					detailMap.put(detail.getItem().getId().toString(),detail);
				}
			}
		}
		/**
		 * 回归订单组合
		 */
		shipOrder.getOrderItemList().clear();//清除订单明细的所有数据
		for(Map.Entry<String, Object> entry : detailMap.entrySet()){
			WmsConsignOrderItem detail=(WmsConsignOrderItem) entry.getValue();
			shipOrder.getOrderItemList().add(detail);
		}
	}
	/**
	 * 发货确认信息重组
	 * @param orderMap
	 * @param shipOrder
	 * @return
	 */
	public String buildDeliverOrderInfo(Map<String,Object> orderMap,ShipOrder shipOrder,User user){
		orderMap.put("deliveryOrderCode", shipOrder.getErpOrderCode());
		orderMap.put("deliveryOrderId", shipOrder.getId());
		orderMap.put("orderType", shipOrder.getOrderType());
		orderMap.put("outBizCode", shipOrder.getErpOrderCode()+"-"+shipOrder.getId());
		orderMap.put("confirmType", 0);
		orderMap.put("operateTime", shipOrder.getOrderExaminationTime());
		orderMap.put("warehouseCode",QmMethodInterface.warehouseCode);
		List<WmsConsignOrderItem> packageLists=new ArrayList<WmsConsignOrderItem>();
		//发票信息
			if (shipOrder.getInvoiceInfoList()!= null) {
				for(InvoiceInfo invoiceInfo:shipOrder.getInvoiceInfoList()){
					InvoiceInfo info = this.invoiceInfoService.getInvoiceInfoById(invoiceInfo.getId());
					Map<String, Object> invoice = new HashMap<String, Object>();
					invoice.put("header", info.getBillTitle());
					invoice.put("amount", Double.valueOf(info.getBillAccount()));
					invoice.put("content", info.getBillContent() + " ");
					List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
					mapList.add(invoice);
					Map<String, Object> invoices = new HashMap<String, Object>();
					invoices.put("invoice", invoice);
					orderMap.put("invoices", invoices);
				}
			}
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("deliveryOrder", orderMap);
		//resultMap.put("confirmType", 0);
		Map<String,Object> packagesMap=new HashMap<String,Object>();
		
		List<Map<String,Object>> packageList=new ArrayList<Map<String,Object>>();
		Map<String,Object> packageMap=new HashMap<String,Object>();
		//List<Map<String,Object>> packagesItemList=new ArrayList<Map<String,Object>>();
		packageList.add(packageMap);
		packagesMap.put("package", packageList);
		resultMap.put("packages", packagesMap);
		packageMap.put("logisticsCode", shipOrder.getTmsServiceCode());
		//packageMap.put("logisticsName", shipOrder.getExpressCompanyName());
		packageMap.put("expressCode", shipOrder.getTmsOrderCode());
		
		packageMap.put("invoiceNo", "");
		Map<String,Object> extendProps=new HashMap<String, Object>();
		extendProps.put("source", "zc");//表示来源仓库为中仓"zc"
		packageMap.put("extendProps", extendProps);
		
		Map<String,Object> itemsMap=new HashMap<String,Object>();
		List<Map<String,Object>> itemsList=new ArrayList<Map<String,Object>>();
		packageMap.put("items", itemsMap);
		itemsMap.put("item", itemsList);
		List<WmsConsignOrderItem> detailList=null;
		if(shipOrder!=null && shipOrder.getOrderSource()<0){
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("orderId", shipOrder.getId());
			detailList=this.orderItemService.getWmsConsignOrderItemByList(params);
		}else{
			detailList=shipOrder.getOrderItemList();
		}
		Item itemObj=null;
		double weight=0;
		for(int i=0;i<detailList.size();i++){
			WmsConsignOrderItem detail=detailList.get(i);
			itemObj=this.itemService.getItem(detail.getItem().getId());
			String num=String.valueOf(detail.getItemQuantity());
			if(num.length()==0 || num.equals("0")){
				continue;
			}
			Map<String,Object> item=new HashMap<String,Object>();
			item.put("itemCode", itemObj.getItemCode());
			item.put("quantity", Integer.valueOf(num));
			item.put("actualQty", Integer.valueOf(num));
			item.put("planQty", Integer.valueOf(num));
			item.put("itemId", itemObj.getId());
			item.put("ownerCode", user.getOwnerCode());
			item.put("orderLineNo", detail.getOrderItemId());
			weight+=itemObj.getWmsGrossWeight();
			itemsList.add(item);
			//packageList.add(item);
//			ShipOrderPackageItem packageItem=new ShipOrderPackageItem();
//			packageItem.setCreateTime(new Date());
//			packageItem.setI
//			packageItem.setQuantity(Integer.valueOf(num));
		//	packageItem.setDetailId(Integer.valueOf(detailAry[i]));
//			packageLists.add(packageItem);
		}
		packageMap.put("weight",weight );
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
		return xmlStr;
	}
}
