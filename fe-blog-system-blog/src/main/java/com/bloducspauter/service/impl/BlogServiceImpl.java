package com.bloducspauter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.bloducspauter.bean.Blog;
import com.bloducspauter.bean.BlogTag;
import com.bloducspauter.bean.Tag;
import com.bloducspauter.bean.TagRelation;
import com.bloducspauter.mapper.BlogMapper;
import com.bloducspauter.mapper.TagMapper;
import com.bloducspauter.mapper.TagRelationMapper;
import com.bloducspauter.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
@Slf4j
public class BlogServiceImpl implements BlogService {


    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TagRelationMapper tagRelationMapper;


    @Override
    public boolean addBlog(Blog blog) {
        blog.setClicks(0);
        blog.setAudited("待审核");
        boolean flag = false;
        int row = blogMapper.insert(blog);
        if (row > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean deleteBlog(int blogId) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        Blog blog = selectInBlog(blogId);
        blog.setDeleted(1);
        blog.setStatus(3);
        int row = blogMapper.update(blog, queryWrapper);
       return row>0;
    }

    @Override
    public boolean modifyBlog(Blog blog) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blog.getBlogId());
        int row = blogMapper.update(blog, queryWrapper);
        return row > 0;
    }


    @Override
    public List<Blog> selectByBlogLimit(@Param("userId") int userId, @Param("page") int page, @Param("size") int size) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);
        queryWrapper.eq("user_id", userId);
        return blogMapper.selectByBlogLimit(userId, page, size);
    }


    @Override
    public List<Blog> fuzzyQuery(@Param("userId") int userId, @Param("title") String title, @Param("page") int page, @Param("size") int size) {

        return blogMapper.fuzzyQuery(userId, title, page, size);
    }


    @Override
    public List<Blog> randomQuery(@Param("userId") int userId, @Param("page") int page, @Param("size") int size) {
        return blogMapper.randomQuery(userId, page, size);
    }


    @Override
    public boolean addRelation(int blogId, int tagId) {
        boolean flag = false;

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        BlogTag blogTag = new BlogTag();
        blogTag.setBlogId(blogId);
        blogTag.setTagId(tagId);

        int row = blogMapper.addRelation(blogTag);
        if (row > 0) {
            flag = true;
        }
        return flag;
    }

    //是根据标题去找BLog,不是Id
    @Override
    public Blog selectByBlogId(String title) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        return blogMapper.selectOne(queryWrapper);
    }

    //这个才是blog_id
    @Override
    public Blog selectInBlog(int blogId) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        return blogMapper.selectOne(queryWrapper);
    }

    @Override
    public int deleteBlogTag(int blogId) {
        QueryWrapper<TagRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        List<TagRelation> tagRelation = tagRelationMapper.selectList(queryWrapper);
        int result = 0;
        for (TagRelation t : tagRelation) {
            t.setDeleted(1);
            result = tagRelationMapper.update(t, queryWrapper);
        }
        return result;
    }

    /**
     * 根据博客id查询全部标签
     *
     * @param blogId
     */
    @Override
    public List<Tag> selectTagsByBlog(int blogId) {
        //采用的注解注释的方式
        return tagMapper.selectTagsByBlog(blogId);
    }


    @Override
    public List<Blog> selectDeletedBlog(int userId, int page, int size) {
        return blogMapper.selectDeletedBlogLimit(userId, page, size);
    }

    @Override
    public int selectBlogCount() {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("*");
        queryWrapper.eq("deleted", 0);
        return blogMapper.selectCount(queryWrapper);
    }

    @Override
    public List<Tag> selectByTag(List<String> tag) {
        return blogMapper.selectByTag(tag);
    }


    @Override
    public List<Blog> selectblogbytag(String tagname) {
        return blogMapper.selectblogbytag(tagname);
    }

    @Override
    public List<Blog> selectAuditingBlog() {
        QueryWrapper<Blog>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("audited","待审核");
        return blogMapper.selectList(queryWrapper);
    }



    @Override
    public List<Blog> popularBlogs() {
        return blogMapper.selectHotBlogs();
    }
}
