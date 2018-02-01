package com.graby.store.service.wms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.graby.store.entity.Process;
import com.graby.store.dao.mybatis.ProcessDao;
import com.graby.store.dao.mybatis.ProcessItemDao;
import com.graby.store.entity.ProcessItem;

@Component
@Transactional(readOnly = true)
public class ProcessService {
	
	@Autowired
	private ProcessDao processDap;
	@Autowired
	private ProcessItemDao itemDao;
	
	public void saveProcess(Process process){
		this.processDap.insert(process);
	}
	
	public void saveProcessItem(ProcessItem item){
		this.itemDao.insert(item);
	}
	
	public void batchSaveProcessItem(List<ProcessItem> itemList){
		for(int i=0;itemList!=null && i<itemList.size();i++){
			this.saveProcessItem(itemList.get(i));
		}
	}

}
