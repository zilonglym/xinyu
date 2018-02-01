package com.xinyu.service.caoniao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.taobao.pac.sdk.cp.SendSysParams;
import com.taobao.pac.sdk.cp.dataobject.request.WMS_ITEM_QUERY.WmsItemQueryRequest;
import com.taobao.pac.sdk.cp.dataobject.response.WMS_ITEM_QUERY.WmsItemQueryResponse;
import com.xinyu.service.common.Constants;
import com.xinyu.service.util.CaiNiaoPacClient;

@Component
public class WmsItemQueryService {

	
	
	/**
	 * 商品查询
	 * @param request
	 * @param params
	 * @return
	 */
	public Map<String,Object> submitItemQuery(String providerTpId,String[] itemIds){
		WmsItemQueryRequest request=new WmsItemQueryRequest();
		SendSysParams params=new SendSysParams();
		params.setFromCode(Constants.cainiao_fromCode);
//		request.setItemIds(itemIds);
		List<Long> list=new ArrayList<Long>();
		for(int i=0;itemIds!=null && i<itemIds.length;i++){
			list.add(Long.valueOf(itemIds[i]));
		}
		request.setItemIds(list);
		request.setProviderTpId(Long.valueOf(providerTpId));
		WmsItemQueryResponse response =CaiNiaoPacClient.getClient().send(request, params);
		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if(response.isSuccess()){
			System.err.println("成功！");
			resultMap.put("ret", 1);
		}else{
			System.err.println("失败");
			resultMap.put("ret", 0);
			resultMap.put("msg", response.getErrorMsg());
		}
		return resultMap;
	}
		
}
