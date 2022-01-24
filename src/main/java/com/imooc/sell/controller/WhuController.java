package com.imooc.sell.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.service.impl.WhuService;
import com.imooc.sell.utils.ResultVOUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/whu")
@Slf4j
@Api(tags = "武大官方信息接口")
public class WhuController {

    @Autowired
    private WhuService whuService;

    @ApiOperation(value = "获取Whu的官网通知", notes = "")
    @ApiImplicitParam(name = "page",value = "页数",required=true)
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @PostMapping("/notice")
    public JSONArray WhuNotice(@RequestParam("page") String page){
        try{
            int pageNum = Integer.parseInt(page);
            if(pageNum<=0 || pageNum >101){
                JSONArray result = new JSONArray();
                result.add(new JSONObject().put("title","页数不合规范！"));
                return result;
            }
            return whuService.getWhuNotice(pageNum);
        }catch (IOException e){
            JSONArray result = new JSONArray();
            result.add(new JSONObject().put("title","出错啦！"));
            return result;
        }

    }

    @ApiOperation(value = "获取Whu的讲座列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数",required=true),
            @ApiImplicitParam(name = "size",value = "页大小",required=true),
    })
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @PostMapping("/lectureList")
    public ResultVO getWhuLectureSum(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "5") Integer size){
        if (page<=0)return ResultVOUtil.error(403,"请求页不合规范！");
        PageRequest pageRequest = new PageRequest(page-1,size);
        JSONArray lectureList = whuService.getLectureSum(pageRequest);
        return ResultVOUtil.success(lectureList);
    }

    @ApiOperation(value = "获取Whu的讲座贴详情", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "time",value = "讲座贴 发布时间",required=true)
    @PostMapping("/lectureInfo")
    public JSONArray getLectureInfo(@RequestParam(value = "time", defaultValue = "1") String time){
        return whuService.getLectureInfo(time);
    }
}
