package com.graby.store.service.local;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.ItemDao;
import com.graby.store.dao.mybatis.LocalBatchDao;
import com.graby.store.dao.mybatis.LocalBoxOutDao;
import com.graby.store.dao.mybatis.LocalItemDao;
import com.graby.store.dao.mybatis.LocalOperateRecordDao;
import com.graby.store.dao.mybatis.LocalPlateDao;
import com.graby.store.dao.mybatis.LocalPlateRecordDao;
import com.graby.store.dao.mybatis.LocalShopDao;
import com.graby.store.entity.Item;
import com.graby.store.entity.local.LocalBatch;
import com.graby.store.entity.local.LocalBoxOut;
import com.graby.store.entity.local.LocalItem;
import com.graby.store.entity.local.LocalOperateRecord;
import com.graby.store.entity.local.LocalPlate;
import com.graby.store.entity.local.LocalPlateRecord;
import com.graby.store.entity.local.LocalShop;

/**
 * @author shark_cj
 * @since 2017-08-22
 */
@Component
public class LocalService {
	public static final Logger logger = Logger.getLogger(LocalService.class);
	@Autowired
	private LocalPlateDao localPlateDao;
	
	@Autowired
	private LocalBatchDao localBatchDao;
	
	@Autowired
	private LocalBoxOutDao localBoxOutDao;
	
	@Autowired
	private  LocalPlateRecordDao  localPlateRecordDao;
	
	@Autowired
	private  ItemDao  itemDao;
	
	@Autowired
	private  LocalItemDao  localItemDao;
	
	@Autowired
	private  LocalShopDao  localShopDao;
	
	@Autowired
	private LocalOperateRecordDao operateRecordDao;
	
	
	/**
	 * 构建货位循序 和结构
	 * @param lists
	 * @return
	 */
	private Map<String,List<LocalBoxOut>> buildLocalBoxOut(List<LocalBoxOut> lists){
		String floorTemp = "";
		List<LocalBoxOut> list =  null;
		Map<String,List<LocalBoxOut>>  map  = new HashMap<String, List<LocalBoxOut>>();
		for(LocalBoxOut boxOut :  lists){
			String floor = boxOut.getFloor();
			if(floorTemp.equals(floor)){
				list.add(boxOut);
			}else{
				if(list!=null){
					System.out.println("floor:"+floor);
					System.out.println("list:"+list.get(0).getFloor());
					map.put(list.get(0).getFloor(), list);
				}
				floorTemp  = floor;
				list  =new ArrayList<LocalBoxOut>();
				list.add(boxOut);
			}
		}
		System.out.println("floor:"+floorTemp);
		logger.error("buildLocalBoxOut"+list);
		if(list!=null && list.size()>0){
			map.put(list.get(0).getFloor(), list);
		}
		return map;
	}
	
	/**
	 * 获得单排货架所有信息
	 * @param row
	 * @return
	 */
	public  Map<String,List<LocalBoxOut>> getLocalBoxOutByRow(String  row){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("row", row);
		return  buildLocalBoxOut(localBoxOutDao.getLocalBoxOut(params));
	}
	
	/**
	 * 获得单排货架所有信息
	 * @param id
	 * @return
	 */
	public  List<LocalBoxOut> getLocalBoxOutById(String  id){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return  localBoxOutDao.getLocalBoxOut(params);
	}
	/**
	 * 保存库位
	 * @param localBoxOut
	 * @return
	 */
	public void  saveLocalBoxOut(LocalBoxOut localBoxOut){
		localBoxOutDao.save(localBoxOut);
		for(int  i  = 1 ; i<4;i++){   //卡位   自动 生产3个库位
			LocalPlate plate =  new LocalPlate();
			plate.setBoxOut(localBoxOut);
			plate.setCode(""+i);
			plate.setLastUpdateDate(new Date());
			plate.setState("0");
			localPlateDao.save(plate);
		}
	}
	
	/**
	 * 根据库位查询板位
	 * @param boxOutId
	 * @return
	 */
	public List<LocalPlate> getLocalPlateByBoxOutId(int boxOutId){
		
		return localPlateDao.findLocalPlateByBoxOutId(boxOutId);
	}
	
