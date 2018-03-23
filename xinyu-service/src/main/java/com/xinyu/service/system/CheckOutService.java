package com.xinyu.service.system;

import java.util.List;
import java.util.Map;

import com.xinyu.model.trade.CheckOut;
import com.xinyu.service.common.BaseService;

/**
 * 出库记录业务接口
 * */
public interface CheckOutService extends BaseService{

	/**
	 * 分页查询出库记录
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	List<CheckOut> findCheckOutPage(Map<String, Object> params, int page, int rows);

	/**
	 * 符合条件的出库记录数
	 * @param params
	 * @return int
	 * */
	int getTotal(Map<String, Object> params);

	
	/**
	 * checkOut数据重组
	 * @param list
	 * @return list
	 * */
	List<Map<String, Object>> buildData(List<CheckOut> checkOuts);
	
	void insertCheckOut(CheckOut checkOut);
	
	/**
	 * 未出库统计
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Map<String, Object>> findNotExist(Map<String, Object> params, int page, int rows);

	int findNotExistCount(Map<String, Object> params);

}
