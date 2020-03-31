package com.cc.cmbc.order.common.support;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 拓仲 on 2020/3/10
 */
public class RedisSupport {

    private JedisCluster jedisCluster;


    /**
     * 删除缓存
     *
     * @param key 缓存
     */
    public void deleteKey(String... key) {
        jedisCluster.del(key);
    }

    /**
     * 设置缓存
     *
     * @param k KEY
     * @param v VALUE
     */
    public void set(String k, String v) {
        jedisCluster.set(k, v);
    }

    /**
     * 设置缓存及失效时间
     *
     * @param k    KEY
     * @param v    VALUE
     * @param var3 失效时间
     * @param var5 时间单位
     */
    public void set(String k, String v, long var3, TimeUnit var5) {
        int seconds = (int) var5.toSeconds(var3);
        jedisCluster.setex(k, seconds, v);
    }

    /**
     * 获取缓存
     *
     * @param key KEY
     * @return 缓存内容
     */
    public String get(String key) {
        return jedisCluster.get(key);
    }

    /**
     * 获取Hash缓存
     *
     * @param key    key
     * @param mapKey mapKey
     * @return 缓存内容
     */
    public String getHashKey(String key, String mapKey) {
        return jedisCluster.hget(key, mapKey);
    }

    /**
     * 设置Hash缓存
     *
     * @param key       key
     * @param hashKey   hashKey
     * @param hashValue hashValue
     */
    public void hashPut(String key, String hashKey, String hashValue) {
        Map<String, String> hashMap = new HashMap<>(16);
        hashMap.put(hashKey, hashValue);
        jedisCluster.hmset(key, hashMap);
    }

    /**
     * 删除Hash缓存
     *
     * @param key  key
     * @param keys keys
     */
    public void deleteHashKeys(String key, String... keys) {
        jedisCluster.hdel(key, keys);
    }

    /**
     * 设置Set缓存
     *
     * @param key     key
     * @param itemKey itemKey
     * @param score   score
     * @return 处理标识
     */
    public boolean zadd(String key, String itemKey, double score) {
        return jedisCluster.zadd(key, score, itemKey) > 0;
    }

    /**
     * 删除Set缓存
     *
     * @param key     key
     * @param itemKey itemKey
     * @return 处理标识
     */
    public Long zrem(String key, String itemKey) {
        return jedisCluster.zrem(key, itemKey);
    }

    /**
     * 根据Score获取Zset内容
     *
     * @param key    key
     * @param min    min
     * @param max    max
     * @param offset offset
     * @param count  count
     * @return 集合
     */
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return jedisCluster.zrangeByScore(key, min, max, offset, count);
    }

    /**
     * 根据Score获取Zset内容
     *
     * @param key    key
     * @param min    min
     * @param max    max
     * @param offset offset
     * @param count  count
     * @return 集合
     */
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset,
                                              int count) {
        return jedisCluster.zrangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * 设置List
     *
     * @param k key
     * @param v value
     */
    public void rightPush(String k, String v) {
        jedisCluster.rpush(k, v);
    }

    /**
     * 获取List内容
     *
     * @param k k
     * @return value
     */
    public String leftPop(String k) {
        return jedisCluster.lpop(k);
    }

    /**
     * 获取多个List内容
     *
     * @param key   key
     * @param start start
     * @param size  size
     * @return 内容
     */
    public List<String> lrange(String key, int start, int size) {
        return jedisCluster.lrange(key, start, size);
    }

    /**
     * 删除多个List内容
     *
     * @param key   key
     * @param jobId key
     * @return 处理标识
     */
    public boolean lrem(String key, String jobId) {
        return jedisCluster.lrem(key, -1, jobId) > 0;
    }

    //=================================================

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }
}
