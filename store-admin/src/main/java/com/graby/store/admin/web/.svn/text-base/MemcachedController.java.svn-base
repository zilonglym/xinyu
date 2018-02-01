package com.graby.store.admin.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.admin.task.ObjectTranscoder;
import com.graby.store.admin.util.IRedisProxy;
import com.graby.store.base.StoreConstants;
import com.graby.store.entity.CheckOut;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.ShipOrderReturn;
import com.graby.store.entity.User;
import com.graby.store.remote.CheckRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.ShipOrderRemote;
import com.graby.store.remote.ShipOrderReturnRemote;
import com.graby.store.remote.UserRemote;

/**
 * 缓存控制类
 * 
 * @author yangmin 2017年8月17日
 *
 */
//@Lazy(false)
@Controller
@RequestMapping(value = "memcached")
public class MemcachedController extends BaseController {

	public static final Logger logger = Logger.getLogger(MemcachedController.class);
	@Autowired
	private ShipOrderReturnRemote orderReturnRemote;
	@Autowired
	private ShipOrderRemote shipOrderRemote;
	@Autowired
	private CheckRemote checkRemote;
	@Autowired
	private UserRemote userRemote;
	@Autowired
	private ItemRemote itemRemote;
	@Autowired
	private IRedisProxy redisProxy;

	public final static String memcached_checkOut = "CheckOut";

	public final static String memcached_success_CheckOut = "success";

	public final static String memcached_item = "Item";
	public final static String memcached_user = "User";

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping(value="redis")
	public void testRedis(){
		Set<String> keys=this.redisProxy.keys("20170918*");
		System.err.println(keys.size());
	}

	/**
	 * 初始化缓存信息
	 */
	@RequestMapping(value = "init")
	public Map<String,Object> initMemcached() {
		logger.error("缓存初始化开始！");
		// 初始化缓存
		// MemcachedManager.
		int index = 0;
		// 1.取退货信息
		Map<String, Object> params = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 7);// 让日期加1
		params.put("beigainTime", sdf.format(calendar.getTime()));
		params.put("lastTime", sdf.format(new Date()));
		List<ShipOrderReturn> returnList = this.orderReturnRemote.findShipOrderByexpress(params);
		for (int i = 0; i < returnList.size(); i++) {
			ShipOrderReturn orderReturn = returnList.get(i);
			if (StringUtils.isNotEmpty(orderReturn.getExpressOrderno())) {
				index++;
				this.redisProxy.set(StoreConstants.memcached_return + orderReturn.getExpressOrderno(),
						orderReturn.getExpressOrderno());
			}
		}
		logger.error("退货信息处理完毕!" + index);
		// 2.取未出库的订单信息
	
