package com.xinyu.check.dao.base;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.FastResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.log4j.Logger;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;




/**
 * 分页拦截�? 支持多种数据�?
 * 
 */
@Intercepts({@Signature(
		type= Executor.class,
		method = "query",
		args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PaginationInterceptor extends AbstractInterceptor {
	private final static ProcessInterceptAfterProcessManager processInterceptAfterProcessManager=new ProcessInterceptAfterProcessManager();
	private final static Logger logger = Logger.getLogger(PaginationInterceptor.class);
	private static int MAPPED_STATEMENT_INDEX = 0;
	private static int PARAMETER_INDEX = 1;
	private static int ROWBOUNDS_INDEX = 2;

	private Dialect dialect;

	public Object intercept(Invocation invocation) throws Throwable {
		processIntercept(invocation.getArgs());
		return invocation.proceed();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void processIntercept(final Object[] queryArgs) {
		MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
		Object parameter = queryArgs[PARAMETER_INDEX];
		final RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];
		int offset = rowBounds.getOffset();
		int limit = rowBounds.getLimit();

		setCu(parameter);
		//分页
		if (dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
			if(parameter instanceof Map){
				//移除count特制的参�? 重新创建map防止原有map数据的破�?
				parameter=QueryCountParamsProcesser.getParams((Map<Object, Object>) parameter);
				queryArgs[PARAMETER_INDEX]=parameter;
			}
			
			BoundSql boundSql = ms.getBoundSql(parameter);
			String sql = boundSql.getSql().trim();
			if(parameter instanceof Map){
				Map params = (Map) parameter;
				if (params.containsKey("sidx")) {
					sql = attachSorts(sql, (String[]) params.get("sidx"),
							(String) params.get("sord"));
				}
			}
			if (dialect.supportsLimitOffset()) {
				sql = dialect.getLimitString(sql, offset, limit);
				offset = RowBounds.NO_ROW_OFFSET;
			} else {
				sql = dialect.getLimitString(sql, 0, limit);
			}
			limit = RowBounds.NO_ROW_LIMIT;

			queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
			BoundSql newBoundSql =this.getNewSqlBoundSql(ms, sql, boundSql);  
			MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql), false);
			queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
		} else if(parameter instanceof CountParameter) {
			
			//获取总数
			parameter = ((CountParameter) parameter).getParameter();
			
			QueryCountParamsProcesser.addCountKey(parameter);
			
			BoundSql boundSql = ms.getBoundSql(parameter);
			String sql = boundSql.getSql().trim();
			sql=QueryCountParamsProcesser.process(sql);
			sql = "select count(1) from (" + sql + ") tmp";
			BoundSql newBoundSql =this.getNewSqlBoundSql(ms, sql, boundSql); 
			MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql), true);
			queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
			queryArgs[PARAMETER_INDEX] = parameter;
		}
			else{
			BoundSql boundSql = ms.getBoundSql(parameter);
			String sql = boundSql.getSql().trim();
			if (parameter instanceof Map) {
				Map params = (Map) parameter;
				if (params.containsKey("sidx")) {
					sql = attachSorts(sql, (String[]) params.get("sidx"),
							(String) params.get("sord"));
				}
			}
		}
		this.runProcessInterceptAfterProcess(dialect,(MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX], parameter);
	}
	
	
	/**
	 * 填充cu
	 * @param parameter
	 */
	@SuppressWarnings({"unchecked" })
	private void setCu(Object parameter){
		
	}

	/**
	 * 
	 * intro: attach 'order by' to sql
	 * 
	 * @author Lineshow
	 * @since 2012-8-3
	 */
	private String attachSorts(String sql, String[] sidxs, String sord) {
		Pattern pattern = Pattern.compile("order(\\s)+by");
		Matcher matcher = pattern.matcher(sql);
		if (matcher.find()) {
			return sql;
		}
		return new StringBuilder(sql).append(" order by ")
				.append(org.apache.commons.lang.StringUtils.join(sidxs))
				.append(" ")
				.append(org.apache.commons.lang.StringUtils.trimToEmpty(sord))
				.toString();
	}

	public Object plugin(Object target) {
		final Object proxy= ResultProcessForMap.process(target);
		if(proxy!=null){
			return proxy;
		}
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		String dialectClass = properties.getProperty("dialectClass");
		try {
			dialect = (Dialect) Class.forName(dialectClass).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(
					"cannot create dialect instance by dialectClass:"
							+ dialectClass, e);
		}
	}
	
	
	
	private void runProcessInterceptAfterProcess(Dialect dialect,MappedStatement mappedStatement,Object parameter){
		final Collection<ProcessInterceptAfterProcess> list=processInterceptAfterProcessManager.getProcessInterceptAfterProcesses();
		if(list==null){
			return;
		}
		for (ProcessInterceptAfterProcess processInterceptAfterProcess : list) {
			processInterceptAfterProcess.process(dialect, mappedStatement, parameter);
		}
	}

	public static class CountParameter {
		private final Object parameter;
		public CountParameter(Object parameter) {
			this.parameter = parameter;
		}
		public Object getParameter() {
			return parameter;
		}
	}
	
	public static  ProcessInterceptAfterProcessManager getProcessInterceptAfterProcessManager(){
		return processInterceptAfterProcessManager;
	}
	
	public static class ProcessInterceptAfterProcessManager{
		//private ConcurrentLinkedQueue<ProcessInterceptAfterProcess> processInterceptAfterProcesses=new ConcurrentLinkedQueue();
		private final static ThreadLocal<List<ProcessInterceptAfterProcess>> processInterceptAfterProcessesHold=new ThreadLocal<List<ProcessInterceptAfterProcess>>();
		public Collection<ProcessInterceptAfterProcess> getProcessInterceptAfterProcesses(){
			final List<ProcessInterceptAfterProcess> list=processInterceptAfterProcessesHold.get();
		     if(list==null||list.isEmpty()){
		    	 return null;
		     }
			OrderComparator.sort(list);
			return Collections.unmodifiableCollection(list);
		}
		
		public void addProcessInterceptAfterProcess(ProcessInterceptAfterProcess processInterceptAfterProcess){
			List<ProcessInterceptAfterProcess> list=processInterceptAfterProcessesHold.get();
			if(list==null){
				list=new LinkedList<PaginationInterceptor.ProcessInterceptAfterProcess>();
				processInterceptAfterProcessesHold.set(list);
			}
			list.add(processInterceptAfterProcess);
		}
		
		public void removeProcessInterceptAfterProcess(ProcessInterceptAfterProcess processInterceptAfterProcess){
			final List<ProcessInterceptAfterProcess> list=processInterceptAfterProcessesHold.get();
			if(list==null){
				return;
			}
			list.remove(processInterceptAfterProcess);
		}
	}
	
	public static interface ProcessInterceptAfterProcess extends Ordered{
		void process(Dialect dialect,MappedStatement mappedStatement,Object parameter);
	}
	
	/**
	 * 处理返回结果是map,key的问�?
	 * 
	 * @author
	 * 
	 */
	public static class ResultProcessForMap {
		//�?��设置 当前线程生效
		private static final ThreadLocal<Boolean> ENABLE=new ThreadLocal<Boolean>();
		//只对当前�?��查询生效 防止调用多次map查询引起的问�?
		private static final ThreadLocal<Boolean> ONCE_ENABLE=new ThreadLocal<Boolean>();
		
		/**
		 * 整个线程 对查询返回map进行大小写处�?
		 * 如果mybatis查询缓存 则会绕过当前处理
		 * 注意sql的别名必须用   "" 引起 才会生效
		 * @param enable
		 */
		public static final void setEnable(boolean enable){
			ENABLE.set(enable);
		}
		
		/**
		 * 单个次对查询返回map进行大小写处�?
		 * 如果mybatis查询缓存 则会绕过当前处理
		 * 注意sql的别名必须用   "" 引起 才会生效
		 * @param enable
		 */
		public static final void setOnceEnable(boolean enable){
			ONCE_ENABLE.set(enable);
		}
		
	
		private static final boolean getEnable(){
			final boolean b1=ENABLE.get()==null?false:ENABLE.get(); 
		    final boolean b2=ONCE_ENABLE.get()==null?false:ONCE_ENABLE.get();
		    return b1||b2;
		}
		
		public static Object process(Object target) {
			final boolean enablePart1=target instanceof FastResultSetHandler;
			final boolean enablePart2=getEnable();
			if (enablePart1&&enablePart2) {
				// 处理 select 返回map出现的问�?
				final FastResultSetHandler fastResultSetHandler = (FastResultSetHandler) target;

				final Executor executor = (Executor) ClassUtil.getFieldValue(fastResultSetHandler, "executor");
				final MappedStatement mappedStatement = (MappedStatement) ClassUtil.getFieldValue(fastResultSetHandler, "mappedStatement");
				final RowBounds rowBounds = (RowBounds) ClassUtil.getFieldValue(fastResultSetHandler, "rowBounds");
				final ParameterHandler parameterHandler = (ParameterHandler) ClassUtil.getFieldValue(fastResultSetHandler, "parameterHandler");
				final ResultHandler resultHandler = (ResultHandler) ClassUtil.getFieldValue(fastResultSetHandler, "resultHandler");
				final BoundSql boundSql = (BoundSql) ClassUtil.getFieldValue(fastResultSetHandler, "boundSql");

				final FastResultSetHandler proxy = new FastResultSetHandler(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds) {
					@Override
					protected void loadMappedAndUnmappedColumnNames(ResultSet rs, ResultMap resultMap, List<String> mappedColumnNames, List<String> unmappedColumnNames)
							throws SQLException {
						final Class<?> resultType=resultMap.getType();
						//只处理返回map类型的�?  
						//这里会有 mybatis查询缓存 不会生效的问�?
						if(getEnable()&&Map.class.isAssignableFrom(resultType)){
							mappedColumnNames.clear();
							unmappedColumnNames.clear();
							final ResultSetMetaData rsmd = rs.getMetaData();
							final int columnCount = rsmd.getColumnCount();
							final Set<String> mappedColumns = resultMap.getMappedColumns();
							for (int i = 1; i <= columnCount; i++) {
								final String columnName = configuration.isUseColumnLabel() ? rsmd.getColumnLabel(i) : rsmd.getColumnName(i);
								final String upperColumnName = columnName.toUpperCase(Locale.ENGLISH);
								if (mappedColumns.contains(upperColumnName)) {
									mappedColumnNames.add(upperColumnName);
									mappedColumnNames.add(columnName);
								} else {
									// unmappedColumnNames.add(upperColumnName);
									unmappedColumnNames.add(columnName);
								}
							}
							
							//清除单次
							ONCE_ENABLE.remove();
						}else{
							super.loadMappedAndUnmappedColumnNames(rs, resultMap, mappedColumnNames, unmappedColumnNames);
						}
						
					}
				};
				return proxy;
			}
            return null;
		}
	}
	
	
	/**
	 *  处理分页查询时�?,自动生成查询count时�?出现order by 引起的�?能问�?
	 *  1.可以在xml中用这样的if包装order by
	 *   <if test="$OnlyQueryCount_removeOrder==null">
		    order by flowinstance.fcreateTime desc
		 </if> 
		2.也可以在调用分页之前加入
		    AUTO_REMOVE_ORDER_BY 的线程�?进行自动处理
	 * @author xsq
	 *
	 */
	public static class QueryCountParamsProcesser{
		public static final String COUNT_KEY_PREFIX="$OnlyQueryCount_";
		//$OnlyQueryCount_removeOrder
		public static final String COUNT_KEY=COUNT_KEY_PREFIX+"removeOrder";
		public static final ThreadLocal<Boolean> AUTO_REMOVE_ORDER_BY=new ThreadLocal<Boolean>();
		
		private static final String[] BLANKS=new String[]{" ","\t","\r","\n"};
		                            
		public static String process(String countSql){
			//自动去除order by
			if(AUTO_REMOVE_ORDER_BY.get()!=null&&AUTO_REMOVE_ORDER_BY.get()){
				String innerCountSql=countSql.toLowerCase();
				
				final int lastOrderIndex=lastIndex(innerCountSql,"order");
				final int lastFormIndex=lastIndex(innerCountSql,"from");
				
				//如果 �?���?��order by 
				final boolean removeOrderByPart01=lastOrderIndex!=-1;
				final boolean removeOrderByPart02=lastOrderIndex>lastFormIndex;
				if(removeOrderByPart01&&removeOrderByPart02){
					countSql=countSql.substring(0,lastOrderIndex);
				}
			}
			
			return countSql;
		}
		
		private static int lastIndex(String countSql,String marke){
			int maxIndex=-1;
			for (String left : BLANKS) {
				final StringBuilder endMarke=new StringBuilder(left);
				endMarke.append(marke);
				for (String right : BLANKS) {
					endMarke.append(right);
					maxIndex=Math.max(maxIndex, countSql.lastIndexOf(left+marke+right));
				}
			}
			return maxIndex;
		}
		
		/**
		 * 如果xml文件�?if test="#count_remove_order==null">则自动在查询count时去�?
		 * @param params
		 */
		public static void addCountKey(Object params){
			if(params instanceof Map<?,?>){
				@SuppressWarnings("unchecked")
				final Map<Object,Object> innerMap= (Map<Object, Object>) params;
				innerMap.put(COUNT_KEY, new Object());
			}
		}
		
		/**
		 * 重新创建map防止 原有map被修�?
		 * @param <K>
		 * @param <V>
		 * @param params
		 * @return
		 */
		public static <K,V>Map<K,V> getParams(Map<K,V> params){
			final Map<K,V> map=new HashMap<K, V>(params);
			final List<Object> removeKeys=new ArrayList<Object>();
			for (Object key : map.keySet()) {
				if(key!=null&&key.toString().startsWith(COUNT_KEY_PREFIX)){
					removeKeys.add(key);
				}
			}
			for (Object key : removeKeys) {
				map.remove(key);
			}
			return map;
		} 
	}
	
	
	
	/**
	 * 处理返回结果是map,key的问�?
	 * 如果�?�� 会影响resultMap的集合标�?如果有collection等标签请不要使用)
	 * @author
	 * 
	 */
	public static class ResultProcesserForMap {
		//�?��设置 当前线程生效
		private static final ThreadLocal<Boolean> ENABLE=new ThreadLocal<Boolean>();
		//只对当前�?��查询生效 防止调用多次map查询引起的问�?
		private static final ThreadLocal<Boolean> ONCE_ENABLE=new ThreadLocal<Boolean>();
		
		/**
		 * 整个线程 对查询返回map进行大小写处�?
		 * 如果mybatis查询缓存 则会绕过当前处理
		 * 注意sql的别名必须用   "" 引起 才会生效
		 * @param enable
		 */
		public static final void setEnable(boolean enable){
			ENABLE.set(enable);
		}
		
		/**
		 * 单个次对查询返回map进行大小写处�?
		 * 如果mybatis查询缓存 则会绕过当前处理
		 * 注意sql的别名必须用   "" 引起 才会生效
		 * @param enable
		 */
		public static final void setOnceEnable(boolean enable){
			ONCE_ENABLE.set(enable);
		}
		
	
		private static final boolean getEnable(){
			final boolean b1=ENABLE.get()==null?false:ENABLE.get(); 
		    final boolean b2=ONCE_ENABLE.get()==null?false:ONCE_ENABLE.get();
		    return b1||b2;
		}
		
		public static Object process(Object target) {
			final boolean enablePart1=target instanceof FastResultSetHandler;
			final boolean enablePart2=getEnable();
			if (enablePart1&&enablePart2) {
				// 处理 select 返回map出现的问�?
				final FastResultSetHandler fastResultSetHandler = (FastResultSetHandler) target;

				final Executor executor = (Executor) ClassUtil.getFieldValue(fastResultSetHandler, "executor");
				final MappedStatement mappedStatement = (MappedStatement) ClassUtil.getFieldValue(fastResultSetHandler, "mappedStatement");
				final RowBounds rowBounds = (RowBounds) ClassUtil.getFieldValue(fastResultSetHandler, "rowBounds");
				final ParameterHandler parameterHandler = (ParameterHandler) ClassUtil.getFieldValue(fastResultSetHandler, "parameterHandler");
				final ResultHandler resultHandler = (ResultHandler) ClassUtil.getFieldValue(fastResultSetHandler, "resultHandler");
				final BoundSql boundSql = (BoundSql) ClassUtil.getFieldValue(fastResultSetHandler, "boundSql");
				
				final Configuration configuration=(Configuration) ClassUtil.getFieldValue(fastResultSetHandler, "configuration");
				final TypeHandlerRegistry typeHandlerRegistry=(TypeHandlerRegistry) ClassUtil.getFieldValue(fastResultSetHandler, "typeHandlerRegistry");
				final ObjectFactory objectFactory=(ObjectFactory) ClassUtil.getFieldValue(fastResultSetHandler, "objectFactory");
				    
				  
				final FastResultSetHandler proxy = new FastResultSetHandler(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds) {
					@Override
					protected void loadMappedAndUnmappedColumnNames(ResultSet rs, ResultMap resultMap, List<String> mappedColumnNames, List<String> unmappedColumnNames)
							throws SQLException {
						final Class<?> resultType=resultMap.getType();
						//只处理返回map类型的�?  
						//这里会有 mybatis查询缓存 不会生效的问�?
						if(getEnable()&&Map.class.isAssignableFrom(resultType)){
							mappedColumnNames.clear();
							unmappedColumnNames.clear();
							final ResultSetMetaData rsmd = rs.getMetaData();
							final int columnCount = rsmd.getColumnCount();
							final Set<String> mappedColumns = resultMap.getMappedColumns();
							for (int i = 1; i <= columnCount; i++) {
								final String columnName = configuration.isUseColumnLabel() ? rsmd.getColumnLabel(i) : rsmd.getColumnName(i);
								final String upperColumnName = columnName.toUpperCase(Locale.ENGLISH);
								if (mappedColumns.contains(upperColumnName)) {
									mappedColumnNames.add(upperColumnName);
									mappedColumnNames.add(columnName);
								} else {
									// unmappedColumnNames.add(upperColumnName);
									unmappedColumnNames.add(columnName);
								}
							}
							
							//清除单次
							ONCE_ENABLE.remove();
						}else{
							super.loadMappedAndUnmappedColumnNames(rs, resultMap, mappedColumnNames, unmappedColumnNames);
						}
						
					}
				};
				
				ClassUtil.setFieldValue(proxy, "configuration", configuration);
				ClassUtil.setFieldValue(proxy, "typeHandlerRegistry", typeHandlerRegistry);
				ClassUtil.setFieldValue(proxy, "objectFactory", objectFactory);
				
				return proxy;
			}
            return null;
		}
	}

}
