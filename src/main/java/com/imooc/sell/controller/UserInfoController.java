package com.imooc.sell.controller;

import com.alibaba.fastjson.JSONObject;
import com.imooc.sell.VO.CaptchaVO;
import com.imooc.sell.VO.ResultVO;

import com.imooc.sell.controller.form.UserInfoFrom;
import com.imooc.sell.converter.UserInfoFrom2UserInfoDTOConverter;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.UserInfoService;
import com.imooc.sell.utils.ResultVOUtil;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/user/account")
@Slf4j
@Api(tags = "用户信息-访问接口")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "注册账号", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @PostMapping("/register")
    public ResultVO register(@Valid UserInfoFrom userInfoFrom, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            log.error("[注册账号] 参数不正确， userInfoForm={}", userInfoFrom);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        UserInfoDTO userInfoDTO = UserInfoFrom2UserInfoDTOConverter.convert(userInfoFrom);

        UserInfoDTO registerResult = userInfoService.createUserInfoOne(userInfoDTO);

        return ResultVOUtil.success(registerResult);


    }

    @ApiOperation(value = "登录", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password",value = "密码",required=true),
            @ApiImplicitParam(name = "openid",value = "用户Id",required=true),
    })
    @PostMapping("/login")
    public ResultVO login(@RequestParam("openid") String openid,
                          @RequestParam("password") String password){
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByBuyerOpenidAndPassword(openid,password);
        return ResultVOUtil.success(userInfoDTO);

    }

    @ApiOperation(value = "发送验证码", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "telephone",value = "手机号",required=true)
    @PostMapping("/captcha")
    public ResultVO captcha(@RequestParam("telephone") String telephone){
        CaptchaVO result =  userInfoService.sendSms(telephone);
        return  ResultVOUtil.success(result);
    }

    @ApiOperation(value = "检查账号是否已注册", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "openid",value = "用户id",required=true)
    @PostMapping("/check")
    public ResultVO check(@RequestParam("openid") String openid){
        UserInfoDTO userInfoDTO =  userInfoService.checkUserInfoByBuyerOpenid(openid);
        return  ResultVOUtil.success(userInfoDTO);
    }

    @ApiOperation(value = "查询用户认证信息", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "openid",value = "用户id",required=true)
    @PostMapping("/inquire/certification")
    public ResultVO getCertification(@RequestParam("openid") String openid){
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(openid);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("certificationStatu",userInfoDTO.getCertification());
        return ResultVOUtil.success(jsonObject);
    }

    @ApiOperation(value = "查询用户信用等级", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "openid",value = "用户id",required=true)
    @PostMapping("inquire/userCreditLevel")
    public ResultVO getCreditLevel(@RequestParam("openid") String openid){
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(openid);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("creditLevel",userInfoDTO.getCreditLevel());
        return ResultVOUtil.success(jsonObject);
    }

    @ApiOperation(value = "查询用户个人信息", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "openid",value = "用户id",required=true)
    @PostMapping("inquire/userInfo")
    public ResultVO getUserInfo(@RequestParam("openid") String openid){
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(openid);
        JSONObject user = new JSONObject();
        user.put("userName",userInfoDTO.getUserName());
        user.put("headPortrait","");
        user.put("QQ",userInfoDTO.getQqNumber());
        user.put("WeChat",userInfoDTO.getWeChat());
        user.put("telephone",userInfoDTO.getTelephone());
        return ResultVOUtil.success(user);
    }
}
