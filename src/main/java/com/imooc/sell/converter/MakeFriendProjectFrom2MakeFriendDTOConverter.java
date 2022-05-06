package com.imooc.sell.converter;

import com.imooc.sell.controller.form.MakeFriendProjectForm;
import com.imooc.sell.dto.MakeFriendProjectDTO;

import java.util.Date;

public class MakeFriendProjectFrom2MakeFriendDTOConverter {
    public static MakeFriendProjectDTO convert(MakeFriendProjectForm makeFriendProjectForm){
        MakeFriendProjectDTO makeFriendProjectDTO = new MakeFriendProjectDTO();
        makeFriendProjectDTO.setUserOpenId(makeFriendProjectForm.getOpenid());
        makeFriendProjectDTO.setTitle(makeFriendProjectForm.getTitle());
        makeFriendProjectDTO.setContactNumber(makeFriendProjectForm.getContactNumber());
        makeFriendProjectDTO.setContent(makeFriendProjectForm.getContent());
        makeFriendProjectDTO.setGender(makeFriendProjectForm.getGender());
        makeFriendProjectDTO.setPicture(makeFriendProjectForm.getPicture());
        makeFriendProjectDTO.setLocation(makeFriendProjectForm.getLocation());
        makeFriendProjectDTO.setCreateTime(new Date());
        makeFriendProjectDTO.setUpdateTime(new Date());
        makeFriendProjectDTO.setFavoriteNumber(0);
        makeFriendProjectDTO.setPageviews(0);
        makeFriendProjectDTO.setType(7);
        String tags = "";
        if(makeFriendProjectForm.getTags()!=null){
            for(String tag : makeFriendProjectForm.getTags())tags = tags + tag + ",";
            makeFriendProjectDTO.setTags(tags.substring(0,tags.length()-1));
        }
        return makeFriendProjectDTO;
    }
}
