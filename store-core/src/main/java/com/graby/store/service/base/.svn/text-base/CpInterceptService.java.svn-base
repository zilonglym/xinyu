package com.graby.store.service.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graby.store.dao.mybatis.CpInterceptDao;
import com.graby.store.entity.CpIntercept;

@Component
public class CpInterceptService {
	
	private static Logger logger = LoggerFactory.getLogger(CpInterceptService.class);

	@Autowired
	private CpInterceptDao interceptDao;
	
	public List<CpIntercept> findCpInterceptList(Map<String, Object> params) {
		return this.interceptDao.findCpInterceptList(params);
	}

	public CpIntercept findCpInterceptByParam(Map<String, Object> params) {
		return this.interceptDao.findCpInterceptByParam(params);
	}

	public void update(CpIntercept intercept) {
		this.interceptDao.update(intercept);
	}

	public void save(CpIntercept iCpIntercept) {
		this.interceptDao.save(iCpIntercept);
	}

	public void delete(CpIntercept iCpIntercept) {
		this.interceptDao.delete(iCpIntercept);
	}

	public void deleteByParams(Map<String, Object> params) {
		this.interceptDao.deleteByParams(params);
	}


}
