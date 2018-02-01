package com.xinyu.dao.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.base.DeliverRequirements;

public interface DeliverRequirementsDao extends BaseDao {

	public void saveDeliverRequirements(DeliverRequirements obj);
	
	public void updateDeliverRequirements(DeliverRequirements obj);
	
	public DeliverRequirements getDeliverRequirementsById(String id);
	
	public List<DeliverRequirements> getDeliverRequirementsByList(Map<String,Object> params);

	public void deleteDeliverRequirements(String id);
	
}
