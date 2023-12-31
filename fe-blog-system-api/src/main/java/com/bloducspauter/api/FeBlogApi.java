package com.bloducspauter.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@FeignClient("fe-blog")
public interface FeBlogApi {

    @RequestMapping("fe-blog/FieldfindAllblog")
    Map<String, Object> findAllBlogsField(HttpServletRequest req, HttpSession session);

    @RequestMapping("fe-blog/FieldFindBlog")
    Map<String, Object> findFieldByBlog(HttpServletRequest req, HttpSession session);

    @RequestMapping("fe-blog/TagFindblog")
    Map<String, Object> findByBlog(HttpServletRequest req);

    @RequestMapping("/fe-blog/SelectDeletedBlog")
    Map<String, Object> selectDelBlog(HttpServletRequest request, HttpSession session);

    @RequestMapping("/fe-blog/SelectLimitBlogController")
    Map<String, Object> selectLimitedBlog(HttpServletRequest request, HttpSession session);

    @RequestMapping("/fe-blog/AddBlogController")
    Map<String, Object> addBlog(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException;

    @RequestMapping("/fe-blog/AllBlogCountServlet")
    Map<String, Object> allBlogs();

    @RequestMapping("/fe-blog/DeleteBlogController")
    Map<String, Object> delBlog(HttpServletRequest request) throws IOException;

    @RequestMapping("fe-blog/DetailBlogServlet")
    Map<String, Object> detailBlog(HttpServletRequest request);

    @RequestMapping("/fe-blog/FuzzyQueryBlog")
    Map<String, Object> fuzzyQueryBlog(HttpServletRequest request);

    @RequestMapping("/fe-blog/ModifyBlogController")
    Map<String, Object> modifyBlog(HttpServletRequest request, HttpServletResponse response, HttpSession session);

    @RequestMapping("/fe-blog/hotBlogs")
    Map<String,Object>hotBlogs();
}
