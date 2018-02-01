package com.graby.store.admin.waybill;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.admin.util.BaseResource;
import com.graby.store.base.ReceiveStateEnums;
import com.graby.store.base.StoreConstants;
import com.graby.store.entity.Centro;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeBatch;
import com.graby.store.entity.User;
import com.graby.store.entity.enums.StoreSystemItemEnums;
import com.graby.store.remote.AuthRemote;
import com.graby.store.remote.CentroRemote;
import com.graby.store.remote.ExpressPriceRemote;
import com.graby.store.remote.ShipOrderRemote;
import com.graby.store.remote.SystemItemRemote;
import com.graby.store.remote.TradeRemote;
import com.graby.store.remote.UserRemote;
import com.graby.store.remote.WayBillRemote;
import com.taobao.api.ApiException;
/**
 * 面单打印控制类
 * @author yangmin
 *
 */
@Controller
@RequestMapping(value="/")
public class AcccessTokenController {
	
	@Autowired
	private AuthRemote authRemote;
	
	@Autowired
	private ShipOrderRemote shipOrderRemote;
	
	@Autowired
	private UserRemote userRemote;
	
	@Autowired
	private TradeRemote tradeRemote;

	@Autowired
	private  WayBillRemote wayBillRemote;
	
	@Autowired
	private  SystemItemRemote systemItemRemote;
	@Autowired
	private CentroRemote centroRemote;
	
	public static String sessionKey="";

	
	
	
	
	/**
	 * 单个单据打印
	 * @param request
	 * @param model
	 * @param id
	 * @return string
	 */
	@RequestMapping(value="wayBill/printByOne/{id}")
	public String orderPrint(HttpServletRequest request,ModelMap model,@PathVariable int id){
		ShipOrder shipOrder =this.shipOrderRemote.getShipOrder(Long.valueOf(id));
		Long tradeId=shipOrder.getTradeId();
		if(tradeId!=0L){
			//调用打印方面的接口
		}else{
			//这里单据没有生成交易订单ID，不能继续执行
		}
		return null;
	}
	/**
	 * 批量电子面单打印
	 * @param request
	 * @param model
	 * @return string
	 */
	@RequestMapping(value="wayBill/printBatch")
	public String orderPrintBatch(HttpServletRequest request,ModelMap model){
		String ids=request.getParameter("ids");
		String[] ary=ids.split(",");
		List<Trade> tradeList=new ArrayList<Trade>();
		for(int i=0;i<ary.length;i++){
			ShipOrder shipOrder =this.shipOrderRemote.getShipOrder(Long.valueOf(ary[i]));
			Long tradeId=shipOrder.getTradeId();
			Trade trade=this.tradeRemote.getTrade(tradeId);
			tradeList.add(trade);
		}
		//调用接口
		return null;
	}
	
