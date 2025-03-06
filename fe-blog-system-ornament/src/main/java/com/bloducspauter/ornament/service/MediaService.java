package com.bloducspauter.ornament.service;


import com.bloducspauter.bean.media.Media;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 这是一个媒体文件的服务接口，用于管理媒体文件<p>
 * 由于要将图片全部放在Minio上，这个服务将会被废弃<P>
 * 所有的媒体文件将在{@link   com.bloducspauter.media.service.MediaService} 中管理
 *   @author Bloduc Spauter
 *
 */
@Deprecated
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
    boolean delete(List<String> medias, String type, String filePath);

    /**
     * 查询所有媒体
     *
     * @param type
     * @return
     */
    List<Media> selectALL(String type);

    /**
     * 查找指定名称媒体文件
     *
     * @param media
     * @param type
     * @return
     */
    Media findMedia(String media, String type);


    /**
     * 返回需要删除的媒体文件名称数组
     *
     * @param inputStream
     * @return
     */
    ArrayList<String> getParameterArrays(InputStream inputStream);
}
