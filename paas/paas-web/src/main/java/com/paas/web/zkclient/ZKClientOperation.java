package com.paas.web.zkclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * create by sumerian on 2020/6/18
 * <p>
 * desc:zookeeper curator 客户端的使用
 **/
@Component
@RefreshScope
@Slf4j
public class ZKClientOperation {
    private static CuratorFramework zkClient;
    @Value("${spring.client.zookeeper.connect-string}")
    private String connectStr;

    private static final Integer CONNECTION_TIMEOUT_MS = 15000;//连接超时时间
    private static final Integer SESSION_TIMEOUT_MS = 60 * 1000;
    private static final Integer RETRY_SLEEP_MS = 300;
    private static final Integer MAX_RETRIES = 3;


    @PostConstruct
    public void init() {
        try {

            zkClient = CuratorFrameworkFactory
                    .builder()
                    .connectString(connectStr)
                    .retryPolicy(new ExponentialBackoffRetry(RETRY_SLEEP_MS, MAX_RETRIES))
                    .connectionTimeoutMs(CONNECTION_TIMEOUT_MS)
                    .sessionTimeoutMs(SESSION_TIMEOUT_MS)
                    .build();
            zkClient.start();
            log.info("ZKClientOperation init success....");
        } catch (Exception e) {
            log.error("ZKClientOperation init error....");
        }
    }

    //单例模式获取zkClient
    public CuratorFramework getZkClient() {
        try {
            if (zkClient.getZookeeperClient().isConnected()) {
                return zkClient;
            }
        } catch (Exception e) {
            log.error("ZKClient  error....");
        }
        return null;
    }


    @PreDestroy
    public void destory() {
        try {
            CloseableUtils.closeQuietly(zkClient);
            log.info("ZKClientOperation destory success....");
        } catch (Exception e) {
            log.info("ZKClientOperation destory error....");
        }
    }
}
