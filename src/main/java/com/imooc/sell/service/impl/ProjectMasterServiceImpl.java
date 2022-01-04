package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProjectMaster;
import com.imooc.sell.dto.ProjectMasterDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.ProjectMasterRepository;
import com.imooc.sell.service.ProjectMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProjectMasterServiceImpl implements ProjectMasterService {
    @Autowired
    ProjectMasterRepository projectMasterRepository;

    @Autowired
    UserInfoServiceImpl userInfoService;

    @Override
    @Transactional
    public ProjectMasterDTO createProjectMasterOne(ProjectMasterDTO projectMasterDTO) throws Exception {
        ProjectMaster projectMaster = new ProjectMaster();
        BeanUtils.copyProperties(projectMasterDTO, projectMaster);
        projectMaster = projectMasterRepository.save(projectMaster);
        if (projectMaster == null)
        {
            throw new SellException(ResultEnum.CREATE_PROJECT_MASTER_FAIL);
        }
        ProjectMasterDTO resultDto = new ProjectMasterDTO();
        BeanUtils.copyProperties(projectMaster,resultDto);
        return resultDto;
    }

    @Override
    public List<ProjectMasterDTO> findProjectMasterByUserOpenId(String userOpenId) {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(userOpenId);
        if (userInfoDTO == null){
            throw  new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        List<ProjectMaster> projectMasters = projectMasterRepository.findByUserId(userId);
        if (projectMasters != null){
            List<ProjectMasterDTO> result = new ArrayList<>();
            for(ProjectMaster projectMaster: projectMasters){
                ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
                BeanUtils.copyProperties(projectMaster, projectMasterDTO);
                result.add(projectMasterDTO);
            }
            return result;
        }
       return null;
    }

    @Override
    public List<ProjectMasterDTO> findProjectMasterByUserOpenIdAndProjectType(String userOpenId, Integer projectType) {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(userOpenId);
        if (userInfoDTO == null){
            throw  new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        List<ProjectMaster> projectMasters = projectMasterRepository.findByUserIdAndProjectType(userId, projectType);

        if (projectMasters != null){
            List<ProjectMasterDTO> result = new ArrayList<>();
            for(ProjectMaster projectMaster: projectMasters){
                ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
                BeanUtils.copyProperties(projectMaster, projectMasterDTO);
                result.add(projectMasterDTO);
            }
            return result;
        }
        return null;
    }

    @Override
    public ProjectMasterDTO findProjectMasterByProjectId(String projectId) {
        ProjectMaster projectMaster = projectMasterRepository.findByProjectId(projectId);
        if (projectMaster == null){
            throw new SellException(ResultEnum.PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID);
        }
        ProjectMasterDTO result = new ProjectMasterDTO();
        BeanUtils.copyProperties(projectMaster,result);
        return result;
    }

    @Override
    @Transactional
    public ProjectMasterDTO setProjectMasterOnProjectType(String projectId, String userOpenId, Integer projectType) {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(userOpenId);
        if (userInfoDTO == null){
            throw  new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        ProjectMaster projectMaster = projectMasterRepository.findByProjectId(projectId);
        if (projectMaster == null){
            throw new SellException(ResultEnum.PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID);
        }

        if (!projectMaster.getUserId().equals(userId)){
            throw new SellException((ResultEnum.PROJECT_MASTER_USER_ID_NOT_EQUAL));
        }

        if (!(projectType>=0 && projectType <= 8)){
            throw new SellException(ResultEnum.PROJECT_MASTER_TYPE_ILLEGAL);
        }
        projectMaster.setProjectType(projectType);
        projectMaster = projectMasterRepository.save(projectMaster);

        if(projectMaster == null){
            throw new SellException(ResultEnum.PROJECT_MASTER_CHANGE_TYPE_FAIL);
        }
        ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
        BeanUtils.copyProperties(projectMaster,projectMasterDTO);
        return projectMasterDTO;
    }

    @Override
    @Transactional
    public Boolean deleateProjectMaster(String projectId, String userOpenId) {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(userOpenId);
        if (userInfoDTO == null){
            throw  new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        ProjectMaster projectMaster = projectMasterRepository.findByProjectId(projectId);
        if(projectMaster == null){
            throw new SellException (ResultEnum.PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID);
        }

        if (!projectMaster.getUserId().equals(userId)){
            throw new SellException((ResultEnum.PROJECT_MASTER_USER_ID_NOT_EQUAL));
        }

        int result = projectMasterRepository.deleteByProjectId(projectId);

        return result != 0;
    }
}
