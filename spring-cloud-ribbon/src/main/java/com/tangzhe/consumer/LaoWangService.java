package com.tangzhe.consumer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 注解形式的Hystri请求合并
 * Created by 唐哲
 * 2017-12-13 17:40
 */
@Service
public class LaoWangService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCollapser(batchMethod = "getLaoWang", collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds", value = "200")})
    public Future<String> batchGetLaoWang(Long id) {
        return null;
    }

    @HystrixCommand
    public List<String> getLaoWang(List<Long> ids) {
        System.out.println("发送请求...参数为：" + ids.toString() + Thread.currentThread().getName());
        String[] result = restTemplate.getForEntity("http://HELLO-SERVICE/laowangs?ids={1}", String[].class, StringUtils.join(ids, ",")).getBody();
        return Arrays.asList(result);
    }

}
