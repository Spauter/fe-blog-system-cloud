package com.bloducspauter.media.controller;

import com.bloducspauter.bean.MediaFiles;
import com.bloducspauter.bean.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 小文件上传控制台
 *
 * @author Bloduc Spauter
 */
@RestController
@RequestMapping("fe-media")
public class CommonFilesController {

    @Resource
    private com.bloducspauter.media.service.MediaService mediaService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private User getUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token == null) {
            return null;
        }
        return (User) redisTemplate.opsForValue().get(token);
    }

    @PostMapping("checkFile")
    public Map<String, Object> checkFile(
            @RequestParam("fileMd5") String fileMd5
    ) throws Exception {
        Map<String, Object> map = new HashMap<>();
        boolean result = mediaService.checkFileExists(fileMd5);
        if (result) {
            map.put("code", 200);
        } else {
            map.put("code", 404);
        }
        map.put("result", result);
        return map;
    }


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        User user = getUser(request);
        if (user == null) {
            map.put("code", 401);
            map.put("msg", "请先登录");
            return map;
        }
        try {
            File templateFile = File.createTempFile("minio", ".temp");
            file.transferTo(templateFile);
            MediaFiles mediaFiles = mediaService.uploadFile(templateFile,user.getUserId(),file.getOriginalFilename());
            if (mediaFiles != null) {
                map.put("code", 200);
                map.put("msg", "上传成功");
                map.put("data", mediaFiles);
            } else {
                map.put("code", 500);
                map.put("msg", "上传失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", "上传失败");
            map.put("cause", e.getMessage());
        }
        return map;
    }
}
