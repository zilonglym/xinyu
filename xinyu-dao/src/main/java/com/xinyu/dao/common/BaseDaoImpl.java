package com.xinyu.dao.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.xinyu.dao.mybatis.PaginationInterceptor;
import com.xinyu.dao.mybatis.PaginationInterceptor.QueryCountParamsProcesser;
import com.xinyu.dao.mybatis.SqlSessionCallback;
import com.xinyu.model.common.CommonConstant;
import com.xinyu.model.common.Entity;
import com.xinyu.model.common.Pagination;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.system.Account;

@SuppressWarnings("unchecked")
@Repository("baseDao")
public class BaseDaoImpl extends SqlSessionDaoSupport implements BaseDao {
	private static final ThreadLocal<SqlSession> currentSqlSession = new ThreadLocal<SqlSession>();
	private final static Logger logger = Logger.getLogger(BaseDaoImpl.class);
	@Autowired
	protected SqlSessionFactory sqlSessionFactory;
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	/**
	 * 支持批量操作
	 * 
	 * @param sqlSessionCallback
	 * @return
	 */
	public Object execute(SqlSessionCallback sqlSessionCallback) {
		SqlSession sqlSession = SqlSessionUtils.getSqlSession(sqlSessionFactory, ExecutorType.BATCH,
				new MyBatisExceptionTranslator(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
						true));
		try {

			return sqlSessionCallback.doInSqlSession(sqlSession);
		} catch (Exception e) {
			logger.error(e, e);
			throw new RuntimeException(e);
		} finally {
			SqlSessionUtils.closeSqlSession(sqlSession, sqlSessionFactory);
		}
	}

	@Override
	public <T> Pagination<T> selectList(String statement, Object parameter, Pagination<T> page) {
		if (page == null) {
			page = new Pagination<T>();
		}
		int count = selectCount(statement, parameter);
		page.setRecordCount(count);

		if (page.getPageSize() < 1) {
			page.setItems(new ArrayList<T>());
			return page;
		}
		if (count == 0) {
			page.setItems(new ArrayList<T>());
		} else {
			Account account = SessionUser.get();
			if (account != null && account.getCu() != null) {
				if (parameter instanceof Entity) {
					Entity entity = (Entity) parameter;
					entity.setCu(account.getCu());
				} else if (parameter instanceof Map) {
					Map map=(Map) parameter;
					map.put("cu", account.getCu());
				}
			}
			List<T> items = getCustomSqlSession().selectList(statement, parameter,
					new RowBounds(page.getCurrentPageStartIndex(), page.getPageSize()));
			page.setItems(items);
			dataBigWarning(statement, items);
		}
		return page;
	}

	/**
	 * 分页时候自动优化查询count时的order by 如果order by不在最后 有可能会出现问题 请选择性调用
	 * 
	 * @param <T>
	 * @param statement
	 * @param parameter
	 * @param page
	 * @return
	 */
	@Override
	public <T> Pagination<T> selectListWithRemoveOrder(String statement, Object parameter, Pagination<T> page) {
		QueryCountParamsProcesser.AUTO_REMOVE_ORDER_BY.set(true);
		final Pagination<T> p = this.selectList(statement, parameter, page);
		QueryCountParamsProcesser.AUTO_REMOVE_ORDER_BY.remove();
		return p;
	}

	@Override
	public int selectCount(String statement, Object parameter) {
		Account account = SessionUser.get();
		if (account != null && account.getCu() != null) {
			if (parameter instanceof Entity) {
				Entity entity = (Entity) parameter;
				entity.setCu(account.getCu());
			} else if (parameter instanceof Map) {
				Map map=(Map) parameter;
				map.put("cu", account.getCu());
			}
		}
		return (Integer) getCustomSqlSession().selectOne(statement,
				new PaginationInterceptor.CountParameter(parameter));
	}

	@Override
	public Object selectOne(String statement, Object parameter) {
		return getCustomSqlSession().selectOne(statement, parameter);
	}

	@Override
	public int update(String statement, Object parameter) {
		return getCustomSqlSession().update(statement, parameter);
	}

	@Override
	public int delete(String statement, Object parameter) {
		return getCustomSqlSession().delete(statement, parameter);
	}

