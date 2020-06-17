package com.example.saasfeign;

import feign.RequestInterceptor;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * create by sumerian on 2020/6/17
 * <p>
 * desc:
 **/

@Configuration
@EnableFeignClients(basePackages = "com.example.saasfeign")
@ConditionalOnProperty(prefix = "feign", value = "enable", matchIfMissing = true)
public class FeignAutoConfiguration {
    @Bean
    public RequestInterceptor traceFeignRequestInterceptor() {
        return new FeignTraceInterceptor();
    }

    /***
     * 替换Feign底层的HTTP客户端实现为HttpClient，来提升Feign的通讯性能
     * 用于提供一个HTTP连接池，并实现连接池管理。
     * 主要目的是，提供一个合理的资源回收方式
     * @return
     */
    @Bean
    public HttpClient httpClient() {
        System.out.println("init feign httpclient configuration ");
        // 生成默认请求配置
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        // 超时时间
        requestConfigBuilder.setSocketTimeout(5 * 1000);
        // 连接时间
        requestConfigBuilder.setConnectTimeout(5 * 1000);
        RequestConfig defaultRequestConfig = requestConfigBuilder.build();
        // 连接池配置
        // 长连接保持30秒
        final PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.MILLISECONDS);
        // 总连接数
        pollingConnectionManager.setMaxTotal(5000);
        // 同路由的并发数
        pollingConnectionManager.setDefaultMaxPerRoute(100);

        // httpclient 配置
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 保持长连接配置，需要在头添加Keep-Alive
        httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        httpClientBuilder.setConnectionManager(pollingConnectionManager);
        httpClientBuilder.setDefaultRequestConfig(defaultRequestConfig);
        HttpClient client = httpClientBuilder.build();
        // 启动定时器，定时回收过期的连接， 最重要。 如果没有定义回收策略。连接池会在运行一段时间后失效。
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory
                        .Builder()
                        .namingPattern("scheduled-pool-%d")
                        .daemon(true)
                        .build());
        service.schedule(() -> {
            pollingConnectionManager.closeExpiredConnections();
            pollingConnectionManager.closeIdleConnections(5, TimeUnit.SECONDS);
        }, 10 * 1000, TimeUnit.MILLISECONDS);

//
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                pollingConnectionManager.closeExpiredConnections();
//                pollingConnectionManager.closeIdleConnections(5, TimeUnit.SECONDS);
//            }
//        }, 10 * 1000, 5 * 1000);
        System.out.println("===== Apache httpclient 初始化连接池===");
        return client;
    }

}
