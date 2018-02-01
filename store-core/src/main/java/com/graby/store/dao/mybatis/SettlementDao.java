package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.Settlement;

/**
 * 结算记录统计
 * */
@MyBatisRepository
public interface SettlementDao {
	
	/**
	 * 结算记录条件查询（单个）
	 * @param params
	 * @return Settlement
	 * */
	public Settlement findSettlemnetByParam(Map<String,Object> params);
	
	/**
	 * 结算记录条件查询（多个）
	 * @param params
	 * @return list
	 * */
	public List<Settlement> findSettlemnets(Map<String,Object> params);
	
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
	
}
