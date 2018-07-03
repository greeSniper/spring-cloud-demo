package com.tangzhe.consumer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 注解形式的Hystrix
 * Created by 唐哲
 * 2017-12-12 20:10
 */
@Service
public class HelloService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 服务降级
     */
    @HystrixCommand(fallbackMethod = "helloFallBack")
    public String helloService() throws ExecutionException, InterruptedException {
        //阻塞
        //return restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();

        //非阻塞
        //future注解方式的调用
        Future<String> future = new AsyncResult<String>() {
            @Override
            public String invoke() {
                return restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
            }
        };
        return future.get();
    }

    public String helloFallBack() {
        return "error";
    }

}
