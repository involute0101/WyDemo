package com.imooc.sell.converter;

//import com.google.gson.Gson;


import com.imooc.sell.controller.form.RewardProjectFrom;
import com.imooc.sell.controller.form.StudyProjectFrom;
import com.imooc.sell.dto.RewardProjectDTO;
import com.imooc.sell.dto.StudyProjectDTO;

public class StudyProjectFrom2StudyDTOConverter {
    public static StudyProjectDTO convert(StudyProjectFrom studyProjectFrom) {
        StudyProjectDTO studyProjectDTO = new StudyProjectDTO();

        studyProjectDTO.setUserOpenid(studyProjectFrom.getOpenid());
        studyProjectDTO.setTitle(studyProjectFrom.getTitle());
        studyProjectDTO.setAmount(studyProjectFrom.getAmount());
        studyProjectDTO.setContent(studyProjectFrom.getContent());
        studyProjectDTO.setGender(studyProjectFrom.getGender());
        studyProjectDTO.setPicture(studyProjectFrom.getPicture());
        studyProjectDTO.setLocation(studyProjectFrom.getLocation());
        studyProjectDTO.setHyperlink(studyProjectFrom.getHyperlink());
        return studyProjectDTO;
    }
}
