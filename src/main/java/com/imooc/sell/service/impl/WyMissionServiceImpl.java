package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.WyMissionProject;
import com.imooc.sell.dto.ProjectMasterDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.dto.WyMissionProjectDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.WyMissionProjectRepository;
import com.imooc.sell.service.UserInfoService;
import com.imooc.sell.service.WyMissionProjectService;
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
public class WyMissionServiceImpl implements WyMissionProjectService {
    @Autowired
    WyMissionProjectRepository wyMissionProjectRepository;

    @Autowired
    UserInfoService userInfoService ;

    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @Override
    @Transactional
    public WyMissionProjectDTO createWyMissionProject(WyMissionProjectDTO wyMissionProjectDTO) throws Exception {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(wyMissionProjectDTO.getUserOpenid());
        if (userInfoDTO == null){
            throw  new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
        projectMasterDTO.setProjectType(4);
        projectMasterDTO.setProjectId(KeyUtil.genUniqueKey());
        projectMasterDTO.setUserId(userId);
        ProjectMasterDTO createResult = projectMasterService.createProjectMasterOne(projectMasterDTO);
        if (createResult == null){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        wyMissionProjectDTO.setProjectId(projectMasterDTO.getProjectId());
        WyMissionProject wyMissionProject = new WyMissionProject();
        BeanUtils.copyProperties(projectMasterDTO,wyMissionProject);
        WyMissionProject result = wyMissionProjectRepository.save(wyMissionProject);
        WyMissionProjectDTO resultDTO = new WyMissionProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    @Override
    public List<WyMissionProjectDTO> findWyMissionProjectsOrderByUpdateTime(Pageable pageable) {
        Page<WyMissionProject> page = wyMissionProjectRepository.findByOrderByUpdateTimeDesc(pageable);
        List<WyMissionProjectDTO> list = new ArrayList<>();

        for (WyMissionProject wyMissionProject: page){
            WyMissionProjectDTO wyMissionProjectDTO = new WyMissionProjectDTO();
            BeanUtils.copyProperties(wyMissionProject, wyMissionProjectDTO);
            list.add(wyMissionProjectDTO);
        }
        return list;
    }

    @Override
    public WyMissionProjectDTO findWyMissionByProjectId(String projectId) {
        WyMissionProject wyMissionProject = wyMissionProjectRepository.findByProjectId(projectId);
        WyMissionProjectDTO wyMissionProjectDTO = new WyMissionProjectDTO();
        if(wyMissionProject != null) {
            BeanUtils.copyProperties(wyMissionProject, wyMissionProjectDTO);
            return wyMissionProjectDTO;
        }
        else {
            throw new SellException(ResultEnum.PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID);
        }

    }
}
