package com.xinyu.check.dao.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

/**
 * 对多个城市间切换，采用多数据源，每个城市对应�?��数据源，对于公共的表，采用直接加数据库名的方式做跨数据库链接
 * 
 * @author Hou Peiqin
 */
@Intercepts(
	{@Signature(
		type= Executor.class,
		method = "query",
		args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}), 
	@Signature(
		type= Executor.class,
		method = "update",
		args = {MappedStatement.class, Object.class})
})
public class MultiDataSourceInterceptor extends AbstractInterceptor {
	private final static Logger logger = Logger.getLogger(PaginationInterceptor.class);
	private static int MAPPED_STATEMENT_INDEX = 0;
	private static int PARAMETER_INDEX = 1;
	private List<String> commonTables = new ArrayList<String>();
	private String commonDatabase;
	
	public Object intercept(Invocation invocation) throws Throwable {
		processIntercept(invocation.getArgs());
		return invocation.proceed();
	}

	private void processIntercept(final Object[] queryArgs) {
		MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
		Object parameter = queryArgs[PARAMETER_INDEX];
		
		BoundSql boundSql = ms.getBoundSql(parameter);
		String sql = boundSql.getSql().trim().toLowerCase();
		
		sql = appentDbNameForCommonTables(sql);
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
		MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql), true);
		queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
		queryArgs[PARAMETER_INDEX] = parameter;
		if(logger.isDebugEnabled()) {
			logger.debug("sql = \n" + sql);
		}
	}

	/**
	 * 把�?用的表加上数据库�?
	 * 
	 * @param sql
	 * @return
	 */
	private String appentDbNameForCommonTables(String sql) {
		if(commonDatabase == null || commonDatabase.trim().equals("") || commonTables.size() == 0)
			return sql;
		for(String table : commonTables) {
			sql = sql.replaceAll("table", commonDatabase + "." + table);
		}
		return sql;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		String commonDatabase = properties.getProperty("commonDatabase");
		String commonTables = properties.getProperty("commonTables");
		logger.info("Common database is : " + commonDatabase);
		logger.info("Common tables are : " + commonTables);
		if(commonTables == null || commonTables.trim().equals("")) {
			return;
		}
		for(String table : commonTables.split(",")) {
			this.commonTables.add(table);
		}
	}

}
