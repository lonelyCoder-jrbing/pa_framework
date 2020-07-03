package com.paas.service.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
/**
 * create by sumerian on 2020/6/2
 * desc:全局变量帮助类
 *   使用的时候，直接使用这个类的getBean方法就可以了
 **/
@Configuration
public class SpringContextApplication implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextApplication.applicationContext = applicationContext;
    }

    /**
     * 获取Context
     * @return
     */
    public static ApplicationContext getContext() {
        return SpringContextApplication.applicationContext;
    }

    /**
     * 获取变量
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> t) {
        return SpringContextApplication.applicationContext.getBean(t);
    }
}
