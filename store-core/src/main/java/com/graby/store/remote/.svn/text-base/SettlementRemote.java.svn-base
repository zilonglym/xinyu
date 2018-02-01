package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.Settlement;

/**
 * 公司结算记录
 * */
public interface SettlementRemote {

	/**
	 * 结算记录条件查询（单个）
	 * @param params
	 * @return Settlement
	 * */
	public Settlement findSettlemnetByParam(Map<String,Object> params);
	/**
	 * 结算记录条件查询（多个分页）
	 * @param params
	 * @param rows
	 * @param page
	 * @return list
	 * */
	public List<Settlement> findSettlemnets(Map<String,Object> params,int page,int rows);
	/**
	 * 结算记录总数
	 * @param params
	 * @return int
	 * */
	public int getTotalResult(Map<String,Object> params);
	/**
	 * 更新结算记录
	 * @param Settlement
	 * */
	public void update(Settlement settlement);
	/**
	 * 新增结算记录
	 * @param Settlement
	 * */
	public void save(Settlement settlement);
	/**
	 * 结算记录条件查询（多个）
	 * @param params
	 * @return list
	 * */
	public List<Settlement> getSettlemnets(Map<String, Object> params);
}
