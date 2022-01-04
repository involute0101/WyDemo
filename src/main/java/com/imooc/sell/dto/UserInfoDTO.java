package com.imooc.sell.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfoDTO {
    private  Integer userId;
    /*
    用户openid
     */
    private String userOpenid;

    /*
    用户昵称
    */
    private  String userName;

    /*
    用户密码
     */
    private  String userPassword;

    //用户专业
    private String userMajor;

    //用户学院
    private String userCollege;

    //用户入学年份
    private String enrollmentYear;

    //用户学历
    private String userDegree;

    //用户性别
    private String userSex;

    //用户所在年级
    private String userGrade;

    //用户所在大学
    private String userUniversity;

    //备注
    private String anotherInfo;

    //创建时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    //更新时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private  Date updateTime;

    //认证状态
    private Integer certification;

    //信用等级
    private Integer creditLevel;
}
