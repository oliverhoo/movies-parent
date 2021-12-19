package com.itheima.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {

    private static JedisPool jedisPool = null;

    static {
        //连接池配置项
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);//默认最大大小
        jedisPoolConfig.setMaxIdle(50);//最大空闲数
        jedisPoolConfig.setMinIdle(20);//最小空闲

        //0.创建jedispool
        //JedisPool jedisPool = new JedisPool("localhost", 6379);
        jedisPool = new JedisPool(jedisPoolConfig, "localhost", 6379);
    }

    public static Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();//从jedispool中获取
        return jedis;
    }
}
