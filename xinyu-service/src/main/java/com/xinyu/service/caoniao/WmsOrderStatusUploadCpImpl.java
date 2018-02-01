package com.xinyu.service.caoniao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.pac.sdk.cp.SendSysParams;
import com.taobao.pac.sdk.cp.dataobject.request.WMS_ORDER_STATUS_UPLOAD.WmsOrderStatusUploadRequest;
import com.taobao.pac.sdk.cp.dataobject.response.WMS_ORDER_STATUS_UPLOAD.WmsOrderStatusUploadResponse;
import com.xinyu.dao.order.StockInOrderDao;
import com.xinyu.dao.order.StockOrderOperatorDao;
import com.xinyu.dao.order.StockOutOrderDao;
import com.xinyu.model.base.RdsConstants;
import com.xinyu.model.common.Entity;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.order.StockInOrder;
import com.xinyu.model.order.StockOrderOperator;
import com.xinyu.model.order.StockOutOrder;
import com.xinyu.model.order.enums.InOrderStateEnum;
import com.xinyu.model.order.enums.OrderTypeEnum;
import com.xinyu.model.order.enums.OutOrderStateEnum;
import com.xinyu.model.order.enums.StockOperateTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.enums.OrderStatusEnum;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.service.common.Constants;
import com.xinyu.service.util.CaiNiaoPacClient;

/**
 * @author shark_cj
 * @since  2017-05-23
 * 订单状态回传
 */
@Component
public class WmsOrderStatusUploadCpImpl  {

	public static final Logger logger = Logger.getLogger(WmsOrderStatusUploadCpImpl.class);
	
	
	
	@Autowired
	private StockInOrderDao stockInOrderDao;
	
	@Autowired
	private StockOutOrderDao stockOutOrderDao;
	
	@Autowired
	private StockOrderOperatorDao operatorDao;
	
		
	public Map<String,Object> updateOrderState(Entity entity,Map<String,Object> params,String orderType){
		logger.debug("状态回传执行开始!"+entity);
		WmsOrderStatusUploadRequest  request  = new WmsOrderStatusUploadRequest();
		SendSysParams sendParams=new SendSysParams();
		sendParams.setFromCode(Constants.cainiao_fromCode);
		Map<String,Object>  retMap  = new HashMap<String, Object>();
		
		Account account = SessionUser.get();
		
		if(entity  instanceof StockInOrder){//入库单状态操作
			try {
				StockInOrder  stockInOrder = (StockInOrder)entity;
				buildStockInOrder(stockInOrder, params,request);
				WmsOrderStatusUploadResponse response = CaiNiaoPacClient.getClient().send(request, sendParams);
				if(response.isSuccess()){
					String obj = ""+params.get("status");
					if("WMS_ACCEPT".equals(obj)){ //接收状态 ， 设置等待出库中
						stockInOrder.setState(InOrderStateEnum.getInOrderStateEnum("WMS_CONFIRMWAITING"));
					}else  if("WMS_REJECT".equals(obj)){ //拒单
						stockInOrder.setState(InOrderStateEnum.getInOrderStateEnum("CANCEL"));
					}
					stockInOrderDao.updateStockInOrder(stockInOrder);
					retMap.put("code", "200");
					retMap.put("msg", "菜鸟上传成功");
					this.insertStockInOrderOperate(stockInOrder,account,""+retMap.get("msg"),"success");
				}else{
					retMap.put("code", "500");
					retMap.put("msg", "菜鸟错误编码:"+response.getErrorCode()+",错误信息:"+response.getErrorMsg());
					this.insertStockInOrderOperate(stockInOrder,account,""+retMap.get("msg"),"fail");
				}
				return retMap;
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				retMap.put("code", "500");
				retMap.put("msg", e.getMessage());
				e.printStackTrace();
				return retMap;
			}
					
		}else  if(entity  instanceof StockOutOrder){
			try {
				StockOutOrder stockOutOrder = (StockOutOrder) entity;
				buildStockOutOrder(stockOutOrder,params,request);
				WmsOrderStatusUploadResponse response = CaiNiaoPacClient.getClient().send(request, sendParams);
				if (response.isSuccess()) {
					String obj = ""+params.get("status");
					if("WMS_ACCEPT".equals(obj)){ //接收状态 ， 设置等待入库中
						stockOutOrder.setState(OutOrderStateEnum.getOutOrderStateEnum("WMS_CONFIRMWAITING"));
					}else  if("WMS_REJECT".equals(obj)){ //拒单
						stockOutOrder.setState(OutOrderStateEnum.getOutOrderStateEnum("CANCEL"));
					}
					stockOutOrderDao.updateStockOutOrder(stockOutOrder);
					retMap.put("code", "200");
					retMap.put("msg", "菜鸟上传成功");
					this.insertStockOutOrderOperate(stockOutOrder,account,""+retMap.get("msg"),"sucess");
				}else{
					retMap.put("code", "500");
					retMap.put("msg", "菜鸟错误编码:"+response.getErrorCode()+",错误信息:"+response.getErrorMsg());
					this.insertStockOutOrderOperate(stockOutOrder,account,""+retMap.get("msg"),"fail");
				}
				return retMap;
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				retMap.put("code", "500");
				retMap.put("msg", e.getMessage());
				e.printStackTrace();
				return retMap;
			}
		}else if(orderType!=null && (orderType.equals("201") || orderType.equals("502"))){
			logger.debug("订单状态回传");
			try{
				buildShipOrder((ShipOrder)entity, request);
				logger.debug("订单状态回传 txt"+ request.getContent());
				WmsOrderStatusUploadResponse response = CaiNiaoPacClient.getClient().send(request, sendParams);			
			}catch(Exception e){
				e.printStackTrace();
				logger.debug(e.getMessage(), e);
				retMap.put("code", "500");
				retMap.put("msg", e.getMessage());
				e.printStackTrace();
				return retMap;
			}
			
		}else if(orderType!=null && orderType.equals(RdsConstants.ORDER_REJECT)){
			
			try{				
				request.setOrderType(String.valueOf(params.get("orderType")));
				request.setOperateDate(new Date());
				request.setStoreOrderCode((String) params.get("storeOrderCode"));
				request.setStatus(OrderStatusEnum.WMS_REJECT.getKey());
				request.setOrderCode((String) params.get("orderCode"));
				request.setOperator("cainiao");
				request.setOperatorContact("18973315680");
				request.setRemark((String)params.get("description"));
				logger.error("订单拒单"+ request.getContent());
				WmsOrderStatusUploadResponse response = CaiNiaoPacClient.getClient().send(request, sendParams);			
			}catch(Exception e){
				e.printStackTrace();
				logger.debug(e.getMessage(), e);
				retMap.put("code", "500");
				retMap.put("msg", e.getMessage());
				e.printStackTrace();
				return retMap;
			}
		}
		return null;
	}
	
