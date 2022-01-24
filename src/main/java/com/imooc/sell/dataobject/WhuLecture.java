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
@Table(name = "whu_lecture")
public class WhuLecture {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String time;

    private String url;

    private String college;
}
