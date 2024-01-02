package com.bloducspauter.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName
public class ltpData implements Serializable {
    @TableId(type = IdType.AUTO)

    //结果码(具体见SDK&API错误码查询 )
    private String code;
    //关键词提取结果
    private List<Map<String,List<Map<String,String>>>> data;
    //错误描述，会话成功为success
    private String desc;
    //会话ID，用来唯一标识本次会话，如会话报错无法解决，可以提供 sid 给讯飞技术人员分析解决。
    private String sid;
}