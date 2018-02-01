package com.xinyu.service.report.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.UserDao;
import com.xinyu.dao.report.ReportDao;
import com.xinyu.model.base.User;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.report.ReportService;

/**
 * 报表
 * */
@Service("reportServiceImpl")
public class ReportServiceImpl extends BaseServiceImpl implements ReportService{

	@Autowired 
	private ReportDao reportDao;
	
	@Autowired 
	private UserDao userDao;
	
	
	/**
	 * 发货统计
	 * @param map
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> findShipCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.reportDao.findShipCount(params);
	}

	/**
	 * 数据封装
	 * */
	@Override
	public List<Map<String, Object>> buildListData(List<Map<String, Object>> ships, List<Map<String, Object>> itemCounts) {
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> map:ships){
			Map<String, Object> retMap = new HashMap<String, Object>();
			String userId = "" + map.get("userId");
			User user = this.userDao.getUserById(userId);
			retMap.put("userName", user.getSubscriberName());
			retMap.put("userId", userId);
			retMap.put("num", map.get("num"));
			for(Map<String, Object> itemMap:itemCounts){
//				System.err.println(itemCounts);
				if (itemMap.get("userId").equals(userId)) {
					retMap.put("sum", itemMap.get("sum"));
				}
			}
			results.add(retMap);
		}
		return results;
	}

	/**
	 * 商品统计
	 * @param map
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> findItemCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.reportDao.findItemCount(params);
	}

	@Override
	public List<Map<String, Object>> findItemTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.reportDao.findItemTotal(params);
	}
}
