package com.xinyu.dao.base;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.system.Notice;

public interface NoticeDao extends BaseDao{

	void insert(Notice notice);

	void update(Notice notice);

	Notice findNoticeById(String id);
	
}
