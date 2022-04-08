package com.imooc.sell.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ApiModel(value = "学生认证-表单")
public class StudentCertificationForm {

    @ApiModelProperty(value = "用户openId",required = true)
    @NotEmpty(message = "用户openId必填")
    private String userOpenId;

    @ApiModelProperty(value = "姓名",required = true)
    @NotEmpty(message = "姓名必填")
    private String userRealName;

    @ApiModelProperty(value = "性别",required = true)
    @NotEmpty(message = "性别必填")
    private String userSex;

    @ApiModelProperty(value = "学校",required = true)
    @NotEmpty(message = "学校必填")
    private String userUniversity;

    @ApiModelProperty(value = "学院",required = true)
    @NotEmpty(message = "学院必填")
    private String userCollege;

    @ApiModelProperty(value = "专业",required = true)
    @NotEmpty(message = "专业必填")
    private String userMajor;

    @ApiModelProperty(value = "年级",required = true)
    @NotEmpty(message = "年级必填")
    private String userDegree;

    @ApiModelProperty(value = "学号",required = true)
    @NotEmpty(message = "学号必填")
    private String studentId;

    @ApiModelProperty(value = "学生证/学生卡 照片",required = true)
    @NotEmpty(message = "学生证/学生卡 照片 必填")
    private String[] studentsCard;
}
