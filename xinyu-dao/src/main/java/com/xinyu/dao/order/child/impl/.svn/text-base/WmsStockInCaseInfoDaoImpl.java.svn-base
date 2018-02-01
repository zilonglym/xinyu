package com.xinyu.dao.order.child.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.order.child.WmsStockInCaseInfoDao;
import com.xinyu.model.order.child.WmsStockInCaseInfo;
import com.xinyu.model.order.child.WmsStockInCaseItem;

@Repository("WmsStockInCaseInfoDaoImpl")
public class WmsStockInCaseInfoDaoImpl extends BaseDaoImpl implements WmsStockInCaseInfoDao {
	
	private final String statement = "com.xinyu.dao.order.child.WmsStockInCaseInfoDao."; 
	private final String statementDetail = "com.xinyu.dao.order.child.WmsStockInCaseItemDao."; 
	
	public void saveWmsStockInCaseInfo(WmsStockInCaseInfo info){
		this.insert(statement+"insertWmsStockInCaseInfo",info);
		List<WmsStockInCaseItem> caseItemList = info.getCaseItemList();
		for(WmsStockInCaseItem caseItem  :   caseItemList){
			this.insert(statementDetail+"insertWmsStockInCaseItem", caseItem);
		}
	}
	
	public void updateWmsStockInCaseInfo(WmsStockInCaseInfo info){
		this.insert("com.xinyu.dao.order.child.updateWmsStockInCaseInfo",info);
	}
	
	public WmsStockInCaseInfo getWmsStockInCaseInfoById(String id){
		WmsStockInCaseInfo  stockInCaseInfo  =  (WmsStockInCaseInfo)this.selectOne(statement+"getWmsStockInCaseInfoById",id);
		Map<String, Object> params  = new HashMap<String, Object>(); 
		params.put("stockInCaseInfoId", id);
		List<WmsStockInCaseItem> itmes = (List<WmsStockInCaseItem>) this.selectList(statementDetail+"getWmsStockInCaseItemByIdList",params);
		stockInCaseInfo.setCaseItemList(itmes);
		return stockInCaseInfo;
	}
	
	public List<WmsStockInCaseInfo> getWmsStockInCaseInfoByList(Map<String, Object> params){
		List<WmsStockInCaseInfo> infoList = (List<WmsStockInCaseInfo>)this.selectList(statement+"getWmsStockInCaseInfoByList",params);
		if(infoList!=null &&  infoList.size()>0){
			for(WmsStockInCaseInfo  info :  infoList){
				params.clear();
				params.put("stockInCaseInfoId", info.getId());
				List<WmsStockInCaseItem> itmes = (List<WmsStockInCaseItem>) this.selectList(statementDetail+"getWmsStockInCaseItemByIdList",params);
				info.setCaseItemList(itmes);
			}
		}
		return infoList;
	}
}
