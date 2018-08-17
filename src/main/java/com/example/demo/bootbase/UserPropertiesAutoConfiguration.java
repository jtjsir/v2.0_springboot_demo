package com.example.demo.bootbase;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author nanco
 * @create 2018/8/13
 **/
@Configuration
@EnableConfigurationProperties(value = UserProperty.class)
public class UserPropertiesAutoConfiguration {
}
