package com.tangzhe.cloud.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 唐哲
 * 2017-12-10 17:12
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello spring cloud eureka";
    }

}
