# v2.0_springboot_demo
Useful demos for springboot based on v2.0

# Programmer Environment
- jdk(v1.8+)
- springboot(v2.0+)
- redis(v3.2+)
- rabbitmq(v3.7.7)

# What's your urgent task to know?
What is the springboot?How does it work?
The answer is to take care of the springboot source,you can click [this](https://www.cnblogs.com/question-sky/p/9360722.html) to have a sight.

# What does this project include?
Now includes redis/jmx/rabbitmq demo which based on the springboot.Samples below

---
Application starter for jmx
```java
package com.example.demo.jmx;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.remoting.rmi.RmiRegistryFactoryBean;

/**
 * @author nanco
 * @create 2018/8/8
 **/
@Configuration
public class JmxAutoConfiguration {

    @Value("${jmx.rmi.host:localhost}")
    private String rmiHost;

    @Value("${jmx.rmi.port:7099}")
    private int rmiPort;

    @Value("${jmx.service.domain:jmxrmi}")
    private String jmxDomain;

    @Bean
    public RmiRegistryFactoryBean rmiRegistry() {
        RmiRegistryFactoryBean factoryBean = new RmiRegistryFactoryBean();
        factoryBean.setPort(rmiPort);
        factoryBean.setAlwaysCreate(true);

        return factoryBean;
    }

    @DependsOn("rmiRegistry")
    @Bean
    public ConnectorServerFactoryBean jmxConnector() {
        ConnectorServerFactoryBean serverFactoryBean = new ConnectorServerFactoryBean();

        serverFactoryBean.setServiceUrl(String.format("service:jmx:rmi://%s:%s/jndi/rmi://%s:%s/%s", rmiHost, rmiPort, rmiHost, rmiPort, jmxDomain));

        return serverFactoryBean;
    }
}

```

---
Rabbitmq demo-producer
```java
package com.example.demo.amqp.producer;

import com.rabbitmq.client.*;

/**
 * @author nanco
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

```

Rabbitmq demo-consumer
```java
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

```

----
Demo for redis
```java
package com.example.demo.redis;

import com.example.demo.redis.operations.RedisApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author nanco
 * -------------
 * -------------
 * @create 2018/8/17
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApplication.class})
public class MainOperationTest {

    private TimeUnit unit_minute = null;

    private int key_timeout = 0;


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Before
    public void before() {
//        Set<String> keys = stringRedisTemplate.keys("*");
//        keys.stream().forEach((key) -> {
//            stringRedisTemplate.delete(key);
//        });

        unit_minute = TimeUnit.MINUTES;

        key_timeout = 5;
    }

    @Test
    public void testHSet() {
        Map<String, String> accountMap = new HashMap<>(8);
        accountMap.put("name", "jingtj");
        accountMap.put("age", "18");
        accountMap.put("email", "questionsky1211@gmail.com");
        accountMap.put("sex", "male");
        accountMap.put("job", "programmer");
        accountMap.put("job_language", "java");


        stringRedisTemplate.opsForHash().putAll("userInfo", accountMap);
    }

    @Test
    public void testHGet() {
        stringRedisTemplate.opsForHash().entries("userInfo").forEach((key, value) -> {
            System.out.println(key + "=" + value);
        });
    }

    @Test
    public void testGet() {
        System.out.println(stringRedisTemplate.opsForValue().get("name"));
    }

    @Test
    public void testSet() {
        stringRedisTemplate.opsForValue().set("name", "jingtj", key_timeout, unit_minute);
        stringRedisTemplate.boundValueOps("name").expire(key_timeout, unit_minute);
    }

    @Test
    public void testLpush() {
        stringRedisTemplate.opsForList().leftPushAll("program_language", new String[]{"java", "php", "object-c", "nodejs", "c++", "c", "python"});
        stringRedisTemplate.opsForList().getOperations().boundListOps("program_language").expire(key_timeout, unit_minute);
    }

    @Test
    public void testLrange() {
        stringRedisTemplate.opsForList().range("program_language", 0, 10)
                .forEach((value) -> {
                    System.out.println(value);
                });
    }

    @Test
    public void testSadd() {
        stringRedisTemplate.opsForSet().add("sex_type", new String[]{"male", "female", "male", "female"});
        stringRedisTemplate.boundSetOps("sex_type").expire(key_timeout, unit_minute);
    }

    @Test
    public void testSmemebers() {
        stringRedisTemplate.opsForSet().members("sex_type").forEach((type) -> {
            System.out.println("sex: " + type);
        });
    }

    @Test
    public void testZadd() {
        stringRedisTemplate.opsForZSet().add("score", "math", 90);
        stringRedisTemplate.opsForZSet().add("score", "chinese", 85);
        stringRedisTemplate.opsForZSet().add("score", "science", 87);
        stringRedisTemplate.opsForZSet().add("score", "english", 95);

        stringRedisTemplate.boundZSetOps("score").expire(key_timeout, unit_minute);
    }

    @Test
    public void testZrange() {
        // default sort by score asc
        stringRedisTemplate.opsForZSet().rangeByScore("score", 0, 100).forEach((course) -> {
            System.out.println(course);
        });

        // get sort object desc
        stringRedisTemplate.opsForZSet().reverseRangeByScoreWithScores("score", 0, 100).forEach(action -> {
            System.out.println(action.getValue() + ":" + action.getScore());
        });
    }

    @Test
    public void testDelKeys(){
        Set<String> hashKeys = stringRedisTemplate.keys("*") ;
        Assert.assertNotEquals("keys in redis is zero" , 0 , hashKeys.size());

        System.out.println(stringRedisTemplate.delete(hashKeys)) ;
    }
}

```


# Conclusion
This project just commits the several plugin demos which integrated by springboot,such as Redis,Rabbitmq,Hbase and so on. 