	/**
	 * 上架
	 * @param localPlateId
	 * @param itemId
	 * @param num
	 * @return
	 */
	@Transactional
	public Map<String, Object> upLocalPlate(Map<String,Object> params) {
		int localPlateId  =   Integer.valueOf(""+params.get("id")); 
		String itemId   =  ""+params.get("itemId"); 
		int num   =  Integer.valueOf(""+params.get("num")); 
		String opertionId  =""+params.get("opertionId"); 
		String batchId  =""+params.get("batchId"); 
		Map<String, Object>  retMap  = new HashMap<String, Object>();
		try {
			if((!"".equals(batchId)) && (!"null".equals(batchId)) ){
				LocalPlate batchPlate = localPlateDao.findLocalPlateByBatchId(batchId);
				if(batchPlate!=null){
					retMap.put("code", "500");
					retMap.put("msg", "此批次信息已上架,不能重复上架");
					return retMap;
				}
			}
			
			LocalPlate localPlate = localPlateDao.findLocalPlate(localPlateId);
			if(num<=0){
				retMap.put("code", "500");
				retMap.put("msg", "上架数量必须大于0");
				return retMap;
			}
			if(localPlate==null){
				retMap.put("code", "404");
				retMap.put("msg", "查不到板位信息");
				return retMap;
			}
//			Item item = itemDao.get(new Long(itemId));
			if(itemId==null||  "null".equals(itemId)){
				retMap.put("code", "404");
				retMap.put("msg", "查不到商品信息");
				return retMap;
			}
			
			//上架
//			Map<String,Object>  params  = new HashMap<String, Object>();
//			params.put("id", localPlateId);
//			params.put("itemId", itemId);
//			params.put("num", num);
			params.put("state", "1");
			
			
			System.err.println("params:"+params);
			
			localPlateDao.updateLocalPlate(params);
			updateBoxOutState(localPlate);
			
			
			LocalPlateRecord localPlateRecord  = new LocalPlateRecord();
			localPlateRecord.setCreateDate(new Date());
			localPlateRecord.setLocalPlate(localPlate);
			localPlateRecord.setItem(itemId);
			localPlateRecord.setNum(new Long(num));
			localPlateRecord.setState("UP");//上架
			localPlateRecord.setOpertionId(opertionId);
			localPlateRecord.setBatchId(batchId);
			localPlateRecordDao.save(localPlateRecord);;
			
			retMap.put("code", "200");
			retMap.put("msg", "返回成功");
		} catch (Exception e) {
			retMap.put("code", "500");
			retMap.put("msg", "上架出现异常");
			e.printStackTrace();
		}
		return retMap;
	}

	
	/**
	 * 下架
	 * @param localPlateId
	 * @return
	 */
	@Transactional
	public Map<String, Object> downLocalPlate(int localPlateId,String opertionId) {
		
		Map<String, Object>  retMap  = new HashMap<String, Object>();
		
		try {
			
			LocalPlate localPlate = localPlateDao.findLocalPlate(localPlateId);
			
			if(localPlate==null){
				retMap.put("code", "404");
				retMap.put("msg", "查不到板位信息");
				return retMap;
			}
			
			String item = localPlate.getItem();
			
			if(item==null){
				retMap.put("code", "404");
				retMap.put("msg", "查不到商品信息");
				return retMap;
			}
//			item =  itemDao.get(item.getId());
			Long num = localPlate.getNum();
			
			
			localPlateDao.downLocalPlate(localPlateId);
			updateBoxOutState(localPlate);
			
			LocalPlateRecord localPlateRecord  = new LocalPlateRecord();
			localPlateRecord.setCreateDate(new Date());
			localPlateRecord.setLocalPlate(localPlate);
			localPlateRecord.setItem(item);
			localPlateRecord.setNum(num);
			localPlateRecord.setState("DOWN");//下架
			localPlateRecord.setOpertionId(opertionId);
			localPlateRecord.setBatchId(localPlate.getbatchId());
			localPlateRecordDao.save(localPlateRecord);;
			
			retMap.put("code", "200");
			retMap.put("msg", "返回成功");
		} catch (Exception e) {
			retMap.put("code", "500");
			retMap.put("error", e.getMessage());
			retMap.put("msg", "下架出现异常");
			e.printStackTrace();
		}
		return retMap;
	}
	
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findTop10LocalPlateWorkByUserId(Map<String,Object> params){
		return localPlateRecordDao.findTop10LocalPlateWorkByUserId(params);
	}
	
	
	
	private void  updateBoxOutState(LocalPlate  localPlate){
		
		LocalBoxOut boxOut = localPlate.getBoxOut();
		List<LocalPlate> plates = localPlateDao.findLocalPlateByBoxOutId(boxOut.getId());
		String state =   "";
		for(LocalPlate plate : plates){
			state = state  +","+plate.getState();
		}
		state = state.substring(1);
		
		Map<String,Object>  params =  new HashMap<String, Object>();
		params.put("id", boxOut.getId());
		params.put("state", state);
		localBoxOutDao.updateLocalBoxOut(params);
		
	}
	
	
	public  Map<String,Object>  getRows(){
		List<Map<String, Object>> rows = localBoxOutDao.getRows();
		Map<String,Object> retMap =  new HashMap<String, Object>();
		retMap.put("rows", rows);
		retMap.put("count", rows.size());
		return retMap;
	}
	
	
	public  List<LocalPlate> getLocalPlateByItemId(int itemId){
		return localPlateDao.findLocalPlateByItemId(itemId);
	}

	/**
	 * 根据参数查询Shop（单个）
	 * @param params
	 * @return
	 */
	public LocalShop getLocalShop(Map<String, Object> params) {
		return this.localShopDao.getLocalShop(params);
	}

