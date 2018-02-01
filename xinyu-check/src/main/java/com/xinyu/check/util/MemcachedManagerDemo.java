package com.xinyu.check.util;

import java.util.Map;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * 写缓存，奇门这边需要在
 * 导入打单数据 订单确认，订单取消，订单作废时调用写入数据
 * @author yangmin
 * 2017年8月16日
 *
 */
public class MemcachedManagerDemo {
	
	private static final String server_url = "175.6.27.205:11211";
	/**
	 * memcached客户端单例
	 */
	private MemCachedClient cachedClient = null;

	/**
	 * 初始化连接池
	 */
	static {
		// 获取连接池的实例
		SockIOPool pool = SockIOPool.getInstance();

		// 服务器列表及其权重
		String[] servers = { server_url };
		Integer[] weights = { 3 };

		// 设置服务器信息
		pool.setServers(servers);
		pool.setWeights(weights);

		// 设置初始连接数、最小连接数、最大连接数、最大处理时间
		pool.setInitConn(10);
		pool.setMinConn(10);
		pool.setMaxConn(1000);
		pool.setMaxIdle(1000 * 60 * 60);

		// 设置连接池守护线程的睡眠时间
		pool.setMaintSleep(60);

		// 设置TCP参数，连接超时
		pool.setNagle(false);
		pool.setSocketTO(60);
		pool.setSocketConnectTO(0);

		// 初始化并启动连接池
		pool.initialize();

		// 压缩设置，超过指定大小的都压缩
		// cachedClient.setCompressEnable(true);
		// cachedClient.setCompressThreshold(1024*1024);
	}

	private void initMemcached() {

		if (cachedClient == null) {
			cachedClient = new MemCachedClient();
		}
	}

	public MemCachedClient getInstance() {
		initMemcached();
		return cachedClient;
	}

	public boolean add(String key, Object value) {
		return getInstance().add(key, value);
	}

	public boolean add(String key, Object value, Integer expire) {
		return getInstance().add(key, value, expire);
	}

	public boolean put(String key, Object value) {
		return getInstance().set(key, value);
	}

	public boolean put(String key, Object value, Integer expire) {
		return getInstance().set(key, value, expire);
	}

	public boolean replace(String key, Object value) {
		return getInstance().replace(key, value);
	}

	public boolean replace(String key, Object value, Integer expire) {
		return getInstance().replace(key, value, expire);
	}

	public Map<String, Map<String, String>> statusItems() {
		return getInstance().statsItems();
	}

	public Map<String, Map<String, String>> statsCacheDump(int arg0, int arg1) {
		return getInstance().statsCacheDump(arg0, arg1);
	}

	public Object get(String key) {
		return getInstance().get(key);
	}
	
	public boolean delete(String key){
		return this.getInstance().delete(key);
	}

	public static void main(String[] args) {
		// cachedClient.flushAll();
		MemcachedManagerDemo manager = new MemcachedManagerDemo();
		for (int i = 0; i < 10; i++) {
			String obj = (String) manager.get("Return" + "1300100694602");
			System.err.println(obj);
		}

	}
}
