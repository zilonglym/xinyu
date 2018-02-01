package com.xinyu.dao.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.trade.WmsConsignOrderItem;

public interface WmsConsignOrderItemDao extends BaseDao {

	public void insertWmsConsignOrderItem(WmsConsignOrderItem item);
	
	public void updateWmsConsignOrderItem(WmsConsignOrderItem item);
	
	public WmsConsignOrderItem getWmsConsignOrderItemById(String id);
	
	public List<WmsConsignOrderItem> getWmsConsignOrderItemByList(Map<String,Object> params);
	
	public void deleteWmsConsignOrderItem(String id);

	public List<Map<String, Object>> findStoreOrderItemList(Map<String, Object> params);
}
