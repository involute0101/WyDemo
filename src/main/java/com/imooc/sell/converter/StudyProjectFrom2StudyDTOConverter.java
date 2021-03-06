package com.imooc.sell.converter;

//import com.google.gson.Gson;


import com.imooc.sell.controller.form.RewardProjectFrom;
import com.imooc.sell.controller.form.StudyProjectFrom;
import com.imooc.sell.dto.RewardProjectDTO;
import com.imooc.sell.dto.StudyProjectDTO;

import java.util.Date;

public class StudyProjectFrom2StudyDTOConverter {
    public static StudyProjectDTO convert(StudyProjectFrom studyProjectFrom) {
        StudyProjectDTO studyProjectDTO = new StudyProjectDTO();

        studyProjectDTO.setUserOpenId(studyProjectFrom.getOpenid());
        studyProjectDTO.setTitle(studyProjectFrom.getTitle());
        studyProjectDTO.setAmount(studyProjectFrom.getAmount());
        studyProjectDTO.setContent(studyProjectFrom.getContent());
        studyProjectDTO.setGender(studyProjectFrom.getGender());
        studyProjectDTO.setPicture(studyProjectFrom.getPicture());
        studyProjectDTO.setLocation(studyProjectFrom.getLocation());
        studyProjectDTO.setHyperlink(studyProjectFrom.getHyperlink());
        studyProjectDTO.setFavoriteNumber(0);
        studyProjectDTO.setPageviews(0);
        studyProjectDTO.setType(3);
        studyProjectDTO.setCreateTime(new Date());
        studyProjectDTO.setUpdateTime(new Date());
        String tags = "";
        if(studyProjectFrom.getTags()!=null){
            for(String tag : studyProjectFrom.getTags())tags = tags + tag + ",";
            studyProjectDTO.setTags(tags.substring(0,tags.length()-1));
        }
        return studyProjectDTO;
    }
}
