package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.CpIntercept;
import com.graby.store.service.base.CpInterceptService;

@RemotingService(serviceInterface = CpInterceptRemote.class, serviceUrl = "/cpIntercept.call")
public class CpInterceptRemoteImpl implements CpInterceptRemote{

	@Autowired
	private CpInterceptService interceptService;
	
	@Override
	public List<CpIntercept> findCpInterceptList(Map<String, Object> params) {
		return this.interceptService.findCpInterceptList(params);
	}

	@Override
	public CpIntercept findCpInterceptByParam(Map<String, Object> params) {
		return this.interceptService.findCpInterceptByParam(params);
	}

	@Override
	public void update(CpIntercept intercept) {
		this.interceptService.update(intercept);
	}

	@Override
	public void save(CpIntercept iCpIntercept) {
		this.interceptService.save(iCpIntercept);
	}

	@Override
	public void delete(CpIntercept iCpIntercept) {
		this.interceptService.delete(iCpIntercept);
	}

	@Override
	public void deleteByParams(Map<String, Object> params) {
		this.interceptService.deleteByParams(params);
	}

}
