package com.bloducspauter.api.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@FeignClient(name = "fe-user")
public interface   FeBlogUserApi {
    @PostMapping("/fe-user/UserLoginController")
    Map<String, Object> login(HttpServletRequest req, HttpSession session);

    @GetMapping("/fe-user/UserLoginController")
    Map<String, Object> getLoginedUser(HttpSession session);

    @RequestMapping("/fe-user/UserLogoutController")
    Map<String, Object> loginOut(HttpSession session);

    @RequestMapping("/fe-user/userUpdateServlet")
    Map<String, Object> updateUser(HttpServletRequest request, HttpSession session);


    @RequestMapping("/fe-user/updatePassword")
    Map<String, Object> modifyPwd(HttpServletRequest request, HttpSession session);

    @RequestMapping("/fe-user/UserUpdateAvatarController")
    Map<String, Object> updateAvatar(@RequestParam MultipartFile avatar, HttpSession session);

    @RequestMapping("/fe-user/VerificationCodeController")
    void VerificationCode(HttpServletResponse resp, HttpSession session);


}
