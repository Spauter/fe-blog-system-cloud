package com.bs.controller;


import com.bs.service.EmailServiceImpl;
import com.bs.util.IsValudEmailAddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class EmailVeriricationController {
    @Autowired
    private EmailServiceImpl emailService;

    @Value("${spring.mail.username}")
    private String from;//发件人地址


    @GetMapping("/EmailVerify")
    public Map<String, Object> sendEmail(HttpServletRequest request, HttpSession session) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String to = request.getParameter("email");
        String code=request.getParameter("code");
        if (to == null) {
            map.put("code", 500);
            map.put("msg", "非法邮件地址");
            log.warn("Empty email address");
            return map;
        }
        if (new IsValudEmailAddressUtil().isValidEmail(to)) {
            log.warn("Invalid email address!");
            map.put("code", 500);
            map.put("msg", "非法邮件地址");
            return map;
        }
        log.info("From email:" + from);
        log.info("To email:" + to);
        String subject = "一次性验证申请";//主题
        String text = to + "，你好!\n" +
                "\n" +
                "我们已收到你要求获得此帐户所用的一次性代码的申请。\n" +
                "\n" +
                "你的一次性代码为: " + code + "\n" +
                "\n" +
                "如果你没有请求此代码，可放心忽略这封电子邮件。别人可能错误地键入了你的电子邮件地址。\n";//内容
        try {
            emailService.sendEmail(from, to, subject, text);
            map.put("code", 200);
            map.put("msg", "邮件发送成功");
            log.info("Email send successfully!");
        } catch (Exception e) {
            log.error(e.getMessage());
            map.put("code", 500);
            map.put("msg", "发送邮件失败，原因:" + e.getCause());

        }
        session.setAttribute("emailVerifyCode", code);
        log.info("The verify code is successfully stored by Redis.If you want to use the code ,please using 'session.getAttribute(\"emailVerifyCode\")' to access your code");
        log.info("Session code:"+ session.getAttribute("emailVerifyCode"));
        return map;
    }


    @RequestMapping("/")
    public String a(){
        return "If you see this page , you're successfully to start the service and working";
    }



}
