package com.xinyu.controller.print;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xinyu.common.BaseController;
import com.xinyu.model.base.Centro;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.TmsOrder;
import com.xinyu.service.system.CentroService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.trade.ReceiverInfoService;
import com.xinyu.service.trade.ShipOrderService;
import com.xinyu.service.trade.TmsOrderService;

@Controller
@RequestMapping(value="print")
public class PrintController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(PrintController.class);
	
	@Autowired
	private ShipOrderService shipOrderService;
	
	@Autowired
	private TmsOrderService tmsOrderService;
	
	@Autowired
	private ReceiverInfoService receiverInfoService;
	
	@Autowired
	private UserService userService;
	
//	@Autowired
//	private CentroService centroService;
	
//	/**
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value="ems")
//	public String emsPrint(ModelMap model){
//		String id=this.getString("id");
//		ShipOrder order=this.shipOrderService.findShipOrderById(id);
//		ReceiverInfo receiverInfo=  this.receiverInfoService.getReceiverInfoById(order.getReceiverInfo().getId());
//		order.setReceiverInfo(receiverInfo);
//		Centro centro=this.centroService.getCentroById(order.getCu());
//		model.put("centro", centro);
//		model.put("order", order);
//		return "admin/print/ems";
//	}
	
	/**
	 * 批量打印EMS普通面单
	 */
	@RequestMapping(value = "ems")
	public String toPrint(ModelMap model,HttpServletRequest request){
		
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();

		String arry = request.getParameter("ids");
		
		String[] ids = arry.split(",");
		
		for (int i = 0; i < ids.length; i++) {
			
			String id = ids[i];
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			TmsOrder tmsOrder = this.tmsOrderService.getTmsOrderById(id);
			
			ShipOrder shipOrder = this.shipOrderService.findShipOrderById(tmsOrder.getOrder().getId());
			
			ReceiverInfo receiverInfo = this.receiverInfoService.getReceiverInfoById(shipOrder.getReceiverInfo().getId());
			
			User user = this.userService.getUserById(shipOrder.getUser().getId());
				
			//寄件人信息
			map.put("senderName", user.getSubscriberName());
			map.put("senderPhone", user.getSubscriberMobile());
			map.put("senderAddress", "湖南省湘潭市岳塘区双马镇金钢人防[允许先验收后签收]");
				
			//收件人信息
			map.put("receiverName", receiverInfo.getReceiverName());
			map.put("receiverNick", receiverInfo.getReceiverNick()!=null?receiverInfo.getReceiverNick():receiverInfo.getReceiverName());
			map.put("receiverPhone", receiverInfo.getReceiverPhone()!=null?receiverInfo.getReceiverPhone():receiverInfo.getReceiverMobile());
			map.put("receiverMobile", receiverInfo.getReceiverMobile()!=null?receiverInfo.getReceiverMobile():receiverInfo.getReceiverPhone());
			map.put("receiverState", receiverInfo.getReceiverProvince());
			map.put("receiverCity", receiverInfo.getReceiverCity());
			map.put("receiverArea", receiverInfo.getReceiverArea()!=null?receiverInfo.getReceiverArea():"");
			
			String address = receiverInfo.getReceiverProvince()+receiverInfo.getReceiverCity()+receiverInfo.getReceiverArea()+receiverInfo.getReceiveTown()+receiverInfo.getReceiverAddress();
			map.put("receiverAddress", address);
				
			//商品明细
			map.put("items", tmsOrder.getItems()!=null?tmsOrder.getItems():this.shipOrderService.bulidItemsDataByTms(tmsOrder));
				
			//打印时间
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("printDate", sf.format(new Date()));
				
			//重量
			map.put("weight", tmsOrder.getPackageWeight());
			
			logger.error("map:"+map);
			
			results.add(map);
		}
		
		logger.error("results:"+results);
		
		model.put("results", results);
		
		return "admin/waybill/print";
	}
}
