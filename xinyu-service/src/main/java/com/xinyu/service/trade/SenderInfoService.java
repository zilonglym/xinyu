package com.xinyu.service.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.model.base.SenderInfo;
import com.xinyu.service.common.BaseService;

public interface SenderInfoService extends BaseService {

	public void save(SenderInfo info);

	public void update(SenderInfo info);

	public SenderInfo getSenderInfoById(String id);

	public List<SenderInfo> getSenderInfoList(Map<String, Object> params);
}
