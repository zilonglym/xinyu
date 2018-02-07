package com.xinyu.check.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xinyu.check.util.MemcachedManager;
import com.xinyu.check.util.ObjectTranscoder;
import com.xinyu.check.util.RedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Controller
@RequestMapping(value = "redis")
public class RedisController extends BaseController {

	public static final Logger logger = Logger.getLogger(RedisController.class);
	@Autowired
	private ShardedJedisPool shardedJedisPool;

	@RequestMapping(value = "index")
	public void springT() {
		ShardedJedis jedis = shardedJedisPool.getResource();
		jedis.set("name", "test");

		System.err.println(jedis.get("name"));
	}

	public static void main(String[] args) {
		// {zpIndex=120, zcIndex=1, 2902082140620=1, 6940971207081=1}
		String orderCode = "9891297785185";
		// String keyValue="20170920Check_out_db"+orderCode+"*";
		// String keyValue="2017-12-10CheckOut*";
		int v = 20171016;
		String keyValue = "success_record*";
		String historyKey = "20170920Check_out_history_db" + orderCode + "*";
		// 连接本地的 Redis 服务
		Jedis jedis = RedisUtil.getJedis();
		System.out.println("连接成功");
		// 查看服务是否运行
		System.err.println(jedis.keys(keyValue).size());
//		deleteInfo(jedis);

		 orderInfo(keyValue);
	}

	private static void orderInfo(String keyValue) {
		Jedis jedis = RedisUtil.getJedis();
		System.err.println("-------------");
		MemcachedManager manager = new MemcachedManager();
		Set<String> sets=jedis.keys(keyValue);
		int i=0;
		for (Iterator<String> iterator = sets.iterator(); iterator.hasNext();) {
			String k=iterator.next();
			jedis.del(k);
			System.err.println(k);
			i++;
			if(i==10000){
				break;
			}
		}
		
	}

	private static void deleteInfo(Jedis jedis) {

		int v = 20171101;
		String keyValue = "CheckOut*";
		for (int i = 0; i < 30; i++) {
			v++;
			String value = String.valueOf(v) + keyValue;
			Set<String> jedisSet = jedis.keys(value);
			System.err.println(value+"||"+jedisSet.size());
			for (Iterator<String> iterator = jedisSet.iterator(); iterator.hasNext();) {
				String key=iterator.next();
				Map<String, String> map=(Map<String, String>) ObjectTranscoder.deserialize(jedis.get(key.getBytes()));
//				logger.error(key);
//				System.err.println(key+"||"+map);
				jedis.del(key);
			}
		}
	}
}
