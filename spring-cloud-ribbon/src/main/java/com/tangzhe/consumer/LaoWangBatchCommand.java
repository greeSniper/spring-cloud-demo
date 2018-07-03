package com.tangzhe.consumer;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 唐哲
 * 2017-12-13 17:00
 */
public class LaoWangBatchCommand extends HystrixCollapser<List<String>, String, Long> {

    private Long id;
    private RestTemplate restTemplate;

    public LaoWangBatchCommand(Long id, RestTemplate restTemplate) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("laowangbatch"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter()
                        .withTimerDelayInMilliseconds(200)));

        this.id = id;
        this.restTemplate = restTemplate;
    }

    @Override
    public Long getRequestArgument() {
        return id;
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Long>> collection) {
        List<Long> ids = new ArrayList<>(collection.size());
        ids.addAll(collection.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        LaoWangCommand command = new LaoWangCommand("laowang", restTemplate, ids);
        return command;
    }

    @Override
    protected void mapResponseToRequests(List<String> results, Collection<CollapsedRequest<String, Long>> collection) {
        System.out.println("分配批量请求结果...");
        int count = 0;
        for(CollapsedRequest<String, Long> collapsedRequest : collection) {
            String result = results.get(count++);
            collapsedRequest.setResponse(result);
        }
    }

}
