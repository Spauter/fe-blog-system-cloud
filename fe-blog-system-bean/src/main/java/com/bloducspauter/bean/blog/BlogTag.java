package com.bloducspauter.bean.blog;

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
public class BlogTag implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer blogId;
    private Integer tagId;
}
