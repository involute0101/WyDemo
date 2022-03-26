package com.imooc.sell.service;

import com.imooc.sell.controller.form.JobsProjectFrom;
import com.imooc.sell.dataobject.JobsProject;
import com.imooc.sell.dto.JobsProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobsProjectService {
    JobsProjectDTO createJobsProjectOne(JobsProjectDTO jobsProjectDTO) throws Exception;

    List<JobsProjectDTO> findJobsProjectsOrderByUpdateTime(Pageable pageable);

    JobsProjectDTO findJobsProjectByProjectId(String projectId);

    List<JobsProjectDTO> findJobsProjectByTagsLike(String keyword, Pageable pageable);

    List<JobsProjectDTO> findJobsProjectByTitleLike(String titleKeyword,Pageable pageable);

    List<JobsProjectDTO> findJobsProjectOrderByFavoritesNumber(Pageable pageable);

    List<JobsProjectDTO> findJobsProjectOrderByAmount(Pageable pageable, String sort);

    JobsProjectDTO updateJobsProject(JobsProjectDTO jobsProjectDTO);

    JobsProjectDTO increasePageviews(String projectId);

    List<JobsProjectDTO> findByComplexService(Pageable pageable);

    List<JobsProjectDTO> findJobsProjectByTagsLikeOrderByUpdateTime(String keyword,Pageable pageable);

    List<JobsProjectDTO> findJobsProjectByTagsLikeOrderByFavoritesNumber(String keyword, Pageable pageable);

    void tagHandler(JobsProjectFrom jobsProjectFrom);
}
