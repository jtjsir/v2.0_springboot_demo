package com.example.demo.redis.operations;

import com.example.demo.DemoSpringbootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author nanco
 * -------------
 * -------------
 * @create 2018/8/17
 **/
public class OperationMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(DemoSpringbootApplication.class);

        // get string redis template
        StringRedisTemplate stringRedisTemplate = applicationContext.getBean(StringRedisTemplate.class);

        // set key-value
        stringRedisTemplate.opsForValue().set("name", "jingtj");

        System.out.println(stringRedisTemplate.opsForValue().get("name"));
    }
}
