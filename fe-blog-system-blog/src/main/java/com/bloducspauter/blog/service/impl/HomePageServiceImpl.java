package com.bloducspauter.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bloducspauter.bean.blog.Homepage;
import com.bloducspauter.blog.mapper.HomePageMapper;
import com.bloducspauter.blog.service.HomePageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private HomePageMapper homePageMapper;

    @Override
    public Integer UpdateHomePage(Homepage homepage) {
        try {
            homePageMapper.updateById(homepage);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            log.error("更换失败");
        }
        return 0;
    }

    @Override
    public List<Homepage> FindHomePage(int Homepageid) {
        QueryWrapper<Homepage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homepageid", Homepageid);
        return homePageMapper.selectList(queryWrapper);
    }
}
