package com.bloducspauter.bean.media;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName
public class Media implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer mediaId;
    private String image;
    private String music;
}
