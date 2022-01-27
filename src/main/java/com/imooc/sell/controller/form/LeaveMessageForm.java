package com.imooc.sell.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

@Data
@ApiModel(value = "留言-表单")
public class LeaveMessageForm {

    @ApiModelProperty(value = "留言id",hidden = true)
    private Integer id;

    @NotEmpty(message = "账号必填")
    @ApiModelProperty(value = "用户id()",required = true)
    private String userId;

    @NotEmpty(message = "项目id不能为空")
    @ApiModelProperty(value = "项目id",required = true)
    private String projectId;

    @NotEmpty(message = "内容不能为空")
    @ApiModelProperty(value = "内容",required = true)
    private String content;

    @ApiModelProperty(value = "发布时间",hidden = true)
    private Date publishTime;

    @ApiModelProperty(value = "点赞数",hidden = true)
    private int likeNumber;
}
