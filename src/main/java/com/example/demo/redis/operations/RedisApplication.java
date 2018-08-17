package com.example.demo.redis.operations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @author nanco
 * -------------
 * redis main starter
 * -------------
 * @create 2018/8/17
 **/
@EnableAutoConfiguration
@Configuration
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class);
    }
}
