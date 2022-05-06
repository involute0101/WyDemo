package com.imooc.sell.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ApiModel(value = "日常项目-表单")
public class DailyProjectForm {

    @ApiModelProperty(value = "用户id",required = true)
    @NotEmpty(message = "账号必填")
    private String openid;

    @ApiModelProperty(value = "标题",required = true)
    @NotEmpty(message = "标题不能为空")
    private  String title;

    @ApiModelProperty(value = "联系方式",required = true)
    @NotEmpty(message =  "联系方式不能为空")
    private String contactNumber;

    @ApiModelProperty(value = "内容",required = true)
    @NotEmpty(message = "内容不能为空")
    private String content;

    @ApiModelProperty(value = "地点",required = false)
    private String location;

    @ApiModelProperty(value = "图片",required = false)
    private String[] picture;

    @ApiModelProperty(value = "性别",required = true)
    private Integer gender;

    @ApiModelProperty(value = "标签（数组）",required = false)
    private String[] tags;
}
