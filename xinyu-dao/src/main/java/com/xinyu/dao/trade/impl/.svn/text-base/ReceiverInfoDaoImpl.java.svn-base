package com.xinyu.dao.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.trade.ReceiverInfoDao;
import com.xinyu.model.base.ReceiverInfo;

@Repository("receiverInfoDaoImpl")
public class ReceiverInfoDaoImpl extends BaseDaoImpl implements ReceiverInfoDao{
	private final String statement="com.xinyu.dao.receiverInfoDao.";
	
	@Override
	public void save(ReceiverInfo info) {
		// TODO Auto-generated method stub
		this.insert(statement+"insertReceiverInfo", info);
	}

	@Override
	public void update(ReceiverInfo info) {
		// TODO Auto-generated method stub
		this.update(statement+"updateReceiverInfo", info);
	}

	@Override
	public ReceiverInfo getReceiverInfoById(String id) {
		// TODO Auto-generated method stub
		return (ReceiverInfo) this.selectOne(statement+"getReceiverInfoById", id);
	}

	@Override
	public List<ReceiverInfo> getReceiverInfoList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<ReceiverInfo>) this.selectList(statement+"getReceiverInfoByList", params);
	}

	@Override
	public void deleteReceiverInfo(String id) {
		this.delete(this.statement+"deleteReceiverInfo", id);
	}

	@Override
	public ReceiverInfo getOldReceiverInfoById(String id) {
		return (ReceiverInfo) this.selectOne(this.statement+"getOldReceiverInfoById", id);
	}

}
