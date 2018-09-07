package com.example.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nanco
 * -------------
 * -------------
 * @create 2018/9/4
 **/
@Controller
@RequestMapping("/boot")
@ResponseBody
public class DemoController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Map<String, String> helloWorld() {
        Map<String, String> result = new HashMap<>();
        result.put("springboot", "hello world");
        return result;
    }
}
