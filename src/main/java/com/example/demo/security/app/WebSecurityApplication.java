package com.example.demo.security.app;

import com.example.demo.environment.SeveralEnvironment;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author nanco
 * -------------
 * demo-springboot
 * -------------
 * @create 2018/11/30 16:05
 **/
@Configuration
public class WebSecurityApplication {

    /**
     * notice this,the static nested class is equal to be scanned with the outer class
     */
    @Configuration
    @Profile(SeveralEnvironment.SECURITY)
    @ConditionalOnClass(MybatisAutoConfiguration.class)
    @ComponentScan(basePackages = {"com.example.demo.security.profile"})
    static class BeanDefinitionScanner {

    }
}
