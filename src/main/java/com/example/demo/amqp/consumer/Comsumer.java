package com.example.demo.amqp.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author nanco
 * @create 2018/8/10
 **/
public class Comsumer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);

        Connection mqConnection = connectionFactory.newConnection();
        final Channel mqChannel = mqConnection.createChannel();

        mqChannel.queueDeclare("hello_queue", false, false, false, null);
        mqChannel.basicConsume("hello_queue", new DefaultConsumer(mqChannel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException {
                String exchangeType = envelope.getExchange();
                String routingKey = envelope.getRoutingKey();
                String contentType = properties.getContentType();

                System.out.println("exchangeType: " + exchangeType + " , routingKey: " + routingKey + " , contentType: " + contentType);

                long deliverTag = envelope.getDeliveryTag();
                // ack message
                mqChannel.basicAck(deliverTag, false);

                System.out.println("recv content: " + new String(body));
            }
        });
    }
}
