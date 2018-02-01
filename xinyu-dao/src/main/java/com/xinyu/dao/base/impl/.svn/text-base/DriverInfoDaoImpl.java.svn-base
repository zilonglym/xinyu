package com.xinyu.dao.base.impl;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.DriverInfoDao;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.model.base.DriverInfo;
@Repository("DriverInfoDaoImpl")
public class DriverInfoDaoImpl extends BaseDaoImpl implements DriverInfoDao {
	
	private final String statement = "com.xinyu.dao.base.DriverInfoDao.";
	
	
	public void saveDriverInfo(DriverInfo info){
		this.insert(statement+"insertDriverInfo",info);
	}
	public void updateDriverInfo(DriverInfo info){
		this.insert(statement+"updateDriverInfo",info);
	}
	public DriverInfo getDriverInfoById(String id){
		return (DriverInfo)this.selectOne(statement+"selectDriverInfoById",id);
	}
	public List<DriverInfo> getDriverInfoByList(Map<String, Object> params){
		return (List<DriverInfo>)this.selectList(statement+"selectDriverInfoByList",params);
	}
}
