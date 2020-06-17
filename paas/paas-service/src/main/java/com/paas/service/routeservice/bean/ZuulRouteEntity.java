package com.paas.service.routeservice.bean;

import lombok.Data;

/**
 * create by sumerian on 2020/6/17
 * <p>
 * desc: 路由信息
 **/

@Data
public class ZuulRouteEntity {
    private String id;
    private String path;
    private String serviceId;
    private String url;
    private boolean StripPrefix;
    private boolean retryable;
    private String apiName;
    private boolean enabled;
}
