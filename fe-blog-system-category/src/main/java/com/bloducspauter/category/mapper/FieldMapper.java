package com.bloducspauter.category.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.bloducspauter.bean.Blog;
import com.bloducspauter.bean.Field;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface FieldMapper extends BaseMapper<Field> {


}
