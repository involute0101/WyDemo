package com.imooc.sell.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class StudyProjectDTO {
    private String userOpenid;

    private String projectId;

    private String title;

    private String content;

    private String location;

    private String[] picture;
    //认证状态
    private Integer certificationStat;
    //浏览量
    private Integer pageviews;

    private BigDecimal amount;

    private String hyperlink;

    private Integer gender;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private String tags;
}
