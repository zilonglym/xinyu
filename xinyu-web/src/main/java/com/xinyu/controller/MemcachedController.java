package com.xinyu.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyu.common.BaseController;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.ItemGroup;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.RdsConstants;
import com.xinyu.model.base.User;
import com.xinyu.model.qm.StoreConstants;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderGroup;
import com.xinyu.model.trade.ShipOrderStop;
import com.xinyu.model.trade.TmsOrder;
import com.xinyu.model.trade.TmsOrderItem;
import com.xinyu.model.trade.WmsConsignOrderItem;
import com.xinyu.service.caoniao.WmsConsignOrderConfirmService;
import com.xinyu.service.system.ItemGroupService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.trade.ReceiverInfoService;
import com.xinyu.service.trade.ShipOrderGroupService;
import com.xinyu.service.trade.ShipOrderService;
import com.xinyu.service.trade.ShipOrderStopService;
import com.xinyu.service.trade.TmsOrderService;
import com.xinyu.service.trade.WmsConsignOrderItemService;
import com.xinyu.service.util.IRedisProxy;
import com.xinyu.service.util.ObjectTranscoder;
import com.xinyu.service.util.RedisUtil;
import redis.clients.jedis.Jedis;

@Controller
@RequestMapping(value = "memcached")
public class MemcachedController extends BaseController {

	public final static String memcached_checkOut = "CheckOut";

	public final static String memcached_success_CheckOut = "success";

	public final static String memcached_item = "Item";
	public final static String memcached_user = "User";

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private ShipOrderService shipOrderService;

	@Autowired
	private TmsOrderService tmsOrderService;

	@Autowired
	private ShipOrderGroupService shipOrderGroupService;

	@Autowired
	private ItemGroupService itemGroupService;

	@Autowired
	private ReceiverInfoService receiverInfoService;
	@Autowired
	private WmsConsignOrderItemService orderItemService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private UserService userService;
	@Autowired
	private IRedisProxy redisProxy;
	@Autowired
	private ShipOrderStopService stopService;

	/**
	 * 刷新订单信息
	 */
	@RequestMapping(value = "refreshOrder")
	@ResponseBody
	public Map<String, Object> initShipOrderInfo() {
		String orderExpressNo = getString("expressNo");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String startDate = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, -3);
		String endDate = sdf.format(cal.getTime());
		params.put("tmsOrderCode", orderExpressNo);
		List<ShipOrder> orderList = this.shipOrderService.findShipOrderByList(params);
		ShipOrder order = orderList == null || orderList.size() == 0 ? null : orderList.get(0);
		/**
		 * modify 2017-11-7
		 */

