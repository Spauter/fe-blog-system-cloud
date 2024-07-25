package com.bloducspauter.media.service;

import com.bloducspauter.bean.MediaFiles;

import java.io.File;

/**
 * 文件管理服务
 * @author Bloduc Spauter
 *
 */
public interface MediaService {
    /**
     * 检查文件是否存在
     * @param id 文件的MD5值
     * @return 文件是否存在
     */
    boolean checkFileExists(String id);

    /**
     * 上传文件，适合小文件上传
     * @return 文件是否上传成功
     */
    MediaFiles uploadFile(File file,String userId,String originFileName);

    /**
     * 获取文件的MD5值
     * @param file 文件
     * @return 文件的MD5值
     */
    String getId(File file);
}
