package com.cc.cmbc.order.common.configuration;

import com.cc.cmbc.order.common.support.RedisClusterLock;
import com.cc.cmbc.order.common.support.RedisSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisCluster;

/**
 * @author 拓仲 on 2020/3/16
 */
@Configuration
public class OrderAutoConfiguration {

    @Bean
    public RedisSupport redisSupport(JedisCluster jedisCluster){
        RedisSupport redisSupport = new RedisSupport();
        redisSupport.setJedisCluster(jedisCluster);
        return redisSupport;
    }

    @Bean
    public RedisClusterLock redisClusterLock(JedisCluster jedisCluster){
        RedisClusterLock redisClusterLock = new RedisClusterLock();
        redisClusterLock.setJedisCluster(jedisCluster);
        return redisClusterLock;
    }
}
