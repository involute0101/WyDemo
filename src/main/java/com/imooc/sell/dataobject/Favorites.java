package com.imooc.sell.dataobject;

import com.imooc.sell.dataobject.map.Favorites_Map;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@IdClass(Favorites_Map.class)
@Table(name = "favorites")
public class Favorites implements Serializable {
    @Id
    private String projectId;

    @Id
    private Integer userId;

    private Date createTime;

    private Date updateTime;
}