	/**
	 * 取消电子面单
	 */
	@RequestMapping(value = "wayBill/ajax/cancel")
	@ResponseBody
	public Map<String, Object> wayBillCancel(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception, IOException {
		String id = request.getParameter("id");
		System.err.println("id:"+id);
		List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
		Trade trade = tradeRemote.getTrade(new Long(id));
		retList.add(wayBillRemote.billCancel(trade));
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, String> map = retList.get(0);
		retMap.put("code", map.get("code"));
		retMap.put("flag", map.get("flag"));
		return retMap;
	}
	
	/**
	 * 批次整理
	 * */
	@RequestMapping(value="wayBill/tradeBatch/ajax")
	@ResponseBody
	public Map<String, Object> getTradeBatch(
			@RequestParam(value = "userId", defaultValue = "0") Long userId,
			@RequestParam(value = "sysId",defaultValue = "0") String cpCode,
			HttpServletRequest request,ModelMap model){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("cuid", BaseResource.getCurrentCentroId());
		params.put("userId", userId);
		params.put("expressCompany", cpCode);
		params.put("status", TradeBatch.TradeBatchStatusEnum.PRINTING);
		List<TradeBatch> tradeBatchs = this.wayBillRemote.getTradeBatch(params);
		Map<String, Object> retMap   = new HashMap<String, Object>();
		retMap.put("tradeBatchList", tradeBatchs);	
		return retMap;
	}

 	/**
 	 * easyui已获取单号订单列表
 	 * @param model
 	 * @return String
 	 * */
 	@RequestMapping(value="wayBill/waitsOk")
 	public String waitsOkList(ModelMap model){
 		List<User> users=this.userRemote.findUsers();
 		model.put("users", users);
 		String type="waybill";
 		List<SystemItem> itemList=this.systemItemRemote.findSystemItemByType(type);
 		model.put("itemList", itemList);
 		return "shipOrder/ShipOrderListWaitsOK";
 	}
	
 	/**
 	 * easyui已获取单号订单列表数据填充
 	 * @param page
 	 * @param rows
 	 * @param request
 	 * @return resultMap
 	 * */
 	@RequestMapping(value="wayBill/waitsOk/listData")
 	@ResponseBody
 	public Map<String, Object> waitsOkListData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
 		if (rows==10) {
 			rows=100;
 		}
 		Map<String, Object> params=new HashMap<String, Object>();
 		int centroId=BaseResource.getCurrentCentroId();
 		String userId=request.getParameter("userId");
 		String code=request.getParameter("code");
 		String beigainTime=request.getParameter("beigainTime");
 		String lastTime=request.getParameter("lastTime");
 		String q=request.getParameter("q");
 		//获取菜鸟单号必须字段
 		params.put("type", StoreSystemItemEnums.SESSIONKEY.getKey());
		List<SystemItem> itemList=this.authRemote.getSystemItemList(params);
		if(itemList==null || itemList.size()==0){
			//跳转到登陆淘宝授权帐号，回转页面URL就是此方法URL
			return null;
		}
 		SystemItem item=itemList.get(0);
		sessionKey=item.getValue();
		StoreConstants.rookie_sessionKey=sessionKey;
		params.clear();
 		
 		params.put("centroId", centroId);
 		params.put("cpCode",code);
 		params.put("q",q);
 		params.put("userId",userId);
 		params.put("beigainTime", beigainTime);
 		params.put("lastTime", lastTime);
 		List<ShipOrder> orders=this.shipOrderRemote.findShipOrderWaitsOk(page,rows,params);
 		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
 		for(ShipOrder order:orders){
 			Map<String,Object> orderMap=new HashMap<String,Object>();
 			orderMap.put("id",order.getId());
 			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 			orderMap.put("createDate",format.format(order.getCreateDate()));
 			orderMap.put("code",order.getTradeBatch());
 			User user=this.userRemote.getUser(order.getCreateUser().getId());
 			orderMap.put("userName",user.getShopName());
 			orderMap.put("tradeId",order.getTradeId());	
 			orderMap.put("printDate",format.format(order.getLastUpdateDate()));
 			orderMap.put("items",order.getItems());
 			orderMap.put("receiverName",order.getReceiverName());
 			orderMap.put("receiverPhone",order.getReceiverMobile()+","+order.getReceiverPhone());
 			orderMap.put("address",order.getReceiverState()+order.getReceiverCity()+order.getReceiverDistrict()+order.getReceiverAddress());
 			orderMap.put("expressName",order.getExpressCompany());
 			orderMap.put("expressOrderNo",order.getExpressOrderno());
 			orderMap.put("printBatch",order.getPrintBatch());
 			resultList.add(orderMap);
 		}
// 		long total=this.shipOrderRemote.getTotalResults(params);
 		Map<String, Object> resultMap=new HashMap<String, Object>();
// 		resultMap.put("total",total);
 		resultMap.put("page",page);
 		resultMap.put("rows",resultList);
 		return resultMap;
 	}
	
 	/**
 	 * easyui待打印订单列表
 	 * @param model
 	 * @return String
 	 * */
 	@RequestMapping(value="wayBill/waits")
 	public String waitsList(ModelMap model,HttpServletRequest request){
 		List<User> users=this.userRemote.findUsers();
 		model.put("users", users);
 		String type="waybill";
 		List<SystemItem> itemList=this.systemItemRemote.findSystemItemByType(type);
 		model.put("itemList", itemList);
 		model.put("stateList", ReceiveStateEnums.values());
 		return "shipOrder/ShipOrderListWaits";
 	}
	
 	/**
 	 * 批次整理
 	 * @param request
 	 * @return resultMap
 	 * @exception JSONException
 	 * */
 	@RequestMapping(value="wayBill/sort")
 	@ResponseBody
 	public Map<String, Object> sort(HttpServletRequest request) throws JSONException{
 		Map<String,Object> params=new HashMap<String, Object>();
 		String json=request.getParameter("json");
 		JSONObject object=new JSONObject(json);
 		if (!object.getString("sysId").isEmpty()&&object.getString("sysId")!="0") {
 	 		String cpCode=object.getString("sysId");
 	 		params.put("cpCode", cpCode);
 			params.put("expressCompany", cpCode);
		}else {
			String cpCode="YUNDA";
 	 		params.put("cpCode", cpCode);
 			params.put("expressCompany", cpCode);
		} 		
 		int  cuid =  BaseResource.getCurrentCentroId();
 		params.put("centroId",cuid);
		params.put("cuid",cuid);
		params.put("userId",object.getString("userId"));
		params.put("q",object.getString("q"));		
		params.put("lastTime",object.getString("lastTime"));		
		params.put("beigainTime",object.getString("beigainTime"));
 		Map<String,Object> resultMap = new HashMap<String, Object>();
 		Map<String, Object> retMap = wayBillRemote.createTradeTatch(params);
		if(retMap!=null){
			resultMap.put("msg", retMap.get("msg"));
			JOptionPane.showMessageDialog(null,retMap.get("msg"),"提示",JOptionPane. PLAIN_MESSAGE);
 		}
 		return resultMap;
 	}
 	
