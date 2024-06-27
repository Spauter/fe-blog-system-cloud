package com.bloducspauter.user.controller;


import com.bloducspauter.bean.User;
import com.bloducspauter.user.service.UploadService;
import com.bloducspauter.user.service.UserService;
import com.bloducspauter.bean.utils.IsValidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.bloducspauter.bean.utils.DefaultValue.UPLOAD_AVATAR_PATH;

@Slf4j
@RestController
@RequestMapping("fe-user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UploadService uploadService;

    private User getUser(HttpServletRequest request) {
        String account = request.getParameter("username");
        return userService.Login(account);
    }


    @PostMapping("UserLoginController")
    public Map<String, Object> login(HttpServletRequest req, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        User loginUser = getUser(req);
        if (loginUser == null) {
            map.put("code", 404);
            map.put("msg", "用户名或者密码错误");
            return map;
        }
        String password = req.getParameter("password");
        String vericode = req.getParameter("vericode");
        String generatedCode = (String) session.getAttribute("verifyCode");
        if (!vericode.equals(generatedCode)) {
            map.put("code", 500);
            map.put("msg", "验证码错误");
            return map;
        }
        if (!password.equals(loginUser.getPassword())) {
            map.put("code", 404);
            map.put("msg", "用户名或者密码错误");
            return map;
        }
        loginUser.setPassword("想看密码？怎么可能给你看😜");
        map.put("code", 200);
        map.put("data", loginUser);
        session.setAttribute("user", loginUser);
        return map;
    }

    @RequestMapping("register")
    public Map<String, Object> register(HttpServletRequest request, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        User isAlreadyLoginUser = getUser(request);
        if (isAlreadyLoginUser != null) {
            map.put("code", 500);
            map.put("msg", "该账号已经被注册");
            return map;
        }
        String account = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String vericode = request.getParameter("vericode");
        String generatedCode = (String) session.getAttribute("emailVerifyCode");
        if(generatedCode==null){
            map.put("code", 500);
            map.put("msg", "无法获取验证码");
            return map;
        }
        if (!password.equals(confirmPassword)) {
            map.put("code", 500);
            map.put("msg", "两次输入的密码不一致");
            return map;
        }
        if (new IsValidUtil().isValidEmail(email)) {
            map.put("code", 500);
            map.put("msg", "邮箱格式不正确");
            return map;
        }
        if (!vericode.equals(generatedCode)) {
            map.put("code", 500);
            map.put("msg", "邮箱验证码错误");
            return map;
        }
        //判断发送邮箱的电子邮箱是否是当前填写的邮箱
        String registerEmail= (String) session.getAttribute("registerEmail");
        if(!registerEmail.equals(email)){
            map.put("code",500);
            map.put("msg","输入的电子邮箱与请求发送的电子邮箱不一致");
            return map;
        }
        User user = userService.register(account, password, email);
        user.setPassword("想看密码？怎么可能会给你看😜");
        map.put("code", 200);
        map.put("data", user);
        session.setAttribute("user", user);
        return map;
    }

    //此段代码是用来获取session（保持登录的）
    @GetMapping("UserLoginController")
    public Map<String, Object> getLoginedUser(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            map.put("code", 200);
            map.put("msg", "已经登录");
            map.put("data",user);
        } else {
            map.put("code", 404);
            map.put("msg", "没有登录");

        }
        return map;
    }


    @RequestMapping("UserLogoutController")
    public Map<String, Object> loginOut(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        session.removeAttribute("user");
        map.put("code", 200);
        map.put("msg", "登出成功");
        return map;
    }



    @RequestMapping("userUpdateServlet")
    public Map<String, Object> updateUser(HttpServletRequest request, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        //获取登录的user对象，如果没有用会话对象，返回null
        User sessionUser = (User) session.getAttribute("user");
        User user = userService.getInfo(sessionUser.getAccount());
        if (user == null) {
            map.put("code", 404);
            map.put("data", null);
            map.put("msg", "没有登录");
            return map;
        }
        //从userinfo表单里获取属性值
        String nick = request.getParameter("nick");
        String date1 = request.getParameter("date");
        //将字符串转化为sql下的date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(date1);
        } catch (ParseException e) {
            log.error(e.getMessage());
            map.put("code", 500);
            map.put("msg", e.getCause());
        }
        assert date != null;
        java.sql.Date birthday = new java.sql.Date(date.getTime());
        String sex = request.getParameter("sex");
        if ("boy".equalsIgnoreCase(sex)) {
            sex = "男";
        } else {
            sex = "女";
        }
        String profile = request.getParameter("profile");
        user.setNick(nick);
        user.setBirthday(birthday);
        user.setSex(sex);
        user.setProfile(profile);
        User updateUser = userService.updateInfo(user);
        //
        if (updateUser == null) {
            map.put("code", 500);
            map.put("msg", "抱歉，修改失败");
        } else {
            map.put("code", 200);
            map.put("data", updateUser);
        }
        return map;
    }

    @RequestMapping("updatePassword")
    public Map<String, Object> modifyPwd(HttpServletRequest request, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            map.put("code", 404);
            map.put("msg", "没有登录,请先登录");
            return map;
        }
        User user = userService.getInfo(sessionUser.getAccount());
        String username = user.getAccount();
        //previousPassword是当前密码，先验证是否输入正确
        String previousPassword = request.getParameter("previousPassword");
        //密码正确时
        if (userService.judgePassword(username, previousPassword)) {
            //modifiedPassword是后面修改的密码
            String modifiedPassword = request.getParameter("modifiedPassword");
            userService.updatePassword(username, modifiedPassword);
            map.put("code", 200);
            map.put("msg", "修改密码成功,请重新登录");
            session.removeAttribute("user");
        } else {//密码错误时
            map.put("code", 500);
            map.put("msg", "输入的密码错误，请重新输入");
        }
        return map;
    }

    @RequestMapping("UserUpdateAvatarController")
    public Map<String, Object> updateAvatar(@RequestParam MultipartFile avatar, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        try {
            User sessionUser = (User) session.getAttribute("user");
            User user = userService.getInfo(sessionUser.getAccount());
            String osName = System.getProperty("os.name");
            String path;
            //根据操作系统的类型进行图片上传操作
            if (osName.startsWith("Windows")) {
                path = this.uploadService.uploadToWindows(avatar, UPLOAD_AVATAR_PATH, true);
            } else {
                path = this.uploadService.uploadToNginx(avatar);
            }
            user.setAvatar(path);
            userService.updateInfo(user);
            session.setAttribute("user", user);
            map.put("code", 200);
            map.put("data", user);
        } catch (Exception e) {
            log.error(e.getMessage());
            map.put("code", 500);
            map.put("msg", e.getCause());
        }
        return map;
    }

}
