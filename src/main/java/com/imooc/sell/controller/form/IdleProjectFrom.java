package com.imooc.sell.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

@Data
public class IdleProjectFrom {
    @NotEmpty(message = "账号必填")
    private String openid;

    @NotEmpty(message =  "标题不能为空")
    private String title;

    private String content;

    @NotEmpty(message = "地点不能为空")
    private String location;

    private String picture;

    private BigDecimal  amount;

    @NotEmpty(message = "联系方式不能为空")
    private String contactNumber;

    private Integer gender;


}
