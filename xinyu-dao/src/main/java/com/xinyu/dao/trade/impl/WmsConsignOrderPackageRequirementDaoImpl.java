package com.xinyu.dao.trade.impl;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.trade.WmsConsignOrderPackageRequirementDao;
import com.xinyu.model.base.WmsConsignOrderPackageRequirement;

@Repository("wmsConsignOrderPackageRequirementDao")
public class WmsConsignOrderPackageRequirementDaoImpl extends BaseDaoImpl
		implements WmsConsignOrderPackageRequirementDao {

	private final String statement="com.xinyu.dao.WmsConsignOrderPackageRequirementDao.";
	
	@Override
	public void insertWmsConsignOrderPackageRequirement(WmsConsignOrderPackageRequirement info) {
		// TODO Auto-generated method stub
		this.insert(statement+"insertWmsConsignOrderPackageRequirement", info);
	}

	@Override
	public void updateWmsConsignOrderPackageRequirement(WmsConsignOrderPackageRequirement info) {
		// TODO Auto-generated method stub
		this.update(statement, info);
	}

	@Override
	public WmsConsignOrderPackageRequirement getWmsConsignOrderPackageRequirementById(String id) {
		// TODO Auto-generated method stub
		return (WmsConsignOrderPackageRequirement) this.selectOne(statement+"getWmsConsignOrderPackageRequirementById", id);
	}

	@Override
	public void deleteWmsConsignOrderItemPackageRequirement(String id) {
		// TODO Auto-generated method stub
		this.delete(this.statement+"deleteWmsConsignOrderItemPackageRequirement", id);
	}

}
