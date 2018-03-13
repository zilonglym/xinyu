package com.graby.store.service.waybill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.base.StoreConstants;
import com.graby.store.entity.Centro;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.Trade;
import com.graby.store.entity.User;
import com.graby.store.entity.enums.OrderStatusEnums;
import com.graby.store.entity.enums.StoreSystemItemEnums;
import com.graby.store.service.base.CentroService;
import com.graby.store.service.base.UserService;
import com.graby.store.service.trade.TradeService;
import com.graby.store.service.wms.ShipOrderService;
import com.graby.store.service.wms.SystemItemService;
import com.graby.store.util.AddressUtil;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.LogisticsService;
import com.taobao.api.domain.PackageItem;
import com.taobao.api.domain.TradeOrderInfo;
import com.taobao.api.domain.WaybillAddress;
import com.taobao.api.domain.WaybillApplyCancelRequest;
import com.taobao.api.domain.WaybillApplyNewInfo;
import com.taobao.api.domain.WaybillApplyNewRequest;
import com.taobao.api.domain.WaybillDetailQueryRequest;
import com.taobao.api.request.CainiaoWaybillIiGetRequest;
import com.taobao.api.request.CainiaoWaybillIiGetRequest.AddressDto;
import com.taobao.api.request.CainiaoWaybillIiGetRequest.OrderInfoDto;
import com.taobao.api.request.CainiaoWaybillIiGetRequest.PackageInfoDto;
import com.taobao.api.request.CainiaoWaybillIiGetRequest.TradeOrderInfoDto;
import com.taobao.api.request.CainiaoWaybillIiGetRequest.UserInfoDto;
import com.taobao.api.request.CainiaoWaybillIiGetRequest.WaybillCloudPrintApplyNewRequest;
import com.taobao.api.request.CainiaoWaybillIiSearchRequest;
import com.taobao.api.request.WlbWaybillICancelRequest;
import com.taobao.api.request.WlbWaybillIGetRequest;
import com.taobao.api.request.WlbWaybillIQuerydetailRequest;
import com.taobao.api.response.CainiaoWaybillIiGetResponse;
import com.taobao.api.response.CainiaoWaybillIiSearchResponse;
import com.taobao.api.response.WlbWaybillICancelResponse;
import com.taobao.api.response.WlbWaybillIGetResponse;
import com.taobao.api.response.WlbWaybillIQuerydetailResponse;




@Component
public class WayBillService {
	public static final Logger logger = Logger.getLogger(WayBillService.class);
	
	private String STO_ADDRESS="湘潭市岳塘区书院路街道新塘村申通快递总部";
	
	private String YUNDA_411353="双马镇金钢人防";
	
	private String HTKY5="双马镇金钢结构";	
	
	private String HTKY11="双马镇金钢人防";
	
	@Autowired
	private SystemItemService systemItemService;	
	@Autowired
	private ShipOrderService shipOrderService;	
	@Autowired
	private TradeService tradeService;	
	@Autowired
	private UserService  userService  ;	
	@Autowired
	private CentroService  centroService  ;	
	
	private Map<String, Object> templateMap = null;

