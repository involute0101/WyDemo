package com.imooc.sell.dataobject;

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
@Table(name = "user_follow")
public class UserFollow {

    @Id
    @GeneratedValue
    private Integer id;

    //用户openId
    private String userOpenId;

    //这个用户关注的目标用户ID
    private String goalFollower;

    //关注时间
    private Date followTime;
}
