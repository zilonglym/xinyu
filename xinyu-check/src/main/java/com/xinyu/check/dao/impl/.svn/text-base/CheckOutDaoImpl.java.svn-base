package com.xinyu.check.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.check.dao.CheckOutDao;
import com.xinyu.check.dao.base.BaseDaoImpl;
import com.xinyu.check.model.CheckOut;

/**
 * 出库记录Dao接口实现
 * */
@Repository("checkOutDaoImpl")
public class CheckOutDaoImpl extends BaseDaoImpl implements CheckOutDao{
	
	private final String statement = "com.xinyu.dao.checkOutDao.";

	/**
	 * 分页查询出库记录
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<CheckOut> findCheckOutPage(Map<String, Object> params, int page, int rows) {
		return super.selectList(this.statement+"findCheckOut", params, rows, page);
	}

	/**
	 * 符合条件的出库记录数
	 * @param params
	 * @return list
	 * */
	@Override
	public List<CheckOut> findCheckOuts(Map<String, Object> params) {
		return (List<CheckOut>) super.selectList(this.statement+"findCheckOut", params);
	}

	/**
	 * 添加出库记录
	 * @param checkOut
	 * */
	@Override
	public void insertCheckOut(CheckOut checkOut) {
		super.insert(this.statement+"save", checkOut);
	}

	@Override
	public int isExistsOrderCode(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Integer) this.selectOne(this.statement+"isExistsOrderCode", params);
	}

}
