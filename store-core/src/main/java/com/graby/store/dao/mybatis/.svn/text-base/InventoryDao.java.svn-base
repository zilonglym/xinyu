package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.ItemInventory;

@MyBatisRepository
public interface InventoryDao {
		
	/**
	 * 插入一条库存明细
	 * @param centroId 仓库ID
	 * @param userId 用户ID
	 * @param itemId 商品ID
	 * @param account 科目
	 * @param num 数量
	 */
	void insert(Long centroId, Long userId, Long itemId, String account, long num);

	/**
	 * 增加库存科目数量
	 * @param centroId 仓库ID
	 * @param itemId 商品ID
	 * @param type 类型
	 * @param num 数量
	 */
	void increase(Long centroId, Long itemId, String account, long num);
	

	/**
	 * 查询是否存在科目
	 * @param centroId 仓库ID
	 * @param itemId 商品ID
	 * @param type 类型
	 * @return
	 */
	int existAccount(Long centroId, Long itemId, String account);
	
	
	ItemInventory getItemInventory(Long centroId, Long itemId, String account);
	
	int inItInventory(Long centroId, Long itemId, Long userId ,Long num);
	
	/**
	 * 库存统计
	 * @param centroId
	 * @param userId
	 * @return
	 */
	List<Map<String, Long>> stat(Long centroId, Long userId); 
	
	List<Map<String, Object>> statPage(Map<String, Object> params); 
	
	Long statPageCount(Map<String, Object> params); 
	
	/**
	 * 按科目统计库存值
	 * @param centroId
	 * @param itemId
	 * @param account
	 * @return
	 */
	Long getValue(Long centroId, Long itemId, String account);
	
	
	/**
	 * 库存查询
	 * c.shop_name as  shopname,  b.code as code ,b.title as  name ,a.num as num
	 * @param centroId
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> getReport(Map map);
	
	/**
	 * 总记录条数
	 * */
	int getTotalResult(Map<String, Object> params); 
	/**
	 * 修改商品库存已使用数量
	 * @param centroId
	 * @param itemId
	 * @param account
	 * @param num
	 */
	void updateUserNum(Long centroId, Long itemId, String account, long num);

}
