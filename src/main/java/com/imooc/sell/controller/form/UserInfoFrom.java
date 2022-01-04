package com.imooc.sell.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class UserInfoFrom {
    /*
     *买家账号
     */
    @NotEmpty(message = "账号必填")
    private String openid;


    /*
     * 密码
     */
    @NotEmpty(message =  "密码不能为空")
    private String password;


}
