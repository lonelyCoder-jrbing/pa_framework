package com.skyscraper.paas.paas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PaasApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaasApplication.class, args);
    }

}
