package com.bloducspauter.utils;



import com.bloducspauter.bean.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsValidUtil {

    // 支持的图片文件后缀名
    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp");

    // 支持的音频文件后缀名
    private static final List<String> AUDIO_EXTENSIONS = Arrays.asList("mp3", "wav", "ogg", "flac");

    public  String checkFileType(String fileName) {
        // 获取文件后缀名
        String[] parts = fileName.split("\\.");
        if (parts.length > 1) {
            String extension = parts[parts.length - 1].toLowerCase();

            // 判断是否为图片文件
            if (IMAGE_EXTENSIONS.contains(extension)) {
                return "Image";
            }

            // 判断是否为音频文件
            if (AUDIO_EXTENSIONS.contains(extension)) {
                return "Audio";
            }

            // 其他类型的文件
            return "Other";
        } else {
            // 没有后缀名的文件
            return "Unknown";
        }
    }


    public boolean isValidEmail(String email) {
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.([a-z]{2,4}|\\d{1,3})(\\.[a-z]{2,4})?$";
        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }


    public  int getUserId(HttpServletRequest request, HttpSession session){
        User user= (User) session.getAttribute("user");
        if(request.getParameter("desk")!=null){
            return -1;
        }
        return user==null?-1:Integer.parseInt(user.getUserId());
    }
}
