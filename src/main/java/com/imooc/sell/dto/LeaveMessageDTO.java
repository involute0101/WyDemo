package com.imooc.sell.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.util.Date;


@Data
public class LeaveMessageDTO {
    private Integer id;

    private Integer userId;

    private String userOpenid;

    private String projectId;

    private String content;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date publishTime;

    private int likeNumber;

    //这个id存在表示这条留言是对某一条留言的回复
    private Integer lmId;

}
