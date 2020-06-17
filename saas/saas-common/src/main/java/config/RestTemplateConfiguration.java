package config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import utils.PropertyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * create by sumerian on 2020/6/17
 * <p>
 * desc: RestTemplate 配置
 **/
@Configuration
@Import(value = PropertyUtils.class)
public class RestTemplateConfiguration {


    private static final Integer CONNECT_TIMEOUT = 30000;
    private static final Integer READ_TIMEOUT = 30000;


    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    @ConditionalOnMissingBean(ClientHttpRequestFactory.class)
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(READ_TIMEOUT);
        factory.setConnectTimeout(CONNECT_TIMEOUT);
        return factory;
    }

    @Bean
    public TraceInterceptor traceInterceptor() {
        return new TraceInterceptor();
    }

    @Bean
    public SmartInitializingSingleton traceRestTemplateInitializer(@Autowired List<RestTemplate> restTemplates, @Autowired TraceInterceptor traceInterceptor) {
        return () -> {
            for (RestTemplate restTemplate : restTemplates) {
                ArrayList<ClientHttpRequestInterceptor> list = Lists.newArrayList(restTemplate.getInterceptors());
                list.add(traceInterceptor);
                restTemplate.setInterceptors(list);
            }
        };
    }


}