	/**
	 * 新增localShop信息
	 * @param localShop
	 */
	public void saveLocalShop(LocalShop localShop) {
		this.localShopDao.save(localShop);
	}

	/**
	 * 根据参数查询Shop（批量）
	 * @param params
	 * @return
	 */
	public List<LocalShop> getLocalShopList(Map<String, Object> params) {
		return this.localShopDao.getLocalShopList(params);
	}

	/**
	 * 统计LocalShop数量
	 * @param params
	 * @return
	 */
	public int getLocalShopCount(Map<String, Object> params) {
		return this.localShopDao.getLocalShopCount(params);
	}

	/**
	 * 更新LocalShop
	 * @param localShop
	 */
	public void updateLocalShop(LocalShop localShop) {
		this.localShopDao.updateLocalShop(localShop);
	}

	/**
	 * 参数查询LocalItem（单个）
	 * @return
	 */
	public LocalItem getLocalItemById(int id) {
		return this.localItemDao.getLocalItemById(id);
	}

	/**
	 * 新增LocalItem
	 * @param localItem
	 */
	public void saveLocalItem(LocalItem localItem) {
		this.localItemDao.save(localItem);
	}

	/**
	 * 参数查询LocalItem（批量）
	 * @param params
	 * @return
	 */
	public List<LocalItem> getLocalItemList(Map<String, Object> params) {
		return this.localItemDao.getLocalItemList(params);
	}

	/**
	 * 统计LocalItem的数量
	 * @param params
	 * @return
	 */
	public int getLocalItemCount(Map<String, Object> params) {
		return this.localItemDao.getLocalItemCount(params);
	}

	/**
	 * 更新LocalItem
	 * @param localItem
	 */
	public void updateLocalItem(LocalItem localItem) {
		this.localItemDao.updateLocalItem(localItem);
	}

	public List<LocalItem> findLocalItemByPage(Map<String, Object> params, int page, int rows) {
		int start=(page-1)*rows;
		int offset=rows;
		params.put("offset", offset);
		params.put("start", start);
		List<LocalItem> localItems = this.localItemDao.getLocalItemList(params);
		return localItems;
	}

	public void deleteLocalItemById(String id) {
		this.localItemDao.delete(id);
	}

	public LocalPlate getLocalPlateByFastCode(String fastCode) {
		// TODO Auto-generated method stub
		return this.localPlateDao.findLocalPlateByFastCode(fastCode);
	}
	
	public void saveBatch(LocalBatch batch) {
		// TODO Auto-generated method stub
		this.localBatchDao.save(batch);
	}

	public List<LocalBatch> findLocalBatchList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.localBatchDao.findLocalBatchList(params);
	}

	public LocalBatch findLocalBatchById(int id) {
		// TODO Auto-generated method stub
		return this.localBatchDao.findLocalBatchById(id);
	}

	public void updateLocalBatch(LocalBatch batch) {
		// TODO Auto-generated method stub
		this.localBatchDao.update(batch);
	}

	public void saveOperateRecord(LocalOperateRecord record) {
		// TODO Auto-generated method stub
		this.operateRecordDao.save(record);
	}
	
	public List<Map<String, Object>> findLocalPlateList(Map<String, Object> params, int page, int rows) {
		int start=(page-1)*rows;
		int offset=rows;
		params.put("offset", offset);
		params.put("start", start);
		List<Map<String, Object>> results = this.localPlateDao.findLocalPlateList(params); 
		return results;
	}

	public int getTotal(Map<String, Object> params) {
		return this.localPlateDao.getTotal(params);
	}

	public List<Map<String, Object>> findLocalReport(Map<String, Object> params, int page, int rows) {
		int start=(page-1)*rows;
		int offset=rows;
		params.put("offset", offset);
		params.put("start", start);
		List<Map<String, Object>> results = this.localPlateDao.findLocalReport(params); 
		return results;
	}

	public List<Map<String, Object>> findLocalPlateRecord(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.localPlateRecordDao.findLocalPlateRecord(params);
	}

	public LocalPlate findLocalPlate(String id) {
		// TODO Auto-generated method stub
		return this.localPlateDao.findLocalPlate(Integer.parseInt(id));
	}

	public List<Map<String, Object>> findItemReport(Map<String, Object> p) {
		// TODO Auto-generated method stub
		return this.localPlateDao.findItemReport(p);
	}

	public int getBatchTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.localBatchDao.getTotal(params);
	}

	public LocalPlate findLocalPlateByBatchId(String batchId) {
		// TODO Auto-generated method stub
		return this.localPlateDao.findLocalPlateByBatchId(batchId);
	}

	public void updateBatchByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		this.localBatchDao.updateBatchByParams(params);
	}

	public List<Map<String, Object>> findPCLocalPlate(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.localPlateDao.findPCLocalPlate(params);
	}

}
