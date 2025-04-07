package com.bloducspauter.chat.controller;

import com.bloducspauter.bean.Comment;
import com.bloducspauter.bean.User;
import com.bloducspauter.chat.entity.NettyJson;
import com.bloducspauter.chat.service.NettyJsonService;
import com.bloducspauter.chat.util.AuditUtil;
import com.bloducspauter.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bloduc Spauter
 */
@RestController
@RequestMapping("fe-chat")
public class ChatController {

    @Resource
    private NettyJsonService nettyJsonService;

    @Resource
    private UserService userService;

    @GetMapping("findAll")
    public List<NettyJson> findAll() {
        return nettyJsonService.findAll();
    }

    @RequestMapping("getByPage")
    public List<NettyJson> findByPage(String location, int pageNo, int pageSize) {
        return nettyJsonService.selectListByPage(location, pageNo, pageSize);
    }

    @RequestMapping("audit")
    public Map<String, Object> AuditChat(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String text = request.getParameter("text");
        return AuditUtil.getResult(text);
    }

    @GetMapping("getCommentedUser")
    public Map<String, Object> getCommentedUser(javax.servlet.http.HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String commentId = request.getParameter("cid");
        try {
           NettyJson nettyJson= nettyJsonService.getCommentUserByCid(commentId);
            if (nettyJson == null) {
                map.put("code", 500);
                map.put("msg", "找不到选中的评论");
                return map;
            }
            String account=nettyJson.getAccount();
            User commentedUser = userService.getInfo(account);
            commentedUser.setPassword("到这里了居然还想看😮");
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("user", commentedUser);
            resultMap.put("comment", account);
            map.put("code", 200);
            map.put("data", resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", e.getMessage());
        }
        return map;
    }
    record A(int a,String b){

    }
}
