package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.CentroItem;

public interface CentroItemRemote {

	
	public List<CentroItem> findCentroItems(Map<String, Object> params);
	
	public  void saveCentroItem(CentroItem centroItem);
}
