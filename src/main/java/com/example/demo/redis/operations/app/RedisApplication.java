package com.example.demo.redis.operations.app;

import com.example.demo.environment.SeveralEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author nanco
 * -------------
 * redis main starter
 * -------------
 * @create 2018/8/17
 **/
@Configuration
public class RedisApplication {

    @Profile(SeveralEnvironment.REDIS)
    @Configuration
    @ComponentScan(basePackages = {"com.example.demo.redis.operations.profile"})
    static class BeanDefinitionScanner {

    }
}
