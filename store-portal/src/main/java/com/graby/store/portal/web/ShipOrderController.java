package com.graby.store.portal.web;

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

import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrder.EntryOrderStatus;
import com.graby.store.entity.ShipOrder.SendOrderStatus;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.service.item.ItemService;
import com.graby.store.service.wms.ShipOrderService;

@Controller
@RequestMapping(value = "/store/shipOrder")
public class ShipOrderController {
	@Autowired
	private ShipOrderService orderService;
	@Autowired
	private ItemService itemService;
	
	
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
		List<ShipOrder> orderList=this.orderService.selectShipOrderByList(params);
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
		List<ShipOrder> orderList=this.orderService.selectShipOrderByList(params);
		model.put("orders", orderList);
		return "store/list/stockoutList";
	}
	/**
	 * 发货单列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="deliveryorderList")
	public String deliveryorderList(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		//默认查询所有的未完成发货单
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("status", EntryOrderStatus.ENTRY_WAIT_STORAGE_RECEIVED);
		params.put("type", ShipOrder.TYPE_DELIVER);
		List<ShipOrder> orderList=this.orderService.selectShipOrderByList(params);
		model.put("orders", orderList);
		return "store/list/deliveryorderList";
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
		List<ShipOrderDetail> detailList=this.orderService.shipOrderDetailbyList(params);
		for(int i=0;detailList!=null && i<detailList.size();i++){
			ShipOrderDetail detail=detailList.get(i);
			long itemId=detail.getItem().getId();
			if(itemId==0){
				itemId=1;
			}
			Item item=this.itemService.getItem(itemId);
			detail.setItem(item);
			detail.setInventoryType(this.orderService.processinventoryType(detail.getInventoryType()));
		}
		ShipOrder order=this.orderService.getShipOrder(Long.valueOf(id));
		model.put("order", order);
		model.put("list", detailList);
		return "store/list/shipOrderDetailInfo";
	}
}
