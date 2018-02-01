package com.xinyu.service.system;

import java.util.List;
import java.util.Map;

import com.xinyu.model.base.Centro;
import com.xinyu.service.common.BaseService;

public interface CentroService extends BaseService{

	Centro getCentroById(String cu);
	
	List<Centro> getCentroByList(Map<String,Object> params);

}
