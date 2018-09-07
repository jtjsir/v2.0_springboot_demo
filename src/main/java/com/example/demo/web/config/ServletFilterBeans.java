package com.example.demo.web.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author nanco
 * -------------
 * -------------
 * @create 2018/9/5
 **/
@Configuration
public class ServletFilterBeans {

    @Bean("simpleServlet")
    public ServletRegistrationBean<Servlet> simpleServlet() {
        return new ServletRegistrationBean<>(new SimpleServlet(), "/simple/");
    }

    @Bean("simpleFilter")
    public FilterRegistrationBean<Filter> simpleFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean<>();
        bean.setFilter(new SimpleFilter());
        bean.addUrlPatterns("/simple/*");
        return bean;
    }

    private static class SimpleServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            System.out.println("doService path: " + req.getRequestURI());
            super.doGet(req, resp);
        }
    }

    private static class SimpleFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            System.out.println("filter path: " + request.getRequestURI());
            filterChain.doFilter(request, response);
        }
    }
}
