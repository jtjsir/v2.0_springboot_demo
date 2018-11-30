package com.example.demo.database.app;

import com.example.demo.environment.SeveralEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author nanco
 * -------------
 * demo-springboot
 * -------------
 * @create 2018/10/17 17:14
 **/
@Configuration
public class DatabaseApplication {


    @Configuration
    @Profile(SeveralEnvironment.DATASOURCE)
    @ComponentScan(basePackages = {"com.example.demo.database.profile"})
    static class BeanDefinitionScanner {

    }
}
