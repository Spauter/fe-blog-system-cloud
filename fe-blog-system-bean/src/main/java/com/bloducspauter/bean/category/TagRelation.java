package com.bloducspauter.bean.category;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
@Data
@AllArgsConstructor
@TableName
public class TagRelation implements Serializable {
    @TableId(type=IdType.AUTO)
    private Integer blog_id;
    private Integer tag_id;
    private Integer deleted;
}
