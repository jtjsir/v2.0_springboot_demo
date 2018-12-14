package com.example.demo.security.profile.test;

import com.example.demo.security.profile.config.DaoUserDetailsServiceConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Resource;

/**
 * @author nanco
 * -------------
 * demo-springboot
 * -------------
 * @create 2018/12/7 16:12
 **/
@Configuration
public class DefaultUserDetailServiceTest implements InitializingBean {

    @Resource
    private DaoUserDetailsServiceConfig.DefaultUserDetailsService defaultUserDetailsService;

    @Override
    public void afterPropertiesSet() throws Exception {
        UserDetails userDetails = defaultUserDetailsService.loadUserByUsername("admin");

        System.out.println(String.format("%s's password is [%s]", userDetails.getUsername(), userDetails.getPassword()));
    }
}
