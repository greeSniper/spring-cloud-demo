package com.tangzhe.cloud.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 自定义source通道
 * Created by 唐哲
 * 2017-12-18 12:22
 */
public interface MySource {

    @Output("myOutput")
    MessageChannel myOutput();

}
