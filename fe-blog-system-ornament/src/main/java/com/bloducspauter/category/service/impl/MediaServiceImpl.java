package com.bloducspauter.category.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.bloducspauter.bean.Media;
import com.bloducspauter.category.mapper.MediaMapper;
import com.bloducspauter.category.service.MediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.bloducspauter.bean.utils.DefaultValue.*;

@Service
@Slf4j
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaMapper mediaMapper;

    /**
     * 增加媒体
     *
     * @param media,type
     * @return
     */
    @Override
    public boolean add(Media media) {
        return mediaMapper.insert(media) != 0;
    }


    /**
     * 删除媒体
     *
     * @param medias
     * @param type
     * @param filePath
     * @return
     */
    @Override
    public boolean delete(List<String> medias, String type, String filePath) {
        //判断media类型,根据类型进行相应操作
        File file;
        int result=0;
        for (String media : medias) {
            file = new File(filePath +"\\"+ media);
            if(DEFAULT_MEDIA_IMAGE.contains(media)){
                continue;
            }
            QueryWrapper<Media> queryWrapper=new QueryWrapper<>();
            if(type.equals(MEDIA_IMAGE_TYPE)){
                queryWrapper.eq(MEDIA_IMAGE_TYPE,media);
            }else {
                queryWrapper.eq(MEDIA_AUDIO_TYPE,media);
            }
            mediaMapper.delete(queryWrapper);
            result+=file.delete()?1:0;
        }
        return result == medias.size();
    }


    /**
     * 查询所有媒体
     *
     * @param type
     * @return
     */
    @Override
    public List<Media> selectALL(String type) {
        QueryWrapper<Media> queryWrapper = new QueryWrapper<>();
        if (type.equals(MEDIA_IMAGE_TYPE)) {
            queryWrapper.isNotNull(MEDIA_IMAGE_TYPE);
            queryWrapper.select(MEDIA_IMAGE_TYPE);
        } else {
            queryWrapper.isNotNull(MEDIA_AUDIO_TYPE);
            queryWrapper.select(MEDIA_AUDIO_TYPE);
        }
        return mediaMapper.selectList(queryWrapper);
    }

    /**
     * 查找指定名称媒体文件
     *
     * @param media
     * @param type
     * @return
     */
    @Override
    public Media findMedia(String media, String type) {
        QueryWrapper<Media> queryWrapper = new QueryWrapper<>();
        if (type.equalsIgnoreCase(MEDIA_IMAGE_TYPE)) {
            queryWrapper.eq(MEDIA_IMAGE_TYPE,media);
        } else {
            queryWrapper.eq(MEDIA_AUDIO_TYPE,media);
        }
        return mediaMapper.selectOne(queryWrapper);
    }



    /**
     * 返回需要删除的媒体文件名称数组
     *
     * @param inputStream
     * @return
     */
    @Override
    public ArrayList<String> getParameterArrays(InputStream inputStream) {
        StringBuffer stringBuffer = null;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            stringBuffer = new StringBuffer();
            String s = null;
            while ((s = bufferedReader.readLine()) != null) {
                stringBuffer.append(s);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        assert stringBuffer != null;
        JSONArray jsonArray = JSONArray.parseArray(stringBuffer.toString());
        ArrayList<String> arrayList = new ArrayList<>();
        for (Object o : jsonArray) {
            arrayList.add(((JSONObject) o).getString("value"));
        }
        return arrayList;
    }
}
