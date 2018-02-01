package com.xinyu.service.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.model.trade.WmsConsignOrderItem;
import com.xinyu.service.common.BaseService;

public interface WmsConsignOrderItemService extends BaseService {

	public void insertWmsConsignOrderItem(WmsConsignOrderItem item);
	
	public void insertWmsConsignOrderItemList(List<WmsConsignOrderItem>  list);
	
	public void updateWmsConsignOrderItem(WmsConsignOrderItem item);
	
	public WmsConsignOrderItem getWmsConsignOrderItemById(String id);
	
	public List<WmsConsignOrderItem> getWmsConsignOrderItemByList(Map<String,Object> params);

	public List<Map<String, Object>> findStoreOrderItemList(Map<String, Object> params);
}
