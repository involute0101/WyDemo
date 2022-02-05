package com.imooc.sell.converter;

//import com.google.gson.Gson;


import com.imooc.sell.controller.form.IdleProjectFrom;
import com.imooc.sell.controller.form.UserInfoFrom;
import com.imooc.sell.dto.IdleProjectDTO;
import com.imooc.sell.dto.UserInfoDTO;

public class IdleProjectFrom2IdleProjectDTOConverter {
    public static IdleProjectDTO convert(IdleProjectFrom idleProjectFrom) {
        IdleProjectDTO idleProjectDTO = new IdleProjectDTO();

        idleProjectDTO.setUserOpenid(idleProjectFrom.getOpenid());
        idleProjectDTO.setTitle(idleProjectFrom.getTitle());
        idleProjectDTO.setAmount(idleProjectFrom.getAmount());
        idleProjectDTO.setContactNumber(idleProjectFrom.getContactNumber());
        idleProjectDTO.setContent(idleProjectFrom.getContent());
        idleProjectDTO.setGender(idleProjectFrom.getGender());
        idleProjectDTO.setPicture(idleProjectFrom.getPicture());
        String tags = "";
        for (String tag : idleProjectFrom.getTags()) tags = tags + tag + ",";
        idleProjectDTO.setTags(tags.substring(0,tags.length()-1));
        return idleProjectDTO;
    }
}
