package com.xinyu.task.dao.base;

import java.util.List;

/**
 * @author yangmin
 *
 */
public interface BaseDao {

	Object selectOne(String statement, Object parameter);
	int update(String statement, Object parameter);
	int delete(String statement, Object parameter);
	int insert(String statement, Object parameter);
	List<?> selectList(String statement);
	List<?> selectList(String statement, Object parameter);
	<T> List<T> selectList(String statement, Object parameter, int pageSize, int pageIndex);
	public void executeSql(String sql);
}
