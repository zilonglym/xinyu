package com.xinyu.service.report;

import java.util.List;
import java.util.Map;

import com.xinyu.service.common.BaseService;

/**
 * 报表
 * */
public interface ReportService extends BaseService {

	/**
	 * 发货统计
	 * @param map
	 * @return list
	 * */
	List<Map<String, Object>> findShipCount(Map<String, Object> params);

	/**
	 * 数据封装
	 * @param itemCounts 
	 * */
	List<Map<String, Object>> buildListData(List<Map<String, Object>> ships, List<Map<String, Object>> itemCounts);

	/**
	 * 商品统计
	 * @param map
	 * @return list
	 * */
	List<Map<String, Object>> findItemCount(Map<String, Object> params);
	

	List<Map<String, Object>> findItemTotal(Map<String, Object> params);

}
