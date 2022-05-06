package com.imooc.sell.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class MakeFriendProjectDTO {
    private Integer id;

    private String projectId;

    private String title;

    private String content;

    private String location;

    private String[] picture;

    private Integer certificationStatus;

    /**
     * 浏览量
     */
    private Integer pageviews;

    private String contactNumber;

    private Integer gender;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private String tags;

    private Integer favoriteNumber;

    private Integer type;

    private String userOpenId;

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id +
                "\", \"projectId\":\"" + projectId + '\"' +
                ", \"title\":\"" + title + '\"' +
                ", \"content\":\"" + content + '\"' +
                ", \"location\":\"" + location + '\"' +
                ", \"picture\":\"" + picture + '\"' +
                ", \"certificationStatus\":\"" + certificationStatus +
                "\", \"pageviews\":\"" + pageviews +
                "\", \"contactNumber\":\"" + contactNumber + '\"' +
                ", \"gender\":\"" + gender +
                "\", \"createTime\":\"" + createTime.getTime()/1000 +
                "\", \"updateTime\":\"" + updateTime.getTime()/1000 +
                "\", \"tags\":\"" + tags + '\"' +
                ", \"favoriteNumber\":\"" + favoriteNumber +
                "\", \"type\":\"" + type +
                "\", \"userOpenId\":\"" + userOpenId + '\"' +
                "}";
    }
}
