package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.ShipOrder;
import com.graby.store.service.inventory.InventoryService;
import com.graby.store.service.inventory.QmInventoryService;


@RemotingService(serviceInterface = InventoryRemote.class, serviceUrl = "/inventory.call")
public class InventoryRemoteImpl implements InventoryRemote {

	@Autowired
	private InventoryService InventoryService;
	
	@Autowired
	private QmInventoryService qmInventoryService;
	
	@Override
	public void  adminAddInventory(Long centroId,Long userId, Long itemId ,Long num,String type,String description){
		qmInventoryService.adminAddInventory(centroId, userId, itemId, num, type, description);
	 
	}
	
	@Override
	public  Long  existOrderNo(Map<String,Object> params) {
		return qmInventoryService.existOrderNo(params);
	}
	
	
	@Override
	public Long getValue(Long centroId, Long itemId, String account) {
		return InventoryService.getValue(centroId, itemId, account);
	}

	@Override
	public List<Map<String, Long>> stat(Long centroId, Long userId) {
		return InventoryService.stat(centroId, userId);
	}
	
	@Override
	public List<Map<String, Object>> inItInventory(List<Map<String,Object>>  param) {
		return qmInventoryService.inItInventory(param);
	}
	
	@Override
	public void  addInventory(Long centroId,Long userId, Long itemId ,Long num,String account,String type,String description){
		qmInventoryService.addInventory(centroId, userId, itemId, num, account, type, description);
	}

	@Override
	public void batchAddInventory(ShipOrder order, String type,String description){
		qmInventoryService.batchAddInventory(order, type, description);
	}
	 
	@Override
	public void batchDelInventory(ShipOrder order, String type,String description){
		qmInventoryService.batchDelInventory(order, type, description);
	}
	
	@Override
	public List<Map<String, Object>> statPage(int page, int rows,Map<String, Object> params) {
		return InventoryService.statPage( page,  rows, params);
	}
	@Override
	public Long statPageCount (Map<String, Object> params) {
		return InventoryService.statPageCount(params);
	}


	@Override
	public void updateUserNum(Long centroId, Long itemId, String account, long usenum) {
		// TODO Auto-generated method stub
		this.InventoryService.updateUserNum(centroId, itemId, account, usenum);
	}

}
