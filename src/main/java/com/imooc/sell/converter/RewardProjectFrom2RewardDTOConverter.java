package com.imooc.sell.converter;

//import com.google.gson.Gson;


import com.imooc.sell.controller.form.PurchasingProjectFrom;
import com.imooc.sell.controller.form.RewardProjectFrom;
import com.imooc.sell.dto.PurchasingProjectDTO;
import com.imooc.sell.dto.RewardProjectDTO;

public class RewardProjectFrom2RewardDTOConverter {
    public static RewardProjectDTO convert(RewardProjectFrom rewardProjectFrom) {
        RewardProjectDTO rewardProjectDTO = new RewardProjectDTO();

        rewardProjectDTO.setUserOpenid(rewardProjectFrom.getOpenid());
        rewardProjectDTO.setTitle(rewardProjectFrom.getTitle());
        rewardProjectDTO.setAmount(rewardProjectFrom.getAmount());
        rewardProjectDTO.setContactNumber(rewardProjectFrom.getContactNumber());
        rewardProjectDTO.setContent(rewardProjectFrom.getContent());
        rewardProjectDTO.setGender(rewardProjectFrom.getGender());
        rewardProjectDTO.setPicture(rewardProjectFrom.getPicture());
        rewardProjectDTO.setLocation(rewardProjectFrom.getLocation());
        String tags = "";
        for(String tag : rewardProjectFrom.getTags())tags = tags + tag + ",";
        rewardProjectDTO.setTags(tags.substring(0,tags.length()-1));
        return rewardProjectDTO;
    }
}
