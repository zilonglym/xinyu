package com.xinyu.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyu.common.BaseController;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.RdsConstants;
import com.xinyu.model.base.User;
import com.xinyu.model.qm.StoreConstants;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderStop;
import com.xinyu.model.trade.WmsConsignOrderItem;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.trade.ReceiverInfoService;
import com.xinyu.service.trade.ShipOrderService;
import com.xinyu.service.trade.ShipOrderStopService;
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
	@RequestMapping(value="refreshOrder")
	@ResponseBody
	public Map<String,Object> initShipOrderInfo(){
		String orderExpressNo=getString("expressNo");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		Map<String,Object> params=new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String startDate = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, -3);
		String endDate = sdf.format(cal.getTime());
		params.put("tmsOrderCode", orderExpressNo);
		List<ShipOrder> orderList=this.shipOrderService.findShipOrderByList(params);
		ShipOrder shipOrder=orderList==null || orderList.size()==0?null:orderList.get(0);
		/**
		 * modify 2017-11-7
		 */
		ShipOrder order = (shipOrder==null?null:shipOrder.getOrder());
		
		if (order!=null && StringUtils.isNotEmpty(order.getTmsOrderCode())) {
			params.clear();
			params.put("orderId", order.getId());
			List<WmsConsignOrderItem> itemList=this.orderItemService.getWmsConsignOrderItemByList(params);
			Map<String, Object> memcachedMap = new HashMap<String, Object>();
			int zcIndex=0,zpIndex=0;
			ReceiverInfo receiverInfo=this.receiverInfoService.getReceiverInfoById(order.getReceiverInfo().getId());
			for (int j = 0; itemList!=null && j<itemList.size(); j++) {
				WmsConsignOrderItem detail = itemList.get(j);
				Item item = this.itemService.getItem(detail.getItem().getId());
				if(item!=null && item.getItemType()!=null && item.getItemType().equals("ZC")){
					zcIndex+=detail.getItemQuantity();
				}
				if(item!=null && item.getItemType()!=null && item.getItemType().equals("ZP")){
					zpIndex+=detail.getItemQuantity();
				}
				memcachedMap.put(item.getBarCode(), item.getId()+"||"+detail.getItemQuantity()+"||"+item.getItemType());
			}
			memcachedMap.put("zcIndex", zcIndex);
			memcachedMap.put("zpIndex", zpIndex);
			memcachedMap.put("orderInfo", order.getId()+"||"+order.getTotalWeight()+"||"+order.getUser().getId());
			memcachedMap.put("orderDate", sdf.format(order.getCreateTime()));
			memcachedMap.put("state", receiverInfo.getReceiverProvince()!=null?receiverInfo.getReceiverProvince():"");
			memcachedMap.put("totalWeight", String.valueOf(order.getTotalWeight()));
			memcachedMap.put("user", String.valueOf(order.getUser().getId()));
			memcachedMap.put("itemInfo", order.getItems());
			this.redisProxy.set(order.getTmsOrderCode().getBytes(), ObjectTranscoder.serialize(memcachedMap));
			logger.error("订单刷新补录:"+order.getTmsOrderCode()+"||"+memcachedMap);
			resultMap.putAll(memcachedMap);
		}
		return resultMap;
	}
	
	

	@RequestMapping(value = "itemInfo")
	public void initItemAndUserInfo() {
		Jedis jedis=RedisUtil.getJedis();
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
			userMap.put("tbUserId",String.valueOf(user.getTbUserId()));
//			jedis.hmset(memcached_user + user.getId(), userMap);
			this.redisProxy.set((memcached_user+user.getId()).getBytes(), ObjectTranscoder.serialize(userMap));
			index++;
		}
		logger.error("初始化商品信息:" + index);
		List<Item> itemList = this.itemService.getItemByList(null);
		for (int i = 0; i < itemList.size(); i++) {
			Item item = itemList.get(i);
			User user=this.userService.getUserById(item.getUser().getId());
			itemMap = new HashMap<String, String>();
			itemMap.put("id", item.getId());
			itemMap.put("code", item.getItemCode());
			itemMap.put("title", item.getName());
			itemMap.put("sku", StringUtils.isNotEmpty(item.getSpecification())?item.getSpecification():"");
			itemMap.put("userId", item.getUser().getId());
			itemMap.put("weight", item.getWmsGrossWeight()!=null?String.valueOf(item.getWmsGrossWeight()):"");
			itemMap.put("packageWeight", item.getGrossWeight()!=null?String.valueOf(item.getGrossWeight()):"");
			itemMap.put("itemType", item.getItemType()==null?"":item.getItemType());
			itemMap.put("itemName", item.getShortName());
			itemMap.put("userName", user.getShortName());
			logger.error(itemMap);
			jedis.set((memcached_item + item.getId()).getBytes(), ObjectTranscoder.serialize(itemMap));
			index++;
		}
		logger.error("商家与商品信息初始化完毕！" + index);
	}
	
	
	
	@RequestMapping(value = "initShipOrderReturn")
	public Map<String,Object> initReturnInfo(){
		int index = 0;
		logger.error("退货信息开始初始化");
		// 1.取退货信息
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("express", "xx");
		List<ShipOrderStop> returnList=this.stopService.getShipOrderStopByList(params);
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
	public static void main(String[] args) {
	}

}
