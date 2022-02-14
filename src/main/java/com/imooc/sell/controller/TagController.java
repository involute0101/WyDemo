package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.controller.form.TagForm;
import com.imooc.sell.dataobject.Tag;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.impl.TagService;
import com.imooc.sell.utils.ResultVOUtil;
import io.swagger.annotations.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/tag")
@Slf4j
@Api(tags = "标签-访问接口")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    JdbcTemplate jdbcTemplate;

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

    @ApiOperation(value = "根据关键字查找标签", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数",required=true),
            @ApiImplicitParam(name = "size",value = "页大小",required=true),
            @ApiImplicitParam(name = "tagKeyword",value = "标签（的关键字）",required=true),
    })
    @PostMapping("/tagLike")
    public ResultVO findByTagLike(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                                  @RequestParam(value = "tagKeyword", defaultValue = "") String tagKeyword) {
        if (page <= 0) return ResultVOUtil.error(403, "请求页不合规范！");
        PageRequest pageRequest = new PageRequest(page - 1, size);
        List<Tag> list = tagService.findByTagContentLike(tagKeyword, pageRequest);
        return ResultVOUtil.success(list);
    }

//    @SneakyThrows
//    @PostMapping("/test")
//    public void test() throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("C:/Users/86178/Desktop/tags.txt")));
//        String line = null;
//        while((line=bufferedReader.readLine())!=null){
//            jdbcTemplate.execute("insert into tag(tag_content,user_open_id) VALUES ('"+line+ "','admin')");
//        }
//    }
}
