package com.graby.store.service.base;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graby.store.dao.mybatis.CentroItemDao;
import com.graby.store.entity.CentroItem;

@Component
public class CentroItemService {
	
	
	@Autowired
	private CentroItemDao centroItemDao;
 
	public List<CentroItem> findCentroItems(Map<String, Object> params){
		return centroItemDao.findCentroItems(params);
	}
	
	public  void saveCentroItem(CentroItem centroItem){
		centroItemDao.saveCentroItem(centroItem);
	}
}
