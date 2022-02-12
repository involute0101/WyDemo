package com.imooc.sell.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ApiModel(value = "用户更新信息-表单")
public class UserInfoUpdateForm {

    @ApiModelProperty(value = "用户openId",required = true)
    @NotEmpty(message = "账号必填")
    private String openid;

    @ApiModelProperty(value = "昵称",required = false)
    private String userName;

    @ApiModelProperty(value = "头像",required = false)
    private String headPortrait;

    @ApiModelProperty(value = "QQ",required = false)
    private String QQ;

    @ApiModelProperty(value = "微信号",required = false)
    private String weChat;

    @ApiModelProperty(value = "常用联系电话",required = false)
    private String telephone;
}
