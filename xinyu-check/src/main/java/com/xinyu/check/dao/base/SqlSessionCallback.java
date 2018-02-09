package com.xinyu.check.dao.base;

import org.apache.ibatis.session.SqlSession;

public interface SqlSessionCallback {
	Object doInSqlSession(SqlSession SqlSession) throws Exception;
}
