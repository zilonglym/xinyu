package com.xinyu.service.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.model.base.DeliverRequirements;
import com.xinyu.service.common.BaseService;

public interface DeliverRequirementsService extends BaseService {
	public void saveDeliverRequirements(DeliverRequirements obj);

	public void updateDeliverRequirements(DeliverRequirements obj);

	public DeliverRequirements getDeliverRequirementsById(String id);

	public List<DeliverRequirements> getDeliverRequirementsByList(Map<String, Object> params);

}
