package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.CheckOut;

@MyBatisRepository
public interface CheckOutDao {
	/**
	 * 保存扫码流水记录
	 * @param checkOut
	 * */
	public void save(CheckOut checkOut);
	/**
	 * 条件查询扫码记录
	 * @param barCode,orderCode,itemName,orderId,itemId,userId,status,q,startDate,endDate,start,offset
	 * @return list
	 * */
	public List<CheckOut> findCheckOut(Map<String,Object> params);
	
	public int isExistsOrderCode(Map<String,Object> params);
	/**
	 * 获取总记录数
	 * @param barCode,orderCode,itemName,orderId,itemId,userId,status,q,startDate,endDate
	 * @return int
	 * */
	public int getTotalResult(Map<String,Object> params);
	/**
	 * 条件查询成功记录
	 * @param status,orderCode,userId,startDate,endDate
	 * @return list<map>
	 * */
	public List<Map<String,Object>> findCheckOutByStatus(Map<String,Object> params);
	/**
	 * 按商品分组条件查询记录
	 * @param status,orderCode,userId,startDate,endDate
	 * @return list<map>
	 * */
	public List<Map<String,Object>> findCheckOutByItem(Map<String,Object> params);
	/**
	 * 按物流公司分组条件查询记录
	 * @param status,orderCode,userId,startDate,endDate
	 * @return list<map>
	 * */
	public List<Map<String,Object>> findCheckOutByExpress(Map<String,Object> params);
	
	/**
	 * 更新checkout
	 * @param checkOut
	 * */
	public void updateCheckOut(CheckOut checkOut);
	
	public List<Map<String,Object>> findCheckOuttoMany(Map<String,Object> params);
	
	public List<CheckOut> findCheckbyOrderCode(String barCode);
	
	public void deleteCheckOrderById(String id);
	/**
	 * 出库明细查询
	 * @param map
	 * @return list<map>
	 * */
	public List<Map<String,Object>> findCheckOutDetail(Map<String, Object> params);
	/**
	 * 未出库明细查询
	 * @param map
	 * @return list<map>
	 * */
	public List<Map<String,Object>> sumTradeOuts(Map<String, Object> params);
}
