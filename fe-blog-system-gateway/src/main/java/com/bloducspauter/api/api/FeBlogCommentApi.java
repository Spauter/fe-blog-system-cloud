package com.bloducspauter.api.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@FeignClient(name = "fe-ornament")
public interface FeBlogCommentApi {
    @RequestMapping("fe-ornament/AddCommentServlet")
    Map<String, Object> addComment(HttpServletRequest request, HttpSession session);

    @RequestMapping("fe-ornament/ModifyCommentServlet")
    Map<String, Object> modifyComment(HttpServletRequest request);


    @RequestMapping("fe-ornament/DeleteCommentServlet")
    Map<String, Object> delComment(HttpServletRequest request);

    @RequestMapping("fe-ornament/SelectAllCommentCountServlet")
    Map<String, Object> selectAllComment(HttpServletRequest request);

    @GetMapping("fe-ornament/SelectAllCommentServlet")
    Map<String, Object> findAllComment(HttpServletRequest request);
}
