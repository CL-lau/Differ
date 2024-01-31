package com.differ.controller;

import com.differ.dispatcher.GlobalDispatcherMap;
import com.differ.dispatcher.HttpDispatcher;
import com.differ.utils.ApiResponseUtil;
import com.differ.entity.enumer.StatusCode;
import com.differ.service.HttpService;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping(value = "/service")
public class HttpController {

    @Autowired
    private HttpDispatcher httpDispatcher;
    @Autowired
    private GlobalDispatcherMap dispatcherMap;
    @Autowired
    private HttpService httpService;

    @RequestMapping(value = "/setHttpService", method = RequestMethod.POST)
    public @ResponseBody String setHttpService(@Nullable @RequestBody(required = false) String requestBody, HttpServletRequest request){
        AtomicBoolean flag = new AtomicBoolean(false);
        Optional<String> requestValue = Optional.ofNullable(requestBody);
        requestValue.ifPresent(s -> {
            flag.set(httpService.setHttpService(requestBody));
        });
        if (flag.get()) return ApiResponseUtil.bulidResponse(StatusCode.SUCCESS.getCode(), "成功", "插入Dispatcher成功。").toString();
        return ApiResponseUtil.bulidResponse(StatusCode.FAILURE.getCode(), "失败", "请见检查请求的body，以及当前是否已经存在相同的dispatcher。").toString();
    }

    @RequestMapping(value = "/setService", method = RequestMethod.POST)
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

    @RequestMapping(value = "/getHttpService", method = RequestMethod.GET)
    public String getHttpService(@RequestParam Map<String, String> params, HttpServletRequest request) {
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

    @RequestMapping(value = "/addDispatcher", method = RequestMethod.POST)
    public String addDispatcher(@Nullable @RequestBody String requestBody, HttpServletRequest request){
        Boolean flag = dispatcherMap.addDispatcher(requestBody, new HttpDispatcher());
        return new ApiResponseUtil(StatusCode.SUCCESS, "成功", flag.toString()).toString();
    }

    @RequestMapping(value = "/containsDispatcher", method = RequestMethod.POST)
    public String containsDispatcher(@Nullable @RequestBody String requestBody, HttpServletRequest request){
        Boolean flag = dispatcherMap.containsDispatcher(requestBody);
        return new ApiResponseUtil(StatusCode.SUCCESS, "成功", flag.toString()).toString();
    }
}