		if (order != null && StringUtils.isNotEmpty(order.getTmsOrderCode())) {
			params.clear();
			params.put("orderId", order.getId());
			List<WmsConsignOrderItem> itemList = this.orderItemService.getWmsConsignOrderItemByList(params);
			Map<String, Object> memcachedMap = new HashMap<String, Object>();
			int zcIndex = 0, zpIndex = 0;
			for (int j = 0; itemList != null && j < itemList.size(); j++) {
				WmsConsignOrderItem detail = itemList.get(j);
				Item item = this.itemService.getItem(detail.getItem().getId());
				if (item != null && item.getItemType() != null && item.getItemType().equals("ZC")) {
					zcIndex += detail.getItemQuantity();
				}
				if (item != null && item.getItemType() != null && item.getItemType().equals("ZP")) {
					zpIndex += detail.getItemQuantity();
				}
				memcachedMap.put(item.getBarCode(),
						item.getId() + "||" + detail.getItemQuantity() + "||" + item.getItemType());
			}
			memcachedMap.put("zcIndex", zcIndex);
			memcachedMap.put("zpIndex", zpIndex);
			memcachedMap.put("orderInfo",
					order.getId() + "||" + order.getTotalWeight() + "||" + order.getUser().getId());
			memcachedMap.put("orderDate", sdf.format(order.getCreateTime()));
			memcachedMap.put("state", "");
			memcachedMap.put("totalWeight", String.valueOf(order.getTotalWeight()));
			memcachedMap.put("user", String.valueOf(order.getUser().getId()));
			this.redisProxy.set(order.getTmsOrderCode().getBytes(), ObjectTranscoder.serialize(memcachedMap));
			logger.error("订单刷新补录:" + order.getTmsOrderCode() + "||" + memcachedMap);
			resultMap.putAll(memcachedMap);
		}
		return resultMap;
	}

	@RequestMapping(value = "itemInfo")
	public void initItemAndUserInfo() {
		Jedis jedis = RedisUtil.getJedis();
		logger.error("初始化商品与商家信息");
		int index = 0;
		Map<String, String> userMap = null;
		Map<String, String> itemMap = null;
		List<User> userList = this.userService.getUserBySearch(null);
		for (int i = 0; userList != null && i < userList.size(); i++) {
			User user = userList.get(i);
			userMap = new HashMap<String, String>();
			userMap.put("id", user.getId());
			userMap.put("shopName", user.getSubscriberName());
			userMap.put("code", String.valueOf(user.getTbUserId()));
			userMap.put("tbUserId", String.valueOf(user.getTbUserId()));
			// jedis.hmset(memcached_user + user.getId(), userMap);
			this.redisProxy.set((memcached_user + user.getId()).getBytes(), ObjectTranscoder.serialize(userMap));
			index++;
		}
		logger.error("初始化商品信息:" + index);
		List<Item> itemList = this.itemService.getItemByList(null);
		for (int i = 0; i < itemList.size(); i++) {
			Item item = itemList.get(i);
			User user = this.userService.getUserById(item.getUser().getId());
			itemMap = new HashMap<String, String>();
			itemMap.put("id", item.getId());
			itemMap.put("code", item.getItemCode());
			itemMap.put("title", item.getName());
			itemMap.put("sku", StringUtils.isNotEmpty(item.getSpecification()) ? item.getSpecification() : "");
			itemMap.put("userId", item.getUser().getId());
			itemMap.put("weight", item.getWmsGrossWeight() != null ? String.valueOf(item.getWmsGrossWeight()) : "");
			itemMap.put("packageWeight", item.getGrossWeight() != null ? String.valueOf(item.getGrossWeight()) : "");
			itemMap.put("itemType", item.getItemType() == null ? "" : item.getItemType());
			itemMap.put("itemName", item.getShortName());
			itemMap.put("userName", user.getShortName());
			logger.error(itemMap);
			jedis.set((memcached_item + item.getId()).getBytes(), ObjectTranscoder.serialize(itemMap));
			index++;
		}
		logger.error("商家与商品信息初始化完毕！" + index);
	}

	/**
	 * 订单重新写入缓存(所有的商家订单)
	 * 
	 * @param orderIds
	 */
	@RequestMapping(value = "orderInfo")
	@ResponseBody
	public Map<String, Object> submitsNew(HttpServletRequest request) {

		String userId = request.getParameter("userId");

		Map<String, Object> retMap = new HashMap<String, Object>();

		logger.error("订单重新加载缓存开始！");

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			// params.put("startDate", sdf.format(this.getYesteday(new
			// Date())));
			// params.put("endDate", sdf.format(new Date()));
			params.put("startDate", "2018-01-30 00:00:00");
			params.put("endDate", "2018-01-31 00:30:00");
			params.put("status", "WMS_FINASH");
			params.put("userId", userId);
			List<ShipOrder> orders = this.shipOrderService.findShipOrderByList(params);

			for (ShipOrder order : orders) {

				params.clear();
				params.put("orderId", order.getId());
				List<TmsOrder> tmsOrders = this.tmsOrderService.getTmsOrderByList(params);

				logger.error(order.getId() + "开始操作！");

				for (int i = 0; i < tmsOrders.size(); i++) {

					TmsOrder tmsOrder = tmsOrders.get(i);
					Map<String, String> memcachedMap = new HashMap<String, String>();
					int zcIndex = 0, zpIndex = 0;

					params.clear();
					params.put("tmsOrderId", tmsOrder.getId());
					List<TmsOrderItem> tOrderItems = this.tmsOrderService.getTmsOrderItemByList(params);

					for (int j = 0; j < tOrderItems.size(); j++) {
						TmsOrderItem detail = tOrderItems.get(j);
						Item item = this.itemService.getItem(detail.getItem().getId());
						memcachedMap.put(item.getBarCode(),
								item.getId() + "||" + detail.getItemQuantity() + "||" + item.getItemType());
						if (item != null && item.getItemType() != null && item.getItemType().equals("ZC")) {
							zcIndex += detail.getItemQuantity();
						}
						if (item != null && item.getItemType() != null && item.getItemType().equals("ZP")) {
							zpIndex += detail.getItemQuantity();
						}

					}

					// 套餐订单和非套餐订单区分处理
					if (StringUtils.isNotEmpty(order.getItemGroupStatus())
							&& order.getItemGroupStatus().equals(ShipOrder.item_Group_Status)) {
						logger.error(order.getId() + "开始操作！");
						memcachedMap = this.buildMemcachedMapByShipOrderGroup(memcachedMap, order);
					}

					ReceiverInfo receiverInfo = this.receiverInfoService
							.getReceiverInfoById(order.getReceiverInfo().getId());
					logger.error("导入订单缓存操作:" + tmsOrder.getOrderCode() + "||" + memcachedMap);
					memcachedMap.put("orderInfo",
							tmsOrder.getId() + "||" + order.getTotalWeight() + "||" + order.getUser().getId());
					memcachedMap.put("zcIndex", String.valueOf(zcIndex));
					memcachedMap.put("zpIndex", String.valueOf(zpIndex));
					memcachedMap.put("orderDate", sdf.format(order.getLastUpdateTime()));
					memcachedMap.put("state", receiverInfo.getReceiverProvince());
					memcachedMap.put("totalWeight", String.valueOf(order.getTotalWeight()));
					memcachedMap.put("user", String.valueOf(order.getUser().getId()));

					String key = tmsOrder.getOrderCode();
					this.redisProxy.set(key.getBytes(), ObjectTranscoder.serialize(memcachedMap));
					logger.error(order.getId() + "操作结束！");

				}

			}
			retMap.put("msg", "缓存加载成功！");
		} catch (Exception e) {
			retMap.put("msg", "缓存加载失败！" + e.getMessage());
		}
		return retMap;
	}

	@RequestMapping(value = "initShipOrderReturn")
	public Map<String, Object> initReturnInfo() {
		int index = 0;
		logger.error("退货信息开始初始化");
		// 1.取退货信息
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("express", "xx");
		List<ShipOrderStop> returnList = this.stopService.getShipOrderStopByList(params);
		for (int i = 0; i < returnList.size(); i++) {
			ShipOrderStop orderReturn = returnList.get(i);
			if (StringUtils.isNotEmpty(orderReturn.getExpressOrderno())) {
				index++;
				logger.error(orderReturn.getExpressOrderno());
				this.redisProxy.set(RdsConstants.memcached_return + orderReturn.getExpressOrderno(),
						orderReturn.getExpressOrderno());
			}
		}
		logger.error("退货信息处理完毕!" + index);
		return null;

	}

	/**
	 * 订单重新写入缓存(包含套餐的订单)
	 * 
	 * @param orderIds
	 */
	@RequestMapping(value = "groupOrderInfo")
	@ResponseBody
	public Map<String, Object> submitsGroupOrder(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		Map<String, Object> retMap = new HashMap<String, Object>();
		logger.error("订单重新加载缓存开始！");
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("startDate", sdf.format(this.getYesteday(new Date())));
			params.put("endDate", sdf.format(new Date()));
			params.put("status", "WMS_FINASH");
			params.put("userId", userId);
			List<ShipOrder> orders = this.shipOrderService.findShipOrderByList(params);
			for (ShipOrder order : orders) {
				if (StringUtils.isNotEmpty(order.getItemGroupStatus())
						&& order.getItemGroupStatus().equals(ShipOrder.item_Group_Status)) {
					Map<String, String> memcachedMap = new HashMap<String, String>();
					logger.error(order.getId() + "开始操作！");
					memcachedMap = this.buildMemcachedMapByShipOrderGroup(memcachedMap, order);
					memcachedMap.put("orderInfo",
							order.getId() + "||"
									+ String.valueOf(order.getTotalWeight() == null ? "0" : order.getTotalWeight())
									+ "||" + order.getUser().getId());
					memcachedMap.put("orderDate", sdf.format(new Date()));
					memcachedMap.put("state", "");
					memcachedMap.put("totalWeight", String.valueOf(order.getTotalWeight()));
					memcachedMap.put("user", String.valueOf(order.getUser().getId()));
					logger.error("菜鸟订单缓存数据:" + order.getTmsOrderCode() + "||" + memcachedMap);
					String key = order.getTmsOrderCode();
					this.redisProxy.set(key.getBytes(), ObjectTranscoder.serialize(memcachedMap));
					logger.error(order.getId() + "操作结束！");
				}

			}
			retMap.put("msg", "缓存加载成功！");
		} catch (Exception e) {
			retMap.put("msg", "缓存加载失败！" + e.getMessage());
		}
		return retMap;
	}

	/**
	 * 套餐的验货处理
	 * 
	 * @param memcached
	 * @param order
	 * @param tmsOrder
	 */
	public Map<String, String> buildMemcachedMapByShipOrderGroup(Map<String, String> memcachedMap, ShipOrder order) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", order.getId());
		List<ShipOrderGroup> groupList = this.shipOrderGroupService.getShipOrderGroupByList(params);
		for (int i = 0; groupList != null && i < groupList.size(); i++) {
			ShipOrderGroup group = groupList.get(i);
			ItemGroup itemGroup = this.itemGroupService.getItemGroupById(group.getItemGroup().getId());
			memcachedMap.put(itemGroup.getBarCode(),
					itemGroup.getId() + "||" + group.getQuantity() + "||" + ShipOrder.item_Group_Status);
		}
		return memcachedMap;
	}

	/**
	 * 获取前天时间
	 * 
	 * @param date
	 * @return
	 */
	private Date getYesteday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date = calendar.getTime();
		return date;
	}

	public static void main(String[] args) {
	}

}
