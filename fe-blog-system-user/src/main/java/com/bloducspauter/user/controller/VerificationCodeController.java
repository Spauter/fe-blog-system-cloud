package com.bloducspauter.user.controller;


import com.bloducspauter.bean.utils.CreateVerificationCode;
import com.bloducspauter.bean.utils.CreateVerificationCodeImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@RestController
@Slf4j
@RequestMapping("fe-user")
public class VerificationCodeController {

    @RequestMapping("/VerificationCodeController")
    public void VerificationCode(HttpServletResponse resp, HttpSession session) throws IOException {

        String vericode = CreateVerificationCode.getSecurityCode();
        //Session交给了redis去管理
//      HttpSession session = req.getSession();
        session.setAttribute("verifyCode",vericode);
        log.info("verifyCode:"+vericode);
        //设置返回的内容
        resp.setContentType("img/jpeg");
        //浏览器不缓存响应内容--验证码图片，点一次就要刷新一次，所以不能有缓存出现
        resp.setHeader("Pragma","No-cache");
        resp.setHeader("Cache-Control","no-cache");
        //设置验证码失效时间
        resp.setDateHeader("Expires",0);
        //以字节流发过去，交给img的src属性去解析即可
        ImageIO.write(new CreateVerificationCodeImage(vericode).createImage(),"JPEG",resp.getOutputStream());

    }
}