 	/**
 	 * easyui待打印订单列表数据填充
 	 * @param page
 	 * @param rows
 	 * @param request
 	 * @return resultMap
 	 * */
 	@RequestMapping(value="wayBill/waits/listData")
 	@ResponseBody
 	public Map<String, Object> waitsListData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
 		if (rows==10) {
 			rows=100;
 		}
 		Map<String, Object> params=new HashMap<String, Object>();
 		String userId=request.getParameter("userId");
 		String cpCode=request.getParameter("sysId");
 		String beigainTime=request.getParameter("beigainTime");
 		String lastTime=request.getParameter("lastTime");
 		String q=request.getParameter("q");
 		String txtno=request.getParameter("txtno");
 		String itemSelect=request.getParameter("item");
 		String tradeBatchId=request.getParameter("tradeBatchId");
 		String receiveState=request.getParameter("receiveState");
 		if(receiveState!=null && receiveState.equals("0")){
 			receiveState="";
 		}
 		params.put("type", StoreSystemItemEnums.SESSIONKEY.getKey());
		List<SystemItem> itemList=this.authRemote.getSystemItemList(params);
		if(itemList==null || itemList.size()==0){
			//跳转到登陆淘宝授权帐号，回转页面URL就是此方法URL
			return null;
		}
 		SystemItem item=itemList.get(0);
		sessionKey=item.getValue();
		StoreConstants.rookie_sessionKey=sessionKey;
		params.clear();
		
 		if (cpCode!=null&&!cpCode.equals("0")) {
 	 		params.put("cpCode",cpCode);
		}	
 		int  cuid =  BaseResource.getCurrentCentroId();
 		params.put("centroId",cuid);
 		params.put("tradeBatchId", tradeBatchId);
 		params.put("q",q);
 		params.put("userId",userId);
 		params.put("beigainTime", beigainTime);
 		params.put("lastTime", lastTime);
 		params.put("receiveState", receiveState);
 		params.put("txtno", txtno);
 		System.err.println("params:"+params);
 		List<ShipOrder> orders=this.shipOrderRemote.findSendOrderWaitsByPages(page,rows,params);
 		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
 		for(ShipOrder order:orders){
 			Map<String,Object> orderMap=new HashMap<String,Object>();
 			orderMap.put("id",order.getId());
 			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 			orderMap.put("createDate",format.format(order.getCreateDate()));
 			orderMap.put("code",order.getTradeBatch());
 			User user=this.userRemote.getUser(order.getCreateUser().getId());
 			orderMap.put("userName",user.getShopName());
 			orderMap.put("tradeId",order.getTradeId());
 			orderMap.put("tradeBatchId",order.getTradeBatchId());
 			orderMap.put("items",order.getItems());
 			orderMap.put("receiverName",order.getReceiverName());
 			orderMap.put("receiverPhone",((order.getReceiverMobile()==null)?"":order.getReceiverMobile())+","+((order.getReceiverPhone()==null)?"":order.getReceiverPhone()));
 			orderMap.put("address",order.getReceiverState()+order.getReceiverCity()+order.getReceiverDistrict()+order.getReceiverAddress());
 			orderMap.put("expressName",order.getExpressCompany()); 								
 			orderMap.put("sellerMessage",((order.getSellerMemo()==null)?"":order.getSellerMemo())+((order.getSellerMessage()==null)?"":order.getSellerMessage())); 								
 			orderMap.put("buyerMessage",((order.getBuyerMemo()==null)?"":order.getBuyerMemo())+((order.getBuyerMessage()==null)?"":order.getBuyerMessage())); 								
 			DecimalFormat df=new DecimalFormat("######0.00"); 
			orderMap.put("weight",(order.getTotalWeight()!=null)?df.format(order.getTotalWeight()):0.00);
 			resultList.add(orderMap);
 			//System.err.println(orderMap);
 		}
 		long total = this.shipOrderRemote.findTradeBatchSendOrderWaitsCount(params);
 		Map<String, Object> resultMap=new HashMap<String, Object>();
 		resultMap.put("total",total);
 		resultMap.put("page",page);
 		resultMap.put("rows",resultList);
 		return resultMap;
 	}
 	
}
