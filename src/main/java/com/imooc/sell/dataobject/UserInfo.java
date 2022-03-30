package com.imooc.sell.dataobject;

/*
   Created by qi
   2020/5/22
 */

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue
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
    真实姓名
     */
    private String userRealName;

    /*
    用户密码
     */
    private  String userPassword;

    //创建时间
    private Date createTime;

    //更新时间
    private  Date updateTime;

    //用户专业
    private String userMajor;

    //用户学院
    private String userCollege;

    //用户入学年份
    private String enrollmentYear;

    //用户年级
    private String userDegree;

    //用户性别
    private String userSex;

    //用户所在年级
    private String userGrade;

    //用户所在大学
    private String userUniversity;

    //认证状态
    private Integer certification;

    //信用等级,一共五级
    private Integer creditLevel;

    //QQ
    private String qqNumber;

    //微信
    private String weChat;

    //电话
    private String telephone;

    //头像
    private String headPortrait;

    // 学生证/学生卡照片
    private String studentCardPhotos;

    //用户加入的圈子，以“，”分割
    private String discussionCircle;
}
