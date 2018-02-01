package com.graby.store.admin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.internal.core.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.graby.store.admin.util.CityJson;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeOrder;
import com.graby.store.entity.User;
import com.graby.store.remote.ExpressRemote;
import com.graby.store.remote.InventoryRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.ShipOrderRemote;
import com.graby.store.remote.TradeRemote;
import com.graby.store.remote.UserRemote;
import com.graby.store.service.inventory.Accounts;
import com.taobao.api.ApiException;

@Controller
@RequestMapping(value = "/trade/")
public class AdminTradeController {

	@Autowired
	private UserRemote userRemote;

	@Autowired
	private TradeRemote tradeRemote;

	@Autowired
	private InventoryRemote inventoryRemote;

	@Autowired
	private ItemRemote itemRemote;

	@Autowired
	private ShipOrderRemote shipOrderRemote;

	@Autowired
	private ExpressRemote expressRemote;

	/**
	 * 查询所有待审核订单
	 * 
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "waits", method = RequestMethod.GET)
	public String waitAudits(@RequestParam(value = "userId", defaultValue = "0") Long userId, Model model)
			throws ApiException {

		List<User> users = userRemote.findAll();
		model.addAttribute("users", users);
		model.addAttribute("userId", userId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		List<Trade> trades = tradeRemote.findWaitAuditTradesBy(params);
		model.addAttribute("trades", trades);
		return "/admin/tradeWaits";
	}

	@RequestMapping(value = "waits/search", method = RequestMethod.GET)
	public String waitAuditsHead(Model model) throws ApiException {

		List<User> users = userRemote.findAll();
		model.addAttribute("users", users);

		CityJson cj = new CityJson();
		List<Map<String, String>> citys = tradeRemote.findWaitAuditCitys();
		if (CollectionUtils.isNotEmpty(citys)) {
			for (Map<String, String> cityMap : citys) {
				cj.putCity(cityMap.get("receiverState"), cityMap.get("receiverCity"));
			}
		}
		model.addAttribute("cityJson", cj.getJson());

		return "/admin/tradeWaitSearch";
	}

	@RequestMapping(value = "ajax/waits", method = RequestMethod.POST)
	public String waitAuditsBatch(@RequestParam(value = "userId", defaultValue = "0") Long userId,
			@RequestParam(value = "state", defaultValue = "") String state,
			@RequestParam(value = "city", defaultValue = "") String city,
			@RequestParam(value = "itemTitle", defaultValue = "") String itemTitle, Model model) throws ApiException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		if (StringUtils.isNotEmpty(state) && !state.equals("请选择")) {
			params.put("receiverState", state);
		}
		if (StringUtils.isNotEmpty(city) && !city.equals("请选择") && !city.equals("null")) {
			params.put("receiverCity", city);
		}
		if (StringUtils.isNotBlank(itemTitle)) {
			params.put("itemTitle", "%" + itemTitle + "%");
		}
		List<Trade> trades = tradeRemote.findWaitAuditTradesBy(params);
		model.addAttribute("trades", trades);

		Map<String, String> expressMap = expressRemote.getExpressMap();
		model.addAttribute("expressCompanys", expressMap);
		return "/admin/tradeWaitsDetail";
	}

	/**
	 * 审核订单页面
	 * 
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "audit/{id}", method = RequestMethod.GET)
	public String audit(@PathVariable("id") Long id, Model model) throws ApiException {
		Trade trade = tradeRemote.getTrade(id);
		List<TradeOrder> orders = trade.getOrders();
		for (TradeOrder order : orders) {
			// 放置库存信息， 目前只支持单库存，如未来支持多库存这里要做改造
			Long itemId = order.getItem().getId();
			if (itemId == null) {
				order.setStockNum(-1);
			} else {
				// 已关联的设置库存
				long stockNum = inventoryRemote.getValue(1L, itemId, Accounts.CODE_SALEABLE);
				order.setStockNum(stockNum);
				Item item = itemRemote.getItem(itemId);
				order.setItem(item);
			}
		}
		model.addAttribute("trade", trade);
		Map<String, String> expressMap = expressRemote.getExpressMap();
		model.addAttribute("expressCompanys", expressMap);
		return "/admin/tradeAudit";
	}

	/**
	 * 删除订单
	 * 
	 * @param tradeId
	 * @return
	 */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String deleteTrade(@PathVariable(value = "id") Long tradeId) {
		tradeRemote.deleteTrade(tradeId);
		return "redirect:/trade/waits";
	}

