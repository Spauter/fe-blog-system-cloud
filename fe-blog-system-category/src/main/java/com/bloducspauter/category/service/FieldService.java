package com.bloducspauter.category.service;



import com.bloducspauter.bean.Field;
import com.bloducspauter.bean.statistics.FieldStatistics;

import java.util.List;

public interface FieldService {


    /**
     * 查询所有领域
     *
     * @return 查询的集合
     * @author
     * @time
     * @version
     */
    List<Field> selectALL();

    /**
     * 往库里增加一个领域
     *
     * @param field
     * @return 查询的集合
     * @author
     * @time
     * @version
     */
    boolean add(String field);

    /**
     * 在库里删除一个领域
     *
     * @param field
     * @return 查询的集合
     * @author
     * @time
     * @version
     */
    boolean delete(String field);

    /**
     * 通过分类名称查找分类id
     *
     * @param field
     * @return 分类id
     */
    Integer findField(String field);

    /**
     * 根据field查id
     *
     * @param field
     * @return
     */
    Field selectByField(String field);

    String SelectFieldNameById(int fieldID);

    List<FieldStatistics>seeHotSubmittedFields();

}
