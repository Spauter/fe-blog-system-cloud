package com.bloducspauter.user.service.impl;


import com.bloducspauter.user.service.UploadService;
import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.UUID;

import static com.bloducspauter.bean.utils.DefaultValue.DEFAULT_AVATAR;


@Slf4j
@Service
public class UploadServicesImpl implements UploadService {

    @Autowired
    private FastFileStorageClient storageClient;


    /**
     * 这是上传Nginx的，在Windows平台不可用，需要Linux环境<br>
     * 用Docker也行
     * @param multipartFile
     * @return
     */
    @Override
    public String uploadToNginx(MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            log.info("上传的文件名" + multipartFile.getOriginalFilename());
            log.info("获得的文件后缀名" + FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
            StorePath storePath = storageClient.uploadFile(IOUtils.toByteArray(inputStream),
                    FilenameUtils.getExtension(multipartFile.getOriginalFilename()));

            log.info("文件上传成功，路径信息:" + storePath);
            log.info("groupd:" + storePath.getGroup());
            log.info("path:" + storePath.getPath());
            return  storePath.getFullPath();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            //这是本机上传到Nginx的一个图片
            return DEFAULT_AVATAR.get(3);
        }
    }

    /**
     * 捕获到异常会返回默认图片，其实这两个方法返回的默认图片是同一张
     * @param multipartFile
     * @return
     */
    @Override
    public String uploadToWindows(MultipartFile multipartFile,String destPath,boolean repeatable) {
        String fileName = multipartFile.getOriginalFilename();
        File file;
        try {
            if (repeatable) {
                String suffix = fileName.replaceAll(".+(\\.\\w+)", "$1");
                String prefix = UUID.randomUUID().toString();
//                fileName =PROJECT_WEB_PATH + destPath + "\\" + ;
                fileName="D:/upload/"+destPath+prefix + suffix;
            }else {
                fileName="D:/upload/"+destPath+fileName;
            }
            System.out.println(fileName);
            file = new File(fileName);
            OutputStream outputStream = Files.newOutputStream(file.toPath());
            outputStream.write(multipartFile.getBytes());
            outputStream.close();
            return "/"+file.getName();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return "/"+ DEFAULT_AVATAR.get(2);
        }
    }
}
