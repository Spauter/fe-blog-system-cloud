package com.bloducspauter.category.controller;
import com.alibaba.fastjson.JSONObject;
import com.bloducspauter.bean.Comment;
import com.bloducspauter.bean.User;
import com.bloducspauter.category.service.CommentService;
import com.bloducspauter.bean.utils.GetRequestJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("fe-ornament")
public class CommentController {

    @Autowired
    CommentService commentService;


    private Comment getComment(HttpServletRequest request) throws IOException {
        JSONObject json = GetRequestJson.getRequestJsonObject(request);
        int blogId = json.getIntValue("blog_id");
        String content = json.getString("content");
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setBlogId(blogId);
        return comment;

    }


    @RequestMapping("AddCommentServlet")
    public Map<String, Object> addComment(HttpServletRequest request,HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        User user= (User) session.getAttribute("user");
        if(user==null){
            map.put("code",404);
            map.put("msg","登录后才能评论哦");
            return map;
        }
        try {
            Comment comment = getComment(request);
            //修改评论方名字为登录名
            if (comment.getContent()==null){
                map.put("code", 500);
                map.put("msg", "请输入评论");
                return map;
            }
            comment.setAccount(user.getAccount());
            int result;
            result = commentService.add(comment);
            if (result != 0) {
                map.put("code", 200);
                map.put("msg", "操作成功");
            } else {
                map.put("code", 500);
                map.put("msg", "操作失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            map.put("code", 500);
            map.put("msg", e.getCause());

        }
        return map;
    }


    @RequestMapping("ModifyCommentServlet")
    public Map<String, Object> modifyComment(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Comment modify;
        try {
            Comment comment = getComment(request);
            modify = commentService.modify(comment);
            if (modify != null) {
                map.put("code", 200);
                map.put("msg", "操作成功");
            } else {
                map.put("code", 500);
                map.put("msg", "操作失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            map.put("code", 500);
            map.put("msg", e.getMessage());
        }
        return map;
    }


    @RequestMapping("DeleteCommentServlet")
    public Map<String, Object> delComment(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject json = GetRequestJson.getRequestJsonObject(request);
            int id = json.getIntValue("id");
            boolean result = commentService.deleted(id);
            if (result) {
                map.put("code", 200);
                map.put("msg", "操作成功");
            } else {
                map.put("code", 500);
                map.put("msg", "操作失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            map.put("code", 500);
            map.put("msg", e.getCause());
        }
        return map;
    }


    @RequestMapping("SelectAllCommentCountServlet")
    public Map<String, Object> selectAllComment(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            int blogId = Integer.parseInt(request.getParameter("blog_id"));
            int count = commentService.selectCommentCount(blogId);
            if (count > 0) {
                map.put("code", 200);
                map.put("msg", "操作成功");
                map.put("data", count);
            } else {
                map.put("code", 500);
                map.put("msg", "操作失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            map.put("code", 500);
            map.put("msg", e.getCause());
        }
        return map;
    }

    @GetMapping("SelectAllCommentServlet")
    public Map<String, Object> findAllComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int blogId = Integer.parseInt(request.getParameter("blog_id"));
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        log.info(blogId + "" + page + size);
        Map<String,Object>map=new HashMap<>();
        try {
            List<Comment> comments = commentService.selectAll(blogId, page, size);
            if(comments!=null){
                map.put("code",200);
                map.put("msg","获取评论成功");
                map.put("data",comments);
            }else {
                map.put("code",404);
                map.put("msg","该博客暂时没有评论");
            }
           } catch (Exception e) {
           log.error(e.getMessage());
           map.put("code",500);
           map.put("msg",e.getCause());
        }
        return map;
    }
}
