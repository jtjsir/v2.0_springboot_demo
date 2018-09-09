package com.example.demo.web.validation;

import com.example.demo.web.model.ResEntity;
import com.google.gson.Gson;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author nanco
 * -------------
 * resolve bindexception
 * -------------
 * @create 18/9/9
 */
@Configuration
public class SimpleExceptionResolver extends AbstractHandlerExceptionResolver {

    private final Map<String, List<String>> errorResultMap = new HashMap<>(2);

    private final String ERROR_KEY = "error_result";

    private Gson gson = new Gson();

    @Override
    public int getOrder() {
        return super.getOrder() + 20;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

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
                e.printStackTrace();
            }
        }
        return new ModelAndView();
    }
}
