package com.tangzhe.cloud.demo.feign;

/**
 * 服务降级
 * Created by 唐哲
 * 2017-12-13 20:44
 */
public class FeignFallBack implements FeignService {

    @Override
    public String hello() {
        return "error";
    }

    @Override
    public String hello(String name) {
        return "error";
    }

    @Override
    public User hello(String name, Integer age) {
        return new User();
    }

    @Override
    public String hello(User user) {
        return "error";
    }

}
