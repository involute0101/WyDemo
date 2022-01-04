package com.imooc.sell.dto;

import lombok.Data;


@Data
public class LeaveMessageDTO {
    private Integer id;

    private Integer userId;

    private String userOpenid;

    private String projectId;

    private String content;

}
