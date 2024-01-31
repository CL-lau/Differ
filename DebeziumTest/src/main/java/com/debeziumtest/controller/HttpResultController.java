package com.debeziumtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/2 0:11
 */
@Controller
@RequestMapping(value = "/test")
public class HttpResultController {
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public @ResponseBody String setHttpService(){
        System.out.println("get http");
        return "get http";
    }

}
