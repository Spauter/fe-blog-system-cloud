package com.bloducspauter.category.mapper;

import com.bloducspauter.bean.statistics.TagStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface TagStatisticsMapper {
    List<TagStatistics>getTagStatistics();
}
