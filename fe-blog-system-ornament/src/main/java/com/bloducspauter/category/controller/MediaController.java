package com.bloducspauter.category.controller;


import com.bloducspauter.bean.Media;
import com.bloducspauter.category.service.MediaService;
import com.bloducspauter.user.service.UploadService;
import com.bloducspauter.bean.utils.IsValidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bloducspauter.bean.utils.DefaultValue.*;

@RestController
@Slf4j
@RequestMapping("fe-ornament")
@MultipartConfig
public class MediaController extends HttpServlet {

    @Autowired
    private MediaService mediaService;

    @Autowired
    private UploadService uploadService;


    @RequestMapping("addMedia")
    public Map<String, Object> addMedia(@RequestParam("image") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        String osName = System.getProperty("os.name");
        String fileName = file.getOriginalFilename();
        try {
            if (osName.startsWith("Windows")) {
                uploadService.uploadToWindows(file, UPLOAD_MEDIA_PATH, false);
            } else {
                uploadService.uploadToNginx(file);
            }
            Media media = new Media();
            String type = new IsValidUtil().checkFileType(file.getOriginalFilename());
            if ("Image".equals(type)) {
                media.setImage(fileName);
            } else if ("Audio".equals(type)) {
                media.setMusic(fileName);
            } else {
                map.put("code", 404);
                map.put("msg", "上传了不支持的文件格式");
                return map;
            }
            if (mediaService.findMedia(media.getImage(), MEDIA_IMAGE_TYPE) != null) {
                map.put("code", 500);
                map.put("msg", "已经存在同名文件！");
                return map;
            }
            mediaService.add(media);
            map.put("code", 200);
            map.put("msg", "添加成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", e.getMessage());
            return map;
        }
    }


    @RequestMapping("deleteMedia")
    public Map<String, Object> delMedia(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> map = new HashMap<>();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        //获取前端传来媒体类型
        String type = req.getParameter("type");
        //获取前端传来需要删除的媒体文件名称集合
        try {
            String[] obj = req.getParameterValues("image");
            if (obj == null) {
                map.put("code", 404);
                map.put("msg", "未选择任何图片");
                return map;
            }
            //获取存储文件位置
            String filePath = null;
            filePath = "D:/upload/" + UPLOAD_MEDIA_PATH;
            List<String> deleteMedias = Arrays.asList(obj);
            boolean result = mediaService.delete(deleteMedias, type, filePath);
            if (result) {
                map.put("code", 200);
                map.put("msg", "删除成功");
            } else {
                map.put("code", 200);
                map.put("msg", "部分文件未删除");
            }
            //创建返回信息
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", e.getCause());
        }
        return map;
    }

    @RequestMapping("FindAllMedia")
    public Map<String, Object> findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        Map<String, Object> map = new HashMap<>();
        String type = req.getParameter("type");
        List<Media> mediaList = mediaService.selectALL(type);
        map.put("code", 200);
        map.put("data", mediaList);
        return map;
    }
}
