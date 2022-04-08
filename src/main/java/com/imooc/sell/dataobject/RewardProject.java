package com.imooc.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "reward_project")
public class RewardProject {
    @Id
    @GeneratedValue
    private Integer id;

    private String projectId;

    private String title;

    private String content;

    private String location;

    private String picture;

    private Integer certificationStatus;

    /**
     * 浏览量
     */
    private Integer pageviews;

    private BigDecimal amount;

    private String contactNumber;

    private Integer gender;

    private Date createTime;

    private Date updateTime;

    /**
     * 前端传入标签数组，把多个标签用英文逗号拼接起来（便于数据库存储）
     */
    private String tags;

    private Integer favoriteNumber;

    /**项目类型
     * 这个字段是前端非要加的，因为前端不会处理分类……（即使使用了json返回 说明了类别）
     * 我觉得很多余.jpg
     */
    private Integer type;

    private String userOpenId;

}
