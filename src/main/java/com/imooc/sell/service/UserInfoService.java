package com.imooc.sell.service;

import com.alibaba.fastjson.JSONArray;
import com.imooc.sell.VO.CaptchaVO;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.dataobject.UserInfo;
import com.imooc.sell.dto.UserInfoDTO;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface UserInfoService {
    //注册
    UserInfoDTO createUserInfoOne(UserInfoDTO userInfoDTO) throws Exception;

    UserInfo updateUserInfo(UserInfoDTO userInfoDTO);

    //登录
    UserInfoDTO findUserInfoByBuyerOpenidAndPassword(String openid, String password);

    //无密码登录
    public UserInfoDTO findUserInfoByUserOpenId(String openId);

    //防止账号重复
    UserInfoDTO checkUserInfoByBuyerOpenid(String openid);

    //通过openId查找用户
    UserInfoDTO findUserInfoByUserOpeinid(String openid);
    //验证
    CaptchaVO sendSms(String telephone);

    //通过批量userId查找用户
    List<UserInfoDTO> findUserInfos(Collection<Integer> collection);

    //根据id查找用户
    UserInfoDTO findByUserId(Integer userId);

    ResultVO modifyPassword(String userOpenId, String newPassword);

    UserInfoDTO studentCertification(UserInfoDTO userInfoDTO);

    String getOpenId(String code) throws IOException;

    ResultVO joinDiscussionCircle(String userOpenId,String circleName);

    boolean checkUserJoinCircle(String userOpenId,String circleName);
}
