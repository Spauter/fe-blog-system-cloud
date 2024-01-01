package com.bloducspauter.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName
@NoArgsConstructor
public class Reply {
    @TableId(type = IdType.AUTO)
    private Integer rid;
    private Integer id;
    private String account;
    private String content;
}
