package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.CheckInventory;
import com.graby.store.entity.CheckInventoryDetail;
import com.graby.store.service.inventory.CheckInventoryService;

@RemotingService(serviceInterface = CheckInventoryRemote.class, serviceUrl = "/checkInventory.call")


public class CheckInventoryRemoteImpl implements CheckInventoryRemote {
	
	@Autowired
	private CheckInventoryService checkInventoryService;

	@Override
	public void insert(CheckInventory checkInventory) {
		checkInventoryService.insert(checkInventory);
		
	}

	@Override
	public CheckInventory getCheckInventoryById(Long id) {
		// TODO Auto-generated method stub
		return checkInventoryService.getCheckInventoryById(id);
	}

	@Override
	public List<CheckInventory> getCheckInventory(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return checkInventoryService.getCheckInventory(params);
	}

	@Override
	public List<CheckInventoryDetail> checkInventoryDetailbyId(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return checkInventoryService.checkInventoryDetailbyId(params);
	}

	@Override
	public void updateCheckInventoryAudit(Map<String, Object> params) {
		// TODO Auto-generated method stub
		checkInventoryService.updateCheckInventoryAudit(params);
	}

}
