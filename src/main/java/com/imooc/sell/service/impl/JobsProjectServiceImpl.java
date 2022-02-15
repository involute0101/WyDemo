package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.JobsProject;
import com.imooc.sell.dto.JobsProjectDTO;
import com.imooc.sell.dto.ProjectMasterDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.JobsProjectRepository;
import com.imooc.sell.service.JobsProjectService;
import com.imooc.sell.service.UserInfoService;
import com.imooc.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class JobsProjectServiceImpl implements JobsProjectService {

    public static final Logger logger = LoggerFactory.getLogger(JobsProjectServiceImpl.class);

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @Autowired
    JobsProjectRepository jobsProjectRepository;

    @Override
    @Transactional
    public JobsProjectDTO createJobsProjectOne(JobsProjectDTO jobsProjectDTO) throws Exception {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(jobsProjectDTO.getUserOpenid());
        if (userInfoDTO == null) {
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
        projectMasterDTO.setProjectType(6);
        projectMasterDTO.setProjectId(KeyUtil.genUniqueKey());
        projectMasterDTO.setUserId(userId);
        ProjectMasterDTO createResult = projectMasterService.createProjectMasterOne(projectMasterDTO);
        if (createResult == null) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        jobsProjectDTO.setProjectId(projectMasterDTO.getProjectId());
        JobsProject jobsProject = new JobsProject();
        BeanUtils.copyProperties(jobsProjectDTO, jobsProject,"picture");
        String pictureArray = "";
        for(String picture : jobsProjectDTO.getPicture()){
            pictureArray = pictureArray + picture + ",";
        }
        jobsProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        logger.info("创建招聘项目:" + jobsProject.toString());
        JobsProject result = jobsProjectRepository.save(jobsProject);
        JobsProjectDTO resultDTO = new JobsProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    @Override
    public List<JobsProjectDTO> findJobsProjectsOrderByUpdateTime(Pageable pageable) {
        List<JobsProjectDTO> list = new ArrayList<>();
        Page<JobsProject> page = jobsProjectRepository.findByOrderByUpdateTimeDesc(pageable);
        for (JobsProject jobsProject : page) {
            JobsProjectDTO jobsProjectDTO = new JobsProjectDTO();
            BeanUtils.copyProperties(jobsProject, jobsProjectDTO,"picture");
            if (jobsProject.getPicture()!=null){
                jobsProjectDTO.setPicture(jobsProject.getPicture().split(","));
            }
            list.add(jobsProjectDTO);
        }
        return list;
    }

    @Override
    public JobsProjectDTO findJobsProjectByProjectId(String projectId) {
        JobsProject jobsProject = jobsProjectRepository.findByProjectId(projectId);
        JobsProjectDTO jobsProjectDTO = new JobsProjectDTO();
        if (jobsProject != null) {
            BeanUtils.copyProperties(jobsProject, jobsProjectDTO,"picture");
            if (jobsProject.getPicture()!=null){
                jobsProjectDTO.setPicture(jobsProject.getPicture().split(","));
            }
            return jobsProjectDTO;
        } else {
            throw new SellException(ResultEnum.PROJECT_ID_NOT_FOUND);
        }
    }

    @Override
    public List<JobsProjectDTO> findJobsProjectByTagsLike(String keyword, Pageable pageable) {
        List<JobsProjectDTO> list = new ArrayList<>();
        Page<JobsProject> page = jobsProjectRepository.findByTagsLike("%" + keyword + "%", pageable);
        for (JobsProject jobsProject : page) {
            JobsProjectDTO jobsProjectDTO = new JobsProjectDTO();
            BeanUtils.copyProperties(jobsProject, jobsProjectDTO,"picture");
            if (jobsProject.getPicture()!=null){
                jobsProjectDTO.setPicture(jobsProject.getPicture().split(","));
            }
            list.add(jobsProjectDTO);
        }
        return list;
    }
}
