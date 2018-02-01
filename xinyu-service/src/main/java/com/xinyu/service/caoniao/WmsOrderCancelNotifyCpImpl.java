package com.xinyu.service.caoniao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.taobao.pac.sdk.cp.ReceiveListener;
import com.taobao.pac.sdk.cp.ReceiveSysParams;
import com.taobao.pac.sdk.cp.dataobject.request.WMS_ORDER_CANCEL_NOTIFY.WmsOrderCancelNotifyRequest;
import com.taobao.pac.sdk.cp.dataobject.response.WMS_ORDER_CANCEL_NOTIFY.WmsOrderCancelNotifyResponse;
import com.xinyu.model.base.User;
import com.xinyu.model.order.StockInOrder;
import com.xinyu.model.order.StockOrderOperator;
import com.xinyu.model.order.StockOutOrder;
import com.xinyu.model.order.enums.OrderTypeEnum;
import com.xinyu.model.order.enums.StockOperateTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.enums.OperatorModel;
import com.xinyu.model.system.enums.OrderStatusEnum;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderOperator;
import com.xinyu.model.trade.ShipOrderStop;
import com.xinyu.model.util.MyException;
import com.xinyu.service.order.StockInOrderService;
import com.xinyu.service.order.StockOrderOperatorService;
import com.xinyu.service.order.StockOutOrderService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.trade.ShipOrderOperatorService;
import com.xinyu.service.trade.ShipOrderService;
import com.xinyu.service.trade.ShipOrderStopService;

/**
 * 单据取消接品
 * @author yangmin
 * 2017年5月1日
 *
 */
@Component
public class WmsOrderCancelNotifyCpImpl  implements ReceiveListener<WmsOrderCancelNotifyRequest, WmsOrderCancelNotifyResponse>{

	public static final Logger logger = Logger.getLogger(WmsOrderCancelNotifyCpImpl.class);
	
	@Autowired
	private ShipOrderService orderService;
	@Autowired
	private ShipOrderOperatorService operatorService;
	@Autowired
	private StockOrderOperatorService stockOrderOperatorService;
	@Autowired
	private StockOutOrderService stockOutOrderService;
	@Autowired
	private StockInOrderService stockInOrderService;
	@Autowired
	private UserService userService;
	@Autowired
	private ShipOrderStopService stopService;
	
	@Override
	public WmsOrderCancelNotifyResponse execute(ReceiveSysParams params, WmsOrderCancelNotifyRequest request) {
		logger.debug("单据取消接口!");
		WmsOrderCancelNotifyResponse response=new WmsOrderCancelNotifyResponse();
		try{
			this.orderCancel(request);
			response.setSuccess(true);
		}catch(Exception  e){
			String msg=e.getMessage();
			response.setSuccess(false);
			response.setErrorMsg(msg);
			e.printStackTrace();
		}
		
		return response;
	}

