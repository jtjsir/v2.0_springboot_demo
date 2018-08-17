package com.example.demo.amqp.producer;

import com.rabbitmq.client.*;

/**
 * @author jingtj15578
 * @create 2018/8/10
 **/
public class Producer {

    public static void main(String[] args) throws Exception {
        // create connectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);

        // get connection
        Connection mqConnection = connectionFactory.newConnection();
        // use channel to transport message
        Channel mqChannel = mqConnection.createChannel();

        // declare exchange type
        AMQP.Exchange.DeclareOk exchangeDeclareOk = mqChannel.exchangeDeclare("halo_mq_topic", BuiltinExchangeType.TOPIC, false);
        System.out.println("exchangeDeclareOk's protocol method name: " + exchangeDeclareOk.protocolMethodName());

        // declare queue
        mqChannel.queueDeclare("hello_queue", false, false, false, null);

        // declare routing key
        String routingKey = "halo.#";

        // bind queue and exchange using routing key
        mqChannel.queueBind("hello_queue", "halo_mq_topic", routingKey);

        // publish message
        mqChannel.basicPublish("halo_mq_topic", "halo.mq", null, "hello_world".getBytes());

        // finally close the channel and connection
        mqChannel.close();
        mqConnection.close();
    }
}
