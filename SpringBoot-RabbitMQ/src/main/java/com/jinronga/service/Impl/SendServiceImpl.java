package com.jinronga.service.Impl;

import com.jinronga.pojo.User;
import com.jinronga.service.SenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class SendServiceImpl implements SenService {
    @Autowired
    AmqpAdmin amqpAdmin;
    @Autowired
    RabbitTemplate rabbitTemplate;

    //发送消息
    @Override
    public void Message() {
        User user = new User();
        user.setAge(3);
        user.setGender(1);
        user.setName("张三");

        //1、发送消息
        rabbitTemplate.convertAndSend("hello-java-exchange", "hello.java",user);
        log.info("发送成功{}", user);
    }

    @Override
    public void createQueue() {
        Queue queue = new Queue("hello.mq", true, false, false);
        amqpAdmin.declareQueue(queue);
        log.info("创建成功[{}]！！！", "hello.mq");
    }
    @Override
    public void createExchange() {
        //amqpAdmin
        //Exchange 交换机
        //参数：String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        /**
         * 参数明细
         * 1、queue 队列名称
         * 2、durable 是否持久化，如果持久化，mq重启后队列还在
         * 3、exclusive 是否独占连接，队列只允许在该连接中访问，如果connection连接关闭队列则自动删除,如果将此参数设置true可用于临时队列的创建
         * 4、autoDelete 自动删除，队列不再使用时是否自动删除此队列，如果将此参数和exclusive参数设置为true就可以实现临时队列（队列不用了就自动删除）
         * 5、arguments 参数，可以设置一个队列的扩展参数，比如：可设置存活时间
         */
        DirectExchange directExchange = new DirectExchange("springboot-rabbitMQ");
        amqpAdmin.declareExchange(directExchange);
        log.info("创建", "springboot-rabbitMQ");
    }

    /**
     * 队列绑定交换机
     */
    @Override
    public void createBinding() {
        //String destination【目的地】,
        // DestinationType destinationType 【目的地类型】, String exchange【交换机】, String routingKey 【路由件】,
        //			@Nullable Map<String, Object> arguments【自定义参数】
        //将exchange指定的交换机和destination目的地进行绑定，使用routingKey指定的路由键
        Binding binding = new Binding("hello.mq", Binding.DestinationType.QUEUE, "springboot-rabbitMQ", "hello.mq", null);


        amqpAdmin.declareBinding(binding);
        log.info("绑定成功[{}]", "hello-mq-bind");
    }
}
