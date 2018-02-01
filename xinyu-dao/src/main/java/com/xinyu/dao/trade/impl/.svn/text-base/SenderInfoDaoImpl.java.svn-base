package com.xinyu.dao.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.SenderInfoDao;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.model.base.SenderInfo;

@Repository("senderInfoDaoImpl")
public class SenderInfoDaoImpl extends BaseDaoImpl implements SenderInfoDao {

	private final String statement="com.xinyu.dao.senderInfoDao.";
	
	@Override
	public void save(SenderInfo info) {
		// TODO Auto-generated method stub
		this.insert(statement+"insertSenderInfo", info);
	}

	@Override
	public void update(SenderInfo info) {
		// TODO Auto-generated method stub
		this.update(statement+"updateSenderInfo", info);
	}

	@Override
	public SenderInfo getSenderInfoById(String id) {
		// TODO Auto-generated method stub
		return (SenderInfo) this.selectOne(statement+"getSenderInfoById", id);
	}

	@Override
	public List<SenderInfo> getSenderInfoList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<SenderInfo>) this.selectList(statement+"getSenderInfoByList", params);
	}

	@Override
	public void deleteSenderInfo(String id) {
		// TODO Auto-generated method stub
		this.delete(this.statement+"deleteSenderInfo", id);
	}

}
