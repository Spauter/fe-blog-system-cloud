package com.bloducspauter.controller;

import com.bloducspauter.demo.BsSendEmailFunction;
import com.bloducspauter.bean.utils.IsValidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@Slf4j
@RequestMapping("fe-user")
public class EmailVerificationController {
    @Autowired
    BsSendEmailFunction bsSendEmailFunction;

    @RequestMapping("/sendEmail")
    @ResponseBody
    public Map<String, Object> sendEmail(HttpServletRequest request, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        Random rd = new Random();
        StringBuilder emailcode = new StringBuilder();
        int[] a = new int[6];
        for (int i = 0; i < a.length; i++) {
            a[i] = rd.nextInt(10);
            emailcode.append(a[i]);
        }
        String to = request.getParameter("email");
        log.info("Session code:"+ session.getAttribute("emailVerifyCode"));
        if (new IsValidUtil().isValidEmail(to)) {
            map.put("code", 500);
            map.put("msg", "非法邮件地址");
            return map;
        }
        try {
            bsSendEmailFunction.sendEmailVerifyCode("EmailVerify", to,emailcode.toString());
            map.put("code", 200);
            map.put("msg", "邮件发送成功");
            session.setAttribute("registerEmail",to);
            session.setAttribute("emailVerifyCode", emailcode.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            map.put("code", 500);
            map.put("msg", "发送失败");
            map.put("cause", e.getCause());
        }
        return map;
    }

}
