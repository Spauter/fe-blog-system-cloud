package com.bloducspauter.mapper;

import com.bloducspauter.statistics.TagStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface TagStatisticsMapper {
    List<TagStatistics>getTagStatistics();
}
