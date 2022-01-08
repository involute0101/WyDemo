package com.imooc.sell.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

@Data
@ApiModel(value = "闲置项目-表单")
public class IdleProjectFrom {

    @NotEmpty(message = "账号必填")
    @ApiModelProperty(value = "用户id",required = true)
    private String openid;

    @NotEmpty(message =  "标题不能为空")
    @ApiModelProperty(value = "标题",required = true)
    private String title;

    @ApiModelProperty(value = "内容",required = false)
    private String content;

    @NotEmpty(message = "地点不能为空")
    @ApiModelProperty(value = "地点",required = true)
    private String location;

    @ApiModelProperty(value = "图片",required = false)
    private String picture;

    @ApiModelProperty(value = "数量",required = true)
    private BigDecimal  amount;

    @NotEmpty(message = "联系方式不能为空")
    @ApiModelProperty(value = "联系方式",required = true)
    private String contactNumber;

    @ApiModelProperty(value = "性别",required = true)
    private Integer gender;


}
