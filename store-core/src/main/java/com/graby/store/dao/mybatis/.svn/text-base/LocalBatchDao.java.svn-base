package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.local.LocalBatch;

@MyBatisRepository
public interface LocalBatchDao {

	void save(LocalBatch batch);

	List<LocalBatch> findLocalBatchList(Map<String, Object> params);

	LocalBatch findLocalBatchById(int id);

	void update(LocalBatch batch);

	int getTotal(Map<String, Object> params);

	void updateBatchByParams(Map<String, Object> params);

}
