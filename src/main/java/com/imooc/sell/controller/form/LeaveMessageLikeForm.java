package com.imooc.sell.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ApiModel(value = "点赞-表单")
public class LeaveMessageLikeForm {

    @ApiModelProperty(value = "留言Id",required = true)
    private int leaveMessageId;

    @NotEmpty(message = "openId必填")
    @ApiModelProperty(value = "用户userOpenId",required = true)
    private String userOpenId;
}
