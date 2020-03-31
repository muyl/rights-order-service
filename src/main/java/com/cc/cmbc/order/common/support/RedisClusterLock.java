package com.cc.cmbc.order.common.support;

import redis.clients.jedis.JedisCluster;

import java.util.Collections;

/**
 * @author 拓仲 on 2020/3/16
 */
public class RedisClusterLock {

    private static final Long   RELEASE_SUCCESS  = 1L;
    private static final String LOCK_SUCCESS     = "OK";
    private static final int    DEFAULT_TIME_OUT = 30 * 60;


    private JedisCluster jedisCluster;

    /**
     * 尝试获取分布式锁
     *
     * @param mutex 锁
     * @return 是否获取成功
     */
    public boolean tryLock(String mutex, String requestId) {
        return tryLock(mutex, requestId, DEFAULT_TIME_OUT);
    }

    /**
     * 尝试获取分布式锁
     *
     * @param mutex      锁
     * @param requestId  请求编号
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public boolean tryLock(String mutex, String requestId, int expireTime) {
        String result = jedisCluster.set(mutex, requestId, "nx", "ex", expireTime);
        return LOCK_SUCCESS.equals(result);
    }

    /**
     * 释放分布式锁
     *
     * @param mutex 锁
     * @return 是否释放成功
     */
    public boolean unLock(String mutex, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedisCluster.eval(script, Collections.singletonList(mutex), Collections.singletonList(requestId));
        return RELEASE_SUCCESS.equals(result);

    }

    /**
     * 释放分布式锁
     *
     * @param mutex 锁
     * @return 是否释放成功
     */
    public boolean unLock(String mutex) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedisCluster.eval(script, Collections.singletonList(mutex), Collections.singletonList("1"));
        return RELEASE_SUCCESS.equals(result);

    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }
}
