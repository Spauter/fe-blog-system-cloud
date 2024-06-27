package com.bloducspauter.controller;

import com.alibaba.fastjson.JSONObject;
import com.bloducspauter.service.MediaService;
import com.bloducspauter.service.TagService;
import com.bloducspauter.bean.statistics.TagStatistics;
import com.bloducspauter.bean.utils.GetRequestJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bloducspauter.bean.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("fe-category")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private MediaService mediaService;

    @RequestMapping("AddTagController")
    public Map<String, Object> addTag(HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap<>();
        JSONObject json = GetRequestJson.getRequestJsonObject(request);
        String strTag = json.getString("tag");
        try {
            boolean flag = tagService.add(strTag);
            if (flag) {
                map.put("code", 200);
                map.put("msg", "增加标签成功");
                map.put("data", strTag);
            } else {
                map.put("code", 500);
                map.put("msg", "添加标签失败");
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            map.put("code", 500);
            map.put("msg", e.getCause());
        }
        return map;
    }

    @RequestMapping("DeleteTagController")
    public Map<String, Object> delTag(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
       try {
            ArrayList<String> parameterArrays = mediaService.getParameterArrays(request.getInputStream());
            for (String tag : parameterArrays) {
                tagService.delete(tag);
            }
            map.put("code", 200);
            map.put("msg", "操作成功");
            map.put("data", parameterArrays);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            map.put("code", 500);
            map.put("msg", "操作失败");
            map.put("cause", e.getCause());
        }
        return map;
    }

    @RequestMapping("SelectAllTagController")
    public Map<String, Object> findAllTag() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Tag> tagList = tagService.selectALL();
            if (tagList != null) {
                map.put("code", 200);
                map.put("msg", "获取成功");

            } else {
                map.put("code", 404);
                map.put("msg", "暂时没有标签");
            }
            map.put("data", tagList);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            map.put("code", 500);
            map.put("msg", e.getCause());
        }
        return map;
    }
    @RequestMapping("hotTags")
    public Map<String, Object> seeDistributionOfBlogsByTag() {
        Map<String, Object> map = new HashMap<>();
        List<TagStatistics> tagStatistics;
        try {
            tagStatistics = tagService.seeHotSubmittedTags();
            map.put("code",200);
            map.put("msg","查询成功");
            map.put("data",tagStatistics);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", e.getMessage());
        }
        return map;
    }

}
