package com.xinyu.dao.order.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.order.StockOutOrderConfirmDao;
import com.xinyu.model.base.PackageInfo;
import com.xinyu.model.base.PackageItem;
import com.xinyu.model.order.StockOutOrder;
import com.xinyu.model.order.StockOutOrderConfirm;
import com.xinyu.model.order.child.StockInCheckItem;
import com.xinyu.model.order.child.StockInItemInventory;
import com.xinyu.model.order.child.StockInOrderItem;
import com.xinyu.model.order.child.StockOutCheckItem;
import com.xinyu.model.order.child.StockOutItemInventory;
import com.xinyu.model.order.child.StockOutOrderItem;
import com.xinyu.model.qm.StockoutConfirmEntry.packageItems;

@Repository("stockOutOrderConfirmDaoImpl")
public class StockOutOrderConfirmDaoImpl extends BaseDaoImpl implements StockOutOrderConfirmDao{

	private final String statement = "com.xinyu.dao.order.StockOutOrderConfirmDao.";
	
	private final String statementOutOrderItems =  "com.xinyu.dao.order.StockOutOrderItemDao.";
	
	private final String statementInventoryItems =  "com.xinyu.dao.order.StockOutItemInventoryDao.";
	
	private final String statementCheckItems =  "com.xinyu.dao.order.StockOutCheckItemDao.";
	
	private final String statementPackageInfo =  "com.xinyu.dao.base.PackageInfoDao.";
	
	private final String statementPackageItems =  "com.xinyu.dao.base.PackageItemDao.";
	
	@Override
	public void insertStockOutOrderConfirm(StockOutOrderConfirm stockOutOrderConfirm) {
		super.insert(this.statement+"insertStockOutOrderConfirm", stockOutOrderConfirm);
		List<StockOutCheckItem> checkItems = stockOutOrderConfirm.getCheckItems();
		for(StockOutCheckItem checkItem: checkItems){
			super.insert(this.statementCheckItems+"insertStockOutCheckItem", checkItem);
		}
		
		List<StockOutOrderItem> orderItems = stockOutOrderConfirm.getOrderItems();
		for(StockOutOrderItem  orderItem : orderItems){
			super.insert(this.statementOutOrderItems+"insertStockOutOrderItem", orderItem);
			List<StockOutItemInventory> items = orderItem.getItems();
			for(StockOutItemInventory inventoryItem: items ){
				super.insert(this.statementInventoryItems+"insertStockOutItemInventory", inventoryItem);
			}
		}
		
		List<PackageInfo> packageInfos = stockOutOrderConfirm.getPackageInfos();
		for(PackageInfo packageInfo:packageInfos){
			super.insert(this.statementPackageInfo+"insertPackageInfo", packageInfo);
			List<PackageItem> packageItems = packageInfo.getPackageItemItems();
			for(PackageItem packageItem:packageItems){
				super.insert(this.statementPackageItems+"insertPackageItem", packageItem);
			}
		}
	}

	@Override
	public List<StockOutOrderConfirm> getStockOutOrderConfirmList(Map<String, Object> params) {
		return (List<StockOutOrderConfirm>) super.selectList(this.statement+"getStockOutOrderConfirmList", params);
	}

	@Override
	public int getCheckItemNum(Map<String, Object> params) {
		return (Integer) super.selectOne(this.statementCheckItems+"getCheckItemSum",params);
	}

	@Override
	public List<StockOutOrderItem> getStockOutOrderItem(Map<String, Object> params) {
		return (List<StockOutOrderItem>) super.selectList(this.statementOutOrderItems+"getStockOutOrderItem", params);
	}

	@Override
	public List<StockOutItemInventory> getStockOutItemInventory(Map<String, Object> params) {
		return (List<StockOutItemInventory>) super.selectList(this.statementInventoryItems+"getStockOutItemInventory", params);
	}

}
