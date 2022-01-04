package com.imooc.sell.VO;

import lombok.Data;

@Data
public class CaptchaVO {
    /*
    用户手机号
    */
    private String phoneNumbers;

    /*
    验证码
    */
    private  String captcha;
}
