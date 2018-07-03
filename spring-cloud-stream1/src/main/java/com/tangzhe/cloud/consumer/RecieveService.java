package com.tangzhe.cloud.consumer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * Created by 唐哲
 * 2017-12-18 12:15
 */
@EnableBinding(Sink.class)
public class RecieveService {

    @StreamListener(Sink.INPUT)
    public void recieve(Object payload) {
        System.out.println(payload);
    }

}
