package com.bloducspauter.media.service;

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

    /**
     * 上传文件，适合小文件上传
     *
     * @return 文件是否上传成功
     */
    MediaFiles uploadFile(File file, String userId, String originFileName);

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
    default List<MediaFiles> selectALL() {
        return selectList(null);
    }

    /**
     *  删除文件
     * @param deleteMedias 文件的MD5值集合
     * @return 文件是否删除成功
     */
    boolean delete(List<String> deleteMedias);

    /**
     * 查询文件
     * @param medias 文件的MD5值集合
     * @return 文件集合
     */
    List<MediaFiles> selectList(List<String> medias);

}
