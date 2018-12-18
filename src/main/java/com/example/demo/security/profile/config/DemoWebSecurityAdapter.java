package com.example.demo.security.profile.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;

/**
 * @author nanco
 * -------------
 * demo-springboot
 * <p>
 * replace the springboot's default web seurity
 * -------------
 * @create 2018/12/14 14:16
 **/
@Order(Ordered.LOWEST_PRECEDENCE - 200)
@EnableWebSecurity
public class DemoWebSecurityAdapter extends WebSecurityConfigurerAdapter {

    @Resource(name = "defaultDemoUserDetailsService")
    private UserDetailsService userDetailsService;

    @Resource(name = "defaultDemoPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    /**
     * 手动配置认证校验器
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 1.支持多方式的校验方式,被以ArrayList存储

        // 1.1 配置基于DAO的校验方式
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

        // 1.2 配置基于内存的校验方式
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser("nanco")
                .password("123456")
                .roles("ADMIN");

        // 1.3 基于JDBC的校验方式
//        boolean hasDatasource = getApplicationContext().getBeansOfType(DataSource.class).size() == 1;
//        if (hasDatasource) {
//            auth.jdbcAuthentication()
//                    .passwordEncoder(passwordEncoder)
//                    .dataSource(getApplicationContext().getBean(DataSource.class))
//                    .rolePrefix("ROLE_");
//        }

        // 1.4 外接自定义的校验方式
        auth.authenticationProvider(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return false;
            }
        });

        // 2. 认证完后清理保存的密码
        auth.eraseCredentials(true);

        // 3. 事件传播器配置
        boolean hasEventPublisher = getApplicationContext().getBeansOfType(AuthenticationEventPublisher.class).size() == 1;
        if (hasEventPublisher) {
            auth.authenticationEventPublisher(getApplicationContext().getBean(AuthenticationEventPublisher.class));
        }
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // also use this to ignore the mvc path to validate
        super.configure(web);
    }

    /**
     * 手动配置HTTP
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // cors access,it will auto detect the mvc cors config
        http.cors();

        // csrf protection
        http
                .csrf()
                // access /boot/** must has role admin
                .and().authorizeRequests().antMatchers("/boot/**").hasRole("ADMIN")
                // access /error/**,/vendor/** for anyone
                .and().authorizeRequests().antMatchers("/error/**", "/vendor/**").permitAll()
                // access /index.html for anyone
                .and().authorizeRequests().antMatchers("/index.html").permitAll()
                // support http basic way to validate
                .and().httpBasic()
        ;

        // Authorize interceptor config
        configureAuthorizeInterceptor(http);

    }

    private void configureAuthorizeInterceptor(HttpSecurity http) {
        boolean hasCustomInterceptor = getApplicationContext().containsBean("customFilterSecurityInterceptor");
        if (hasCustomInterceptor) {
            FilterSecurityInterceptor customInterceptor = (FilterSecurityInterceptor) getApplicationContext().getBean("customFilterSecurityInterceptor");
            http.addFilterAfter(customInterceptor, FilterSecurityInterceptor.class);
        }
    }
}
