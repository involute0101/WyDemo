package com.imooc.sell.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ApiModel(value = "失物招领项目-表单")
/**
 * 这个form是给前端调用的，避免创建多个标签的时候需要调用多次接口，所以form直接使用数组，后续转换为多个tag对象
 */
public class TagForm {

    @ApiModelProperty(value = "标签id",hidden = true)
    private Integer id;

    @ApiModelProperty(value = "内容(数组)",required = true)
    @NotEmpty(message = "内容不能为空")
    private String[] tagContent;

    @ApiModelProperty(value = "用户openId",required = true)
    @NotEmpty(message = "用户openId不能为空")
    private String userOpenId;
}
