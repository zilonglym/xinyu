package com.xinyu.dao.common;

import java.util.List;

import com.xinyu.model.common.Pagination;

/**
 * @author lenovo
 *
 */
public interface BaseDao {
	
	
	/**
	 * 扩展MyBatis的分页查询
	 * @param statement
	 * @param parameter
	 * @param page
	 * @return
	 */
	<T> Pagination<T> selectList(String statement, Object parameter, Pagination<T> page);
	/**
	 * 查询分页时自动优化生成查询count的sql 
	 * 如果order by不在最后 有可能会出现问题 请选择性调用
	 * @param <T>
	 * @param statement
	 * @param parameter
	 * @param page
	 * @return
	 */
	<T> Pagination<T> selectListWithRemoveOrder(String statement, Object parameter, Pagination<T> page);
	int selectCount(String statement, Object parameter);
	Object selectOne(String statement, Object parameter);
	int update(String statement, Object parameter);
	int delete(String statement, Object parameter);
	int insert(String statement, Object parameter);
	List<?> selectList(String statement);
	List<?> selectList(String statement, Object parameter);
	<T> List<T> selectList(String statement, Object parameter, int pageSize, int pageIndex);
	public void executeSql(String sql);
}
