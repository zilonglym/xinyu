package com.graby.store.remote;

import java.util.List;

import com.graby.store.entity.ShipOrderPackage;
import com.graby.store.entity.ShipOrderPackageItem;

public interface ShipOrderPackageRemote {
	ShipOrderPackage findPackage(int id);
	
	 List<ShipOrderPackage> findPackageByOrderId(int orderId);
	
	List<ShipOrderPackageItem> findPackageItem(int packageId);
	
	void save(ShipOrderPackage obj);
	
	void saveItem(ShipOrderPackageItem item);
	
	void saveItemList(List<ShipOrderPackageItem> itemList);
		
}
