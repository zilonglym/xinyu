package com.graby.store.web.top;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.graby.store.base.ServiceException;
import com.graby.store.web.auth.ShiroContextUtils;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoResponse;
import com.taobao.api.domain.TradeOrderInfo;
import com.taobao.api.domain.WaybillAddress;
import com.taobao.api.domain.WaybillApplyNewRequest;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.request.WlbWaybillIGetRequest;
import com.taobao.api.response.WlbWaybillIGetResponse;

/**
 * 电子面单
 * 
 */
@Component
public class TopWmsApi {


	@Value("${top_wms.appkey}")
	private String appKey = "";

	@Value("${top_wms.appSecret}")
	private String appSecret = "";

	@Value("${top.serverUrl}")
	private String serverUrl = "";
	
	@Value("${top_wms.redirectUri}")
	private String wmsRedirectUri = "";

	private DefaultTaobaoClient client;

	@PostConstruct
	public void init() {
		client = new DefaultTaobaoClient(serverUrl, appKey, appSecret, "json");
	}
	
	public String getAuthurl()  {
		String env = System.getenv("mode");
		boolean online = env != null && env.equalsIgnoreCase("online");
		StringBuffer url = new StringBuffer();
		url.append(online? "https://oauth.taobao.com/authorize?response_type=code":"https://oauth.tbsandbox.com/authorize?response_type=code");
		url.append("&client_id=" + appKey);
		url.append("&redirect_uri=" + wmsRedirectUri);
		return url.toString();
	}

	/**
	 * 电子面单获取接口
	 * @param cpCode
	 * @param address
	 * @param tradeOrderInfos
	 * @return
	 * @throws ApiException
	 */
	public WlbWaybillIGetResponse wayBillGet(String cpCode, WaybillAddress address, TradeOrderInfo tradeOrderInfo) throws ApiException {
		
		WlbWaybillIGetRequest req=new WlbWaybillIGetRequest();
		
		WaybillApplyNewRequest waybillApplyNewRequest = new WaybillApplyNewRequest();
		waybillApplyNewRequest.setCpCode(cpCode);
		waybillApplyNewRequest.setShippingAddress(address);
		
		//waybillApplyNewRequest.setTradeOrderInfoCols(tradeOrderInfo);
		req.setWaybillApplyNewRequest(waybillApplyNewRequest);
		WlbWaybillIGetResponse response = client.execute(req , ShiroContextUtils.getWmsSessionKey());
		
		
//		waybillApplyNewRequest.setCpCode("SF");
//		WaybillAddress waybillAddress = new WaybillAddress();
//		waybillAddress.setProvince("浙江省");
//		waybillAddress.setCity("杭州市");
//		waybillAddress.setArea("余杭区");
//		waybillAddress.setAddressDetail("文一西路969号");
//		waybillApplyNewRequest.setShippingAddress(waybillAddress);
		
//		TradeOrderInfo tradeOrderInfo = new TradeOrderInfo();
//		tradeOrderInfo.setConsigneeAddress(waybillAddress);
//		tradeOrderInfo.setConsigneeName("张三");
//		tradeOrderInfo.setConsigneePhone("13798870987");
//		tradeOrderInfo.setItemName("测试商品");
//		tradeOrderInfo.setWaybillCode("123456");
//		tradeOrderInfo.setOrderChannelsType("TB");
//		tradeOrderInfo.setProductType("STANDARD_EXPRESS");
//		tradeOrderInfo.setRealUserId(123232L);
		
//		List<PackageItem> items = new ArrayList<PackageItem>();
//		PackageItem packageItem = new PackageItem();
//		packageItem.setCount(11L);
//		packageItem.setItemName("衣服");
//		items.add(packageItem);
//		tradeOrderInfo.setPackageItems(items);
		
//		List<String> orderList = new ArrayList<String>();
//		orderList.add("SW-20140403-00654");
//		tradeOrderInfo.setTradeOrderList(orderList);
		return response;
	}

	private void throwIfError(TaobaoResponse resp) {
		if (StringUtils.isNotEmpty(resp.getErrorCode())) {
			throw new ServiceException(resp.getMsg() + resp.getSubMsg());
		}
	}

	private static JSONWriter write = new JSONWriter(false, true);
	private static String toJsonString(Object object) {
		return write.write(object);
	}

}
