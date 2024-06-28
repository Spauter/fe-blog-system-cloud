package com.bloducspauter.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * fe-media
 *
 * @author Bloduc Spauter
 */
@FeignClient(name = "fe-media")
public interface NFeBlogMediaApi {

    @PostMapping("checkFile")
    Map<String, Object> checkFile( @RequestParam("fileMd5") String fileMd5);

    @PostMapping(value = "/upload/commonFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Map<String, Object> upload(@RequestParam("image") MultipartFile file, HttpServletRequest request);
}
