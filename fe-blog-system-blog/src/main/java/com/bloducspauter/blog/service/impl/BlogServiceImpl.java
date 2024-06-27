package com.bloducspauter.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.bloducspauter.bean.*;
import com.bloducspauter.blog.service.BlogService;
import com.bloducspauter.mapper.BlogMapper;
import com.bloducspauter.mapper.MediaMapper;
import com.bloducspauter.mapper.TagMapper;
import com.bloducspauter.mapper.TagRelationMapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
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

    @Autowired
    private MediaMapper mediaMapper;

    private List<Blog> accesBlogMediaName(List<Blog> blogs) {
        for (Blog b : blogs) {
            int mediaId = b.getMediaId();
            Media media = mediaMapper.selectById(mediaId);
            b.setMediaName(media.getImage());
        }
        return blogs;
    }

    @Override
    public boolean addBlog(Blog blog) {
        blog.setClicks(0);
        int row = blogMapper.insert(blog);
        return row > 0;
    }

    @Override
    public void deleteBlog(int blogId) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        Blog blog = selectInBlog(blogId);
        blog.setDeleted(1);
        blog.setStatus(3);
        int row = blogMapper.update(blog, queryWrapper);
    }

    @Override
    public void delete(Integer id) {
        blogMapper.deleteById(id);
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
        List<Blog> blogs = blogMapper.selectByBlogLimit(userId, page, size);
        return accesBlogMediaName(blogs);
    }


    @Override
    public List<Blog> fuzzyQuery(@Param("userId") int userId, @Param("title") String title, @Param("page") int page, @Param("size") int size) {
        List<Blog> blogs = blogMapper.fuzzyQuery(userId, title, page, size);
        return accesBlogMediaName(blogs);
    }


    @Override
    public List<Blog> randomQuery(@Param("userId") int userId, @Param("page") int page, @Param("size") int size) {
        List<Blog> blogs = blogMapper.randomQuery(userId, page, size);
        return accesBlogMediaName(blogs);
    }


    @Override
    public boolean addRelation(int blogId, int tagId) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        BlogTag blogTag = new BlogTag();
        blogTag.setBlogId(blogId);
        blogTag.setTagId(tagId);
        int row = blogMapper.addRelation(blogTag);
        return row > 0;
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
    public void deleteBlogTag(int blogId) {
        QueryWrapper<TagRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        List<TagRelation> tagRelation = tagRelationMapper.selectList(queryWrapper);
        for (TagRelation t : tagRelation) {
            t.setDeleted(1);
            tagRelationMapper.update(t, queryWrapper);
        }
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
        List<Blog> blogs = blogMapper.selectDeletedBlogLimit(userId, page, size);
        return accesBlogMediaName(blogs);
    }

    @Override
    public int selectBlogCount() {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("*");
        queryWrapper.eq("deleted", 0);
        queryWrapper.eq("audited", "已通过");
        return blogMapper.selectCount(queryWrapper);
    }

    @Override
    public List<Tag> selectByTag(List<String> tag) {
        return blogMapper.selectByTag(tag);
    }


    @Override
    public List<Blog> selectBlogByTag(String tagname) {
        List<Blog> blogs = blogMapper.selectblogbytag(tagname);
        return accesBlogMediaName(blogs);
    }

    @Override
    public List<Blog> selectAuditingBlog() {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("audited", "待审核");
        List<Blog> blogs = blogMapper.selectList(queryWrapper);
        return accesBlogMediaName(blogs);
    }


    @Override
    public List<Blog> popularBlogs() {
        return blogMapper.selectHotBlogs();
    }

    @Override
    public List<Blog> selectBlogbyField(int Fieldid, int user_id, int page, int size) {
        List<Blog> blogs = blogMapper.selectBlogbyField(Fieldid, user_id, page, size);
        return accesBlogMediaName(blogs);
    }

    @Override
    public List<Blog> selectBlogbytitle(int fieldid, int userid, String blogtitle, int page, int size) {
        List<Blog> blogs = blogMapper.selectBlogbytitle(fieldid, userid, blogtitle, page, size);
        return accesBlogMediaName(blogs);
    }
}
