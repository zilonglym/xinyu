package com.xinyu.service.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.org.apache.regexp.internal.recompile;
import com.xinyu.dao.trade.ReceiverInfoDao;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.trade.ReceiverInfoService;

@Service("receiverInfoServiceImpl")
public class ReceiverInfoServiceImpl extends BaseServiceImpl implements ReceiverInfoService {

	@Autowired
	private ReceiverInfoDao receiverDao;
	
	@Override
	public void saveReceiverInfo(ReceiverInfo info) {
		// TODO Auto-generated method stub
		this.receiverDao.save(info);
	}

	@Override
	public void updateReceiverInfo(ReceiverInfo info) {
		// TODO Auto-generated method stub
		this.receiverDao.update(info);
	}

	@Override
	public ReceiverInfo getReceiverInfoById(String id) {
		// TODO Auto-generated method stub
		return this.receiverDao.getReceiverInfoById(id);
	}

	@Override
	public List<ReceiverInfo> getReceiverInfoByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.receiverDao.getReceiverInfoList(params);
	}

	/**
	 * 数据封装
	 * */
	@Override
	public Map<String, Object> buildData(ReceiverInfo receiverInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", receiverInfo.getId());
		map.put("receiverProvince", receiverInfo.getReceiverProvince()==null?"":receiverInfo.getReceiverProvince());
		map.put("receiverCity", receiverInfo.getReceiverCity()==null?"":receiverInfo.getReceiverCity());
		map.put("receiverArea", receiverInfo.getReceiverArea()==null?"":receiverInfo.getReceiverArea());
		map.put("receiveTown", receiverInfo.getReceiveTown()==null?"":receiverInfo.getReceiveTown());
		map.put("receiverAddress", receiverInfo.getReceiverAddress());
		map.put("receiverName", receiverInfo.getReceiverName());
		map.put("receiverNick", receiverInfo.getReceiverNick()==null?"":receiverInfo.getReceiverNick());
		map.put("receiverMobile", receiverInfo.getReceiverMobile()==null?"":receiverInfo.getReceiverMobile());
		map.put("receiverPhone", receiverInfo.getReceiverPhone()==null?"":receiverInfo.getReceiverPhone());
		return map;
	}

}
