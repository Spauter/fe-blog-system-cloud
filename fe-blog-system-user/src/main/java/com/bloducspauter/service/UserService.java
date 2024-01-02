package com.bloducspauter.service;


import com.bloducspauter.bean.User;

public interface UserService {



    User Login(String account);

    User register(String account,String password,String email);

    /**
     * 根据传入的user对象修改数据库内容并返回修改后的user对象
     *
     * @time
     * @version 1.0
     * @param user
     * @return User对象
     */
    User updateInfo(User user);

    /**
     * 根据传入的account获取据库内容并返回user对象
     *
     * @author
     * @time
     * @version
     * @param account
     * @return User对象
     */
    User getInfo(String account);

    /**
     * @param account 用户名
     * @param password 修改后的密码
     * @return
     */
    User updatePassword(String account,String password);

    /**
     * 根据用户名和密码匹配数据库
     * 如果用户名和密码都正确，则成功，返回true，否则返回false
     * @param account
     * @param password
     * @version 1.0
     */
    boolean judgePassword(String account,String password);


}
