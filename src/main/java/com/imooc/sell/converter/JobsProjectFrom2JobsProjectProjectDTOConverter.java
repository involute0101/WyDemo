package com.imooc.sell.converter;

//import com.google.gson.Gson;


import com.imooc.sell.controller.form.IdleProjectFrom;
import com.imooc.sell.controller.form.JobsProjectFrom;
import com.imooc.sell.dto.IdleProjectDTO;
import com.imooc.sell.dto.JobsProjectDTO;

public class JobsProjectFrom2JobsProjectProjectDTOConverter {
    public static JobsProjectDTO convert(JobsProjectFrom jobsProjectFrom) {
        JobsProjectDTO jobsProjectDTO = new JobsProjectDTO();

        jobsProjectDTO.setUserOpenid(jobsProjectFrom.getOpenid());
        jobsProjectDTO.setTitle(jobsProjectFrom.getTitle());
        jobsProjectDTO.setAmount(jobsProjectFrom.getAmount());
        jobsProjectDTO.setHyperlink(jobsProjectFrom.getHyperlink());
        jobsProjectDTO.setContent(jobsProjectFrom.getContent());
        jobsProjectDTO.setGender(jobsProjectFrom.getGender());
        jobsProjectDTO.setPicture(jobsProjectFrom.getPicture());
        return jobsProjectDTO;
    }
}
