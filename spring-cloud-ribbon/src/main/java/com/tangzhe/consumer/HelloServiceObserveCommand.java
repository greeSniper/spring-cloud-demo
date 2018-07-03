package com.tangzhe.consumer;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

/**
 * 观察者模式
 * Created by 唐哲
 * 2017-12-12 21:06
 */
public class HelloServiceObserveCommand extends HystrixObservableCommand<String> {

    private RestTemplate restTemplate;

    protected HelloServiceObserveCommand(String commandGroupKey, RestTemplate restTemplate) {
        super(HystrixCommandGroupKey.Factory.asKey(commandGroupKey));
        this.restTemplate = restTemplate;
    }

    @Override
    protected Observable<String> construct() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if(!subscriber.isUnsubscribed()) {
                        String result = restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
                        subscriber.onNext(result);
                        String result1 = restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
                        subscriber.onNext(result1);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    protected Observable<String> resumeWithFallback() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if(!subscriber.isUnsubscribed()) {
                        subscriber.onNext("error");
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
