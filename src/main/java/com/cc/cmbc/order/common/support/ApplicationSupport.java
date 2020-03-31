package com.cc.cmbc.order.common.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author 拓仲 on 2020/3/16
 */
@Component
public class ApplicationSupport implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationSupport.class);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public <T> T getBean(String name, Class<T> clazz) {
        try {
            return applicationContext.getBean(name, clazz);
        } catch (BeansException e) {
            logger.error("获取服务BEAN失败", e);
            return null;
        }
    }
}
