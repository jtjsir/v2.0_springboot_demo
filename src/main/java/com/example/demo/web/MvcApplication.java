package com.example.demo.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author nanco
 * -------------
 * mvc config
 * -------------
 * @create 2018/9/4
 **/
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class MvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcApplication.class, args);
    }
}
