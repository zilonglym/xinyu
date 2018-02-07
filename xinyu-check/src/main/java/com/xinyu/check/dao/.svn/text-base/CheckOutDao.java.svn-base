package com.xinyu.check.dao;

import java.util.List;
import java.util.Map;

import com.xinyu.check.dao.base.BaseDao;
import com.xinyu.check.model.CheckOut;

/**
 * 出库记录Dao接口
 * */
public interface CheckOutDao extends BaseDao{

	/**
	 * 分页查询出库记录
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	List<CheckOut> findCheckOutPage(Map<String, Object> params, int page, int rows);

	/**
	 * 查询符合条件的出库记录
	 * @param params
	 * @return list
	 * */
	List<CheckOut> findCheckOuts(Map<String, Object> params);

	/**
	 * 添加出库记录
	 * @param checkOut
	 * */
	void insertCheckOut(CheckOut checkOut);
	
	
	int isExistsOrderCode(Map<String,Object> params);

}