	/**
	 * 入库单生成操作日志
	 * @param stockInOrder
	 * @param account
	 * */
	private void insertStockInOrderOperate(StockInOrder stockInOrder, Account account,String msg,String isSuccess) {
		
		StockOrderOperator stockOrderOperator = new StockOrderOperator();	
		stockOrderOperator.generateId();
//		stockOrderOperator.setCu("7d6f6f97-a988-45a2-a3de-zc8888centro");
		stockOrderOperator.setAccount(account);
		stockOrderOperator.setOrderId(stockInOrder.getId());
		stockOrderOperator.setOperateDate(new Date());
		stockOrderOperator.setOrderType(stockInOrder.getOrderType().getDescription());
		if (stockInOrder.getState().equals(OutOrderStateEnum.WMS_CONFIRMWAITING)&&isSuccess.equals("success")) {
			stockOrderOperator.setOperateType(StockOperateTypeEnum.RECEIVE);
			stockOrderOperator.setDescription("单据接收："+stockInOrder.getOrderCode()+"|"+msg);
			stockOrderOperator.setNewValue(OutOrderStateEnum.WMS_CONFIRMWAITING.getKey());
			stockOrderOperator.setOldValue(OutOrderStateEnum.SAVE.getKey());
		}
		
		else if(stockInOrder.getState().equals(OutOrderStateEnum.CANCEL)&&isSuccess.equals("success")){
			stockOrderOperator.setOperateType(StockOperateTypeEnum.REJECT);
			stockOrderOperator.setDescription("单据拒收："+stockInOrder.getOrderCode()+"|"+msg);
			stockOrderOperator.setNewValue(OutOrderStateEnum.CANCEL.getKey());
			stockOrderOperator.setOldValue(OutOrderStateEnum.SAVE.getKey());
		}
		
		else{
			stockOrderOperator.setOperateType(StockOperateTypeEnum.FAIL);
			stockOrderOperator.setDescription("状态修改："+stockInOrder.getOrderCode()+"|"+msg);
			stockOrderOperator.setNewValue(stockInOrder.getState().getKey());
			stockOrderOperator.setOldValue(stockInOrder.getState().getKey());
		}
		
		this.operatorDao.insertStockOrderOperator(stockOrderOperator);
	}


