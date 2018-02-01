package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.Storage;
import com.graby.store.entity.StorageItem;
import com.graby.store.entity.enums.StorageStatusEnums;
import com.graby.store.service.inventory.Accounts;
import com.graby.store.service.inventory.QmInventoryService;
import com.graby.store.service.wms.StorageService;

@RemotingService(serviceInterface = StorageRemote.class, serviceUrl = "/storage.call")
public class StorageRemoteImpl implements StorageRemote {

	@Autowired
	private StorageService storageService;
	@Autowired
	private QmInventoryService qmInventoryService;

	@Override
	public void save(Storage storage) {
		storageService.save(storage);
	}
	
	@Override
	public Page<Storage> findStoragesList(int pageNo, int pageSize, Map<String, Object> params){
		return storageService.findStoragesList(pageNo, pageSize, params);
	}
	
	@Override
	public Long findStoragesCount(Map<String, Object> params){
		return storageService.findStoragesCount(params);
	}

	@Override
	public void saveStorageAndAddInventory(Storage storage) {
		storageService.save(storage);

		List<StorageItem> items = storage.getItems();
		Long centroId = storage.getCentro().getId();
		Long userId = storage.getUser().getId();
		String account = Accounts.CODE_SALEABLE;
		String type = "";
		String description = "";
		if (storage.getStatus().equals(StorageStatusEnums.ENTRY_DELIVERY) ){
			description = "出库_"+storage.getOrderNo();
			type = "出库";
			for (StorageItem item : items) {// 写库存
				qmInventoryService.addInventory(centroId, userId, item.getItem().getId(),
						Long.valueOf(item.getQuantity())*-1, account, type, description);
			}
		}else if (storage.getStatus().equals(StorageStatusEnums.ENTRY_FINISH)){
			description	 = "扫码入库_"+storage.getOrderNo();
			type = "扫码入库";
			for (StorageItem item : items) {// 写库存
				qmInventoryService.addInventory(centroId, userId, item.getItem().getId(),
						Long.valueOf(item.getQuantity()), account, type, description);
			}
		}else if (storage.getStatus().equals(StorageStatusEnums.ENTRY_CHECK_FINISH)){
			description = "盘点入库_"+storage.getOrderNo();
			type = "盘点入库";
			for (StorageItem item : items) {// 写库存
				qmInventoryService.addInventory(centroId, userId, item.getItem().getId(),
						Long.valueOf(item.getQuantity()), account, type, description);
			}
		}
	}

	@Override
	public Storage getStorageById(Long id) {
		// TODO Auto-generated method stub
		return this.storageService.getStorageById(id);
	}

	@Override
	public List<Storage> getStorageList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return storageService.getStorages(params);
	}

	@Override
	public List<StorageItem> getStorageItemList(Map<String, Object> params) {
		return this.storageService.getStorageItemList(params);
	}
	
	/**
	 * 单据明细id查询
	 * */
	@Override
	public List<StorageItem> storageDetailbyId(Map<String, Object> params) {
		return storageService.storageDetailbyId(params);
	}
	

}
