package com.xinyu.dao.base;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.SenderInfo;
/**
 * 
 * @author yangmin
 * 2017年4月27日
 *
 */
public interface SenderInfoDao extends BaseDao {
	
	public void save(SenderInfo info);
	
	public void update(SenderInfo info);
	
	public SenderInfo getSenderInfoById(String id);
	
	public List<SenderInfo> getSenderInfoList(Map<String,Object> params);

	public void deleteSenderInfo(String id);
}
