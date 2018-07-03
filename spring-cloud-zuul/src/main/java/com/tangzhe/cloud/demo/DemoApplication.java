package com.tangzhe.cloud.demo;

import com.tangzhe.cloud.demo.zuul.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

/**
 * Created by 唐哲
 * 2017-12-13 17:59
 */
@SpringBootApplication
@EnableZuulProxy
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * 注入自定义过滤器
     */
//    @Bean
//    public TokenFilter tokenFilter() {
//        return new TokenFilter();
//    }

    /**
     * 自定义路径匹配规则
     */
//    @Bean
//    public PatternServiceRouteMapper serviceRouteMapper() {
//        return new PatternServiceRouteMapper("", "");
//    }

}
