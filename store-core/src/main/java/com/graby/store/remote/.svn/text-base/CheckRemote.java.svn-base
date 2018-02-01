package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.CheckOut;
import com.graby.store.entity.ShipOrder;

public interface CheckRemote {
	
	public Map<String,Object>  getItemInfoBybarCode(String barCode) ;
	
	Map<String,Object>  getItemInfoBybarCodes(String barCode);
	
	
	
	public Map<String,Object>  checkTrade(String orderCode,String barCode,String stock,String cp,String userId,String personId,List<String> sysItemList,String sys);
	 
	
	public String checkHttpTrade(String url);
	/**
	 * 分页条件查询扫码记录
	 * @param barCode,orderCode,itemName,orderId,itemId,userId,status,q,startDate,endDate,start,offset
	 * @return list
	 * */
	public List<CheckOut> findCheckOutByPage(int page,int rows,Map<String,Object> params);
	/**
	 * 条件查询扫码记录
	 * @param barCode,orderCode,itemName,orderId,itemId,userId,status,q,startDate,endDate,start,offset
	 * @return list
	 * */
	public List<CheckOut> findCheckOut(Map<String,Object> params);
	/**
	 * 获取总记录数
	 * @param params(barCode,orderCode,itemName,orderId,itemId,userId,status,q,startDate,endDate)
	 * @return int
	 * */
	public int getTotalResult(Map<String,Object> params);
	/**
	 * 条件查询成功记录
	 * @param status,orderCode,userId,startDate,endDate
	 * @return list<map>
	 * */
	public List<Map<String, Object>> findCheckOutByStatus(Map<String, Object> params);
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
	
	public void saveCheckOut(CheckOut checkOut);
	/**
	 * 出库记录快递单号重复去除
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int buildCheckOut(String startDate,String endDate);
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

	public void saveCheckOutList(List<CheckOut> outList);
	
}

