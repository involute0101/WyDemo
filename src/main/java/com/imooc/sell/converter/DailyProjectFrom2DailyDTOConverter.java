package com.imooc.sell.converter;

import com.imooc.sell.controller.form.DailyProjectForm;
import com.imooc.sell.dto.DailyProjectDTO;

import java.util.Date;

public class DailyProjectFrom2DailyDTOConverter {
    public static DailyProjectDTO convert(DailyProjectForm dailyProjectForm){
        DailyProjectDTO dailyProjectDTO = new DailyProjectDTO();
        dailyProjectDTO.setUserOpenId(dailyProjectForm.getOpenid());
        dailyProjectDTO.setTitle(dailyProjectForm.getTitle());
        dailyProjectDTO.setContactNumber(dailyProjectForm.getContactNumber());
        dailyProjectDTO.setContent(dailyProjectForm.getContent());
        dailyProjectDTO.setGender(dailyProjectForm.getGender());
        dailyProjectDTO.setPicture(dailyProjectForm.getPicture());
        dailyProjectDTO.setLocation(dailyProjectForm.getLocation());
        dailyProjectDTO.setCreateTime(new Date());
        dailyProjectDTO.setUpdateTime(new Date());
        dailyProjectDTO.setFavoriteNumber(0);
        dailyProjectDTO.setPageviews(0);
        dailyProjectDTO.setType(8);
        String tags = "";
        if(dailyProjectForm.getTags()!=null){
            for(String tag : dailyProjectForm.getTags())tags = tags + tag + ",";
            dailyProjectDTO.setTags(tags.substring(0,tags.length()-1));
        }
        return dailyProjectDTO;
    }
}
