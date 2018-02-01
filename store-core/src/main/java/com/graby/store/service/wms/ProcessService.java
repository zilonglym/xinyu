package com.graby.store.service.wms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.graby.store.entity.StoreProcess;
import com.graby.store.dao.mybatis.ProcessDao;
import com.graby.store.dao.mybatis.ProcessItemDao;
import com.graby.store.entity.StoreProcessItem;

@Component
@Transactional
public class ProcessService {
	
	@Autowired
	private ProcessDao processDap;
	@Autowired
	private ProcessItemDao itemDao;
	
	public void saveProcess(StoreProcess process){
		this.processDap.insert(process);
	}
	
	public List<StoreProcess> findProcessConfirm(){
		return this.processDap.findProcessConfirm();
	}
	
	
	public StoreProcess findById(Integer id){
		return this.processDap.selectById(id);
	}
	
	public List<StoreProcessItem> findByparentId(Integer parentId){
		return this.itemDao.selectByparentId(parentId);
	}
	
	public void saveProcessItem(StoreProcessItem item){
		this.itemDao.insert(item);
	}
	
	public void batchSaveProcessItem(List<StoreProcessItem> itemList){
		for(int i=0;itemList!=null && i<itemList.size();i++){
			this.saveProcessItem(itemList.get(i));
		}
	}

}
