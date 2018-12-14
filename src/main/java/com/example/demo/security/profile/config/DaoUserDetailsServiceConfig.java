package com.example.demo.security.profile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author nanco
 * -------------
 * demo-springboot
 * <p>
 * 基于DAO加载用户信息
 * -------------
 * @create 2018/12/7 10:18
 **/
@Configuration
public class DaoUserDetailsServiceConfig {

    /**
     * load user info by dao
     *
     * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider
     */
    @Configuration(value = "defaultDemoUserDetailsService")
    public static class DefaultUserDetailsService implements UserDetailsService {

        private static final String DEFAULT_PASS = "defaultPass";

        // admin authority
        private Collection<? extends GrantedAuthority> adminAuthority;

        @Resource
        private PasswordEncoder defaultPasswordEncoder;

        public DefaultUserDetailsService() {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(authority);

            adminAuthority = Collections.unmodifiableList(authorities);
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User userdetails = new User(username, defaultPasswordEncoder.encode(DEFAULT_PASS), adminAuthority);

            return userdetails;
        }

        @Bean(name = "defaultDemoPasswordEncoder")
        public PasswordEncoder daoPasswordEncoder() {
            PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();

            return passwordEncoder;
        }
    }
}
