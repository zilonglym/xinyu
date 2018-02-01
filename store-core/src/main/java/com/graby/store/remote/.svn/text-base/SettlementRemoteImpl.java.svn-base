package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.Settlement;
import com.graby.store.service.finance.SettlementService;

/**
 * 公司结算记录
 * */
@RemotingService(serviceInterface = SettlementRemote.class, serviceUrl = "/settlement.call")
public class SettlementRemoteImpl implements SettlementRemote{
	
	@Autowired
	private SettlementService settlemenService;
	
	/**
	 * 结算记录条件查询（单个）
	 * @param params
	 * @return Settlement
	 * */
	@Override
	public Settlement findSettlemnetByParam(Map<String, Object> params) {
		return this.settlemenService.findSettlemnetByParam(params);
	}
	
	/**
	 * 结算记录条件查询（多个分页）
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<Settlement> findSettlemnets(Map<String, Object> params,int page,int rows) {
		return this.settlemenService.findSettlemnets(params,page,rows);
	}
	
	/**
	 * 结算记录总数
	 * @param params
	 * @return int
	 * */
	@Override
	public int getTotalResult(Map<String, Object> params) {
		return this.settlemenService.getTotalResult(params);
	}

	/**
	 * 更新结算记录
	 * @param Settlement
	 * */
	@Override
	public void update(Settlement settlement) {
		this.settlemenService.update(settlement);
	}
	
	/**
	 * 新增结算记录
	 * @param Settlement
	 * */
	@Override
	public void save(Settlement settlement) {
		this.settlemenService.save(settlement);
	}
	
	/**
	 * 结算记录条件查询（多个）
	 * @param params
	 * @return list
	 * */
	@Override
	public List<Settlement> getSettlemnets(Map<String, Object> params) {
		return this.settlemenService.getSettlemnets(params);
	}

}
