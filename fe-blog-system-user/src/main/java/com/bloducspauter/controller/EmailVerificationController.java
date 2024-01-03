package com.bloducspauter.controller;

import com.bloducspauter.demo.BsSendEmailFunction;
import com.bloducspauter.utils.IsValidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("fe-user")
public class EmailVerificationController {
    @Autowired
    BsSendEmailFunction bsSendEmailFunction;

    @RequestMapping("/sendEmail")
    @ResponseBody
    public Map<String, Object> sendEmail(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String to = request.getParameter("email");
        if (new IsValidUtil().isValidEmail(to)) {
            map.put("code", 500);
            map.put("msg", "非法邮件地址");
            return map;
        }
        try {
            bsSendEmailFunction.sendEmail("sendEmail", to);
            map.put("code", 200);
            map.put("msg", "邮件发送成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            map.put("code", 500);
            map.put("msg", "发送失败");
            map.put("cause", e.getCause());
        }
        return map;
    }

}
