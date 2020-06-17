package com.paas.service.routeservice;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * create by sumerian on 2020/6/17
 * <p>
 * desc:
 **/
@Slf4j
@Component
public class DynamicRouteForZuul extends SimpleRouteLocator implements RefreshableRouteLocator {
    @Autowired
    private ZuulProperties properties;


    @Autowired
    private DynamicRouteServiceImplByNacos2 dynamicRouteServiceImplByNacos2;


    public DynamicRouteForZuul(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @SneakyThrows
    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        //从springboot中的application.properties中加载路由信息
        routesMap.putAll(super.locateRoutes());
        //从nacos中加载路由信息
        routesMap.putAll(dynamicRouteServiceImplByNacos2.getRoute());
        //优化配置
        Map<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        routesMap.forEach((k, v) -> {
            if (!k.startsWith("/")) {
                k = k + "/";
            }
            if (Objects.nonNull(this.properties.getPrefix())) {
                k = this.properties.getPrefix() + k;
                if (!k.startsWith("/")) {
                    k = k + "/";
                }
            }
            values.put(k, v);
        });
        return values;
    }
}
