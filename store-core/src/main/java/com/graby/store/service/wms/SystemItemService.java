package com.graby.store.service.wms;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.PackageDao;
import com.graby.store.dao.mybatis.ShipOrderCancelDao;
import com.graby.store.dao.mybatis.SystemItemDao;
import com.graby.store.entity.ShipOrderCancel;
import com.graby.store.entity.ShipOrderPackage;
import com.graby.store.entity.ShipOrderPackageItem;
import com.graby.store.entity.SystemItem;

@Component
@Transactional
public class SystemItemService {

	@Autowired
	private SystemItemDao systemDao;
	
	
	
	public SystemItem findSystemItem(int id){
		return this.systemDao.findSystemItem(id);
	}
	
	
	public List<SystemItem> findSystemItemByParentId(int parentId){
		return this.systemDao.findSystemItemByParentId(parentId);
	}
	
	public List<SystemItem> findSystemItemByType(String type){
		return this.systemDao.findSystemItemByType(type);
	}
	
	public List<SystemItem> findSystemItemByTypeAndVal(Map<String,Object> params){
		return this.systemDao.findSystemItemByTypeAndVal(params);
	}
	
}
