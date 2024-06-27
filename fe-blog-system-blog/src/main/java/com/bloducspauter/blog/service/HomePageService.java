package com.bloducspauter.blog.service;



import com.bloducspauter.bean.Homepage;

import java.util.List;

public interface HomePageService {
    /**
     * 实现对Homepage的数据更新操作
     * @param homepage
     * @return
     */
    Integer UpdateHomePage(Homepage homepage);
    /**
     * 实现对Homepage的查找工作
     * @param Homepageid
     * @return
     */
    List<Homepage> FindHomePage(int Homepageid);
}
