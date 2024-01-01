package com.bloducspauter.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String uploadToNginx(MultipartFile multipartFile);

    String uploadToWindows(MultipartFile multipartFile,String destPath,boolean repeatable);
}
