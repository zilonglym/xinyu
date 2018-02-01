package com.xinyu.service.system.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taobao.api.domain.Account;
import com.xinyu.dao.base.AccountDao;
import com.xinyu.dao.base.AccountRelationDao;
import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.trade.CheckOutDao;
import com.xinyu.dao.trade.ReceiverInfoDao;
import com.xinyu.dao.trade.ShipOrderDao;
import com.xinyu.dao.trade.ShipOrderStopDao;
import com.xinyu.dao.trade.WmsConsignOrderItemDao;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.system.AccountRelation;
import com.xinyu.model.system.enums.OrderStatusEnum;
import com.xinyu.model.trade.CheckOut;
import com.xinyu.model.trade.CheckOut.CheckOutStatus;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderStop;
import com.xinyu.model.trade.WmsConsignOrderItem;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.system.CheckService;

@Service("checkServiceImpl")
public class CheckServiceImpl extends BaseServiceImpl implements CheckService{

	public static final Logger logger = Logger.getLogger(CheckServiceImpl.class);
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private ShipOrderDao shipOrderDao;
	
	@Autowired
	private CheckOutDao checkOutDao;
	
	@Autowired
	private AccountRelationDao relationDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private WmsConsignOrderItemDao orderItemDao;
	
	@Autowired
	private ReceiverInfoDao receiverInfoDao;
	@Autowired
	private ShipOrderStopDao orderStopDao;
	
//	1.根据条码取商品相应信息
	/**
	 * 
	 * @param barCode
	 * <pre>
	 *  code = 200 匹配成功
	 *  code = 404  无法匹配信息
	 *  code = 500  系统异常
	 *  msg  错误信息    
	 *  error 系统异常时， 异常错误 
	 * </pre>
	 * @return
	 */
	@Override
	public Map<String, Object> getItemInfoBybarCode(String barCode) {
		
		Map<String,Object>  retMap  =  new HashMap<String,Object>();
		Item item  = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("barCode", barCode);
			item = this.itemDao.getItemByList(params).get(0);
			if (item != null) {
				retMap.put("item", item);
				retMap.put("code", "200");
			}else{
				retMap.put("code", "404");
			}
			return retMap ;
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("msg","获取商品信息失败:barCode=" +barCode+",["+e.toString()+"]"); 
			retMap.put("error", e);
			retMap.put("code", "500");
		}
		return  retMap;
	}

//	1.根据条码取商品相应信息
	/**
	 * 
	 * @param barCode
	 * <pre>
	 *  code = 200 匹配成功
	 *  code = 404  无法匹配信息
	 *  code = 500  系统异常
	 *  msg  错误信息    
	 *  error 系统异常时， 异常错误 
	 * </pre>
	 * @return
	 */
	@Override
	public Map<String, Object> getItemInfoBybarCodes(String barCode) {
		
		Map<String,Object>  retMap  =  new HashMap<String,Object>();
		List<Item> list  = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("barCode", barCode);
			list= this.itemDao.getItemByList(params);
			if (list != null) {
				retMap.put("items", list);
				retMap.put("code", "200");
			}else{
				retMap.put("code", "404");
			}
			return retMap ;
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("msg","获取商品信息失败:barCode=" +barCode+",["+e.toString()+"]"); 
			retMap.put("error", e);
			retMap.put("code", "500");
		}
		return  retMap;
	}
	
	/**
	 *  远程订单  检验 后台
	 * @param orderCode
	 * @param barCode
	 * @param stock
	 * @param cp
	 * @param userId
	 * @param sysItemList
	 * @return
	 * 
	 * 
	 */
	@Override
	@Transactional
	public synchronized Map<String, Object> checkTrade(String orderCode, String barCode, String stock, String cp, String userId,
			String personId, List<String> sysItemList, String sys) {
		Date Date=new Date();		
		//检测订单是否 是 一退货订单
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tmsOrderCode", orderCode);
		List<ShipOrder> orderList=this.shipOrderDao.getShipOrderByList(params);
		ShipOrder shipOrder=orderList!=null && orderList.size()>0?orderList.get(0):null;	
		logger.error("校验订单：单号："+orderCode+" 条码："+barCode+" 订单是否存在："+(shipOrder==null?"true":"false"));
		params.clear();
		if(shipOrder!=null){
			params.put("orderId", shipOrder.getId());
		}else{
			params.put("expressOrderno",orderCode);
		}
		List<ShipOrderStop> returns =this.orderStopDao.getShipOrderStopByList(params);
		if(returns!=null  &&  returns.size()>0) {
			logger.error("shipOrderReturn:{userId："+userId+";orderCode:"+orderCode+";barCode:"+barCode+"}");
			CheckOut checkOut  = new CheckOut();
			checkOut.generateId();
			checkOut.setOrderCode(orderCode);  //单号号 条码
			checkOut.setBarCode(barCode); //  商品条码信息
			checkOut.setCpCompany(cp);//快递公司
			checkOut.setCreateDate(new Date());
//			checkOut.setCentroId(Long.valueOf(stock));
			//checkOut.setWeight(0f);
			String	msgStr = "此订单号已退货！【" +orderCode+"】" ;
			checkOut.setMsg(sys+msgStr);
			checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
			checkOutDao.insertCheckOut(checkOut);
			Map<String,Object>  retMap  =  new HashMap<String,Object>();
			retMap.put("msg", msgStr); 
			retMap.put("code", "406");
			return retMap;
		}
		/**
		 * 根据运单号重新取USERID
		 */
		
		if(shipOrder!=null){
			userId=""+shipOrder.getUser().getId();
		}
		if(shipOrder==null){
			shipOrder=new ShipOrder();
			shipOrder.setId("0");
			shipOrder.setTmsOrderCode(orderCode);
		}
		if("0".equals(userId)){   //是否  开启订单检测商家范围
			return isCheckTrade(shipOrder, barCode,stock,cp,personId,sys);
		}
//			logger.info("checkTrade:{userId："+userId+";orderCode:"+orderCode+";barCode:"+barCode+"}");
			return isCheckTrade(shipOrder, barCode, stock, cp, personId,sys);
//		logger.info("checkItem:{userId："+userId+";orderCode:"+orderCode+";barCode:"+barCode+"}");
//		return noCheckTrade(orderCode, barCode,stock,cp,personId);
	}
	
