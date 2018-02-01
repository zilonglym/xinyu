package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.CpIntercept;

@MyBatisRepository
public interface CpInterceptDao {

	List<CpIntercept> findCpInterceptList(Map<String, Object> params);

	CpIntercept findCpInterceptByParam(Map<String, Object> params);

	void save(CpIntercept intercept);

	void update(CpIntercept intercept);

	void delete(CpIntercept intercept);

	void deleteByParams(Map<String, Object> params);

}
