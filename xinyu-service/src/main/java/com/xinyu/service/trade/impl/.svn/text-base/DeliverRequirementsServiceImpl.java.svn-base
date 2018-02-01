package com.xinyu.service.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.trade.DeliverRequirementsDao;
import com.xinyu.model.base.DeliverRequirements;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.trade.DeliverRequirementsService;

@Service
public class DeliverRequirementsServiceImpl extends BaseServiceImpl implements DeliverRequirementsService {

	@Autowired
	private DeliverRequirementsDao deliverRequirementsDao;
	@Override
	public void saveDeliverRequirements(DeliverRequirements obj) {
		// TODO Auto-generated method stub
		this.deliverRequirementsDao.saveDeliverRequirements(obj);
	}

	@Override
	public void updateDeliverRequirements(DeliverRequirements obj) {
		// TODO Auto-generated method stub
		this.deliverRequirementsDao.updateDeliverRequirements(obj);
	}

	@Override
	public DeliverRequirements getDeliverRequirementsById(String id) {
		// TODO Auto-generated method stub
		return this.deliverRequirementsDao.getDeliverRequirementsById(id);
	}

	@Override
	public List<DeliverRequirements> getDeliverRequirementsByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.deliverRequirementsDao.getDeliverRequirementsByList(params);
	}

}
