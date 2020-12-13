package com.jinronga.service.Impl;

import com.jinronga.pojo.User;
import com.jinronga.service.ReceiveService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消息监听类
 */

@Component
public class ReceiveServiceImpl implements ReceiveService {

    @RabbitListener(queues = "hello-java-Queue")//监听的队列名称hello-java-Queue

//    @RabbitHandler
    @Override
    public void process(User user) {
        System.out.println("监听的消息：" + user.toString());
    }
}
