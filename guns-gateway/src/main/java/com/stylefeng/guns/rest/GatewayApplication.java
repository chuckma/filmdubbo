package com.stylefeng.guns.rest;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // 开启异步调用
@EnableDubboConfiguration // 开启dubbo配置
public class GatewayApplication {
    //@EnableHystrixDashboard
    //@EnableCircuitBreaker
    //@EnableHystrix
    public static void main(String[] args) {

        SpringApplication.run(GatewayApplication.class, args);
    }
}
