package com.cc.cmbc.order.common.config;

import com.cc.cmbc.order.common.log.ActiveMQLogConverter;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.ConnectionFactory;

/**
 * @author 拓仲 on 2020/3/29
 */
@Configuration
@EnableConfigurationProperties(ActiveMQProperties.class)
public class ActiveMQConnectionConfig {


    @Bean("connectionFactory")
    public ActiveMQConnectionFactory connectionFactory(ActiveMQProperties properties) {
        return new ActiveMQConnectionFactory(properties.getBrokerUrl());
    }

    @Bean(destroyMethod = "stop")
    @ConditionalOnProperty(prefix = "spring.activemq.pool", name = "enabled", havingValue = "true", matchIfMissing = false)
    public PooledConnectionFactory pooledJmsConnectionFactory(
            ActiveMQProperties properties) {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(
                new ActiveMQConnectionFactory(properties.getBrokerUrl()));
        ActiveMQProperties.Pool pool = properties.getPool();
        pooledConnectionFactory.setBlockIfSessionPoolIsFull(pool.isBlockIfFull());
        if (pool.getBlockIfFullTimeout() != null) {
            pooledConnectionFactory.setBlockIfSessionPoolIsFullTimeout(
                    pool.getBlockIfFullTimeout().toMillis());
        }
        pooledConnectionFactory
                .setCreateConnectionOnStartup(pool.isCreateConnectionOnStartup());
        if (pool.getExpiryTimeout() != null) {
            pooledConnectionFactory
                    .setExpiryTimeout(pool.getExpiryTimeout().toMillis());
        }
        if (pool.getIdleTimeout() != null) {
            pooledConnectionFactory
                    .setIdleTimeout((int) pool.getIdleTimeout().toMillis());
        }
        pooledConnectionFactory.setMaxConnections(pool.getMaxConnections());
        pooledConnectionFactory.setMaximumActiveSessionPerConnection(
                pool.getMaximumActiveSessionPerConnection());
        pooledConnectionFactory
                .setReconnectOnException(pool.isReconnectOnException());
        if (pool.getTimeBetweenExpirationCheck() != null) {
            pooledConnectionFactory.setTimeBetweenExpirationCheckMillis(
                    pool.getTimeBetweenExpirationCheck().toMillis());
        }
        pooledConnectionFactory
                .setUseAnonymousProducers(pool.isUseAnonymousProducers());
        return pooledConnectionFactory;
    }


    @Bean("activeMQLogConverter")
    public MessageConverter activeMQLogConverter() {
        return new ActiveMQLogConverter();
    }



    @Bean("jmsTemplate")
    JmsTemplate jmsTemplate(@Qualifier("activeMQLogConverter")MessageConverter activeMQLogConverter,@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setMessageConverter(activeMQLogConverter);
        //设置优先级，0最低，9最高
        jmsTemplate.setPriority(9);
        return jmsTemplate;
    }

    @Bean
    public JmsListenerContainerFactory jmsListenerQueueContainer(ActiveMQConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(Boolean.FALSE);
        factory.setMessageConverter(new ActiveMQLogConverter());
        return  factory;
    }


}
