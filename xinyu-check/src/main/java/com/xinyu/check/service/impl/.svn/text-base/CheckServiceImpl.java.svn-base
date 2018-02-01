package com.xinyu.check.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinyu.check.dao.CheckOutDao;
import com.xinyu.check.dao.ItemDao;
import com.xinyu.check.dao.ShipOrderDao;
import com.xinyu.check.model.CheckOut;
import com.xinyu.check.model.CheckOut.CheckOutStatus;
import com.xinyu.check.model.Item;
import com.xinyu.check.model.ShipOrder;
import com.xinyu.check.model.ShipOrderDetail;
import com.xinyu.check.model.ShipOrderReturn;
import com.xinyu.check.model.User;
import com.xinyu.check.service.CheckService;

@Component
public class CheckServiceImpl implements CheckService {

	public static final Logger logger = Logger.getLogger(CheckServiceImpl.class);

	@Autowired
	private ItemDao itemDao;


	@Autowired
	private ShipOrderDao shipOrderDao;

	@Autowired
	private CheckOutDao checkOutDao;

	// 1.根据条码取商品相应信息
	/**
	 * 
	 * @param barCode
	 * 
	 *            <pre>
	 *  code = 200 匹配成功
	 *  code = 404  无法匹配信息
	 *  code = 500  系统异常
	 *  msg  错误信息    
	 *  error 系统异常时， 异常错误
	 *            </pre>
	 * 
	 * @return
	 */
	public Map<String, Object> getItemInfoBybarCode(String barCode) {

		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		Item item = null;
		try {
			params.put("barCode", barCode);
			List<Item> itemList = this.itemDao.getItemByList(params);
			if (itemList != null && itemList.size() > 0) {
				retMap.put("item", itemList.get(0));
				retMap.put("code", "200");
			} else {
				retMap.put("code", "404");
			}
			return retMap;
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("msg", "获取商品信息失败:barCode=" + barCode + ",[" + e.toString() + "]");
			retMap.put("error", e);
			retMap.put("code", "500");
		}
		return retMap;
	}

	private Item getItemBybarCode(String barCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		Item item = null;
		List<Item> itemList = getItemListBybarCode(barCode);
		if (itemList != null && itemList.size() > 0) {
			retMap.put("item", itemList.get(0));
			retMap.put("code", "200");
		} else {
			retMap.put("code", "404");
		}
		return item;
	}
	
	
	private List<Item> getItemListBybarCode(String barCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		Item item = null;
		params.put("barCode", barCode);
		List<Item> itemList = this.itemDao.getItemByList(params);
		if (itemList != null && itemList.size() > 0) {
			retMap.put("item", itemList.get(0));
			retMap.put("code", "200");
		} else {
			retMap.put("code", "404");
		}
		return itemList;
	}

