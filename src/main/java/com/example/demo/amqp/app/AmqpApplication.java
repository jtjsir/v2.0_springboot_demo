package com.example.demo.amqp.app;

import com.example.demo.environment.SeveralEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author nanco
 * -------------
 * -------------
 * @create 2018/8/29
 **/
@Configuration
public class AmqpApplication {


    @Profile(SeveralEnvironment.AMQP)
    @Configuration
    @ComponentScan(basePackages = {"com.example.demo.amqp.profile"})
    static class BeanDefinitionScanner {

    }
}
