package com.bloducspauter.blog.controller;



import com.bloducspauter.bean.blog.Homepage;

import com.bloducspauter.blog.service.HomePageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("fe-blog")
public class HomePageController {
    @Autowired
    private HomePageService homePageService;

    @RequestMapping("FindHomePage")
    public Map<String, Object> findHomePage() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Homepage> homepages = homePageService.FindHomePage(1);
            map.put("code", 200);
            map.put("data", homepages);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", e.getCause());
        }
        return map;

    }

    @RequestMapping("HomePageManage")
    public Map<String, Object> modifyHomePage(HttpServletRequest req) {
        Map<String, Object> map = new HashMap<>();
        String title = req.getParameter("title");
        String welcome = req.getParameter("welcome");
        String description = req.getParameter("description");
        String banner = req.getParameter("banner");
        String announcement = req.getParameter("announcement");
        try {
            Homepage homepage = new Homepage(1, title, description, welcome, banner, announcement);
            homePageService.UpdateHomePage(homepage);
            map.put("code", 200);
            map.put("msg", "更新成功");
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            map.put("code", 500);
            map.put("msg", "更新失败");
            map.put("cause", e.getCause());
        }
        return map;
    }
}
