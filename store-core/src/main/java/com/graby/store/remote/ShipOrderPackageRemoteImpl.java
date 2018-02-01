package com.graby.store.remote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.ShipOrderPackage;
import com.graby.store.entity.ShipOrderPackageItem;
import com.graby.store.service.wms.ShipOrderPackageService;
@RemotingService(serviceInterface = ShipOrderPackageRemote.class, serviceUrl = "/order.call")
public class ShipOrderPackageRemoteImpl implements ShipOrderPackageRemote{
	@Autowired
	private ShipOrderPackageService shipOrderPackageService;
	
	@Override
	public ShipOrderPackage findPackage(int id){
		return shipOrderPackageService.findPackage(id);
	}
	@Override
	public List<ShipOrderPackage> findPackageByOrderId(int orderId){
		return shipOrderPackageService.findPackageByOrderId(orderId);
	}
	@Override
	public List<ShipOrderPackageItem> findPackageItem(int packageId){
		return shipOrderPackageService.findPackageItem(packageId);
	}
	@Override
	public  void save(ShipOrderPackage obj){
		shipOrderPackageService.save(obj);
	}
	@Override
	public void saveItem(ShipOrderPackageItem item){
		shipOrderPackageService.saveItem(item);
	}
	@Override
	public void saveItemList(List<ShipOrderPackageItem> itemList) {
		shipOrderPackageService.saveItemList(itemList);
		
	}
}
