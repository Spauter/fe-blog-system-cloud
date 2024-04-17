package com.bloducspauter.api.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@FeignClient(name = "fe-blog")
public interface FeBlogHomapageApi {
    @RequestMapping("fe-blog/FindHomePage")
    Map<String, Object> findHomePage();
    @RequestMapping("fe-blog/HomePageManage")
    Map<String, Object> modifyHomePage(HttpServletRequest req);
}
