package com.imooc.sell.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

@Data
public class StudyProjectFrom {
    @NotEmpty(message = "账号必填")
    private String openid;

    @NotEmpty(message = "标题不能为空")
    private  String title;

    @NotEmpty(message = "内容不能为空")
    private String content;

    @NotEmpty(message = "地点不能为空")
    private String location;

    @NotEmpty(message = "链接不能为空")
    private String hyperlink;

    private String picture;

    private Integer gender;

    private BigDecimal amount;
}
