package com.graby.store.service.check;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.CheckOutDao;
import com.graby.store.dao.mybatis.ItemDao;
import com.graby.store.dao.mybatis.ShipOrderDao;
import com.graby.store.dao.mybatis.ShipOrderReturnDao;
import com.graby.store.dao.mybatis.UserDao;
import com.graby.store.entity.CheckOut;
import com.graby.store.entity.CheckOut.CheckOutStatus;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.ShipOrderReturn;
import com.graby.store.entity.Trade;
import com.graby.store.entity.User;
import com.graby.store.remote.InventoryRemote;
import com.graby.store.service.inventory.Accounts;
import com.graby.store.service.inventory.InventoryService;
import com.graby.store.service.inventory.QmInventoryService;
import com.graby.store.util.qm.HttpClientUtils;



@Component
@Transactional
public class CheckService {
	
	public static final Logger logger = Logger.getLogger(CheckService.class);
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ShipOrderDao shipOrderDao;
	
	@Autowired
	private CheckOutDao  checkOutDao;
	@Autowired
	private QmInventoryService  qmInventoryService;
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private ShipOrderReturnDao  shipOrderReturnDao;
	
	
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
	public Map<String,Object> getItemInfoBybarCode(String barCode){
		
		Map<String,Object>  retMap  =  new HashMap<String,Object>();
		Item item  = null;
		try {
			item = itemDao.findItmeByBarCode(barCode);
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
	public Map<String,Object> getItemInfoBybarCodes(String barCode){
		
		Map<String,Object>  retMap  =  new HashMap<String,Object>();
		List<Item> list  = null;
		try {
			list= itemDao.findItmeByBarCodes(barCode);
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
	
	private Map<String,Object> noCheckTrade(String orderCode ,  String barCode,String stock,String cp,String personId){
		Map<String,Object>  retMap  =  new HashMap<String,Object>();
		Item item  = null;
		try {
			item = itemDao.findItmeByBarCode(barCode);
			CheckOut checkOut  = new CheckOut();
			checkOut.setOrderCode(orderCode);  //单号号 条码
			checkOut.setBarCode(barCode); //  商品条码信息
			checkOut.setCpCompany(cp);//快递公司
			checkOut.setCentroId(Long.valueOf(stock));//仓库ID
			checkOut.setPersonId(Long.valueOf(personId)); //操作人员
			checkOut.setCreateDate(new Date());
			if (item != null) {
//				checkOut.setCentroId(Long.valueOf(userDao.get(item.getUserid()).getCentroId()));
				checkOut.setItemId(item.getId()); //商品ID
				checkOut.setItemName(item.getTitle());
				checkOut.setUserId(item.getUserid());
				if(item.getPackageWeight() >0 ){ //有包裹重量  取包裹重量 。 没有时，  直接取商品重量
					checkOut.setWeight(item.getPackageWeight());
				}else{
					checkOut.setWeight(item.getWeight());
				}
				//严重成功扫描出库   、、  扣库存
				Map<String,Object> params   =   new HashMap<String,Object>();
				params.put("itemId", item.getId());
				params.put("orderNo", orderCode);
				Long existOrderNo = qmInventoryService.existOrderNo(params);
				if(existOrderNo==0){
					retMap.put("item", item);
					retMap.put("code", "200");
					checkOut.setMsg("商品验证成功");
					checkOut.setStatus(CheckOutStatus.SUCCESS_GOODS);
					checkOut.setNum(1L);
					qmInventoryService.addInventory(Long.valueOf(stock), item.getUserid(), item.getId(), -1L, Accounts.CODE_SALEABLE, "扫描出库(不检查订单)", orderCode);
//					if(order.getSource().equals(Trade.SourceFlag.SourceFlag_QM)){
//						this.inventoryService.updateUserNum(Long.valueOf(stock),  item.getId(), Accounts.CODE_SALEABLE, -detail.getNum() );
//					}
				}else{
					retMap.put("item", item);
					retMap.put("code", "406");
					retMap.put("msg","订单重复扫描!"); 
					checkOut.setMsg("订单重复扫描");
					checkOut.setStatus(CheckOutStatus.FAIL_GOODS);
				}
				checkOutDao.save(checkOut);
			}else{
				String msgStr= "["+barCode+"]此条码对应的商品不存在！" ;
				retMap.put("code", "404");
				retMap.put("msg",msgStr); 
				checkOut.setMsg(msgStr);
				checkOut.setStatus(CheckOutStatus.FAIL_GOODS);
				checkOutDao.save(checkOut);
			}
			return retMap ;
		} catch (Exception e) {
			retMap.put("msg","商品接口调用失败[barCode=" +barCode+",{"+e.toString()+"}]"); 
			retMap.put("error", e);
			retMap.put("code", "500");
		}
		return retMap;
		
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
	 * synchronized
	 */
	public  Map<String,Object>  checkTrade(String orderCode ,  String barCode,String stock,String cp,String userId,String personId,List<String> sysItemList,String sys){
		Date Date=new Date();		
		//检测订单是否 是 一退货订单
		logger.error(sys+"验货入口:"+orderCode+"|"+barCode+"|");
		Date date=new Date();
		ShipOrder shipOrder=this.shipOrderDao.findShipOrderByExpressOrderno(orderCode);	
		logger.error("取订单时间:"+(new Date().getTime()));
		Map<String, Object>  params =  new HashMap<String,Object>();
		if(shipOrder!=null){
			params.put("orderId", shipOrder.getId());
		}else{
			params.put("expressOrderno",orderCode);
		}
		date=new Date();
		List<ShipOrderReturn> returns = shipOrderReturnDao.findShipOrderByexpress(params);
		logger.error("判断退货时间:"+(new Date().getTime()-date.getTime()));
		if(returns!=null  &&  returns.size()>0) {
			CheckOut checkOut  = new CheckOut();
			checkOut.setOrderCode(orderCode);  //单号号 条码
			checkOut.setBarCode(barCode); //  商品条码信息
			checkOut.setCpCompany(cp);//快递公司
			checkOut.setCreateDate(new Date());
			checkOut.setCentroId(Long.valueOf(stock));
			checkOut.setWeight(0D);
			String	msgStr = "此订单号已退货！【" +orderCode+"】" ;
			checkOut.setMsg(sys+msgStr);
			checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
			//checkOutDao.save(checkOut);
			this.saveCheckOut(checkOut);
			Map<String,Object>  retMap  =  new HashMap<String,Object>();
			retMap.put("msg", msgStr); 
			retMap.put("code", "406");
			return retMap;
		}
				
		if(shipOrder!=null){
			userId=""+shipOrder.getCreateUser().getId();
		}
		if(shipOrder==null){
			shipOrder=new ShipOrder();
			shipOrder.setId(0L);
			shipOrder.setExpressCode(orderCode);
		}
		/**
		 * 对所有的订单进行校验操作
		 */
		date=new Date();
		Map<String,Object> resultMap=isCheckTrade(shipOrder, barCode,stock,cp,personId,sys);
		logger.error("service 校验订单时间:"+(new Date().getTime()-date.getTime()));
		return resultMap;
		/*
		if("0".equals(userId)){   //是否  开启订单检测商家范围
			return isCheckTrade(shipOrder, barCode,stock,cp,personId,sys);
		}
		if(sysItemList.contains(userId)){
//			logger.info("checkTrade:{userId："+userId+";orderCode:"+orderCode+";barCode:"+barCode+"}");
			return isCheckTrade(shipOrder, barCode, stock, cp, personId,sys);
		}
//		logger.info("checkItem:{userId："+userId+";orderCode:"+orderCode+";barCode:"+barCode+"}");
		return noCheckTrade(orderCode, barCode,stock,cp,personId);
		*/
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
	private Map<String,Object> isCheckTrade(ShipOrder order,String barCode,String stock,String cp,String personId,String sys){
		Date date=null;
		Map<String,Object>  retMap  =  new HashMap<String,Object>();
		CheckOut checkOut  = null;
		String msgStr = "";
		checkOut  = new CheckOut();
		if(order==null || order.getId()==0){  // 没有批到到单据信息
			msgStr = "单号对应的订单不存在！【" +barCode+"】" ;
			checkOut.setMsg(sys+msgStr);
			checkOut.setBarCode(barCode);
			checkOut.setPersonId(Long.valueOf(personId));
			checkOut.setCreateDate(new Date());
			checkOut.setOrderCode(order!=null ?order.getExpressOrderno():"");
			checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
			this.saveCheckOut(checkOut);
			retMap.put("msg", msgStr); 
			retMap.put("code", "404");
			return retMap;
		}
			try { //订单条码 检测
				
				checkOut.setOrderCode(order.getExpressOrderno());  //单号号 条码
				checkOut.setBarCode(barCode); //  商品条码信息
				checkOut.setCpCompany(cp);//快递公司
				checkOut.setCreateDate(new Date());
				checkOut.setCentroId(Long.valueOf(stock));
				checkOut.setPersonId(Long.valueOf(personId));
				
				try {  //商品明细  商品条码检测
					Long  orderId   =  order.getId();
					date=new Date();
					List<ShipOrderDetail> details = shipOrderDao.getShipOrderDetailByOrderId(orderId);
					logger.error("取订单明细所花时间:"+(new Date().getTime()-date.getTime()));
					User createUser = order.getCreateUser();
					checkOut.setUserId(createUser.getId());
					checkOut.setOrderId(orderId);
					String state=order.getReceiverState()+order.getReceiverCity();
					checkOut.setState(state);//目的地省份
					String itemBarCode="";
					for(ShipOrderDetail detail  : details) {
						Item item = itemDao.get(detail.getItem().getId());
//						logger.debug("item:"+item.getId()+"| barCode:【"+barCode+"】itemCode:【"+item.getBarCode()+"】");
						checkOut.setBarCode(barCode);
						itemBarCode=item.getBarCode();
						if(item.getBarCode()!=null  && barCode.trim().equals(item.getBarCode().trim())){
							//匹配商品信息正确
							if(item!=null){
								if(item.getPackageWeight() >0 ){ //有包裹重量  取包裹重量 。 没有时，  直接取商品重量
									checkOut.setWeight(item.getPackageWeight()*detail.getNum());
								}else{
									checkOut.setWeight(item.getWeight()*detail.getNum());
								}
								checkOut.setItemId(item.getId());
								checkOut.setItemName(item.getTitle());
							}
							//严重成功扫描出库   、、  扣库存
							Map<String,Object> params   =   new HashMap<String,Object>();
							params.put("itemId", item.getId());
							params.put("orderNo", order.getExpressOrderno());
							date=new Date();
							int existOrderNo = getCheckOutByCode(order.getExpressOrderno());
							logger.error("判断订单是否重复所花时间:"+(new Date().getTime()-date.getTime()));
							if(existOrderNo==0){
								retMap.put("code", "200");
								retMap.put("msg", "订单校验成功!");
								checkOut.setMsg(sys+"订单校验成功!");
								checkOut.setStatus(CheckOut.CheckOutStatus.SUCCESS_TRADE);
								checkOut.setNum(detail.getNum());
							}else{
								retMap.put("code", "405");
								retMap.put("msg", "订单重复扫描");
								checkOut.setMsg(sys+"订单重复扫描!");
								checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
								logger.error("系统重复扫描:"+orderId+"|"+order.getExpressOrderno()+"|"+barCode);
							}
							this.saveCheckOut(checkOut);
							return retMap;
						}
					}
					msgStr="失败:[此商品与订单商品不匹配]";
					logger.error("["+barCode+"]["+itemBarCode+"]商品条码信息不匹配!");
					retMap.put("orderId", orderId);
					retMap.put("msg", msgStr); 
					retMap.put("code", "500");
					checkOut.setMsg(sys+msgStr);
					checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
					this.saveCheckOut(checkOut);
					return retMap;
				} catch (Exception e) { // 获取商品信息出错
					logger.error("验货异常信息:"+e.getMessage());
					e.printStackTrace();
					msgStr ="失败[商品信息获取失败!"+e.toString()+"]";
					retMap.put("msg", msgStr); 
					retMap.put("error", e);
					retMap.put("code", "500");
					checkOut.setMsg(sys+msgStr);
					checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
					checkOutDao.save(checkOut);
				}
			} catch (Exception e) { //获取订单信息出错
				logger.error("验货取订单异常信息:"+e.getMessage());
				e.printStackTrace();
				msgStr ="失败:[订单条码 " +barCode+"！"+e.toString()+"]";
				retMap.put("msg", msgStr); 
				retMap.put("error", e);
				retMap.put("code", "500");
				checkOut.setMsg(sys+msgStr);
				checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_TRADE);
				checkOutDao.save(checkOut);
			}
			return retMap;
	}
	
	
	/**
	 * 分页条件查询扫码记录
	 * @param barCode,orderCode,itemName,orderId,itemId,userId,status,q,startDate,endDate,start,offset
	 * @param rows
	 * @param page
	 * @return checkOuts
	 * */
	public List<CheckOut> findCheckOutByPage(int page,int rows,Map<String,Object> params){
		int start=(page-1)*rows;
		int offset=rows;
		params.put("offset", offset);
		params.put("start", start);
		List<CheckOut> checkOuts=this.checkOutDao.findCheckOut(params);
		return checkOuts;
	}
	
	/**
	 * 条件查询扫码记录
	 * @param barCode,orderCode,itemName,orderId,itemId,userId,status,q,startDate,endDate,start,offset
	 * @return checkOuts
	 * */
	public List<CheckOut> findCheckOut(Map<String,Object> params){
		List<CheckOut> checkOuts=this.checkOutDao.findCheckOut(params);
		return checkOuts;
	}
	
	/**
	 * 获取总记录数
	 * @param barCode,orderCode,itemName,orderId,itemId,userId,status,q,startDate,endDate
	 * @return int
	 * */
	public int getTotalResult(Map<String,Object> params){
		return checkOutDao.getTotalResult(params);
	}
	
	/**
	 * 条件查询成功记录
	 * @param status,orderCode,userId,startDate,endDate
	 * @return list<map>
	 * */
	public List<Map<String,Object>> findCheckOutByStatus(Map<String,Object> params){
		return checkOutDao.findCheckOutByStatus(params);
	}
	/**
	 * 按商品分组条件查询记录
	 * @param status,orderCode,userId,startDate,endDate
	 * @return list<map>
	 * */
	public List<Map<String,Object>> findCheckOutByItem(Map<String,Object> params){
		return checkOutDao.findCheckOutByItem(params);
	}
	
	/**
	 * 按物流公司分组条件查询记录
	 * @param status,orderCode,userId,startDate,endDate
	 * @return list<map>
	 * */
	public List<Map<String,Object>> findCheckOutByExpress(Map<String,Object> params){
		return checkOutDao.findCheckOutByExpress(params);
	}
	/**
	 * 更新checkout
	 * @param checkOut
	 * */
	public void updateCheckOut(CheckOut checkOut) {
		checkOutDao.updateCheckOut(checkOut);
	}
	
	
	/**
	 * 新增checkout
	 * @param checkOut
	 * */
	public void saveCheckOut(CheckOut checkOut) {
		Date date=new Date();
		checkOutDao.save(checkOut);
		logger.error("saveCheckOut:"+(new Date().getTime()-date.getTime()));
	}
	/**
	 * 重构去除重复数据,这里只处理订单成功与商品成功的快递单重复
	 * @param start
	 * @param end
	 */
	public int buildCheckOut(String start,String end){
		int i=0;
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("startDate", start);
		params.put("endDate", end);
		/**
		 * 查询出所有重复的快递单条码
		 */
		List<Map<String,Object>> mapList=this.checkOutDao.findCheckOuttoMany(params);
		for(Map<String,Object> map:mapList){
			String orderCode=(String) map.get("orderCode");
			List<CheckOut> list=this.checkOutDao.findCheckbyOrderCode(orderCode);
			if(list!=null && list.size()>0){
				logger.info("出库重复去除:【"+i+"】"+orderCode);
				CheckOut out=list.get(0);
				this.checkOutDao.deleteCheckOrderById(out.getId().toString());
			}
			i++;
		}
		return i;
	}
	
	/**
	 * 判断当前快递是否有出库过
	 * @param barCode
	 * @param orderCode
	 * @return
	 */
	private int getCheckOutByCode(String orderCode){
		Date date=new Date();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		int result = this.checkOutDao.isExistsOrderCode(params);
		logger.error("判断是否重复出库所花费时间:getCheckOutByCode["+(new Date().getTime()-date.getTime())+"]");
		return result;
	} 
	
	
	/**
	 * 出库明细查询
	 * @param map
	 * @return list<map>
	 * */
	public List<Map<String,Object>> findCheckOutDetail(Map<String, Object> params) {
		return this.checkOutDao.findCheckOutDetail(params);
	}
	/**
	 * 未出库明细查询
	 * @param map
	 * @return list<map>
	 * */
	public List<Map<String, Object>> sumTradeOuts(Map<String, Object> params) {
		return this.checkOutDao.sumTradeOuts(params);
	}
	
	
	public String checkHttpTrade(String url){
		 String result=HttpClientUtils.httpGet(url, null);
		 return result;
	}


	public void saveCheckOutList(List<CheckOut> outList) {
		// TODO Auto-generated method stub
		for(int i=0;i<outList.size();i++){
			this.checkOutDao.save(outList.get(i));
		}
		
	}
}
