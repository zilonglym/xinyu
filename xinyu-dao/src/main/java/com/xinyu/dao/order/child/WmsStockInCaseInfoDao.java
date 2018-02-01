package com.xinyu.dao.order.child;
import java.util.List;
import java.util.Map;
import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.order.child.WmsStockInCaseInfo;
public interface WmsStockInCaseInfoDao extends BaseDao {
	 public void saveWmsStockInCaseInfo(WmsStockInCaseInfo info);
	 public void updateWmsStockInCaseInfo(WmsStockInCaseInfo info);
	 public WmsStockInCaseInfo getWmsStockInCaseInfoById(String id);
	 public List<WmsStockInCaseInfo> getWmsStockInCaseInfoByList(Map<String, Object> params);
}
