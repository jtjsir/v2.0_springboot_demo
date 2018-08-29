package com.example.demo.amqp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author nanco
 * -------------
 * @RabbitListener to receive certain message data
 * -------------
 * @create 2018/8/29
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AmqpApplication.class})
public class RabbitmqAnnotationTest {

    /**
     * same as <a>AmqpTemplate<a/>
     */
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * same as <a>AmqpAdmin<a/>
     */
    @Resource
    private RabbitAdmin rabbitAdmin;

    /**
     * it will auto declare queue and exchange
     *
     * @param message waiting consume message
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "rabbit_direct_queue", durable = "true", autoDelete = "false"),
                    key = "rabbit_direct",
                    exchange = @Exchange(name = "rabbit_direct_exchange", type = ExchangeTypes.DIRECT)
            )
    })
    public void rabbitMsgListener(String message) {
        System.out.println(message);
    }

    @Test
    public void send() {
        rabbitTemplate.convertAndSend("rabbit_direct_exchange", "rabbit_direct", "hello,rabbit");
    }

    @Test
    public void delete() {
        rabbitAdmin.deleteExchange("rabbit_direct_exchange");
        rabbitAdmin.deleteQueue("rabbit_direct_queue");
    }
}
