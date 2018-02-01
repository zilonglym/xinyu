package com.xinyu.service.system.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.SnSampleDao;
import com.xinyu.model.base.SampleRule;
import com.xinyu.model.base.SnSample;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.system.SnSampleService;

@Service("snSampleService")
public class SnSampleServiceImpl extends BaseServiceImpl implements SnSampleService {

	@Autowired
	private SnSampleDao sampleDao;
	@Override
	public void insertSnSample(SnSample info) {
		// TODO Auto-generated method stub
		this.sampleDao.insertSnSample(info);
	}

	@Override
	public void updateSnSample(SnSample info) {
		// TODO Auto-generated method stub
		this.sampleDao.updateSnSample(info);
	}

	@Override
	public List<SnSample> getSnSampleList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.sampleDao.getSnSampleList(params);
	}

	@Override
	public void insertSnSampleRule(SampleRule info) {
		// TODO Auto-generated method stub
		this.sampleDao.insertSnSampleRule(info);
	}

	@Override
	public void updateSnSampleRule(SampleRule info) {
		// TODO Auto-generated method stub
		this.sampleDao.updateSnSampleRule(info);
		
	}

	@Override
	public List<SampleRule> getSampleRule(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.sampleDao.getSampleRule(params)	;
	}

	@Override
	public void insertSnSampleRuleList(List<SampleRule> list) {
		// TODO Auto-generated method stub
		for(int i=0;list!=null && i<list.size();i++){
			this.insertSnSampleRule(list.get(i));
		}
	}

}
