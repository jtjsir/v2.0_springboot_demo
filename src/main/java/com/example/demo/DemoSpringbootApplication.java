package com.example.demo;

import com.example.demo.amqp.app.AmqpApplication;
import com.example.demo.bootbase.UserProperty;
import com.example.demo.database.app.DatabaseApplication;
import com.example.demo.jmx.app.JmxApplication;
import com.example.demo.redis.operations.app.RedisApplication;
import com.example.demo.web.app.MvcApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {
        MvcApplication.class,
        AmqpApplication.class,
        JmxApplication.class,
        DatabaseApplication.class,
        RedisApplication.class
})
public class DemoSpringbootApplication {

    public static void main(String[] args) {
        ApplicationContext demoApplicationContext = SpringApplication.run(DemoSpringbootApplication.class, args);

        UserProperty userProperty = demoApplicationContext.getBean(UserProperty.class) ;

        System.out.println(userProperty);
    }
}
