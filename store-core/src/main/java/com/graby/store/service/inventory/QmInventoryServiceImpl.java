package com.graby.store.service.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graby.store.dao.mybatis.InventoryDao;
import com.graby.store.dao.mybatis.InventoryRecordDao;
import com.graby.store.dao.mybatis.ItemDao;
import com.graby.store.dao.mybatis.ShipOrderDao;
import com.graby.store.entity.CheckInventory;
import com.graby.store.entity.CheckInventoryDetail;
import com.graby.store.entity.Item;
import com.graby.store.entity.ItemInventory;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.service.inventory.Accounts.Account;


@Component
public class QmInventoryServiceImpl implements QmInventoryService {

	
	@Autowired
	private InventoryDao inventoryDao;
	@Autowired
	private ShipOrderDao shipOrderDao;
	
	@Autowired
	private InventoryRecordDao  inventoryRecordDao;
	
	@Autowired
	private ItemDao itemDao;
	
	
	public Long  existOrderNo(Map<String,Object> params){
		return inventoryRecordDao.existOrderNo(params);
	}
			
	public void  deleteOrderNo(Map<String,Object> params){
		inventoryRecordDao.deleteOrderNo(params);
	}
	
	/**
	 * 添加库存
	 * @param centroId
	 * @param userId
	 * @param itemId
	 * @param num
	 * @param type  类型，  出库？ 入库？
	 * @param description   描述
	 */
	public void  addInventory(Long centroId,Long userId, Long itemId ,Long num,String account,String type,String description){
		
		System.err.println("库存修改:"+centroId+"|"+userId+"|"+account+"|"+num);
		if(inventoryDao.existAccount(centroId, itemId, account)==0){
			System.err.println("insert");
			inventoryDao.insert(centroId, userId, itemId, account, num);
		}else{
			System.err.println("update");
			inventoryDao.increase(centroId, itemId, account, num);
		}
		inventoryRecordDao.insert(centroId, userId, itemId, num, type, description);
	}
	
	
	public void  adminAddInventory(Long centroId,Long userId, Long itemId ,Long num,String type,String description){
		
		if(inventoryDao.existAccount(centroId, itemId,  Accounts.CODE_SALEABLE)==0){
			System.err.println("insert");
			inventoryDao.insert(centroId, userId, itemId,  Accounts.CODE_SALEABLE, num);
		}else{
			System.err.println("update");
			inventoryDao.increase(centroId, itemId,  Accounts.CODE_SALEABLE, num);
		}
		inventoryRecordDao.insert(centroId, userId, itemId, num, type, description);
	}
	
	
	
	
	
	public List<Map<String,Object>> inItInventory(List<Map<String,Object>>  params){
		
		if(params!=null   &&  params.size()>0){
			System.out.println("库存初始化开始");
			for(Map<String,Object> map  :  params){				
//				itemParams.put("userId", userId);
//	    		itemParams.put("barCode", barCodeStrTemp.trim());
//	    		Item item  =
				Item item =  null;
				if((""+map.get("userId")).equals("30")){
					item =  itemDao.findItmeByBarCodeAndUserId(map);
				}else{
					//item = itemDao.getItemByCode(map);
					item=itemDao.getItemBySkuAndCode(map);
				}
				if(item!=null){
					System.out.println("item:"+item.getId()+"-"+item.getCode());
					Long userId = Long.valueOf(""+ map.get("userId"));
					Long num =  Long.valueOf(""+ map.get("num"));
					Long centroId =  Long.valueOf(""+ map.get("centroId"));
					if(inventoryDao.existAccount(centroId, item.getId(), Accounts.CODE_SALEABLE)==0){
						inventoryDao.insert(centroId, userId, item.getId(), Accounts.CODE_SALEABLE, num);
					}else{
						inventoryDao.inItInventory(centroId, item.getId(), userId, num);
					}
					inventoryRecordDao.insert(centroId, userId, item.getId(), num, "库存初始化", "库存初始化");
				}else {
					System.err.println("item:"+map.get("code")+" 无法匹配！");
				}
			}
		}
		return null;
	}
	
	/**
	 * 盘点库存
	 * @param checkInventory
	 */
	public void checkInventory(CheckInventory checkInventory){
		
		List<CheckInventoryDetail> details = checkInventory.getDetails();
		Long centroId = checkInventory.getCentro().getId();
		Long userId = checkInventory.getPerson().getId();
		for(int i =0,size  = details.size();i<size; i++){
			CheckInventoryDetail detail = details.get(i);
			Long num = Long.valueOf(""+ detail.getQuantity());
			ItemInventory itemInventory = inventoryDao.getItemInventory(centroId, detail.getItem().getId(), Accounts.CODE_SALEABLE);
			if(itemInventory!=null){
				inventoryDao.insert(centroId, userId, detail.getItem().getId(), Accounts.CODE_SALEABLE, num);
				inventoryRecordDao.insert(centroId, userId, detail.getItem().getId(),  itemInventory.getNum() - num , "库存盘点", "库存盘点");
			}else{
				inventoryDao.inItInventory(centroId, detail.getId(), userId, num);
				inventoryRecordDao.insert(centroId, userId, detail.getItem().getId(), num, "库存盘点", "库存盘点");
				
			}
			
		}
	}
	
	
	/**
	 * 审核出库
	 * @param order  出库 
	 * @param type  类型
	 * @param description  备注
	 */
	public void batchDelInventory(ShipOrder order, String type,String description) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("orderId", order.getId());
		List<ShipOrderDetail> detailList=this.shipOrderDao.getShipOrderDetailList(params);
		System.err.println("审核订单出库:"+order.getId()+",size:"+detailList.size());
		for (ShipOrderDetail detail : detailList) {
			this.addInventory(order.getCentroId(), order.getCreateUser().getId(),  detail.getItem().getId(), - detail.getNum(),Accounts.CODE_SALEABLE,type,order.getId()+description);
		};
	}
	
	
	/**
	 * 审核入库
	 * @param order  出库 
	 * @param type  类型
	 * @param description  备注
	 */
	public void batchAddInventory(ShipOrder order, String type,String description) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("orderId", order.getId());
		List<ShipOrderDetail> detailList=this.shipOrderDao.getShipOrderDetailList(params);
		for (ShipOrderDetail detail : detailList) {
			this.addInventory(order.getCentroId(), order.getCreateUser().getId(),  detail.getItem().getId(), detail.getNum(),Accounts.CODE_SALEABLE,type,order.getId()+description);
		};
	}
}