	/**
	 * 删除订单
	 * 
	 * @param tradeId
	 * @return
	 */
	@RequestMapping(value = "reset/{id}", method = RequestMethod.GET)
	public String resetTrade(@PathVariable(value = "id") Long tradeId) {
		tradeRemote.reset(tradeId);
		return "redirect:/trade/unfinish";
	}
	
	/**
	 * 查询被拆分订单
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "splited", method = RequestMethod.GET)
	public String splited(Model model) {
		List<Trade> trades = tradeRemote.findSplitedTrades();
		model.addAttribute("trades", trades);
		return "/admin/tradeSplited";
	}

	/**
	 * 查询被拆分订单
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "merge/{hash}", method = RequestMethod.GET)
	public String merge(@PathVariable(value = "hash") String mergeHash, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hash", mergeHash);
		List<Trade> trades = tradeRemote.findWaitAuditTradesBy(params);
		model.addAttribute("trades", trades);
		return "/admin/tradeMerge";
	}

	@RequestMapping(value = "merge")
	public String doMerge(@RequestParam("tradeIds") Long[] tradeIds, Model model) {
		Assert.isTrue(tradeIds != null && tradeIds.length>=2);
		tradeRemote.mergeTrade(tradeIds);
		return "/admin/success";
	}

	/**
	 * 删除订单
	 * 
	 * @param tradeId
	 * @return
	 */
	@RequestMapping(value = "split/{tradeId}/{orderId}", method = RequestMethod.GET)
	public String splitTrade(@PathVariable(value = "tradeId") Long tradeId,
			@PathVariable(value = "orderId") Long orderId) {
		tradeRemote.splitTrade(tradeId, orderId);
		return "redirect:/trade/audit/" + tradeId;
	}

	/**
	 * 审核通过，创建出库单。
	 */
	@RequestMapping(value = "mkship", method = RequestMethod.POST)
	public String mkship(@RequestParam("tradeId") Long tradeId,
			@RequestParam(value = "expressCompany", defaultValue = "-1") String expressCompany, Model model) {
		ShipOrder sendOrder = auditOneTrade(tradeId, expressCompany);
		model.addAttribute("sendOrder", sendOrder);
		return "redirect:/trade/waits?userId=" + sendOrder.getCreateUser().getId();
	}

	private ShipOrder auditOneTrade(Long tradeId, String expressCompany) {
		ShipOrder sendOrder = tradeRemote.createSendShipOrderByTradeId(tradeId);
		if (!expressCompany.equals("-1")) {
			shipOrderRemote.chooseExpress(sendOrder.getId(), expressCompany);
		}
		return sendOrder;
	}

	@RequestMapping(value = "mkships")
	public String batchMkship(@RequestParam("tradeIds") Long[] tradeIds,
			@RequestParam(value = "expressCompany", defaultValue = "-1") String expressCompany, Model model) {
		for (Long tradeId : tradeIds) {
			auditOneTrade(tradeId, expressCompany);
		}
		return "/admin/success";
	}

	@RequestMapping(value = "special/waits")
	public String special() {
		return "/admin/tradeSpecialAudit";
	}

	/**
	 * 活动专场 审核所有待处理交易订单
	 * 
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "mkship/all", method = RequestMethod.GET)
	public String auditAll() throws ApiException {
		tradeRemote.createAllSendShipOrder(1L);
		return "redirect:/trade/waits";
	}

	/**
	 * 查询所有待处理出库单
	 * 
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "send/waits", method = RequestMethod.GET)
	public String sendWaits(Model model) throws ApiException {
		List<ShipOrder> sendOrders = shipOrderRemote.findSendOrderWaits();
		model.addAttribute("orders", sendOrders);
		return "/admin/sendOrderWaits";
	}

	/**
	 * 手工设置运单
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "send/do/{id}", method = RequestMethod.GET)
	public String doSendOrderForm(@PathVariable("id") Long orderId, Model model) {
		ShipOrder sendOrder = shipOrderRemote.getShipOrder(orderId);
		model.addAttribute("order", sendOrder);
		model.addAttribute("expressCompanys", expressRemote.getExpressMap());
		return "/admin/sendOrderForm";
	}

	/**
	 * 查询所有待拣货出库单
	 * 
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "send/pickings", method = RequestMethod.GET)
	public String pickingList(Model model) {
		List<ShipOrder> sendOrders = shipOrderRemote.findSendOrderByStatus(1L,
				ShipOrder.SendOrderStatus.WAIT_EXPRESS_PICKING);
		model.addAttribute("orders", sendOrders);
		return "/admin/sendOrderPickings";
	}

	/**
	 * 重置拣货单为运单打印状态
	 * 
	 * @param ids
	 * @return
	 * @throws NumberFormatException
	 * @throws ApiException
	 */
	@RequestMapping(value = "send/express")
	public String reExpress(@RequestParam(value = "ids", defaultValue = "") Long[] ids) {
		shipOrderRemote.reExpressShipOrder(ids);
		return "redirect:/trade/send/pickings";
	}

