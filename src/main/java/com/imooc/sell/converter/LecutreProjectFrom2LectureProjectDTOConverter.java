package com.imooc.sell.converter;

//import com.google.gson.Gson;


import com.imooc.sell.controller.form.IdleProjectFrom;
import com.imooc.sell.controller.form.LectureProjectFrom;
import com.imooc.sell.dto.IdleProjectDTO;
import com.imooc.sell.dto.LectureProjectDTO;

public class LecutreProjectFrom2LectureProjectDTOConverter {
    public static LectureProjectDTO convert(LectureProjectFrom lectureProjectFrom) {
        LectureProjectDTO lectureProjectDTO = new LectureProjectDTO();
        lectureProjectDTO.setPresenter(lectureProjectFrom.getPresenter());
        lectureProjectDTO.setUserOpenid(lectureProjectFrom.getOpenid());
        lectureProjectDTO.setTitle(lectureProjectFrom.getTitle());
        lectureProjectDTO.setContent(lectureProjectFrom.getContent());
        lectureProjectDTO.setGender(lectureProjectFrom.getGender());
        lectureProjectDTO.setLocation(lectureProjectFrom.getLocation());
        lectureProjectDTO.setPicture(lectureProjectFrom.getPicture());
        return lectureProjectDTO;
    }
}
