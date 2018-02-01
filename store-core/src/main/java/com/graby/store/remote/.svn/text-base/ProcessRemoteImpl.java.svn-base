package com.graby.store.remote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.dao.mybatis.ProcessDao;
import com.graby.store.dao.mybatis.ProcessItemDao;
import com.graby.store.entity.StoreProcess;
import com.graby.store.entity.StoreProcessItem;
import com.graby.store.service.wms.ProcessService;

@RemotingService(serviceInterface = ProcessRemote.class, serviceUrl = "/process.call")
public class ProcessRemoteImpl implements ProcessRemote{
	@Autowired
	private ProcessService processService;
	
	@Override
	public List<StoreProcess> findProcessConfirm(){
		return processService.findProcessConfirm();
	}
	
	@Override
	public StoreProcess findById(Integer id){
		return processService.findById(id);
	}
	@Override
	public List<StoreProcessItem> findByparentId(Integer parentId){
		return processService.findByparentId(parentId);
	}
	
	public void saveProcessItem(StoreProcessItem item){
		processService.saveProcessItem(item);
	}

	@Override
	public void saveProcess(StoreProcess process) {
		// TODO Auto-generated method stub
		processService.saveProcess(process);
	}
	
}
