package com.tangzhe.cloud.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息生产者
 * Created by 唐哲
 * 2017-12-18 11:56
 */
@RestController
public class ProducerController {

    @Autowired
    private SendService sendService;

    @RequestMapping("/send/{msg}")
    public void send(@PathVariable("msg") String msg) {
        sendService.sendMsg(msg);
    }

}
