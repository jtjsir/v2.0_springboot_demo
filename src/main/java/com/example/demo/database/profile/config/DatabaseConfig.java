package com.example.demo.database.profile.config;

import com.mysql.jdbc.Driver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * @author nanco
 * -------------
 * demo-springboot
 * -------------
 * @create 2018/10/17 17:09
 **/
@Configuration
public class DatabaseConfig {

    @Configuration
    @ConditionalOnClass(Driver.class)
    @MapperScan("com.example.demo.database.profile.mysql.dao")
    static class MysqlInterfaceScanner {

    }
}
