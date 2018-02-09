package com.xinyu.check.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisTest {
	@Autowired
	private ShardedJedisPool shardedJedisPool;

	@Test
	public void testSet() {
		ShardedJedis jedis = shardedJedisPool.getResource();
		jedis.set("name", "test");
	}

	@Test
	public void testGet() {
		ShardedJedis jedis = shardedJedisPool.getResource();
		String name = jedis.get("name");
		System.out.println(name);
	}
}