	@Override
	public List<?> selectList(String statement) {
		
		return getCustomSqlSession().selectList(statement);
	}

	@Override
	public int insert(String statement, Object parameter) {
		if (parameter instanceof Entity) {
			Entity entity = (Entity) parameter;
			if (null == entity.getCu()) {
				Account account = SessionUser.get();
				logger.error("account info:" + account);

				if (account != null && account.getCu() != null) {
					entity.setCu(account.getCu());
				} else {
					// entity.setCu("7d6f6f97-a988-45a2-a3de-zc8888centro");
					logger.error("account is null");
				}
			}
		}
		return getCustomSqlSession().insert(statement, parameter);
	}

	@Override
	public <T> List<T> selectList(String statement, Object parameter, int pageSize, int pageIndex) {
		Account account = SessionUser.get();
		if (account != null && account.getCu() != null) {
			if (parameter instanceof Entity) {
				Entity entity = (Entity) parameter;
				entity.setCu(account.getCu());
			} else if (parameter instanceof Map) {
				Map map=(Map) parameter;
				map.put("cu", account.getCu());
			}
		}
		List<T> items = getCustomSqlSession().selectList(statement, parameter,
				new RowBounds((pageIndex - 1) * pageSize, pageSize));
		String sql = getCustomSqlSession().getConfiguration().getMappedStatement(statement).getBoundSql(null).getSql();
		logger.error(sql);
		dataBigWarning(statement, items);
		return items;
	}

	private void dataBigWarning(String statement, List items) {
		if (items != null) {
			if (items.size() > 2000) {
				StringBuffer sb = new StringBuffer(" More than 2000 data,sqlId:").append(statement)
						.append(". queryTotalSize:").append(items.size()).append(",\n CallbackInfo:")
						.append(getCallbackInfo());
				logger.info(sb.toString());
			}
		}
	}

	@Override
	public List<?> selectList(String statement, Object parameter) {
		
		// String
		// sql=getCustomSqlSession().getConfiguration().getMappedStatement(statement).getBoundSql(null).getSql();
		// logger.error(sql);
		Account account = SessionUser.get();
		if (account != null && account.getCu() != null) {
			if (parameter instanceof Entity) {
				Entity entity = (Entity) parameter;
				entity.setCu(account.getCu());
			} else if (parameter instanceof Map) {
				Map map=(Map) parameter;
				map.put("cu", account.getCu());
			}
		}
		List<?> items = getCustomSqlSession().selectList(statement, parameter);
		dataBigWarning(statement, items);
		return items;
	}

	protected SqlSession getCustomSqlSession() {
		final SqlSession customSession = currentSqlSession.get();
		if (customSession != null) {
			return customSession;
		}
		return this.getSqlSession();
	}

	@Override
	public void executeSql(String sql) {
		jdbcTemplate.execute(sql);
	}

	protected String createStatement(String prefix, String statementId) {
		return prefix + statementId;
	}

	protected String getStatement(String prefix, String statementId) {
		return prefix + CommonConstant.POINT + statementId;
	}

	/**
	 * 支持批处理注解 防止业务逻辑进入dao层 防止数据库层进入service层
	 * 
	 * @author xsq 用法
	 *         参考com.shihua.service.workflow.impl.QuickTemplateServiceImpl
	 *         .doQuickTemplateEdit方法
	 */
	public static class BatchTools {
		public static final ThreadLocal<Object> batchLocal = new ThreadLocal<Object>();
		// public static final ThreadLocal<Object> transactionLocal = new
		// ThreadLocal<Object>();

