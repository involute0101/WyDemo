package com.imooc.sell.converter;

//import com.google.gson.Gson;


import com.imooc.sell.controller.form.IdleProjectFrom;
import com.imooc.sell.controller.form.LostPropertyProjectFrom;
import com.imooc.sell.dto.IdleProjectDTO;
import com.imooc.sell.dto.LostPropertyProjectDTO;

import java.util.Date;

public class LostPropertyProjectFrom2LostPropertyProjectDTOConverter {
    public static LostPropertyProjectDTO convert(LostPropertyProjectFrom lostPropertyProjectFrom) {
        LostPropertyProjectDTO lostPropertyProjectDTO = new LostPropertyProjectDTO();

        lostPropertyProjectDTO.setUserOpenid(lostPropertyProjectFrom.getOpenid());
        lostPropertyProjectDTO.setTitle(lostPropertyProjectFrom.getTitle());
        lostPropertyProjectDTO.setAmount(lostPropertyProjectFrom.getAmount());
        lostPropertyProjectDTO.setContactNumber(lostPropertyProjectFrom.getContactNumber());
        lostPropertyProjectDTO.setContent(lostPropertyProjectFrom.getContent());
        lostPropertyProjectDTO.setGender(lostPropertyProjectFrom.getGender());
        lostPropertyProjectDTO.setPicture(lostPropertyProjectFrom.getPicture());
        lostPropertyProjectDTO.setLocation(lostPropertyProjectFrom.getLocation());
        lostPropertyProjectDTO.setFavoriteNumber(0);
        lostPropertyProjectDTO.setPageviews(0);
        lostPropertyProjectDTO.setCreateTime(new Date());
        lostPropertyProjectDTO.setUpdateTime(new Date());
        String tags = "";
        if(lostPropertyProjectFrom.getTags()!=null){
            for(String tag : lostPropertyProjectFrom.getTags())tags = tags + tag + ",";
            lostPropertyProjectDTO.setTags(tags.substring(0,tags.length()-1));
        }
        return lostPropertyProjectDTO;
    }
}