//	2.校验接口，根据传回的两个条码一个商品条码一个快递单条码。
//	主要是根据快递单的条码找到相应的订单，然后再找到当前订单所属的商品的条码。
//	再对比传过来的商品条码是否一致，如果一致则返回成功，否则返回失败。
//	成功或失败都要求写入流水。
	/**
	 * 
	 * @param orderCode   订单条码
	 * @param barCode     商品条码信息
	 * <pre>
	 *  code = 200 匹配成功
	 *  code = 404  无法匹配信息
	 *  code = 500  系统异常
	 *  code = 405 商品和订单不匹配
	 *  msg  错误信息    
	 *  error 系统异常时， 异常错误 
	 *  orderId  如订单号匹配成功， 商品无法匹配时， 订单ID 返回信息
	 *</pre>
	 * @return
	 */
	private Map<String, Object> isCheckTrade(ShipOrder order, String barCode, String stock, String cp,
			String personId, String sys) {
		Map<String,Object>  retMap  =  new HashMap<String,Object>();
		CheckOut checkOut    = new CheckOut();
		checkOut.generateId();
		String msgStr = "";
		Date date=new Date();
		if(StringUtils.isEmpty(personId) || personId.equals("0")){
			//admin的帐号ID
			personId="9d3038e4-a246-45b2-852d-06e1dd100900";
		}
		AccountRelation relation = this.relationDao.findAccountRlationByPersonId(personId);
		if(order==null || order.getId()==null){  // 没有批到到单据信息
			msgStr = "单号对应的订单不存在！【" +barCode+"】" ;
			checkOut.setMsg(sys+msgStr);
			checkOut.setBarCode(barCode);		
			checkOut.setAccount(relation.getAccount());
			checkOut.setCreateDate(new Date());
			checkOut.setOrderCode(order!=null ?order.getTmsOrderCode():"");
			checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
			checkOutDao.insertCheckOut(checkOut);
			retMap.put("msg", msgStr); 
			retMap.put("code", "404");
			return retMap;
		}
			try { //订单条码 检测
				
				checkOut.setOrderCode(order.getTmsOrderCode());  //单号号 条码
				checkOut.setBarCode(barCode); //  商品条码信息
				checkOut.setCpCompany(cp);//快递公司
				checkOut.setCreateDate(new Date());
//				checkOut.setCentroId(Long.valueOf(stock));
				
				checkOut.setAccount(relation.getAccount());
				
				try {  //商品明细  商品条码检测
					String  orderId   =  order.getId();
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("stockInOrderId", orderId);
					List<WmsConsignOrderItem> details = this.orderItemDao.getWmsConsignOrderItemByList(params);
					User createUser = order.getUser();
					checkOut.setUser(createUser);
					checkOut.setShipOrder(order);		
					ReceiverInfo receiverInfo = this.receiverInfoDao.getReceiverInfoById(order.getReceiverInfo().getId());
					String state=receiverInfo.getReceiverProvince()+receiverInfo.getReceiverCity();
					checkOut.setState(state);//目的地省份
					for(WmsConsignOrderItem detail  : details) {
						Item item = itemDao.getItem(detail.getItem().getId());
//						logger.debug("item:"+item.getId()+"| barCode:【"+barCode+"】itemCode:【"+item.getBarCode()+"】");
						checkOut.setBarCode(barCode);
						if(item.getBarCode()!=null  && barCode.equals(item.getBarCode().trim())){
							//匹配商品信息正确
							if(item!=null){
								if(item.getWmsGrossWeight()>0 ){ //有包裹重量  取包裹重量 。 没有时，  直接取商品重量
									//checkOut.setWeight(item.getWmsGrossWeight()*detail.getItemQuantity());
								}else{
								//	checkOut.setWeight(item.getGrossWeight()*detail.getItemQuantity());
								}
								checkOut.setItemName(item.getName());
							}
							//严重成功扫描出库   、、  扣库存
							params.clear();
							params.put("itemId", item.getId());
							params.put("orderNo", order.getTmsOrderCode());
							//Long existOrderNo = qmInventoryService.existOrderNo(params);
							
							int existOrderNo = getCheckOutByCode(order.getTmsOrderCode());
	//						Long existOrderNo =0l;
							if(existOrderNo==0){
								Date tempDate=new Date();
//								logger.debug("isCheckTrade 验货成功所花时间:"+(tempDate.getTime()-date.getTime())+"|"+retMap);
								retMap.put("code", "200");
								retMap.put("msg", "订单校验成功!");
								checkOut.setMsg(sys+"订单校验成功!");
								checkOut.setStatus(CheckOut.CheckOutStatus.SUCCESS_TRADE);
								checkOut.setNum(detail.getItemQuantity());
								//this.qmInventoryService.addInventory(Long.valueOf(stock), createUser.getId(), item.getId(), -detail.getNum(), Accounts.CODE_SALEABLE, "扫描出库", order.getExpressOrderno());
								//this.inventoryService.updateUserNum(Long.valueOf(stock),  item.getId(), Accounts.CODE_SALEABLE, -detail.getNum() );
//								logger.debug("isCheckTrade 库存所花时间:"+(tempDate.getTime()-date.getTime())+"|"+retMap);
							}else{
								retMap.put("code", "405");
								retMap.put("msg", "订单重复扫描");
								checkOut.setMsg(sys+"订单重复扫描!");
								checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
							}
							checkOutDao.insertCheckOut(checkOut);
							return retMap;
						}
					}
					msgStr="订单校验失败:[此商品与订单商品不匹配]";
				//	msgStr = "订单ID=" +orderId +";此订单内无法匹配商品条码信息 barCode =" +barCode ;
					retMap.put("orderId", orderId);
					retMap.put("msg", msgStr); 
					retMap.put("code", "500");
					checkOut.setMsg(sys+msgStr);
					checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
					checkOutDao.insertCheckOut(checkOut);
//					logger.debug("isCheckTrade 所花时间:"+(new Date().getTime()-date.getTime())+"|"+retMap);
					return retMap;
				} catch (Exception e) { // 获取商品信息出错
					e.printStackTrace();
					msgStr ="订单校验失败[商品信息获取失败!"+e.toString()+"]";
					retMap.put("msg", msgStr); 
					retMap.put("error", e);
					retMap.put("code", "500");
					checkOut.setMsg(sys+msgStr);
					checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
					checkOutDao.insertCheckOut(checkOut);
				}
			} catch (Exception e) { //获取订单信息出错
				e.printStackTrace();
				msgStr ="订单校验失败:[订单条码 " +barCode+"！"+e.toString()+"]";
				retMap.put("msg", msgStr); 
				retMap.put("error", e);
				retMap.put("code", "500");
				checkOut.setMsg(sys+msgStr);
				checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
				checkOutDao.insertCheckOut(checkOut);
			}
			return retMap;
	}

	/**
	 * 判断当前快递是否有出库过
	 * @param barCode
	 * @param orderCode
	 * @return
	 */
	private int getCheckOutByCode(String tmsOrderCode) {	
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("orderCode", tmsOrderCode);
		params.put("success","1");//成功的验货记录
		int result=this.checkOutDao.findCheckOuts(params).size();
		return result;
	}

	@Override
	public int getCheckSuccessCountByDate(Map<String, Object> params) {
		params.put("success", "1");
		int total = this.checkOutDao.findCheckOuts(params).size();
		return total;
	}

	@Override
	public int getSuccessCountByDate(Map<String, Object> params) {
		params.put("success", "1");
		int total = this.checkOutDao.findCheckOuts(params).size();
		return total;
	}

	@Override
	public int getCheckFailCountByDate(Map<String, Object> params) {
		params.put("success", "0");
		int total = this.checkOutDao.findCheckOuts(params).size();
		return total;
	}
}
