package com.imooc.sell.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ApiModel(value = "题目答案-表单")
public class QuestionAnswerForm {

    @ApiModelProperty(value = "答案选项（数组）",required = true)
    @NotEmpty(message = "答案必填")
    private Integer[] answer;

}
