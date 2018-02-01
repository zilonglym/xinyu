package com.graby.store.portal.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.spec.ElGamalKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.base.GroupMap;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeMapping;
import com.graby.store.entity.TradeOrder;
import com.graby.store.portal.util.MySortList;
import com.graby.store.service.item.ItemService;
import com.graby.store.service.trade.TradeService;
import com.graby.store.service.wms.ExpressService;
import com.graby.store.service.wms.ShipOrderService;
import com.graby.store.web.auth.ShiroContextUtils;
import com.graby.store.web.top.TopWmsApi;
import com.graby.store.web.top.TradeTrace;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Refund;

/**
 * 用户交易
 * 
 * @author huabiao.mahb
 */
@Controller
@RequestMapping(value = "/trade")
public class TradeController {

	public static final Logger logger=Logger.getLogger(TradeController.class);
	
	@Autowired
	private TradeService tradeService;

	@Autowired
	private ShipOrderService shipOrderService;

	@Autowired
	private ExpressService expressService;
	
	@Autowired
	private TopWmsApi topWmsApi;
	
	@Autowired
	private ItemService itemService;

	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	/**
	 * 活动专场 用于大批量团购
	 * 
	 * @return
	 */
	@RequestMapping(value = "/special")
	public String special() {
		return "trade/special";
	}

	@RequestMapping(value = "/special/fetch/ajax")
	public String specialResult(@RequestParam(value = "preday") int preday, Model model) throws Exception {
		GroupMap<String, Long> results = tradeService.fetchWaitSendTopTradeTotalResults(preday);
		model.addAttribute("related", results.getList("related"));
		List<Long> unRelated = results.getList("unrelated");
		model.addAttribute("unrelated", unRelated == null ? new ArrayList<Long>() : unRelated);
		return "trade/specialFetch";
	}

//	/**
//	 * 查询所有等待买家发货交易订单（新版）
//	 * 
//	 * useable : 可发送的<br>
//	 * related : 已由物流通处理的<br>
//	 * failed : 未关联或库存不足的<br>
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/waits/fetch")
//	public String fetch(@RequestParam(value = "preday") int preday, Model model) throws Exception {
//
//		/* -1 查询最近一周， 其他指定天数 */
//		GroupMap<String, Trade> tradeMap = preday == -1 ? tradeService.fetchWaitSendTopTrades(0, 1, 2, 3, 4)
//				: tradeService.fetchWaitSendTopTrades(preday);
//		System.err.println(tradeMap);
//		model.addAttribute("useable", tradeMap.getList("useable"));
//		model.addAttribute("related", tradeMap.getList("related"));
//		model.addAttribute("failed", tradeMap.getList("failed"));
//		model.addAttribute("refund", tradeMap.getList("refund"));
//		return "trade/waitsFetch";
//	}

