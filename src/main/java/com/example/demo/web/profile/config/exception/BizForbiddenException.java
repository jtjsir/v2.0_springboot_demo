package com.example.demo.web.profile.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author nanco
 * -------------
 * demo-springboot directly reponse the ForbiddenException
 * -------------
 * @create 2018/9/30 15:58
 **/
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "maybe you have no right!")
public class BizForbiddenException extends BizException {
}
