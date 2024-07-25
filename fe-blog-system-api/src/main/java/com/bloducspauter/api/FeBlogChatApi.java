package com.bloducspauter.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Bloduc Spauter
 *
 */
@FeignClient(name = "fe-chat")
public interface FeBlogChatApi<T> {
    @GetMapping("findAll")
    List<T> findAll();

    @RequestMapping("getByPage")
    List<T>findByPage(String location, int pageNo, int pageSize);
}