		/**
		 * 利用Advisor创建proxy 处理和其它aop标签组合用
		 * 
		 * @author xsq
		 */
		@Component("beanFactoryBatchAdvisor")
		@Lazy(true)
		@Role(value = BeanDefinition.ROLE_INFRASTRUCTURE)
		public static class BeanFactoryBatchAdvisor extends AbstractBeanFactoryPointcutAdvisor
				implements InitializingBean, Ordered {
			private static final long serialVersionUID = 1L;
			private static final int DEFAULTORDERVALUE = 1;
			@Autowired
			protected SqlSessionFactory sqlSessionFactory;

			/**
			 * 定位加入Batch注解的方法 一般切入在service层
			 */
			private final StaticMethodMatcherPointcut batchPointcut = new StaticMethodMatcherPointcut() {
				@Override
				public boolean matches(Method method, Class<?> targetClass) {
					final Method targetMethod = BeanUtils.findMethod(targetClass, method.getName(),
							method.getParameterTypes());
					final Batch annotationByTargetMethod = AnnotationUtils.findAnnotation(targetMethod, Batch.class);
					if (annotationByTargetMethod != null) {
						return true;
					}
					return false;
				}
			};

			/**
			 * 具体批处理逻辑
			 */
			public final MethodInterceptor batchAdvice = new MethodInterceptor() {
				@Override
				public Object invoke(final MethodInvocation invocation) throws Throwable {
					if (batchLocal.get() != null) {// 已经是批处理 防止batch嵌套
						return invocation.proceed();
					}
					final SqlSession batchSqlSession = getBatchSqlSession();
					BaseDaoImpl.currentSqlSession.set(batchSqlSession);
					batchLocal.set(true);

					final SqlSessionCallback sqlSessionCallback = new SqlSessionCallback() {
						@Override
						public Object doInSqlSession(SqlSession SqlSession) throws Exception {
							try {
								return invocation.proceed();
							} catch (Throwable e) {
								if (e instanceof Exception) {
									throw (Exception) e;
								}
								throw new Exception("代理批处理出错[" + e.getMessage() + "]", e);
							}
						}
					};
					try {
						return sqlSessionCallback.doInSqlSession(batchSqlSession);
					} catch (Exception e) {
						if (e instanceof RuntimeException) {
							throw (RuntimeException) e;
						}
						throw new RuntimeException(e);
					} finally {
						currentSqlSession.remove();
						// batchLocal.remove();
						/*
						 * if (transactionLocal.get() != null) {
						 * transactionLocal.remove(); // batch
						 * 注解在transaction标签之后由事物拦截器去关闭所有连接 } else {
						 */
						SqlSessionUtils.closeSqlSession(batchSqlSession, sqlSessionFactory);
						// }
					}
				}
			};

			@Override
			public void afterPropertiesSet() throws Exception {
				this.setAdvice(batchAdvice);
			}

			/**
			 * 处理 事物和缓存组合
			 */
			@Override
			public int getOrder() {
				return DEFAULTORDERVALUE;
			}

			@Override
			public Pointcut getPointcut() {
				return batchPointcut;
			}

			/**
			 * 取得批处理session
			 * 
			 * @return
			 */
			private SqlSession getBatchSqlSession() {
				try {
					final SqlSession batchSqlSession = SqlSessionUtils.getSqlSession(sqlSessionFactory,
							ExecutorType.BATCH, new MyBatisExceptionTranslator(
									sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(), true));
					return batchSqlSession;
				} catch (TransientDataAccessResourceException e) {
					throw new RuntimeException("Transaction注解不能在batch注解之前", e);
					/*
					 * // 防止事物batch之前添加事物
					 * logger.warn("batch之前添加了事物利用sqlSessionFactory取得sqlSession"
					 * ); transactionLocal.set(true); final SqlSession
					 * oldSqlSession =
					 * SqlSessionUtils.getSqlSession(sqlSessionFactory); final
					 * SqlSession session =
					 * sqlSessionFactory.openSession(ExecutorType.BATCH,
					 * oldSqlSession.getConnection()); return session;
					 */
				}

			}

		}

		/**
		 * 批处理注解
		 * 
		 * @author xsq
		 * 
		 */
		@Target({ ElementType.METHOD })
		@Retention(RetentionPolicy.RUNTIME)
		@Documented
		public static @interface Batch {
		}

	}

	private static String getCallbackInfo() {
		final StackTraceElement[] stackElements = new Throwable().getStackTrace();
		final StringBuilder info = new StringBuilder();
		if (stackElements != null) {
			for (int i = 0; i < stackElements.length; i++) {
				if (i == 50) {
					break;
				}
				info.append(stackElements[i].getClassName()).append(".").append(stackElements[i].getMethodName())
						.append(",");
			}
		}
		return info.toString();
	}

	protected String createSqlId(String namespace, String id) {
		return namespace + "." + id;
	}

}