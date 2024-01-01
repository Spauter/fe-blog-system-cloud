package com.bloducspauter.controller;

import com.alibaba.nacos.api.naming.pojo.healthcheck.impl.Http;
import com.bloducspauter.bean.Reply;
import com.bloducspauter.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("fe-ornament")
public class ReplyController {
    @Autowired
    private ReplyService replyService;


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
        String account=request.getParameter("account");
        Map<String,Object>map=new HashMap<>();
        return map;
    }


    @PostMapping("addResponse")
    public Map<String,Object>addResponce(HttpServletRequest request, HttpSession session){
        Map<String,Object>map=new HashMap<>();
        return map;
    }
}
