package com.bloducspauter.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@FeignClient(name = "fe-category")
public interface FeBlogTagApi {
    @RequestMapping("fe-category/AddTagController")
    Map<String, Object> addTag(HttpServletRequest request) throws IOException;

    @RequestMapping("/fe-category/DeleteTagController")
    Map<String, Object> delTag(HttpServletRequest request);

    @RequestMapping("/fe-category/SelectAllTagController")
    Map<String, Object> findAllTag();

    @RequestMapping("/fe-category/hotTags")
    Map<String, Object> seeDistributionOfBlogsByTag();

}