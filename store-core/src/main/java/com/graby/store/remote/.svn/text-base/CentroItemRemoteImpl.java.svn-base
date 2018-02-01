package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.CentroItem;
import com.graby.store.service.base.CentroItemService;

@RemotingService(serviceInterface = CentroItemRemote.class, serviceUrl = "/centroItem.call")
public class CentroItemRemoteImpl implements CentroItemRemote {
	
	@Autowired
	public CentroItemService centroItemService;
	
	
	public List<CentroItem> findCentroItems(Map<String, Object> params){
		return centroItemService.findCentroItems(params);
	}
	
	public  void saveCentroItem(CentroItem centroItem){
		centroItemService.saveCentroItem(centroItem);
	}
}
