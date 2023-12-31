package com.bloducspauter.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bloducspauter.bean.Tag;
import com.bloducspauter.mapper.TagMapper;
import com.bloducspauter.mapper.TagStatisticsMapper;
import com.bloducspauter.service.TagService;
import com.bloducspauter.statistics.TagStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TagServiceImpl implements TagService {


    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TagStatisticsMapper tagStatisticsMapper;

    /**
     * 查询所有标签
     *
     * @param
     * @return 查询的集合
     * @time 8-9
     * @version
     */
    @Override
    public List<Tag> selectALL() {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        return tagMapper.selectList(queryWrapper);
    }

    /**
     * 往库里增加一个标签
     *
     * @return 查询的集合
     * @time 8-9
     * @version
     */
    @Override
    public boolean add(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        int row = tagMapper.insert(tag);
        return row>0;
    }

    /**
     * 删除一个标签
     *
     * @param tag
     * @return 查询的集合
     * @time 8-9
     * @version
     */
    @Override
    public boolean delete(String tag) {
        QueryWrapper<Tag>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",tag);
        int row = tagMapper.delete(queryWrapper);
        return row>0;
    }

    @Override
    public List<TagStatistics> seeHotSubmittedTags() {
        return tagStatisticsMapper.getTagStatistics();
    }

}
