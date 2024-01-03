package com.bloducspauter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.bloducspauter.bean.User;
import com.bloducspauter.mapper.UserMapper;
import com.bloducspauter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.bloducspauter.utils.DefaultValue.DEFAULT_AVATAR;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User Login(String account) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User register(String account, String password, String email) {
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setEmail(email);
        user.setNick(account);
        user.setProfile("系统的默认签名，送给每一个小可爱");
        user.setStatus("注册用户");
        if (System.getProperty("os.name").startsWith("Windows")) {
            user.setAvatar("/"+DEFAULT_AVATAR.get(2));
        } else {
            //默认图片
            user.setAvatar("/"+DEFAULT_AVATAR.get(3));
        }
        int result = userMapper.insert(user);
        return result == 1 ? user : null;
    }

    @Override
    public boolean judgePassword(String account, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return false;
        }

        return user.getPassword().equals(password);
    }


    /**
     * 这是更改个人信息的原xml文件,
     * 改了一下，发送错误会返回修改前的信息
     */
//
//    <select id="updateUser" resultType="com.fe.blog.bean.User">
//      update db_mybatis.user
//      set
//          email=#{email},
//          nick=#{nick},
//          profile=#{profile},
//          sex=#{sex},
//          birthday=#{birthday},
//          update_time=current_time
//      where account=#{account}
//    </select>

    @Override
    public User updateInfo(User user) {
        String account=user.getAccount();
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("account",account);
        try {
            userMapper.update(user, queryWrapper);
            return user;
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            log.error("更新用户信息失败,返回原用户信息");
            return getInfo(account);
        }
    }

    @Override
    public User getInfo(String account) {
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("account",account);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User updatePassword(String account, String password) {
        User user = getInfo(account);
        user.setPassword(password);
        try {
            userMapper.updateById(user);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.error("更新用户信息失败,返回原用户信息");
            return getInfo(account);
        }
    }
}
