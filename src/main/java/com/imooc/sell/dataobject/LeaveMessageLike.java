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
@Table(name = "leave_message_like")
public class LeaveMessageLike {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer leaveMessageId;

    private String userOpenId;
}
