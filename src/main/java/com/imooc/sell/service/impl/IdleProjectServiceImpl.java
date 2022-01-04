package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.IdleProject;
import com.imooc.sell.dto.IdleProjectDTO;
import com.imooc.sell.dto.ProjectMasterDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.IdleProjectRepository;
import com.imooc.sell.service.IdleProjectService;
import com.imooc.sell.service.UserInfoService;
import com.imooc.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
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
public class IdleProjectServiceImpl implements IdleProjectService {
    @Autowired
    IdleProjectRepository idleProjectRepository;

    @Autowired
    UserInfoService userInfoService ;

    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @Override
    @Transactional
    public IdleProjectDTO createIdleProjectOne(IdleProjectDTO idleProjectDTO) throws Exception {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(idleProjectDTO.getUserOpenid());
        if (userInfoDTO == null){
            throw  new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
        projectMasterDTO.setProjectType(6);
        projectMasterDTO.setProjectId(KeyUtil.genUniqueKey());
        projectMasterDTO.setUserId(userId);
        ProjectMasterDTO createResult = projectMasterService.createProjectMasterOne(projectMasterDTO);
        if (createResult == null){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        idleProjectDTO.setProjectId(projectMasterDTO.getProjectId());
        IdleProject idleProject = new IdleProject();
        BeanUtils.copyProperties(idleProjectDTO,idleProject);
        IdleProject result = idleProjectRepository.save(idleProject);
        IdleProjectDTO resultDTO = new IdleProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    @Override
    public List<IdleProjectDTO> findIdleProjectsOrderByUpdateTime(Pageable pageable) {
        Page<IdleProject> page = idleProjectRepository.findByOrderByUpdateTimeDesc(pageable);
        List<IdleProjectDTO> list = new ArrayList<>();

        for (IdleProject idleProject: page){
            IdleProjectDTO idleProjectDTO = new IdleProjectDTO();
            BeanUtils.copyProperties(idleProject, idleProjectDTO);
            list.add(idleProjectDTO);
        }
        return list;
    }


    @Override
    public IdleProjectDTO findIdleProjectByProjectId(String projectId) {
        IdleProject idleProject = idleProjectRepository.findByProjectId(projectId);
        IdleProjectDTO idleProjectDTO = new IdleProjectDTO();
        if(idleProject != null) {
            BeanUtils.copyProperties(idleProject, idleProjectDTO);
            return idleProjectDTO;
        }
        else {
            throw new SellException(ResultEnum.PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID);
        }
    }
}
