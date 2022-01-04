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
@Table(name = "jobs_roject")
public class JobsProject {
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

    private String hyperlink;

    private Integer gender;

    private Date createTime;

    private Date updateTime;
}
