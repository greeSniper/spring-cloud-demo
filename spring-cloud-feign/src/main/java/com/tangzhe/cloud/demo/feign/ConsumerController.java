package com.tangzhe.cloud.demo.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 唐哲
 * 2017-12-13 18:30
 */
@RestController
public class ConsumerController {

    @Autowired
    private FeignService feignService;

    @RequestMapping("/consumer")
    public String helloConsumer() {
        return feignService.hello();
    }

    @RequestMapping("/consumer1")
    public String helloConsumer1() {
        String result1 = feignService.hello("laowang");
        String result2 = feignService.hello(new User("laowang", 28));
        String result3 = feignService.hello("laowang", 28).toString();
        return result1 + "---" + result2 + "---" + result3;
    }

}
