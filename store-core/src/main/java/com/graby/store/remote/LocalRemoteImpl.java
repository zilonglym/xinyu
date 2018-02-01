package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.local.LocalBatch;
import com.graby.store.entity.local.LocalBoxOut;
import com.graby.store.entity.local.LocalItem;
import com.graby.store.entity.local.LocalOperateRecord;
import com.graby.store.entity.local.LocalPlate;
import com.graby.store.entity.local.LocalShop;
import com.graby.store.service.local.LocalService;

@RemotingService(serviceInterface = LocalRemote.class, serviceUrl = "/local.call")
public class LocalRemoteImpl implements LocalRemote{
	
	@Autowired
	private LocalService localService;

	@Override
	public Map<String, List<LocalBoxOut>> getLocalBoxOutByRow(String row) {
		// TODO Auto-generated method stub
		return this.localService.getLocalBoxOutByRow(row);
	}

	@Override
	public void saveLocalBoxOut(LocalBoxOut localBoxOut) {
		// TODO Auto-generated method stub
		this.localService.saveLocalBoxOut(localBoxOut);
	}

	@Override
	public List<LocalPlate> getLocalPlateByBoxOutId(int boxOutId) {
		// TODO Auto-generated method stub
		return this.localService.getLocalPlateByBoxOutId(boxOutId);
	}

	@Override
	public Map<String, Object> upLocalPlate(Map<String,Object> params) {
		// TODO Auto-generated method stub
		return this.localService.upLocalPlate(params);
	}

	@Override
	public Map<String, Object> downLocalPlate(int localPlateId,String opertionId) {
		// TODO Auto-generated method stub
		return this.localService.downLocalPlate(localPlateId,opertionId);
	}

	@Override
	public Map<String, Object> getRows() {
		// TODO Auto-generated method stub
		return this.localService.getRows();
	}
	
	@Override
	public  List<LocalPlate> getLocalPlateByItemId(int itemId){
		// TODO Auto-generated method stub
		return this.localService.getLocalPlateByItemId(itemId);
	}
	
	@Override
	public  List<LocalBoxOut> getLocalBoxOutById(String  id){
		// TODO Auto-generated method stub
		return this.localService.getLocalBoxOutById(id);
	}

	@Override
	public LocalShop getLocalShop(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.localService.getLocalShop(params);
	}

	@Override
	public void saveLocalShop(LocalShop localShop) {
		// TODO Auto-generated method stub
		this.localService.saveLocalShop(localShop);
		
	}

	@Override
	public List<LocalShop> getLocalShopList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.localService.getLocalShopList(params);
	}

	@Override
	public int getLocalShopCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.localService.getLocalShopCount(params);
	}

	@Override
	public void updateLocalShop(LocalShop localShop) {
		// TODO Auto-generated method stub
		this.localService.updateLocalShop(localShop);
	}

	@Override
	public LocalItem getLocalItemById(int id) {
		// TODO Auto-generated method stub
		return this.localService.getLocalItemById(id);
	}

	@Override
	public void saveLocalItem(LocalItem localItem) {
		// TODO Auto-generated method stub
		this.localService.saveLocalItem(localItem);
	}

	@Override
	public List<LocalItem> getLocalItemList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.localService.getLocalItemList(params);
	}

	@Override
	public int getLocalItemCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.localService.getLocalItemCount(params);
	}

	@Override
	public void updateLocalItem(LocalItem localItem) {
		// TODO Auto-generated method stub
		this.localService.updateLocalItem(localItem);
	}

	@Override
	public List<LocalItem> findLocalItemByPage(Map<String, Object> params, int page, int rows) {
		// TODO Auto-generated method stub
		return this.localService.findLocalItemByPage(params,page,rows);
	}

	@Override
	public void deleteLocalItemById(String id) {
		// TODO Auto-generated method stub
		this.localService.deleteLocalItemById(id);
	}
	
	@Override
	public void saveBatch(LocalBatch batch) {
		// TODO Auto-generated method stub
		this.localService.saveBatch(batch);
	}

	@Override
	public List<LocalBatch> findLocalBatchList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.localService.findLocalBatchList(params);
	}

	@Override
	public LocalBatch findLocalBatchById(int id) {
		// TODO Auto-generated method stub
		return this.localService.findLocalBatchById(id);
	}

	@Override
	public void updateLocalBatch(LocalBatch batch) {
		// TODO Auto-generated method stub
		this.localService.updateLocalBatch(batch);
	}
	
	@Override
	public LocalPlate getLocalPlateByFastCode(String fastCode) {
		// TODO Auto-generated method stub
		return localService.getLocalPlateByFastCode(fastCode);
	}
	
	@Override
	public List<Map<String, Object>> findLocalPlateList(Map<String, Object> params, int page, int rows) {
		// TODO Auto-generated method stub
		return this.localService.findLocalPlateList(params,page,rows);
	}

	@Override
	public int getTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.localService.getTotal(params);
	}

	@Override
	public List<Map<String, Object>> findLocalReport(Map<String, Object> params, int page, int rows) {
		// TODO Auto-generated method stub
		return this.localService.findLocalReport(params,page,rows);
	}

	@Override
	public List<Map<String, Object>> findLocalPlateRecord(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.localService.findLocalPlateRecord(params);
	}

	@Override
	public LocalPlate findLocalPlate(String id) {
		// TODO Auto-generated method stub
		return this.localService.findLocalPlate(id);
	}

	@Override
	public void saveOperateRecord(LocalOperateRecord record) {
		// TODO Auto-generated method stub
		this.localService.saveOperateRecord(record);
	}

	@Override
	public List<Map<String, Object>> findItemReport(Map<String, Object> p) {
		// TODO Auto-generated method stub
		return localService.findItemReport(p);
	}

	@Override
	public int getBatchTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return localService.getBatchTotal(params);
	}

	@Override
	public LocalPlate findLocalPlateByBatchId(String batchId) {
		// TODO Auto-generated method stub
		return localService.findLocalPlateByBatchId(batchId);
	}

	@Override
	public void updateBatchByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		localService.updateBatchByParams(params);
		
	}

	@Override
	public List<Map<String, Object>> findTop10LocalPlateWorkByUserId(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return localService.findTop10LocalPlateWorkByUserId(params);
	}

	@Override
	public List<Map<String, Object>> findPCLocalPlate(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return localService.findPCLocalPlate(params);
	}

	
}
