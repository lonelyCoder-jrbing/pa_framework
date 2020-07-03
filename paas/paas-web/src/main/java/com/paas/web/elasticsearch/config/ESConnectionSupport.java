package com.paas.web.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * create by sumerian on 2020/7/3
 * <p>
 * desc:es客户端连接服务端
 **/
@Configuration
public class ESConnectionSupport {

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(RestClient
                .builder(new HttpHost("localhost", 9200, "http")));
    }

}
