package com.xinyu.dao.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.trade.DeliverRequirementsDao;
import com.xinyu.model.base.DeliverRequirements;

@Repository("deliverRequirementsDao")
public class DeliverRequirementsDaoImpl extends BaseDaoImpl implements DeliverRequirementsDao {
	
	private final String statement="com.xinyu.dao.deliverRequirementsDao.";

	@Override
	public void saveDeliverRequirements(DeliverRequirements obj) {
		// TODO Auto-generated method stub
		this.insert(statement+"insertDeliverRequirements", obj);
	}

	@Override
	public void updateDeliverRequirements(DeliverRequirements obj) {
		// TODO Auto-generated method stub
		this.update(statement+"updateDeliverRequirements", obj);
	}

	@Override
	public DeliverRequirements getDeliverRequirementsById(String id) {
		// TODO Auto-generated method stub
		return (DeliverRequirements) this.selectOne(statement+"getDeliverRequirementsById", id);
	}

	@Override
	public List<DeliverRequirements> getDeliverRequirementsByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<DeliverRequirements>) this.selectList(statement+"getDeliverRequirementsByList", params);
	}

	@Override
	public void deleteDeliverRequirements(String id) {
		// TODO Auto-generated method stub
		this.delete(this.statement+"deleteDeliverRequirements", id);
	}

}
