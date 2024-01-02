package com.bloducspauter.controller;

import com.bloducspauter.bean.Comment;
import com.bloducspauter.bean.Reply;
import com.bloducspauter.bean.User;
import com.bloducspauter.service.CommentService;
import com.bloducspauter.service.ReplyService;
import com.bloducspauter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("fe-ornament")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @RequestMapping("findResponseByCommentId")
    public Map<String,Object>findResponseByCommentId(HttpServletRequest request){
        Map<String,Object>map=new HashMap<>();
        String commentId=request.getParameter("cid");
        List<Reply>list;
        try{
            int cid=Integer.parseInt(commentId);
            list=replyService.selectAllResponseByCommentId(cid);
            if (list.isEmpty()){
                map.put("code",404);
                map.put("msg","暂无回复列表");
            }else {
                map.put("code",200);
                map.put("data",list);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            map.put("code",500);
            map.put("msg",e.getCause());
        }
        return map;
    }

    @GetMapping("getCommentedUser")
    public Map<String,Object>getCommentedUser(HttpServletRequest request){
        Map<String,Object>map=new HashMap<>();
        String commentId=request.getParameter("cid");
        try {
            int cid=Integer.parseInt(commentId);
            Comment selectedComment=commentService.selectCommentById(cid);
            if (selectedComment==null){
                map.put("code",500);
                map.put("msg","找不到选中的评论");
                return map;
            }
            User commentedUser = userService.getInfo(selectedComment.getAccount());
            commentedUser.setPassword("到这里了居然还想看😮");
            Map<String,Object>resultMap=new HashMap<>();
            resultMap.put("user",commentedUser);
            resultMap.put("comment",selectedComment);
            map.put("code",200);
            map.put("data",resultMap);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            map.put("code",500);
            map.put("msg",e.getCause());
        }
        return map;
    }


    @PostMapping("addResponse")
    public Map<String,Object>addResponce(HttpServletRequest request, HttpSession session){
        Map<String,Object>map=new HashMap<>();
        User loginUser= (User) session.getAttribute("user");
        if(loginUser==null){
            map.put("code",404);
            map.put("msg","未登录");
            return map;
        }
        try{
            String commentId=request.getParameter("cid");
            String content=request.getParameter("content");
            int cid=Integer.parseInt(commentId);
            Reply reply=new Reply();
            reply.setId(cid);
            reply.setAccount(loginUser.getAccount());
            reply.setContent(content);
            reply.setRdate(new Date());
            int i=replyService.addReply(reply);
            if (i>0){
                map.put("code",200);
                map.put("msg","回复成功");
            }else {
                map.put("code",500);
                map.put("msg","回复失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            map.put("code",500);
            map.put("msg",e.getCause());
        }
        return map;
    }
}
