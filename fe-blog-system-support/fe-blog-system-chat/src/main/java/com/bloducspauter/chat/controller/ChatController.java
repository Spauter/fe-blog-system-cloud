package com.bloducspauter.chat.controller;

import com.bloducspauter.chat.entity.NettyJson;
import com.bloducspauter.chat.service.NettyJsonService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
/**
 * @author Bloduc Spauter
 *
 */
@RestController
@RequestMapping("fe-ornament")
public class ChatController {

    @Resource
    private NettyJsonService nettyJsonService;

    @GetMapping("findAll")
    public List<NettyJson>findAll() {
        return nettyJsonService.findAll();
    }

    @RequestMapping("getByPage")
    public List<NettyJson>findByPage(String location,int pageNo,int pageSize) {
        return nettyJsonService.selectListByPage(location,pageNo,pageSize);
    }

}