	// 1.根据条码取商品相应信息
	/**
	 * 
	 * @param barCode
	 * 
	 *            <pre>
	 *  code = 200 匹配成功
	 *  code = 404  无法匹配信息
	 *  code = 500  系统异常
	 *  msg  错误信息    
	 *  error 系统异常时， 异常错误
	 *            </pre>
	 * 
	 * @return
	 */
	public Map<String, Object> getItemInfoBybarCodes(String barCode) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("barCode", barCode);
		List<Item> list = null;
		try {
			list = itemDao.getItemByList(params);
			if (list != null) {
				retMap.put("items", list);
				retMap.put("code", "200");
			} else {
				retMap.put("code", "404");
			}
			return retMap;
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("msg", "获取商品信息失败:barCode=" + barCode + ",[" + e.toString() + "]");
			retMap.put("error", e);
			retMap.put("code", "500");
		}
		return retMap;
	}

	private Map<String, Object> noCheckTrade(String orderCode, String barCode, String stock, String cp,
			String personId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Item item = null;
		try {
			item = this.getItemBybarCode(barCode);
			CheckOut checkOut = new CheckOut();
			checkOut.setOrderCode(orderCode); // 单号号 条码
			checkOut.setBarCode(barCode); // 商品条码信息
			checkOut.setCpCompany(cp);// 快递公司
			checkOut.setCentroId(Long.valueOf(stock));// 仓库ID
			checkOut.setPersonId(Long.valueOf(personId)); // 操作人员
			checkOut.setCreateDate(new Date());
			if (item != null) {
				checkOut.setItemId(item.getId()); // 商品ID
				checkOut.setItemName(item.getTitle());
				checkOut.setUserId(item.getUserid());
				if (item.getPackageWeight() > 0) { // 有包裹重量 取包裹重量 。 没有时， 直接取商品重量
					checkOut.setWeight(item.getPackageWeight());
				} else {
					checkOut.setWeight(item.getWeight());
				}
				// 严重成功扫描出库 、、 扣库存
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("itemId", item.getId());
				params.put("orderNo", orderCode);
//				Long existOrderNo = qmInventoryService.existOrderNo(params);
				Long existOrderNo=0l;
				if (existOrderNo == 0) {
					retMap.put("item", item);
					retMap.put("code", "200");
					checkOut.setMsg("商品验证成功");
					checkOut.setStatus(CheckOutStatus.SUCCESS_GOODS);
					checkOut.setNum(1L);
//					qmInventoryService.addInventory(Long.valueOf(stock), item.getUserid(), item.getId(), -1L,
//							Accounts.CODE_SALEABLE, "扫描出库(不检查订单)", orderCode);
					// if(order.getSource().equals(Trade.SourceFlag.SourceFlag_QM)){
					// this.inventoryService.updateUserNum(Long.valueOf(stock),
					// item.getId(), Accounts.CODE_SALEABLE, -detail.getNum() );
					// }
				} else {
					retMap.put("item", item);
					retMap.put("code", "406");
					retMap.put("msg", "订单重复扫描!");
					checkOut.setMsg("订单重复扫描");
					checkOut.setStatus(CheckOutStatus.FAIL_GOODS);
				}
				checkOutDao.insertCheckOut(checkOut);
			} else {
				String msgStr = "[" + barCode + "]此条码对应的商品不存在！";
				retMap.put("code", "404");
				retMap.put("msg", msgStr);
				checkOut.setMsg(msgStr);
				checkOut.setStatus(CheckOutStatus.FAIL_GOODS);
				checkOutDao.insertCheckOut(checkOut);
			}
			return retMap;
		} catch (Exception e) {
			retMap.put("msg", "商品接口调用失败[barCode=" + barCode + ",{" + e.toString() + "}]");
			retMap.put("error", e);
			retMap.put("code", "500");
		}
		return retMap;

	}

	/**
	 * 远程订单 检验 后台
	 * 
	 * @param orderCode
	 * @param barCode
	 * @param stock
	 * @param cp
	 * @param userId
	 * @param sysItemList
	 * @return
	 * 
	 * 		synchronized
	 */
	public Map<String, Object> checkTrade(String orderCode, String barCode, String stock, String cp, String userId,
			String personId, List<String> sysItemList, String sys) {
		Date Date = new Date();
		// 检测订单是否 是 一退货订单
		logger.error(sys + "验货入口:" + orderCode + "|" + barCode + "|");
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		List<ShipOrder> shipOrderList= this.shipOrderDao.getShipOrderByList(params);
		ShipOrder shipOrder=null;
		if (shipOrderList != null && shipOrderList.size()>0) {
			shipOrder=shipOrderList.get(0);
			params.put("orderId", shipOrder.getId());
		} else {
			params.put("expressOrderno", orderCode);
		}
		List<ShipOrderReturn> returns = this.shipOrderDao.getShpOrderReturnByList(params);
		if (returns != null && returns.size() > 0) {
			CheckOut checkOut = new CheckOut();
			checkOut.setOrderCode(orderCode); // 单号号 条码
			checkOut.setBarCode(barCode); // 商品条码信息
			checkOut.setCpCompany(cp);// 快递公司
			checkOut.setCreateDate(new Date());
			checkOut.setCentroId(Long.valueOf(stock));
			checkOut.setWeight(0D);
			String msgStr = "此订单号已退货！【" + orderCode + "】";
			checkOut.setMsg(sys + msgStr);
			checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
			checkOutDao.insertCheckOut(checkOut);
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("msg", msgStr);
			retMap.put("code", "406");
			return retMap;
		}
		/**
		 * 根据运单号重新取USERID
		 */

		if (shipOrder != null) {
			userId = "" + shipOrder.getCreateUser().getId();
		}
		if (shipOrder == null) {
			shipOrder = new ShipOrder();
			shipOrder.setId(0L);
			shipOrder.setExpressCode(orderCode);
		}
		/**
		 * 关闭非验货判断，所有的都走验货
		 */
		return isCheckTrade(shipOrder, barCode, stock, cp, personId, sys);
		/*
		  if ("0".equals(userId)) { // 是否 开启订单检测商家范围
			return isCheckTrade(shipOrder, barCode, stock, cp, personId, sys);
		}
		if (sysItemList.contains(userId)) {
			// logger.info("checkTrade:{userId："+userId+";orderCode:"+orderCode+";barCode:"+barCode+"}");
			return isCheckTrade(shipOrder, barCode, stock, cp, personId, sys);
		}
		return noCheckTrade(orderCode, barCode, stock, cp, personId);
		*/
	}

	// 2.校验接口，根据传回的两个条码一个商品条码一个快递单条码。
	// 主要是根据快递单的条码找到相应的订单，然后再找到当前订单所属的商品的条码。
	// 再对比传过来的商品条码是否一致，如果一致则返回成功，否则返回失败。
	// 成功或失败都要求写入流水。
	/**
	 * 
	 * @param orderCode
	 *            订单条码
	 * @param barCode
	 *            商品条码信息
	 * 
	 *            <pre>
	 *  code = 200 匹配成功
	 *  code = 404  无法匹配信息
	 *  code = 500  系统异常
	 *  code = 405 商品和订单不匹配
	 *  msg  错误信息    
	 *  error 系统异常时， 异常错误 
	 *  orderId  如订单号匹配成功， 商品无法匹配时， 订单ID 返回信息
	 *            </pre>
	 * 
	 * @return
	 */
	private Map<String, Object> isCheckTrade(ShipOrder order, String barCode, String stock, String cp, String personId,
			String sys) {
		logger.error("isCheckTrade：" + order + "|" + barCode);
		Map<String, Object> retMap = new HashMap<String, Object>();
		CheckOut checkOut = null;
		String msgStr = "";
		Date date = new Date();
		checkOut = new CheckOut();
		if (order == null || order.getId() == 0) { // 没有批到到单据信息
			msgStr = "单号对应的订单不存在！【" + barCode + "】";
			checkOut.setMsg(sys + msgStr);
			checkOut.setBarCode(barCode);
			checkOut.setPersonId(Long.valueOf(personId));
			checkOut.setCreateDate(new Date());
			checkOut.setOrderCode(order != null ? order.getExpressOrderno() : "");
			checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
			checkOutDao.insertCheckOut(checkOut);
			retMap.put("msg", msgStr);
			retMap.put("code", "404");
			return retMap;
		}
		try { // 订单条码 检测

			checkOut.setOrderCode(order.getExpressOrderno()); // 单号号 条码
			checkOut.setBarCode(barCode); // 商品条码信息
			checkOut.setCpCompany(cp);// 快递公司
			checkOut.setCreateDate(new Date());
			checkOut.setCentroId(Long.valueOf(stock));
			checkOut.setPersonId(Long.valueOf(personId));

			try { // 商品明细 商品条码检测
				Long orderId = order.getId();
				Map<String,Object> params=new HashMap<String, Object>();
				params.put("orderId", orderId);
				Date d=new Date();
				List<ShipOrderDetail> details = shipOrderDao.getShipOrderDetailByOrderId(params);
			//	logger.error("查询订单明细所花费时间 :"+(new Date().getTime()-d.getTime()));
				User createUser = order.getCreateUser();
				checkOut.setUserId(createUser.getId());
				checkOut.setOrderId(orderId);
				String state = order.getReceiverState() + order.getReceiverCity();
				checkOut.setState(state);// 目的地省份
				Date tempDate=new Date();
				String itemBarCode="";
				for (ShipOrderDetail detail : details) {
					Item item = itemDao.getItem(String.valueOf(detail.getItem().getId()));
					itemBarCode=item.getBarCode();
					checkOut.setBarCode(barCode);
					if (item.getBarCode() != null && barCode.trim().equals(item.getBarCode().trim())) {
						// 匹配商品信息正确
						if (item != null) {
							if (item.getPackageWeight() > 0) { // 有包裹重量 取包裹重量 。
																// 没有时， 直接取商品重量
								checkOut.setWeight(item.getPackageWeight() * detail.getNum());
							} else {
								checkOut.setWeight(item.getWeight() * detail.getNum());
							}
							checkOut.setItemId(item.getId());
							checkOut.setItemName(item.getTitle());
						}
						// 严重成功扫描出库 、、 扣库存
						int existOrderNo = getCheckOutByCode(order.getExpressOrderno());
						if (existOrderNo == 0) {
							retMap.put("code", "200");
							retMap.put("msg", "订单校验成功!");
							checkOut.setMsg(sys + "订单校验成功!");
							checkOut.setStatus(CheckOut.CheckOutStatus.SUCCESS_TRADE);
							checkOut.setNum(detail.getNum());
						} else {
							retMap.put("code", "405");
							retMap.put("msg", "订单重复扫描");
							checkOut.setMsg(sys + "订单重复扫描!");
							checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
							logger.error("系统重复扫描:" + orderId + "|" + order.getExpressOrderno() + "|" + barCode);
						}
						logger.error("判断商品是否正确所花时间:"+(new Date().getTime()-tempDate.getTime()));
						this.saveCheckOut(checkOut);
						return retMap;
					}
				}
//				logger.error("判断商品是否正确所花时间:"+(new Date().getTime()-tempDate.getTime()));
				msgStr = "失败:[此商品与订单商品不匹配]";
				logger.error("["+barCode+"]["+itemBarCode+"]验货失败条码信息!");
				retMap.put("orderId", orderId);
				retMap.put("msg", msgStr);
				retMap.put("code", "500");
				checkOut.setMsg(sys + msgStr);
				checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
				checkOutDao.insertCheckOut(checkOut);
				return retMap;
			} catch (Exception e) { // 获取商品信息出错
				logger.error("验货异常信息:" + e.getMessage());
				e.printStackTrace();
				msgStr = "失败[商品信息获取失败!" + e.toString() + "]";
				retMap.put("msg", msgStr);
				retMap.put("error", e);
				retMap.put("code", "500");
				checkOut.setMsg(sys + msgStr);
				checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
				checkOutDao.insertCheckOut(checkOut);
			}
		} catch (Exception e) { // 获取订单信息出错
			logger.error("验货取订单异常信息:" + e.getMessage());
			e.printStackTrace();
			msgStr = "失败:[订单条码 " + barCode + "！" + e.toString() + "]";
			retMap.put("msg", msgStr);
			retMap.put("error", e);
			retMap.put("code", "500");
			checkOut.setMsg(sys + msgStr);
			checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
			checkOutDao.insertCheckOut(checkOut);
		}
		return retMap;
	}
	/**
	 * 新增checkout
	 * 
	 * @param checkOut
	 */
	public void saveCheckOut(CheckOut checkOut) {
		Date date=new Date();
		checkOutDao.insertCheckOut(checkOut);
		logger.error("saveCheckOut所花时间:"+(new Date().getTime()-date.getTime()));
	}

	

	/**
	 * 判断当前快递是否有出库过
	 * 
	 * @param barCode
	 * @param orderCode
	 * @return
	 */
	private int getCheckOutByCode(String orderCode) {
		Date date=new Date();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		int result = this.checkOutDao.isExistsOrderCode(params);
		logger.error("判断是否重复出库所花费时间:getCheckOutByCode["+(new Date().getTime()-date.getTime())+"]");
		return result;
	}
	
	
	public Long getCountByDate(Map<String,Object> params){
		return shipOrderDao.getCountByDate(params);
	}
	
	public Long getSuccessCountByDate(Map<String,Object> params){
		return shipOrderDao.getSuccessCountByDate(params);
	}
	
	public Long getCheckSuccessCountByDate(Map<String,Object> params){
		return shipOrderDao.getCheckSuccessCountByDate(params);
	}
	
	public Long getCheckFailCountByDate(Map<String,Object> params){
		return shipOrderDao.getCheckFailCountByDate(params);
	}

	@Override
	public Map<String, Object> checkTradenew(String orderCode, String barCode, String stock, String cp, String userId,
			String personId, List<String> sysItemList, String sys) {
		/**
		 * 验货
		 * 1.判断是否成功 
		 * 2.判断是否退货与是否重复验货
		 */
		Map<String,Object> params=new HashMap<String, Object>();
		
		params.put("orderCode", orderCode);
		params.put("barCode", barCode);
		
		
		
		String msg="";
		String code="";
		Date date=new Date();
		List<ShipOrder> shipOrderList= this.shipOrderDao.getShipOrderByList(params);
		logger.error("查询列表时间:"+(new Date().getTime()-date.getTime()));
		ShipOrder order=null;
		if(shipOrderList==null || shipOrderList.size()==0){
			msg="单号对应的订单不存在！【" + orderCode + "】";
			code="404";
		}else {
			order=shipOrderList.get(0);
			date=new Date();
			Long index=this.shipOrderDao.getTradeCheck(params);
			logger.error("判断是否准确时间:"+(new Date().getTime()-date.getTime()));
			if(index==null || index.intValue()==0){
				msg="验货失败！商品与订单信息不匹配";
				code="500";
			}else{
				date=new Date();
				Map<String,Object> result=this.shipOrderDao.getTradeCheckAndReturn(params);
				logger.error("判断是否重复时间:"+(new Date().getTime()-date.getTime()));
				Long r=(Long) result.get("r");
				Long o=(Long) result.get("o");
				if(r.intValue()>0){
					code="406";
					msg="此订单已退货!";
				}
				if(o.intValue()>0){
					code="405";
					msg="重复扫描!";
				}
				if(o==0 && r==0){
					code="200";
					msg="订单校验成功!";
				}
			}
		}	
		
		CheckOut checkOut=new CheckOut();
		checkOut.setBarCode(barCode);
		checkOut.setCreateDate(new Date());
		checkOut.setOrderCode(orderCode);
		checkOut.setCentroId(Long.valueOf(stock));
		checkOut.setWeight(order!=null?order.getTotalWeight():0D);
		checkOut.setPersonId(Long.valueOf(personId));
		checkOut.setUserId(order!=null ? order.getCreateUser().getId():0l);
		if(code.equals("200")){
			checkOut.setStatus(CheckOutStatus.SUCCESS_TRADE);
		}else{
			checkOut.setStatus(CheckOutStatus.FAIL_TRADE);
		}
		checkOut.setMsg(msg);
		this.saveCheckOut(checkOut);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		List<Item> itemList=this.getItemListBybarCode(barCode);
		if(itemList!=null && itemList.size()>0){
			Item item=itemList.get(0);
			resultMap.put("itemName", item.getTitle()+"|"+item.getCode());
		}
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		resultMap.put("barCode", barCode);
		resultMap.put("orderCode", orderCode);
		
		return resultMap;
	}
	
	
}
