package com.bloducspauter.user.controller;


import com.bloducspauter.bean.MediaFiles;
import com.bloducspauter.bean.User;
import com.bloducspauter.bean.utils.DefaultValue;
import com.bloducspauter.media.service.MediaService;
import com.bloducspauter.user.service.UserService;
import com.bloducspauter.bean.utils.IsValidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("fe-user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private MediaService mediaService;

    private User getUser(HttpServletRequest request) {
        String account = request.getParameter("username");
        return userService.Login(account);
    }

    public User getRedisUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token == null) {
            return null;
        }
        return (User) redisTemplate.opsForValue().get(token);
    }

    @PostMapping("UserLoginController")
    public Map<String, Object> login(HttpServletRequest req) {
        Map<String, Object> map = new HashMap<>();
        User loginUser = getUser(req);
        if (loginUser == null) {
            map.put("code", 404);
            map.put("msg", "用户名或者密码错误");
            return map;
        }
        String password = req.getParameter("password");
        if (!password.equals(loginUser.getPassword())) {
            map.put("code", 404);
            map.put("msg", "用户名或者密码错误");
            return map;
        }
        loginUser.setPassword("想看密码？怎么可能给你看😜");
        //生成token;
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token, loginUser);
        map.put("code", 200);
        map.put("data", loginUser);
        map.put("token", token);
        return map;
    }

    @RequestMapping("register")
    public Map<String, Object> register(HttpServletRequest request) {
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
        User user = userService.register(account, password, email);
        user.setPassword("想看密码？怎么可能会给你看😜");
        map.put("code", 200);
        map.put("data", user);
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token, user);
        map.put("token", token);
        return map;
    }

    //此段代码是用来获取session（保持登录的）
    @GetMapping("UserLoginController")
    public Map<String, Object> getLoginUser(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        User user = getRedisUser(request);
        if (user != null) {
            map.put("code", 200);
            map.put("msg", "已经登录");
            map.put("data", user);
        } else {
            map.put("code", 404);
            map.put("msg", "没有登录");

        }
        return map;
    }


    @RequestMapping("UserLogoutController")
    public Map<String, Object> loginOut(HttpServletRequest request) {
        String token = request.getHeader("token");
        Map<String, Object> map = new HashMap<>();
        redisTemplate.delete(token);
        map.put("code", 200);
        map.put("msg", "登出成功");
        return map;
    }


    @RequestMapping("userUpdateServlet")
    public Map<String, Object> updateUser(HttpServletRequest request) {

        Map<String, Object> map = new HashMap<>();
        //获取登录的user对象，如果没有用会话对象，返回null
        User sessionUser = getRedisUser(request);
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
    public Map<String, Object> modifyPwd(HttpServletRequest request ){
        Map<String, Object> map = new HashMap<>();
        String token = request.getHeader("token");
        User tokenUser =getRedisUser(request);
        if (tokenUser == null) {
            map.put("code", 404);
            map.put("msg", "没有登录,请先登录");
            return map;
        }
        User user = userService.getInfo(tokenUser.getAccount());
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
            redisTemplate.delete("token");
        } else {//密码错误时
            map.put("code", 500);
            map.put("msg", "输入的密码错误，请重新输入");
        }
        return map;
    }

    @RequestMapping("UserUpdateAvatarController")
    public Map<String, Object> updateAvatar(@RequestParam MultipartFile avatar, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String token = request.getHeader("token");
        File file = null;
        try {
            User sessionUser = getRedisUser(request);
            User user = userService.getInfo(sessionUser.getAccount());
            String path= DefaultValue.getUploadTempPath();
            path += avatar.getOriginalFilename();
            file = new File(path);
            avatar.transferTo(file);
            //根据操作系统的类型进行图片上传操作
            MediaFiles newFile = mediaService.uploadFile(file, user.getUserId(), avatar.getOriginalFilename());
            user.setAvatar(newFile.getUrl());
            userService.updateInfo(user);
            redisTemplate.opsForValue().set(token, user);
            map.put("code", 200);
            map.put("data", user);
        } catch (Exception e) {
            log.error(e.getMessage());
            map.put("code", 500);
            map.put("msg", e.getMessage());
        } finally {
            if (file != null) {
                file.delete();
            }
        }
        return map;
    }

}
