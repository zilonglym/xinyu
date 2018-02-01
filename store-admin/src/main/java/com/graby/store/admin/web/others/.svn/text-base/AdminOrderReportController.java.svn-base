package com.graby.store.admin.web.others;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.admin.web.BaseController;
import com.graby.store.entity.User;
import com.graby.store.remote.ReportRemote;
import com.graby.store.remote.UserRemote;

import jxl.common.Logger;

/**
 * 订单出库数据统计安卓客户端查询接口
 * */
@Controller
@RequestMapping(value="admin/report")
public class AdminOrderReportController extends BaseController{
	
	@Autowired
	private ReportRemote reportRemote;
	
	@Autowired
	private UserRemote userRemote;
	
	public static final Logger logger=Logger.getLogger(AdminOrderReportController.class);
	
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	
	/**
	 * 订单出库统计数据封装回传给安卓客户端
	 * 当日推单数：cunrretNum
	 * 已成功出库订单数：processedNum
	 * 未处理订单数：unfinishNum
	 * @param page
	 * @param rows
	 * @return map
	 * @throws ParseException 
	 * */
	@RequestMapping(value="list")
	@ResponseBody
	public Map<String, Object> ReportList(@RequestParam(value="page",defaultValue="1") int page,
			@RequestParam(value="rows",defaultValue="100") int rows) throws ParseException{		
		logger.debug("安卓订单查询开始！！！");
		if (rows==10) {
			rows=100;
		}	
		Map<String, Object> retMap = new HashMap<String, Object>(); 
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		//当天today
		params.put("endDate", this.plusDay(1, sf.format(new Date())));
		params.put("startDate", sf.format(new Date()));
		//当天推单数量
		Long todayTotalNum = this.reportRemote.findShipOrderNumByParams(params);
		retMap.put("todayTotalNum", todayTotalNum);
		//当天已发货订单数量
		params.put("status", "finished");
		Long todayConfirmNum = this.reportRemote.findShipOrderNumByParams(params);
		retMap.put("todayConfirmNum", todayConfirmNum);
		//当天订单已出库数
		params.put("status", "");
		Long todayChectOutNum = this.reportRemote.findCheckOutNumByParams(params);
		retMap.put("todayChectOutNum", todayChectOutNum);
		
		//昨天yestoday
		params.put("endDate", sf.format(new Date()));
		params.put("startDate", this.plusDay(-1, sf.format(new Date())));
		//当天推单数量
		Long yestodayTotalNum = this.reportRemote.findShipOrderNumByParams(params);
		retMap.put("yestodayTotalNum", yestodayTotalNum);
		//当天已发货订单数量
		params.put("status", "finished");
		Long yestodayConfirmNum = this.reportRemote.findShipOrderNumByParams(params);
		retMap.put("yestodayConfirmNum", yestodayConfirmNum);
		//当天订单已出库数
		params.put("status", "");
		Long yestodayChectOutNum = this.reportRemote.findCheckOutNumByParams(params);
		retMap.put("yestodayChectOutNum", yestodayChectOutNum);
		
		//前天lastday
		params.put("endDate", this.plusDay(-1, sf.format(new Date())));
		params.put("startDate", this.plusDay(-2, sf.format(new Date())));
		//当天推单数量
		Long lastdayTotalNum = this.reportRemote.findShipOrderNumByParams(params);
		retMap.put("lastdayTotalNum", lastdayTotalNum);
		//当天已发货订单数量
		params.put("status", "finished");
		Long lastdayConfirmNum = this.reportRemote.findShipOrderNumByParams(params);
		retMap.put("lastdayConfirmNum", lastdayConfirmNum);
		//当天订单已出库数
		params.put("status", "");
		Long lastChectOutNum = this.reportRemote.findCheckOutNumByParams(params);
		retMap.put("lastChectOutNum", lastChectOutNum);

		logger.debug("安卓订单查询结束！！！");
		return retMap;
	}
	
	/**
	 * 未出库订单明细查询
	 * */
	@RequestMapping(value="detailList")
	@ResponseBody
	public Map<String, Object> ReportDetailList(@RequestParam(value="userId",defaultValue="") String userId,
			@RequestParam(value="type",defaultValue="0") String type) throws ParseException{
		logger.debug("安卓订单明细查询开始！！！");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		List<User> users = this.userRemote.findUsers();
		retMap.put("users", users);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		if ("0".equals(type)) {
			params.put("endDate", this.plusDay(-1, sf.format(new Date())));
			params.put("startDate", this.plusDay(-2, sf.format(new Date())));
		}else if("1".equals(type)){
			params.put("endDate", sf.format(new Date()));
			params.put("startDate", this.plusDay(-1, sf.format(new Date())));
		}else if ("2".equals(type)) {
			params.put("endDate", this.plusDay(1, sf.format(new Date())));
			params.put("startDate", sf.format(new Date()));
		}
	
		/**
		 * 未成功出库订单
		 * 商家：shopName
		 * 物流公司：company
		 * 物流单号：orderNo
		 * 商品明细：items
		 * 更新时间：lastUpdate
		 * 条码：barCode
		 * */
		List<Map<String, Object>> orders = this.reportRemote.findUnfinishOrder(params);
		retMap.put("orders", this.buildListData(orders));
		
		logger.debug("安卓订单明细查询结束！！！");
		return retMap;
	}
	

	/**
	 * 未出库订单信息封装
	 * @param details
	 * @return list
	 * */
	private List<Map<String,Object>> buildListData(List<Map<String, Object>> orders) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> map : orders){
			Map<String, Object> retMap = new HashMap<String, Object>();
			//商家
			String userId = ""+map.get("userId");
			User user = this.userRemote.getUser(Long.parseLong(userId));
			retMap.put("shopName", user.getShopName());
			//打印时间
			Date lastUpdate = (Date) map.get("lastUpdate");
			retMap.put("lastUpdate", sf.format(lastUpdate));
			//创建时间
			Date createDate = (Date) map.get("createDate");
			retMap.put("createDate", sf.format(createDate));
			//物流公司
			retMap.put("company", map.get("company"));
			//物流单号
			retMap.put("orderNo", map.get("orderNo"));
			//商品明细
			retMap.put("items", map.get("items"));
			//条码明细
			retMap.put("barCode", map.get("barCode"));
			
			resultList.add(retMap);
		}
		return resultList;
	}

	/**
	 * 当前时间前移或者后移几天
	 * @param num(间隔天数)
	 * @param newDate（指定时间）
	 * @return string
	 * */
	 private String plusDay(int num,String newDate) throws ParseException{
		 Date currdate = sf.parse(newDate);
	     Calendar ca = Calendar.getInstance();
		 ca.add(Calendar.DATE, num);
		 currdate = ca.getTime();
		 String enddate = sf.format(currdate);
		 return enddate;
	 }
}
