package com.imooc.sell.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ApiModel(value = "失物招领项目-表单")
public class UserFollowForm {

    @ApiModelProperty(value = "用户openId",required = true)
    @NotEmpty(message = "用户openId不能为空")
    private String userOpenId;

    @ApiModelProperty(value = "目标关注用户openId",required = true)
    @NotEmpty(message = "目标关注用户openId不能为空")
    private String goalFollower;
}
