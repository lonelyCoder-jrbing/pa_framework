package com.skyscraper.saas.conf;

import com.alibaba.cloud.dubbo.annotation.DubboTransported;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

/**
 * create by sumerian on 2020/6/1
 * <p>
 * desc: lb 过滤器配置
 **/
@Configuration
public class LoadbalancedRestTemplateConfig {

    @Bean
    public SmartInitializingSingleton mixLoadbalancedRestTemplateInitializer(@Autowired List<RestTemplate> restTemplates,
                                                                             @Autowired LoadBalancerInterceptor loadBalancerInterceptor) {
        return () -> {
            for (RestTemplate restTemplate : restTemplates) {
                List<ClientHttpRequestInterceptor> clientHttpRequestInterceptors = new ArrayList<>(restTemplate.getInterceptors());
                clientHttpRequestInterceptors.add(loadBalancerInterceptor);
                restTemplate.setInterceptors(clientHttpRequestInterceptors);
            }
        };
    }

    @Bean
    @SuppressWarnings("")
    public LoadBalancerInterceptor loadBalancerInterceptor(ClientHttpRequestFactory clientHttpRequestFactory) {
        return new LoadBalancerInterceptor(clientHttpRequestFactory);
    }

    @Bean
    @LoadBalanced
    @DubboTransported
    public RestTemplate restTemplate() {
        return new RestTemplate();

    }


}
