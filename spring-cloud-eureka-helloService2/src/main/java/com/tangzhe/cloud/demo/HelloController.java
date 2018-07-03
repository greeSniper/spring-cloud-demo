package com.tangzhe.cloud.demo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 唐哲
 * 2017-12-10 17:12
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello spring cloud eureka 8082";
    }

    /**
     * 用于测试Hystrix请求合并
     */
    @RequestMapping("/laowangs")
    public List<String> laowangs(String ids) {
        List<String> list = new ArrayList<>();
        list.add("laowang1");
        list.add("laowang2");
        list.add("laowang3");
        return list;
    }

    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    public String hello(@RequestParam String name) {
        return "Hello " + name;
    }

    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    public User hello(@RequestHeader String name, @RequestHeader Integer age) {
        return new User(name, age);
    }

    @RequestMapping(value = "/hello3", method = RequestMethod.POST)
    public String hello(@RequestBody User user) {
        return "Hello " + user.getName() + ", " + user.getAge();
    }

}
