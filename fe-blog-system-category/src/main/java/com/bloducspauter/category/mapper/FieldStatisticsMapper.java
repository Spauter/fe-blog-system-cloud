package com.bloducspauter.category.mapper;

import com.bloducspauter.bean.statistics.FieldStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FieldStatisticsMapper {
    List<FieldStatistics> getFieldStatistics();
}
