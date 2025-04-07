package com.bloducspauter.media.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bloducspauter.bean.MediaFiles;

import java.io.File;
import java.util.List;

/**
 * 文件管理服务
 *
 * @author Bloduc Spauter
 */
public interface MediaService {
    /**
     * 检查文件是否存在
     *
     * @param id 文件的MD5值
     * @return 文件是否存在
     */
    boolean checkFileExists(String id);

    boolean checkFileExists(String fileName,String bucket);

    /**
     * 上传文件，适合小文件上传
     *
     * @return 文件是否上传成功
     */
    MediaFiles uploadFile(File file, String userId, String originFileName,String bucket);

    /**
     * 获取文件的MD5值
     *
     * @param file 文件
     * @return 文件的MD5值
     */
    String getId(File file);

    /**
     * 查询所有文件
     * @return 文件集合
     */
    default List<MediaFiles> selectALL(String bucket) {
        return selectList(null,bucket);
    }

    /**
     *  删除文件
     * @param deleteMedias 文件的MD5值集合
     * @return 文件是否删除成功
     */
    boolean delete(List<String> deleteMedias, String bucket );

    /**
     * 查询文件
     * @param medias 文件的MD5值集合
     * @return 文件集合
     */
    List<MediaFiles> selectList(List<String> medias,String bucket);

    MediaFiles findById(String id) ;

    MediaFiles findIdByName(String fileName);

}