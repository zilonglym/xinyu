package com.xinyu.dao.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.report.ReportDao;


/**
 * 报表
 * */
@Repository("reportDaoImpl")
public class ReportDaoImpl extends BaseDaoImpl implements ReportDao{

	private final String statement = "com.xinyu.dao.report.ReportDao.";
	
	/**
	 * 发货统计
	 * @param map
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> findShipCount(Map<String, Object> params) {
		return (List<Map<String, Object>>) super.selectList(this.statement+"findShipCount", params);
	}

	/**
	 * 商品统计
	 * @param map
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> findItemCount(Map<String, Object> params) {
		return (List<Map<String, Object>>) super.selectList(this.statement+"findItemCount", params);
	}

	@Override
	public List<Map<String, Object>> findItemTotal(Map<String, Object> params) {
		return (List<Map<String, Object>>) super.selectList(this.statement+"findItemTotal", params);
	}

}
