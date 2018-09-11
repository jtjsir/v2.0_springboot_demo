package com.example.demo.web.config;

import com.example.demo.web.validation.SimpleExceptionResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author nanco
 * -------------
 * color the mvc config
 * -------------
 * @create 2018/9/5
 **/
@Configuration
public class BootWebMvcConfigurer implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        // response first
        resolvers.add(0, new SimpleExceptionResolver());
    }
}
