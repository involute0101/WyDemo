package com.imooc.sell.converter;

//import com.google.gson.Gson;


import com.imooc.sell.controller.form.UserInfoFrom;
import com.imooc.sell.dto.UserInfoDTO;

public class UserInfoFrom2UserInfoDTOConverter {
    public static UserInfoDTO convert(UserInfoFrom userInfoFrom) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();

        userInfoDTO.setUserOpenid(userInfoFrom.getOpenid());
        userInfoDTO.setUserPassword(userInfoFrom.getPassword());
        userInfoDTO.setCertification(0);
        userInfoDTO.setCreditLevel(1);

        return userInfoDTO;
    }
}