	/**
	 * 出库单生成操作日志
	 * @param stockOutOrder
	 * @param account
	 * */
	private void insertStockOutOrderOperate(StockOutOrder stockOutOrder, Account account,String msg,String isSuccess) {
		
		StockOrderOperator stockOrderOperator = new StockOrderOperator();	
		stockOrderOperator.generateId();
//		stockOrderOperator.setCu("7d6f6f97-a988-45a2-a3de-zc8888centro");
		stockOrderOperator.setAccount(account);
		stockOrderOperator.setOrderId(stockOutOrder.getId());
		stockOrderOperator.setOperateDate(new Date());
		stockOrderOperator.setOrderType(stockOutOrder.getOrderType().getDescription());
		
		if (stockOutOrder.getState().equals(OutOrderStateEnum.WMS_CONFIRMWAITING)&&isSuccess.equals("success")) {
			stockOrderOperator.setOperateType(StockOperateTypeEnum.RECEIVE);
			stockOrderOperator.setDescription("单据接收："+stockOutOrder.getOrderCode()+"|"+msg);
			stockOrderOperator.setNewValue(OutOrderStateEnum.WMS_CONFIRMWAITING.getKey());
			stockOrderOperator.setOldValue(OutOrderStateEnum.SAVE.getKey());
		}
		
		else if(stockOutOrder.getState().equals(OutOrderStateEnum.CANCEL)&&isSuccess.equals("success")){
			stockOrderOperator.setOperateType(StockOperateTypeEnum.REJECT);
			stockOrderOperator.setDescription("单据拒收："+stockOutOrder.getOrderCode()+"|"+msg);
			stockOrderOperator.setNewValue(OutOrderStateEnum.CANCEL.getKey());
			stockOrderOperator.setOldValue(OutOrderStateEnum.SAVE.getKey());
		}
		
		else{
			stockOrderOperator.setOperateType(StockOperateTypeEnum.FAIL);
			stockOrderOperator.setDescription("状态修改："+stockOutOrder.getOrderCode()+"|"+msg);
			stockOrderOperator.setNewValue(stockOutOrder.getState().getKey());
			stockOrderOperator.setOldValue(stockOutOrder.getState().getKey());
		}
		
		this.operatorDao.insertStockOrderOperator(stockOrderOperator);
	}


	private Map<String,Object> buildStockOutOrder(StockOutOrder stockOutOrder, Map<String, Object> params,WmsOrderStatusUploadRequest request) {
		request.setOrderType(stockOutOrder.getOrderType().getKey());
		request.setOrderCode(stockOutOrder.getOrderCode());
		String status = ""+params.get("status");
		request.setStatus(status);
		request.setOperator(stockOutOrder.getStoreCode());
		request.setOperatorContact("0731-52777568");
		request.setOperateDate(new Date());
//		request.setTimeZone(timeZone);
//		request.setFeatures(features);
		request.setRemark("中仓联调测试用例");
		request.setContent("中仓联调测试用例");
		return null;
	}


	private Map<String,Object> buildStockInOrder(StockInOrder stockInorder,Map<String,Object> params, WmsOrderStatusUploadRequest request){
		
		request.setOperateDate(new Date());
		request.setStoreOrderCode(stockInorder.getStoreCode());
		request.setOrderCode(stockInorder.getOrderCode());
//		OrderStatusEnum.getOrderStatusEnum("")
		//拒单 WMS_REJECT
		//设置接受状态  WMS_ACCEPT
		String status = ""+params.get("status");
		request.setStatus(status);
		request.setOrderType(stockInorder.getOrderType().getKey());
		request.setOperator(stockInorder.getStoreCode());
		request.setRemark("中仓联调测试用例");
		request.setContent("中仓联调测试用例");
		return null;
	}
	
	private void buildShipOrder(ShipOrder order,WmsOrderStatusUploadRequest request){
		logger.debug("shipOrder status upload"+order.getOrderType());
		request.setOrderType(String.valueOf(order.getOrderType()));
		request.setOperateDate(new Date());
		request.setStoreOrderCode(order.getStoreCode());
		request.setStatus(order.getStatus().getKey());
		request.setOrderCode(order.getOrderCode());
		request.setOperator("cainiao");
		request.setOperatorContact("18973315680");
	}
}

