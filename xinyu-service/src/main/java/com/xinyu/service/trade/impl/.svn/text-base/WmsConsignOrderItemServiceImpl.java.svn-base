package com.xinyu.service.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.trade.WmsConsignOrderItemDao;
import com.xinyu.model.trade.WmsConsignOrderItem;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.trade.WmsConsignOrderItemService;

@Service("wmsConsignOrderItemService")
public class WmsConsignOrderItemServiceImpl extends BaseServiceImpl implements WmsConsignOrderItemService {

	@Autowired
	private WmsConsignOrderItemDao wmsConsignOrderItemDao;
	
	@Override
	public void insertWmsConsignOrderItem(WmsConsignOrderItem item) {
		// TODO Auto-generated method stub
		this.wmsConsignOrderItemDao.insertWmsConsignOrderItem(item);
	}

	@Override
	public void updateWmsConsignOrderItem(WmsConsignOrderItem item) {
		// TODO Auto-generated method stub
		this.wmsConsignOrderItemDao.updateWmsConsignOrderItem(item);
	}

	@Override
	public WmsConsignOrderItem getWmsConsignOrderItemById(String id) {
		// TODO Auto-generated method stub
		return this.wmsConsignOrderItemDao.getWmsConsignOrderItemById(id);
	}

	@Override
	public List<WmsConsignOrderItem> getWmsConsignOrderItemByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.wmsConsignOrderItemDao.getWmsConsignOrderItemByList(params);
	}

	@Override
	public void insertWmsConsignOrderItemList(List<WmsConsignOrderItem> list) {
		// TODO Auto-generated method stub
		if(list!=null){
			for(WmsConsignOrderItem item :list){
				this.insertWmsConsignOrderItem(item);
			}
		}
	}

	@Override
	public List<Map<String, Object>> findStoreOrderItemList(Map<String, Object> params) {
		return this.wmsConsignOrderItemDao.findStoreOrderItemList(params);
	}

}
