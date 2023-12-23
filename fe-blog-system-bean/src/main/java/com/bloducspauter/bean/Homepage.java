package com.bloducspauter.bean;

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
@TableName(value = "homepage")
public class Homepage implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer homepageid;
    private String title;
    private String description;
    private String welcome;
    private String banner;
    private String announcement;
}
