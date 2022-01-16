package com.imooc.sell.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imooc.sell.service.impl.WhuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @PostMapping("/notice")
    public JSONArray WhuNotice(){
        try{
            return whuService.getWhuNotice();
        }catch (IOException e){
            JSONArray result = new JSONArray();
            result.add(new JSONObject().put("title","出错啦！"));
            return result;
        }

    }
}
