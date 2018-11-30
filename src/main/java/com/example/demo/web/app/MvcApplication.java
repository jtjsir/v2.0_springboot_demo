package com.example.demo.web.app;

import com.example.demo.environment.SeveralEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author nanco
 * -------------
 * mvc config
 * -------------
 * @create 2018/9/4
 **/
@Configuration
public class MvcApplication {

    @Profile(SeveralEnvironment.SERVLET)
    @Configuration
    @ComponentScan("com.example.demo.web.profile")
    static class BeanDefinitionScanner {

    }
}
