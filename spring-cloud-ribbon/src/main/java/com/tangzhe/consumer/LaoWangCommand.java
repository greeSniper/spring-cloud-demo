package com.tangzhe.consumer;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 唐哲
 * 2017-12-13 16:51
 */
public class LaoWangCommand extends HystrixCommand<List<String>> {

    private RestTemplate restTemplate;

    private List<Long> ids;

    protected LaoWangCommand(String commandGroupKey, RestTemplate restTemplate, List<Long> ids) {
        super(HystrixCommandGroupKey.Factory.asKey(commandGroupKey));
        this.restTemplate = restTemplate;
        this.ids = ids;
    }

    @Override
    protected List<String> run() throws Exception {
        System.out.println("发送请求...参数为：" + ids.toString() + Thread.currentThread().getName());
        String[] result = restTemplate.getForEntity("http://HELLO-SERVICE/laowangs?ids={1}", String[].class, StringUtils.join(ids, ",")).getBody();
        return Arrays.asList(result);
    }

}
