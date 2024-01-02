package com.bloducspauter.mapper;

import com.bloducspauter.statistics.FieldStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FieldStatisticsMapper {
    List<FieldStatistics> getFieldStatistics();
}
