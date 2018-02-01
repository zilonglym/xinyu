package com.xinyu.controller.qm;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyu.model.base.User;
import com.xinyu.model.base.enums.XmlEnum;
import com.xinyu.model.system.SystemItem;
import com.xinyu.model.system.enums.OperatorModel;
import com.xinyu.model.system.enums.OrderStatusEnum;
import com.xinyu.model.system.enums.StoreSystemItemEnums;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderOperator;
import com.xinyu.model.trade.WmsConsignOrderItem;
import com.xinyu.service.inventory.InventoryRecordService;
import com.xinyu.service.inventory.InventoryService;
import com.xinyu.service.qm.QmMethodInterface;
import com.xinyu.service.qm.QmSyncQueryService;
import com.xinyu.service.qm.QmSyncService;
import com.xinyu.service.system.SystemItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.trade.ShipOrderOperatorService;
import com.xinyu.service.trade.ShipOrderService;
import com.xinyu.service.trade.WmsConsignOrderItemService;
import com.xinyu.service.util.XmlUtil;



/**
 * 奇门接口入口
 * 
 * @author 杨敏
 *
 */
@Controller
public class QmWebController extends QMBaseController{

	public static final Logger logger = Logger.getLogger(QmWebController.class);
	
	@Autowired
	public QmSyncQueryService qmSyncQueryService;
	
	@Autowired
	private QmSyncService qmSyncService;
	
	@Autowired
	private ShipOrderService shipOrderService;
	
	@Autowired
	private ShipOrderOperatorService shipOrderOperatorService;
	
	@Autowired
	private WmsConsignOrderItemService orderItemService;
	
	@Autowired
	private SystemItemService sysService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private InventoryRecordService inventoryRecordService;

	/**
	 * 奇门调用WMS接口入口
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "qm_index", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String method = request.getParameter("method");// 调用API的方法名，根据相就在的method来调用不同的方法。
		Map<String, Object> resultMap = new HashMap<String, Object>();
		InputStreamReader reader = new InputStreamReader(request.getInputStream(), "UTF8");
		char[] buff = new char[1024];
		int length = 0;
		StringBuilder requestStr=new StringBuilder();
		while ((length = reader.read(buff)) != -1) {
			String x = new String(buff, 0, length);
			requestStr.append(x);
		}
		String xmlStr = requestStr.toString();
		logger.info("奇门调用参数 qm_index:"+xmlStr);
		String msg = "";
		String resultStr="";
		try {
			if (method == null || method.length() == 0) {
				resultMap.put("flag", "failure");
				resultMap.put("message", "method参数为空!");
				String resultXml = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
				return resultXml;
			}
			
			if (method.equals("items.synchronize")) {
				msg = this.qmSyncService.itemBatchSync(xmlStr);
			}else if (method.equals("singleitem.synchronize")) {// 商品同步 OK
				msg = this.qmSyncService.itemSync(xmlStr);
			} else if (method.equals("combineitem.synchronize")) {// 组合商品
				msg = this.qmSyncService.combineitem(xmlStr);
			} else if (method.equals("entryorder.create")) {// 入库单创建 OK
				msg = this.qmSyncService.entryorder(xmlStr);
			} else if (method.equals("returnorder.create")) {// 退货入库单 ok
				msg = this.qmSyncService.returnorder(xmlStr);
			} else if (method.equals("stockout.create")) {// 出库单创建 OK
				msg = this.qmSyncService.stockout(xmlStr);
			} else if (method.equals("deliveryorder.create")) {// 发货单创建 OK
				msg = this.qmSyncService.deliveryorder(xmlStr);
			} else if (method.equals("deliveryorder.query")) {// 发货单查询接口
				resultStr=this.qmSyncService.deliveryQuery(xmlStr);
			} else if (method.equals("orderprocess.query")) {// 订单查询接口
				resultStr=this.qmSyncService.orderprocessQuery(xmlStr);
			}else if(method.equals("order.cancel")){//单据取消接口
				logger.debug("单据取消:"+xmlStr);
				msg=this.qmSyncService.orderCancel(xmlStr);
				logger.debug("单据取消返回:"+msg);
			}else if(method.equals("inventory.query")){//库存查询接口
				resultStr=this.qmSyncService.inventoryQuery(xmlStr);
			}else if(method.equals("storeprocess.create")){//仓内加工单创建接口 OK
				msg=this.qmSyncService.storeprocessCreate(xmlStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("flag", "failure");
			resultMap.put("message", msg);
			resultMap.put("itemId", "");
			msg = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
		}
		return msg;
	}
	
	/**
	 *跳转发货确认界面
	 * @return
	 */
	@RequestMapping(value="/store/shipOrder/toConfirmDelivery/{id}")
	public String confirmDeliveryOrder(@PathVariable String id,HttpServletRequest request,ModelMap model){
		ShipOrder shipOrder = shipOrderService.findShipOrderById(id);
		model.addAttribute("order", shipOrder);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", StoreSystemItemEnums.LOGISTICS.getKey());
		List<SystemItem> itemList=this.sysService.findSystemItemByList(params);
		model.put("logistList", itemList);
		return "admin/qm/deliveryorderList";
	}
	
