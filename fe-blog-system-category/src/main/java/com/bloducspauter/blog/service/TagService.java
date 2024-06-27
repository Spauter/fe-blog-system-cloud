package com.bloducspauter.service;



import com.bloducspauter.bean.Tag;
import com.bloducspauter.bean.statistics.TagStatistics;

import java.util.List;

public interface TagService {
    /**
     * 查询所有标签
     *
     * @return 查询的集合
     * @author 风
     * @time   8-9
     * @version
     */
    List<Tag> selectALL();


    /**
     *  往库里增加一个标签
     *
     * @param tag
     * @return 查询的集合
     * @time   8-9
     * @version
     */
    boolean add(String tag);

    /**
     *  在库里删除一个标签
     *
     * @param tag
     * @return 查询的集合
     * @time   8-9
     * @version
     */
    boolean delete(String tag);


    List<TagStatistics> seeHotSubmittedTags();
}
