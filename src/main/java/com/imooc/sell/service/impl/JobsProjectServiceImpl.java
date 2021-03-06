package com.imooc.sell.service.impl;

import com.imooc.sell.controller.form.JobsProjectFrom;
import com.imooc.sell.controller.form.TagForm;
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

    @Autowired
    TagService tagService;

    @Override
    @Transactional
    public JobsProjectDTO createJobsProjectOne(JobsProjectDTO jobsProjectDTO) throws Exception {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(jobsProjectDTO.getUserOpenId());
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
        if(jobsProjectDTO.getPicture()!=null){
            for(String picture : jobsProjectDTO.getPicture()){
                pictureArray = pictureArray + picture + ",";
            }
            jobsProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        }
        logger.info("??????????????????:" + jobsProject.toString());
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

    /**
     * ???????????????????????????????????????
     * @param titleKeyword  ??????????????????
     * @param pageable  ????????????
     * @return
     */
    @Override
    public List<JobsProjectDTO> findJobsProjectByTitleLike(String titleKeyword, Pageable pageable) {
        List<JobsProjectDTO> list = new ArrayList<>();
        Page<JobsProject> page = jobsProjectRepository.findByTitleLike("%" + titleKeyword + "%", pageable);
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

    /**
     * ?????????????????????????????????????????????
     * @param pageable ????????????
     * @return
     */
    @Override
    public List<JobsProjectDTO> findJobsProjectOrderByFavoritesNumber(Pageable pageable) {
        Page<JobsProject> page = jobsProjectRepository.findByOrderByFavoriteNumberDesc(pageable);
        List<JobsProjectDTO> list = new ArrayList<>();
        for(JobsProject jobsProject : page){
            JobsProjectDTO jobsProjectDTO = new JobsProjectDTO();
            BeanUtils.copyProperties(jobsProject, jobsProjectDTO,"picture");
            if (jobsProject.getPicture()!=null){
                jobsProjectDTO.setPicture(jobsProject.getPicture().split(","));
            }
            list.add(jobsProjectDTO);
        }
        return list;
    }

    /**
     * ???????????????????????????????????????????????????
     * @param pageable
     * @param sort ?????????asc????????????desc?????????
     * @return
     */
    @Override
    public List<JobsProjectDTO> findJobsProjectOrderByAmount(Pageable pageable, String sort) {
        Page<JobsProject> page = null;
        if("desc".equals(sort))page = jobsProjectRepository.findByOrderByAmountDesc(pageable);
        else page = jobsProjectRepository.findByOrderByAmount(pageable);
        List<JobsProjectDTO> list = new ArrayList<>();
        for(JobsProject jobsProject : page){
            JobsProjectDTO jobsProjectDTO = new JobsProjectDTO();
            BeanUtils.copyProperties(jobsProject, jobsProjectDTO,"picture");
            if (jobsProject.getPicture()!=null){
                jobsProjectDTO.setPicture(jobsProject.getPicture().split(","));
            }
            list.add(jobsProjectDTO);
        }
        return list;
    }

    /**
     *
     * @param jobsProjectDTO
     * @return
     */
    @Override
    public JobsProjectDTO updateJobsProject(JobsProjectDTO jobsProjectDTO) {
        JobsProject jobsProject = new JobsProject();
        BeanUtils.copyProperties(jobsProjectDTO, jobsProject,"picture");
        String pictureArray = "";
        if(jobsProjectDTO.getPicture()!=null){
            for(String picture : jobsProjectDTO.getPicture()){
                pictureArray = pictureArray + picture + ",";
            }
            jobsProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        }
        logger.info("??????????????????:" + jobsProject.toString());
        JobsProject result = jobsProjectRepository.save(jobsProject);
        JobsProjectDTO resultDTO = new JobsProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    /**
     * ?????????????????????
     * @param projectId ??????Id
     * @return
     */
    @Override
    public JobsProjectDTO increasePageviews(String projectId) {
        JobsProject jobsProject = jobsProjectRepository.findByProjectId(projectId);
        if (jobsProject==null){
            throw new SellException(ResultEnum.PROJECT_ID_NOT_FOUND);
        }
        jobsProject.setPageviews(jobsProject.getPageviews()+1);
        jobsProjectRepository.save(jobsProject);
        JobsProjectDTO jobsProjectDTO = new JobsProjectDTO();
        BeanUtils.copyProperties(jobsProject, jobsProjectDTO,"picture");
        if (jobsProject.getPicture()!=null){
            jobsProjectDTO.setPicture(jobsProject.getPicture().split(","));
        }
        return jobsProjectDTO;
    }

    /**
     * ???????????????????????????=?????????*0.000001+?????????*10
     * @param pageable  ????????????
     * @return
     */
    @Override
    public List<JobsProjectDTO> findByComplexService(Pageable pageable) {
        List<JobsProjectDTO> result = new ArrayList<>();
        int pageSize = pageable.getPageSize();
        int offsetNumber = pageable.getOffset();
        List<JobsProject> jobsProjectList = jobsProjectRepository.findByComplex(pageSize, offsetNumber);
        for(JobsProject jobsProject : jobsProjectList){
            JobsProjectDTO jobsProjectDTO = new JobsProjectDTO();
            BeanUtils.copyProperties(jobsProject, jobsProjectDTO,"picture");
            if (jobsProject.getPicture()!=null){
                jobsProjectDTO.setPicture(jobsProject.getPicture().split(","));
            }
            result.add(jobsProjectDTO);
        }
        return result;
    }

    /**
     * ???????????????????????????????????????????????????
     * @param keyword ???????????????
     * @param pageable ????????????
     * @return
     */
    @Override
    public List<JobsProjectDTO> findJobsProjectByTagsLikeOrderByUpdateTime(String keyword, Pageable pageable) {
        List<JobsProjectDTO> result = new ArrayList<>();
        Page<JobsProject> page = jobsProjectRepository.findByTagsLikeOrderByUpdateTimeDesc("%" + keyword + "%", pageable);
        for(JobsProject jobsProject : page){
            JobsProjectDTO jobsProjectDTO = new JobsProjectDTO();
            BeanUtils.copyProperties(jobsProject, jobsProjectDTO,"picture");
            if (jobsProject.getPicture()!=null){
                jobsProjectDTO.setPicture(jobsProject.getPicture().split(","));
            }
            result.add(jobsProjectDTO);
        }
        return result;
    }

    /**
     * ????????????????????????????????????????????????
     * @param keyword ???????????????
     * @param pageable ????????????
     * @return
     */
    @Override
    public List<JobsProjectDTO> findJobsProjectByTagsLikeOrderByFavoritesNumber(String keyword, Pageable pageable) {
        List<JobsProjectDTO> result = new ArrayList<>();
        Page<JobsProject> page = jobsProjectRepository.findByTagsLikeOrderByFavoriteNumberDesc("%" + keyword + "%", pageable);
        for(JobsProject jobsProject : page){
            JobsProjectDTO jobsProjectDTO = new JobsProjectDTO();
            BeanUtils.copyProperties(jobsProject, jobsProjectDTO,"picture");
            if (jobsProject.getPicture()!=null){
                jobsProjectDTO.setPicture(jobsProject.getPicture().split(","));
            }
            result.add(jobsProjectDTO);
        }
        return result;
    }

    /**
    * ???????????????????????????????????????????????????
    * @param jobsProjectFrom
    */
    @Override
    public void tagHandler(JobsProjectFrom jobsProjectFrom) {
        TagForm tagForm = new TagForm();
        tagForm.setUserOpenId(jobsProjectFrom.getOpenid());
        tagForm.setTagContent(jobsProjectFrom.getTags());
        tagService.createTag(tagForm,1);
    }
}
