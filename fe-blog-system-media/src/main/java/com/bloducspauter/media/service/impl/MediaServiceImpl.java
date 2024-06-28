package com.bloducspauter.media.service.impl;


import com.bloducspauter.bean.MediaFiles;
import com.bloducspauter.media.mapper.MediaFilesMapper;
import com.bloducspauter.media.service.MediaService;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Bloduc Spauter
 *
 */
@Service
@Slf4j
public class MediaServiceImpl implements MediaService {
    @Resource
    private MediaFilesMapper mapper;

    @Resource
    private MinioClient minioClient;
    //存储普通文件
    @Value("${minio.bucket.files}")
    private String bucketFiles;

    //存储视频
    @Value("${minio.bucket.video-files}")
    private String bucketVideo;

    @Value("${minio.bucket.emojis}")
    private String bucketEmojis;

    /**
     * 将文件信息添加到文件表
     *
     * @param fileMd5             文件md5值
     * @param bucket              桶
     * @param objectName          对象名称
     */
    @Transactional

    public MediaFiles addMediaFilesToDb(String fileMd5, String bucket, String objectName,long size) {
        //将文件信息保存到数据库
        MediaFiles mediaFiles = mapper.selectById(fileMd5);
        if (mediaFiles == null) {
            mediaFiles = new MediaFiles();
            //文件id
            mediaFiles.setId(fileMd5);
            //桶
            mediaFiles.setBucket(bucket);
            //file_path
            mediaFiles.setFilePath(objectName);
            //file_id
            mediaFiles.setFileId(fileMd5);
            //url
            mediaFiles.setUrl("/" + bucket + "/" + objectName);
            //上传时间
            mediaFiles.setCreateDate(LocalDateTime.now());
            mediaFiles.setFileSize(size);
            //插入数据库
            int insert = mapper.insert(mediaFiles);
            if (insert <= 0) {
                log.debug("向数据库保存文件失败,bucket:{},objectName:{}", bucket, objectName);
                return null;
            }
            return mediaFiles;

        }
        return mediaFiles;

    }

    /**
     * 获取文件的类型
     * @param extension 文件扩展名
     */
    private String getMimeType(String extension) {
        if (extension == null) {
            extension = "";
        }
        //根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
        //通用mimeType，字节流
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if (extensionMatch != null) {
            mimeType = extensionMatch.getMimeType();
        }
        return mimeType;
    }

    /**
     * 得到合并后的文件的地址
     * @param fileMd5 文件id即md5值
     * @param fileExt 文件扩展名
     */
    private String getFilePathByMd5(String fileMd5,String fileExt){
        return   fileMd5.charAt(0) + "/" + fileMd5.charAt(1) + "/" + fileMd5 + "/" +fileMd5 +fileExt;
    }

    //得到文件的目录
    private String getFileFolderPath(String fileMd5) {
        return fileMd5.charAt(0) + "/" + fileMd5.charAt(1) + "/" + fileMd5 + "/";
    }

    /**
     * 将文件上传到minio
     *
     * @param localFilePath 文件本地路径
     * @param mimeType      媒体类型
     * @param bucket        桶
     * @param objectName    对象名
     * @return boolean
     */
    public boolean addMediaFilesToMinIO(String localFilePath, String mimeType, String bucket, String objectName) {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    //桶
                    .bucket(bucket)
                    //指定本地文件路径
                    .filename(localFilePath)
                    //对象名 放在子目录下
                    .object(objectName)
                    //设置媒体文件类型
                    .contentType(mimeType)
                    .build();
            //上传文件
            minioClient.uploadObject(uploadObjectArgs);
            log.debug("上传文件到minio成功,bucket:{},objectName:{}", bucket, objectName);
            return true;
        } catch (Exception e) {
            log.error("上传文件出错,bucket:{},objectName:{},错误信息:{}", bucket, objectName, e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkFileExists(String id) {
        MediaFiles mediaFiles = mapper.selectById(id);
        if (mediaFiles != null) {
            String bucket = mediaFiles.getBucket();
            String filePath = mediaFiles.getFilePath();
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(filePath)
                    .build();
            //查询远程服务获取的一个对象
            try {
                FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
                if (inputStream != null) {
                    //文件已经存在
                  return true;
                }
            } catch (Exception e) {
                log.error("Exception thread in main:{}:{}",e.getClass().getSimpleName(),e.getMessage());
            }
        }
        return false;
    }

    private String getDefaultFolderPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date()).replace("-", "/") + "/";
    }

    @Override
    public MediaFiles uploadFile(File file) {
        String id=getId(file);
        String fileName=file.getName();
        String defaultFolderPath = getDefaultFolderPath();
        String filePath=getFileFolderPath(id);
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String minType=getMimeType(extension);
        String objectName = defaultFolderPath + id + extension;
        long size=file.length();
        boolean result= addMediaFilesToMinIO(filePath,minType,bucketEmojis,filePath);
        if(!result){
            return null;
        }
        return addMediaFilesToDb(id,bucketEmojis,objectName,size);
    }

    @Override
    public String getId(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return DigestUtils.md5Hex(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
