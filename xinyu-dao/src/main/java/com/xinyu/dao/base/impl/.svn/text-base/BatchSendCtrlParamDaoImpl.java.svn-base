package com.xinyu.dao.base.impl;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.base.BatchSendCtrlParamDao;
import com.xinyu.model.base.BatchSendCtrlParam;
@Repository("BatchSendCtrlParamDaoImpl")
public class BatchSendCtrlParamDaoImpl extends BaseDaoImpl implements BatchSendCtrlParamDao {
	
	private final String statement = "com.xinyu.dao.base.BatchSendCtrlParamDao.";
	
	public void saveBatchSendCtrlParam(BatchSendCtrlParam info){
		this.insert(statement+"insertBatchSendCtrlParam",info);
	}
	
	public void updateBatchSendCtrlParam(BatchSendCtrlParam info){
		this.insert(statement+"updateBatchSendCtrlParam",info);
	}
	
	public BatchSendCtrlParam getBatchSendCtrlParamById(String id){
		return (BatchSendCtrlParam)this.selectOne(statement+"selectBatchSendCtrlParamById",id);
	}
	public List<BatchSendCtrlParam> getBatchSendCtrlParamByList(Map<String, Object> params){
		return (List<BatchSendCtrlParam>)this.selectList(statement+"selectBatchSendCtrlParamByList",params);
	}

	@Override
	public void deleteBatchSendCtrlParamById(String id) {
		this.delete(statement+"deleteBatchSendCtrlParamById", id);
	}
	
}
