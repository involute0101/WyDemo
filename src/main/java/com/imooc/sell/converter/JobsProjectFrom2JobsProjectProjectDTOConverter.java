package com.imooc.sell.converter;

//import com.google.gson.Gson;


import com.imooc.sell.controller.form.IdleProjectFrom;
import com.imooc.sell.controller.form.JobsProjectFrom;
import com.imooc.sell.dto.IdleProjectDTO;
import com.imooc.sell.dto.JobsProjectDTO;

import java.util.Date;

public class JobsProjectFrom2JobsProjectProjectDTOConverter {
    public static JobsProjectDTO convert(JobsProjectFrom jobsProjectFrom) {
        JobsProjectDTO jobsProjectDTO = new JobsProjectDTO();

        jobsProjectDTO.setUserOpenId(jobsProjectFrom.getOpenid());
        jobsProjectDTO.setTitle(jobsProjectFrom.getTitle());
        jobsProjectDTO.setAmount(jobsProjectFrom.getAmount());
        jobsProjectDTO.setHyperlink(jobsProjectFrom.getHyperlink());
        jobsProjectDTO.setContent(jobsProjectFrom.getContent());
        jobsProjectDTO.setGender(jobsProjectFrom.getGender());
        jobsProjectDTO.setPicture(jobsProjectFrom.getPicture());
        jobsProjectDTO.setFavoriteNumber(0);
        jobsProjectDTO.setPageviews(0);
        jobsProjectDTO.setType(6);
        jobsProjectDTO.setLocation(jobsProjectFrom.getLocation());
        jobsProjectDTO.setCreateTime(new Date());
        jobsProjectDTO.setUpdateTime(new Date());
        String tags = "";
        if(jobsProjectFrom.getTags()!=null){
            for(String tag : jobsProjectFrom.getTags())tags = tags + tag + ",";
            jobsProjectDTO.setTags(tags.substring(0,tags.length()-1));
        }
        return jobsProjectDTO;
    }
}
