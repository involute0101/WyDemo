package com.imooc.sell.converter;

import com.imooc.sell.controller.form.UserInfoUpdateForm;
import com.imooc.sell.dto.UserInfoDTO;

public class UserInfoUpdateForm2UserInfoDTOConverter {
    public static UserInfoDTO converter(UserInfoUpdateForm userInfoUpdateForm){
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserOpenid(userInfoUpdateForm.getOpenid());
        userInfoDTO.setUserName(userInfoUpdateForm.getUserName());
        userInfoDTO.setHeadPortrait(userInfoUpdateForm.getHeadPortrait());
        userInfoDTO.setQqNumber(userInfoUpdateForm.getQQ());
        userInfoDTO.setWeChat(userInfoUpdateForm.getWeChat());
        userInfoDTO.setTelephone(userInfoUpdateForm.getTelephone());
        return userInfoDTO;
    }
}
