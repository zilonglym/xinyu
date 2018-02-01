package com.xinyu.service.system.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.CentroDao;
import com.xinyu.model.base.Centro;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.system.CentroService;

@Service("centroServiceImpl")
public class CentroServiceImpl extends BaseServiceImpl implements CentroService {

	public static final Logger logger = Logger.getLogger(CentroServiceImpl.class);
	
	@Autowired
	private CentroDao centroDao;
	
	@Override
	public Centro getCentroById(String cu) {
		return this.centroDao.getCentroById(cu);
	}

	@Override
	public List<Centro> getCentroByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.centroDao.getCentroByList(params);
	}

}
