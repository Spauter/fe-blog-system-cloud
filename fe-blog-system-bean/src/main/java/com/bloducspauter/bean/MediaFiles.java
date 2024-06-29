package com.bloducspauter.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 媒资信息
 * </p>
 *
 * @author Bloduc Spauter
 */
@Data
@TableName("fe_media")

public class MediaFiles implements Serializable {

    private static final long serialVersionUID = 146651L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;



    /**
     * 文件名称
     */
    private String fileName;


    /**
     * 存储目录
     */
    private String bucket;

    /**
     * 存储路径
     */
    private String filePath;


    /**
     * 媒资文件访问地址
     */
    private String url;

    /**
     * 上传人
     */
    private String userId;

    /**
     * 上传时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;


    /**
     * 文件大小
     */
    private Long fileSize;

}
