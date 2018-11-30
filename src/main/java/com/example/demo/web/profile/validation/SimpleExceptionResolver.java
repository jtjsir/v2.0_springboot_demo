package com.example.demo.web.profile.validation;

import com.example.demo.web.profile.model.ResEntity;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nanco
 * -------------
 * resolve bindexception
 * -------------
 * @create 18/9/9
 */
public class SimpleExceptionResolver extends AbstractHandlerExceptionResolver {

    private static final Logger EXCEPTION_LOG = LoggerFactory.getLogger(SimpleExceptionResolver.class);

    private final Map<String, List<String>> errorResultMap = new HashMap<>(2);

    private final String ERROR_KEY = "error_result";

    private Gson gson = new Gson();

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // only process BindException,unless return null to allow the next handler understanding the exception
        if (BindException.class.isInstance(ex)) {
            ResEntity resEntity = new ResEntity();
            try {
                BindException bindException = BindException.class.cast(ex);
                List<ObjectError> allErrors = bindException.getAllErrors();

                List<String> resMessages = new ArrayList<>(allErrors.size());
                allErrors.stream().forEach(error -> {
                    resMessages.add(error.getDefaultMessage());
                });

                errorResultMap.put(ERROR_KEY, resMessages);

                resEntity.setData(errorResultMap);

                response.getOutputStream().write(gson.toJson(resEntity).getBytes());
            } catch (IOException e) {
                EXCEPTION_LOG.error("process BindException fail.", e);
            }

            return new ModelAndView();
        }
        return null;
    }
}
