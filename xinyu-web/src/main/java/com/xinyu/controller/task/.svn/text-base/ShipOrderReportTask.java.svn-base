package com.xinyu.controller.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyu.common.BaseController;
import com.xinyu.common.util.XmlUtil;
import com.xinyu.model.system.enums.OperatorModel;
import com.xinyu.model.system.enums.OrderStatusEnum;
import com.xinyu.model.trade.CheckOut;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderOperator;
import com.xinyu.model.trade.ShipOrderReport;
import com.xinyu.service.system.CheckOutService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.trade.ShipOrderOperatorService;
import com.xinyu.service.trade.ShipOrderReportService;
import com.xinyu.service.trade.ShipOrderService;

/**
 * 订单报表系统定时器
 * @author yangmin
 * 2017年11月30日
 *
 */
@Lazy(value=false)
@Controller
@RequestMapping(value="shipOrderReportTask")
public class ShipOrderReportTask extends BaseController {
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdftt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	
	private final static Logger logger = Logger.getLogger(ShipOrderReportTask.class);
	@Autowired
	private ShipOrderService shipOrderService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ShipOrderOperatorService operatorService;
	@Autowired
	private CheckOutService checkOutService;
	@Autowired
	private ShipOrderReportService shipOrderReportService;
	
	
	@RequestMapping(value="controller")
	@ResponseBody
	public void shipOrderOperatorController(){
		shipOrderOperatorTime();
	}
	/**
	 * 订单操作时间处理
	 */
	@Scheduled(cron="0 0 23 * * ?")
	public void shipOrderOperatorTime(){
		/**
		 * 1.查询要处理的订单并循环处理所有的订单
		 * 2.查询操作记录得到发货时间
		 * 3.查询验货记录得到验货时间
		 * 4.计算时间的间隔
		 */
		logger.error("十一点定时任务开启!");
		String date=sdf.format(new Date());
		String startDate=date+" 00:00:00";
		String endDate=date+" 23:59:00";
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("status", OrderStatusEnum.WMS_FINASH);
		logger.error("shipOrderOperatorTime:"+startDate+"----"+endDate);
		List<ShipOrder> tmsOrderList=this.shipOrderService.getShipOrderListByAll(params);
		logger.error("订单记录数据:"+tmsOrderList.size());
		for(ShipOrder order:tmsOrderList){
			ShipOrderReport report=new ShipOrderReport();
			report.generateId();
			report.setOrder(order);
			report.setExpressCompany(order.getTmsServiceCode());
			report.setExpressOrderNo(order.getTmsOrderCode());
			report.setOrderCreateTime(order.getCreateTime());
			//查询订单确认发货时间
			params.clear();
			params.put("operatorModel", OperatorModel.TRADE_CONFIRM);
			params.put("orderId", order.getId());
			List<ShipOrderOperator> operatorList=this.operatorService.getShipOrderOperatorByList(params);
			if(operatorList!=null && operatorList.size()>0){
				ShipOrderOperator operator=operatorList.get(0);
				report.setConfirmDate(operator.getOperatorDate());
			}else{
				/**
				 * 如果没有找到确认发货的操作记录，则认为此订单没有进行打单与确认发货，
				 * 保存此对象，进行下一条的操作。
				 */
				report.setStatus(OrderStatusEnum.WMS_ACCEPT);
				saveShipOrderReport(report);
				continue;
			}
			/**
			 * 在checkOut表中，查询验货出库的数据
			 */
			params.clear();
			params.put("orderCode", order.getTmsOrderCode());
			params.put("status", "SUCCESS_TRADE");
			List<CheckOut> checkOutList=this.checkOutService.findCheckOutPage(params, 1, 10);
			if(checkOutList!=null && checkOutList.size()>0){
				CheckOut checkOut=checkOutList.get(0);
				report.setCheckOrderTime(checkOut.getCreateDate());
			}else{
				report.setStatus(OrderStatusEnum.WMS_CONFIRM);
				saveShipOrderReport(report);
				
				continue;
			}
			/**
			 * 计算时间
			 */
			int operatorTime=new Long((report.getCheckOrderTime().getTime()-report.getConfirmDate().getTime())/1000).intValue();
			report.setOperatorTime(operatorTime);
			int printTime=new Long((report.getConfirmDate().getTime()-report.getOrderCreateTime().getTime())/1000).intValue();
			report.setPrintTime(printTime);
			report.setStatus(OrderStatusEnum.WMS_FINASH);
			
			this.saveShipOrderReport(report);
		}
		
		logger.error("十一点定时任务结束!");
	}
	
	/**
	 * 订单异常信息排查
	 */
	@Scheduled(cron="0 0 22 * * ?")
	public void shipOrderException(){
		
		String date=sdf.format(new Date());
		String startDate=date+"00:00:00";
		String endDate=date+"23:00:00";
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("status", OrderStatusEnum.WMS_FINASH);
		logger.error("shipOrderOperatorTime:"+startDate+"----"+endDate);
		List<ShipOrder> tmsOrderList=this.shipOrderService.getShipOrderListByAll(params);
		//1.判断拆单是否准确
		for(int i=0;tmsOrderList!=null && i<tmsOrderList.size();i++){
			ShipOrder order=tmsOrderList.get(i);
		}
		
	}
	
	
	private void searchLogistInfo(ShipOrderReport report){
		Map<String,Object> xmlPar=new HashMap<String, Object>();
		xmlPar.put("mailNo", report.getExpressOrderNo());
		xmlPar.put("cpCode", report.getExpressCompany());
		xmlPar.put("appName", "");
		String xmlStr= XmlUtil.converterPayPalm(xmlPar, "request");
//		HttpUtil.
	}
	
	
	
	private void saveShipOrderReport(ShipOrderReport report){
		report.setCreateTime(new Date());
		this.shipOrderReportService.insertShipOrderReport(report);
	}
}
