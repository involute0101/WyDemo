package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.RewardProject;
import com.imooc.sell.dto.ProjectMasterDTO;
import com.imooc.sell.dto.RewardProjectDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.RewardProjectRepository;
import com.imooc.sell.service.RewardProjectService;
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
public class RewardServiceImpl implements RewardProjectService {
    @Autowired
    RewardProjectRepository rewardProjectRepository;

    @Autowired
    UserInfoService userInfoService ;

    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @Override
    @Transactional
    public RewardProjectDTO createRewardProject(RewardProjectDTO rewardProjectDTO) throws Exception {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(rewardProjectDTO.getUserOpenid());
        if (userInfoDTO == null){
            throw  new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
        projectMasterDTO.setProjectType(2);
        projectMasterDTO.setProjectId(KeyUtil.genUniqueKey());
        projectMasterDTO.setUserId(userId);
        ProjectMasterDTO createResult = projectMasterService.createProjectMasterOne(projectMasterDTO);
        if (createResult == null){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        rewardProjectDTO.setProjectId(projectMasterDTO.getProjectId());
        RewardProject rewardProject = new RewardProject();
        BeanUtils.copyProperties(rewardProjectDTO,rewardProject);
        RewardProject result = rewardProjectRepository.save(rewardProject);
        RewardProjectDTO resultDTO = new RewardProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    @Override
    public List<RewardProjectDTO> findRewardProjectsOrderByUpdateTime(Pageable pageable) {
        Page<RewardProject> page = rewardProjectRepository.findByOrderByUpdateTimeDesc(pageable);
        List<RewardProjectDTO> list = new ArrayList<>();

        for (RewardProject rewardProject: page){
            RewardProjectDTO rewardProjectDTO = new RewardProjectDTO();
            BeanUtils.copyProperties(rewardProject, rewardProjectDTO);
            list.add(rewardProjectDTO);
        }
        return list;
    }

    @Override
    public RewardProjectDTO findRewardByProjectId(String projectId) {
        RewardProject rewardProject = rewardProjectRepository.findByProjectId(projectId);
        RewardProjectDTO rewardProjectDTO = new RewardProjectDTO();
        if(rewardProject != null) {
            BeanUtils.copyProperties(rewardProject, rewardProjectDTO);
            return rewardProjectDTO;
        }
        else {
            throw new SellException(ResultEnum.PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID);
        }

    }
}