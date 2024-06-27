package com.bloducspauter.blog.config;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
//只要有异常直接在这里抛出
@ControllerAdvice
@Order(-900)
public class CustomerExceptionHandelAdvice {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Map<String,Object>handelRuntimeException(RuntimeException e){
        Map<String,Object>map=new HashMap<>();
        map.put("code",-1);
        map.put("msg","Who put a TNT here?");
        map.put("cause","意料之外的错误:"+e.getCause());
        return map;
    }
}
