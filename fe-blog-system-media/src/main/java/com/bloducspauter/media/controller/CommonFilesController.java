package com.bloducspauter.media.controller;

import com.bloducspauter.bean.MediaFiles;
import com.bloducspauter.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Objects;

/**
 * 小文件上传控制台
 *
 * @author Bloduc Spauter
 */
@Slf4j
@RestController
@RequestMapping("fe-media")
public class CommonFilesController {

    @Resource
    private com.bloducspauter.media.service.MediaService mediaService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    //存储普通文件
    @Value("${minio.bucket.files}")
    private String bucketFiles;
    //存储视频
    @Value("${minio.bucket.video-files}")
    private String bucketVideo;
    //存储聊天图片
    @Value("${minio.bucket.emojis}")
    private String bucketEmojis;
    @Value("${minio.bucket.titles}")
    private String bucketTitles;

    private User getUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token == null) {
            return null;
        }
        return (User) redisTemplate.opsForValue().get(token);
    }

    @PostMapping("checkFile")
    public Map<String, Object> checkFile(@RequestParam("fileMd5") String fileMd5) {
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
            MediaFiles mediaFiles = mediaService.uploadFile(templateFile, user.getUserId(), file.getOriginalFilename(), bucketFiles);
            if (mediaFiles != null) {
                map.put("code", 200);
                map.put("msg", "上传成功");
                map.put("data", mediaFiles);
            } else {
                map.put("code", 500);
                map.put("msg", "上传失败");
            }
        } catch (IOException e) {
            map.put("code", 500);
            map.put("msg", "上传失败");
            map.put("cause", e.getMessage());
        }
        return map;
    }

    public Map<String, Object> delMedia(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        //获取前端传来媒体类型
        String type = req.getParameter("type");
        //获取前端传来需要删除的媒体文件名称集合
        String[] obj = req.getParameterValues("image");
        if (obj == null) {
            map.put("code", 404);
            map.put("msg", "未选择任何图片");
            return map;
        }
        try {
            mediaService.delete(List.of(obj), bucketFiles);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return map;
    }

    @RequestMapping("findAllMedia")
    public Map<String, Object> findAllMedia(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        List<MediaFiles> mediaFiles = mediaService.selectALL(bucketTitles);
        map.put("code", 200);
        map.put("data", mediaFiles);
        return map;
    }

    @RequestMapping("addTitleImage")
    public Map<String, Object> addTitleImage(HttpServletRequest req, @RequestParam("image") MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String userId = Objects.requireNonNull(getUser(req)).getUserId();
        String osName = System.getProperty("os.name");
        String fileName = file.getOriginalFilename();
        File templateFile = File.createTempFile("minio", ".temp");
        file.transferTo(templateFile);
        try {
            mediaService.uploadFile(templateFile, userId, fileName, bucketTitles);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            map.put("code", 500);
            map.put("msg","上传失败:"+ e.getMessage());
        }finally {
            templateFile.delete();
        }
        map.put("code", 200);
        map.put("msg", "上传成功");
        return map;
    }
}
