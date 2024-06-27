package com.bloducspauter.bean.media;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class MediaRelation implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer blogId;
    private Integer mediaId;
}
