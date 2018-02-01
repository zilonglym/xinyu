package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;

@MyBatisRepository
public interface InventoryRecordDao {
	
	/**
	 * 插入一条库存台账明细
	 * @param centroId 仓库ID
	 * @param userId 用户ID
	 * @param itemId 用户ID
	 * @param num  数量
	 * @param type  类型
	 * @param description  描述
	 */
	void insert(Long centroId, Long userId, Long itemId,long num,String type, String description);
	
	Long existOrderNo(Map<String,Object> params);
	
	/**
	 * 选出某商品  的出库记录
	 * @param params
	 */
	void  deleteOrderNo(Map<String,Object> params);
	/**
	 * 及时出入记录
	 * shopname,   code , name ,  anum, bnum , sum
	 * @param centroId
	 * @param userId
	 * @param  startDate
	 * @param  endDate
	 * @return
	 */
	List<Map<String, Object>> getReport(Map map);

	/**
	 * 总记录条数
	 * */
	int getTotalResult(Map<String, Object> params); 
}
