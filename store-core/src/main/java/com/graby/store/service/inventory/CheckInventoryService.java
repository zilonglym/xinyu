package com.graby.store.service.inventory;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.CheckInventoryDao;
import com.graby.store.entity.CheckInventory;
import com.graby.store.entity.CheckInventoryDetail;

@Component
@Transactional
public class CheckInventoryService {

	@Autowired
	private CheckInventoryDao checkInventoryDao;
	
	@Autowired
	private QmInventoryService  qmInventoryService;
	
	
	@Transactional
	public  void insert(CheckInventory  checkInventory){
		checkInventoryDao.save(checkInventory);
		List<CheckInventoryDetail> details = checkInventory.getDetails();
		for(int i =0, size = details.size();i<size;i++){
			CheckInventoryDetail checkInventoryDetail = details.get(i);
			checkInventoryDetail.setCheckInventory(checkInventory);
			checkInventoryDao.saveDetail(checkInventoryDetail);
		}
	}
	
	public  CheckInventory getCheckInventoryById(Long  id){
		
		return  checkInventoryDao.getCheckInventoryById(id);
	}
	
	public  List<CheckInventory> getCheckInventory(Map<String,Object> params){
		
		return checkInventoryDao.getCheckInventory(params);
	}
	public   List<CheckInventoryDetail> checkInventoryDetailbyId(Map<String,Object> params){
		
		return checkInventoryDao.checkInventoryDetailbyId(params);
	}
	
	@Transactional
	public   void updateCheckInventoryAudit(Map<String,Object> params){
		
		 CheckInventory checkInventory = checkInventoryDao.getCheckInventoryById(Long.valueOf(""+params.get("id")));
		 List<CheckInventoryDetail> details = checkInventoryDao.checkInventoryDetailbyId(params);
		 checkInventory.setDetails(details);
		 qmInventoryService.checkInventory(checkInventory);
		
		 checkInventoryDao.updateCheckInventoryAudit(params);
	}
	
}
