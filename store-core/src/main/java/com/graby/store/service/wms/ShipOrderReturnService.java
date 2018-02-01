package com.graby.store.service.wms;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.base.StoreConstants;
import com.graby.store.dao.mybatis.ShipOrderReturnDao;
import com.graby.store.entity.ShipOrderReturn;
import com.graby.store.service.util.IRedisProxy;

@Component
@Transactional
public class ShipOrderReturnService {
	@Autowired
	private ShipOrderReturnDao returnDao;
	
	public List<ShipOrderReturn> findShipOrderByexpress(Map<String,Object> params){
		return this.returnDao.findShipOrderByexpress(params);
	}
	
	public void save(ShipOrderReturn orderReturn){
		this.returnDao.save(orderReturn);
	}
	}
