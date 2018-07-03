package com.tangzhe.cloud.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Created by 唐哲
 * 2017-12-18 12:04
 */
//@EnableBinding(Source.class)
@EnableBinding(MySource.class) //使用自定义source通道
public class SendService {

    //使用框架中的source通道
//    @Autowired
//    Source source;
//
//    public void sendMsg(String msg) {
//        source.output().send(MessageBuilder.withPayload(msg).build());
//    }

    //使用自定义的source通道
    @Autowired
    MySource source;

    public void sendMsg(String msg) {
        source.myOutput().send(MessageBuilder.withPayload(msg).build());
    }

}
