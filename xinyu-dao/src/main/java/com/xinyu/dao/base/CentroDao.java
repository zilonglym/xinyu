package com.xinyu.dao.base;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.base.Centro;

public interface CentroDao extends BaseDao{

	Centro getCentroById(String cu);
	
	List<Centro> getCentroByList(Map<String,Object> params);

}
