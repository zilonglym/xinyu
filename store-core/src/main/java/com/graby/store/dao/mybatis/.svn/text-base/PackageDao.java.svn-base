package com.graby.store.dao.mybatis;

import java.util.List;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.Centro;
import com.graby.store.entity.ShipOrderPackage;
import com.graby.store.entity.ShipOrderPackageItem;

@MyBatisRepository
public interface PackageDao {
	
	public ShipOrderPackage findPackage(int id);
	
	public List<ShipOrderPackage> findPackageByOrderId(int orderId);
	
	public List<ShipOrderPackageItem> findPackageItem(int packageId);
	
	public  void save(ShipOrderPackage obj);
	
	public void saveItem(ShipOrderPackageItem item);
	
}
