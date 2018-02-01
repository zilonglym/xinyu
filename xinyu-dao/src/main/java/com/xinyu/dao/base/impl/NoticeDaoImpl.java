package com.xinyu.dao.base.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.NoticeDao;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.model.base.User;
import com.xinyu.model.system.Notice;

@Repository("noticeDaoImpl")
public class NoticeDaoImpl extends BaseDaoImpl implements NoticeDao{
	
	private static final Logger logger = Logger.getLogger(NoticeDaoImpl.class);
	
	private final String statement = "com.xinyu.dao.base.NoticeDao.";
	
	@Override
	public void insert(Notice notice) {
		super.insert(statement+"insertNotice",notice);
	}

	@Override
	public void update(Notice notice) {
		super.update(statement+"updateNotice",notice);
	}

	@Override
	public Notice findNoticeById(String id) {
		User tempUser=new User();
		tempUser.generateId();
		return (Notice) super.selectOne(statement+"findNoticeById", id);
	}

}
