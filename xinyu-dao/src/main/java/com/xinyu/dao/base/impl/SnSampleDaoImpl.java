package com.xinyu.dao.base.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.model.base.SampleRule;
import com.xinyu.model.base.SnSample;

@Repository("snSampleDao")
public class SnSampleDaoImpl extends BaseDaoImpl implements com.xinyu.dao.base.SnSampleDao {

	private final String statement="com.xinyu.dao.base.SnSampleDao.";
	private final String statementRule="com.xinyu.dao.base.SnSampleRuleDao.";
	
	@Override
	public void insertSnSample(SnSample info) {
		// TODO Auto-generated method stub
		this.insert(statement+"insertSnSample", info);
	}

	@Override
	public void updateSnSample(SnSample info) {
		// TODO Auto-generated method stub
		this.update(statement+"updateSnSample", info);
	}

	@Override
	public List<SnSample> getSnSampleList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<SnSample>) this.selectList(statement+"getSnSampleByList", params);
	}

	@Override
	public void insertSnSampleRule(SampleRule info) {
		// TODO Auto-generated method stub
		this.insert(statementRule+"insertSampleRule", info);
	}

	@Override
	public void updateSnSampleRule(SampleRule info) {
		// TODO Auto-generated method stub
		this.update(statementRule+"updateSampleRule", info);
	}

	@Override
	public List<SampleRule> getSampleRule(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<SampleRule>) this.selectList(statementRule+"getSampleRuleByList", params);
	}

}
