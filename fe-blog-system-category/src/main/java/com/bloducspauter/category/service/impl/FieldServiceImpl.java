package com.bloducspauter.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.bloducspauter.bean.Field;

import com.bloducspauter.category.mapper.FieldMapper;
import com.bloducspauter.category.mapper.FieldStatisticsMapper;
import com.bloducspauter.category.service.FieldService;
import com.bloducspauter.bean.statistics.FieldStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FieldServiceImpl implements FieldService {

    @Autowired
    private FieldMapper fieldMapper;

    @Autowired
    private FieldStatisticsMapper fieldStatisticsMapper;

    @Override
    public List<Field> selectALL() {
        QueryWrapper<Field> queryWrapper = new QueryWrapper<>();
        return fieldMapper.selectList(queryWrapper);
    }

    @Override
    public boolean add(String field) {
        Field insert = new Field();
        insert.setName(field);
        return fieldMapper.insert(insert) == 1;
    }

    @Override
    public boolean delete(String field) {
        int id = findField(field);
        return fieldMapper.deleteById(id) == 1;
    }

    @Override
    public Integer findField(String field) {
        Field field1 = selectByField(field);
        return field1!=null?field1.getFieldId():-1;
    }

    /**
     * 这玩意应该叫selectByFieldName
     * @param field
     * @return
     */
    @Override
    public Field selectByField(String field) {
        QueryWrapper<Field> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", field);
        return fieldMapper.selectOne(queryWrapper);
    }

    @Override
    public String SelectFieldNameById(int fieldID) {
       Field field=fieldMapper.selectById(fieldID);
      return field.getName();
    }

    @Override
    public List<FieldStatistics> seeHotSubmittedFields() {
        return fieldStatisticsMapper.getFieldStatistics();
    }
}
