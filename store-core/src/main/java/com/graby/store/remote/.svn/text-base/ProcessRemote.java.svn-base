package com.graby.store.remote;

import java.util.List;

import com.graby.store.entity.StoreProcess;
import com.graby.store.entity.StoreProcessItem;

public interface ProcessRemote {
	void saveProcess(StoreProcess process);
	
	List<StoreProcess> findProcessConfirm();
	
	StoreProcess findById(Integer id);
	
	List<StoreProcessItem> findByparentId(Integer parentId);
	
	void saveProcessItem(StoreProcessItem item);
}