	/**
	 * 拣货单打印(PDF)
	 * 
	 * @param ids
	 * @return
	 * @throws NumberFormatException
	 * @throws ApiException
	 */
	@RequestMapping(value = "send/pick/report")
	public ModelAndView pickReport(@RequestParam(value = "ids", defaultValue = "") Long[] ids,
			@RequestParam(value = "format", defaultValue = "pdf") String format,
			@RequestParam(value = "type", defaultValue = "minPickReport") String type) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put(
				"data",
				type.equals("minPickReport") ? shipOrderRemote.findSendOrders(ids) : shipOrderRemote
						.findSendOrdersGroup(ids));
		model.put("format", format);
		return new ModelAndView(type, model);
	}

	/**
	 * 订单已拣货，审核通过提交到系统。（批量 不建议）
	 * 
	 * @param ids
	 * @return
	 * @throws NumberFormatException
	 * @throws ApiException
	 */
	@RequestMapping(value = "send/submits")
	public String submits(@RequestParam(value = "ids", defaultValue = "") Long[] ids,
			@RequestParam(value = "action", defaultValue = "send/pickings") String action)
			throws NumberFormatException, ApiException {
		shipOrderRemote.submits(ids);
		return "redirect:/trade/" + action;
	}

	@RequestMapping(value = "ship/audit")
	public String auditForm() {
		return "admin/shipAuditForm";
	}

	@RequestMapping(value = "ship/audit/ajax")
	public String auditOrder(@RequestParam(value = "q", defaultValue = "") String q, Model model) {
		List<ShipOrder> orders = shipOrderRemote.findSendOrderByQ(q);
		List<Entry> entrys = new ArrayList<Entry>();
		for (ShipOrder shipOrder : orders) {
			Entry entry = new Entry();
			Trade tarde = tradeRemote.getTrade(shipOrder.getTradeId());
			entry.setOrder(shipOrder);
			entry.setTrade(tarde);
			entrys.add(entry);
		}
		model.addAttribute("entrys", entrys);
		return "admin/shipAuditOrder";
	}

	@RequestMapping(value = "ship/audit/done")
	public String auditdone() {
		return "admin/shipAuditDone";
	}

	/**
	 * (仓库方)提交出库单，等待用户签收。
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "send/submit", method = RequestMethod.POST)
	public String submitOrder(ShipOrder order, Model model) throws ApiException {
		shipOrderRemote.submitSendOrder(order);
		return "redirect:/trade/send/waits";
	}

	/**
	 * 等待用户签收订单列表
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "sign/waits")
	public String signWaits(@RequestParam(value = "q", defaultValue = "") String q, Model model) {
		List<ShipOrder> sendOrders = shipOrderRemote.findSendOrderSignWaits();
		model.addAttribute("orders", sendOrders);
		return "/admin/signWaits";
	}

	/**
	 * 用户签收页面
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "send/sign/{id}", method = RequestMethod.GET)
	public String signSendOrder(@PathVariable("id") Long orderId, Model model) {
		ShipOrder sendOrder = shipOrderRemote.getShipOrder(orderId);
		model.addAttribute("order", sendOrder);
		return "/admin/signForm";
	}

	/**
	 * 点击用户签收
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "sign/submit/{id}", method = RequestMethod.GET)
	public String submitSign(@PathVariable(value = "id") Long orderId, Model model) {
		ShipOrder sendOrder = shipOrderRemote.signSendOrder(orderId);
		model.addAttribute("order", sendOrder);
		return "redirect:/trade/sign/waits";
	}

	/**
	 * 查询所有未关闭订单
	 * 
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "unfinish", method = RequestMethod.GET)
	public String unfinish(@RequestParam(value = "page", defaultValue = "1") int page, Model model) throws ApiException {
		Page<Trade> trades = tradeRemote.findUnfinishedTrades(page, 10);
		model.addAttribute("trades", trades);
		return "/admin/tradeUnfinishs";
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
}
