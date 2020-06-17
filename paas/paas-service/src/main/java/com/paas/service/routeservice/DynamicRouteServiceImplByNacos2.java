package com.paas.service.routeservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.paas.service.routeservice.bean.ZuulRouteEntity;
import com.sun.glass.ui.Application;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Executor;

/**
 * create by sumerian on 2020/6/2
 * <p>
 * desc: nacos 动态刷新配置
 **/
@Component
@RefreshScope
@Slf4j
public class DynamicRouteServiceImplByNacos2 implements ApplicationEventPublisherAware {

    private static Logger logger = LoggerFactory.getLogger(DynamicRouteServiceImplByNacos2.class);
    @Autowired
    DynamicRouteForZuul routes;


    @Value("${nacos.dataId}")
    private String dataId;

    @Value("${nacos.group}")
    private String group;
    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;

    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespaceId;
    private ApplicationEventPublisher publisher;
    private Long timeOut;

    private volatile List<ZuulRouteEntity> zuulRouteEntities;

    private volatile static ConfigService configService;


    @Bean
    public String routeServiceInit() {
        logger.info("-------------------------------------------------------------------------------");
        logger.info("dataId:{}", dataId);
        logger.info("group:{}", group);
        logger.info("serverAddr:{}", serverAddr);
        return "success";
    }

    public Map<String, ZuulProperties.ZuulRoute> getRoute() throws NacosException {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();
        List<ZuulRouteEntity> results = routes();
        for (ZuulRouteEntity result : results) {
            if (Objects.isNull(result.getPath())) {
                continue;
            }
            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            try {
                BeanUtils.copyProperties(result, zuulRoute);
            } catch (Exception e) {
                log.info("route config error{}", e);
            }
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }

    private List<ZuulRouteEntity> routes() throws NacosException {
        if (Objects.nonNull(zuulRouteEntities)) {
            return zuulRouteEntities;
        }
        zuulRouteEntities = ListeningNacos();
        return zuulRouteEntities;
    }

    public void addLisener() throws NacosException {
        try {
            getConfigService().addListener(dataId, group, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String routConfig) {
                    //刷新路由
                    log.info("nacos refresh route=={}", routConfig);
                    zuulRouteEntities = JSON.parseArray(routConfig, ZuulRouteEntity.class);
                    RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routes);
                    publisher.publishEvent(routesRefreshedEvent);
                }
            });
        } catch (Exception e) {
            log.info("nacos addListener error");
        }
    }


    private List<ZuulRouteEntity> ListeningNacos() throws NacosException {
        try {
            ConfigService configService = getConfigService();
            String content = configService.getConfig(dataId, group, timeOut);
            log.info("content from nacos=={}", content);
            return JSON.parseArray(content, ZuulRouteEntity.class);
        } catch (Exception e) {
            log.info("listenerNacos route error", e);
        }
        return Collections.emptyList();
    }

    private ConfigService getConfigService() throws NacosException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.put(PropertyKeyConst.NAMESPACE, namespaceId);
        if (Objects.isNull(configService)) {
            configService = NacosFactory.createConfigService(properties);
        }
        return configService;
    }


    @SneakyThrows
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
        ListeningNacos();

    }
}
