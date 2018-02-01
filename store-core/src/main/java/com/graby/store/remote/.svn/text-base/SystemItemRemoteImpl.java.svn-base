package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.SystemItem;
import com.graby.store.service.wms.SystemItemService;

@RemotingService(serviceInterface = SystemItemRemote.class, serviceUrl = "/sys.call")
public class SystemItemRemoteImpl implements SystemItemRemote{
	@Autowired
	private SystemItemService systemService;
	
	
	
	public SystemItem findSystemItem(int id){
		return systemService.findSystemItem(id);
	}
	
	
	public List<SystemItem> findSystemItemByParentId(int parentId){
		return systemService.findSystemItemByParentId(parentId);
	}
	
	public List<SystemItem> findSystemItemByType(String type){
		return systemService.findSystemItemByType(type);
	}
	
	public List<SystemItem> findSystemItemByTypeAndVal(Map<String,Object> params){
		return this.systemService.findSystemItemByTypeAndVal(params);
	}
}
