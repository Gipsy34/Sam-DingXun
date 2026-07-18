package com.codeying.component.chat;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Spring上下文工具类，用于在非Spring管理的类中获取Spring Bean
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 根据Bean类型获取Bean实例
     *
     * @param beanType Bean类型
     * @param <T>      Bean类
     * @return Bean实例
     */
    public static <T> T getBean(Class<T> beanType) {
        return applicationContext.getBean(beanType);
    }

    /**
     * 根据Bean名称与Bean类型获取Bean实例
     *
     * @param beanName Bean名称
     * @param beanType Bean类型
     * @param <T>      Bean类
     * @return Bean实例
     */
    public static <T> T getBean(String beanName, Class<T> beanType) {
        return applicationContext.getBean(beanName, beanType);
    }
}
