package com.tangzhe.consumer;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

/**
 * 非注解形式的Hystrix
 * Created by 唐哲
 * 2017-12-12 20:17
 */
public class HelloServiceCommand extends HystrixCommand<String> {

    private RestTemplate restTemplate;

    protected HelloServiceCommand(String commandGroupKey, RestTemplate restTemplate) {
        super(HystrixCommandGroupKey.Factory.asKey(commandGroupKey));
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception {
        System.out.println(Thread.currentThread().getName());
        return restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
    }

    @Override
    protected String getFallback() {
        return "error";
    }

}
