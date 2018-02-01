package com.xinyu.task.dao.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.List;

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

@SuppressWarnings("unchecked")
@Repository("baseDao")
public class BaseDaoImpl extends SqlSessionDaoSupport implements BaseDao {
	private static final ThreadLocal<SqlSession> currentSqlSession = new ThreadLocal<SqlSession>();
	private final static Logger logger = Logger.getLogger(BaseDaoImpl.class);
	@Autowired
	protected SqlSessionFactory sqlSessionFactory;
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	public Object selectOne(String statement, Object parameter){
		return getCustomSqlSession().selectOne(statement, parameter);
	}

	public int update(String statement, Object parameter) {
		return getCustomSqlSession().update(statement, parameter);
	}

	public int delete(String statement, Object parameter) {
		return getCustomSqlSession().delete(statement, parameter);
	}

	public List<?> selectList(String statement) {
		return getCustomSqlSession().selectList(statement);
	}

	public int insert(String statement, Object parameter) {
	  
		return getCustomSqlSession().insert(statement, parameter);
	}

	public <T> List<T> selectList(String statement, Object parameter, int pageSize, int pageIndex) {
		List<T> items = getCustomSqlSession().selectList(statement, parameter, new RowBounds((pageIndex - 1) * pageSize, pageSize));
		dataBigWarning(statement, items);
		return items;
	}
	
	private void dataBigWarning(String statement,List items){
		if(items!=null){
			if(items.size()>2000){		
				StringBuffer sb=new StringBuffer(" More than 2000 data,sqlId:").append(statement).append(". queryTotalSize:").append(items.size()).append(",\n CallbackInfo:").append(getCallbackInfo());
				logger.info(sb.toString());
			}
		}
	}

	public List<?> selectList(String statement, Object parameter) {
		List<?> items=  getCustomSqlSession().selectList(statement, parameter);
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

	public void executeSql(String sql) {
		jdbcTemplate.execute(sql);
	}

	protected String createStatement(String prefix, String statementId) {
		return prefix + statementId;
	}
	
	protected String getStatement(String prefix, String statementId) {
		return prefix +CommonConstant.POINT+ statementId;
	}

	

	/**
	 * 支持批处理注�?防止业务逻辑进入dao�?防止数据库层进入service�?
	 * 
	 * @author xsq 用法
	 *         参�?com.shihua.service.workflow.impl.QuickTemplateServiceImpl
	 *         .doQuickTemplateEdit方法
	 */
	public static class BatchTools {
		public static final ThreadLocal<Object> batchLocal = new ThreadLocal<Object>();
		//public static final ThreadLocal<Object> transactionLocal = new ThreadLocal<Object>();

		/**
		 * 利用Advisor创建proxy 处理和其它aop标签组合�?
		 * 
		 * @author xsq
		 */
		@Component("beanFactoryBatchAdvisor")
		@Lazy(true)
		@Role(value = BeanDefinition.ROLE_INFRASTRUCTURE)
		public static class BeanFactoryBatchAdvisor extends AbstractBeanFactoryPointcutAdvisor implements InitializingBean, Ordered {
			private static final long serialVersionUID = 1L;
			private static final int DEFAULTORDERVALUE = 1;
			@Autowired
			protected SqlSessionFactory sqlSessionFactory;

			/**
			 * 定位加入Batch注解的方�?�?��切入在service�?
			 */
			private final StaticMethodMatcherPointcut batchPointcut = new StaticMethodMatcherPointcut() {
				public boolean matches(Method method, Class<?> targetClass) {
					final Method targetMethod = BeanUtils.findMethod(targetClass, method.getName(), method.getParameterTypes());
					final Batch annotationByTargetMethod = AnnotationUtils.findAnnotation(targetMethod, Batch.class);
					if (annotationByTargetMethod != null) {
						return true;
					}
					return false;
				}
			};
			public final MethodInterceptor batchAdvice = new MethodInterceptor() {
				public Object invoke(final MethodInvocation invocation) throws Throwable {
					if (batchLocal.get() != null) {// 已经是批处理 防止batch嵌套
						return invocation.proceed();
					}
					final SqlSession batchSqlSession = getBatchSqlSession();
					BaseDaoImpl.currentSqlSession.set(batchSqlSession);
					batchLocal.set(true);

					final SqlSessionCallback sqlSessionCallback = new SqlSessionCallback() {
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
						//batchLocal.remove();
						/*if (transactionLocal.get() != null) {
							transactionLocal.remove();
							// batch 注解在transaction标签之后由事物拦截器去关闭所有连�?
						} else {*/
							SqlSessionUtils.closeSqlSession(batchSqlSession, sqlSessionFactory);
						//}
					}
				}
			};

			public void afterPropertiesSet() throws Exception {
				this.setAdvice(batchAdvice);
			}

			public int getOrder() {
				return DEFAULTORDERVALUE;
			}

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
					final SqlSession batchSqlSession = SqlSessionUtils.getSqlSession(sqlSessionFactory, ExecutorType.BATCH, new MyBatisExceptionTranslator(sqlSessionFactory
							.getConfiguration().getEnvironment().getDataSource(), true));
					return batchSqlSession;
				} catch (TransientDataAccessResourceException e) {
					throw new RuntimeException("Transaction注解不能在batch注解之前", e);
					/*// 防止事物batch之前添加事物
					logger.warn("batch之前添加了事物利用sqlSessionFactory取得sqlSession");
					transactionLocal.set(true);
					final SqlSession oldSqlSession = SqlSessionUtils.getSqlSession(sqlSessionFactory);
					final SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, oldSqlSession.getConnection());
					return session;*/
				}

			}

		}

		/**
		 * 批处理注�?
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
	
	private static String getCallbackInfo(){
		final StackTraceElement[] stackElements = new Throwable().getStackTrace();
		final StringBuilder info=new StringBuilder();
		if(stackElements != null)
		{
			for(int i = 0; i < stackElements.length; i++)
			{
				if(i==50){
					break;
				}
				info.append(stackElements[i].getClassName()).append(".").append(stackElements[i].getMethodName()).append(",");
			}
		}
		return info.toString();
	}
	


	protected String createSqlId(String namespace,String id){
		return namespace+"."+id;
	}
  
	

}