package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.TradeBatch;

@MyBatisRepository
public interface TradeBatchDao {
	
	void save(TradeBatch tradeBatch);

	TradeBatch getTradeBatchById(Long id);

	List<TradeBatch> getTradeBatch(Map<String, Object> params);

	/**
	 * trade_batch_id
	 * count
	 * @return
	 */
	List<Map<String, Object>> getCompleteIds();
	
	void updateComplete(Map<String, Object> params);
	
	List<TradeBatch> findTradeBatchByUserId(Long userId);
	TradeBatch findTradeBatchById(Long id);
}
