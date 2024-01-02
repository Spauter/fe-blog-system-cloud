package com.bloducspauter.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName
@NoArgsConstructor
public class Reply {
    @TableId(type = IdType.AUTO)
    private Integer rid;
    private Integer id;
    private String account;
    private String content;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date rdate;
}
