package com.bloducspauter.blog.service;


import com.bloducspauter.bean.Blog;
import com.bloducspauter.bean.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 祁
 */

public interface BlogService {
    /**
     * 增加博客
     *
     * @param blog
     * @return boolean
     * @time 8-9
     * @version
     */
    boolean addBlog(Blog blog);

    /**
     * 删除博客
     *
     * @param blogId
     * @time 8-9
     * @version
     */
    void deleteBlog(int blogId);

    void delete(Integer id);

    /**
     * 修改博客
     *
     * @param blog
     * @return boolean
     * @time 8-9
     * @version
     */
    boolean modifyBlog(@Param("blog") Blog blog);


    /**
     * 分页查询博客管理页面
     *
     * @param page
     * @return
     * @time 2021/8/9
     * @version 1.0
     */
    List<Blog> selectByBlogLimit(@Param("userId") int userId, @Param("page") int page, @Param("size") int size);

    /**
     * 查询含有指定标题的博客（即模糊查询）
     *
     * @param title
     * @return 查询的集合
     * @time 2021/8/9
     * @version 1.0
     */
    List<Blog> fuzzyQuery(@Param("userId") int userId, @Param("title") String title, @Param("page") int page, @Param("size") int size);

    /**
     * 随机查询几条博客(用户默认展示的博客，如果少于用户的博客少于五条，则展示全部，多于五条则随机抽取五条展示)
     *
     * @return 查询的集合
     * @time 2021/8/9
     * @version
     */
    List<Blog> randomQuery(@Param("userId") int userId, @Param("page") int page, @Param("size") int size);

    /**
     * 在tag_relation表中建立联系
     *
     * @param blogId
     * @param tagId
     * @return
     */
    boolean addRelation(int blogId, int tagId);

    /**
     * 查询博客id
     *
     * @param title
     * @return
     */
    Blog selectByBlogId(String title);

    /**
     * 详细查询
     *
     * @param blogId
     * @return
     */
    Blog selectInBlog(int blogId);

    /**
     * 删除某一博客的对应关系
     *
     * @param blogId
     */
    void deleteBlogTag(int blogId);

    /**
     * 根据博客id查询全部标签
     */
    List<Tag> selectTagsByBlog(int blogId);

    List<Blog> selectDeletedBlog(@Param("userId") int userId, @Param("page") int page, @Param("size") int size);

    int selectBlogCount();

    /**
     * 根据标签名查询tagId
     *
     * @param tag
     */
    List<Tag> selectByTag(List<String> tag);

    /**
     * 查询该标签下所有的博客信息
     *
     * @param tagName
     */
    List<Blog> selectBlogByTag(String tagName);


    List<Blog> selectAuditingBlog();

    List<Blog> popularBlogs();
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
}
