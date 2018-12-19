package com.example.demo.security.profile.config.authorize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * @author nanco
 * -------------
 * demo-springboot
 * <p>
 * 配置自定义的访问路径授权校验
 * -------------
 * @create 2018/12/18 13:29
 **/
@Configuration
public class CustomAuthorizeConfig {

    @Bean
    public FilterInvocationSecurityMetadataSource customSecurityMetadataSource() {
        return new CustomSecurityMetadataSource();
    }

    @Bean
    public AccessDecisionManager customAccessDecisionManager() {
        return new CustomAccessDecisionManager();
    }

    /**
     * 自定义权限校验拦截器
     *
     * @return
     * @see FilterSecurityInterceptor
     */
    @Bean("customFilterSecurityInterceptor")
    public FilterSecurityInterceptor customFilterSecurityInterceptor() {
        FilterSecurityInterceptor interceptor = new CustomFilterSecurityInterceptor();
        interceptor.setAccessDecisionManager(customAccessDecisionManager());
        interceptor.setSecurityMetadataSource(customSecurityMetadataSource());

        return interceptor;
    }


    /**
     * 角色校验拦截器
     */
    private class CustomFilterSecurityInterceptor extends FilterSecurityInterceptor {

        @Override
        public void invoke(FilterInvocation fi) throws IOException, ServletException {
            // 不采取原先的校验方式,以免该拦截器无法生效
            InterceptorStatusToken token = super.beforeInvocation(fi);
            try {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            } finally {
                super.finallyInvocation(token);
            }
        }
    }

    /**
     * 自定义角色获取
     */
    private class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

        private final Logger LOG = LoggerFactory.getLogger(CustomSecurityMetadataSource.class);


        @Override
        public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
            if (!FilterInvocation.class.isAssignableFrom(object.getClass())) {
                LOG.error(String.format("object's class is not %s", FilterInvocation.class.getName()));

                return null;
            }

            String certainPath = FilterInvocation.class.cast(object).getHttpRequest().getRequestURI();
            return loadByDao(certainPath);
        }

        @Override
        public Collection<ConfigAttribute> getAllConfigAttributes() {
            return null;
        }

        @Override
        public boolean supports(Class<?> clazz) {
            return FilterInvocation.class.isAssignableFrom(clazz);
        }

        private Collection<ConfigAttribute> loadByDao(String certainPath) {
            // 复写实现从数据库或者第三方获取角色信息
            if (!StringUtils.hasText(certainPath)) {
                return Collections.unmodifiableList(SecurityConfig.createList("ADMIN"));
            }

            return Collections.emptyList();
        }
    }

    /**
     * 角色校验
     */
    private class CustomAccessDecisionManager implements AccessDecisionManager {

        @Override
        public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
            // 空则放行
            if (configAttributes == null || configAttributes.size() == 0) {
                return;
            }

            // 获取当前检验通过的用户所拥有的权限
            Collection<? extends GrantedAuthority> userGrantedAuthorities = authentication.getAuthorities();
            // 与当前路径对应的可访问角色相比较
            for (GrantedAuthority authority : userGrantedAuthorities) {
                for (ConfigAttribute pathRole : configAttributes) {
                    if (authority.getAuthority().equals("ROLE_" + pathRole)) {
                        return;
                    }
                }
            }

            // 失败则报错
            throw new AccessDeniedException(String.format("%s has no right.", authentication.getName()));

        }

        @Override
        public boolean supports(ConfigAttribute attribute) {
            return true;
        }

        @Override
        public boolean supports(Class<?> clazz) {
            return true;
        }
    }
}
