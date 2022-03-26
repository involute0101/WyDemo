package com.imooc.sell.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class UserFollowDTO {
    private Integer id;

    //用户openId
    private String userOpenId;

    //这个用户关注的目标用户openId
    private String goalFollower;

    //关注时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date followTime;
}
