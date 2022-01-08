package com.imooc.sell.service;

import com.imooc.sell.VO.CaptchaVO;
import com.imooc.sell.dto.UserInfoDTO;

import java.util.Collection;
import java.util.List;

public interface UserInfoService {
    //注册
    UserInfoDTO createUserInfoOne(UserInfoDTO userInfoDTO) throws Exception;

    //登录
    UserInfoDTO findUserInfoByBuyerOpenidAndPassword(String openid, String password);

    //防止账号重复
    UserInfoDTO checkUserInfoByBuyerOpenid(String openid);

    //通过openId查找用户
    UserInfoDTO findUserInfoByUserOpeinid(String openid);
    //验证
    CaptchaVO sendSms(String telephone);

    //通过批量userId查找用户
    List<UserInfoDTO> findUserInfos(Collection<Integer> collection);
}