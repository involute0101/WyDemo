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
@Table(name = "project_master")
public class ProjectMaster {
    @Id
    private  String projectId;

    private Integer userId;

    private Integer projectType;

    private Date createTime;

    private Date updateTime;

}
