package com.imooc.sell.converter;


import com.imooc.sell.controller.form.UserFollowForm;
import com.imooc.sell.dto.UserFollowDTO;

import java.util.Date;

public class UserFollowForm2UserFollowDTOConverter {
    public static UserFollowDTO convert(UserFollowForm userFollowForm){
        UserFollowDTO userFollowDTO = new UserFollowDTO();
        userFollowDTO.setUserOpenId(userFollowForm.getUserOpenId());
        userFollowDTO.setGoalFollower(userFollowForm.getGoalFollower());
        userFollowDTO.setFollowTime(new Date());
        return userFollowDTO;
    }
}
