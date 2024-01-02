package com.bloducspauter.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@FeignClient(name = "fe-ornament")
public interface FeReplyApi {
    @RequestMapping("/fe-ornament/findResponseByCommentId")
    Map<String,Object> findResponseByCommentId(HttpServletRequest request);

    @GetMapping("/fe-ornament/getCommentedUser")
    Map<String,Object>getCommentedUser(HttpServletRequest request);

    @PostMapping("addResponse")
    Map<String,Object>addResponce(HttpServletRequest request, HttpSession session);
}
