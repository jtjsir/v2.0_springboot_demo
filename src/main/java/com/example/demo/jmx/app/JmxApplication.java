package com.example.demo.jmx.app;

import com.example.demo.environment.SeveralEnvironment;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author nanco
 * -------------
 * demo-springboot
 * -------------
 * @create 2018/11/30 9:12
 **/
@Configuration
public class JmxApplication {

    @Profile(SeveralEnvironment.JMX)
    @Configuration
    @ComponentScan(basePackages = {"com.example.demo.jmx.profile"})
    static class BeanDefinitionScanner implements InitializingBean {

        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("JMX auto package is already assigned.");
        }
    }
}
