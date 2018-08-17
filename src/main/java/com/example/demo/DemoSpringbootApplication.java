package com.example.demo;

import com.example.demo.bootbase.UserProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoSpringbootApplication {

    public static void main(String[] args) {
        ApplicationContext demoApplicationContext = SpringApplication.run(DemoSpringbootApplication.class, args);

        UserProperty userProperty = demoApplicationContext.getBean(UserProperty.class) ;

        System.out.println(userProperty);
    }
}
