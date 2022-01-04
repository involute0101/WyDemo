package com.imooc.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.math.BigDecimal;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "idle_project")
public class IdleProject {
    @Id
    @GeneratedValue
    private Integer id;

    private String projectId;

    private String title;

    private String content;

    private String location;

    private String picture;

    private Integer certificationStatus;

    private Integer pageviews;

    private BigDecimal amount;

    private String contactNumber;

    private Integer gender;

    private Date createTime;

    private Date updateTime;
}
