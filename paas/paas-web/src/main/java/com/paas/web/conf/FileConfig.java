package com.paas.web.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
/**
 * create by sumerian on 2020/6/18
 * <p>
 * desc:配置文件上传大小
 **/
@Configuration
public class FileConfig {
    private static final int maxFileSize = 10;

        @Bean
        public MultipartConfigElement multipartConfigElement(){
            MultipartConfigFactory factory = new MultipartConfigFactory();
            //  单个数据大小 10M
            factory.setMaxFileSize("10240KB");
            /// 总上传数据大小 10M
            factory.setMaxRequestSize("10240KB");
            return factory.createMultipartConfig();
        }
}
