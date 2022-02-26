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
@Table(name = "leave_message")
public class LeaveMessage {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer userId;

    private String projectId;

    private String content;

    private Date publishTime;

    private int likeNumber;

    //这个id存在表示这条留言是对某一条留言的回复
    private Integer lmId;

}
