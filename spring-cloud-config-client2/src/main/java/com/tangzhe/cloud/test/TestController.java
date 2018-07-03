package com.tangzhe.cloud.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 唐哲
 * 2017-12-18 10:52
 */
@RestController
@RefreshScope //刷新配置需要这个注解
public class TestController {

    @Value("${name}")
    private String name;
    @Value("${age}")
    private Integer age;

    @RequestMapping("/test")
    public String test() {
        return name + ", " + age;
    }

}
