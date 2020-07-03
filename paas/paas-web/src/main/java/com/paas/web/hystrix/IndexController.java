package com.paas.web.hystrix;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/hello")
    public String hello(String name){
        return "hi " + name;
    }

    @RequestMapping("/timeout")
    public String timeout(){
        try{
            //睡5秒，网关Hystrix3秒超时，会触发熔断降级操作
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "timeout";
    }
}
