package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.AuditArea;
import com.graby.store.entity.local.LocalBatch;
import com.graby.store.entity.local.LocalBoxOut;
import com.graby.store.entity.local.LocalItem;
import com.graby.store.entity.local.LocalOperateRecord;
import com.graby.store.entity.local.LocalPlate;
import com.graby.store.entity.local.LocalShop;

public interface LocalRemote {
	
	public List<Map<String, Object>> findTop10LocalPlateWorkByUserId(Map<String,Object> params);
	
	public  Map<String,List<LocalBoxOut>> getLocalBoxOutByRow(String  row);
	
	public void  saveLocalBoxOut(LocalBoxOut localBoxOut);
	
	public List<LocalPlate> getLocalPlateByBoxOutId(int boxOutId);
	
	public Map<String, Object> upLocalPlate(Map<String, Object>  params);
	
	public Map<String, Object> downLocalPlate(int localPlateId,String opertionId) ;
	
	
	public  Map<String,Object>  getRows();
	
	public  List<LocalPlate> getLocalPlateByItemId(int itemId);
	
	public  List<LocalBoxOut> getLocalBoxOutById(String  id);
	
	
	public LocalPlate findLocalPlateByBatchId(String batchId);
	
	/**
	 * LocalShop
	 */
	public LocalShop getLocalShop(Map<String, Object> params);
	
	public void saveLocalShop(LocalShop localShop);
	
	public List<LocalShop> getLocalShopList(Map<String, Object> params);
	
	public int getLocalShopCount(Map<String, Object> params);
	
	public void updateLocalShop(LocalShop localShop);
	
	
	/**
	 * LocalItem
	 */
	public LocalItem getLocalItemById(int id);
	
	public void saveLocalItem(LocalItem localItem);
	
	public List<LocalItem> getLocalItemList(Map<String, Object> params);
	
	public int getLocalItemCount(Map<String, Object> params);
	
	public void updateLocalItem(LocalItem localItem);

	public List<LocalItem> findLocalItemByPage(Map<String, Object> params, int page, int rows);

	public void deleteLocalItemById(String id);

	public LocalPlate getLocalPlateByFastCode(String fastCode);

	/**
	 *	localBatch
	 */
	public void saveBatch(LocalBatch batch);

	public List<LocalBatch> findLocalBatchList(Map<String, Object> params);

	public LocalBatch findLocalBatchById(int id);

	public void updateLocalBatch(LocalBatch batch);
	
	public int getBatchTotal(Map<String, Object> params);
	
	public void updateBatchByParams(Map<String, Object> params);

	/**
	 * admin后台
	 * @param params
	 * @param rows 
	 * @param page 
	 * @return
	 */
	public List<Map<String, Object>> findLocalPlateList(Map<String, Object> params, int page, int rows);

	public int getTotal(Map<String, Object> params);

	public List<Map<String, Object>> findLocalReport(Map<String, Object> params, int page, int rows);

	public List<Map<String, Object>> findLocalPlateRecord(Map<String, Object> params);

	public LocalPlate findLocalPlate(String id);
	
	public void saveOperateRecord(LocalOperateRecord record);

	public List<Map<String, Object>> findItemReport(Map<String, Object> p);

	/**
	 * 手机apk库位查询
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findPCLocalPlate(Map<String, Object> params);

	

}