	/**
	 * taobao.wlb.waybill.i.get 获取物流服务商电子面单号v1.0
	 * @param url
	 */
	public Map<String,String> billGet(Trade trade,String cpCode,String batchCode){		
		
		Map<String,String>  retMap  = new HashMap<String, String>();
		TaobaoClient client = new DefaultTaobaoClient(StoreConstants.rookie_url, StoreConstants.rookie_appkey, StoreConstants.rookie_secret);
		WlbWaybillIGetRequest req = new WlbWaybillIGetRequest();

		WaybillApplyNewRequest waybillApplyNewRequest = new WaybillApplyNewRequest();
		
		Centro centro = centroService.findCentro(Long.valueOf(""+trade.getCentro().getId()));
		User user = userService.getUser(trade.getUser().getId());
		String  nameMsg   =  trade.getId()  +";";  //订单错误提示信息前缀
		// 面单详细信息
		/**
		 * 订单地址。。 发货地址
		 */
		ShipOrder shipOrder=this.shipOrderService.getSendShipOrderByTradeId(trade.getId());
		WaybillAddress address = new WaybillAddress();
		address.setArea(centro.getArea());
		address.setProvince(centro.getProvince());
		if(cpCode!=null && cpCode.equals("STO")){
			address.setAddressDetail(STO_ADDRESS);
		}else if(cpCode!=null && cpCode.equals("YUNDA411353")){
			address.setAddressDetail(YUNDA_411353);
			cpCode="YUNDA";
		}else if(cpCode!=null && cpCode.equals("HTKY")){
			address.setAddressDetail(YUNDA_411353);
			cpCode="HTKY";
		}else if(cpCode!=null && cpCode.equals("ZTO")){
			address.setAddressDetail(YUNDA_411353);
		}else{
			address.setAddressDetail(centro.getAddress());
		}
		
		waybillApplyNewRequest.setCpCode(cpCode);
		address.setCity(centro.getCity());
		
		waybillApplyNewRequest.setShippingAddress(address);

		List<TradeOrderInfo> tradeOrderInfoList = new ArrayList<TradeOrderInfo>();

		TradeOrderInfo tradeItem = new TradeOrderInfo();
		if(shipOrder.getItems()!=null && shipOrder.getItems().length()>50){
			tradeItem.setItemName(shipOrder.getItems().substring(0, 50));
		}else{
			tradeItem.setItemName(shipOrder.getItems());
		}
		nameMsg =nameMsg  + trade.getReceiverName();
		tradeItem.setConsigneeName(AddressUtil.cainiaoFormat(trade.getReceiverName()));// 收件人
		if(trade.getTradeFrom()!=null && trade.getTradeFrom()!=null && trade.getTradeFrom().equals(Trade.SourceFlag.SourceFlag_QM)){
			if(shipOrder.getSourcePlatformCode()!=null && shipOrder.getSourcePlatformCode().equals("TB")){
				tradeItem.setOrderChannelsType("TB");
			}else{
				tradeItem.setOrderChannelsType("OTHERS");
			}
		}else{
			tradeItem.setOrderChannelsType("TB");
		}
		List<String> tradeList = new ArrayList<String>();
		tradeList.add(String.valueOf(trade.getOtherOrderNo()));
		tradeItem.setTradeOrderList(tradeList);

		/**
		 * 如果手机号码为空，则传电话号码
		 */
		if(StringUtils.isNotEmpty(trade.getReceiverMobile())){
			nameMsg=  nameMsg  +":"+trade.getReceiverMobile()  ;
			tradeItem.setConsigneePhone(trade.getReceiverMobile());
		}else{
			nameMsg=  nameMsg  +":"+ trade.getReceiverPhone()  ;
			tradeItem.setConsigneePhone(trade.getReceiverPhone());
		}

		// 收货信息
		WaybillAddress address1 = new WaybillAddress();
		address1.setArea(trade.getReceiverDistrict());
		address1.setProvince(trade.getReceiverState()); // 省份
		// address.setTown(""); //城镇
		address1.setAddressDetail(AddressUtil.cainiaoFormat(trade.getReceiverAddress().replaceAll("/", "").replaceAll("\"", "").replaceAll("\\\\","").replaceAll("\'", "")));
		address1.setCity(trade.getReceiverCity());
		tradeItem.setConsigneeAddress(address1);
		

		tradeItem.setSendPhone(centro.getPhone());
		tradeItem.setWeight(100L);
		tradeItem.setSendName(user.getShopName());

		List<PackageItem> packageList = new ArrayList<PackageItem>();
		PackageItem packageItem = new PackageItem();
		if(shipOrder.getItems()!=null && shipOrder.getItems().length()>50){
			packageItem.setItemName(shipOrder.getItems().substring(0,50));
		}else{
			packageItem.setItemName(shipOrder.getItems());
		}
		packageItem.setCount(shipOrder.getTotalnum()==0?1L:Long.valueOf(shipOrder.getTotalnum()));
		packageList.add(packageItem);
		tradeItem.setPackageItems(packageList);

		List<LogisticsService> logisticsList = new ArrayList<LogisticsService>();
		LogisticsService logisticsItem = new LogisticsService();

//		logisticsItem.setServiceValue4Json( "{\"value\": \"NORMAL\"}");
		logisticsItem.setServiceCode("SVC-DELIVERY-ENV");
		logisticsList.add(logisticsItem);
		tradeItem.setLogisticsServiceList(logisticsList);

		// 物流模式枚举
		tradeItem.setProductType("STANDARD_EXPRESS");
		
		tradeItem.setRealUserId(user.getTbUserId());
		
		tradeItem.setVolume(1L);
		tradeItem.setWeight(10L);
		//packageId
		tradeItem.setPackageId(String.valueOf(trade.getId()));
		tradeOrderInfoList.add(tradeItem);

		waybillApplyNewRequest.setTradeOrderInfoCols(tradeOrderInfoList);

		System.err.println("billGet:"+waybillApplyNewRequest.toString());
		req.setWaybillApplyNewRequest(waybillApplyNewRequest);
		
	//	req.setWaybillApplyNewRequest("{'cp_code':'YTO','shipping_address':{'address_detail':'湖南省湘潭市岳塘区双拥南路25号高新创业园A3栋3楼星宇物流客服部','area':'岳塘区','city':'湘潭市','province':'湖南省'},'trade_order_info_cols':[{'consignee_address':{'address_detail':'重庆市九龙坡区石桥铺正街254号','area':'九龙坡区','city':'重庆市','province':'重庆'},'consignee_name':'谢黎','consignee_phone':'18716330842','logistics_service_list':[{'service_code':'SVC-DELIVERY-ENV','service_value4_json':'{\'value\': \'NORMAL\'}'}],'order_channels_type':'TB','package_id':'01 -1','package_items':[{'count':1,'item_name':'itemName0'}],'product_type':'STANDARD_EXPRESS','real_user_id':2599053189,'send_name':'xinyun乐器旗舰店','send_phone':'13500000000','trade_order_list':['1195085016717298'],'volume':1,'weight':10}]}");
		logger.debug(req.getTextParams());
		try {
			initSessionKey();
			
			WlbWaybillIGetResponse response = client.execute(req , StoreConstants.rookie_sessionKey);
			System.err.println("sessionKey:"+StoreConstants.rookie_sessionKey);
			retMap.put("message", "taobao.wlb.waybill.i.get 接口调用成功!");
			retMap.put("tradeId", ""+trade.getId());
			logger.debug("菜鸟面单返回:"+response.getBody());
			List<WaybillApplyNewInfo> waybillApplyNewCols = response.getWaybillApplyNewCols();
			if (waybillApplyNewCols != null  &&waybillApplyNewCols.size()>0){
					WaybillApplyNewInfo waybillApplyNewInfo = waybillApplyNewCols.get(0);
					try{
						
					//更新出库单状态
						ShipOrder sendShipOrder = shipOrderService.getSendShipOrderByTradeId(trade.getId());
						List<Map<String,String>> paramMapList =  new  ArrayList<Map<String,String>>();
						Map<String,String>  paramMap =  new HashMap<String, String>();
						if(cpCode!=null &&  cpCode.trim().equals("YUNDA411353")){
							paramMap.put("expressCompany", "YUNDA");
						}else{
							paramMap.put("expressCompany", cpCode);
						}
						paramMap.put("id", ""+sendShipOrder.getId());
						
						paramMap.put("expressOrderno", waybillApplyNewInfo.getWaybillCode());
						
						paramMap.put("status", ShipOrder.EntryOrderStatus.WAIT_EXPRESS_PICKING);
						
						//paramMap.put("status", ShipOrder.SendOrderStatus.WAIT_BUYER_RECEIVED);
						paramMap.put("orderFlag", waybillApplyNewInfo.getShortAddress());  //  大头
						paramMap.put("sellerMobile", waybillApplyNewInfo.getPackageCenterName()); //收货网点名称
						paramMap.put("sellerPhone", waybillApplyNewInfo.getPackageCenterCode());  //收货网点CODE
						paramMap.put("batchCode", batchCode);//打印批次号
						paramMapList.add(paramMap);
						shipOrderService.setSendOrderExpressAndStatusById(paramMapList);
						//更新订单状态
					//	tradeService.updateTradeStatus(trade.getId(), Trade.Status.TRADE_WAIT_EXPRESS_NOFITY);
						retMap.put("flag", "成功");
						retMap.put("orderNo", waybillApplyNewInfo.getWaybillCode());
						retMap.put("code", "200");
					}catch(Exception e){
						e.printStackTrace();
						retMap.put("flag", "接口调用成功,数据反写系统失败");
						retMap.put("code", "501");
						retMap.put("errorInfo", nameMsg  +";msg:"+"数据反写系统失败;"+e.getMessage());
					}
					
			}else{
				retMap.put("flag", "接口调用成功,返回数据为空");
				retMap.put("code", "502");
				retMap.put("errorInfo", nameMsg  +";msg:"+response.getBody());
			}
			
		} catch (Exception e) {
			retMap.put("flag", "接口调用失败");
			retMap.put("message", "taobao.wlb.waybill.i.get 接口调用失败!");
			retMap.put("code", "500");
			retMap.put("errorInfo",nameMsg  +";msg:"+"接口异常:"+e.getMessage());
			
			
		}
		return retMap;
	}
	
	

