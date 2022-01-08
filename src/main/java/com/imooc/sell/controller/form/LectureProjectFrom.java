package com.imooc.sell.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

@Data
@ApiModel(value = "讲座项目-表单")
public class LectureProjectFrom {

    @ApiModelProperty(value = "用户id",required = true)
    @NotEmpty(message = "账号必填")
    private String openid;

    @ApiModelProperty(value = "主讲人",required = true)
    @NotEmpty(message = "主讲人不能为空")
    private  String presenter;

    @ApiModelProperty(value = "标题",required = true)
    @NotEmpty(message =  "标题不能为空")
    private String title;

    @ApiModelProperty(value = "内容",required = false)
    private String content;

    @ApiModelProperty(value = "地点",required = true)
    @NotEmpty(message = "地点不能为空")
    private String location;

    @ApiModelProperty(value = "图片",required = false)
    private String picture;

    @ApiModelProperty(value = "性别",required = true)
    private Integer gender;


}