	/**
	 *跳转发货确认界面
	 * @return
	 */
	@RequestMapping(value="/store/shipOrder/toItemLack/{id}")
	public String toItemLack(@PathVariable String id,HttpServletRequest request,ModelMap model){
		ShipOrder shipOrder = shipOrderService.findShipOrderById(id);
		model.addAttribute("order", shipOrder);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", StoreSystemItemEnums.LOGISTICS.getKey());
		List<SystemItem> itemList=this.sysService.findSystemItemByList(params);
		List<WmsConsignOrderItem> detailList=new ArrayList<WmsConsignOrderItem>();
		for(int i=0;shipOrder.getOrderItemList()!=null && i<shipOrder.getOrderItemList().size();i++){
			WmsConsignOrderItem detail=shipOrder.getOrderItemList().get(i);
			detailList.add(detail);
		}
		model.put("logistList", itemList);
		model.put("detailList", detailList);
		return "store/list/itemLackDetail";
	}
	
	/**
	 * 批量发货确认
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/store/shipOrder/submitButchDeliverOrder")
	@ResponseBody
	public Map<String,Object> batchSubmitDeliverOrder(HttpServletRequest request){
		String ids=request.getParameter("ids");
		String[] idAry=ids.split(",");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		String xmlStr="";
		try{
			for(int i=0;i<idAry.length;i++){
				ShipOrder shipOrder = shipOrderService.findShipOrderById(idAry[i]);
				User user=this.userService.getUserById(shipOrder.getUser().getId());
				Map<String,Object> orderMap=new HashMap<String, Object>();
				/**
				 * 判断当前单据是否有拆过单,如果拆过单，则调用buildOrderDetailInfo方法重构明细
				 */
				if(shipOrder!=null && shipOrder.getOrderSource()>=0){
					buildOrderDetailInfo(shipOrder);
				}
				xmlStr=buildDeliverOrderInfo(orderMap, shipOrder,user);
				String  retStr  = qmSyncQueryService.submitQm(xmlStr, QmMethodInterface.METHOD_DELIVERORDER,user.getOwnerCode());
				logger.info("DELIVERORDER_XMLSTR:"+retStr);
				//请求成功
				if(retStr.indexOf("success")!=-1){
					/**
					 * 发货成功进行操作
					 * 1.运单号之类的数据进行返回
					 * 2.单据的状态进行改写
					 * 3.发货数据保存
					 * 4.生成日志
					 * 5.生成库存流水记录
					 * 暂时不反写操作,运单号在打面单的时候已写入数据库，这里不能修改  2015-10-12
					 */
					String oldValue = shipOrder.getStatus().getKey();
					shipOrder.setStatus(OrderStatusEnum.WMS_FINASH);
					this.shipOrderService.updateShipOrder(shipOrder);
					//生成日志
					ShipOrderOperator record=new ShipOrderOperator();
					record.generateId();
					record.setOldValue(oldValue);
					record.setNewValue(OrderStatusEnum.WMS_FINASH.getKey());
					record.setCu("");
					record.setDescription("奇门订单发货确认!");
					record.setOperatorDate(new Date());
					record.setOperatorModel(OperatorModel.TRADE_CONFIRM);
					record.setShipOrder(shipOrder);
					record.setAccount(this.getCurrentAccount());
					this.shipOrderOperatorService.saveShipOrderOperator(record);
					
					resultMap.put("ret", 1);
					//生成出库记录
 					this.itemInventoryHandle(shipOrder);
				}else{
					resultMap.put("ret", 0);
				}
			}
			return resultMap;
		}catch(Exception e){
			e.printStackTrace();
			System.err.println(e.getMessage());
			resultMap.put("ret", 0);
			return resultMap;
		}
	}
	
	
	/**
	 * 确认发货生成出库记录
	 * @param shipOrder
	 */
	private void itemInventoryHandle(ShipOrder shipOrder){
		List<WmsConsignOrderItem> orderItems = shipOrder.getOrderItemList();
		for(WmsConsignOrderItem orderItem:orderItems){
//			InventoryRecord record = new InventoryRecord();
//			record.generateId();
//			record.setCu("");
//			record.setAccount(this.getCurrentAccount());
//			record.setItem(orderItem.getItem());
//			User user = this.userService.getUserById(orderItem.getUserId());
//			record.setUser(user);
//			record.setCreateDate(new Date());
//			record.setNum(orderItem.getItemQuantity());
//			record.setItemType(null);
//			record.setOperateType(OperateTypeEnum.outOrder);
//			record.setDescription("订单发货确认出库！");
//			this.inventoryRecordService.saveInventoryRecord(record);
		}
		
//		List<ShipOrderDetail> detailList=this.shipOrderService.getShipOrderDetailByOrderId(shipOrder.getId());
//		for(ShipOrderDetail detail :detailList){
//			 logger.debug("qm item inventory:"+detail.getItem().getId()+"|"+detail.getNum());
//			 this.inventoryService.addInventory(Long.valueOf(BaseResource.getCurrentCentroId()), shipOrder.getCreateUser().getId(), detail.getItem().getId(), -detail.getNum(), Accounts.CODE_SALEABLE, "确认出库", shipOrder.getExpressOrderno());
//			 this.inventoryService.updateUserNum(Long.valueOf(BaseResource.getCurrentCentroId()),  detail.getItem().getId(), Accounts.CODE_SALEABLE, -detail.getNum() );
//		}
		//qmInventoryService.addInventory(Long.valueOf(stock), createUser.getId(), item.getId(), -detail.getNum(), Accounts.CODE_SALEABLE, "扫描出库", order.getExpressOrderno());
		//this.inventoryService.updateUserNum(Long.valueOf(stock),  item.getId(), Accounts.CODE_SALEABLE, -detail.getNum() );
	}
	
	/**
	 * 提交发货确认信息
	 * @return
	 */
	@RequestMapping(value="/store/shipOrder/submitDeliverOrder/{id}")
	public String submitDeliverOrder(@PathVariable String id,HttpServletRequest request,ModelMap model){
		try{
		ShipOrder shipOrder = shipOrderService.findShipOrderById(id);
		User user=this.userService.getUserById(shipOrder.getUser().getId());
		Map<String,Object> orderMap=new HashMap<String, Object>();
		/**
		 * 判断当前单据是否有过拆单,如果有拆过单则调用buildOrderDetailInfo方法重构明细
		 */
		if(shipOrder!=null && shipOrder.getOrderSource()>=0){
			//有过拆单，这里就要进行组合
			buildOrderDetailInfo(shipOrder);
		}
		String xmlStr = buildDeliverOrderInfo(orderMap, shipOrder,user);
		logger.debug("xmlStr:"+xmlStr);
		String  retStr  = qmSyncQueryService.submitQm(xmlStr, QmMethodInterface.METHOD_DELIVERORDER,user.getOwnerCode());
		logger.debug("retStr:"+retStr);
		//请求成功
		if(retStr.indexOf("success")!=-1){
			/**
			 * 发货成功进行操作
			 * 1.运单号之类的数据进行返回
			 * 2.单据的状态进行改写
			 * 3.发货数据保存
			 * 4.生成日志
			 * 5.生成库存流水记录
			 * 暂时不反写操作,运单号在打面单的时候已写入数据库，这里不能修改  2015-10-12
			 */
			String oldValue = shipOrder.getStatus().getKey();
			shipOrder.setStatus(OrderStatusEnum.WMS_FINASH);
			
			ShipOrderOperator record=new ShipOrderOperator();
			record.generateId();
			record.setOldValue(oldValue);
			record.setNewValue(OrderStatusEnum.WMS_FINASH.getKey());
			record.setCu("");
			record.setDescription("奇门订单发货确认!");
			record.setOperatorDate(new Date());
			record.setOperatorModel(OperatorModel.TRADE_CONFIRM);
			record.setShipOrder(shipOrder);
			record.setAccount(this.getCurrentAccount());
			this.shipOrderOperatorService.saveShipOrderOperator(record);
			//生成出库记录
			this.itemInventoryHandle(shipOrder);
		}
		}catch(Exception  e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		//跳转回发货确表
		return "redirect:/store/shipOrder/deliveryorderList";
	}

	
	//itemlack
	@RequestMapping(value="/store/shipOrder/itemLack/{id}")
	public String itemLack(@PathVariable String id,HttpServletRequest request,ModelMap model){
		ShipOrder shipOrder = shipOrderService.findShipOrderById(id);
		User user=this.userService.getUserById(shipOrder.getUser().getId());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("warehouseCode", QmMethodInterface.warehouseCode);
		resultMap.put("deliveryOrderCode", shipOrder.getErpOrderCode());
		resultMap.put("deliveryOrderId", shipOrder.getId());
		resultMap.put("createTime", sdf.format(new Date()));
		resultMap.put("outBizCode", shipOrder.getErpOrderCode());
		
		String[] itemIdAry = request.getParameterValues("itemId");
		String[] itemAry = request.getParameterValues("item");
		String[] numsPar = request.getParameterValues("num");
		String[] detailAry=request.getParameterValues("detailId");
		String[] reasonAry=request.getParameterValues("reason");
		
		List<Map<String,Object>> itemList=new ArrayList<Map<String,Object>>();
		Map<String,Object> itemMap=new HashMap<String,Object>();
		itemMap.put("item", itemList);
		resultMap.put("items", itemMap);
		for(int i=0;i<numsPar.length;i++){
			String num=numsPar[i];
			if(num==null || num.equals("0") || num.length()==0){
				continue;
			}
			String detailId=detailAry[i];
			WmsConsignOrderItem detail=this.orderItemService.getWmsConsignOrderItemById(detailId);
			Map<String,Object> item=new HashMap<String,Object>();
			item.put("itemId", itemIdAry[i]);
			item.put("itemCode", itemAry[i]);
			item.put("planQty", detail.getItemQuantity());
			item.put("lackQty", num);
			item.put("reason", reasonAry[i]);
			itemList.add(item);
		}
		
		String xmlStr = XmlUtil.converterPayPalm(resultMap, XmlEnum.REQUEST);
		
		logger.debug("xmlStr:"+xmlStr);
		String  retStr  = qmSyncQueryService.submitQm(xmlStr, QmMethodInterface.METHOD_ITEMLACK,user.getOwnerCode());
		logger.debug("retStr:"+retStr);
		if(retStr.indexOf("success")!=-1){
			//转向至发送成功页面
		}
		return confirmDeliveryOrder(id, request, model);
	}
	

	
	/**
	 * 手动调用生成订单
	 * @param request
	 */
	@RequestMapping(value="qmHelp/cts")
	public void helpTradeByShipOrder(HttpServletRequest request){
		String id=request.getParameter("id");
//		this.qmSyncService.createTradeByShipOrder(Long.valueOf(id));
	}
	
}



