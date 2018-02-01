package com.graby.store.service.wms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.PackageDao;
import com.graby.store.dao.mybatis.ShipOrderCancelDao;
import com.graby.store.entity.ShipOrderCancel;
import com.graby.store.entity.ShipOrderPackage;
import com.graby.store.entity.ShipOrderPackageItem;

@Component
@Transactional
public class ShipOrderPackageService {

	@Autowired
	private PackageDao packageDao;
	
	
	public ShipOrderPackage findPackage(int id){
		return this.packageDao.findPackage(id);
	}
	
	public List<ShipOrderPackage> findPackageByOrderId(int orderId){
		return this.packageDao.findPackageByOrderId(orderId);
	}
	
	public List<ShipOrderPackageItem> findPackageItem(int packageId){
		return this.packageDao.findPackageItem(packageId);
	}
	
	public  void save(ShipOrderPackage obj){
		this.packageDao.save(obj);
	}
	
	public void saveItem(ShipOrderPackageItem item){
		this.packageDao.saveItem(item);
	}
	
	public void saveItemList(List<ShipOrderPackageItem> itemList){
		for(int i=0;itemList!=null && i<itemList.size();i++){
			this.packageDao.saveItem(itemList.get(i));
		}
	}	
	
}
