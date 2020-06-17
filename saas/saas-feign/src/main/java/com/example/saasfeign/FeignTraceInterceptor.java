package com.example.saasfeign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;

import java.util.Collection;
import java.util.UUID;

/**
 * create by sumerian on 2020/6/17
 * <p>
 * desc: 给
 **/

public class FeignTraceInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String headerTrace = traceIdRequestTemplate(requestTemplate);
        if (null == headerTrace) {
            return;
        }
        requestTemplate.header("traceId", traceId());
    }

    /***
     * 生成traceId 方法
     * @param
     * @return
     */
    private String traceId() {
        String traceId = MDC.get("traceId");
        return null == traceId ? UUID.randomUUID().toString().replaceAll("-", "") : traceId;
    }

    private String traceIdRequestTemplate(RequestTemplate requestTemplate) {
        Collection<String> headers = requestTemplate.headers().get("traceId");
        if (null != headers && headers.size() > 0) {
            return "";
        }
        return headers.iterator().next();
    }
}
