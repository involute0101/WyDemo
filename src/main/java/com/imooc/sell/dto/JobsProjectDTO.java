package com.imooc.sell.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

@Data
public class JobsProjectDTO {
    private Integer id;

    private String projectId;

    private String userOpenId;

    private String title;

    private String content;

    private String location;

    private String[] picture;

    private Integer certificationStat;

    private Integer pageviews;

    private BigDecimal amount;

    private String hyperlink;

    private Integer gender;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private String tags;

    private Integer favoriteNumber;

    private Integer type;

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id +
                "\", \"projectId\":\"" + projectId + '\"' +
                ", \"userOpenId\":\"" + userOpenId + '\"' +
                ", \"title\":\"" + title + '\"' +
                ", \"content\":\"" + content + '\"' +
                ", \"location\":\"" + location + '\"' +
                ", \"picture\":\"" + Arrays.toString(picture) +
                "\", \"certificationStat\":\"" + certificationStat +
                "\", \"pageviews\":\"" + pageviews +
                "\", \"amount\":\"" + amount +
                "\", \"hyperlink\":\"" + hyperlink + '\"' +
                ", \"gender\":\"" + gender +
                "\", \"createTime\":\"" + createTime.getTime()/1000 +
                "\", \"updateTime\":\"" + updateTime.getTime()/1000 +
                "\", \"tags\":\"" + tags + '\"' +
                ", \"favoriteNumber\":\"" + favoriteNumber +
                "\", \"type\":\"" + type +
                "\"}";
    }
}
