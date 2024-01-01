package com.bloducspauter.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@FeignClient(name = "fe-ornament")
public interface FeReplyApi {
    @RequestMapping("/fe-ornament/findResponseByCommentId")
    Map<String,Object> findResponseByCommentId(HttpServletRequest request);
}
