package com.example.demo.amqp;

import com.example.demo.amqp.app.AmqpApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author nanco
 * -------------
 * amqp template api
 * -------------
 * @create 2018/8/29
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AmqpApplication.class})
public class AmqpTemplateTest {

    /**
     * message send and receive operation api
     */
    @Resource
    private AmqpTemplate amqpTemplate;

    /**
     * exchange/queue/binding operation api
     */
    @Resource
    private AmqpAdmin amqpAdmin;

    @Test
    public void declareDirectExchange() {
        // direct exchange
        ExchangeBuilder exchangeBuilder = new ExchangeBuilder("amqp_direct_exchange", "direct");
        exchangeBuilder.durable(true);
        amqpAdmin.declareExchange(exchangeBuilder.build());
    }

    @Test
    public void declareTopicExchange() {
        Exchange topicExchange = new TopicExchange("amqp_topic_exchange", true, false);

        amqpAdmin.declareExchange(topicExchange);
    }

    @Test
    public void declareDirectQueue() {
        QueueBuilder queueBuilder = QueueBuilder.durable("amqp_direct_queue");

        amqpAdmin.declareQueue(queueBuilder.build());
    }

    @Test
    public void declareTopicQueue() {
        QueueBuilder queueBuilder = QueueBuilder.durable("amqp_topic_queue");

        amqpAdmin.declareQueue(queueBuilder.build());
    }

    /**
     * one exchange can bind many queues,also one queue can be bind by many exchanges
     */
    @Test
    public void declareDirectBinding() {
        Binding directBinding = BindingBuilder.
                bind(QueueBuilder.nonDurable("amqp_direct_queue").build()).
                to(new DirectExchange("amqp_direct_exchange")).with("halo_direct");

        amqpAdmin.declareBinding(directBinding);
    }

    @Test
    public void declareTopicBinding() {
        Binding topicBinding = BindingBuilder.
                bind(QueueBuilder.nonDurable("amqp_topic_queue").build()).
                to(new TopicExchange("amqp_topic_exchange")).with("msg.topic.#");

        amqpAdmin.declareBinding(topicBinding);
    }

    /**
     * send message must assign exchange and routing key
     */
    @Test
    public void sendMsg() {
        amqpTemplate.convertAndSend("amqp_direct_exchange", "halo_direct", "hello,direct amqp");

        amqpTemplate.convertAndSend("amqp_topic_exchange", "msg.topic.halo", "hello,topic amqp");
    }

    /**
     * default ack the message,so the message will be delete in the amqp
     */
    @Test
    public void receiveMsg() {
        Message msg = amqpTemplate.receive("amqp_direct_queue");
        System.out.println(msg.toString());

        System.out.println(new String(amqpTemplate.receive("amqp_topic_queue").getBody()));
    }

    @Test
    public void delete() {
        Assert.assertEquals("direct_change delete success", true, amqpAdmin.deleteExchange("amqp_direct_exchange"));
        Assert.assertEquals("topic_change delete success", true, amqpAdmin.deleteExchange("amqp_topic_exchange"));

        Assert.assertEquals("direct_queue delete success", true, amqpAdmin.deleteQueue("amqp_direct_queue"));
        Assert.assertEquals("topic_queue delete success", true, amqpAdmin.deleteQueue("amqp_topic_queue"));
    }
}
