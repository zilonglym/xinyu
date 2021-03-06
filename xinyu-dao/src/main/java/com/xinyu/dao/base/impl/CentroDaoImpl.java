package com.xinyu.dao.base.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.CentroDao;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.model.base.Centro;

@Repository("centroDaoImpl")
public class CentroDaoImpl extends BaseDaoImpl implements CentroDao{

	@Override
	public Centro getCentroById(String cu) {
		Centro centro=new Centro();
		centro.generateId();
		return (Centro) super.selectOne("com.xinyu.dao.base.CentroDao.getCentroById", cu);
	}

	@Override
	public List<Centro> getCentroByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<Centro>) super.selectList("com.xinyu.dao.base.CentroDao.getCentroByList", params);
	}

}
