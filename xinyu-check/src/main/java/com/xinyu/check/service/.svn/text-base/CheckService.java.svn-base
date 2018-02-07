package com.xinyu.check.service;

import java.util.List;
import java.util.Map;

import com.xinyu.check.model.SystemItem;

public interface CheckService extends BaseService{
	
	public Map<String,Object>  getItemInfoBybarCode(String barCode) ;
	
	Map<String,Object>  getItemInfoBybarCodes(String barCode);
	
	public Long getCountByDate(Map<String,Object> params);
	
	public Long getSuccessCountByDate(Map<String,Object> params);
	
	public Long getCheckSuccessCountByDate(Map<String,Object> params);
	
	public Long getCheckFailCountByDate(Map<String,Object> params);
	
	public   Map<String,Object>  checkTrade(String orderCode ,  String barCode,String stock,String cp,String userId,String personId,List<String> sysItemList,String sys);
	
	public   Map<String,Object>  checkTradenew(String orderCode ,  String barCode,String stock,String cp,String userId,String personId,List<String> sysItemList,String sys);
}
