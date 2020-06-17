package utils;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import jdk.nashorn.internal.objects.annotations.Property;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.Properties;

/**
 * create by sumerian on 2020/6/17
 * <p>
 * desc:  配置文件读取工具类
 **/
@Configuration
public class PropertyUtils {

    private static String serverAddr;
    private static String namespaceId;
    private static String groupId;


    @Value("${spring.cloud.nacos.config.server.addr:}")
    void setServerAddr(String server) {
        PropertyUtils.serverAddr = server;
    }

    @Value("${spring.cloud.nacos.config.namespace:}")
    void setNamespaceId(String namespaceId) {
        PropertyUtils.namespaceId = namespaceId;
    }

    @Value("${spring.cloud.nacos.config.namespace:}")
    void setGroupId(String groupId) {
        PropertyUtils.groupId = groupId;
    }

    private static volatile ConfigService configService;

    private static ConfigService getConfigService() throws NacosException {
        if (Objects.isNull(serverAddr)) {
            return null;
        }
        if (Objects.nonNull(configService)) {
            return configService;
        }
        return initConfigService();
    }


    /**
     * 单例模式获取configService对象
     *
     * @return
     * @throws NacosException
     */
    private static synchronized ConfigService initConfigService() throws NacosException {
        if (Objects.nonNull(configService)) {
            return configService;
        }
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        properties.put("namespace", namespaceId);
        configService = NacosFactory.createConfigService(properties);
        return configService;
    }

    /***
     *  根据nacos的dataId获取nacos配置中的value
     * @param dataId
     * @return
     * @throws NacosException
     */
    private static String getContent(String dataId) throws NacosException {
        if (Objects.isNull(configService)) return null;
        ConfigService configService = getConfigService();
        return configService.getConfig(dataId, groupId, 3000L);
    }

}
