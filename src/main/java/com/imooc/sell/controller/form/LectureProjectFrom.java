package com.imooc.sell.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

@Data
public class LectureProjectFrom {
    @NotEmpty(message = "账号必填")
    private String openid;

    @NotEmpty(message = "主讲人不能为空")
    private  String presenter;

    @NotEmpty(message =  "标题不能为空")
    private String title;

    private String content;

    @NotEmpty(message = "地点不能为空")
    private String location;

    private String picture;

    private Integer gender;


}
