package com.example.demo.amqp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @author nanco
 * -------------
 * -------------
 * @create 2018/8/29
 **/
@Configuration
@EnableAutoConfiguration
public class AmqpApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmqpApplication.class, args);
    }
}
