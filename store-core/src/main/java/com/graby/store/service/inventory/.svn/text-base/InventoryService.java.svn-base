package com.graby.store.service.inventory;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.InventoryDao;
import com.graby.store.dao.mybatis.InventoryRecordDao;
import com.graby.store.entity.ItemInventory;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.service.inventory.Accounts.Account;

/**
 * 库存信息
 * */
@Component
@Transactional
public class InventoryService {

	@Autowired
	private InventoryDao inventoryDao;
	@Autowired
	private QmInventoryService qmInventoryService;
	
	@Autowired
	private InventoryRecordDao  inventoryRecordDao;
	
	/**
	 * 条件查询库存
	 * @param map
	 * @return
	 * */
	public List<Map<String, Object>> getInventoryReport(Map<String,Object> map){
		return inventoryDao.getReport(map);
	}
	
	/**
	 * 条件查询库存出入库明细
	 * @param map
	 * @return
	 * */
	public List<Map<String, Object>> getInventoryRecordReport(Map<String,Object> map){
		return inventoryRecordDao.getReport(map);
	}

	/**
	 * 库存记账 同向增加，反向减少
	 * 
	 * @param centroId
	 *            仓库ID
	 * @param itemId
	 *            商品ID
	 * @param num
	 *            数量
	 * @param template
	 *            记账模板
	 */
	public void input(Long centroId, Long userId, Long itemId, long num, AccountTemplate template) {
		// 借方
		Account credit = template.getCredit();
		increase(centroId, userId, itemId, credit.getCode(), credit.getDirection().isCredit() ? num : -num);
		// 贷方
		Account dibit = template.getDibit();
		increase(centroId, userId, itemId, dibit.getCode(), dibit.getDirection().isDebit() ? num : -num);
	}

	/**
	 * 记账
	 * 
	 * @param centroId
	 * @param userId
	 * @param itemId
	 * @param entrys
	 */
	public void inputs(Long centroId, Long userId, Long itemId, AccountEntry[] entrys,String type) {
		for (AccountEntry e : entrys) {
			//input(centroId, userId, itemId, e.getNum(), e.getAccountTemplate());
			//修改库存记帐方式
			//默认在途入库 201
			Account credit = e.getAccountTemplate().getCredit();
			this.qmInventoryService.addInventory(centroId,
					userId,
					itemId,
					e.getNum(),
					credit.getCode(),
					type, "");
		}
	}
	
	/**
	 * 根据发货单记账
	 * @param order
	 * @param template
	 */
	public void input(ShipOrder order, AccountTemplate template) {
		for (ShipOrderDetail detail : order.getDetails()) {
			input(order.getCentroId(), order.getCreateUser().getId(),  detail.getItem().getId(), detail.getNum(),template);
		};
	}

	/**
	 * 记账
	 * 
	 * @param itemId
	 *            商品ID
	 * @param type
	 *            科目类型
	 * @param num
	 *            数量
	 */
	private void increase(Long centroId, Long userId, Long itemId, String account, long num) {
		int exist = inventoryDao.existAccount(centroId, itemId, account);
		if (exist == 0) {
			inventoryDao.insert(centroId, userId, itemId, account, num);
		} else {
			inventoryDao.increase(centroId, itemId, account, num);
		}
	}

	/**
	 * 库存统计
	 * @param centroId
	 * @param userId
	 * @return
	 */
	public List<Map<String, Long>> stat(Long centroId, Long userId) {
		return inventoryDao.stat(centroId, userId);
	}
	
	
	public List<Map<String, Object>> statPage(int page, int rows,Map<String, Object> params) {
		int start=(page-1)*rows;
		int offset=rows;
		params.put("start",start);
		params.put("offset",offset);
		return inventoryDao.statPage(params);
	}
	
	public Long statPageCount (Map<String, Object> params) {
		return inventoryDao.statPageCount(params);
	}
	
	/**
	 * 按科目统计库存值
	 * @param centroId
	 * @param itemId
	 * @param account
	 * @return
	 */
	public Long getValue(Long centroId, Long itemId, String account) {
		return inventoryDao.getValue(centroId, itemId, account);
	}
	
	/**
	 * 添加库存
	 * @param ItemInventory
	 * */
	public void insert(ItemInventory inventory){
		this.inventoryDao.insert(inventory.getCentro().getId(), inventory.getUser().getId(), 
				inventory.getItem().getId(), inventory.getAccount(), inventory.getNum());
	}

	/**
	 * 分页查询库存明细
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 * */
	public List<Map<String, Object>> getInventoryReportDetails(Map<String, Object> params, int page, int rows) {
		int start=(page-1)*rows;
		int offset=rows;
		params.put("start",start);
		params.put("offset",offset);
		List<Map<String,Object>> results=this.inventoryDao.getReport(params);
		return results;
	}

	/**
	 * 总记录数
	 * @param params
	 * @return int
	 * */
	public int getTotalResult(Map<String, Object> params) {
		return inventoryDao.getTotalResult(params);
	}

	/**
	 * 总记录数
	 * @param params
	 * @return int
	 * */
	public int getInventoryRecordTotal(Map<String, Object> params) {
		return inventoryRecordDao.getTotalResult(params);
	}

	/**
	 * 分页查询出入库明细
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 * */
	public List<Map<String, Object>> getInventoryRecordReportByPages(Map<String, Object> params, int page, int rows) {
		int start=(page-1)*rows;
		int offset=rows;
		params.put("start",start);
		params.put("offset",offset);
		List<Map<String,Object>> results=this.inventoryRecordDao.getReport(params);
		return results;
	}
	
	public ItemInventory getItemInventory(Long centroId, Long itemId, String account){
		return this.inventoryDao.getItemInventory(centroId, itemId, account);
	}
	/**
	 * 修改库存商品已使用数量
	 * @param centroId
	 * @param itemId
	 * @param account
	 * @param num
	 */
	public void updateUserNum(Long centroId, Long itemId, String account, long usenum){
		this.inventoryDao.updateUserNum(centroId, itemId, account, usenum);
	}
	
}
