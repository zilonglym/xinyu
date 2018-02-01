package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.CheckOut;
import com.graby.store.service.check.CheckService;


@RemotingService(serviceInterface = CheckRemote.class, serviceUrl = "/check.call")
public class CheckRemoteImpl implements CheckRemote {
	@Autowired
	private CheckService  checkService;
	@Override
	public Map<String,Object>  getItemInfoBybarCode(String barCode){
		return checkService.getItemInfoBybarCode(barCode);
	}
	
	@Override
	public Map<String,Object>  getItemInfoBybarCodes(String barCode){
		return checkService.getItemInfoBybarCodes(barCode);
	}
	
	@Override
	public Map<String,Object>  checkTrade(String orderCode,String barCode,String stock,String cp,String userId,String personId,List<String> sysItemList,String sys){
		return checkService.checkTrade(orderCode, barCode,stock,cp,userId,personId,sysItemList,sys);
	}
	
	/**
	 * 分页条件查询扫码记录
	 * @param barCode,orderCode,itemName,orderId,itemId,userId,status,q,startDate,endDate,start,offset
	 * @return list
	 * */
	@Override
	public List<CheckOut> findCheckOutByPage(int page, int rows, Map<String, Object> params) {
		return checkService.findCheckOutByPage(page, rows, params);
	}
	/**
	 * 条件查询扫码记录
	 * @param barCode,orderCode,itemName,orderId,itemId,userId,status,q,startDate,endDate,start,offset
	 * @return list
	 * */
	@Override
	public List<CheckOut> findCheckOut(Map<String, Object> params) {
		return checkService.findCheckOut(params);
	}
	/**
	 * 获取总记录数
	 * @param barCode,orderCode,itemName,orderId,itemId,userId,status,q,startDate,endDate
	 * @return int
	 * */
	@Override
	public int getTotalResult(Map<String, Object> params) {
		return checkService.getTotalResult(params);
	}
	/**
	 * 条件查询成功记录
	 * @param barCode,orderCode,itemName,orderId,itemId,userId,status,q,startDate,endDate
	 * @return list<map>
	 * */
	@Override
	public List<Map<String, Object>> findCheckOutByStatus(Map<String, Object> params) {
		return checkService.findCheckOutByStatus(params);
	}
	/**
	 * 按商品分组条件查询记录
	 * @param barCode,orderCode,itemName,orderId,itemId,userId,status,q,startDate,endDate
	 * @return list<map>
	 * */
	@Override
	public List<Map<String, Object>> findCheckOutByItem(Map<String, Object> params) {
		return checkService.findCheckOutByItem(params);
	}
	/**
	 * 按物流公司分组条件查询记录
	 * @param barCode,orderCode,itemName,orderId,itemId,userId,status,q,startDate,endDate
	 * @return list<map>
	 * */
	@Override
	public List<Map<String, Object>> findCheckOutByExpress(Map<String, Object> params) {
		return checkService.findCheckOutByExpress(params);
	}
	/**
	 * 更新checkout
	 * @param checkOut
	 * */
	@Override
	public void updateCheckOut(CheckOut checkOut) {
		checkService.updateCheckOut(checkOut);
	}

	@Override
	public void saveCheckOut(CheckOut checkOut) {
		checkService.saveCheckOut(checkOut);
	}

	@Override
	public int buildCheckOut(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return this.checkService.buildCheckOut(startDate, endDate);
	}
	/**
	 * 出库明细查询
	 * @param map
	 * @return list<map>
	 * */
	@Override
	public List<Map<String,Object>> findCheckOutDetail(Map<String, Object> params) {
		return this.checkService.findCheckOutDetail(params);
	}
	/**
	 * 未出库明细查询
	 * @param map
	 * @return list<map>
	 * */
	@Override
	public List<Map<String, Object>> sumTradeOuts(Map<String, Object> params) {
		return this.checkService.sumTradeOuts(params);
	}

	@Override
	public String checkHttpTrade(String url) {
		// TODO Auto-generated method stub
		return this.checkService.checkHttpTrade(url);
	}

	@Override
	public void saveCheckOutList(List<CheckOut> outList) {
		// TODO Auto-generated method stub
		this.checkService.saveCheckOutList(outList);
	}

}
