package com.bloducspauter.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@FeignClient(name = "fe-category")
public interface FeBlogFieldApi {
    @RequestMapping("/fe-category/Addfield")
    Map<String, Object> addField(HttpServletRequest req);


    @RequestMapping("/fe-category/DelField")
    public Map<String, Object> delFeild(HttpServletRequest req) throws IOException;

    @RequestMapping("/fe-category/FindAllField")
    Map<String, Object> findAllFeild();

    @RequestMapping("/fe-category/hotFields")
    Map<String, Object> seeDistributionOfBlogsByField();
}
