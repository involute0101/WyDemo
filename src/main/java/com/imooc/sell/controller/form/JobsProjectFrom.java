package com.imooc.sell.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
@Data
@ApiModel(value = "招聘项目-表单")
public class JobsProjectFrom {

    @ApiModelProperty(value = "用户id",required = true)
    @NotEmpty(message = "账号必填")
    private String openid;

    @ApiModelProperty(value = "标题",required = true)
    @NotEmpty(message =  "标题不能为空")
    private String title;

    @ApiModelProperty(value = "内容",required = false)
    private String content;

    @NotEmpty(message = "地点不能为空")
    @ApiModelProperty(value = "地点",required = true)
    private String location;

    @ApiModelProperty(value = "图片",required = false)
    private String picture;

    @ApiModelProperty(value = "数量",required = true)
    private BigDecimal amount;

    @ApiModelProperty(value = "性别",required = true)
    private Integer gender;

    @ApiModelProperty(value = "超链接",required = false)
    private String hyperlink;

    @ApiModelProperty(value = "标签（数组）",required = false)
    private String[] tags;

}
