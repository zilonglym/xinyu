package com.xinyu.service.system;

import java.util.List;
import java.util.Map;

import com.xinyu.service.common.BaseService;

public interface CheckService extends BaseService{

	Map<String, Object> getItemInfoBybarCode(String barCode);

	Map<String, Object> getItemInfoBybarCodes(String barCode);

	Map<String, Object> checkTrade(String orderCode, String barCode, String stock, String cp, String userId,
			String persinId, List<String> sysItemList, String string);

	int getCheckSuccessCountByDate(Map<String, Object> params);

	int getSuccessCountByDate(Map<String, Object> params);

	int getCheckFailCountByDate(Map<String, Object> params);

}
