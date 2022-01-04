package com.imooc.sell.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.util.Date;


@Data
public class LectureProjectDTO {
    private String userOpenid;

    private String projectId;

    private String title;

    private String content;

    private String location;

    private String picture;

    private Integer certificationStat;

    private Integer pageviews;

    private String presenter;

    private Integer gender;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
}
