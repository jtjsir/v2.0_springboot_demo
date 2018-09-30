package com.example.demo.web.controller;

import com.example.demo.web.config.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author nanco
 * -------------
 * demo-springboot ExceptionHandlerExceptionResolver will process this advice
 * -------------
 * @create 2018/9/30 16:05
 **/
@ControllerAdvice(basePackages = "com.example.demo.web.controller")
public class BizControllerAdvice {

    private static final Logger ADVICE_LOGGER = LoggerFactory.getLogger(BizControllerAdvice.class);

    /**
     * aim to resolve the biz exception
     *
     * @param response
     * @param exception
     */
    @ExceptionHandler(value = BizException.class)
    public void resolveBizException(HttpServletResponse response, BizException exception) {
        try {
            response.sendError(500, exception.getMessage());
        } catch (IOException e) {
            ADVICE_LOGGER.error("response fail.", e);
        }
    }
}
