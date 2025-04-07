package com.bloducspauter.bean.media;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 媒体文件实体类<p>
 * 即将由{@link com.bloducspauter.bean.MediaFiles} 替代
 * @author Bloduc Spauter
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName
@Deprecated
public class Media implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer mediaId;
    private String image;
    private String music;
}
