package com.xinyu.dao.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.trade.CheckOut;

/**
 * 出库记录Dao接口
 * */
public interface CheckOutDao extends BaseDao{


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

	
	int getTotal(Map<String, Object> params);
	
	

}
