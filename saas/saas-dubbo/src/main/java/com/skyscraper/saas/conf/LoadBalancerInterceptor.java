package com.skyscraper.saas.conf;

import org.springframework.core.PriorityOrdered;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * create by sumerian on 2020/6/1
 * <p>
 * desc:
 **/
public class LoadBalancerInterceptor implements ClientHttpRequestInterceptor, PriorityOrdered {

    private ClientHttpRequestFactory clientHttpRequestFactory;

    public LoadBalancerInterceptor(ClientHttpRequestFactory clientHttpRequestFactory) {
        this.clientHttpRequestFactory = clientHttpRequestFactory;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        return null;
    }
}