		// 3.取最近两天的验货信息,只取成功的，为的是重复扫描的判断
		params.clear();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String startDate = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, -3);
		String endDate = sdf.format(cal.getTime());
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("status", "SUCCESS");
		List<CheckOut> checkList = this.checkRemote.findCheckOut(params);
		for (int i = 0; i < checkList.size(); i++) {
			CheckOut out = checkList.get(i);
			Map<String, Object> checkOut = new HashMap<String, Object>();
			checkOut.put(memcached_success_CheckOut + out.getOrderCode(), out.getOrderCode());
			this.redisProxy.set((memcached_checkOut + out.getOrderCode()).getBytes(), ObjectTranscoder.serialize(checkOut));
			index++;
		}
		// 4.初始化所有的商品信息
		// 5.初始化所有的商家信息
		this.initItemAndUserInfo();
		logger.error("验货信息处理完毕，添加记录条数:" + index);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("ret", 1);
		return resultMap;
	}

	@RequestMapping(value="initShipOrder")
	public String initShipOrder(){
		int index=0;
		logger.error("订单信息初始化!");
		Map<String, Object> params = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String startDate = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, -3);
		String endDate = sdf.format(cal.getTime());
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		List<ShipOrder> orderList = shipOrderRemote.selectShipOrderNotEXISTS(params);
		for (int i = 0; i < orderList.size(); i++) {
			ShipOrder order = orderList.get(i);
			if (StringUtils.isNotEmpty(order.getExpressOrderno())) {
				ShipOrder tempOrder = this.shipOrderRemote.getShipOrder(order.getId());
				Map<String, Object> memcachedMap = new HashMap<String, Object>();
				for (int j = 0; j < tempOrder.getDetails().size(); j++) {
					ShipOrderDetail detail = tempOrder.getDetails().get(j);
					Item item = detail.getItem();
					memcachedMap.put(item.getBarCode(), detail.getNum());
				}
				index++;
				this.redisProxy.set(order.getExpressOrderno().getBytes(), ObjectTranscoder.serialize(memcachedMap));
			}
		}

		logger.error("订单信息处理完毕！" + index);
		return null;
	}
	
	@RequestMapping(value="toRefreshOrder")
	public String toRefershOrder(){
		
		return "";
	}
	/**
	 * 刷新订单信息
	 */
	@RequestMapping(value="refreshOrder")
	@ResponseBody
	public Map<String,Object> initShipOrderInfo(){
		String orderExpressNo=getString("expressNo");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		ShipOrder order=this.shipOrderRemote.findShipOrderByExpressOrderno(orderExpressNo);
		if (order!=null && StringUtils.isNotEmpty(order.getExpressOrderno())) {
			ShipOrder tempOrder = this.shipOrderRemote.getShipOrder(order.getId());
			Map<String, Object> memcachedMap = new HashMap<String, Object>();
			int zcIndex=0,zpIndex=0;
			for (int j = 0; j < tempOrder.getDetails().size(); j++) {
				ShipOrderDetail detail = tempOrder.getDetails().get(j);
				Item item = detail.getItem();
				if(item!=null && item.getItemType()!=null && item.getItemType().equals("ZC")){
					zcIndex+=detail.getNum();
				}
				if(item!=null && item.getItemType()!=null && item.getItemType().equals("ZP")){
					zpIndex+=detail.getNum();
				}
				memcachedMap.put(item.getBarCode(), item.getId()+"||"+detail.getNum()+"||"+item.getItemType());
			}
			memcachedMap.put("zcIndex", zcIndex);
			memcachedMap.put("zpIndex", zpIndex);
			memcachedMap.put("orderInfo", order.getId()+"||"+order.getTotalWeight()+"||"+order.getCreateUser().getId());
			memcachedMap.put("orderDate", sdf.format(order.getLastUpdateDate()));
			memcachedMap.put("state", order.getReceiverState());
			memcachedMap.put("totalWeight", String.valueOf(order.getTotalWeight()));
			memcachedMap.put("user", String.valueOf(order.getCreateUser().getId()));
			this.redisProxy.set(order.getExpressOrderno().getBytes(), ObjectTranscoder.serialize(memcachedMap));
			logger.error("订单刷新补录:"+order.getExpressOrderno()+"||"+memcachedMap);
			resultMap.put("ret", "1");
			resultMap.put("info", memcachedMap);
		}
		return resultMap;
	}
	
	@RequestMapping(value = "itemInfo")
	public void initItemAndUserInfo() {
		logger.error("初始化商品与商家信息");
		int index = 0;
		Map<String, Object> userMap = null;
		Map<String, Object> itemMap = null;
		List<User> userList = this.userRemote.findUsers();
		for (int i = 0; userList != null && i < userList.size(); i++) {
			User user = userList.get(i);
			userMap = new HashMap<String, Object>();
			userMap.put("id", user.getId());
			userMap.put("shopName", user.getShopName());
			userMap.put("code", user.getCode());
			userMap.put("tbUserId", user.getTbUserId());
			userMap.put("shortName", user.getShopName());
			this.redisProxy.set((memcached_user+user.getId()).getBytes(), ObjectTranscoder.serialize(userMap));
			index++;
		}
		logger.error("初始化商品信息:" + index);
		List<Item> itemList = this.itemRemote.getItemListByParams(null);
		for (int i = 0; i < itemList.size(); i++) {
			Item item = itemList.get(i);
			itemMap = new HashMap<String, Object>();
			itemMap.put("id", item.getId());
			itemMap.put("code", item.getCode());
			itemMap.put("title", item.getTitle());
			itemMap.put("sku", item.getSku());
			itemMap.put("userId", item.getUserid());
			itemMap.put("weight", item.getWeight());
			itemMap.put("packageWeight", item.getPackageWeight());
			itemMap.put("itemType", item.getItemType());
			itemMap.put("itemName", item.getShortName());
			itemMap.put("itemCode", item.getCode());
			itemMap.put("shortName", item.getShortName());
			logger.error(itemMap);
			if(item==null || item.getUserid()==0){
				continue;
			}
			User user=this.userRemote.getUser(item.getUserid());
			itemMap.put("userName", user.getShopName());
			this.redisProxy.set((memcached_item + item.getId()).getBytes(), ObjectTranscoder.serialize(itemMap));
			index++;
		}
		logger.error("商家与商品信息初始化完毕！" + index);
	}
	
	
	
	@RequestMapping(value = "itemInfoByCode")
	public void initItemInfo() {
		logger.error("初始化商品与商家信息");
		int index = 0;
		Map<String, Object> itemMap = null;
		String barCode=getString("barCode");
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("barCode", barCode);
//		params.put("code", "8600");
//		params.put("userId", "18");
//		List<Item> itemList = this.itemRemote.getItemListByParams(params);
		List<Item> itemList=new ArrayList<Item>();
		itemList.add(this.itemRemote.getItem(8501l));
		for (int i = 0; i < itemList.size(); i++) {
			Item item = itemList.get(i);
			itemMap = new HashMap<String, Object>();
			itemMap.put("id", item.getId());
			itemMap.put("code", item.getCode());
			itemMap.put("title", item.getTitle());
			itemMap.put("sku", item.getSku());
			itemMap.put("userId", item.getUserid());
			itemMap.put("weight", item.getWeight());
			itemMap.put("packageWeight", item.getPackageWeight());
			itemMap.put("itemType", item.getItemType());
			User user=this.userRemote.getUser(item.getUserid());
			itemMap.put("userName", user.getShopName());
			itemMap.put("itemName", item.getTitle());
			itemMap.put("itemCode", item.getCode());
			itemMap.put("shortName", item.getShortName());
			logger.error(itemMap);
			this.redisProxy.set((memcached_item + item.getId()).getBytes(), ObjectTranscoder.serialize(itemMap));
			index++;
		}
		logger.error("商品信息初始化完毕！" + index);
	}
	
	
	
	@RequestMapping(value = "itemInfoByUser")
	public void initItemInfoByUser() {
		logger.error("初始化商品与商家信息");
		int index = 0;
		Map<String, Object> itemMap = null;
		String userId=getString("userId");
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		List<Item> itemList = this.itemRemote.getItemListByParams(params);
		for (int i = 0; i < itemList.size(); i++) {
			Item item = itemList.get(i);
			itemMap = new HashMap<String, Object>();
			itemMap.put("id", item.getId());
			itemMap.put("code", item.getCode());
			itemMap.put("title", item.getTitle());
			itemMap.put("sku", item.getSku());
			itemMap.put("userId", item.getUserid());
			itemMap.put("weight", item.getWeight());
			itemMap.put("packageWeight", item.getPackageWeight());
			itemMap.put("itemType", item.getItemType());
			logger.error(itemMap);
			this.redisProxy.set((memcached_item + item.getId()).getBytes(), ObjectTranscoder.serialize(itemMap));
			index++;
		}
		logger.error("商品信息初始化完毕！" + index);
	}
	public final static String memcached_fail_CheckOut = "fail";
	public final static String memcached_chongfu_CheckOut = "chongfu";
	public final static String memcached_return_CheckOut = "return";
	


}
