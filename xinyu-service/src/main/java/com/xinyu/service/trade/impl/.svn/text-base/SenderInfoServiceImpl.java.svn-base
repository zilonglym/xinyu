package com.xinyu.service.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.SenderInfoDao;
import com.xinyu.model.base.SenderInfo;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.trade.SenderInfoService;

@Service("senderInfoService")
public class SenderInfoServiceImpl extends BaseServiceImpl implements SenderInfoService {

	@Autowired
	private SenderInfoDao senderInfoDao;
	
	@Override
	public void save(SenderInfo info) {
		// TODO Auto-generated method stub
		this.senderInfoDao.save(info);
	}

	@Override
	public void update(SenderInfo info) {
		// TODO Auto-generated method stub
		this.senderInfoDao.update(info);
	}

	@Override
	public SenderInfo getSenderInfoById(String id) {
		// TODO Auto-generated method stub
		return this.senderInfoDao.getSenderInfoById(id);
	}

	@Override
	public List<SenderInfo> getSenderInfoList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.senderInfoDao.getSenderInfoList(params);
	}

}
