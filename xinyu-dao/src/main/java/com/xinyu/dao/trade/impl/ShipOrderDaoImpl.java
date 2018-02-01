package com.xinyu.dao.trade.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.trade.ShipOrderDao;
import com.xinyu.model.trade.ShipOrder;


/**
 * shipOrderDao实现
 * */
@Repository("shipOrderDaoImpl")
public class ShipOrderDaoImpl extends BaseDaoImpl implements ShipOrderDao {

	private final String statement="com.xinyu.dao.shipOrderDao.";
	
	/**
	 * 新建订单
	 * @param shipOrder
	 * */
	@Override
	public void insertShipOrder(ShipOrder shipOrder) {
		// TODO Auto-generated method stub
		if(shipOrder.getCreateTime()!=null){
			shipOrder.setSeq(shipOrder.getCreateTime().getTime());
		}
		this.insert(statement+"insertShipOrder", shipOrder);
	}

	/**
	 * 更新订单
	 * @param shipOrder
	 * */
	@Override
	public void updateShipOrder(ShipOrder shipOrder) {
		// TODO Auto-generated method stub
		this.update(statement+"updateShipOrder", shipOrder);
	}

	/**
	 * 根据Id查询订单
	 * @param id
	 * @return shipOrder
	 * */
	@Override
	public ShipOrder getShipOrderById(String id) {
		// TODO Auto-generated method stub
		return (ShipOrder) this.selectOne(statement+"getShipOrderById", id);
	}

	/**
	 * 条件查询shipOrder数据（非分页）
	 * @param params
	 * @return list
	 * */
	@Override
	public List<ShipOrder> getShipOrderByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<ShipOrder>) this.selectList(statement+"getShipOrderByList", params);
	}

	
	
	@Override
	public List<ShipOrder> getShipOrderListByALl(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return  (List<ShipOrder>) this.selectList(statement+"getShipOrderListByAll", params);
	}
	
	@Override
	public int getShipOrderListByAllCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Integer) this.selectOne(statement+"getShipOrderListByAllCount", params);
	}
	/**
	 * 条件查询shipOrder数据（分页）
	 * @param params
	 * @param pageNump
	 * @param pageSize
	 * @return list
	 * */
	@Override
	public List<ShipOrder> findShipOrderListByPage(Map<String, Object> params, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		int start = (pageNum - 1) * pageSize;
		params.put("pageSize", pageSize);
		params.put("pageNum", start);
		
		return (List<ShipOrder>) this.selectList(this.statement+"getShipOrderByList", params);	
	}
	
	/**
	 * 条件查询历史shipOrder数据（分页）
	 * @param params
	 * @param pageNump
	 * @param pageSize
	 * @return list
	 * */
	@Override
	public List<ShipOrder> findOLdShipOrderListByPage(Map<String, Object> params, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		int start = (pageNum - 1) * pageSize;
		params.put("pageSize", pageSize);
		params.put("pageNum", start);
		return (List<ShipOrder>) this.selectList(this.statement+"getOldShipOrderByList", params);	
	}

	/**
	 * 订单删除
	 * @param id
	 * */
	@Override
	public void deleteShipOrder(String id) {
		// TODO Auto-generated method stub
		this.delete(this.statement+"deleteShipOrder", id);
	}

	@Override
	public void updateShipOrderStatus(Map<String, Object> params) {
		// TODO Auto-generated method stub
		this.update(statement+"updateShipOrderStatus", params);
	}

	@Override
	public void updateShipOrderByWaybill(Map<String, String> params) {
		// TODO Auto-generated method stub
		this.update(this.statement+"updateShipOrderByWaybill", params);
	}

	@Override
	public int getShipOrderTotal(Map<String, Object> params) {
		// getShipOrderByCount
		return (Integer) this.selectOne(statement+"getShipOrderByCount", params);	
	}

	@Override
	public int getOldShipOrderTotal(Map<String, Object> params) {
		return (Integer) this.selectOne(statement+"getOldShipOrderByCount", params);	
	}

	@Override
	public int isHaveOrder(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Integer) this.selectOne(statement+"isHaveOrder", params);
	}

	@Override
	public void updateExpressOrderNo(Map<String, Object> params) {
		// TODO Auto-generated method stub
		this.update(this.statement+"updateExpressOrderNo", params);
	}

	@Override
	public List<Map<String, Object>> getShipOrderItemByWorkGroup(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) this.selectList(statement+"getShipOrderItemByWorkGroup",params);
	}

	@Override
	public ShipOrder getShipOrderByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (ShipOrder) this.selectOne(statement+"getShipOrderByParams", params);
	}

	@Override
	public ShipOrder getOldShipOrderById(String id) {
		return (ShipOrder) this.selectOne(this.statement+"getOldShipOrderById", id);
	}

	@Override
	public List<ShipOrder> findTmsNotExsist(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<ShipOrder>) this.selectList(this.statement+"findTmsNotExsist", params);
	}
	@Override
	public List<Map<String, Object>> findStoreOrderList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) this.selectList(this.statement+"findStoreOrderList", params);
	}

}
