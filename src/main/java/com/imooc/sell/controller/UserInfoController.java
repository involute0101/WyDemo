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

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/user/account")
@Slf4j

public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    //注册账号
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

    @PostMapping("/login")
    public ResultVO login(@RequestParam("openid") String openid,
                          @RequestParam("password") String password){
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByBuyerOpenidAndPassword(openid,password);
        return ResultVOUtil.success(userInfoDTO);

    }

    @PostMapping("/captcha")
    public ResultVO captcha(@RequestParam("telephone") String telephone){
        CaptchaVO result =  userInfoService.sendSms(telephone);
        return  ResultVOUtil.success(result);
    }

    @PostMapping("/check")
    public ResultVO check(@RequestParam("openid") String openid){
        UserInfoDTO userInfoDTO =  userInfoService.checkUserInfoByBuyerOpenid(openid);
        return  ResultVOUtil.success(userInfoDTO);
    }

    @PostMapping("/inquire/certification")
    public ResultVO getCertification(@RequestParam("openid") String openid){
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(openid);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("certificationStatu",userInfoDTO.getCertification());
        return ResultVOUtil.success(jsonObject);
    }

    @PostMapping("inquire/userCreditLevel")
    public ResultVO getCreditLevel(@RequestParam("openid") String openid){
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(openid);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("creditLevel",userInfoDTO.getCreditLevel());
        return ResultVOUtil.success(jsonObject);
    }
}
