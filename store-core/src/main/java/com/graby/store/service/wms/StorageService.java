package com.graby.store.service.wms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.CentroItemDao;
import com.graby.store.dao.mybatis.StorageDao;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.Storage;
import com.graby.store.entity.StorageItem;
import com.graby.store.entity.enums.StorageStatusEnums;
import com.graby.store.util.Seqence;

/**
 * 入库单操作service
 * @author yangmin
 *
 */
@Component
@Transactional
public class StorageService {

	@Autowired
	private StorageDao storageDao;
	
	@Autowired
	private CentroItemDao centroItemDao;
	
	
	public List<Storage> getStorages(Map<String,Object> params){
		return storageDao.getStorages(params);
	}
	
	
	
	/**
	 * 查询所有的入库单
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page<Storage> findStoragesList(int pageNo, int pageSize, Map<String, Object> params) {
		int start = (pageNo-1)*pageSize;
		int offset = pageSize;
		params.put("start", start);
		params.put("offset",offset);
		params.remove("rownum");
		List<Storage> orders =  storageDao.getStorages(params);
		long count = orders.size();
		PageRequest pageable = new PageRequest(pageNo-1, offset);
		Page<Storage> page = new PageImpl<Storage>(orders, pageable, count);		
		return page;	
	}
	
	public Long findStoragesCount( Map<String, Object> params) {
		return storageDao.getStoragesCount(params);
	}
	
	/**
	 * 入库单保存
	 * @param storage
	 */
	public void save(Storage storage){
		//
		if(storage==null){
			return;
		}
		if(storage.getId()==null){
			//新增
			storage.setOrderNo(geneOrderno());
			//storage.setStatus(StorageStatusEnums.ENTRY_WAIT_STORAGE_RECEIVED);
			this.storageDao.save(storage);
			for(int i=0;storage.getItems()!=null && i<storage.getItems().size();i++){
				StorageItem item=storage.getItems().get(i);
				item.setStorage(storage);
				this.storageDao.saveItems(item);
			}
		}
		
	}
	
	
	
	
	private String geneOrderno() {
		StringBuffer number = new StringBuffer();
		number.append("R");
		Date today = new Date();
		number.append(formateDate(today, "yyyyMMdd"));
		String max = Seqence.getInstance().next();
		number.append(max);
		return number.toString();
	}
	
	private String formateDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	

	/**
	 * 单据明细查询
	 * */
	public List<StorageItem> storageDetailbyId(Map<String, Object> params){
		return storageDao.storageDetailbyId(params);
	}

	public List<StorageItem> getStorageItemList(Map<String,Object> params){
		return this.storageDao.getStorageItemList(params);
	}
	
	public Storage getStorageById(Long id){
		return this.storageDao.getStorageById(id);
	}

	
}
