package com.xinyu.dao.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.trade.CheckOutDao;
import com.xinyu.model.trade.CheckOut;

/**
 * 出库记录Dao接口实现
 * */
@Repository("checkOutDaoImpl")
public class CheckOutDaoImpl extends BaseDaoImpl implements CheckOutDao{
	
	private final String statement = "com.xinyu.dao.trade.CheckOutDao.";

	/**
	 * 符合条件的出库记录数
	 * @param params
	 * @return list
	 * */
	@Override
	public List<CheckOut> findCheckOuts(Map<String, Object> params) {
		return (List<CheckOut>) super.selectList(this.statement+"findCheckOuts", params);
	}

	/**
	 * 添加出库记录
	 * @param checkOut
	 * */
	@Override
	public void insertCheckOut(CheckOut checkOut) {
		super.insert(this.statement+"insertCheckOut", checkOut);
	}

	@Override
	public int getTotal(Map<String, Object> params) {
		return (Integer) super.selectOne(this.statement+"getTotal", params);
	}

	@Override
	public int findNotExistCount(Map<String, Object> params) {
		return (Integer) super.selectOne(this.statement+"findNotExistCount", params);
	}

	@Override
	public List<Map<String, Object>> findNotExist(Map<String, Object> params) {
		return (List<Map<String, Object>>) super.selectList(this.statement+"findNotExist", params);
	}

}
