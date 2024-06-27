package com.bloducspauter.category.service;


import com.bloducspauter.bean.Media;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public interface MediaService {


    /**
     * 增加媒体
     *
     * @param media,type
     * @return
     */
    boolean add(Media media);

    /**
     * 删除媒体
     *
     * @param medias
     * @param type
     * @param filePath
     * @return
     */
    boolean delete(List<String> medias,String type,String filePath);

    /**
     * 查询所有媒体
     *
     * @param type
     * @return
     */
    List<Media> selectALL(String type);

    /**
     * 查找指定名称媒体文件
     * @param media
     * @param type
     * @return
     */
    Media findMedia(String media,String type);


    /**
     * 返回需要删除的媒体文件名称数组
     * @param inputStream
     * @return
     */
    ArrayList<String> getParameterArrays(InputStream inputStream);
}
