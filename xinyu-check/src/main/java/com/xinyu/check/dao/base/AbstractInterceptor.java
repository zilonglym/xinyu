package com.xinyu.check.dao.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;

/**
 * Mybatis拦截器基�?
 * 
 * @author Hou Peiqin
 *
 */
public abstract class AbstractInterceptor implements Interceptor {
	private final static Logger logger = Logger.getLogger(AbstractInterceptor.class);
	protected MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource, boolean isCount) {
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		builder.keyProperty(ms.getKeyProperty());
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		if(isCount) {
			List<ResultMap> resultMaps = new ArrayList<ResultMap>();
			resultMaps.add(new ResultMap.Builder(ms.getConfiguration(), ms.getId(), Integer.class, new ArrayList<ResultMapping>()).build());
			builder.resultMaps(resultMaps);
		} else {
			builder.resultMaps(ms.getResultMaps());
		}
		builder.cache(ms.getCache());
		MappedStatement newMs = builder.build();
		return newMs;
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}
	
	/**
	 * 反射替换sql 主要处理additionalParameters生成的参�?
	 * @param sql
	 * @param newBoundSql
	 */
	  BoundSql getNewSqlBoundSql(MappedStatement ms,String sql,BoundSql boundSql){
		try {
			final Field field=ReflectionUtils.findField(boundSql.getClass(), "sql");
			ReflectionUtils.makeAccessible(field);
			ReflectionUtils.setField(field, boundSql, sql);
			return boundSql;
		}catch (Exception e) {
			logger.warn("反射设置sql失败"+e.getMessage(),e); 
		}
		return  new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
	}
}
