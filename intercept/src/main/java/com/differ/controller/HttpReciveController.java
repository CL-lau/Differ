package com.differ.controller;

import com.differ.dispatcher.GlobalDispatcherMap;
import com.differ.dispatcher.HttpDispatcher;
import com.differ.utils.ApiResponseUtil;
import com.differ.entity.enumer.StatusCode;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/")
public class HttpReciveController {

    @Autowired
    private HttpDispatcher httpDispatcher;
    @Autowired
    private GlobalDispatcherMap dispatcherMap;

    @RequestMapping(value = "/postRequest", method = RequestMethod.POST)
    public @ResponseBody String setService(@RequestBody(required = false) String requestBody, HttpServletRequest request){
        System.out.println(request.getContentType());
        System.out.println(request);
        System.out.println(requestBody);
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
            System.out.println(headerName + "  " + headerValue);
        }

        // 处理JSON或表单参数
        if (requestBody != null) {
            System.out.println(requestBody);
        } else {
            Map<String, String[]> parameterMap = request.getParameterMap();
            for(Map.Entry<String, String[]> entity: parameterMap.entrySet()){
                System.out.println(entity.getKey() + "  " + Arrays.toString(entity.getValue()));
            }
        }
        return ApiResponseUtil.bulidResponse(StatusCode.SUCCESS.getCode(), "成功", "content").toString();
    }

    @RequestMapping(value = "/getRequest", method = RequestMethod.GET)
    public String handleGetRequest(@RequestParam Map<String, String> params, HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        for(Map.Entry<String, String[]> entity: parameterMap.entrySet()){
            System.out.println(entity.getKey() + "  " + Arrays.toString(entity.getValue()));
        }
        return ApiResponseUtil.bulidResponse(StatusCode.SUCCESS.getCode(), "成功", "content").toString();
    }

    @GetMapping("/{path}")
    public String handleGetRequest(@PathVariable String path, HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();

        List<String> headerList = Collections.list(request.getHeaderNames());
        Map<String, String> headers = headerList.stream()
                .collect(Collectors.toMap(headerName -> headerName, request::getHeader));

        Map<String, String[]> params = request.getParameterMap();

        // 处理请求逻辑
        // ...

        return "GET request handled " + path;
    }

    @PostMapping("/{path}")
    public String handlePostRequest(@PathVariable String path, @Nullable @RequestBody String body, HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();

        List<String> headerList = Collections.list(request.getHeaderNames());
        Map<String, String> headers = headerList.stream()
                .collect(Collectors.toMap(headerName -> headerName, request::getHeader));

        // 解析请求Body
        // 根据实际需要解析JSON或表单数据等

        // 解析请求参数
        Map<String, String[]> params = request.getParameterMap();

        // 处理请求逻辑
        // ...

        return "POST request handled" + path;
    }

    @GetMapping("/{*path}")
    public String handleGetRequestMutliPath(@PathVariable Map<String, String> pathVars, HttpServletRequest request) {
        // 解析请求地址
        String requestUrl = request.getRequestURL().toString();

        List<String> headerList = Collections.list(request.getHeaderNames());
        Map<String, String> headers = headerList.stream()
                .collect(Collectors.toMap(headerName -> headerName, request::getHeader));

        // 解析请求参数
        Map<String, String[]> params = request.getParameterMap();
        String res = "";
        for (Map.Entry<String, String> pathEntry : pathVars.entrySet()){
            res += (pathEntry.getKey() + " " + pathEntry.getValue());
        }

        // 处理请求逻辑

        return res;
    }

    @PostMapping("/{*path}")
    public String handlePostRequest(@PathVariable Map<String, String> pathVars, @Nullable @RequestBody String body, HttpServletRequest request) {
        // 解析请求地址
        String requestUrl = request.getRequestURL().toString();

        List<String> headerList = Collections.list(request.getHeaderNames());
        Map<String, String> headers = headerList.stream()
                .collect(Collectors.toMap(headerName -> headerName, request::getHeader));

        // 解析请求Body
        // 根据实际需要解析JSON或表单数据等

        // 解析请求参数
        Map<String, String[]> params = request.getParameterMap();

        // 处理请求逻辑
        String res = "";
        for (Map.Entry<String, String> pathEntry : pathVars.entrySet()){
            res += (pathEntry.getKey() + " " + pathEntry.getValue());
        }

        return res;
    }

    @RequestMapping(value = "/addDispatcher", method = RequestMethod.POST)
    public String addDispatcher(@Nullable @RequestBody String requestBody, HttpServletRequest request){
        Boolean flag = dispatcherMap.addDispatcher(requestBody,  new HttpDispatcher());
        return new ApiResponseUtil(StatusCode.SUCCESS, "成功", flag.toString()).toString();
    }

    @RequestMapping(value = "/containsDispatcher", method = RequestMethod.POST)
    public String containsDispatcher(@Nullable @RequestBody String requestBody, HttpServletRequest request){
        Boolean flag = dispatcherMap.containsDispatcher(requestBody);
        return new ApiResponseUtil(StatusCode.SUCCESS, "成功", flag.toString()).toString();
    }
}
