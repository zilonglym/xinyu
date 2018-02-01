package com.graby.store.service.wms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.ShipOrderCancelDao;
import com.graby.store.entity.ShipOrderCancel;

@Component
@Transactional(readOnly = true)
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
	
}
