package com.imooc.sell.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ApiModel(value = "收藏项目-表单")
public class FavoritesForm {

    @NotEmpty(message = "项目Id必填")
    @ApiModelProperty(value = "项目Id",required = true)
    private String projectId;

    @NotEmpty(message = "账号必填")
    @ApiModelProperty(value = "用户openId",required = true)
    private String userOpenId;
}
