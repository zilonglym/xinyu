package com.xinyu.check.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
		String keyValue = "*20180131Check_out_db*";
		String historyKey = "20170920Check_out_history_db" + orderCode + "*";
		String key="ORDER_ACCEPT*";
		// 连接本地的 Redis 服务
		Jedis jedis = RedisUtil.getJedis();
		System.out.println("连接成功");
		Set<String> sets=jedis.keys(keyValue);
		System.err.println(sets.size());
		// 查看服务是否运行
//		deleteInfo(jedis);

//		 orderInfo(keyValue);
		 
//		 System.err.println(jedis.get("20171111Check_out_db887180201207159800e4ddb392-3346-4d72-b337-369951ac3bf3"));
	}

	private static void orderInfo(String keyValue) {
		Jedis jedis = RedisUtil.getJedis();
		System.err.println("-------------");
		Set<String> sets=jedis.keys(keyValue);
		int i=0;
		for (Iterator<String> iterator = sets.iterator(); iterator.hasNext();) {
			String k=iterator.next();
//			jedis.del(k);
			System.err.println(k);
			i++;
			if(i==10000){
				break;
			}
		}
		
	}

	private static void deleteInfo(Jedis jedis) {

		String value = "20180120Check_out_db*";
			Set<String> jedisSet = jedis.keys(value);
			System.err.println(value+"||"+jedisSet.size());
//			for (Iterator<String> iterator = jedisSet.iterator(); iterator.hasNext();) {
//				String key=iterator.next();
//				Map<String, String> map=(Map<String, String>) ObjectTranscoder.deserialize(jedis.get(key.getBytes()));
//				System.err.println(key+"||"+map);
////				jedis.del(key);
//			}
	}
}
