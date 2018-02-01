package com.graby.store.dao.mybatis;

import java.util.List;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.Centro;

@MyBatisRepository
public interface CentroDao {
	
	/**
	 * 返回所有仓库
	 * @return
	 */
	List<Centro> findCentros();
	
	Centro findCentro(Long id);
	
	Centro findCentroById(String id);
	
	Centro findCentroByCode(String code);
	
	void saveCentro(Centro centro);
	
	void updateCentro(Centro centro);
	 
	Centro findCentrosByCity(String city);

}
