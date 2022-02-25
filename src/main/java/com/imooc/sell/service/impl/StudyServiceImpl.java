package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.PurchasingProject;
import com.imooc.sell.dataobject.StudyProject;
import com.imooc.sell.dto.ProjectMasterDTO;
import com.imooc.sell.dto.PurchasingProjectDTO;
import com.imooc.sell.dto.StudyProjectDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.StudyProjectRepository;
import com.imooc.sell.service.StudyProjectService;
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
public class StudyServiceImpl implements StudyProjectService {

    public static final Logger logger = LoggerFactory.getLogger(StudyServiceImpl.class);

    @Autowired
    StudyProjectRepository studyProjectRepository;

    @Autowired
    UserInfoService userInfoService ;

    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @Override
    @Transactional
    public StudyProjectDTO createStudyProject(StudyProjectDTO studyProjectDTO) throws Exception {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(studyProjectDTO.getUserOpenid());
        if (userInfoDTO == null){
            throw  new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
        projectMasterDTO.setProjectType(3);
        projectMasterDTO.setProjectId(KeyUtil.genUniqueKey());
        projectMasterDTO.setUserId(userId);
        ProjectMasterDTO createResult = projectMasterService.createProjectMasterOne(projectMasterDTO);
        if (createResult == null){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        studyProjectDTO.setProjectId(projectMasterDTO.getProjectId());
        StudyProject studyProject = new StudyProject();
        BeanUtils.copyProperties(studyProjectDTO,studyProject,"picture");
        String pictureArray = "";
        for(String picture : studyProjectDTO.getPicture()){
            pictureArray = pictureArray + picture + ",";
        }
        studyProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        logger.info("创建学习项目:"+studyProject.toString());
        StudyProject result = studyProjectRepository.save(studyProject);
        StudyProjectDTO resultDTO = new StudyProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    @Override
    public List<StudyProjectDTO> findStudyProjectsOrderByUpdateTime(Pageable pageable) {
        Page<StudyProject> page = studyProjectRepository.findByOrderByUpdateTimeDesc(pageable);
        List<StudyProjectDTO> list = new ArrayList<>();

        for (StudyProject studyProject: page){
            StudyProjectDTO studyProjectDTO = new StudyProjectDTO();
            BeanUtils.copyProperties(studyProject, studyProjectDTO,"picture");
            if (studyProject.getPicture()!=null){
                studyProjectDTO.setPicture(studyProject.getPicture().split(","));
            }
            list.add(studyProjectDTO);
        }
        return list;
    }

    @Override
    public StudyProjectDTO findStudyByProjectId(String projectId) {
        StudyProject studyProject = studyProjectRepository.findByProjectId(projectId);
        StudyProjectDTO studyProjectDTO = new StudyProjectDTO();
        if(studyProject != null) {
            BeanUtils.copyProperties(studyProject, studyProjectDTO,"picture");
            if (studyProject.getPicture()!=null){
                studyProjectDTO.setPicture(studyProject.getPicture().split(","));
            }
            return studyProjectDTO;
        }
        else {
            throw new SellException(ResultEnum.PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID);
        }

    }

    @Override
    public List<StudyProjectDTO> findStudyProjectByTagsLike(String keyword, Pageable pageable) {
        List<StudyProjectDTO> list = new ArrayList<>();
        Page<StudyProject> page = studyProjectRepository.findByTagsLike("%" + keyword + "%", pageable);
        for(StudyProject studyProject: page){
            StudyProjectDTO studyProjectDTO = new StudyProjectDTO();
            BeanUtils.copyProperties(studyProject,studyProjectDTO,"picture");
            if (studyProject.getPicture()!=null){
                studyProjectDTO.setPicture(studyProject.getPicture().split(","));
            }
            list.add(studyProjectDTO);
        }
        return list;
    }

    /**
     * 更新项目，用于其他项目调用，当其他项目要修改信息时，调用此方法保存修改后的值
     * @param studyProjectDTO
     * @return
     */
    @Override
    public StudyProjectDTO updateStudyProjectDTO(StudyProjectDTO studyProjectDTO) {
        StudyProject studyProject = new StudyProject();
        BeanUtils.copyProperties(studyProjectDTO,studyProject,"picture");
        String pictureArray = "";
        for(String picture : studyProjectDTO.getPicture()){
            pictureArray = pictureArray + picture + ",";
        }
        studyProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        logger.info("更新学习项目:"+studyProject);
        StudyProject result = studyProjectRepository.save(studyProject);
        StudyProjectDTO resultDTO = new StudyProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }
}
