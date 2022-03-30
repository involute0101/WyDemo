package com.imooc.sell.controller;

import com.alibaba.fastjson.JSONObject;
import com.imooc.sell.VO.CaptchaVO;
import com.imooc.sell.VO.ResultVO;

import com.imooc.sell.controller.form.StudentCertificationForm;
import com.imooc.sell.controller.form.UserFollowForm;
import com.imooc.sell.controller.form.UserInfoFrom;
import com.imooc.sell.controller.form.UserInfoUpdateForm;
import com.imooc.sell.converter.StudentCertificationForm2UserInfoDTOConverter;
import com.imooc.sell.converter.UserFollowForm2UserFollowDTOConverter;
import com.imooc.sell.converter.UserInfoFrom2UserInfoDTOConverter;
import com.imooc.sell.converter.UserInfoUpdateForm2UserInfoDTOConverter;
import com.imooc.sell.dataobject.UserInfo;
import com.imooc.sell.dto.UserFollowDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.UserFollowService;
import com.imooc.sell.service.UserInfoService;
import com.imooc.sell.utils.ResultVOUtil;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/user/account")
@Slf4j
@Api(tags = "用户信息-访问接口")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserFollowService userFollowService;

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
            @ApiImplicitParam(name = "openid",value = "用户openId",required=true),
    })
    @PostMapping("/login")
    public ResultVO login(@RequestParam("openid") String openid){
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpenId(openid);
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

    @ApiOperation(value = "openId查询用户个人信息", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "openid",value = "用户id",required=true)
    @PostMapping("inquire/userInfo")
    public ResultVO getUserInfo(@RequestParam("openid") String openid){
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(openid);
        JSONObject user = new JSONObject();
        user.put("userName",userInfoDTO.getUserName());
        user.put("headPortrait",userInfoDTO.getHeadPortrait());
        user.put("QQ",userInfoDTO.getQqNumber());
        user.put("WeChat",userInfoDTO.getWeChat());
        user.put("telephone",userInfoDTO.getTelephone());
        user.put("university",userInfoDTO.getUserUniversity());
        user.put("degree",userInfoDTO.getUserDegree());
        return ResultVOUtil.success(user);
    }

    @ApiOperation(value = "userId查询用户个人信息", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "userId",value = "用户id",required=true)
    @PostMapping("inquire/userInfoByUserId")
    public ResultVO getUserInfo(@RequestParam("userId") Integer userId){
        UserInfoDTO userInfoDTO = userInfoService.findByUserId(userId);
        JSONObject user = new JSONObject();
        user.put("userName",userInfoDTO.getUserName());
        user.put("headPortrait",userInfoDTO.getHeadPortrait());
        user.put("QQ",userInfoDTO.getQqNumber());
        user.put("WeChat",userInfoDTO.getWeChat());
        user.put("telephone",userInfoDTO.getTelephone());
        return ResultVOUtil.success(user);
    }

    @ApiOperation(value = "更新用户信息", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @PostMapping("update/userInfo")
    public ResultVO updateUserInfo(@Valid UserInfoUpdateForm userInfoUpdateForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("[用户信息] 参数不正确， userInfoUpdateForm={}", userInfoUpdateForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        UserInfoDTO userInfoDTO = UserInfoUpdateForm2UserInfoDTOConverter.converter(userInfoUpdateForm);
        UserInfo userInfo = userInfoService.updateUserInfo(userInfoDTO);
        return ResultVOUtil.success(userInfoUpdateForm);
    }

    @ApiOperation(value = "修改用户密码", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userOpenId",value = "用户openId",required=true),
            @ApiImplicitParam(name = "newPassword",value = "新密码",required=true)
    })
    @PostMapping("/modifyPassword")
    public ResultVO modifyPassword(@RequestParam("userOpenId") String userOpenId,
                               @RequestParam("newPassword") String newPassword){
        return userInfoService.modifyPassword(userOpenId,newPassword);
    }

    @ApiOperation(value = "用户学生认证", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @PostMapping("/studentCertification")
    public ResultVO studentCertification(@Valid StudentCertificationForm studentCertificationForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("[学生认证] 参数不正确， studentCertificationForm={}", studentCertificationForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        UserInfoDTO userInfoDTO = StudentCertificationForm2UserInfoDTOConverter.convert(studentCertificationForm);
        UserInfoDTO result = userInfoService.studentCertification(userInfoDTO);
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "获取用户OpenId", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "code",value = "授权码",required=true)
    @PostMapping("/openId")
    public ResultVO getUserOpenId(@RequestParam("code") String code){
        try{
            String openId = userInfoService.getOpenId(code);
            return ResultVOUtil.success(openId);
        }catch (IOException e) {
            JSONObject result = new JSONObject();
            result.put("state","出现异常！");
            result.put("message",e.getMessage());
            return ResultVOUtil.success(result);
        }
    }

    @ApiOperation(value = "根据openId关注用户", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @PostMapping("/follow")
    public ResultVO followByUserOpenId(@Valid UserFollowForm userFollowForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("[关注用户] 参数不正确， userFollowForm={}", userFollowForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        UserFollowDTO userFollowDTO = UserFollowForm2UserFollowDTOConverter.convert(userFollowForm);
        UserFollowDTO result = userFollowService.createFollow(userFollowDTO);
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "用户加入圈子", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userOpenId",value = "用户openId",required=true),
            @ApiImplicitParam(name = "circleName",value = "圈子名称",required=true)
    })
    @PostMapping("/joinCircle")
    public ResultVO joinCircle(@RequestParam("userOpenId") String userOpenId,
                               @RequestParam("circleName") String circleName){
        return userInfoService.joinDiscussionCircle(userOpenId,circleName);
    }
}
