package com.graby.store.service.wms;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.ShipOrderCancelDao;
import com.graby.store.entity.ShipOrderCancel;

@Component
@Transactional
public class ShipOrderCancelService {

	@Autowired
	private ShipOrderCancelDao shipOrderCancelDao;
	/**
	 * 保存单据取消记录
	 * @param cancel
	 */
	public void save(ShipOrderCancel cancel){
		this.shipOrderCancelDao.save(cancel);
	}
	
	public List<ShipOrderCancel> getShipOrderCancelByList(Map<String, Object> params, int page, int rows) {
		int start = (page-1)*rows;
		int offset = rows;
		params.put("start", start);
		params.put("offset",offset);
		List<ShipOrderCancel> orderCancels = this.shipOrderCancelDao.getShipOrderCancelByList(params);
		return orderCancels;
	}
	
	public int selectByCount(Map<String, Object> params) {
		return this.shipOrderCancelDao.selectByCount(params);
	}
	
}
