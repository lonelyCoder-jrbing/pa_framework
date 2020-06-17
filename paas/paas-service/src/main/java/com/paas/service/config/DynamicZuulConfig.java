package com.paas.service.config;

import com.paas.service.routeservice.DynamicRouteForZuul;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * create by sumerian on 2020/6/17
 * <p>
 * desc:
 **/
@Configuration
public class DynamicZuulConfig {

    @Autowired
    private ZuulProperties zuulProperties;
    @Autowired
    private ServerProperties serverProperties;


    @Bean
    public DynamicRouteForZuul routeLocator() {
        return new DynamicRouteForZuul(this.serverProperties.getServlet().getContextPath(),this.zuulProperties);
    }
}
