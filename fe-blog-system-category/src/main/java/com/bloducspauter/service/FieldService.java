package com.bloducspauter.service;



import com.bloducspauter.bean.Blog;
import com.bloducspauter.bean.Field;
import com.bloducspauter.statistics.FieldStatistics;

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


    /**
     * 根据field名称查询所有的博客
     *
     * @param fieldid
     * @return
     */
    List<Blog> selectBlogbyField(int fieldid, int user_id, int page, int size);

    /**
     * 根据fileldid和blogtitle查询博客
     *
     * @param fieldid
     * @param blogtitle
     * @return
     */
    List<Blog> selectBlogbytitle(int fieldid, int userid, String blogtitle, int page, int size);

    String SelectFieldNameById(int fieldID);

    List<FieldStatistics>seeHotSubmittedFields();

}
