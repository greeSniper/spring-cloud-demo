package com.tangzhe.consumer;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 服务消费者
 * Created by 唐哲
 * 2017-12-12 19:37
 */
@RestController
public class ConsumerController {

//    @Autowired
//    private LoadBalancerClient loadBalancerClient;

//    @Autowired
//    private RestTemplate restTemplate;

    /**
     * 原生ribbon
     */
//    @RequestMapping("/consumer")
//    public String helloConsumer() {
//        ServiceInstance instance = loadBalancerClient.choose("hello");
//        URI uri = URI.create(String.format("http://%s:%s", instance.getHost(), instance.getPort()));
//        return uri.toString();
//    }

    /**
     * 基于Eureka的ribbon
     */
//    @RequestMapping("/consumer")
//    public String helloConsumer() {
//        return restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
//    }

    @Autowired
    private HelloService helloService;

    /**
     * 注解形式的Hystrix请求熔断、服务降级
     */
//    @RequestMapping("/consumer")
//    public String helloConsumer() throws ExecutionException, InterruptedException {
//        String result = helloService.helloService();
//        return result;
//    }

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 非注解形式的Hystrix请求熔断、服务降级
     */
//    @RequestMapping("/consumer")
//    public String helloConsumer() throws ExecutionException, InterruptedException {
//        HelloServiceCommand command = new HelloServiceCommand("hello", restTemplate);
//        //阻塞
//        //String result = command.execute();
//
//        //IO 阻塞IO   NIO 非阻塞式 ： Future 将来式   Callable 回调式
//        //非阻塞
//        Future<String> future = command.queue();
//        String result = future.get();
//
//        return result;
//    }

    /**
     * 观察者模式的Hystrix请求熔断、服务降级
     */
    @RequestMapping("/consumer")
    public String helloConsumer() throws ExecutionException, InterruptedException {
        List<String> list = new ArrayList<>();

        HelloServiceObserveCommand command = new HelloServiceObserveCommand("hello", restTemplate);
        Observable observable = command.observe(); //热执行
        //Observable observable = command.toObservable(); //冷执行
        observable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("聚合完了所有的查询请求！");
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onNext(String o) {
                list.add(o);
            }
        });

        return list.toString();
    }

    /**
     * 非注解形式的
     * Hystrix请求合并
     */
    @RequestMapping("/consumer2")
    public String helloConsumer2() throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        LaoWangBatchCommand command1 = new LaoWangBatchCommand(1L, restTemplate);
        LaoWangBatchCommand command2 = new LaoWangBatchCommand(2L, restTemplate);
        LaoWangBatchCommand command3 = new LaoWangBatchCommand(3L, restTemplate);

        //command1和command2请求合并
        Future<String> future1 = command1.queue();
        Future<String> future2 = command2.queue();

        String r1 = future1.get();
        String r2 = future2.get();

        Thread.sleep(2000);

        //command3请求独立
        Future<String> future3 = command3.queue();
        String r3 = future3.get();

        /*
            发送请求...参数为：[1, 2]hystrix-LaoWangService-1
            laowang1
            laowang2
            发送请求...参数为：[3]hystrix-LaoWangService-2
            laowang1
         */
        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);

        context.close();

        return r1 + r2 + r3;
    }

    @Autowired
    private LaoWangService laoWangService;

    /**
     * 注解形式的
     * Hystrix请求合并
     */
    @RequestMapping("/consumer3")
    public String helloConsumer3() throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        Future<String> future1 = laoWangService.batchGetLaoWang(1L);
        Future<String> future2 = laoWangService.batchGetLaoWang(2L);

        Thread.sleep(2000);
        Future<String> future3 = laoWangService.batchGetLaoWang(3L);

        /*
            发送请求...参数为：[1, 2]hystrix-LaoWangService-1
            laowang1
            laowang2
            发送请求...参数为：[3]hystrix-LaoWangService-2
            laowang1
         */
        System.out.println(future1.get());
        System.out.println(future2.get());
        System.out.println(future3.get());

        context.close();

        return null;
    }

}
