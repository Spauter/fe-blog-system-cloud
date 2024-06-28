package com.bloducspauter.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
/**
 * @author Bloduc Spauter
 *
 */
@FeignClient(name = "fe-ornament")
@Deprecated
public interface FeBlogMediaApi {
    @RequestMapping("/fe-ornament/addMedia")
    Map<String, Object> addMedia(@RequestParam("image") MultipartFile file);

    @RequestMapping("/fe-ornament/deleteMedia")
    Map<String,Object> delMedia(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    @RequestMapping("/fe-ornament/FindAllMedia")
    Map<String,Object> findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
