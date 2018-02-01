package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.CpIntercept;

public interface CpInterceptRemote {

	List<CpIntercept> findCpInterceptList(Map<String, Object> params);

	CpIntercept findCpInterceptByParam(Map<String, Object> map);

	void update(CpIntercept intercept);

	void save(CpIntercept iCpIntercept);

	void delete(CpIntercept iCpIntercept);

	void deleteByParams(Map<String, Object> params);


}
