package com.xinyu.service.qm;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinyu.dao.inventory.InventoryDao;
import com.xinyu.dao.trade.ShipOrderDao;
import com.xinyu.model.qm.EntryorderConfirmEntry;
import com.xinyu.service.system.ItemService;


/**
 * 奇门确认接口实现
 * @author shark_cj
 * QmSyncService  创建接口类
 * QmSyncQueryService 初始化
 *
 */
@Component
public class QmConfirmService{

	@Autowired
	private InventoryDao inventoryDao;
	
	@Autowired
	private ShipOrderDao shipOrderDao;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private QmInventoryService qmInventoryService;
	
	
	/**
	 * <pre>
	 * WMS调用的奇门API名称：taobao.qimen.entryorder.confirm
	 * 奇门调用ERP的API名称：entryorder.confirm
	 * 入库单确认 
	 * </pre>
	 * @param map
	 * @return
	 */
	public String entryOrderConfirm(Map<String,Object> map) throws Exception {
		
		List<Map<String,Object>>  mapDetail  =(List<Map<String,Object>>) map.get(EntryorderConfirmEntry.details);
		
		for(int i =0, size  =mapDetail.size();i<size;i++ ){
			
			Map<String, Object> map1 = mapDetail.get(i);
			//设置入库 单  数量
//			shipOrderDao.setSumEntryOrderDetail(Long.valueOf(""+map1.get("detailId")),Long.valueOf(""+map1.get("numStr")),Long.valueOf(""+map1.get("numStr")));
			
//			qmInventoryService.addInventory(Long.valueOf(1), Long.valueOf(1), Long.valueOf(""+map1.get("itemId")), Long.valueOf(""+map1.get("numStr")),Accounts.CODE_SALEABLE, "入库", ""+map.get("orderId"));
//			//写库存
//			if(inventoryDao.existAccount(Long.valueOf(1), Long.valueOf(""+map1.get("itemId")), null)==0){
//				
//				inventoryDao.insert(Long.valueOf(1), Long.valueOf(1), Long.valueOf(""+map1.get("itemId")), null, Long.valueOf(""+map1.get("numStr")));
//			}else{
//				inventoryDao.increase(Long.valueOf(1), Long.valueOf(""+map1.get("itemId")), null, Long.valueOf(""+map1.get("numStr")));
//			}
		}
		
		//设置局部入库确认
// 		shipOrderDao.setOrderStatus(Long.valueOf(""+map.get("orderId")), ""+map.get("status"));
		
		return "success";
	}

	
	
	/**
	 * <pre>
	 * WMS调用的奇门API名称：taobao.qimen.stockout.confirm
	 * 奇门调用ERP的API名称：stockout.confirm
	 * 出库确认单 
	 * </pre>
	 * @param map
	 * @return
	 */
	public String stockoutConfirm(Map<String, Object> map) throws Exception {
		
//		
//		List<Map<String,Object>>  mapDetail  =(List<Map<String,Object>>) map.get("stockout");
//		
//		for(int i =0, size  =mapDetail.size();i<size;i++ ){
//			
//			Map<String, Object> map1 = mapDetail.get(i);
//			
//			//设置入库 单  数量
//			shipOrderDao.setSumEntryOrderDetail(Long.valueOf(""+map1.get("detailId")),Long.valueOf(""+map1.get("numStr")),Long.valueOf(""+map1.get("numStr")));
//			
//			qmInventoryService.addInventory(Long.valueOf(1), Long.valueOf(1), Long.valueOf(""+map1.get("itemId")), Long.valueOf("-"+map1.get("numStr")),Accounts.CODE_SALEABLE, "出库", ""+map.get("orderId"));
//		}
//		//设置出库库确认
//		shipOrderDao.setOrderStatus(Long.valueOf(""+map.get("orderId")), ""+map.get("status"));

		return "success";
	}
	
	
	public String returnorderConfirm(Map<String, Object> map) throws Exception {
				
		return "success";
	}
	
}
