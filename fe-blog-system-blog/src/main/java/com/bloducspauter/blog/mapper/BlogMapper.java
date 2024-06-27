package com.bloducspauter.blog.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bloducspauter.bean.Blog;
import com.bloducspauter.bean.BlogTag;
import com.bloducspauter.bean.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    /**
     * 根据fieldid和blogtitle查询blog
     * @param Fieldid
     * @param blogtitle
     * @return
     */
    List<Blog> selectBlogbytitle(int Fieldid, int user_id, String blogtitle, @Param("page") int page, @Param("size") int size);

    List<Blog> selectBlogbyField(@Param("Fieldid") int Fieldid, @Param("user_id") int user_id, @Param("page") int page, @Param("size") int size);

    List<Blog> selectByBlogLimit(@Param("userId") int userId, @Param("page") int page, @Param("size") int size);

    /**
     * 根据标题模糊查询博客
     *
     * @time
     * @version
     * @param title
     * @return
     */
    List<Blog> fuzzyQuery(@Param("userId") int userId,@Param("title") String title, @Param("page") int page, @Param("size") int size);

    /**
     * 随机查询
     *
     * @time
     * @version
     * @return
     */
    List<Blog> randomQuery(@Param("userId") int userId, @Param("page") int page, @Param("size") int size);

    /**
     * 绑定博客与标签
     *
     * @param blogTag
     * @return
     */
    int addRelation(BlogTag blogTag);

    /**
     * 根据标签名查询tagId
     *
     * @param tag
     * @return
     */
    List<Tag> selectByTag(List<String> tag);

    List<Blog> selectDeletedBlogLimit(@Param("userId") int userId, @Param("page") int page, @Param("size") int size);
    /**
     * 通过标签名查询到该标签下的博客信息
     * @param tagname
     * @return
     */
    List<Blog> selectblogbytag(String tagname);

    List<Blog>selectHotBlogs();


}