	/**
	 * 查询所有等待买家发货交易订单（新版）
	 * 
	 * useable : 可发送的<br>
	 * related : 已由物流通处理的<br>
	 * failed : 未关联或库存不足的<br>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/waits/fetch")
	public String fetch(@RequestParam(value = "preday") int preday, Model model) throws Exception {

		/* -1 查询最近一周， 其他指定天数 */
		GroupMap<String, Trade> tradeMap = preday == -1 ? tradeService.fetchWaitSendTopTrades(0, 1, 2, 3, 4)
				: tradeService.fetchWaitSendTopTrades(preday);
		System.err.println(tradeMap);
		model.addAttribute("useable", tradeMap.getList("useable"));
		model.addAttribute("related", tradeMap.getList("related"));
		model.addAttribute("failed", tradeMap.getList("failed"));
		model.addAttribute("refund", tradeMap.getList("refund"));
		return "trade/waitsFetch";
	}

	/**
	 * 淘宝订单拉取
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/waits/listData_bak")
	@ResponseBody
	public Map<String,Object> getTrade_bak(
			@RequestParam(defaultValue="1") int page,
			@RequestParam(defaultValue="100") int rows,
			HttpServletRequest request) throws Exception{
			if (rows!=100) {
//				System.err.println(rows);
				rows=100;
			}
//			System.err.println(rows);
			int preday=Integer.parseInt(request.getParameter("preday"));
			String status=request.getParameter("status");
//			System.err.println("preday:"+preday+" status:"+status);
			/* -1 查询最近一周， 其他指定天数 */
			GroupMap<String, Trade> tradeMap = preday == -1 ? tradeService.fetchWaitSendTopTrades(0, 1, 2, 3, 4)
					: tradeService.fetchWaitSendTopTrades(preday);
			Map<String,Object> resultMap=new HashMap<String, Object>();
			if (tradeMap.getList(status)!=null) {
				List<Trade> trades=tradeMap.getList(status);
	 			List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
	 			for(Trade trade:trades){
	 				Map<String, Object> map=new HashMap<String, Object>();
	 				map.put("tid", trade.getTid());
	 				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 				map.put("payTime",format.format(trade.getPayTime()));
	 				map.put("status",trade.getStatus());
	 				map.put("description",status);
	 				map.put("type",trade.getType());
	 				map.put("shippingType",trade.getShippingType());
	 				map.put("lgAgingType",trade.getLgAgingType()+trade.getLgAging());
	 				map.put("buyerNick",trade.getBuyerNick());
	 				map.put("receiverName",trade.getReceiverName());
	 				map.put("phone",trade.getReceiverMobile()+","+trade.getReceiverPhone());
					map.put("address",trade.getReceiverState()+trade.getReceiverCity()+trade.getReceiverDistrict()+trade.getReceiverAddress());
					String items=null;
					for(TradeOrder order:trade.getOrders()){
						String title=order.getTitle();
						String sku=order.getSkuPropertiesName();
						long stockNum=order.getStockNum();
						if (stockNum==-1) {
							String stock="未关联";
							items=items+title+(sku==null?"":sku)+"("+stock+")\n";
						}else if (stockNum==0) {
							String stock="无库存";
							items=items+title+(sku==null?"":sku)+"("+stock+")\n";
						}else {
							items=items+title+(sku==null?"":sku)+"("+stockNum+")\n";
						}
					}
					map.put("items",items);
					map.put("remark","卖家："+trade.getSellerMemo()+"\n"+" 买家："+trade.getBuyerMemo()+trade.getBuyerMessage());
					resultList.add(map);
				}
				resultMap.put("page",page);
				resultMap.put("rows",resultList);
				resultMap.put("total",resultList.size());
//				System.err.println("map:"+resultMap);
			}else {
				System.err.println("tradeMap数据为空！！！！");
			}
			return resultMap;
	}
	
	/**

	 * 待打印订单（电子面单）
	 * 
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/waits/wms")
	public String waitsWms(Model model) {
		String wmsSessionKey = ShiroContextUtils.getWmsSessionKey();
		if (wmsSessionKey == null) {
			return "redirect:" + topWmsApi.getAuthurl();
		}
		// 如果已登录 wsm app session
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", ShiroContextUtils.getCurrentUser().getUserid());
		List<Trade> trades = tradeService.findWaitAuditTradesBy(params);
		model.addAttribute("trades", trades);
		Map<String, String> expressMap = expressService.getExpressWmsMap();
		model.addAttribute("expressCompanys", expressMap);
		return "trade/tradeWaitsWms";
	}

	/**
	 * 退款列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/refunds")
	public String refunds(Model model) throws Exception {
//		List<Refund> refunds = tradeService.fetchRefunds();
//		List<RefundEntry> entrys = new ArrayList<RefundEntry>();
//		if (CollectionUtils.isNotEmpty(refunds)) {
//			for (Refund refund : refunds) {
//				TradeMapping mapping = tradeService.getRelatedMapping(refund.getTid());
//				entrys.add(new RefundEntry(refund, mapping));
//			}
//		}
//		model.addAttribute("refunds", entrys);
//		return "trade/refunds";
		return "trade/tradeRefunds";
	}
	
	/**
	 * 退款列表数据填充
	 * @param model,request
	 * @return map
	 * */
	@RequestMapping(value = "/refunds/listData")
	@ResponseBody
	public Map<String, Object> listData(ModelMap model,HttpServletRequest request) throws Exception {
		Map<String, Object> retMap=new HashMap<String, Object>();
		List<Refund> refunds = tradeService.fetchRefunds();
 		List<RefundEntry> entrys = new ArrayList<RefundEntry>();
 		if (CollectionUtils.isNotEmpty(refunds)) {
 			for (Refund refund : refunds) {
 				TradeMapping mapping = tradeService.getRelatedMapping(refund.getTid());
 				entrys.add(new RefundEntry(refund, mapping));
 			}
 		}
 		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
 		for(RefundEntry entry:entrys){
 			Map<String,Object> map=new HashMap<String, Object>();
 			Refund refund=entry.getRefund();
 			TradeMapping mapping=entry.getMapping();
 			map.put("tid",refund.getTid());
 			map.put("buyerNick",refund.getBuyerNick());
 			map.put("title",refund.getTitle());
 			map.put("orderStatus",refund.getOrderStatus());
 			map.put("status",refund.getStatus());
 			map.put("goodStatus",refund.getGoodStatus());
 			map.put("reason",refund.getReason());
 			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 			map.put("created",sf.format(refund.getCreated()));
 			map.put("id",mapping.getTradeId());
 			resultList.add(map);
 		}
		retMap.put("rows", resultList);
		retMap.put("total", resultList.size());
		return retMap;
	}

	@RequestMapping(value = "/delete/{tradeId}")
	public String delete(@PathVariable("tradeId") Long tradeId) {
		tradeService.deleteTrade(tradeId,-1);
		return "redirect:/trade/refunds";
	}
	
	/**
	 * 删除已退款订单
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request) {
		Map<String,Object> retMap=new HashMap<String, Object>();
		String tradeId=request.getParameter("tradeId");
		tradeService.deleteTrade(Long.parseLong(tradeId),-1);
		retMap.put("data", "success");
		return retMap;
	}
	
	public class RefundEntry {
		public RefundEntry(Refund refund, TradeMapping mapping) {
			this.refund = refund;
			this.mapping = mapping;
		}

		private Refund refund;
		private TradeMapping mapping;

		public Refund getRefund() {
			return refund;
		}

		public TradeMapping getMapping() {
			return mapping;
		}

		public void setRefund(Refund refund) {
			this.refund = refund;
		}

		public void setMapping(TradeMapping mapping) {
			this.mapping = mapping;
		}
	}

	/**
	 * 当前用户仓库已接收订单列表
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/search")
	public String search() {
		return "trade/search";
	}

	@RequestMapping(value = "/search/ajax")
	public String search(@RequestParam(value = "q", defaultValue = "") String q, Model model) {
		List<ShipOrder> orders = shipOrderService.findSendOrderByQ(q);
		List<Entry> entrys = new ArrayList<Entry>();
		for (ShipOrder shipOrder : orders) {
			Entry entry = new Entry();
			Trade tarde = tradeService.getTrade(shipOrder.getTradeId());
			entry.setOrder(shipOrder);
			entry.setTrade(tarde);
			entrys.add(entry);
		}
		model.addAttribute("entrys", entrys);
		return "trade/searchResult";
	}

	/**
	 * 物流信息追踪
	 * 
	 * @param status
	 * @param page
	 * @param model
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/traces", method = RequestMethod.GET)
	public String trace(@RequestParam(value = "page", defaultValue = "1") int page, Model model) throws ApiException {
		Page<TradeTrace> trades = tradeService.findUserTradeTraces(ShiroContextUtils.getUserid(), page, 50);
		model.addAttribute("traces", trades);
		return "trade/tradeTraces";
	}

	@RequestMapping(value = "/close")
	public String close(@RequestParam(value = "tradeIds") Long[] tradeIds) throws ApiException {
		tradeService.closeTrades(tradeIds);
		return "redirect:/trade/traces";
	}

	@RequestMapping(value = "/test")
	public String test(Model model) {
		int len = 10000;
		String[] ii = new String[len];
		for (int i = 0; i < len; i++) {
			ii[i] = Long.valueOf("92108892868727") + i + "";
		}
		model.addAttribute("array", ii);
		return "trade/test";
	}

	public class Entry {
		private Trade trade;
		private ShipOrder order;

		public Trade getTrade() {
			return trade;
		}

		public ShipOrder getOrder() {
			return order;
		}

		public void setTrade(Trade trade) {
			this.trade = trade;
		}

		public void setOrder(ShipOrder order) {
			this.order = order;
		}
	}

	// /**
	// * 根据淘宝交易ID批量创建系统交易订单
	// * 库存记账: 可销售->冻结
	// * @param tids
	// * @return
	// * @throws NumberFormatException
	// * @throws ApiException
	// */
	// @RequestMapping(value = "/send", method = RequestMethod.POST)
	// public String send(@RequestParam(value = "tids") String[] tids) throws
	// NumberFormatException, ApiException {
	// tradeService.createTradesFromTop(tids);
	// return "redirect:/trade/waits";
	// }
	// /**
	// * 商铺方通知用户签收
	// * 库存记账: 冻结->已销售
	// * @param tid
	// * @param redirectAttributes
	// * @return
	// * @throws ApiException
	// */
	// @RequestMapping(value = "/notify", method = RequestMethod.POST)
	// public String notifyUser(@RequestParam(value = "ids", defaultValue = "")
	// Long[] tradeIds,
	// RedirectAttributes redirectAttributes) throws ApiException {
	// shipOrderService.batchNotifyUserSign(tradeIds);
	// return "redirect:/trade/notifys";
	// }
	
	/**
	 * 批量查询淘宝交易订单（多条）
	 * @return
	 */
	@RequestMapping(value = "/waits")
	public String waits() {
		//return "trade/waits";
		return "trade/tradeWaits";
	}
	
	/**
	 * 批量查询淘宝交易订单数据填充
	 * @param page,rows,request
	 * @return map
	 * @throws exception
	 * */
	@RequestMapping(value = "/waits/listData")
	@ResponseBody
	public Map<String,Object> getTrade(
			@RequestParam(defaultValue="1") int page,
			@RequestParam(defaultValue="100") int rows,
			HttpServletRequest request) throws Exception{
			if (rows!=100) {
				rows=100;
			}
			String status=request.getParameter("status");
			String searchText=request.getParameter("q");
			String startTime=request.getParameter("startDate");
			String endTime=request.getParameter("endDate");		
			logger.debug("查询参数：：：：：");
			logger.debug("status："+status);
			logger.debug("searchText："+searchText);
			logger.debug("startTime："+startTime);
			logger.debug("endDate："+endTime);
			Date start  = sdf.parse(startTime);
			Date end  = sdf.parse(endTime);
			
			/* -1 查询最近一周， 其他指定天数 */			
			Map<String, Object> resultMap=null;
			if(status!=null && status.equals("useable")){
				 resultMap = getTaobaoTradeData(page, searchText, start, end, status);
			}else if(status!=null && status.equals("related")){
				resultMap=this.getTradeDateByZC(start, end, searchText, null);
			}
			return resultMap;
	}
	/**
	 * 查询已推送到本系统的订单数据
	 * @param start
	 * @param end
	 * @param searchText
	 * @param status  本地系统的订单状态 存放在Trade表中
	 * @return
	 */
	private Map<String,Object> getTradeDateByZC(Date start,Date end,String searchText,String status){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Map<String,Object> params=new HashMap<String, Object>();
		Map<String,Object> resultMap=new HashMap<String, Object>();
		int page=1;
		int rows=200;
		params.put("beigainTime", sdf.format(start));
		params.put("lastTime", sdf.format(end));
		params.put("userId", ShiroContextUtils.getUserid());
		List<Trade> tradeList=this.tradeService.findTradesBy(params, page, rows);
		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
		for(int i=0;tradeList!=null && i<tradeList.size();i++){
			Map<String, Object> map=new HashMap<String, Object>();
			Trade trade=tradeList.get(i);
			String items="";
			String code="";
			List<TradeOrder> orderList=this.tradeService.fetchTradeOrders(trade.getId());
			for(TradeOrder order:orderList){
				String title=order.getTitle();
				if(title.indexOf(Trade.titleAndCodeSplit)!=-1){
					String ary[] =title.split(Trade.titleAndCodeSplit);
					title=ary[0];
					code=ary[1];
				}
				String sku=order.getSkuPropertiesName();
				long num = order.getNum();
				long stockNum=order.getStockNum();
				if (stockNum==-1) {
					String stock="未关联";
					items=items+title+((sku==null)?"":sku)+"("+num+"件)\n("+stock+")\n";
				}else if (stockNum==0) {
					String stock="无库存";
					items=items+title+((sku==null)?"":sku)+"("+num+"件)\n("+stock+")\n";
				}else {
					items=items+title+((sku==null)?"":sku)+"("+num+"件)\n("+stockNum+")\n";
				}
			}
			map.put("code", code);
			map.put("items",items);
			map.put("tid", trade.getTid());
			map.put("tb", trade.getTradeFrom());
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("payTime",format.format(trade.getPayTime()));
			map.put("status",trade.getStatus());
			map.put("description",status);
			map.put("sellerFlag",trade.getSellerFlag());
			map.put("type",trade.getType());
			map.put("shippingType",trade.getShippingType());
			map.put("lgAgingType",trade.getLgAgingType()+trade.getLgAging());
			map.put("buyerNick",trade.getBuyerNick());
			map.put("receiverName",trade.getReceiverName());
			map.put("phone",((trade.getReceiverMobile()==null)?"":trade.getReceiverMobile())+","+((trade.getReceiverPhone()==null)?"":trade.getReceiverPhone()));
			map.put("address",trade.getReceiverState()+trade.getReceiverCity()+trade.getReceiverDistrict()+trade.getReceiverAddress());
			map.put("seller_flag", trade.getSellerFlag());		
			map.put("remark","卖家："+((trade.getSellerMemo()==null)?"":trade.getSellerMemo())+"\n"+" 买家："+((trade.getBuyerMemo()==null)?"":trade.getBuyerMemo())+((trade.getBuyerMessage()==null)?"":trade.getBuyerMessage()));
			resultList.add(map);
		}
		resultMap.put("page",page);
		resultMap.put("rows",resultList);
		resultMap.put("total",resultList.size());
		return resultMap;
	}
	
	/**
	 * 去淘宝去订单数据
	 * @param page
	 * @param str
	 * @param start
	 * @param end
	 * @param status
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> getTaobaoTradeData(int page, String str, Date start, Date end, String status)
			throws Exception {
		logger.debug("=====进入查询方法=====");
		Map<Long,Object> tidMap=new HashMap<Long, Object>();
		GroupMap<String, Trade> tradeMap = tradeService.fetchWaitSendTopTradesByTime(start, end);
//			preday == -1 ? tradeService.fetchWaitSendTopTrades(0, 1, 2, 3, 4)
//					: tradeService.fetchWaitSendTopTrades(preday);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if (tradeMap.getList(status)!=null) {
			List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
			List<Trade> trades=tradeMap.getList(status);
			//自定义排序
			MySortList<Trade> msList = new MySortList<Trade>();
			msList.sortByMethod(trades, "getPayTime", true);
			String tids="";
			for(Trade trade:trades){
				Map<String, Object> map=new HashMap<String, Object>();
				String items="";
				String codes="";
				for(TradeOrder order:trade.getOrders()){
					String title=order.getTitle();
//					if(title.indexOf(Trade.titleAndCodeSplit)!=-1){
//						String ary[] =title.split(Trade.titleAndCodeSplit);
//						title=ary[0];
//						code=ary[1];
//					}
					
					long num = order.getNum();
					Item item = order.getItem();
					if(item!=null){
						String  code = item.getCode();
						if(code!=null){
							codes =  codes+ code+"("+num+"件)\n";
						}
					}
					String sku=order.getSkuPropertiesName();
					long stockNum=order.getStockNum();
					if (stockNum==-1) {
						String stock="未关联";
						items=items+title+((sku==null)?"":sku)+"("+num+"件)\n("+stock+")\n";
					}else if (stockNum==0) {
						String stock="无库存";
						items=items+title+((sku==null)?"":sku)+"("+num+"件)\n("+stock+")\n";
					}else {
						items=items+title+((sku==null)?"":sku)+"("+num+"件)\n("+stockNum+")\n";
					}
				}
				String  address  = trade.getReceiverState()+trade.getReceiverCity()+trade.getReceiverDistrict()+trade.getReceiverAddress();
				String remark  ="卖家："+((trade.getSellerMemo()==null)?"":trade.getSellerMemo())+"\n"+" 买家："+((trade.getBuyerMemo()==null)?"":trade.getBuyerMemo())+((trade.getBuyerMessage()==null)?"":trade.getBuyerMessage());
				String  tb =   trade.getTradeFrom();
				//如果str为空或者items包含str继续对当前trade操作，否则会直接对下一个trade操作
				if (str==null || str=="" || codes.indexOf(str.trim())>=0  || address.indexOf(str.trim())>=0 ||  remark.indexOf(str.trim())>=0  || tb.indexOf(str.trim())>=0) {
					if(tidMap.get(trade.getTid())!=null){
						/**
						 * 判断当前列表中是否有tid重复的订单，如果有则跳过
						 */
						continue;
					}
					tidMap.put(trade.getTid(), trade);
					map.put("code", codes);
					map.put("items",items);
					map.put("tid", trade.getTid());
					map.put("tb", tb);
					SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					map.put("payTime",format.format(trade.getPayTime()));
					map.put("status",trade.getStatus());
					map.put("description",status);
					map.put("sellerFlag",trade.getSellerFlag());
					map.put("type",trade.getType());
					map.put("shippingType",trade.getShippingType());
					map.put("lgAgingType",trade.getLgAgingType()+trade.getLgAging());
					map.put("buyerNick",trade.getBuyerNick());
					map.put("receiverName",trade.getReceiverName());
					map.put("phone",((trade.getReceiverMobile()==null)?"":trade.getReceiverMobile())+","+((trade.getReceiverPhone()==null)?"":trade.getReceiverPhone()));
					map.put("address",address);
					map.put("seller_flag", trade.getSellerFlag());		
					map.put("remark",remark);
					resultList.add(map);
				}					
			}
			resultMap.put("page",page);
			resultMap.put("rows",resultList);
			resultMap.put("total",resultList.size());
		}else {
			resultMap.clear();
			List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
			resultMap.put("page",page);
			resultMap.put("rows",resultList);
			resultMap.put("total",resultList.size());
			System.err.println("tradeMap数据为空！！！！");
		}
		logger.debug("=====进入查询结束法=====");
		return resultMap;
	}
	
	public static void main(String[] args) {
		
//		String  items    = "小熊绞肉机 家用小型电动多功能碎肉机 搅馅切菜碎菜机打肉蒜泥机 颜色分类:金色(1)件";
//		System.out.println("小熊绞肉机 家用小型电动多功能碎肉机 搅馅切菜碎菜机打肉蒜泥机 颜色分类:金色(1)件");
//		String  str =  "菜机";
//		
//		if (str==null||str=="" || items.indexOf(str)>0) {
//			System.out.println(11111);
//		}
		
		
		System.out.println(16%7);
			
	}
	
	
	
	
	/**
	 * 当前用户仓库已接收订单列表
	 * @return
	 */
	@RequestMapping(value = "/received")
	public String received(Model model){
		return "trade/tradeReceived";
	}
	
	/**
	 * 当前用户仓库已接收订单列表数据填充
	 * @param page,rows,model,request
	 * @return map
	 * @throws apiException
	 */
	@RequestMapping(value = "/received/listData")
	@ResponseBody
	public Map<String, Object> ListData(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "100") int rows,
			ModelMap model,HttpServletRequest request) throws ApiException {
		if(rows!=100){
			rows  =  100;
		}
//		String status=request.getParameter("status");
		String q=request.getParameter("q");
		Map<String,Object> retMap=new HashMap<String, Object>();
		Page<Trade> trades = tradeService.getTradesTBnumber(ShiroContextUtils.getUserid(), q.trim(), page, rows);
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		for(Trade trade:trades){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("tid", trade.getTid());
			map.put("tb", trade.getTradeFrom());
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("payTime",format.format(trade.getPayTime()));
			map.put("status",trade.getStatus());
			map.put("description","");
			map.put("shippingType",trade.getShippingType());
			map.put("lgAgingType",trade.getLgAgingType()+trade.getLgAging());
			map.put("buyerNick",trade.getBuyerNick());
			map.put("receiverName",trade.getReceiverName());
			map.put("phone",trade.getReceiverMobile()+","+trade.getReceiverPhone());
			map.put("address",trade.getReceiverState()+trade.getReceiverCity()+trade.getReceiverDistrict()+trade.getReceiverAddress());
			ShipOrder order=this.shipOrderService.getSendShipOrderByTradeId(trade.getId());
			if (order!=null) {
				map.put("items", order.getItems());
				map.put("orderno", (order.getExpressOrderno()==null?"":order.getExpressOrderno()));
				map.put("company", (order.getExpressCompany()==null?"":order.getExpressCompany()));
			}else {
				String items=null;
				List<TradeOrder> orders=this.tradeService.fetchTradeOrders(trade.getId());
//				System.err.println(orders.size());
				for(TradeOrder tradeOrder:orders){
					Item item=this.itemService.getItem(tradeOrder.getItem().getId());
					items=items+item.getTitle()+" "+(item.getSku()==null?"":item.getSku())+"("+tradeOrder.getNum()+")件|";
				}
//				System.err.println("items:"+items);
				map.put("items",items);
			}
			map.put("remark","卖家："+(trade.getSellerMemo()==null?"":trade.getSellerMemo())+" 买家："+(trade.getBuyerMemo()==null?"":trade.getBuyerMemo())+(trade.getBuyerMessage()==null?"":trade.getBuyerMessage()));
			resultList.add(map);
		}
		retMap.put("rows", resultList);
		retMap.put("page", page);
		retMap.put("total", resultList.size());
		return retMap;
	}
	
	/**
	 * 等待用户签收列表
	 * @return
	 */
	@RequestMapping(value = "/notifys")
	public String notifyTrades(Model model) throws ApiException {
//		Page<Trade> trades = tradeService.findUserTrades(ShiroContextUtils.getUserid(),
//				Trade.Status.TRADE_WAIT_EXPRESS_NOFITY, page, 15);
//		model.addAttribute("trades", trades);
//		return "trade/tradeNotifys";
		return "trade/notifys";
	}
	
	/**
	 * 等待用户签收列表数据填充
	 * @param page,rows,request,model
	 * @return map
	 * @throws apiException
	 */
	@RequestMapping(value = "/notifys/listData")
	@ResponseBody
	public Map<String, Object> ListData(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "100") int rows,HttpServletRequest request,
			ModelMap model)throws Exception {
		if (rows!=100) {
			rows=100;
		}
		Map<String, Object> retMap=new HashMap<String, Object>();
		logger.debug("通知用户签收:userId"+ShiroContextUtils.getUserid());
		Page<Trade> trades = tradeService.findUserTrades(ShiroContextUtils.getUserid(),
				Trade.Status.TRADE_WAIT_EXPRESS_NOFITY, page, rows);
		logger.debug("通知用户签收列表查询完成，数据:"+trades.getTotalPages());
		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
		for(Trade trade:trades){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("tid",trade.getId());
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("payTime",sf.format(trade.getPayTime()));
			map.put("shippingType",trade.getShippingType());
			map.put("tradeFrom",trade.getTradeFrom());
			map.put("buyerNick",(trade.getBuyerNick()==null)?"":trade.getBuyerNick());
			map.put("receiverName",trade.getReceiverName());
			map.put("phone",((trade.getReceiverPhone()==null)?"":trade.getReceiverPhone())+","+((trade.getReceiverMobile()==null)?"":trade.getReceiverMobile()));
			map.put("address",trade.getReceiverState()+trade.getReceiverCity()+trade.getReceiverDistrict()+trade.getReceiverAddress());
			ShipOrder order=this.shipOrderService.getSendShipOrderByTradeId(trade.getId());
			map.put("items", order!=null?order.getItems():"");
			map.put("company", order!=null?order.getExpressCompany():"");
			map.put("orderno", order!=null ?order.getExpressOrderno():"");
			resultList.add(map);
		}
		logger.debug("通知用户签收列表重构完成，数据:"+resultList.size());
		retMap.put("rows", resultList);
		retMap.put("page", page);
		retMap.put("total", trades.getTotalElements());
		return retMap;
	}
}
