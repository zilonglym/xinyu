package com.xinyu.service.trade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.trade.WmsConsignOrderPackageRequirementDao;
import com.xinyu.model.base.WmsConsignOrderPackageRequirement;
import com.xinyu.service.common.BaseService;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.trade.WmsConsignOrderPackageRequirementService;

@Service("wmsConsignOrderPackageRequirementService")
public class WmsConsignOrderPackageRequirementServiceImpl extends BaseServiceImpl implements WmsConsignOrderPackageRequirementService {

	@Autowired
	private WmsConsignOrderPackageRequirementDao wmsConsignOrderPackageRequirementDao;
	
	@Override
	public void insertWmsConsignOrderPackageRequirement(WmsConsignOrderPackageRequirement info) {
		// TODO Auto-generated method stub
		this.wmsConsignOrderPackageRequirementDao.insertWmsConsignOrderPackageRequirement(info);
	}

	@Override
	public void updateWmsConsignOrderPackageRequirement(WmsConsignOrderPackageRequirement info) {
		// TODO Auto-generated method stub
		this.wmsConsignOrderPackageRequirementDao.updateWmsConsignOrderPackageRequirement(info);
	}

	@Override
	public WmsConsignOrderPackageRequirement getWmsConsignOrderPackageRequirementById(String id) {
		// TODO Auto-generated method stub
		return this.wmsConsignOrderPackageRequirementDao.getWmsConsignOrderPackageRequirementById(id);
	}

}
