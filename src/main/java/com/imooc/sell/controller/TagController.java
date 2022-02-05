package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.controller.form.TagForm;
import com.imooc.sell.dataobject.Tag;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.impl.TagService;
import com.imooc.sell.utils.ResultVOUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/tag")
@Slf4j
@Api(tags = "标签-访问接口")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("/")
    @ApiOperation(value = "查找标签", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "userOpenId",value = "用户openId",required=true)
    public ResultVO findByUserOpenId(@RequestParam(name = "userOpenId")String userOpenId){
        List<Tag> result = tagService.findTagByUserOpenId(userOpenId);
        return ResultVOUtil.success(result);
    }

    @PostMapping("/create")
    @ApiOperation(value = "创建 标签（数组）", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    public ResultVO createTag(@Valid TagForm tagForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("【创建标签】参数不正确, tagForm={}", tagForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        List<Tag> result = tagService.createTag(tagForm);
        return ResultVOUtil.success(result);
    }
}