	/**
	 * 单据取消业务处理
	 * @param request
	 */
	@Transactional
	private void orderCancel(WmsOrderCancelNotifyRequest request) throws MyException{
		String orderCode=request.getOrderCode();
		String type=request.getOrderType();
		OrderTypeEnum ordertype=OrderTypeEnum.getOrderTypeEnum(type);
		logger.debug("ordertype:"+ordertype.getKey());
		
		String ownerCode=request.getOwnerUserId();	
		User user = userService.getUserByOwnerCode(ownerCode);
		
		if(OrderTypeEnum.SHIPORDER.equals(ordertype)){
		
			logger.error("orderCancel:"+ownerCode+"|"+user);
//			Map<String,Object> resultMap=this.orderService.orderCancel(orderCode, user==null?ownerCode:user.getId(), type);
//			
			/**
			 * 查询改成精确单个查询
			 * */
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("orderCode", orderCode);
			ShipOrder order = this.orderService.getShipOrderByParams(params);
			if (order!=null) {
				if( order.getStatus().equals(OrderStatusEnum.WMS_GETNO) 
						|| order.getStatus().equals(OrderStatusEnum.WMS_PRINT) 
						|| order.getStatus().equals(OrderStatusEnum.WMS_FINASH)){
					throw new MyException("此订单已发货，请联系WMS处理!");
				}else{
					logger.error("订单订单取消操作允许！");
					order.setStatus(OrderStatusEnum.WMS_CANCEL);
					order.setLastUpdateTime(new Date());
					this.orderService.updateShipOrder(order);
					//写操作记录
					ShipOrderOperator operator=new ShipOrderOperator();
					operator.generateId();
					//operator.setAccount((Account) SessionUser.get());
					Account account = new Account();
					account.setId("cainiao");
					operator.setAccount(account);
					operator.setDescription(orderCode+"菜鸟单据取消！");
					operator.setOperatorDate(new Date());
					operator.setOperatorModel(OperatorModel.TRADE_CANCEL);
					operator.setShipOrder(order);
					operator.setOldValue(order.getStatus().getKey());
					operator.setNewValue(OrderStatusEnum.WMS_CANCEL.getKey());
					this.operatorService.saveShipOrderOperator(operator);
					
					this.orderService.orderCancel(orderCode, user==null?ownerCode:user.getId(), type);
				}
			}
			
		}else if (OrderTypeEnum.NORMALSO.equals(ordertype)) {
			//普通出库单取消
			this.StockOutOrderCancel(orderCode, user.getId(), type);
		}else if (OrderTypeEnum.TRANSFERSSO.equals(ordertype)) {
			//调拨出库单取消
			this.StockOutOrderCancel(orderCode, user.getId(), type);
		}else if(OrderTypeEnum.PURCHASEGRN.equals(ordertype)){
			//采购入库单取消
			this.StockInOrderCancel(orderCode, user.getId(), type);
		}else if(OrderTypeEnum.NORMALGRN.equals(ordertype)){
			//普通入库单取消
			this.StockInOrderCancel(orderCode, user.getId(), type);
		}else if(OrderTypeEnum.SALEGRN.equals(ordertype)){
			//销退入库单取消
			this.StockInOrderCancel(orderCode, user.getId(), type);
		}else if(OrderTypeEnum.TRANSFERGRN.equals(ordertype)){
			//调拨入库单取消
			this.StockInOrderCancel(orderCode, user.getId(), type);
		}
	}

	/**
	 * 入库单取消
	 * @param orderCode
	 * @param userId
	 * @param type
	 * */
	private void StockInOrderCancel(String orderCode, String userId, String type) {
		
		Map<String, Object> map = this.stockInOrderService.orderCancel(orderCode, userId, type);
		
		//写操作日志
		StockOrderOperator operator = new StockOrderOperator();
		operator.generateId();
		Account account = new Account();
		account.setId("cainiao");
		operator.setAccount(account);
		operator.setDescription("菜鸟单据取消:" + orderCode + "," + map.get("msg"));
		operator.setOperateDate(new Date());
		operator.setOperateType(StockOperateTypeEnum.CANCEL);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		StockInOrder stockInOrder = this.stockInOrderService.findStockInOrderByParams(params);
		operator.setOrderId(stockInOrder.getId());
		operator.setOrderType(stockInOrder.getOrderType().getDescription());
		operator.setNewValue(orderCode);
		operator.setOldValue(orderCode);
		this.stockOrderOperatorService.insertStockOrderOperator(operator);
	}

	/**
	 * 出库单取消
	 * @param orderCode
	 * @param userId
	 * @param type
	 * @throws MyException 
	 * */
	private void StockOutOrderCancel(String orderCode, String userId, String type) throws MyException{
		
		Map<String, Object> map = this.stockOutOrderService.orderCancel(orderCode, userId, type);
		
		//写操作日志
		StockOrderOperator operator = new StockOrderOperator();
		operator.generateId();
		Account account = new Account();
		account.setId("cainiao");
		operator.setAccount(account);
		operator.setDescription("菜鸟单据取消:" + orderCode + "," + map.get("msg"));
		operator.setOperateDate(new Date());
		operator.setOperateType(StockOperateTypeEnum.CANCEL);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		StockOutOrder stockOutOrder = this.stockOutOrderService.findStockOutOrderByParam(params);
		operator.setOrderId(stockOutOrder.getId());
		operator.setOrderType(stockOutOrder.getOrderType().getDescription());
		operator.setNewValue(orderCode);
		operator.setOldValue(orderCode);
		this.stockOrderOperatorService.insertStockOrderOperator(operator);	
	}

}
