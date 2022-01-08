package com.imooc.sell.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ApiModel(value = "用户信息-表单")
public class UserInfoFrom {

    @ApiModelProperty(value = "买家账号",required = true)
    @NotEmpty(message = "账号必填")
    private String openid;

    @ApiModelProperty(value = "密码",required = true)
    @NotEmpty(message =  "密码不能为空")
    private String password;


}
