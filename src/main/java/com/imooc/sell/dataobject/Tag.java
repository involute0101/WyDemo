package com.imooc.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue
    private Integer id;

    private String tagContent;

    private String userOpenId;
}
