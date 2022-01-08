package com.imooc.sell.service;

import com.imooc.sell.dataobject.JobsProject;
import com.imooc.sell.dto.JobsProjectDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobsProjectService {
    JobsProjectDTO createJobsProjectOne(JobsProjectDTO jobsProjectDTO) throws Exception;
    List<JobsProjectDTO> findJobsProjectsOrderByUpdateTime(Pageable pageable);
    JobsProjectDTO findJobsProjectByProjectId(String projectId);
}