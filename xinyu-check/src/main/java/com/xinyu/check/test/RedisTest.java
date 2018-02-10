package com.xinyu.check.test;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisTest {
	@Autowired
	private ShardedJedisPool shardedJedisPool;

}
