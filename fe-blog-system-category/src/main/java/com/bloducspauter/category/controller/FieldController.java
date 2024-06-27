package com.bloducspauter.category.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bloducspauter.bean.Field;
import com.bloducspauter.bean.statistics.FieldStatistics;
import com.bloducspauter.category.service.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("fe-category")
public class FieldController {

    @Autowired
    private FieldService fieldService;


    private static String getString(HttpServletRequest req) throws IOException {
        ServletInputStream inputStream = req.getInputStream();
        StringBuilder stringBuffer = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String s = "";
        while ((s = bufferedReader.readLine()) != null) {
            stringBuffer.append(s);
        }
        return stringBuffer.toString();
    }

    @RequestMapping("Addfield")
    public Map<String, Object> addField(HttpServletRequest req) {
        Map<String, Object> map = new HashMap<>();
        String parameter = req.getParameter("Field");
        boolean result = parameter != null && (fieldService.findField(parameter) == -1);
        if (result) {
            fieldService.add(parameter);
            map.put("code", 200);
            map.put("msg", "添加成功");
            map.put("data", parameter);
        } else {
            map.put("code", 500);
            map.put("msg", "已经有该种类");
        }
        return map;
    }

    @RequestMapping("DelField")
    public Map<String, Object> delFeild(HttpServletRequest req) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String json = getString(req);
        JSONArray jsonArray = JSONArray.parseArray(json);
        for (Object o : jsonArray) {
            JSONObject object = (JSONObject) o;
            System.out.println(object.get("value"));
            fieldService.delete(object.get("value").toString());
        }
        map.put("code", 200);
        map.put("msg", "操作完成");
        return map;
    }


    @RequestMapping("FindAllField")
    public Map<String, Object> findAllFeild() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Field> fields = fieldService.selectALL();
            if (fields != null) {
                map.put("code", 200);
                map.put("msg", "获取成功");
            } else {
                map.put("code", 404);
                map.put("msg", "暂时没有这样的分类");
            }
            map.put("data", fields);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            map.put("code", 500);
            map.put("msg", e.getCause());
        }
        return map;
    }

    @RequestMapping("hotFields")
    public Map<String, Object> seeDistributionOfBlogsByField() {
        Map<String, Object> map = new HashMap<>();
        List<FieldStatistics> fieldStatistics;
        try {
            fieldStatistics = fieldService.seeHotSubmittedFields();
            map.put("code",200);
            map.put("msg","查询成功");
            map.put("data",fieldStatistics);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", e.getMessage());
        }
        return map;
    }
}