	/**
	 * taobao.wlb.waybill.i.cancel 商家取消获取的电子面单号v1.0
	 * @param url
	 */
	public Map<String,String> billCancel(Trade trade){
		
		TaobaoClient client = new DefaultTaobaoClient(StoreConstants.rookie_url, StoreConstants.rookie_appkey, StoreConstants.rookie_secret);
		
		Map<String,String>  retMap  = new HashMap<String, String>();
		ShipOrder sendShipOrder = shipOrderService.getSendShipOrderByTradeId(trade.getId());
		User user = userService.getUser(trade.getUser().getId());
		//{"cp_code": "POSTB",
		//"real_user_id": 89346737,
		//"trade_order_list": ["order"],
		//"waybill_code": "yourCode"}
		WlbWaybillICancelRequest req=new WlbWaybillICancelRequest();
		WaybillApplyCancelRequest cancel = new WaybillApplyCancelRequest();
		
		cancel.setCpCode(sendShipOrder.getExpressCompany());
		cancel.setRealUserId(user.getTbUserId());
		
		ShipOrder shipOrder=this.shipOrderService.getSendShipOrderByTradeId(trade.getId());
		List<String> orderList = new ArrayList<String>();
		
		if(shipOrder!=null){
			List<ShipOrderDetail> detailList=this.shipOrderService.getShipOrderDetailByOrderId(shipOrder.getId());
			ShipOrderDetail detail=detailList.get(0);
			orderList.add( detail.getTradeOrderNo()==null ? trade.getLgAgingType():""+detail.getTradeOrderNo());
		}else{
			orderList.add( trade.getTradeFrom()==null ? trade.getLgAgingType():""+trade.getTradeFrom());
		}
		
		
	//	orderList.add( trade.getTradeFrom()==null ? trade.getLgAgingType():""+trade.getTradeFrom());
		cancel.setTradeOrderList(orderList);
		cancel.setPackageId(String.valueOf(trade.getId()));
		cancel.setWaybillCode(sendShipOrder.getExpressOrderno());
		System.err.println("WaybillCode:"+sendShipOrder.getExpressOrderno());
		System.err.println("real_user_id:"+user.getTbUserId());
		System.err.println("cp_code:"+sendShipOrder.getExpressCompany());
		req.setWaybillApplyCancelRequest(cancel);
		System.err.println("订单取消:"+req.getTextParams());
		try {
			initSessionKey();
			WlbWaybillICancelResponse response = client.execute(req , StoreConstants.rookie_sessionKey);
			retMap.put("message", "taobao.wlb.waybill.i.cancel 接口调用成功!");
			retMap.put("tradeId", ""+trade.getId());
			System.out.println("response:"+response.getBody());
			if(response.isSuccess()){
				try {
					//更新还原状态
					List<Map<String,String>> paramMapList =  new  ArrayList<Map<String,String>>();
					Map<String,String>  paramMap =  new HashMap<String, String>();
					paramMap.put("id", ""+sendShipOrder.getId());
					paramMap.put("expressCompany", "");
					paramMap.put("expressOrderno", "");
					paramMap.put("status", ShipOrder.EntryOrderStatus.ENTRY_WAIT_STORAGE_RECEIVED);
					paramMap.put("orderFlag", "");  //  大头
					paramMap.put("sellerMobile", ""); //收货网点名称
					paramMap.put("sellerPhone", "");  //收货网点CODE
					paramMapList.add(paramMap);
					shipOrderService.setSendOrderExpressAndStatusById(paramMapList);
					//更新订单状态
					tradeService.updateTradeStatus(trade.getId(), Trade.Status.TRADE_WAIT_EXPRESS_SHIP);
					retMap.put("flag", "成功");
					retMap.put("code", "200");
				} catch (Exception e) {
					retMap.put("flag", "接口调用成功,反写单据状态失败");
					retMap.put("code", "501");
					e.printStackTrace();
				}
			}else{
				retMap.put("flag", "接口调用成功,无效单据或取消失败");
				retMap.put("code", "502");
				retMap.put("error", response.getBody());
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("flag", "接口调用失败");
			retMap.put("message", "taobao.wlb.waybill.i.cancel 接口调用失败!");
			retMap.put("code", "500");
			e.printStackTrace();
		}
		return retMap;
	 }
	
	/**
	 * taobao.wlb.waybill.i.querydetail (查面单号状态v1.0)
	 * @param code 订单号
	 * @return 此订单号状态
	 */
	public Map<String,Object> querydetail(String code){
		TaobaoClient client = new DefaultTaobaoClient(StoreConstants.rookie_url, StoreConstants.rookie_appkey, StoreConstants.rookie_secret);
		WlbWaybillIQuerydetailRequest req = new WlbWaybillIQuerydetailRequest();
		WaybillDetailQueryRequest obj1 = new WaybillDetailQueryRequest();
		
		List<String> codeList=new ArrayList<String>();
		codeList.add(code);
		obj1.setWaybillCodes(codeList);
		req.setWaybillDetailQueryRequest(obj1);
		WlbWaybillIQuerydetailResponse rsp;
		System.out.println(req.getTextParams());
		initSessionKey();
		System.err.println("sessionKey"+StoreConstants.rookie_sessionKey);
		System.out.println("sessionKey"+StoreConstants.rookie_sessionKey);
		try {
			rsp = client.execute(req, StoreConstants.rookie_sessionKey);
			System.out.println(rsp.getBody());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 取菜鸟的通用标准模板
	 * @throws Exception 
	 */
	public String getCainiaoTemplates() throws Exception{		
		
		initSessionKey();
		/**
		 * 获取所有的菜鸟标准电子面单模板
		 */
//		TaobaoClient client = new DefaultTaobaoClient(StoreConstants.cainiao_url, StoreConstants.rookie_appkey, StoreConstants.rookie_secret);
//		CainiaoCloudprintStdtemplatesGetRequest req = new CainiaoCloudprintStdtemplatesGetRequest();
//		
//		CainiaoCloudprintStdtemplatesGetResponse rsp = client.execute(req, StoreConstants.rookie_sessionKey);
//		String result=rsp.getBody();
		/**
		 * 获取商家的自定义区模板信息
		 */
//		TaobaoClient client = new DefaultTaobaoClient(StoreConstants.cainiao_url, StoreConstants.rookie_appkey, StoreConstants.rookie_secret);
//		CainiaoCloudprintCustomaresGetRequest req = new CainiaoCloudprintCustomaresGetRequest();
//		req.setTemplateId(999L);
//		CainiaoCloudprintCustomaresGetResponse rsp = client.execute(req, StoreConstants.rookie_sessionKey);
//		System.out.println(rsp.getBody());
//		
		
		
		/**
		 * 获取用户使用的菜鸟电子面单模板信息)
		 */
//		TaobaoClient client = new DefaultTaobaoClient(StoreConstants.cainiao_url, StoreConstants.rookie_appkey, StoreConstants.rookie_secret);
//		CainiaoCloudprintMystdtemplatesGetRequest req = new CainiaoCloudprintMystdtemplatesGetRequest();
//		CainiaoCloudprintMystdtemplatesGetResponse rsp = client.execute(req, StoreConstants.rookie_sessionKey);
//		System.out.println(rsp.getBody());
		
		/**
		 * 用户使用了哪些自定义区模板
		 */
		TaobaoClient client = new DefaultTaobaoClient(StoreConstants.cainiao_url, StoreConstants.rookie_appkey, StoreConstants.rookie_secret);
//		CainiaoCloudprintIsvtemplatesGetRequest req = new CainiaoCloudprintIsvtemplatesGetRequest();
//		CainiaoCloudprintIsvtemplatesGetResponse rsp = client.execute(req, StoreConstants.rookie_sessionKey);
//		System.out.println(rsp.getBody());
		
		/**
		 * 用户自定义资源
		 */
//		TaobaoClient client = new DefaultTaobaoClient(StoreConstants.cainiao_url, StoreConstants.rookie_appkey, StoreConstants.rookie_secret);
//		CainiaoCloudprintIsvResourcesGetRequest req = new CainiaoCloudprintIsvResourcesGetRequest();
//		req.setIsvResourceType("CUSTOM_AREA");
//		CainiaoCloudprintIsvResourcesGetResponse rsp = client.execute(req);
//		System.out.println(rsp.getBody());
		
		
		
		
//		String result=rsp.getBody();
//		return result;
		return null;
	}
	
	/**
	 * 取菜鸟电子面单号
	 * @param ids
	 * @param cpCode
	 * @param batchCode
	 * @param url 模板的URL
	 * @return
	 */
	@Transactional
	public Map<String,Object> getCainiaoBill(String ids,String cpCode,String url,String batchCode){
		if (this.templateMap == null) {
			 this.refreshTemplate();
		}
		String idArry="";
		String[] ary=ids.split(",");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		StringBuffer buffer=new StringBuffer();
		for(int i=0;i<ary.length;i++){
			String cp=cpCode;
			ShipOrder shipOrder=this.shipOrderService.getShipOrder(Long.valueOf(ary[i]));
			Trade trade=this.tradeService.getTrade(shipOrder.getTradeId());
			User user=this.userService.getUser(shipOrder.getCreateUser().getId());
			TaobaoClient client = new DefaultTaobaoClient(StoreConstants.cainiao_url, StoreConstants.rookie_appkey, StoreConstants.rookie_secret);
			CainiaoWaybillIiGetRequest req = new CainiaoWaybillIiGetRequest();
			WaybillCloudPrintApplyNewRequest params = new WaybillCloudPrintApplyNewRequest();
			UserInfoDto userInfo = new UserInfoDto();//发货人信息
			AddressDto address = new AddressDto();//发货地址
			Centro centro = centroService.findCentro(Long.valueOf(""+trade.getCentro().getId()));
			String  nameMsg   =  trade.getId()  +";";  //订单错误提示信息前缀
			if(cpCode!=null && cpCode.equals("STO")){
				address.setDetail(STO_ADDRESS);
			}else if(cpCode!=null && cpCode.equals("YUNDA411353")){
				address.setDetail(YUNDA_411353);
				cp="YUNDA";
			}else if(cpCode!=null && cpCode.equals("HTKY")){
				address.setDetail(YUNDA_411353);
				cp="HTKY";
			}else if(cpCode!=null && cpCode.equals("HTKY5")){
				address.setDetail(HTKY5);
				cp="HTKY";
			}else if(cpCode!=null && cpCode.equals("HTKY11")){
				address.setDetail(HTKY11);
				cp="HTKY";
			}else if(cpCode!=null && cpCode.equals("ZTO")){
				address.setDetail(YUNDA_411353);
			}else{
				address.setDetail(centro.getAddress());
			}
			params.setCpCode(cp);
			address.setCity(centro.getCity());
			if(cpCode!=null && cpCode.equals("HTKY5")){
				address.setDistrict("雨湖区");
			}else{
				address.setDistrict(centro.getArea());
			}
			address.setProvince(centro.getProvince());
//			address.setTown("i");
			userInfo.setAddress(address);
			userInfo.setMobile(centro.getPhone());
			userInfo.setName("马亮");
			userInfo.setPhone(centro.getPhone());
			params.setSender(userInfo);
			
			
			List<TradeOrderInfoDto> tradeInfoList = new ArrayList<TradeOrderInfoDto>();//请求面单信息
			TradeOrderInfoDto tradeOrderInfo = new TradeOrderInfoDto();
			tradeInfoList.add(tradeOrderInfo);
//			tradeOrderInfo.setLogisticsServices("{\"SVC-COD\": {\"value\":  \"NORMAL\"} }");
			tradeOrderInfo.setObjectId(trade.getOtherOrderNo().toString());
			OrderInfoDto tradeInfo = new OrderInfoDto();//订单信息
			
			
			if(trade.getTradeFrom()!=null && trade.getTradeFrom()!=null && trade.getTradeFrom().equals(Trade.SourceFlag.SourceFlag_QM)){
				if(shipOrder.getSourcePlatformCode()!=null && shipOrder.getSourcePlatformCode().equals("TB")){
					
					tradeInfo.setOrderChannelsType("TB");
				}else{
					tradeInfo.setOrderChannelsType("OTHERS");
				}
			}else{
				tradeInfo.setOrderChannelsType("TB");
			}
			
			List<String> tradeList = new ArrayList<String>();
			tradeList.add(String.valueOf(trade.getOtherOrderNo()));
			tradeInfo.setTradeOrderList(tradeList);
			tradeOrderInfo.setOrderInfo(tradeInfo);
			
			
			PackageInfoDto packageInfo = new PackageInfoDto();//包裹信息
			packageInfo.setId(trade.getOtherOrderNo().toString());
			List<com.taobao.api.request.CainiaoWaybillIiGetRequest.Item> itemList = new ArrayList<com.taobao.api.request.CainiaoWaybillIiGetRequest.Item>();//包裹高品信息,不能超过10
			com.taobao.api.request.CainiaoWaybillIiGetRequest.Item item=new com.taobao.api.request.CainiaoWaybillIiGetRequest.Item();
			
			if(shipOrder.getItems()!=null && shipOrder.getItems().length()>50){
				item.setName(shipOrder.getItems().substring(0,50));
			}else{
				item.setName(shipOrder.getItems());
			}
			item.setCount(shipOrder.getTotalnum()==0?1L:Long.valueOf(shipOrder.getTotalnum()));
			itemList.add(item);
			packageInfo.setItems(itemList);
			tradeOrderInfo.setPackageInfo(packageInfo);
			
			UserInfoDto receiveUser = new UserInfoDto(); //收件人信息
			AddressDto receiveAddress = new AddressDto();//收件人地址信息
			receiveAddress.setCity(trade.getReceiverCity());
			receiveAddress.setDetail(AddressUtil.cainiaoFormat(trade.getReceiverAddress()));
			receiveAddress.setDistrict(trade.getReceiverDistrict());
			receiveAddress.setProvince(trade.getReceiverState());
			receiveUser.setAddress(receiveAddress);
			/**
			 * 如果手机号码为空，则传电话号码
			 */
			if(StringUtils.isNotEmpty(trade.getReceiverMobile())){
				nameMsg=  nameMsg  +":"+trade.getReceiverMobile()  ;
				receiveUser.setMobile(trade.getReceiverMobile());
			}else{
				nameMsg=  nameMsg  +":"+ trade.getReceiverPhone()  ;
				receiveUser.setMobile(trade.getReceiverPhone());
			}
			receiveUser.setName((AddressUtil.cainiaoFormat(trade.getReceiverName())));
			receiveUser.setPhone(trade.getReceiverPhone());
			tradeOrderInfo.setRecipient(receiveUser);
			
			if(StringUtils.isNotBlank(url)){
				tradeOrderInfo.setTemplateUrl(url);
			}
			tradeOrderInfo.setUserId(user.getTbUserId());
			params.setTradeOrderInfoDtos(tradeInfoList);
//			params.setStoreCode("553323");//对接落地配业务
//			params.setResourceCode("DISTRIBUTOR_978324");
//			params.setDmsSorting(true);
			req.setParamWaybillCloudPrintApplyNewRequest(params);
			logger.debug("菜鸟云参数:"+req.getTextParams());
			try {
				initSessionKey();				
				CainiaoWaybillIiGetResponse response = client.execute(req, StoreConstants.rookie_sessionKey);
				System.err.println("sessionKey:"+StoreConstants.rookie_sessionKey);
				resultMap.put("message", "cainiao.waybill.ii.get  接口调用成功!");
				String responseText=response.getBody();
				resultMap.put("tradeId", ""+trade.getId());
//				logger.debug("菜鸟云 面单返回:"+responseText);
				if (response.isSuccess()){
					logger.debug("菜鸟云 面单成功返回"+responseText);
						try{
							JSONObject object=new JSONObject(responseText);
							JSONObject responseObj=object.getJSONObject("cainiao_waybill_ii_get_response");
							JSONObject modulesObj=responseObj.getJSONObject("modules");
							JSONArray cloudAry=modulesObj.getJSONArray("waybill_cloud_print_response");
							JSONObject rootObj=cloudAry.getJSONObject(0);
							String dataStr=rootObj.getString("print_data");
							
							JSONObject printData=new JSONObject(dataStr);
							JSONObject dataObj=printData.getJSONObject("data");
							JSONObject routingInfo=dataObj.getJSONObject("routingInfo");
							JSONObject consolidation=routingInfo.getJSONObject("consolidation");
							JSONObject sortation=routingInfo.getJSONObject("sortation");
							
						//更新出库单状态
							ShipOrder sendShipOrder = shipOrderService.getSendShipOrderByTradeId(trade.getId());
							List<Map<String,String>> paramMapList =  new  ArrayList<Map<String,String>>();
							Map<String,String>  paramMap =  new HashMap<String, String>();
							paramMap.put("id", ""+sendShipOrder.getId());
							if(cpCode!=null &&  cpCode.trim().equals("YUNDA411353")){
								paramMap.put("expressCompany", "YUNDA");
							}else if(cpCode!=null && cpCode.trim().equals("HTKY5")){
								paramMap.put("expressCompany", "HTKY");
							}else if(cpCode!=null && cpCode.trim().equals("HTKY11")){
								paramMap.put("expressCompany", "HTKY");
							}else{
								paramMap.put("expressCompany", cpCode);
							}
//							paramMap.put("expressCompany", cpCode);
							if(cpCode!=null && cpCode.equals("EYB")){
								paramMap.put("expressOrderno", rootObj.getString("waybill_code"));
							}else{
								paramMap.put("expressOrderno", rootObj.getString("waybill_code"));
							}
//							paramMap.put("status", ShipOrder.EntryOrderStatus.WAIT_EXPRESS_PICKING);
							paramMap.put("status", OrderStatusEnums.WMS_GETNO.getKey());
							String templateUrl = (String) this.templateMap.get(cpCode);
							if (StringUtils.isEmpty(templateUrl)) {
								templateUrl = printData.getString("templateURL");
							}
							paramMap.put("templateURL", templateUrl);// 模板URL
							paramMap.put("routeCode", routingInfo.optString("routeCode"));
							paramMap.put("orderFlag", sortation.optString("name"));  //  大头
							paramMap.put("sellerMobile", consolidation.isNull("name")?"":consolidation.optString("name")); //收货网点名称
							paramMap.put("sellerPhone", consolidation.optString("code"));  //收货网点CODE
//							paramMap.put("batchCode", batchCode);//打印批次号
							paramMapList.add(paramMap);
							shipOrderService.setSendOrderExpressAndStatusById(paramMapList);
							
							idArry=idArry+ary[i]+",";
							resultMap.put("ids", idArry);
							
							//更新订单状态
						//	tradeService.updateTradeStatus(trade.getId(), Trade.Status.TRADE_WAIT_EXPRESS_NOFITY);
							resultMap.put("flag", "成功");
							resultMap.put("orderNo",rootObj.getString("waybill_code"));
							resultMap.put("code", "200");
						}catch(Exception e){
							e.printStackTrace();
							resultMap.put("flag", "接口调用成功,数据反写系统失败");
							resultMap.put("code", "501");
							resultMap.put("errorInfo", nameMsg  +";msg:"+"数据反写系统失败;"+e.getMessage());
							String msg=e.getMessage();
							buffer.append("["+trade.getReceiverName()+"|"+trade.getReceiverMobile()).append("]").append(msg).append("</br>");
						}
						
				}else{
					logger.debug("菜鸟云 面单失败返回"+responseText);
					JSONObject object=new JSONObject(responseText);
					JSONObject errorObj=object.getJSONObject("error_response");
					String msg=errorObj.getString("sub_msg");
					buffer.append(trade.getReceiverName()+"|"+trade.getReceiverMobile()+"|"+msg+"</br>");
				}
				
			} catch (Exception e) {
				resultMap.put("flag", "接口调用失败");
				resultMap.put("message", "cainiao.waybill.ii.get  接口调用失败!");
				resultMap.put("code", "500");
				resultMap.put("errorInfo",nameMsg  +";msg:"+"接口异常:"+e.getMessage());
			}
			resultMap.put("msg", buffer.toString());
		}
		return resultMap;
	}
	
	public void searchCpCode(String cpCode) throws Exception{
		TaobaoClient client = new DefaultTaobaoClient(StoreConstants.cainiao_url, StoreConstants.rookie_appkey, StoreConstants.rookie_secret);
		CainiaoWaybillIiSearchRequest req = new CainiaoWaybillIiSearchRequest();
		req.setCpCode(cpCode);
		initSessionKey();
		CainiaoWaybillIiSearchResponse response = client.execute(req , StoreConstants.rookie_sessionKey);
		logger.debug(""+response.getBody());
	}
	

	private void initSessionKey(){
		
		Map<String,Object> params=new HashMap<String,Object>();
		String type=StoreSystemItemEnums.SESSIONKEY.getKey();
		List<SystemItem> itemList= this.systemItemService.findSystemItemByType(type);
		SystemItem item=itemList.get(0);
		String sessionKey=item.getValue();
		StoreConstants.rookie_sessionKey=sessionKey;
	}

	
	private void refreshTemplate() {
		if (this.templateMap == null) {
			this.templateMap = new HashMap<String, Object>();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", "isv_template");
		List<SystemItem> sysItem = this.systemItemService.findSystemItemByType("isv_template");
		for (int i = 0; i < sysItem.size(); i++) {
			SystemItem obj = sysItem.get(i);
			this.templateMap.put(obj.getDescription(), obj.getValue());
		}
	}
		
}
